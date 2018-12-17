/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.util.secure.AVProtocol;

/**
 *
 * @author ATX
 */
public class Delta {

	private Proof proof;
	private TTP ttp;
	
	public Delta (Proof proof, TTP ttp)
	{
		this.proof = proof;
		this.ttp = ttp;
	}
	
	public Proof getProof() {
		return proof;
	}
	
	public void setProof(Proof proof) {
		this.proof = proof;
	}
	
	public TTP getTtp() {
		return ttp;
	}
	
	public void setTtp(TTP ttp) {
		this.ttp = ttp;
	}
	
}
