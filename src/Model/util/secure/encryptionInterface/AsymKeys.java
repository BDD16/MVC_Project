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

import java.math.BigInteger;

public interface AsymKeys<K> {
	
	/**
	 * Generate the private and public key according to the implemented asymmetric algorithm
	 * @param password The password to encrypt private key.
	 * @return True if successful generation
	 */
	public boolean generate(String password);
	
	
	
	/**
	 * Get the publicKey
	 * @return
	 */
	public K getPublicKey();
	
	
	/**
	 * Get the privateKey
	 * @return
	 */
	public K getPrivateKey();
	
	/**
	 * Get G of the publicKey
	 * @return
	 */
	public BigInteger getG();
	
	/**
	 * Get P of the publicKey
	 * @return
	 */
	public BigInteger getP();
}
