/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import net.jxta.endpoint.Message;

/**
 *
 * @author ATX
 */
public interface ServiceInterface {
	
	/**
	 * Return the service name
	 * @return A string representing the service
	 */
	public String getServiceName();
	
	/**
	 * Send a message to this service
	 * @param the message
	 */
	public void putMessage(Message m);
	
	/**
	 * Define the communication instance used to send messages.
	 * @param c
	 */
	public void setCommunication(Communication c);
}
