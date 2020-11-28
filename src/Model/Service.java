/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Model.Network.NetworkInterface;
import java.util.ArrayList;


import net.jxta.endpoint.Message;
import net.jxta.peer.PeerID;

/**
 *
 * @author ATX
 */
public abstract class Service<D> implements ServiceInterface {

	private ArrayList<ServiceListener<D>> listeners = new ArrayList<ServiceListener<D>>(); //listeners list
	protected Communication sender;
	
	@Override
	public void putMessage(Message m) {
		D res = handleMessage(m);
		if(res != null) notifyListeners(res);
	}
	/**
	 * Add a new listener for this service.
	 * @param l A listener.
	 */
	public void addListener(ServiceListener<D> l) {
		listeners.add(l);
	}
	
	/**
	 * Send the event to all listeners.
	 * @param m
	 */
	private void notifyListeners(D m) {
		for(ServiceListener<D> l : listeners) {
			l.messageEvent(m);
		}
	}
	
	/**
	 * Process the message
	 * @param m the message
	 * @return true if the message was correctly processed
	 */
	public abstract D handleMessage(Message m);
	
	@Override
	public void setCommunication(Communication c) {
		this.sender = c;
	}
	
	public NetworkInterface getNetwork() {
		return sender.getNetwork();
	}
	
	public abstract void sendMessage(D data, PeerID ...ids);

}
