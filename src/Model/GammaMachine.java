package Model;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class GammaMachine extends AbstractModel {
	
	Cipher cipher;
        Cipher AEScipher;
	private PrivateKey prvkey;
	private PublicKey pubkey;
        
       
	
	@SuppressWarnings("static-access")
	public GammaMachine() throws NoSuchAlgorithmException, NoSuchPaddingException{
		this.cipher = this.cipher.getInstance("RSA");
                AEScipher = AEScipher.getInstance("AES/GCM/NoPadding");
	}
        
        public PrivateKey getPrivateKey(){
            return prvkey;
        }
        
        public byte[] AESEncrypt(byte[] plaintext, Key keyone, GCMParameterSpec spec) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
            try {
                byte[] result = null;
                SecureRandom secureRandom = new SecureRandom();
                secureRandom.nextBytes(keyone.getEncoded());
                SecretKey secretKey = null;
                
                secretKey = new SecretKeySpec(keyone.getEncoded(),"AES/GCM/NoPadding");
                //byte[] iv = new byte[12]; //NEVER REUSE THIS IV WITH SAME KEY
                //secureRandom.nextBytes(iv);
                //GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);
                AEScipher.init(AEScipher.ENCRYPT_MODE, keyone, spec);
                result = AEScipher.doFinal(plaintext);
                result = Base64.getEncoder().encode(result);
                
                return result;
            } catch (InvalidAlgorithmParameterException ex) {
                Logger.getLogger(GammaMachine.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
        
        public byte[] AESDecrypt(byte[] ciphertext, Key key, GCMParameterSpec spec) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException{
            byte[] result = null;
            SecretKeySpec keyspec = new SecretKeySpec(key.getEncoded(), "AES");
		AEScipher.init(AEScipher.DECRYPT_MODE, keyspec, spec);
		byte[] ciphertextb64 = Base64.getDecoder().decode(ciphertext);
		result = AEScipher.doFinal(ciphertextb64);
		return result;
        }
	
	public byte[] RSAEncrypt(byte[] plaintext, Key key) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		byte[] result = null;
		this.cipher.init(this.cipher.ENCRYPT_MODE, key);
		result = cipher.doFinal(plaintext);
		result = Base64.getEncoder().encode(result);
		
		return result;
	}
	
	public byte[] RSADecrypt(byte[] ciphertext, PrivateKey key) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException{
		byte[] result = null;
                byte[] ciphertextb64 = null;
		this.cipher.init(cipher.DECRYPT_MODE, key);
                try{
		 ciphertextb64 = Base64.getDecoder().decode((new String(ciphertext)));
                }catch(Exception e){
                    ciphertextb64 = ciphertext;
                } 
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
