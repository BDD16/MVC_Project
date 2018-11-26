package View;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.*;

import Controller.AuthenticationController;
import Model.Authentication;
import Model.ViewHolder;

public class Mainwindow extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5203647673128224782L;
	Authentication Authentication;
	private JFrame Window;
	private JTextArea login;
	JPasswordField password;
	private String username;
	private char[] Password;
	private String passwordstring;
	JButton button;
	
	public ViewHolder AllViews;
	
	//put the main view panel
	JPanel view;
	
	public Mainwindow(){
		super();
		this.Init();
	}
	
	public void Init(){
	//	this.setWindow(new JFrame("Hulk Chat App"));
	//	this.getWindow().setSize(400,647);//done by the golden ratio
	//	this.getWindow().setOpacity((float) 1.0);
	//	this.getWindow().setLocationRelativeTo(null);
	//	this.getWindow().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setLogin(new JTextArea("Username"));
		this.getLogin().setLayout(null);
		this.getLogin().setBounds(130,100,150,20);
		this.password = new JPasswordField("PasswordField");
		this.password.setLayout(null);
		this.password.setBounds(130,140,150,20);
		this.Authentication = new Authentication();
		
		JPanel panel = new JPanel();
		this.button = new JButton("Login");
		JLabel logo = new JLabel();
		JLabel hulkmonster = new JLabel();
		JLabel loginkey = new JLabel();
		ImageIcon x = new ImageIcon(getClass().getResource("/img/hulkicon.png"));
		ImageIcon y = new ImageIcon(getClass().getResource("/img/hulkmonstersmall.png"));
		ImageIcon z = new ImageIcon(getClass().getResource("/img/keyiconsmall.png"));
		//setup Hulk Theme Colors
			//GREEN
		float[] green = new float[3];
			//purple
		float[] purple = new float[3];
		Color.RGBtoHSB(90, 72, 98, purple);
		Color.RGBtoHSB(43, 165, 78, green);
		panel.setBackground(Color.getHSBColor(purple[0],purple[1], purple[2]));//change color to a more HULK themed color
		panel.setLayout(null);
		button.setLayout(null);
		hulkmonster.setLayout(null);
		logo.setLayout(null);
		loginkey.setLayout(null);
		button.setBounds(140,190,125,25);
		//set up the button to have a picture
		hulkmonster.setIcon(y);
		hulkmonster.setBounds(80,80,50,50);
		logo.setIcon(x);
		logo.setBounds(130,300,100,100);
		loginkey.setIcon(z);
		loginkey.setBounds(80,130,50,50);
		loginkey.setToolTipText("so you want to talk with Mr. Blue?");
		hulkmonster.setToolTipText("RRRAARRR");
		getLogin().setToolTipText("Enter Username");
		password.setToolTipText("Enter Password");
		button.setToolTipText("Click to Login");
		button.setOpaque(true);
		button.setBackground(Color.getHSBColor(green[0], green[1], green[2]));
		
		//add in all the buttons, and text boxes
		panel.add(getLogin());
		panel.add(password);
		panel.add(button);
		panel.add(logo);
		panel.add(hulkmonster);
		panel.add(loginkey);
		this.view = panel;
		//add event listeners to button
		button.addMouseListener((MouseListener) new AuthenticationController(this));
		//this.getWindow().add(panel);
		//this.getWindow().setVisible(true);
		
		this.setName("LoginWindow");
		AllViews = new ViewHolder();
		AllViews.addToViewArray(this);
		
	}
	
	public static void main(String[] args){
		WindowContainer test = new WindowContainer();
		Mainwindow      login = new Mainwindow();
               // NewChatView testone = new NewChatView();
		test.setWindow(login.view);
		login.AllViews.addToViewArray(test);
		login.AllViews.addToViewArray(login);
               //login.AllViews.addToViewArray(testone);
	}
	
	public void addHulkListenner(ActionListener HulkListener){
		this.button.addActionListener(HulkListener);
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordstring() {
		return passwordstring;
	}

	public void setPasswordstring(String passwordstring) {
		this.passwordstring = passwordstring;
	}

	public JPasswordField getPassword() {
		return password;
	}

	public void setPassword(char[] password) {
		Password = password;
	}

	public JTextArea getLogin() {
		return login;
	}

	public void setLogin(JTextArea login) {
		this.login = login;
	}

	public void setPassword(JPasswordField password2) {
		// TODO Auto-generated method stub
		this.password = password2;
		
	}

}
