/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.util.secure;
import java.security.InvalidKeyException;

import Model.util.secure.encryptionInterface.SymEncryption;
import Serpent.Serpent_BitSlice;
/**
 *
 * @author ATX
 */
public class Serpent implements SymEncryption<byte[], String>{
	private Object secretKey = null;
	private static final int BLOCK_SIZE = 16;


	/**
	 * Create a new Serpent instance with the specified secret key.
	 * @param secretKey the secret key.
	 */
	public Serpent(String secretKey) {
		setSecretKey(secretKey);
	}

	/**
	 * Modify the plain text to get a multiple of 16 bits
	 * @param data
	 * @return
	 */
	private byte[] getValidsBlocks(byte[] data) {
		int lenght = ((data.length - 1)/BLOCK_SIZE + 1)*BLOCK_SIZE;
		byte[] res = new byte[lenght + 1];
		byte added = 0;
		for(int i = 0; i < lenght; i++) {
			if(i >= data.length) {
				res[i] = 0;
				added++;
			}
			else res[i] = data[i];
		}
		res[lenght] = added;
		return res;
	}


	/**
	 * Encrypt the datas
	 * @param plainText the original datas
	 * @return the data encrypted.
	 */
	public byte[] encrypt(byte[] plainText) {
		byte[] validPlainTest = getValidsBlocks(plainText);
		byte[] res =  new byte[validPlainTest.length];
		for(int i = 0; i < validPlainTest.length - 1; i+= BLOCK_SIZE) {
			byte[] cypher = Serpent_BitSlice.blockEncrypt(validPlainTest, i, secretKey);
			for(int j = 0; j < cypher.length; j++) {
				res[i + j] = cypher[j];
			}
		}
		res[validPlainTest.length - 1] = validPlainTest[validPlainTest.length - 1];
		return res;
	}
	

	/**
	 * Decrypt the encrypted datas
	 * @param cypher the encrypted datas
	 * @return the original data
	 */
	public byte[] decrypt(byte[] cypher) {
		byte added = cypher[cypher.length - 1];
		byte[] res = new byte[cypher.length - added - 1];
		for(int i = 0; i < cypher.length - 1; i+= BLOCK_SIZE) {
			byte[] plainText = Serpent_BitSlice.blockDecrypt(cypher, i, secretKey);
			for(int j = 0; j < plainText.length; j++) {
				if(i+j >= res.length) break;
				res[i + j] = plainText[j];
			}
		}
		
		return res;
	}


	public static void main(String[] args) {
		String plainText = "Lorem ipsum dolor sit amet ! Izy";
		//String plainText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis sit amet ex ex. Sed vel nibh non dolor dignissim pretium. Aliquam eu suscipit arcu, sed vestibulum lorem. Fusce scelerisque eleifend eleifend. Donec tempus quis felis sit amet vehicula. Aenean ut porttitor nibh, consectetur efficitur metus. Nam pulvinar aliquam laoreet. Ca marche ?";
		String password = "qosfqifquig";

		System.out.println("Original");
		System.out.println("============================");
		System.out.println(plainText);
		System.out.println("============================\n\n");

		Serpent s = new Serpent(password);
		byte[] cypher = s.encrypt(plainText.getBytes());

		System.out.println("Cypher");
		System.out.println("============================");
		System.out.println(new String(cypher));
		System.out.println("============================\n\n");
		byte[] decrypt = s.decrypt(cypher);

		System.out.println("Decrypted");
		System.out.println("============================");
		System.out.println(new String(decrypt));
		System.out.println("============================");
		
		if(plainText.equals(new String(decrypt))) {
			System.out.println("ok !");
		}
	}

	@Override
	public void setSecretKey(String key) {
		try {
			this.secretKey = Serpent_BitSlice.makeKey(key.getBytes());
		} catch (InvalidKeyException e) {
			System.err.println("---- invalid key ----");
			e.printStackTrace();
		}
	}

}
