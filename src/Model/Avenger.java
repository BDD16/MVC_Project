package Model;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.NoSuchPaddingException;
import javax.imageio.ImageIO;
import net.jxta.peergroup.PeerGroup;

public class Avenger {
	
	private String          Name;
	private Image           Avatar;
	private String          Address;
	public  PublicKey       PubKey;
        public PeerGroup        FriendCircle;
        private FriendGroup     secureGroup;
	
	public Avenger(String name, Image avatar, String address, PublicKey pubKey) throws NoSuchAlgorithmException, NoSuchPaddingException{
		Name = name;
		Avatar = avatar;
		Address = address;
		PubKey = pubKey;
	}
	
	@Override 
    public String toString(){
		return String.format("Avenger{" +'"' + "Name" +'"' + ':'+ '"'+"%s"+ '"'+" , " + 
                       '"' + "Address" + '"'+ ':' + '"'+"%s" + '"'+"," +  '"' + 
                             "PubKey" + '"' +':'+ '"'+"%s"+ '"'+"\n}", Name, Address, new String(Base64.getEncoder().encode(PubKey.getEncoded())));
	}
	
	public Avenger(String name, Image avatar, String address) throws NoSuchAlgorithmException, NoSuchPaddingException{
		Name = name;
		Avatar = avatar;
		Address = address;
	}
        
        public Avenger(String name, Image avatar, String address, PeerGroup pg){
            Name = name;
            Avatar = avatar;
            Address = address;
            FriendCircle = pg;
        }
        
        public Avenger(String name, Image avatar, String address, String peergroupName){
            Name = name;
            Avatar = avatar;
            Address = address;
            FriendGroup x = new FriendGroup();
            x.CreatePeerGroup(peergroupName, peergroupName + " FriendCircle");
            secureGroup = x;
            
        }
	
	public Avenger(String name) throws URISyntaxException{
		Name = name;
		try {
			Avatar =  ImageIO.read(new File(getClass().getResource("/img/hulkmonstersmall.png").toURI()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Address = "";
		KeyPairGenerator keyGen = null;
		try {
			keyGen = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        keyGen.initialize(4096);
        KeyPair keypair = keyGen.generateKeyPair();
        byte[] publicKey = keypair.getPublic().getEncoded();
        StringBuffer retString = new StringBuffer();
        for (int i = 0; i < publicKey.length; ++i) {
            retString.append(Integer.toHexString(0x0100 + (publicKey[i] & 0x00FF)).substring(1));
        }
        System.out.println(retString);
		PubKey = keypair.getPublic();
		String b64 = new String(Base64.getEncoder().encode(keypair.getPrivate().getEncoded()));
		String longstring = new String();
		for(int i = 0; i < b64.length(); i += 1){
			if (( (i % 66) == 0) && (i != 0)){
				longstring += "\n";
			}
			longstring += b64.charAt(i);
			
		}
		Path filepath = Paths.get(getClass().getResource("/Model/AvengerDataBase/Avengers/" + name + "Private" + ".PEM").toURI());
		//TODO: as this was mainly at first just an exercise for myself I'll encrypt the private key later
		String writethis = new String("-----BEGIN PRIVATE KEY-----\n" + longstring + "\n-----END PRIVATE KEY-----");
		try {
			Files.write(filepath, writethis.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
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
        
        public PeerGroup getPeerGroup(){
            return FriendCircle;
        }
	
	public void setAddress(String address){
		Address = address;
	}
	
	public void TurnPrivateKeyToPEM(PrivateKey prvkey){
		
	}
	
	public void TurnPublicKeyToPEM(PublicKey pubkey){
		
	}

}
