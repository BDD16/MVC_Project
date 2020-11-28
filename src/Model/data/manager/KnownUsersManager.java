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

import java.util.HashMap;

import Model.data.favorites.KnownUsers;
import Model.data.user.User;
import Model.util.Printer;

public class KnownUsersManager {
	private HashMap<String, KnownUsers> knownUsers = new HashMap<>();
	private Manager m;
	
	public KnownUsersManager(Manager m) {
		this.m = m;
	}
	
	/**
	 * Get the current user's user favorites.
	 * @return
	 */
	public KnownUsers getCurrentUserKnownUsers() {
		if(m.getUserManager().getCurrentUser() == null) {
			Printer.printError(this, "getCurrentUserKnownUsers", "no users logged");
		}
		User currentUser = m.getUserManager().getCurrentUser();
		String publicKey = currentUser.getKeys().getPublicKey().toString(16);
		if(!knownUsers.containsKey(publicKey)) knownUsers.put(publicKey, new KnownUsers(currentUser));
		return knownUsers.get(publicKey);
	}
	
}
