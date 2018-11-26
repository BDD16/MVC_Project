package Controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import Model.GammaEngine;
import Model.GammaMachine;
import View.AvengersList;
import View.MainChat;
import View.Mainwindow;
import View.NewChatView;
import View.Settings;
import View.WindowContainer;

	
public class SettingsSmasher implements MouseListener{
		private Mainwindow MainView;
		private GammaMachine model;
		private MainChat MainChatView;
		private GammaEngine Encryption;
		private Settings settingsView;
                private NewChatView View;
		
		public SettingsSmasher(Mainwindow theView, GammaMachine theModel){
			this.MainView = theView;
			this.setModel(theModel);
			
			
			
		}
                
                public SettingsSmasher(NewChatView view){
                    View = view;
                    
                }
		
		private void setModel(GammaMachine theModel) {
			model = theModel;
			
		}
		
		public Mainwindow getMainwindow(){
			return MainView;
		}
		
		public void setMainwindow(Mainwindow window){
			this.MainView = window;
		}

		public SettingsSmasher(MainChat mainChat, GammaEngine gammaEngine) {
			// TODO Auto-generated constructor stub
			this.MainChatView = mainChat;
			this.MainView = mainChat.controller.getMainWindow();
			this.setEncryption(gammaEngine);
			this.MainChatView.addMouseListener(new SettingsListener());
		}


		private void setEncryption(GammaEngine gammaEngine) {
			Encryption = gammaEngine;
			
		}

		public SettingsSmasher(MainChat mainChat, GammaMachine theModel) {
			// TODO Auto-generated constructor stub
			this.MainChatView = mainChat;
			this.setModel(theModel);
			this.MainChatView.addMouseListener(new SettingsListener());
		}


		public SettingsSmasher(Settings settings, GammaMachine theModel) {
			// TODO Auto-generated constructor stub
			this.settingsView = settings;
			this.setModel(theModel);
			//this.MainChatView.addMouseListener((MouseListener) new SettingsListener());
		
		}


		private class SettingsListener implements MouseListener{

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
				// TODO Auto-generated method stub
				System.out.println("you pressed some container");
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(settingsView);

		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fc.getSelectedFile();
		            //This is where a real application would open the file.
		            
		        } else {
		            
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
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			if(e.getComponent().getName() == "RSAPublicKey"){
			System.out.println("you clicked a container in settings");
			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Public Key (.DER and .PEM)", "DER", "PEM");
			fc.setFileFilter(filter);
			int returnVal = fc.showOpenDialog(settingsView);

	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fc.getSelectedFile();
	            //This is where a real application would open the file.
	            
	        } else {
	            
	        }
	        
		 }
			else if (e.getComponent().getName() == "RSAPrivateKey"){
				System.out.println("you clicked a container in settings");
				JFileChooser fc = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Private Key (.DER and .PEM)", "DER", "PEM");
				fc.setFileFilter(filter);
				int returnVal = fc.showOpenDialog(settingsView);

		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fc.getSelectedFile();
		           
		            //This is where a real application would open the file.
		            
		        } else {
		            
		        }
		        
			}
			else if (e.getComponent().getName() == "gammaButton"){
				System.out.println("we are going to head back to the lab");
				this.settingsView.getView().setVisible(false);
				WindowContainer original = null;
				original =  (WindowContainer) settingsView.AllViews.getView("WindowContainer");
	
				this.MainChatView = (MainChat) this.settingsView.AllViews.getView("MainChatView");
				this.MainView =  (Mainwindow) this.settingsView.AllViews.getView("LoginWindow");
				original.getWindow().remove(this.settingsView.getView());
				original.setWindow((JPanel) MainChatView.getView());
				original.getWindow().setVisible(true);
			}
			
			else if (e.getComponent().getName() == "AESSymetricKey"){
				
			}
			
			else if (e.getComponent().getName() == "FriendsList"){
				System.out.println("We clicked the Friends List");
				WindowContainer original = null;
				original =  (WindowContainer) settingsView.AllViews.getView("WindowContainer");
				
				AvengersList friendsView = new AvengersList();
				friendsView.AllViews = settingsView.AllViews;
				friendsView.AllViews.addToViewArray(friendsView);
						
	
				MainChatView = (MainChat) settingsView.AllViews.getView("MainChatView");
				MainView =  (Mainwindow) settingsView.AllViews.getView("LoginWindow");
				original.getWindow().remove(settingsView.getView());
				original.setWindow((JPanel) friendsView.getView());
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


}
