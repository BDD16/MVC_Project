/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Model.data.user.User;
import Model.data.user.UserMessage;
import Model.Network.search.Search;
import Controller.SendMessageController;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.jxta.endpoint.Message;
import net.jxta.peer.PeerID;

/**
 *
 * @author ATX
 */
public class MessageService extends Service<UserMessage> {
    
        BlockingQueue<SecureEnvelope> PostOfficeTunnel;
        public MessageService(ArrayBlockingQueue<SecureEnvelope> InputQueue){
            PostOfficeTunnel = InputQueue;
        }
	@Override
	public String getServiceName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public UserMessage handleMessage(Message m) {
		if(m.getMessageElement("content") == null) return null;
		UserMessage msg = new UserMessage(new String(m.getMessageElement("content").getBytes(true)));
                
		User u = SendMessageController.getInstance().getManager().getUserManager().getCurrentUser();
		if(u != null) {
			if(u.getKeys().getPublicKey().equals(msg.getReceiver().getPublicKey())) {
                                /*PostOfficeTunnel = SendMessageController.getTransferPipe();
                                SecureEnvelope securenvelope = new SecureEnvelope();
                                securenvelope.msg.data = m.getMessageElement("content").getBytes(true);
                                PostOfficeTunnel.add(securenvelope);*/
				SendMessageController.getInstance().getManager().getMessageManager().getCurrentUserConversations().addMessage(msg);
                            try {
                                SendMessageController.getInstance().getMessageQueue().put(m);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(MessageService.class.getName()).log(Level.SEVERE, null, ex);
                            }
				return new UserMessage(new String(m.getMessageElement("content").getBytes(true)));
			}
		}
                
		return null;
		
	}

	public void sendMessage(UserMessage data) {
		sendMessage(data, (PeerID[])null);
	}
	
	@Override
	public void sendMessage(UserMessage data, PeerID... ids) {
		Search<User> s = new Search<User>(this.getNetwork(), User.class.getSimpleName(),
				"publicKey", true);
		s.search(data.getReceiver().getPublicKey().toString(16), 3, 5);
		ArrayList<Search<User>.Result> r = s.getResultsWithPeerID();
		PeerID[] pids = new PeerID[r.size()];
		for(int i = 0; i < r.size(); i++) {
			pids[i] = r.get(i).peerID;
			i++;
		}
		
		sender.sendMessage(data.toString(), this.getServiceName(), pids);
		
	}

}
