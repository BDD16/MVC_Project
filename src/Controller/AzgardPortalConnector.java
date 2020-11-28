/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.NewChatView;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.jxta.peergroup.PeerGroup;

/**
 *
 * @author ATX
 * This class will be the controller for the send button of general data (FTP) 
 * as well as for sending text via a P2P secure pipe using JXTA
 */
public class AzgardPortalConnector implements MouseListener{
    
    PeerGroup Avenger;
    
    
    AzgardPortalConnector(PeerGroup avenger){
        Avenger = avenger;
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
        if(Avenger == null){
            //this means that the avenger isn't even found or nobody is logged or
            //some catastrophic error occured
            Logger.getLogger(AzgardPortalConnector.class.getName()).log(Level.SEVERE, null, e);
        }
        
        if(Avenger.getPeerGroupID() == null){
            //this means that no pipe is setup
            Logger.getLogger(AzgardPortalConnector.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
