package Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

//server chat view that will be stored locally on each machine, so each machine is it's own server.
public class DrBanner {
	BufferedReader in;
    PrintWriter out;
    JTextArea textField = new JTextArea(8,40);
    JTextArea messageArea = new JTextArea(8, 40);

	
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
public void run() throws IOException {

    // Make connection and initialize streams
    String serverAddress = getServerAddress(null);
    Socket socket = new Socket(serverAddress, 9001);
    in = new BufferedReader(new InputStreamReader(
        socket.getInputStream()));
    out = new PrintWriter(socket.getOutputStream(), true);

    // Process all messages from server, according to the protocol.
    while (true) {
        String line = in.readLine();
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

