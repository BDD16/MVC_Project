/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author ATX
 * Packet blob is a AES-256 or better GCM encrypted pack of data
 */
public class PacketBlob {
    //Turns the plaintext userdata into ciphertext while also providing a signature
    //using AES-GCM encryption (left out IV for now but will need to implement that)
    byte[] data;//tag is already included in data and according to java documentation
                //the tag is used for authentication of decryption within the cipher
    byte[] IV;
    byte[] signature;
    int tagSize = 256;
    
    public PacketBlob(){
        
    }
    
    public PacketBlob(byte[] userdata, Key aesKey) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
        
        GammaMachine cipher = new GammaMachine();
        byte[] iv = new byte[12]; //NEVER REUSE THIS IV WITH SAME KEY
        SecureRandom secureRandom = new SecureRandom();
                secureRandom.nextBytes(aesKey.getEncoded());
                SecretKey secretKey = null;
                
                secretKey = new SecretKeySpec(aesKey.getEncoded(),"AES/GCM/NoPadding");
                secureRandom.nextBytes(iv);
                GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);
        data = cipher.AESEncrypt(userdata, aesKey, parameterSpec);//turning plaintextdata into ciphertext
        IV = parameterSpec.getIV();
        signature = Arrays.copyOfRange(data, data.length - (tagSize / Byte.SIZE), data.length);
        
        
    }
    
}
