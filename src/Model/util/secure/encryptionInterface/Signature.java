/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.util.secure.encryptionInterface;

/**
 *
 * @author ATX
 */
public interface Signature<M, S> {
	public S getMessageSignature(M message);
	public boolean verifySignature(M message, S signature);
}