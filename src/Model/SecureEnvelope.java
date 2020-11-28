
package Model;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.DestroyFailedException;


/**
 *
 * @author ATX
 */
public class SecureEnvelope {
    byte[] cipherKey;
    PacketBlob msg;
    int RSAKeySize;
    int AESKeySize;
    byte[] message = null;
    byte[] IVmsg;

    SecureEnvelope() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public byte[] getcipherKey(){
        return cipherKey;
    }
    
    public byte[] getPacketBlob(){
        return msg.data;
    }
    
    public byte[] getMessage(){
        return message;
    }
    
    public String getSecureEnvelopeData(){
        String allData = Arrays.toString(cipherKey);
        allData = allData + Arrays.toString(IVmsg);
        allData = allData + Arrays.toString(msg.data);
        
        return allData;
    }
    public SecureEnvelope(String ciphermsg, PrivateKey privatekey) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException{
        //this assumes that we are receiving all the information lots of assumptions
        //are going to be made such as RSAPubKey size (4096 bits) AESGcmKey is 128Bits
        //actually will need to have two variables that let us know how long the keys are
        //1)Calculate each lengths of the keys i.e. first gain secret key
            //the first RSAKeySize/8 bytes will be the AES Key to decrypt AESGCM
            PacketBlob x = new PacketBlob();
            RSAKeySize = 4096;
            byte[] keyCipher;
            byte[] AesCipher;
            byte[] AESKey;
            String bytestring = ciphermsg;
            bytestring = bytestring.replaceAll("]", ",");
            String[] chars = bytestring.split(",");
            byte[] message = new byte[chars.length];
            chars[0] = chars[0].replace("[", "");
            for(int i = 0; i < chars.length; i += 1){
                chars[i] = chars[i].replaceAll(" ", ""); 
                chars[i] = chars[i].replace("[", "");
                
                message[i] = Byte.valueOf(chars[i]);
            } 
            String b64text = (new String(message));
            String checkthis = b64text;//new String(cipherplain);
            String keyCiphertext = checkthis.substring(0, checkthis.indexOf("=") + 1);
            String aesCiphertext = checkthis.substring(checkthis.indexOf("=") + 1,checkthis.length());
            String aesspec = aesCiphertext.substring(0,aesCiphertext.indexOf("=") + 1);
            aesCiphertext = aesCiphertext.substring(aesCiphertext.indexOf("=") + 1, aesCiphertext.length());
            GammaMachine decryptionSuite = new GammaMachine();
            AESKey = decryptionSuite.RSADecrypt(keyCiphertext.getBytes(), privatekey );
            byte[] iv = decryptionSuite.RSADecrypt(aesspec.getBytes(), privatekey);
            SecretKeySpec z = new SecretKeySpec(AESKey, "AES/GCM/NoPadding");
            GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);
                
        try {
            this.message = decryptionSuite.AESDecrypt(aesCiphertext.getBytes(),  z, parameterSpec);
        } catch (InvalidAlgorithmParameterException ex) {
            Logger.getLogger(SecureEnvelope.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        try {
            z.destroy();
            

        } catch (DestroyFailedException ex) {
            Logger.getLogger(SecureEnvelope.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public SecureEnvelope(byte[] plaintext, Key AesGcmKey, PublicKey RSAPubKey){
        try {
            AESKeySize = AesGcmKey.getEncoded().length * Byte.SIZE;
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
            RSAKeySize = RSAPubKey.getEncoded().length * Byte.SIZE;
           cipherKey = NukeHardenTheKey.RSAEncrypt(AesGcmKey.getEncoded(), RSAPubKey);
           IVmsg = NukeHardenTheKey.RSAEncrypt(msg.IV, RSAPubKey);
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
