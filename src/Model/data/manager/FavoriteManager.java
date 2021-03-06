/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.data.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.jdom2.Element;

import Model.util.Printer;
import Model.util.StringToElement;
import Model.util.secure.AsymKeysImpl;

import Model.data.favorites.Favorites;
import Model.data.item.Item;
import Model.data.user.User;
/**
 *
 * @author ATX
 */
public class FavoriteManager {
	private HashMap<String, Favorites> favorites = new HashMap<String, Favorites>();
	private Manager manager;
	
	///////////////////////////////////////////////// CONSTRUCTORS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public FavoriteManager(Manager m) {
		manager = m;
	}
	
	
	///////////////////////////////////////////////// GETTERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * Get the current user's favorites. If doesn't exist, return null;
	 * @return Favorites
	 */
	public Favorites getUserFavorites(String publicKey){
		if(!favorites.containsKey(publicKey))
			return null;
		return favorites.get(publicKey);
	}
	
	/**
	 * Get the current user's favorites. If doesn't exist, it will return null.
	 * @return Favorites
	 */
	public Favorites getFavoritesCurrentUser(){
		User currentUser = manager.getUserManager().getCurrentUser();
		if(currentUser == null) {
			System.err.println("no user logged");
			return null;
		}
		String publicKey = currentUser.getKeys().getPublicKey().toString(16);
		if(!favorites.containsKey(publicKey))
			favorites.put(publicKey, new Favorites(currentUser));
		return getUserFavorites(publicKey);
	}
	
	
	///////////////////////////////////////////////// ADDERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * Add Favorites to the owner of the Favorites. If the user isn't in the manager, abort.
	 * @param f
	 */
	public void addFavorites(Favorites f){
		if(f == null){
			Printer.printError(this, "addFavorites","This Favorites is null !");
			return;
		}
		String owner = f.getOwner();
		if(owner.isEmpty()){
			Printer.printError(this, "addFavorites","No owner found !");
			return;
		}
		if(manager.getUserManager().getUser(owner) == null){
			Printer.printError(this, "addFavorites","Owner unknown "+owner);
			return;
		}
		if(owner.equals(manager.getUserManager().getCurrentUser()) && !f.checkSignature(manager.getUserManager().getCurrentUser().getKeys())){
			Printer.printError(this, "addFavorites","Bad Signature for Favorite (current User)");
			return;
		}else if(!f.checkSignature(manager.getUserManager().getUser(owner).getKeys())){
			Printer.printError(this, "addFavorites","Bad Signature for Favorite");
			return;
		}
		favorites.put(f.getOwner(), f);
		//f.publish(manager.getNetwork()); // TODO BUG !!!!!!!!!
	}
	
	/**
	 * Add Favorites to the owner of the Favorites. If the user isn't in the manager, abort.
	 * @param f
	 */
	public void addFavorites(Favorites f, boolean publish){
		if(f == null){
			Printer.printError(this, "addFavorites","This Favorites is null !");
			return;
		}
		String owner = f.getOwner();
		if(owner.isEmpty()){
			Printer.printError(this, "addFavorites","No owner found !");
			return;
		}
		if(manager.getUserManager().getUser(owner) == null){
			Printer.printError(this, "addFavorites","Owner unknown "+owner);
			return;
		}
		if(owner.equals(manager.getUserManager().getCurrentUser()) && !f.checkSignature(manager.getUserManager().getCurrentUser().getKeys())){
			Printer.printError(this, "addFavorites","Bad Signature for Favorite (current User)");
			return;
		}else if(!f.checkSignature(f.getKeys())){
			Printer.printError(this, "addFavorites","Bad Signature for Favorite");
			return;
		}
		favorites.put(f.getOwner(), f);
		if(publish)
			f.publish(manager.getNetwork()); // TODO BUG !!!!!!!!!
	}
	
	/**
	 * Add an item to current user's Favorites
	 * @param item
	 */
	public void addFavoritesItem(Item item){
		User currentUser = manager.getUserManager().getCurrentUser();
		if(currentUser == null){
			Printer.printError(this, "addFavoritesItem", "not user logged !");
			return;
		}
		AsymKeysImpl keys = new AsymKeysImpl(manager.getUserManager().getCurrentUser().getKeys().toString());
		keys.decryptPrivateKey(manager.getUserManager().getCurrentUser().getClearPwd());
		String publicKey = currentUser.getKeys().getPublicKey().toString(16);
		if(publicKey == null || publicKey.isEmpty()){
			Printer.printError(this, "addFavoritesItem", "Not user logged or PublicKey empty !");
			return;
		}
		if(!favorites.containsKey(publicKey)){
			Favorites f = new Favorites(manager.getUserManager().getCurrentUser());
			f.sign(keys);
			addFavorites(f);
		}
		if(item == null){
			Printer.printError(this, "addFavoritesItem","This Item is null !");
			return;
		}
		favorites.get(publicKey).addItem(item);
		favorites.get(publicKey).sign(keys);
	}
	
	
	///////////////////////////////////////////////// XML \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * Get an XML string representing all the favorites that are saved on this device.
	 * @return A string, XML formated
	 */
	protected String getFavoritesXML(){
		StringBuffer s = new StringBuffer();
		for(Favorites f : favorites.values()) {
			s.append(f);
		}
		return s.toString();
	}
	
	
	/**
	 * Load all the favorites in this element
	 * @param e an element that contains messages in XML format.
	 */
	protected void loadFavorites(Element e) {
		Element root = StringToElement.getElementFromString(e.getValue(), e.getName());
		for(Element f: root.getChildren()){
			addFavorites(new Favorites(f));
		}
	}


	public Collection<Favorites> getFavorites() {
		return favorites.values();
	}
	
	
}
