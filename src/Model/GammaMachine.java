package Model;
import java.security.*;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class GammaMachine extends AbstractModel {
	
	Cipher cipher;
	PrivateKey prvkey;
	private PublicKey pubkey;
	
	@SuppressWarnings("static-access")
	public GammaMachine() throws NoSuchAlgorithmException, NoSuchPaddingException{
		this.cipher = this.cipher.getInstance("RSA");
	}
	
	public byte[] RSAEncrypt(byte[] plaintext, Key key) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		byte[] result = null;
		this.cipher.init(this.cipher.ENCRYPT_MODE, key);
		result = cipher.doFinal(plaintext);
		
		return result;
	}
	
	public byte[] RSADecrypt(byte[] ciphertext, Key key) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException{
		byte[] result = null;
		cipher.init(cipher.DECRYPT_MODE, key);
		result = cipher.doFinal(ciphertext);
		
		return result;
	}
	
	public void generateKeys() throws NoSuchAlgorithmException{
	   KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
	   kpg.initialize(4096);
	   KeyPair kp = kpg.generateKeyPair();
	   
	   this.setPubkey(kp.getPublic());
	   this.prvkey = kp.getPrivate();
	    
	}
	
	public static byte[] SetCiphertoAES(String key, String initVector, String value ) throws NoSuchAlgorithmException, NoSuchPaddingException{
		try{
		IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
        SecretKeySpec skeySpec = new SecretKeySpec( key.getBytes("UTF-8"), "AES");
		
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

		byte[] encrypted = cipher.doFinal(value.getBytes());
        
        return encrypted;
    } catch (Exception ex) {
        ex.printStackTrace();
    }
		return null;//if it returns null then an error has occured
		
	}

	public PublicKey getPubkey() {
		return pubkey;
	}

	public void setPubkey(PublicKey pubkey) {
		this.pubkey = pubkey;
	}
	
	

}
