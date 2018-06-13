package Model;
import javax.swing.JPasswordField;

public class Authentication extends AbstractModel {
	
	public Authentication(){
		
	}
	
	public static boolean VerifyPassword(String Username, JPasswordField password){
		//trying to encapsulate the password in just the password box;
		
		if(DoesUsernameExist(Username)){
			
			if(Username.equals("Dr.Banner")){
				return true;
			}
			
			else return false;//username does not exist but don't let anyone know that
		
		
		}
		return false;
		
		
		
		
	}
	
	public static boolean DoesUsernameExist(String Username){
		//lookup from a database that the Username exists;
		//TODO: need to add in a spring based database
		
		return true;
	}
	
	public static boolean DoesPasswordMatch(String Username, JPasswordField Password ){
		//TBD
		return true;
	}

}
