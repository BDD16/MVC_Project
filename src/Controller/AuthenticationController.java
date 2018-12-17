package Controller;

import java.awt.HeadlessException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Model.Authentication;
import Model.Avenger;
import Model.AzgardMessaging;
import Model.DrBanner;
import Model.GammaEngine;
import Model.GammaMachine;
import View.MainChat;
import View.Mainwindow;
import View.NewChatView;
import View.Settings;
import View.WindowContainer;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthenticationController implements MouseListener {
	
	private Mainwindow MainView;
	private GammaMachine model;
	private MainChat MainChatView;
	private GammaEngine Encryption;
	private Settings settingsView;
	
	private AzgardMessaging P2PServer;
	
	public AuthenticationController(Mainwindow mainview){
		MainView = mainview;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		//THIS NEEDS TO HAVE IT'S OWN CONTROLLER
				//use this as a view switcher
				//each class is a view
				//so close the main window after logging on and open a new window
				this.MainView.setUsername(this.MainView.getLogin().getText());
				this.MainView.setPassword(this.MainView.getPassword());
				this.MainView.setPasswordstring(this.MainView.getPassword().getPassword().toString());
				
				//TODO: AUTHENTICATION
				
				//if the Authentication succeeds then open the view
				try {
					if(Authentication.VerifyPassword(this.MainView.getUsername(), 
							this.MainView.getPassword())){
						WindowContainer original = null;
						original =  (WindowContainer) MainView.AllViews.getView("WindowContainer");

						this.MainView.getView().setVisible(false);
						MainChat chatview = new MainChat(this.MainView.AllViews);
                                                NewChatView test = new NewChatView(this.MainView);
                                            try {
                                                chatview.controller.getAvengerRelationalDB().AddAnAvenger(MainView.getUsername(), new Avenger(MainView.getUsername()));
                                            } catch (URISyntaxException ex) {
                                                Logger.getLogger(AuthenticationController.class.getName()).log(Level.SEVERE, null, ex);
                                            }
						chatview.AllViews = this.MainView.AllViews;
						chatview.AllViews.addToViewArray(chatview);
						chatview.addMouseListener(this);
						chatview.controller.setMainWindow(this.MainView);
						original.getWindow().remove(this.MainView.getView());
                                                test.mainwindow = this.MainView;
						original.setWindow((JPanel) test/*chatview.getView()*/);
						original.getWindow().setVisible(true);
						
						//Turn on P2P Server
						
						/* DrBanner y = new DrBanner();
						Thread x = new Thread(y);//client
						chatview.Client = y;
						x.start();
						try {
							P2PServer = new AzgardMessaging();
							P2PServer.TurnServerOn();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} */
						
						
					}
					
					else{
						//
						JFrame messageBox = new JFrame("Unable to login");
						JPanel message = new JPanel();
						JLabel whoa = new JLabel();
						JButton okay = new JButton("Okay");
						whoa.setText("Unable to login");
						message.add(whoa);
						message.add(okay);
						messageBox.add(message);
						messageBox.setLocationRelativeTo(this.MainView.getWindow());
						messageBox.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);//so it doesn't close out the whole program
						messageBox.setVisible(true);
						messageBox.setSize(300, 100);
						System.out.println("failed to login");
					}
				} catch (HeadlessException | NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	}

	private Avenger NewAvenger(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
