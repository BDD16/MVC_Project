package Model;

import java.awt.Image;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

import javax.crypto.NoSuchPaddingException;

public class Avenger {
	
	private String		 Name;
	private Image        Avatar;
	private String		 Address;
	public  PublicKey    PubKey;
	
	public Avenger(String name, Image avatar, String address, PublicKey pubKey) throws NoSuchAlgorithmException, NoSuchPaddingException{
		Name = name;
		Avatar = avatar;
		Address = address;
		PubKey = pubKey;
	}
	
	@Override 
    public String toString(){
		return String.format("Avenger[Name=%s, Address=%s, PubKey=%s]", Name, Address, PubKey.toString());
	}
	
	public Avenger(String name, Image avatar, String address) throws NoSuchAlgorithmException, NoSuchPaddingException{
		Name = name;
		Avatar = avatar;
		Address = address;
	}
	
	public String getName(){
		return Name;
	}
	
	public void setName(String name){
		Name = name;
	}
	
	public Image getAvatar(){
		return Avatar;
	}
	
	public void setAvatar(Image avatar){
		Avatar = avatar;
	}
	
	public String getAddress(){
		return Address;
	}
	
	public void setAddress(String address){
		Address = address;
	}

}
