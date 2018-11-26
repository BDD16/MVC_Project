package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Controller.HulkController;
import Controller.SettingsSmasher;
import Model.DrBanner;
import Model.GammaEngine;
import Model.GammaMachine;
import Model.ViewHolder;

public class Settings extends JFrame {
	JPanel view;
	public SettingsSmasher controller;
	private JTextField prvkey;
	private JTextField publickey;
	public ViewHolder AllViews;
	
	public Settings(){
		Init();
	}
	
	public void Init(){
		 
		JPanel panel = new JPanel();
		JTextArea message = new JTextArea();
		JTextArea cipher = new JTextArea();
		JTextArea plaintext = new JTextArea();
		JLabel gamma = new JLabel();
		ImageIcon gammaicon = new ImageIcon(getClass().getResource("/img/GammaSignSmall.png"));
		JLabel logoutimg = new JLabel();
		ImageIcon logouticon = new ImageIcon(getClass().getResource("/img/Logout.png"));//make sure to fill this in once the graphic is there
		JLabel friendsimg = new JLabel();
		ImageIcon friendsicon = new ImageIcon(getClass().getResource("/img/hulkmonstersmall.png"));
		JLabel settingsimg = new JLabel();
		settingsimg.setName("Settings");
		
		JTextField Rsakeypath = new JTextField("RSA Public Key Location");
		JTextField RsakeypathPriv = new JTextField("RSA Private Key Location");
		JTextField AESkeyPath = new JTextField("AES Key Location");
		RsakeypathPriv.setLayout(null);
		Rsakeypath.setLayout(null);
		AESkeyPath.setLayout(null);
		
		
		panel.setPreferredSize(new Dimension(400,647));
		JTabbedPane JTabbedPane = new JTabbedPane();
         
        JComponent panel1 = makeTextPanel("Panel #1");
        JTabbedPane.addTab("Tab 1", friendsicon, panel1,
                "Does nothing");
        JTabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
         
        JComponent panel2 = makeTextPanel("Panel #2");
        JTabbedPane.addTab("Tab 2", gammaicon, panel2,
                "Does twice as much nothing");
        panel2.setPreferredSize(new Dimension(410, 50));

		JTabbedPane.setMnemonicAt(0, KeyEvent.VK_2);
		JTabbedPane.setMnemonicAt(1, KeyEvent.VK_4);
		
		panel.setLayout(null);
		logoutimg.setLayout(null);
		friendsimg.setLayout(null);
		friendsimg.setName("FriendsList");
		gamma.setLayout(null);
		logoutimg.setLayout(null);
	
		float[] gammayellow = new float[3];
		float[] greengrey = new float[3];
		float[] purple = new float[3];
		float[] flamered = new float[3];
		float[] forestgreen = new float[3];
		Color.RGBtoHSB(69, 93, 59, greengrey);
		Color.RGBtoHSB(243, 83, 65, flamered);
		Color.RGBtoHSB(90, 72, 98, purple);
		Color.RGBtoHSB(37, 51, 36, forestgreen);
		Color.RGBtoHSB(255, 204, 0, gammayellow);
		
		panel.setBackground(Color.getHSBColor(greengrey[0], greengrey[1], greengrey[2]));
		
		gamma.setToolTipText("Click to Leave the Lab");
		
		logoutimg.setToolTipText("Logout");
		friendsimg.setToolTipText("Avengers List");
		settingsimg.setToolTipText("Back to the Lab");
		
		gamma.setName("gammaButton");

		gamma.setIcon(gammaicon);
		logoutimg.setIcon(logouticon);
		friendsimg.setIcon(friendsicon);
		
		gamma.setBounds(225,25,50,50);
		logoutimg.setBounds(25,45,75,27);
		friendsimg.setBounds(125,25,75,50);
		Rsakeypath.setBounds(25, 200, 200, 25);
		RsakeypathPriv.setBounds(25,285, 200,25);
		AESkeyPath.setBounds(25,250, 200, 25);
		
		
		JTabbedPane.setVisible(true);
		Rsakeypath.setVisible(true);
		AESkeyPath.setVisible(true);
		
		//add listeners
		try {
			Rsakeypath.setName("RSAPublicKey");
			RsakeypathPriv.setName("RSAPrivateKey");
			Rsakeypath.addMouseListener((MouseListener) (controller = (new SettingsSmasher(this, new GammaMachine()))));
			RsakeypathPriv.addMouseListener(controller);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setPrvkey(RsakeypathPriv);
		setPublickey(Rsakeypath);
		
		gamma.addMouseListener(controller);
		friendsimg.addMouseListener(controller);
		
		panel.add(JTabbedPane);
		panel.add(gamma);
		panel.add(logoutimg);
		panel.add(friendsimg);
		panel.add(Rsakeypath);
		panel.add(RsakeypathPriv);
		panel.add(AESkeyPath);
		
		//Add the tabbed pane to this panel.
        add(JTabbedPane);
         
        //The following line enables to use scrolling tabs.
        JTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		this.view = panel;
		
		this.setName("SettingsView");
		
	}
	
	public JPanel getView(){
		return view;
	}
	
	protected JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }

	public JTextField getPrvkey() {
		return prvkey;
	}

	public void setPrvkey(JTextField rsakeypathPriv) {
		this.prvkey = rsakeypathPriv;
	}

	public JTextField getPublickey() {
		return publickey;
	}

	public void setPublickey(JTextField publickey) {
		this.publickey = publickey;
	}

}
