package View;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;
import javax.swing.table.TableCellRenderer;

import Controller.HulkController;
import Model.HULKCellRenderer;
import Model.DrBanner;
import Model.GammaEngine;
import Model.GammaMachine;
import Model.ViewHolder;
//turn into MVC design pattern will need to look up more info on this
public class MainChat extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9109219592927177283L;
	private JPanel view;
	JTextArea messagearea;
	private JTextArea cipherarea;
//	JScrollPane cipherarea1;
	private JTextArea plaintextarea;
	public JLabel gammaLabel;
	public HulkController controller;
	public JProgressBar progressBar;
	public DrBanner Client;
	
	private static final int TXT_AREA_ROWS = 25;
    private static final int TXT_AREA_COLS = 80;
	
	public ViewHolder AllViews;
	
	public MainChat(){
		this.Init();
	}
	
	public MainChat(ViewHolder allViews2) {
		AllViews = allViews2;
		this.Init();
	}

	public void Init(){
		JPanel panel = new JPanel();
		JTextArea message = new JTextArea();
		JTextArea cipher = new JTextArea();
		JTextArea plaintext = new JTextArea();
		JLabel gamma = new JLabel();
		ImageIcon gammaicon = new ImageIcon(getClass().getResource("/img/GammaSignSmall.png"));
		gamma.setName("GammaRaySign");
		JLabel logoutimg = new JLabel();
		ImageIcon logouticon = new ImageIcon(getClass().getResource("/img/Logout.png"));//make sure to fill this in once the graphic is there
		JLabel friendsimg = new JLabel();
		ImageIcon friendsicon = new ImageIcon(getClass().getResource("/img/FriendsSmall.png"));
		JLabel settingsimg = new JLabel();
		ImageIcon settingsicon = new ImageIcon(getClass().getResource("/img/SettingsSmall.png"));
		JLabel sendimg = new JLabel();
		ImageIcon sendicon = new ImageIcon(getClass().getResource("/img/send_icon_small.png"));
		progressBar = new JProgressBar();
		progressBar.setVisible(false);
		progressBar.setLayout(null);
		progressBar.setIndeterminate(true);
        progressBar.setBounds(310,575,50,45);
		
		settingsimg.setName("Settings");
		
		//TableCellRenderer cellpattern = new CellRenderer();
		
		panel.setLayout(null);
		message.setLayout(null);
		cipher.setLayout(null);
		plaintext.setLayout(null);
		logoutimg.setLayout(null);
		logoutimg.setName("LogoutButton");
		sendimg.setLayout(null);
		sendimg.setName("SendButton");
		sendimg.setIcon(sendicon);
		friendsimg.setLayout(null);
		gamma.setLayout(null);
		logoutimg.setLayout(null);
		settingsimg.setLayout(null);
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
		message.setBounds(100,100,200,350);
		cipher.setBounds(100,475,200,25);
		cipher.setForeground(Color.getHSBColor(gammayellow[0], gammayellow[1], gammayellow[2]));
		cipher.setBackground(Color.BLACK);
		cipher.setEditable(false);
		cipher.setToolTipText("Cipher Text");
		cipher.setLineWrap(true);
		plaintext.setBounds(100,510,200, 75);
		message.setBorder(BorderFactory.createLineBorder(Color.getHSBColor(purple[0], purple[1], purple[2]), 12));
		plaintext.setBorder(BorderFactory.createLineBorder(Color.getHSBColor(flamered[0], flamered[1], flamered[2]), 5));
		plaintext.setLineWrap(true);
		plaintext.setToolTipText("Enter a Message");
		
		gamma.setToolTipText("Click to Encrypt Message");
		try {
			gamma.addMouseListener((MouseListener) (controller = new HulkController(this, (new GammaMachine()))));
			settingsimg.addMouseListener(controller);
			logoutimg.addMouseListener(controller);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logoutimg.setToolTipText("Logout");
		friendsimg.setToolTipText("Avengers List");
		settingsimg.setToolTipText("Back to the Lab");
		sendimg.setToolTipText("Click to Send the Message");
		//gamma.addMouseListener(this);
		JScrollPane gammapane = new JScrollPane(cipher);
		JScrollPane Plaintextpane = new JScrollPane(panel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		Plaintextpane.add(plaintext);
		JTable test = new JTable(TXT_AREA_ROWS,1);
		test.setDefaultRenderer(Object.class, new HULKCellRenderer());
		test.setBounds(100,100,200,350);
		JScrollPane testpane = new JScrollPane(test,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		Plaintextpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		gammapane.setForeground(Color.getHSBColor(gammayellow[0], gammayellow[1], gammayellow[2]));
		gammapane.setToolTipText("cipher text area");
		gammapane.setBackground(Color.black);
		gammapane.setBounds(100,475,200,25); 
		gamma.setIcon(gammaicon);
		logoutimg.setIcon(logouticon);
		friendsimg.setIcon(friendsicon);
		settingsimg.setIcon(settingsicon);
		gamma.setBounds(310,525,50,50);
		sendimg.setBounds(310,475, 50,29);
		logoutimg.setBounds(25,25,75,27);
		friendsimg.setBounds(125,25,75,27);
		settingsimg.setBounds(225,25,75,27);
		
		testpane.setPreferredSize(new Dimension(200,350));
		testpane.setBounds(100,100,200,350);
		panel.setPreferredSize(new Dimension(400,647));
		
		panel.add(gamma);
		panel.add(sendimg);
		panel.add(logoutimg);
		panel.add(friendsimg);
		panel.add(settingsimg);
		//panel.add(message);
		//panel.add(cipher);
		panel.add(plaintext);
		//panel.add(test);
		panel.add(gammapane);
		panel.add(testpane);
		panel.add(progressBar);
		DrBanner x = new DrBanner(message, plaintext);
		
		this.gammaLabel = gamma;
		this.setView(panel);
		this.setCipherarea(cipher);
		this.setPlaintextarea(plaintext);
		
		this.setName("MainChatView");
	  
	}
	
	public void actionPerformed(ActionEvent e) {
		
	}
	
	void addHulkListener(ActionListener ListenForButtonClick){
		
	}

	public JTextArea getPlaintextarea() {
		return plaintextarea;
	}

	public void setPlaintextarea(JTextArea plaintextarea) {
		this.plaintextarea = plaintextarea;
	}

	public JTextArea getCipherarea() {
		return cipherarea;
	}

	public void setCipherarea(JTextArea cipherarea) {
		this.cipherarea = cipherarea;
	}

	public Component getView() {
		// TODO Auto-generated method stub
		return this.view;
	}

	public void setView(JPanel view) {
		this.view = view;
	}


}
