/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.util;

import java.beans.Beans;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 *
 * @author ATX
 */
public class Langues {
	private Langues() {
		// do not instantiate
	}
	
	private static final String BUNDLE_NAME = "util.messages";
	private static ResourceBundle RESOURCE_BUNDLE = loadBundle();
	
	public static ResourceBundle loadBundle() {
		return ResourceBundle.getBundle(BUNDLE_NAME);
	}
	
	/**
	 * Retrieve a string according to the key and the current Locale.
	 * @param key the string's key
	 * @return the according string in the current user language.
	 */
	public static String getString(String key) {
		try {
			ResourceBundle bundle = Beans.isDesignTime() ? loadBundle() : RESOURCE_BUNDLE;
			return bundle.getString(key);
		} catch (MissingResourceException e) {
			return "!" + key + "!";
		}
	}
	
	/**
	 * Call if the current langage may change.
	 */
	public static void updateLanguage() {
		RESOURCE_BUNDLE = loadBundle();
	}
}
