package Controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Model.DataBase;
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

public class HulkControllerNewMainChat implements MouseListener{
	private Mainwindow MainView;
	private GammaMachine model;
	private MainChat MainChatView;
	private GammaEngine Encryption;
	private Settings settingsView;
	private DataBase AvengerRelationalDB;
        private NewChatView View;
	
	public HulkControllerNewMainChat(Mainwindow theView, GammaMachine theModel){
		this.MainView = theView;
		this.setModel(theModel);
		
		
	}
        
        public HulkControllerNewMainChat(NewChatView view, GammaMachine theModel){
            View = view;
            model = theModel;
            MainView = view.mainwindow;
        }
	
	public HulkControllerNewMainChat(MainChat mainChat, GammaEngine gammaEngine) {
		// TODO Auto-generated constructor stub
		this.MainChatView = mainChat;
		this.setEncryption(gammaEngine);
		this.MainChatView.addMouseListener(new HulkListener());
            try {
                this.setAvengerRelationalDB(new DataBase(MainView.getUsername()));
            } catch (URISyntaxException ex) {
                Logger.getLogger(HulkControllerNewMainChat.class.getName()).log(Level.SEVERE, null, ex);
            }
	}


	public HulkControllerNewMainChat(MainChat mainChat, GammaMachine theModel) {
		// TODO Auto-generated constructor stub
		this.MainChatView = mainChat;
		this.setModel(theModel);
		this.MainChatView.addMouseListener(new HulkListener());
		Mainwindow initialview = (Mainwindow) mainChat.AllViews.getFromViewArray("LoginWindow");
            try {
                this.setAvengerRelationalDB(new DataBase(initialview.getUsername()));
            } catch (URISyntaxException ex) {
                Logger.getLogger(HulkControllerNewMainChat.class.getName()).log(Level.SEVERE, null, ex);
            }
	}


	public HulkControllerNewMainChat(Settings settings, GammaMachine theModel) {
		// TODO Auto-generated constructor stub
		this.settingsView = settings;
		this.setModel(theModel);
		this.MainChatView.addMouseListener(new HulkListener());
            try {
                this.setAvengerRelationalDB(new DataBase(MainView.getUsername()));
            } catch (URISyntaxException ex) {
                Logger.getLogger(HulkControllerNewMainChat.class.getName()).log(Level.SEVERE, null, ex);
            }
	}


	private class HulkListener implements MouseListener{


		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			System.out.println("you clicked the gamma buttton OOOHHHNOOO");
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			ImageIcon ig = new ImageIcon("src/img/GammaSignInvertedSmall.png");
			MainChatView.gammaLabel.setIcon(ig);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			ImageIcon g = new ImageIcon(getClass().getResource("img/GammaSignSmall.png"));
			View.gammaLabel.setIcon(g);
			//perform cryptographic operations on the plain text area
                        if(View != null){
                            try {
				GammaMachine hulk = new GammaMachine();
				hulk.generateKeys();
				//Generate Keys here or grab the keys from the Key File if set
				
				//Grab Key File
				Settings settingsPlaceHolder = (Settings) MainChatView.AllViews.getView("SettingsView");
				//settingsPlaceHolder.
				
				
				byte[] drbanner = hulk.RSAEncrypt(MainChatView.getPlaintextarea().getText().getBytes(), hulk.getPubkey());
				View.getCipherarea().setText(new String(drbanner));
			} catch (NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
                        }
			
			try {
				GammaMachine hulk = new GammaMachine();
				hulk.generateKeys();
				//Generate Keys here or grab the keys from the Key File if set
				
				//Grab Key File
				Settings settingsPlaceHolder = (Settings) MainChatView.AllViews.getView("SettingsView");
				//settingsPlaceHolder.
				
				
				byte[] drbanner = hulk.RSAEncrypt(MainChatView.getPlaintextarea().getText().getBytes(), hulk.getPubkey());
				
                                MainChatView.getCipherarea().setText(new String(drbanner));
			} catch (NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
				
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


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		/*if(e.getComponent().getName() == "GammaRaySign"){
			
			ImageIcon ig = new ImageIcon(getClass().getResource("/img/GammaSignInvertedSmall.png"));
			MainChatView.gammaLabel.setIcon(ig);
			
			SwingUtilities.invokeLater(new Runnable() {
			    public void run() {
			    	MainChatView.progressBar.setVisible(true);
			    
			    }
			});
		} */
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getComponent().getName() == "GammaRaySign"){
                    
			
			//ImageIcon g = new ImageIcon("src/img/GammaSignSmall.png");
			//View.gammaLabel.setIcon(g);
			//perform cryptographic operations on the plain text area
			
			
			try {
				GammaMachine hulk = new GammaMachine();
				hulk.generateKeys();
				byte[] drbanner = hulk.RSAEncrypt(View.getPlaintextarea().getText().getBytes(), hulk.getPubkey());
                                SendMessageController.getInstance().SetFriendKey(hulk.getPubkey());
				View.getCipherarea().setText(new String(drbanner));
                                View.getMainChat().addElement(View.getPlaintextarea().getText());
                                SendMessageController.getInstance().setMyKey(hulk.getPrivateKey());
                               
                                
				
				SwingUtilities.invokeLater(new Runnable() {
				    public void run() {
				    	//MainChatView.progressBar.setVisible(false);
				    }
				});
			} catch (NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(MainChatView != null){
                            SwingUtilities.invokeLater(new Runnable() {
                                public void run() {
                                    MainChatView.progressBar.setVisible(false);
                                    while(MainChatView.getCipherarea().getText() == null){
                                            MainChatView.progressBar.setValue(MainChatView.progressBar.getValue() + 1);
                                    }

                                }
                            });
                        }
			
		}
		
	  if(e.getComponent().getName() == "Settings"){
		  System.out.println("this is another button click for settings");
		    this.MainView.getView().setVisible(false);
		    WindowContainer original = null;
			original =  (WindowContainer) MainView.AllViews.getView("WindowContainer");

			Settings settings = new Settings();
			settings.AllViews = this.MainChatView.AllViews;
			settings.AllViews.addToViewArray(settings);
			this.settingsView = settings;
			original.getWindow().remove(this.MainChatView.getView());
		    original.setWindow((JPanel)settingsView.getView());
			original.getWindow().setVisible(true);
			
			
	  }
          
          if(e.getComponent().getName() == "SettingsButton"){
              System.out.println("Changing view to settings");
              //this.MainView.getView().setVisible(false);
              WindowContainer original = null;
              original = (WindowContainer) MainView.AllViews.getView("WindowContainer");
              
              Settings settings = new Settings();
              settings.AllViews = this.MainView.AllViews;
              settings.AllViews.addToViewArray(settings);
              this.settingsView = settings;
              original.getWindow().remove(this.MainView.getView());
              original.setWindow((JPanel)settingsView.getView());
              original.getWindow().setVisible(true);
          }
	  
	  if(e.getComponent().getName() == "LogoutButton"){
		  System.out.println("We Are Logging Out");
		  WindowContainer original = null;
		  original =  (WindowContainer) MainChatView.AllViews.getView("WindowContainer");

		 MainView = new Mainwindow();
		 MainView.AllViews = MainChatView.AllViews;
		 this.MainChatView.getView().setVisible(false);
		 original.getWindow().remove(this.MainChatView.getView());
		 original.setWindow((JPanel) MainView.getView());
		 original.getWindow().setVisible(true);
			
	  }
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public GammaMachine getModel() {
		return model;
	}

	public void setModel(GammaMachine model) {
		this.model = model;
	}

	public GammaEngine getEncryption() {
		return Encryption;
	}

	public void setEncryption(GammaEngine encryption) {
		Encryption = encryption;
	}
	
	public Mainwindow getMainWindow(){
		return MainView;
	}
	
	public void setMainWindow(Mainwindow x){
		this.MainView = x;
	}

	public DataBase getAvengerRelationalDB() {
		return AvengerRelationalDB;
	}

	public void setAvengerRelationalDB(DataBase avengerRelationalDB) {
		AvengerRelationalDB = avengerRelationalDB;
	}
	
}
