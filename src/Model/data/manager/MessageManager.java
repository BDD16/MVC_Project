/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.data.manager;

/**
 *
 * @author ATX
 */
import java.util.ArrayList;
import java.util.HashMap;

import org.jdom2.Element;

import Model.util.Printer;
import Model.util.StringToElement;
import Model.data.user.Conversations;
import Model.data.user.User;
import Model.data.user.UserMessage;

public class MessageManager {
	private ArrayList<UserMessage> messages = new ArrayList<UserMessage>();	// Messages for users attempting to be received.
	private HashMap<String, Conversations> conversations = new HashMap<String, Conversations>(); //users's conversation (already received.) (string : user public key that own the conversations
	private Manager manager;
	
	
	///////////////////////////////////////////////// CONSTRUCTORS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public MessageManager(Manager m) {
		manager = m;
	}
	
	
	///////////////////////////////////////////////// GETTERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public ArrayList<UserMessage> getMessages() {
		return messages;
	}
	
	public HashMap<String, Conversations> getConversations() {
		return conversations;
	}
	
	public Conversations getConversation(String key) {
		return conversations.get(key);
	}
	
	
	/**
	 * Get the user conversations. If the conversations doesn't exist, it will be created.
	 * @param publicKey
	 * @return
	 */
	public Conversations getUserConversations(String publicKey){
		/*if(!conversations.containsKey(publicKey))
			addConversations(new Conversations(publicKey));*/
		return conversations.get(publicKey);
	}
	
	
	/**
	 * Get the current user conversations. If the conversations doesn't exist, it will be created.
	 * @return a Conversations
	 */
	public Conversations getCurrentUserConversations() {
		if(manager.getUserManager().getCurrentUser() == null) {
			System.err.println("no user logged");
			return null;
		}
		User u = manager.getUserManager().getCurrentUser();
		if(!conversations.containsKey(u.getKeys().getPublicKey().toString(16))) {
			addConversations(new Conversations(u));
		}
		return conversations.get(u.getKeys().getPublicKey().toString(16));
	}
	
	///////////////////////////////////////////////// ADDERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * Add an existing conversation to this manager.
	 * @param c
	 */
	public void addConversations(Conversations c) {
		if(c == null){
			Printer.printError(this, "addConversations","This Conversation is null !");
			return;
		}
		String owner = c.getOwner();
		if(owner == null || owner.isEmpty()){
			Printer.printError(this, "addConversations","No owner found !");
			return;
		}
		if(manager.getUserManager().getUser(owner) == null){
			Printer.printError(this, "addConversations","Owner unknown "+owner);
			return;
		}
		if(!c.checkSignature(c.getKeys())){
			Printer.printError(this, "addConversations","Bad Signature for Conversation");
			return;
		}
		conversations.put(c.getOwner(), c);
	}
	
	public void addMessage(UserMessage msg) {
		this.messages.add(msg);
		msg.publish(manager.getNetwork());
	}
	
	///////////////////////////////////////////////// REMOVERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * Remove a message from the Manager
	 * @param msg
	 * @return
	 */
	public boolean removeMessage(UserMessage msg){
		return messages.remove(msg);
	}
	/**
	 * Remove a message from the Manager with this id
	 * @param id
	 * @return
	 */
	public boolean removeMessage(String id) {
		for(UserMessage m : messages) {
			if(m.getId().equals(id))
				return removeMessage(m);
		}
		return false;
	}
	public boolean removeCurrentUserMessages(String id) {
		//String sender = 
		//conversations.get(manager.getUserManager().getCurrentUser().getKeys().getPublicKey().toString(16));
		return true;
	}
	public boolean removeCurrentUserConversation(String publicKey) {
		return conversations.get(manager.getUserManager().getCurrentUser().getKeys().getPublicKey().toString(16)).removeConversation(publicKey);
	}
	///////////////////////////////////////////////// XML \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * Load all the messages in this element
	 * @param e an element that contains messages in XML format.
	 */
	protected void loadReceivedMessages(Element e) {
		Element root = StringToElement.getElementFromString(e.getValue(), e.getName());
		for(Element m: root.getChildren()) {
			addConversations(new Conversations(m));
		}
	}
	
	/**
	 * Get an XML string representing all the messages that are saved on this device.
	 * @return A string, XML formated
	 */
	protected String getMessagesXML() {
		StringBuffer s = new StringBuffer();
		for(UserMessage m: messages) {
			s.append(m); 
		}
		return s.toString();
	}
	
	protected String getReceivedMessagesXML() {
		StringBuffer s = new StringBuffer();
		for(Conversations c : conversations.values()) {
			s.append(c);
		}
		return s.toString();
	}
	

	
}
