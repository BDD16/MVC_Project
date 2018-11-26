/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author ATX
 */
public class SecureEnvelope {
    byte[] cipherKey;
    PacketBlob msg;
    
    SecureEnvelope(byte[] plaintext, Key AesGcmKey, PublicKey RSAPubKey){
        try {
            msg = new PacketBlob(plaintext, AesGcmKey);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(SecureEnvelope.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(SecureEnvelope.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(SecureEnvelope.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(SecureEnvelope.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(SecureEnvelope.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            GammaMachine NukeHardenTheKey = new GammaMachine();
            NukeHardenTheKey.RSAEncrypt(AesGcmKey.getEncoded(), (Key) RSAPubKey);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(SecureEnvelope.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(SecureEnvelope.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(SecureEnvelope.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(SecureEnvelope.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(SecureEnvelope.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
