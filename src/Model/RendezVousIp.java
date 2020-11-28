/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author ATX
 */

import Model.advertisement.AbstractAdvertisement;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import Model.util.StringToElement;
import java.util.ArrayList;

public class RendezVousIp extends AbstractAdvertisement {
	private Element ips;

	
	public RendezVousIp() {
		super();
	}
	
	public RendezVousIp(Element root) {
		super(root);
	}
	
	public RendezVousIp(String xml) {
		super(xml);
	}
	
	@SuppressWarnings("rawtypes")
	public RendezVousIp(net.jxta.document.Element root) {
		super(root);
	}
	
	public ArrayList<String> getIps() {
		ArrayList<String> iparray = new ArrayList<String>();
		for(Element e : ips.getChildren()) {
			iparray.add(e.getValue());
		}
		return iparray;
	}
	
	public void addIp(String ip) {
		Element e = new Element("ip");
		e.addContent(ip);
		ips.addContent(e);
	}
	
	public void removeIp(String ip) {
		for(Element e : ips.getChildren()) {
			if(e.getValue().equals(ip)) {
				e.detach();
			}
		}
	}
	
	
	@Override
	public String getSimpleName() {
		return getClass().getSimpleName();
	}

	@Override
	protected String getAdvertisementName() {
		return getClass().getName();
	}

	@Override
	protected void setKeys() {
		ips = new Element("allips");
		addKey("ips", false, true);
	}

	@Override
	protected void putValues() {
		XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
		addValue("ips", out.outputString(ips));
	}

	@Override
	protected boolean handleElement(Element e) {
		switch(e.getName()) {
		case "ips": ips = StringToElement.getElementFromString(e.getValue()); return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		RendezVousIp rdvip = new RendezVousIp();
		rdvip.addIp("tcp://85.240.163.6:9800");
		rdvip.addIp("tcp://120.120.120.120:9800");
		rdvip.addIp("tcp://125.125.125.125:9800");
		rdvip.removeIp("tcp://120.120.120.120:9800");
		
		RendezVousIp rdv2 = (RendezVousIp) rdvip.clone();
		for(String s: rdv2.getIps()) {
			System.out.println(s);
		}
		
	}
}
