/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Network.search;

/**
 *
 * @author ATX
 */
public interface SearchListener <T> {
	public void searchEvent(T event);
}