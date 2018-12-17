
package Controller;

import Model.data.manager.UserManager;
import Model.RendezVousIp;
import Model.Communication;
import Model.FriendGroup;
import Model.MessageService;
import Model.Network.Network;
import Model.SecureEnvelope;
import Model.UpdateService;
import Model.data.item.Category.Category;
import Model.data.item.Category.Category.CATEGORY;
import Model.data.item.Item;
import Model.data.item.contrat.Clause;
import Model.data.item.contrat.Contrat;
import Model.data.user.User;
import Model.data.user.UserMessage;
import Model.util.VARIABLES;
import View.FileTransferView;
import Model.data.manager.Manager;
import Model.data.user.Conversations;
import View.NewChatView;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.security.PublicKey;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import Model.util.Printer;
import Model.util.secure.AsymKeysImpl;
import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Long.TYPE;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.concurrent.BlockingQueue;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import net.jxta.peergroup.PeerGroup;
import net.jxta.platform.NetworkManager;
import net.jxta.endpoint.Message;

/**
 *
 * @author ATX
 */
public class SendMessageController implements MouseListener {


    NewChatView View;
    FileTransferView FTVView;
    PublicKey        FriendKey;
    PrivateKey       MyKey;
    private static ArrayBlockingQueue<SecureEnvelope> TransferPipe;
    private static ArrayBlockingQueue<byte[]> testPipe;
    private static ArrayBlockingQueue<Message> UserMessageQueue;
    FriendGroup      peerGroup;
    PeerGroup        someGroup;
    Network          network;
    private static NetworkManager   managernetwork;
    Communication    sender;
    MessageService   msgService;
    UserManager      userMngr;
    private static SendMessageController instance = null; //the current instance of this class.
	
	
    private static Manager manager;
	
	//private SharingManager sharingManager;
    
    public SendMessageController(NewChatView view, ArrayBlockingQueue<SecureEnvelope> OutputMesgQueue, ArrayBlockingQueue<Message>InputQueue){
        try {
            View = view;
            TransferPipe = OutputMesgQueue;
            network = new Network(4099, "testFolder", "Blake");
            network.setLogger(Level.SEVERE);
            UserMessageQueue = InputQueue;
            
            
            someGroup = network.getDefaultGroup();
            File x = new File(VARIABLES.BootstrapFilePath);
            System.out.println(x.getAbsolutePath());
            x.createNewFile();
            try {
                    
			if(new File(VARIABLES.BootstrapFilePath).exists()){
				FileReader xmlFile = new FileReader(VARIABLES.BootstrapFilePath);
				BufferedReader br = new BufferedReader(xmlFile);
			    StringBuilder sb = new StringBuilder();
			    String line = br.readLine();
	
			    while (line != null) {
			        sb.append(line);
			        sb.append(System.lineSeparator());
			        line = br.readLine();
			    }
			    String xml = sb.toString();
			    br.close();
				RendezVousIp rdv = new RendezVousIp();//RendezVousIp(xml)
				for(String ip : rdv.getIps()){
					network.boot(ip);
				}
			} else {
				Printer.printInfo(this, "startNetwork", VARIABLES.BootstrapFileName + " doesn't exist.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
            manager = new Manager(network);
	    manager.recovery(VARIABLES.ManagerFilePath);
            network.start();
            addAllGroups();
            //network.addGroup(UserMessage.class.getSimpleName(), true);
            userMngr = new UserManager(manager);
            msgService = new MessageService(TransferPipe);
            startCommunication();
            msgService.setCommunication(sender);
           
            
        } catch (Exception ex) {
            Logger.getLogger(SendMessageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        FriendKey = network.getPeers().get("Blake");//TEST ONLY REMOVE BEFORE RELEASE
        instance = this;
        User user = new User("testnickname", "password", "Banner", "Bruce", "bruce.banner@hulknet.com", "+5121231232");//made up info
                SendMessageController.getInstance().getManager().getUserManager().registration(user);
                SendMessageController.getInstance().getManager().getUserManager().login("testnickname", "password");
        
        
        
        
    }
    
    public static BlockingQueue getTransferPipe() {
        return TransferPipe;
    }
    
    public  ArrayBlockingQueue<Message> getMessageQueue(){
        return UserMessageQueue;
    }

    public static NetworkManager getNetworkManager() {
        return managernetwork;
    }
    
    public static Manager getManager(){
        return manager;
    }
    
    public void setMyKey(PrivateKey lastditcheffort){
        MyKey = lastditcheffort;
    }
    
    private void addAllGroups() {
		network.addGroup(Item.class.getSimpleName(), true);
		network.addGroup(User.class.getSimpleName(), true);
		
		network.addGroup(UserMessage.class.getSimpleName(), true);
	}
	/*
	private void startSharingManager() {
		sharingManager = new SharingManager(manager, network, sender,  VARIABLES.ReplicationsAccount, VARIABLES.CheckTimeAccount);
		//sharingManager.addResiliance(new ContratsResiliance(manager, sender));
		//sharingManager.addResiliance(new FavoritesResiliance(manager, sender));
		//sharingManager.addResiliance(new ItemResiliance(manager, sender));
		//sharingManager.addResiliance(new MessageResiliance(manager, sender));
		sharingManager.addResiliance(new UserResiliance(manager, sender));
		sharingManager.startSharing();
	} */
	
	/**
	 * Start the communication package and adds the services
	 */
	private void startCommunication() {
		try {
			this.sender = new Communication(network,UserMessageQueue);
			sender.addService(new MessageService(TransferPipe));
			sender.addService(new UpdateService());
			//ClassSenderService.addSenderServices(sender);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Start the network and keep instance reference.
	 * TODO keep reference ?
	 */
	private void startNetwork() {
		network = new Network(9800, VARIABLES.NetworkFolderName, VARIABLES.NetworkPeerName);
		network.setLogger(Level.SEVERE);
		//network.boot("tcp://85.171.121.182:9800"); TODO Check !
		try {
			if(new File(VARIABLES.BootstrapFilePath).exists()){
				FileReader xmlFile = new FileReader(VARIABLES.BootstrapFilePath);
				BufferedReader br = new BufferedReader(xmlFile);
			    StringBuilder sb = new StringBuilder();
			    String line = br.readLine();
	
			    while (line != null) {
			        sb.append(line);
			        sb.append(System.lineSeparator());
			        line = br.readLine();
			    }
			    String xml = sb.toString();
			    br.close();
				RendezVousIp rdv = new RendezVousIp(xml);
				for(String ip : rdv.getIps()){
					network.boot(ip);
				}
			} else {
				Printer.printInfo(this, "startNetwork", VARIABLES.BootstrapFileName + " doesn't exist.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		network.start();
	}
	
	/**
	 * Launch the localServer for websocket communication between HTML5 UI and the P2P network.
	 */
	/*private void startLocalServer() {
		try {
			server = new EmbeddedRunner(8080).init().start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	} */
	
	/**
	 * Get the unique SendMessageController instance
	 * @return the SendMessageController instance.
	 */
	public static SendMessageController getInstance() {
		if(instance == null) {
			try {
				throw new Exception("there is no instance of this class");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return instance;
	}
	
	/**
	 * Get the current Network.
	 * @return An instanced and started Network.
	 */
	public Network getNetwork() {
		return network;
	}
	
	
	/**
	 * Get the current Communication
	 * @return
	 */
	public Communication getCommunication(){
		return sender;
	}
	
	/*public void stopServer() {
		if(server == null) return;
		try {
			server.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	} */
	
	/**
	 * Properly close the app : closing network & server, and saving datas.
	 */
	public void close() {
		System.out.println("closing ...");
		//stopServer();
		network.stop();
		//sharingManager.stopSharing();
		
		File f = new File(".data");
		FileWriter fw = null;
		try {
			fw = new FileWriter(f);
			fw.write(manager.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("bye !");	
	}
	
	/* private static void createAccountTest(boolean create) {
		if(create) {
			User user = new User("test", " ", "Doe", "John", "john.doe@hulknet.com", "+33442044204");
			User sender = new User("test2", " ", "name", "firstName", "email@email.email", "phone"); 
			AsymKeysImpl keySender = sender.getKeys().copy();
			keySender.decryptPrivateKey(" ");
			
			SendMessageController.getInstance().getManager().getUserManager().registration(user);
			SendMessageController.getInstance().getManager().getUserManager().registration(sender);
			SendMessageController.getInstance().getManager().getUserManager().login("test", " ");
			
			// Messages 
			UserMessage m1 = new UserMessage(SendMessageController.getInstance().getManager().getUserManager().getCurrentUser().getKeys(), sender.getKeys(), "question", "Bonjour ? Je peux etre votre ami ?");
			AsymKeysImpl key = sender.getKeys().copy();
			key.decryptPrivateKey(" ");
			m1.sign(key);
			UserMessage m2 = new UserMessage(sender.getKeys(), SendMessageController.getInstance().getManager().getUserManager().getCurrentUser().getKeys(), "reponse", "non !!!");
			m2.sign(SendMessageController.getInstance().getManager().getUserManager().getCurrentUser().getKeys());
			// Conversation 
			Conversations c = new Conversations(SendMessageController.getInstance().getManager().getUserManager().getCurrentUser());
			c.addMessage(m1);
			c.addMessage(m2);
			c.sign(SendMessageController.getInstance().getManager().getUserManager().getCurrentUser().getKeys());
			SendMessageController.getInstance().getManager().getMessageManager().addConversations(c);
			// Items 
			Item item1 = new Item(user, "Potatoes", new Category(CATEGORY.FoodAndBeverages), "Great potatoes", "", "FRANCE", "Call me", 0L, 0L, TYPE.OFFER);
			item1.sign(SendMessageController.getInstance().getManager().getUserManager().getCurrentUser().getKeys());
			SendMessageController.getInstance().getManager().getItemManager().addItem(item1);
			Item item2 = new Item(sender, "Carott", new Category(CATEGORY.FoodAndBeverages), "Great food for rabbits", "", "FRANCE", "Call me", 0L, 0L, TYPE.OFFER);
			item2.sign(keySender);
			SendMessageController.getInstance().getManager().getItemManager().addItem(item2);
			SendMessageController.getInstance().getManager().getFavoriteManager().addFavoritesItem(item2);
			// Contracts
			Contrat contract = new Contrat("Food deal", SendMessageController.getInstance().getManager().getUserManager().getCurrentUser());
			contract.addItem(item1);
			contract.addItem(item2);
			contract.addClaus(new Clause("Art. 1", "Can't abort !"));
			SendMessageController.getInstance().getManager().getContratManager().addContrat(contract);
			
			SendMessageController.getInstance().getManager().getUserManager().logout();
			SendMessageController.getInstance().getManager().getUserManager().login("test2", " ");
			SendMessageController.getInstance().getManager().getUserManager().logout();
		}
	}  */
	
	/**
	 * We start the app here !
	 * @param args
	 */
	/*public static void main(String[] args) {
		new SendMessageController(true);
		Network n = SendMessageController.getInstance().getNetwork();
		createAccountTest(false);
		if(Desktop.isDesktopSupported()) {
		  try {
			Desktop.getDesktop().browse(new URI("http://localhost:8080/HulkNetManager/index.html"));
		  } catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		  }
		}
		
	} */
    
    public SendMessageController(FileTransferView view){
        FTVView = view;
        
    }
    
    public void SetFriendKey(PublicKey fk){
        FriendKey = fk;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //TODO:  Create Secure Envelope
        if(View != null){
            try {
                
                //one time key generation
                KeyGenerator kg =  KeyGenerator.getInstance("AES");
                kg.init(128);
                SecretKey sc = kg.generateKey();
                SecureEnvelope envelope = new SecureEnvelope(View.getCipherarea().getText().getBytes(), (Key)sc, FriendKey );
                /*if(!sc.isDestroyed()){
                    sc.destroy();
                }*/
                //TransferPipe.add(envelope);
                
                
                UserMessage x = new UserMessage(SendMessageController.getInstance().getManager().getUserManager().getCurrentUser().getKeys(), SendMessageController.getInstance().getManager().getUserManager().getCurrentUser().getKeys(), "Message", envelope.getSecureEnvelopeData());
                x.sign(SendMessageController.getInstance().getManager().getUserManager().getCurrentUser().getKeys());
                Conversations c = new Conversations(SendMessageController.getInstance().getManager().getUserManager().getCurrentUser());
		c.addMessage(x);
		c.sign(SendMessageController.getInstance().getManager().getUserManager().getCurrentUser().getKeys());
		SendMessageController.getInstance().getManager().getMessageManager().addConversations(c);
                msgService.sendMessage(x);
                //sender.sendMessage(envelope.getSecureEnvelopeData(),"MessageService", network.getDefaultGroup().getPeerID());
               // network.stop();
                
                
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(SendMessageController.class.getName()).log(Level.SEVERE, null, ex);
            } /*catch (DestroyFailedException ex) {
                Logger.getLogger(SendMessageController.class.getName()).log(Level.SEVERE, null, ex);
            }*/
        
        }
        
        //TODO:  Pass Secure Envelope to an Azgard outputPipe
        
        
    }


    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
