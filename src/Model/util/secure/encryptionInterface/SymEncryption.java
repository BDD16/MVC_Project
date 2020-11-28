/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.util.secure.encryptionInterface;

/**
 *
 * @author ATX
 */
public interface SymEncryption<D, K> {
	public void setSecretKey(K key);
	public D encrypt(D data);
	public D decrypt(D data);
}
