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
public interface AsymEncryption<D, K> {
	
	
	/**
	 * Set the public/private key if known, and the right P & G if needed
	 * @param keys
	 */
	public void setAsymsKeys(AsymKeys<K> keys);
	
	/**
	 * Encrypt the data with a public key.
	 * @param data the data to encrypt
	 * @param publicKey the public key
	 * @return the data encrypted
	 */
	public D encryptWithPublicKey(D data);
	
	/**
	 * Decrypt the data with the private key
	 * @param data the data encrypted
	 * @param privateKey the private key
	 * @return the data decrypted
	 */
	public D decryptWithPrivateKey(D data);
}
