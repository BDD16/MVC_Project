/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.AzgardOutputPipe;
import Model.PacketBlob;
import Model.SecureEnvelope;
import Model.data.user.UserMessage;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import net.jxta.endpoint.Message;

/**
 *
 * @author ATX
 */
public class PostOfficeCenter implements MouseListener, Runnable{
    
    //Model
   
    AzgardOutputPipe outgoingMail;
    ArrayBlockingQueue<SecureEnvelope> outgoingqueue;
    ArrayBlockingQueue<SecureEnvelope> incomingqueue;
    public ArrayBlockingQueue<byte[]> exampledata;
    private static ArrayBlockingQueue<Message> UserMessageQueue;
    boolean             exit;
    boolean             receive;
    
    
    
    public PostOfficeCenter(){
        outgoingMail = new AzgardOutputPipe();
        outgoingqueue = new ArrayBlockingQueue<SecureEnvelope>(50);
        incomingqueue = new ArrayBlockingQueue<SecureEnvelope>(50);
        exampledata = new ArrayBlockingQueue<byte[]>(50);
        UserMessageQueue = new ArrayBlockingQueue<Message>(50);
        exit = false;
        receive = true;
        
        
    }
    
    public ArrayBlockingQueue<SecureEnvelope> getOutputQueue(){
        return outgoingqueue;
    }
    public ArrayBlockingQueue<Message> getUserMessageQueue(){
        return UserMessageQueue;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        while(exit != true){
            if(receive){
                try {
                   UserMessage x = new UserMessage(new String(UserMessageQueue.take().getMessageElement("content").getBytes(true)));
                   x.decrypt(SendMessageController.getInstance().getManager().getUserManager().getCurrentUser().getKeys());
                   System.out.println(new String(x.getContent()));
                   SecureEnvelope y = new SecureEnvelope(x.getContent(), SendMessageController.getInstance().MyKey);
                   System.out.println(new String(y.getMessage()));
                    
                } catch (InterruptedException ex) {
                    Logger.getLogger(PostOfficeCenter.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(PostOfficeCenter.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchPaddingException ex) {
                    Logger.getLogger(PostOfficeCenter.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalBlockSizeException ex) {
                    Logger.getLogger(PostOfficeCenter.class.getName()).log(Level.SEVERE, null, ex);
                } catch (BadPaddingException ex) {
                    Logger.getLogger(PostOfficeCenter.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvalidKeyException ex) {
                    Logger.getLogger(PostOfficeCenter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else{
                //poll for
            }
        }
    }
    
}
