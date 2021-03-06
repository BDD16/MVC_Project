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

public class ElGamalEncrypt {
		private BigInteger u;
		private BigInteger v;
		private BigInteger k; // only for SigmaProtocol 
		private byte [] m;
		/**
		 * Initialize the ElGamal signature with the right R & S
		 * @param r
		 * @param s
		 */
		public ElGamalEncrypt(BigInteger u, BigInteger v, BigInteger k, byte[] m) {
			this.u = u;
			this.v = v;
			this.k = k;
			this.m = m;
		}
		
		public BigInteger getU() {
			return u;
		}
		
		public BigInteger getV() {
			return v;
		}
		
		public BigInteger getK() {
			return k;
		}

		public byte [] getM() {
			return m;
		}

		public void setM(byte [] m) {
			this.m = m;
		}
		
}
