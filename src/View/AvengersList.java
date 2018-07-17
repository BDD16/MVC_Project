package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Controller.SettingsSmasher;
import Model.GammaMachine;
import Model.ViewHolder;

public class AvengersList extends JFrame{
	
	JPanel view;
	public SettingsSmasher controller;
	
	public ViewHolder AllViews;
	public AvengersList(){
		Init();
	}
	
	public void Init(){
		JPanel panel = new JPanel();
		
		panel.setLayout(null);
	
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
		
		//Need for the layout to be list view imageicon next to a username, need a way to pass
		//pass this info as well.  assuming this will be in the model
		JLabel gamma = new JLabel();
		ImageIcon gammaicon = new ImageIcon("src/img/GammaSignSmall.png");
		JLabel logoutimg = new JLabel();
		ImageIcon logouticon = new ImageIcon("src/img/Logout.png");//make sure to fill this in once the graphic is there
		
	    gamma.setLayout(null);
	    gamma.setIcon(gammaicon);
	    
	    logoutimg.setLayout(null);
	    logoutimg.setIcon(logouticon);
	    
	    gamma.setBounds(225,25,50,50);
		logoutimg.setBounds(25,45,75,27);
		
		gamma.addMouseListener(controller);
		logoutimg.addMouseListener(controller);
	    
	    panel.add(gamma);
	    panel.add(logoutimg);
	    
	   
		
		this.view = panel;
		
		this.setName("AvengersList");
		
		
	}
	
	public JPanel getView(){
		return view;
	}

}
