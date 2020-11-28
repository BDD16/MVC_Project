/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.util.secure.AVProtocol;

import java.math.BigInteger;

import Model.util.secure.AsymKeysImpl;
import Model.util.secure.ElGamal;
import Model.util.secure.ElGamalEncrypt;
/**
 *
 * @author ATX
 */
public class ParticipantEx {

	private AsymKeysImpl keys ;
	private BigInteger x;
	private int number ;
	private byte[] Mi;
	private byte [] miD;
	
	public ParticipantEx (AsymKeysImpl keys, int number, BigInteger x)
	{
		this.setKeys(keys);
		this.setNumber(number);
		this.setX(x);
	}

	public AsymKeysImpl getKeys() {
		return keys;
	}

	public void setKeys(AsymKeysImpl keys) {
		this.keys = keys;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public byte[] getMi() {
		return Mi;
	}

	public void setMi(byte[] mi) {
		Mi = mi;
	}

	public BigInteger getX() {
		return x;
	}

	public void setX(BigInteger x) {
		this.x = x;
	}
	
	public void decryptMi ()
	{
		ElGamal elGamal = new ElGamal (keys);
		setMiD(elGamal.decryptWithPrivateKey(Mi));
	}

	public byte [] getMiD() {
		return miD;
	}

	public void setMiD(byte [] miD) {
		this.miD = miD;
	}
}
