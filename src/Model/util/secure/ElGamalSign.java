/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.util.secure;

/**
 *
 * @author ATX
 */


import java.math.BigInteger;
import org.jdom2.Element;
import Model.util.StringToElement;


public class ElGamalSign {
	private BigInteger r;
	private BigInteger s;
	
	/**
	 * Initialize the ElGamal signature with the right R & S
	 * @param r
	 * @param s
	 */
	public ElGamalSign(BigInteger r, BigInteger s) {
		this.r = r;
		this.s = s;
	}
	
	public ElGamalSign(String xml) {
		Element root = StringToElement.getElementFromString(xml, "sign");
		r = new BigInteger(root.getChild("signR").getValue(), 16);
		s = new BigInteger(root.getChild("signS").getValue(), 16);
	}
	
	public BigInteger getR() {
		return r;
	}
	
	public BigInteger getS() {
		return s;
	}
	
	public String toString() {
		return "<signR>" + r.toString(16) + "</signR>" +
				"<signS>" + s.toString(16) + "</signS>";
	}
}