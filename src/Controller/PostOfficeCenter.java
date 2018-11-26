/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.AzgardOutputPipe;
import Model.PacketBlob;
import Model.SecureEnvelope;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.ArrayBlockingQueue;

/**
 *
 * @author ATX
 */
public class PostOfficeCenter implements MouseListener, Runnable{
    
    //Model
    AzgardOutputPipe outgoingMail;
    ArrayBlockingQueue<SecureEnvelope> outgoingqueue;
    ArrayBlockingQueue<SecureEnvelope> incomingqueue;
    boolean             exit;
    boolean             send;
    
    
    
    public PostOfficeCenter(){
        outgoingMail = new AzgardOutputPipe();
        outgoingqueue = new ArrayBlockingQueue<SecureEnvelope>(50);
        incomingqueue = new ArrayBlockingQueue<SecureEnvelope>(50);
        exit = false;
        
        
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
            if(send){
                
            }
            else{
                //poll for
            }
        }
    }
    
}
