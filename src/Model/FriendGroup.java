/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import java.io.IOException;
import java.net.Authenticator;
import java.util.Enumeration;
import java.util.Vector;
import net.jxta.*;
import net.jxta.credential.AuthenticationCredential;
import net.jxta.credential.Credential;
import net.jxta.impl.*;
import net.jxta.pipe.PipeService;
import net.jxta.protocol.ModuleImplAdvertisement;
import net.jxta.rendezvous.RendezVousService;
import net.jxta.peergroup.PeerGroup;
import net.jxta.peergroup.NetPeerGroupFactory;
import net.jxta.exception.PeerGroupException;
import net.jxta.document.Advertisement;
import net.jxta.discovery.DiscoveryService;
import net.jxta.document.StructuredDocument;
import net.jxta.id.ID;
import net.jxta.id.IDFactory;
import net.jxta.membership.MembershipService;
import net.jxta.pipe.PipeService;
import net.jxta.protocol.PeerGroupAdvertisement;

/**
 *
 * @author ATX
 */
public class FriendGroup {
    
    private PeerGroup netPeerGroup;
    private PeerGroup myPeerGroup;
    private PeerGroup restoNet;
    private int timeout = 3000;//timesout after 3 seconds
    
    //services
    private PipeService pipes;
    private DiscoveryService disco;
    private MembershipService membershipService;
    
    
    
    
    //discovered peers
    private Vector friendsVector;
    
    FriendGroup() {
        try{
        NetPeerGroupFactory npgf = new NetPeerGroupFactory();
        
        netPeerGroup = npgf.getNetPeerGroup();
        }catch(PeerGroupException e){
            System.out.println("Fatal error: creating the net peer group");
            System.exit(1);
        }
        
        
       
                
    }
    
    public void CreatePeerGroup(String groupName, String groupDescription){
         

try
{
    //We will create a new group based on the netPeerGroup so let's copy its
    //impl advertisement and modify it.
    ModuleImplAdvertisement implAdv = 
      netPeerGroup.getAllPurposePeerGroupImplAdvertisement();
      
    myPeerGroup = netPeerGroup.newGroup(
            null,               //Create a new group id for this group.
            implAdv,            //Use the above advertisement.
            groupName,       //This is the name of the group.
            groupDescription //This is the description of the group.
            );

    System.out.println("---Peer group created successfully, id: " + 
      myPeerGroup.getPeerGroupAdvertisement().getID() );
    //Now that the group is created, it is automatically published and stored locally,
    //but we need to publish it remotely so other peers can discover it.
    disco.remotePublish( myPeerGroup.getPeerGroupAdvertisement() );
    System.out.println("---Published peer group advertisement remotely");
}
catch (Exception e)
{
    System.out.println("An error occurred");
    e.printStackTrace();
}


    }
    
    public void startJxta() throws PeerGroupException{
        //Discover or create or create and join the default jxta network
        NetPeerGroupFactory npgf = new NetPeerGroupFactory();
        if(npgf != null){
  
        netPeerGroup = npgf.getNetPeerGroup();
        }
        
        // Discover and join the RestoNet peergroup
        // HungryPeers never create the RestoNet peergroup
        try {
            if (!joinRestoNet(  )) {
                System.out.println(
                       "Sorry could not find the RestoNet Peergroup");
                System.exit(2);
            }
        } catch (Exception e) {
            System.out.println("Can't join RestoNet group");
            System.exit(1);
        }
    }
    
    
    private boolean joinRestoNet(  ) {

        int count = 3; // Maximum number of attempts to discover

        System.out.println(
            "Attempting to discover the RestoNet Peergroup");

        // Get the discovery service handle from the NetPeerGroup
        DiscoveryService hdisco = netPeerGroup.getDiscoveryService(  );

        // All discovered RestoNet peers
        Enumeration ae = null;

        // Loop until we find the RestoNet peergroup advertisement
        // or we've exhausted the desired number of attempts
        while (count-- > 0) {
            try {
                // Check if we have the advertisement in the local
                // peer cache
                ae = hdisco.getLocalAdvertisements(
                    DiscoveryService.GROUP, "Name", "RestoNet");

                // If we found the RestoNet advertisement, we are done
                if ((ae != null) && ae.hasMoreElements(  ))
                    break;

                // The RestoNet advertisement is not in the local
                // cache. Send a discovery request to search for it.
                hdisco.getRemoteAdvertisements(null,
                       DiscoveryService.GROUP, "Name",
                       "RestoNet", 1, null);

                // Wait to give peers a chance to respond
                try {
                    Thread.sleep(timeout);
                } catch (InterruptedException ie) {}
            } catch (IOException e) {
                // Found nothing! Move on.
            }
        }

        // Check if we found the RestoNet advertisement
        if (ae == null || !ae.hasMoreElements(  )) {
            return false;
        }

        System.out.println("Found the RestoNet PeerGroup Advertisement");
        // Get the advertisement
        PeerGroupAdvertisement adv =
            (PeerGroupAdvertisement) ae.nextElement(  );

        try {
            // Call the peergroup factory to instantiate a new
            // peergroup instance
            restoNet = netPeerGroup.newGroup(adv);
        } catch (Exception e) {
          System.out.println("Could not create RestoPeerGroup");
          return false;
        }

        try {
            // Get the discovery and pipe services for the RestoNet
            // peergroup (unused in this example)
            disco = restoNet.getDiscoveryService(  );
            pipes = restoNet.getPipeService(  );
        } catch (Exception e) {
            System.out.println("Error getting services from RestoNet");
            throw e;
        }

        System.out.println(
            "The Peer joined the restoNet PeerGroup");
        return true;
    }
    
    private void connectToRdv(PeerGroup peerGroup)
{     
        RendezVousService rdv = null;
    
    if( rdv == null)
    {
        //Get the rdv service
        rdv = peerGroup.getRendezVousService();
    }
    
    //Make sure that we are connected before proceeding
    while( !rdv.isConnectedToRendezVous() )
    {
        try
        {
            Thread.sleep(5000);
        }
        catch (InterruptedException e1)
        {
            System.out.println("rdv connect interrupted");
            e1.printStackTrace();
        }
    }
}
    
     

private void joinGroup()
{
    //Assuming myPeerGroup has been instantiated 
    //before calling this method.
    System.out.println("Trying to join the peer group");
    try
    {
        //Create the document that will identity this peer.
        StructuredDocument identityInfo = null;
            //No identity information required for our group.

        AuthenticationCredential authCred = 
          new AuthenticationCredential( 
            myPeerGroup, //Peer group that it is created in 
            null); //authentication method. );
        MembershipService membershipService = 
           myPeerGroup.getMembershipService();
           net.jxta.membership.Authenticator auth = membershipService.apply(authCred);
            
        //See if the group is ready to be joined.
        //Authenticator currently makes no distinction between 
        //failed and unfinished authentication.
        if( auth.isReadyForJoin() )
        {
            Credential myCred = membershipService.join((net.jxta.membership.Authenticator) auth);
            System.out.println("Joined myPeerGroup");
               
            System.out.println("Group id: " + 
                myPeerGroup.getPeerGroupID() );                
        }
        else
        {
            System.out.println("Unable to join the group");
        }
    }
    catch (Exception e)
    {
        System.out.println("An error occurred"); 
        e.printStackTrace();
    }
}
    
    public ID createPipe(PeerGroup pipeToGroup){
        ID id = IDFactory.newPipeID( pipeToGroup.getPeerGroupID() );
        System.out.println( id.toURI() );

        return id;
    }

}
