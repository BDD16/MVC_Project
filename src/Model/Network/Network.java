/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * this file and IpChecker and NetworkInterface were taken from: 
*  https://www.programcreek.com/java-api-examples/?code=pja35/p2pEngine/p2pEngine-master/src/main/java/model/network/Network.java#
 */
package Model.Network;

import Model.GammaMachine;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.jxta.document.AdvertisementFactory;
import net.jxta.exception.PeerGroupException;
import net.jxta.id.IDFactory;
import net.jxta.peergroup.PeerGroup;
import net.jxta.peergroup.PeerGroupID;
import net.jxta.pipe.PipeID;
import net.jxta.pipe.PipeService;
import net.jxta.platform.NetworkConfigurator;
import net.jxta.platform.NetworkManager;
import net.jxta.protocol.ModuleImplAdvertisement;
import net.jxta.protocol.PipeAdvertisement;
import Model.IpChecker;
import Model.IpChecker;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Security;
import javax.crypto.NoSuchPaddingException;


/**
 *
 * @author ATX
 */
public class Network implements NetworkInterface {
	private NetworkManager networkManager;
	private HashMap<String, PeerGroup> peergroups = new HashMap<String, PeerGroup> ();
        private HashMap<String, PublicKey> peers = new HashMap<String, PublicKey>();
	private PeerGroup defaultGroup;
	private PeerGroup temp = null;
        private GammaMachine encryptionSuite;
	
	
	/**
	 * Create a new P2P network, setting the port and the 
	 * folder where Jxta store the configuration and his cache.
	 * Define the peer name on the network
	 * @param port Port used by Jxta network
	 * @param folder Folder where Jxta store it need.
	 * @param peerName Peer name on the network
	 */
	public Network(int port, String folder, String peerName) {
            try {
                Security.addProvider( new org.bouncycastle.jce.provider.BouncyCastleProvider() );
                File configFile = null;
                configFile = new File(System.getProperty("user.dir") + "/HulkNetCache/"+ folder + "/.cache"); /* Used by the networkManager */
                networkManager = networkManagerSetup(configFile, port, peerName);
                networkManager.setConfigPersistent(false);
                encryptionSuite = new GammaMachine();
                encryptionSuite.generateKeys();
                peers.put(peerName,encryptionSuite.getPubkey());
                        } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchPaddingException ex) {
                Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
            } 
	}
        
        public HashMap<String, PublicKey> getPeers(){
            return peers;
        }

	@Override
	public PeerGroup getGroup(String group) {
		return this.peergroups.get(group);
	}
	
	@Override
	public PeerGroup getDefaultGroup() {
		return defaultGroup;
	}

	
	/**
	 * Create our default PeerGroup.
	 */
	private void createDefaultGroup() {
		try {
			PeerGroup netpeerGroup = networkManager.getNetPeerGroup();
			ModuleImplAdvertisement madv = netpeerGroup.getAllPurposePeerGroupImplAdvertisement();
			System.out.println(madv.toString());
			defaultGroup = netpeerGroup.newGroup(this.generatePeerGroupID("HulkNet-group"),
					madv, "HulkNet group", "HulkNet group");
			System.out.println("default group generated");
			defaultGroup.startApp(new String[0]);
			defaultGroup.getRendezVousService().setAutoStart(true, 60*1000);
		} catch (PeerGroupException e) {
			System.err.println("impossible to create default group");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void addGroup(final String name) {
		ModuleImplAdvertisement mAdv = null;
		temp = null;
		System.out.println("creating new group ..");
		try {
			mAdv = defaultGroup.getAllPurposePeerGroupImplAdvertisement();
			temp = defaultGroup.newGroup(generatePeerGroupID(name), mAdv, name, name); /* creating & publishing the group */
			getDefaultGroup().getDiscoveryService().remotePublish(temp.getPeerGroupAdvertisement());
			temp.startApp(new String[0]);
			temp.getRendezVousService().setAutoStart(true, 60);
			peergroups.put(name, temp);
		} catch (Exception e) {
			e.printStackTrace();
		} /* Getting the advertisement of implemented modules */
		
	}
	
	@Override 
	public void addGroup(final String name, boolean idGroup) {
		addGroup(name);
		if(idGroup) {
			addGroup("id-" + name);
		}
	}

	@Override
        @SuppressWarnings("deprecation")
	public void start() {
		try {
			networkManager.startNetwork(); /* Starting the network and JXTA's infrastructure. */
			networkManager.getNetPeerGroup()
				.getRendezVousService().setAutoStart(true, 60*1000); /* Switching to RendezVousMode if needed. Check every 60s */
			createDefaultGroup();
			System.out.println("GroupName : " + defaultGroup.getPeerGroupName());
			System.out.println("waiting for rendez vous.");
			
			if(networkManager.waitForRendezvousConnection(10000)) {
				System.out.println("rendez vous found");
			}
			else {
				System.out.println("no rendez vous ...");
			}
		} catch (PeerGroupException | IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void stop() {
		networkManager.stopNetwork();
	}
	
	/**
	 * Generate an unique PeerGroup ID from the peer group name
	 * @param peerGroupName A string, generally the peer name, from the PeerID will be generated.
	 * @return the newly generated PeerID
	 */
	private PeerGroupID generatePeerGroupID(String peerGroupName) {
		return IDFactory.newPeerGroupID(PeerGroupID.defaultNetPeerGroupID, peerGroupName.getBytes());
	}
	
	
	/**
	 * Setup the networkManager that will store data in configFile folder.
	 * @param configFile The file where the network manager will put or retrieve data.
	 * @param port The port used by JXTA to communicate.
	 * @param peerName The new future peer name.
	 * @return
	 */
	private NetworkManager networkManagerSetup(File configFile, int port, String peerName) {
		NetworkManager manager = null;
		NetworkConfigurator configurator = null;
		try {
			manager = new NetworkManager(NetworkManager.ConfigMode.EDGE, peerName, configFile.toURI()); /* Setting network */
			configurator = manager.getConfigurator(); /* Getting configurator for future tweaks */
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		/* Configuration settings */
		 configurator.setTcpPort(port);
         configurator.setTcpEnabled(true);
         configurator.setHttpPort(port+10);
         configurator.setHttpEnabled(true);
         configurator.setTcpIncoming(true);
         configurator.setHttpIncoming(true);
         configurator.setHttpOutgoing(true);
         configurator.setTcpOutgoing(true);
         configurator.setUseMulticast(true);
         try {
			configurator.setTcpInterfaceAddress("0.0.0.0");
			configurator.setTcpPublicAddress(IpChecker.getIp(), false);
			configurator.setHttpInterfaceAddress("0.0.0.0");
			configurator.setHttpPublicAddress(IpChecker.getIp(), false);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
         configurator.setTcpEndPort(-1);
         configurator.setTcpStartPort(-1);
         configurator.setName("HulkNetpeerGroup");
         configurator.setDescription("HulkNet default peer group");
         configurator.setPrincipal("HulkNet peer group");
     
		return manager;
	}
	
	/**
	 * Set the JXTA's logger's level
	 * @param level
	 */
	public void setLogger(Level level) {
		Logger.getLogger("net.jxta").setLevel(level);
	}

	@Override
	public boolean isStarted() {
		return this.networkManager.isStarted();
	}
	
	
	/**
	 * Generate an advertisement for a new Pipe
	 * @param id the Pipe's id
	 * @param is_multicast the pipe's type.
	 * @return a PipeAdvertisement.
	 */
	public static PipeAdvertisement getPipeAdvertisement(PipeID id, boolean is_multicast) {
        PipeAdvertisement adv = (PipeAdvertisement )AdvertisementFactory.
            newAdvertisement(PipeAdvertisement.getAdvertisementType());
        adv.setPipeID(id);
        if (is_multicast)
            adv.setType(PipeService.PropagateType); 
        else 
            adv.setType(PipeService.UnicastType); 
        adv.setName("Pipe");
        adv.setDescription("...");
        return adv;
    }
	
	@Override
	public void boot(String adress) {
		URI theSeed = URI.create(adress);
		try {
			networkManager.getConfigurator().addSeedRendezvous(theSeed);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//networkManager.setUseDefaultSeeds(false);
                
	}
	
	public ArrayList<String> getSeeds() {
		ArrayList<String> ips = new ArrayList<String>();
		try {
			for(URI ip : networkManager.getConfigurator().getRdvSeedingURIs()){
				ips.add(ip.toString());
			}
			return ips;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public String getBootStrapIp() {
		try {
			return "tcp://" + this.networkManager.getConfigurator().getTcpPublicAddress() + ":" + networkManager.getConfigurator().getTcpPort();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
