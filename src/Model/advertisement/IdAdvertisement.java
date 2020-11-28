/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.advertisement;

import org.jdom2.Element;
/**
 *
 * @author ATX
 */
public class IdAdvertisement extends AbstractAdvertisement{

	
	public IdAdvertisement(AbstractAdvertisement adv) {
		super();
		this.setId(adv.getId());
		this.setKeys(adv.getKeys());
	}
	
	@Override
	protected String getAdvertisementName() {
		return getClass().getSimpleName();
	}

	@Override
	protected void setKeys() {
	}

	@Override
	protected void putValues() {
	}

	@Override
	protected boolean handleElement(Element e) {
		return false;
	}

	@Override
	public String getSimpleName() {
		return null;
	}

}
