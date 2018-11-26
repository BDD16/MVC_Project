package Model;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JPasswordField;

public class Authentication extends AbstractModel {
	
	public Authentication(){
		
	}
	
	public static boolean VerifyPassword(String Username, JPasswordField password) throws NoSuchAlgorithmException{
		//trying to encapsulate the password in just the password box;
		
		if(DoesUsernameExist(Username)){
			//1)take the sha256 of the password
			byte[] compareThis = (new String(password.getPassword())).getBytes();
			compareThis = takeSha256(compareThis);
			String compareThisString = bytesToHex(compareThis);
			//2)compare with the sha256 of the stored pwd
			boolean result = DoesPasswordMatch(Username, compareThisString);
			if(Username.equals("Dr.Banner")){//For testing purposes leave to Dr.Banner else cahnge to result
				return true;
			}
			
			else return false;//username does not exist but don't let anyone know that
		
		}
		return false;
		
	}
	
	public static boolean DoesUsernameExist(String Username){
		//lookup from a database that the Username exists;
		//TODO: need to add in a relational database?
		
		return true;
	}
	
	public static boolean DoesPasswordMatch(String Username, String PasswordSha256 ){
		//TBD
		
		//1)Parse json file
		
		//2)look for username.json
		
		//if the username doesn't exist then it hasn't been established on this particular computer
		//so need to create the new username and password make sure to blacklist some characters to
		//avoid sql injection attacks or DOS attacks <>/\:.! in the database
		
		//
		return true;
	}
	
	public static File FetchUserNameList(String FileName){
		File  file = new File("FileName");
		
		return file;
	}
	
	public static byte[] takeSha256(byte[] bytearray) throws NoSuchAlgorithmException{
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] encodedhash = digest.digest(bytearray);
		
		return encodedhash;
	}
	
	private static String bytesToHex(byte[] hash) {
	    StringBuffer hexString = new StringBuffer();
	    for (int i = 0; i < hash.length; i++) {
	    String hex = Integer.toHexString(0xff & hash[i]);
	    if(hex.length() == 1) hexString.append('0');
	        hexString.append(hex);
	    }
	    return hexString.toString();
	}
	
	

}
