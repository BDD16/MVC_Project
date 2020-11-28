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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.ImageIcon;

import java.util.Base64;

@SuppressWarnings("deprecation")
public class ImageBase64 {
	public static String encode(String path) {
		File file = new File(path);
		byte[] imageData = new byte[(int) file.length()];
		FileInputStream imageInFile;
		try {
			imageInFile = new FileInputStream(file);
			imageInFile.read(imageData);
			imageInFile.close();
		} catch (IOException e) {	
			e.printStackTrace();
		}
		
		return Base64.getEncoder().encodeToString(imageData);
	}
	
	
	public static ImageIcon decode(String encoded) {
            return new ImageIcon(Base64.getDecoder().decode(encoded));
	}
}
