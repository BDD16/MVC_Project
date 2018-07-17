package Model;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class GammaMachine extends AbstractModel {
	
	Cipher cipher;
	private PrivateKey prvkey;
	private PublicKey pubkey;
	
	@SuppressWarnings("static-access")
	public GammaMachine() throws NoSuchAlgorithmException, NoSuchPaddingException{
		this.cipher = this.cipher.getInstance("RSA");
	}
	
	public byte[] RSAEncrypt(byte[] plaintext, Key key) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		byte[] result = null;
		this.cipher.init(this.cipher.ENCRYPT_MODE, key);
		result = cipher.doFinal(plaintext);
		result = Base64.getEncoder().encode(result);
		
		return result;
	}
	
	public byte[] RSADecrypt(byte[] ciphertext, Key key) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException{
		byte[] result = null;
		cipher.init(cipher.DECRYPT_MODE, key);
		byte[] ciphertextb64 = Base64.getDecoder().decode(ciphertext);
		result = cipher.doFinal(ciphertextb64);
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
	
	public PublicKey  PubKeyreadDerFile(String derFile) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException{
		PublicKey result = null;
		Path path = Paths.get(derFile);
		byte[] pubKeyByteArray = Files.readAllBytes(path);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pubKeyByteArray);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	    result = keyFactory.generatePublic(keySpec);
	
		return result;
		
	}
	
	public PrivateKey PrivKeyreadDerFile(String derFile) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException{
		PrivateKey result = null;
		Path path = Paths.get(derFile);
		byte[] pubKeyByteArray = Files.readAllBytes(path);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pubKeyByteArray);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	    result = keyFactory.generatePrivate(keySpec);
		
		return null;
	}
	
	

}
