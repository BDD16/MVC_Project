/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

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
    byte[] signature;
    int tagSize = 256;
    
    public PacketBlob(byte[] userdata, Key aesKey) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
        GammaMachine cipher = new GammaMachine();
        data = cipher.AESEncrypt(data, aesKey);//turning plaintextdata into ciphertext
        signature = Arrays.copyOfRange(data, data.length - (tagSize / Byte.SIZE), data.length);
        
        
    }
    
}
