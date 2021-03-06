/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Network;

import net.jxta.peergroup.PeerGroup;

/**
 *
 * @author ATX
 */
public interface NetworkInterface {
	
	
	/**
	 * Get the mentioned group
	 * @param group The group name
	 * @return a PeerGroup registered with the name group, or null if it doesn't exist.
	 */	
	public PeerGroup getGroup(String group);
	
	/**
	 * Get the default group of the network.
	 * @return the default PeerGroup.
	 */
	public PeerGroup getDefaultGroup();
	
	/**
	 * Add a new PeerGroup on the network.
	 * @param name The new PeerGroup's name
	 */
	public void addGroup(String name);
	
	/**
	 * Add a new PeerGroup on the network
	 * @param name The new PeerGroup's name
	 * @param idGroup add a 2nd PeerGroup, "id-name".
	 */
	public void addGroup(String name, boolean idGroup);
	
	/**
	 * Starts the network
	 */
	public void start();
	
	
	/**
	 * Stop the network
	 */
	public void stop();
	
	
	/**
	 * Should return true only if the network is correctly started.
	 * @return true if the network is running, otherwise false.
	 */
	public boolean isStarted();
	
	/**
	 * Get a valid bootrapable Ip adress
	 * @return a public Ip adresse
	 */
	public String getBootStrapIp();
	
	/**
	 * Boot on an existing network.
	 * @param adress
	 */
	public void boot(String adress);
}