/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.util;

/**
 *
 * @author ATX
 */
public class Hexa {
	/**
	 * To convert byte array to Hex String 
	 * @param txtInByte
	 * @return text in hex string
	 */
	public static String bytesToHex(byte[] txtInByte) {
		   StringBuilder sb = new StringBuilder(txtInByte.length * 2);
		   for(byte b: txtInByte)
		      sb.append(String.format("%02x", b & 0xff));
		   return sb.toString();
		}
    
	/**
	 * To convert byte array to Hex String with capital
	 * @param txtInByte
	 * @return text in hex string
	 */
    public static String bytesToHex_UpperCase(byte[] txtInByte) {
		return bytesToHex(txtInByte).toUpperCase();
    }
    
    /**
     * To convert a Hex String to Readable String
     * @param txtInHex
     * @return
     */
    public static String hexToString(String txtInHex) {
    	int groupLength = 2;
        StringBuilder sb = new StringBuilder(txtInHex.length() / groupLength);
 
        for (int i = 0; i < txtInHex.length() - groupLength + 1; i += groupLength) {
            String hex = txtInHex.substring(i, i + groupLength);
            sb.append((char) Integer.parseInt(hex, 16));
        }
 
        return sb.toString();
    }
    
    /**
     * To convert a Readable String to Hex String
     * @param input
     * @return
     */
    public static String stringToHex(String input)
    {
        if (input == null) throw new NullPointerException();
        	return bytesToHex(input.getBytes());
    }
    
    /**
     * To convert a byte Array to Readable String
     * @param txtInByte
     * @return
     */
    public static String bytesToString(byte[] txtInByte){
    	return hexToString(bytesToHex(txtInByte));
    }
    
    /**
     * To convert a Hex String to byte Array
     * @param txtInHex
     * @return
     */
    public static byte[] hexToBytes(String txtInHex) {
        byte[] txtInBytes = new byte[txtInHex.length() / 2];
        for (int i = 0; i < txtInBytes.length; i++) {
          int index = i * 2;
          int v = Integer.parseInt(txtInHex.substring(index, index + 2), 16);
          txtInBytes[i] = (byte) v;
        }
        return txtInBytes;
      }
}
