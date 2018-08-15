package View;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.*;

import Controller.ComplexityController;

public class Mainwindow extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5203647673128224782L;
	private JFrame Window;
	private JPasswordField password;
	public  JProgressBar   meter;
	public  JLabel		   meterwords;
	
	
	
	//put the main view panel
	JPanel view;
	
	public Mainwindow(){
		super();
		this.Init();
	}
	
	public void Init(){
		
		password = new JPasswordField("PasswordField");
		meter = new JProgressBar();
		meterwords = new JLabel();
		JPanel panel = new JPanel();
		
		password.setLayout(null);
		meter.setLayout(null);
		meter.setName("ProgressBar");
		meterwords.setLayout(null);
		panel.setLayout(null);
		
		meterwords.setBounds(130,60, 150,20);
		meter.setBounds(130,40,150,20);
		password.setBounds(130,20,150,20);
		
		panel.setPreferredSize((new Dimension(400,100)));
		password.setPreferredSize((new Dimension(150,20)));
		
		password.addActionListener((ActionListener) new ComplexityController(this));
		
		
		panel.add(password);
		panel.add(meter);
		panel.add(meterwords);
		view = panel;
		
		
		this.setName("LoginWindow");
		
		
	}
	
	public static void main(String[] args){
		WindowContainer test = new WindowContainer();
		Mainwindow      login = new Mainwindow();
		test.setWindow(login.view);
		login.setResizable(false);
		test.setResizable(false);
	}
	
	

	public JFrame getWindow() {
		return Window;
	}

	public void setWindow(JFrame window) {
		Window = window;
	}
	
	public Component getView(){
		return view;
	}
	
	public void setView(Component component){
		view = (JPanel) component;
	}

	public void setPassword(JPasswordField password2) {
		password = password2;
		
	}
	
	public String getPassword(){
		return new String(password.getPassword());
	}

}
