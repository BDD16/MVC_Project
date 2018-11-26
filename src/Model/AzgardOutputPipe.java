/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import static Model.PipeListener.netPeerGroup;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import net.jxta.discovery.DiscoveryService;
import net.jxta.document.AdvertisementFactory;
import net.jxta.document.MimeMediaType;
import net.jxta.document.XMLElement;
import net.jxta.endpoint.Message;
import net.jxta.endpoint.StringMessageElement;
import net.jxta.exception.PeerGroupException;
import net.jxta.peergroup.NetPeerGroupFactory;
import net.jxta.pipe.OutputPipe;
import net.jxta.pipe.OutputPipeEvent;
import net.jxta.pipe.OutputPipeListener;
import net.jxta.pipe.PipeService;
import net.jxta.protocol.PipeAdvertisement;
import net.jxta.rendezvous.RendezVousService;
import net.jxta.rendezvous.RendezvousEvent;
import net.jxta.rendezvous.RendezvousListener;

/**
 *
 * @author ATX
 */
public class AzgardOutputPipe implements Runnable, OutputPipeListener, RendezvousListener {

    private RendezVousService rendezvous;
    private PipeService pipe;
    private DiscoveryService discovery;
    private PipeAdvertisement pipeAdv;
    private String SenderMessage;
    
    //instead of implementing our own just use what's available

    
    public AzgardOutputPipe(String senderMessage){
        SenderMessage = senderMessage;
    }
    
    public AzgardOutputPipe(){
        
    }

    private void startJxta() {
        try {
// create, and Start the default jxta NetPeerGroup netPeerGroup = PeerGroupFactory.newNetPeerGroup();
            NetPeerGroupFactory npgf = new NetPeerGroupFactory();
            netPeerGroup = npgf.getNetPeerGroup();
          
        } catch (PeerGroupException e) {
        }
// get the pipe service, and discovery
        pipe = netPeerGroup.getPipeService();
        rendezvous = netPeerGroup.getRendezVousService();
        rendezvous.addListener(this);
        discovery = netPeerGroup.getDiscoveryService();
        System.out.println("Reading in pipexample.adv");
        try {
            FileInputStream is = new FileInputStream("advertisement.adv");
            
           pipeAdv = (PipeAdvertisement) AdvertisementFactory.newAdvertisement((XMLElement) MimeMediaType.XMLUTF8);
            is.close();
            
        } catch (Exception e) {
            System.out.println("failed to read/parse pipe advertisement");
            e.printStackTrace();
            System.exit(-1);
        }
    }


    public synchronized void run() {
        try {
// create output pipe with asynchronously
            System.out.println("Attempting to create a OutputPipe");
            pipe.createOutputPipe(pipeAdv, this);
// send out a second pipe resolution after we connect to a rendezvous
            if (!rendezvous.isConnectedToRendezVous()) {
                System.out.println("Waiting for Rendezvous Connection");
                try {
                    wait();
// ok we connected to a rendezvous, attempt to resolve again
                    pipe.createOutputPipe(pipeAdv, this);
                } catch (InterruptedException e) {
                }
            }
        } catch (IOException e) {
            System.out.println("OutputPipe creation failure");
            e.printStackTrace();
            System.exit(-1);
        }
    }
    @Override
    public synchronized void rendezvousEvent(RendezvousEvent event) {
        if (event.getType() == event.RDVCONNECT) {
        }
    }
    @Override
    public void outputPipeEvent(OutputPipeEvent event) {
        System.out.println(" Got an output pipe event");
        OutputPipe op = event.getOutputPipe();
        StringMessageElement sme = null;
        try {
            System.out.println("Sending message");
            Message msg = new Message();
            Date date = new Date(System.currentTimeMillis());
            sme = new StringMessageElement(SenderMessage,date.toString(), null);
                    msg.addMessageElement(null, sme);
            op.send(msg);
        } catch (IOException e) {
            System.out.println("failed to send message");
            e.printStackTrace();
        }
        op.close();
        System.out.println("message sent");
    }
    
    

  
}
