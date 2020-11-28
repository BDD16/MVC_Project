/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.advertisement;

import Model.data.item.Item;
import Model.data.user.User;
import net.jxta.document.Advertisement;
import net.jxta.document.AdvertisementFactory.Instantiator;
import net.jxta.document.Element;

/**
 *
 * @author ATX
 */
public class AdvertisementInstaciator implements Instantiator{
	
	private Class<? extends Advertisement> advClass;
	private String advType;
	
	public AdvertisementInstaciator(AbstractAdvertisement advClass) {
		this.advClass = advClass.getClass();
		this.advType = advClass.getAdvType();
	}
	
	@Override
	public String getAdvertisementType() {
		return advType;
	}

	@Override
	public Advertisement newInstance() {
		try {
			return advClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Advertisement newInstance(Element root) {
		try {
			return advClass.getConstructor(Element.class).newInstance(root);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void RegisterAllAdv() {
		Item.register();
		User.register();
	}

}
