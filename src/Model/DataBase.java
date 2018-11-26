package Model;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DataBase {
	//Search for an avenger
	//1 folder Avengers
	//contains 1 encrypted json file with contents of an Avenger
	File AvengerRelationalDB;
	
	public DataBase(String nicName) throws URISyntaxException{
		//create a new json file if it doesn't already exist
		boolean check = new File(getClass().getResource("/Model/AvengerDataBase/Avengers/"+ nicName + ".json").toURI()).exists();
		//create
		if(check){
			AvengerRelationalDB = new File(getClass().getResource("/Model/AvengerDataBase/Avengers/" + nicName + ".json").toURI());
			System.out.println("Found the Batcave");
		}
		else{
			AvengerRelationalDB = new File(getClass().getResource("/Model/AvengerDataBase/Avengers/" + nicName + ".json").toURI());
			System.out.println("built the batcave");
			try {
				AvengerRelationalDB.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	

	public Avenger FindAnAvenger(String nicName){
		Avenger result = null;
		//1) open Json file
		
		//1) Decrypt Json File
		
		//3)Parse Json File looking for the avenger
		
		//4)Done reading the file
		
		//5)Re-encrypt the file using the nicNames PrivateKey
		
		//6) write the new encrypted file
		
		//7) close the file
		
		return result;
		
		
	}
	public boolean AddAnAvenger(String nicName, Avenger NewAvenger){
		Path filepath = Paths.get(AvengerRelationalDB.getAbsolutePath());
		try {
			Files.write(filepath, NewAvenger.toString().getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
	
	public boolean DeleteAnAvenger(String nicName, Avenger UnworthyAvenger){
		
		return false;
	}

}
