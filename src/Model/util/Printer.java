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
public  class Printer {

	public static void printInfo(Object o, String method, String info){
		System.out.println("INFO : "+ o.getClass().getName()+ "."+method+" : "+info);
	}
	public static boolean printError(Object o, String method, String error){
		System.err.println("ERROR : "+ o.getClass().getName()+"."+method+" : "+error);
		return false;
	}
	
}
