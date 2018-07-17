package Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

//server chat view that will be stored locally on each machine, so each machine is it's own server.
public class DrBanner implements Runnable {
	BufferedReader in;
    PrintWriter out;
    JTextArea textField = new JTextArea(8,40);
    JTextArea messageArea = new JTextArea(8, 40);
    private static final int PORT = 9001;

	
public DrBanner(JTextArea textField, JTextArea messageArea){
	// Layout GUI
	this.textField = textField;
	this.textField.setEditable(false);
	this.messageArea = messageArea;
	this.messageArea.setEditable(true);

    // Add Listeners
	
  
}

public DrBanner() {
		// TODO Auto-generated constructor stub
	}

/**
 * Prompt for and return the address of the server.
 */
private String getServerAddress(JFrame view) {
    return JOptionPane.showInputDialog(
        view,
        "Enter IP Address of the Server:",
        "Welcome to the Chatter",
        JOptionPane.QUESTION_MESSAGE);
}

/**
 * Prompt for and return the desired screen name.
 */
private String getName(JFrame view) {
    return JOptionPane.showInputDialog(
        view,
        "Choose a screen name:",
        "Screen name selection",
        JOptionPane.PLAIN_MESSAGE);
}

/**
 * Connects to the server then enters the processing loop.
 */
public void run() {

    // Make connection and initialize streams
   // String serverAddress = getServerAddress(null);
    try {
		System.out.println("This is the server address: " + InetAddress.getLocalHost());
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    Socket socket = null;
	try {
		socket = new Socket(InetAddress.getLocalHost(), PORT);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    try {
		in = new BufferedReader(new InputStreamReader(
		    socket.getInputStream()));
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
    try {
		out = new PrintWriter(socket.getOutputStream(), true);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

    // Process all messages from server, according to the protocol.
    while (true) {
        String line = null;
		try {
			line = in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (line.startsWith("SUBMITNAME")) {
            out.println(getName(null));
        } else if (line.startsWith("NAMEACCEPTED")) {
            textField.setEditable(true);
        } else if (line.startsWith("MESSAGE")) {
            messageArea.append(line.substring(8) + "\n");
        }
    }
}

/**
 * Runs the client as an application with a closeable frame.
 */
public static void main(String[] args) throws Exception {
    DrBanner client = new DrBanner();
    client.run();
  }
}

