/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author ATX
 */

/**
 * Interface for some class that want's to know a service receive a message.
 * @param <D> The type of data returned
 * 
 */


public interface ServiceListener<D>{
	
	/**
	 * Will be call if a service that's listen receive a message, after processing.
	 * @param m The message
	 */
	public void messageEvent(D m);
}
