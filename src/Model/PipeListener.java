/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.FileInputStream;
import java.util.Date;
import net.jxta.document.AdvertisementFactory;
import net.jxta.document.MimeMediaType;
import net.jxta.endpoint.Message;
import net.jxta.endpoint.MessageElement;
import net.jxta.exception.PeerGroupException;
import net.jxta.peergroup.NetPeerGroupFactory;
import net.jxta.peergroup.PeerGroup;
import net.jxta.pipe.InputPipe;
import net.jxta.pipe.PipeMsgEvent;
import net.jxta.pipe.PipeMsgListener;
import net.jxta.pipe.PipeService;
import net.jxta.protocol.PipeAdvertisement;

/**
 *
 * @author ATX
 */
public class PipeListener implements PipeMsgListener {

    static PeerGroup netPeerGroup = null;
    private final static String TAG = "PipeListenerMsg";
    private final static String FILENAME = "examplepipe.adv";
    private PipeService pipeSvc;
    private PipeAdvertisement pipeAdv;
    private InputPipe pipeIn = null;

    public static void main(String args[]) {
        PipeListener myapp = new PipeListener();
        myapp.startJxta();
        myapp.run();
    }

    public PipeListener() {
    } // Default constructor

    @Override
    public void pipeMsgEvent(PipeMsgEvent event) {
        Message msg = null;
        msg = event.getMessage();
        if (msg == null) {
            return;
        }
        String SenderMessage = null;
// get the message element named SenderMessage
        MessageElement msgElement = msg.getMessageElement(null, SenderMessage);
// Get message
        if (msgElement.toString() == null) {
            System.out.println("null msg received");
        } else {
            Date date = new Date(System.currentTimeMillis());
            System.out.println("Message received at :" + date.toString());
            System.out.println("Message created at :" + msgElement.toString());
        }
    }

    private void startJxta() {
        try {
// create, and Start the default jxta NetPeerGroup
            NetPeerGroupFactory npgf = new NetPeerGroupFactory();
            netPeerGroup = npgf.getNetPeerGroup();
        } catch (PeerGroupException e) {
        }
        pipeSvc = netPeerGroup.getPipeService();
        System.out.println("Reading in " + FILENAME);
        try {
            FileInputStream is = new FileInputStream(FILENAME);
            //pipeAdv = (PipeAdvertisement) AdvertisementFactory.newAdvertisement(MimeMediaType.XMLUTF8.getMimeMediaType(), is);
            is.close();
        } catch (Exception e) {
        }
    }

// create input pipe, and register as a PipeMsgListener to be
// asynchronously notified of any messages received on this input pipe
    public void run() {
        try {
            System.out.println("Creating input pipe");
            pipeIn = pipeSvc.createInputPipe(pipeAdv, this);
        } catch (Exception e) {
        }
        if (pipeIn == null) {
            System.out.println("Error: cannot open InputPipe");
            System.exit(-1);
        }

        System.out.println("Waiting for msgs on input pipe");
    }

}
