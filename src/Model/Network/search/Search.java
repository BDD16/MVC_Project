/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Network.search;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Enumeration;

import Model.advertisement.AbstractAdvertisement;
import Model.Network.NetworkInterface;
import net.jxta.discovery.DiscoveryEvent;
import net.jxta.discovery.DiscoveryListener;
import net.jxta.discovery.DiscoveryService;
import net.jxta.document.Advertisement;
import net.jxta.document.MimeMediaType;
import net.jxta.id.IDFactory;
import net.jxta.peer.PeerID;

/**
 *
 * @author ATX
 */
public class Search<T extends AbstractAdvertisement> implements DiscoveryListener {

	private ArrayList<SearchListener<T>> listeners = new ArrayList<SearchListener<T>>();
	private DiscoveryService discovery;
	private String attribute;
	private boolean exact;
	private ArrayList<T> results = new ArrayList<T>();
	private ArrayList<Result> resultsWithPeerID = new ArrayList<Result>();
	
	public class Result implements Cloneable{
		public PeerID peerID;
		public T result;
		
		protected Result(String peerID, T result) {
			
			try {
				this.peerID = (PeerID) IDFactory.fromURI(new URI(peerID));
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			this.result = result;
		}
		
	}
	
	
	/**
	 * Initialise a new research on the discovery, on the specified attribute.
	 * @param NetworkInterface
	 * @param pg the peerGroup. Usually Class name or id-ClassName
	 * @param attribute
	 * @param exact true if the search has to be exact (letter sensitive) or not (search "foo" can find "foobar")
	 */
	public Search(NetworkInterface n, String pg, String attribute, boolean exact) {
		this.discovery = n.getGroup(pg).getDiscoveryService();
		this.attribute = attribute;
		this.exact = exact;
	}
	
	/**
	 * Search all the advertisement in this discovery that match the attribute with this value
	 * @param value
	 * @param maxWaitTime Time to wait before function returns. Can be 0, the search will continue and notify listeners and function returns immediately
	 * @param waitResult, number of results expected before the function return;
	 */
	public void search(String value, long maxWaitTime, int waitResult) {
		results = new ArrayList<T>();
		String searchValue = !exact ? "*" + value + "*": value;
		discovery.getRemoteAdvertisements(null, DiscoveryService.ADV, attribute, searchValue, 10, this);
		
		long waiting = maxWaitTime;
		
		if(maxWaitTime != 0) {
			while(waiting > 0 && (results.size() < waitResult || waitResult == 0)) {
				long currentTime = System.currentTimeMillis();
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				waiting -= System.currentTimeMillis()-currentTime;
			}
		}
	}
		
		//Wait for maxWaitTime or number of results > waitResult

	
	/**
	 * Add a listeners that want to be called when advertisements are reveived.
	 * @param l
	 */
	public void addListener(SearchListener<T> l) {
		listeners.add(l);
	}
	
	private void notifyListeners(T event) {
		for(SearchListener<T> l: listeners) {
			l.searchEvent(event);
		}
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<T> getResults() {
		return (ArrayList<T>) results.clone();
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Result> getResultsWithPeerID() {
		return (ArrayList<Search<T>.Result>) this.resultsWithPeerID.clone();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void discoveryEvent(DiscoveryEvent event) {
		String pid = "urn:jxta:" + event.getSource().toString().substring(7);
		Enumeration<Advertisement> advs = event.getResponse().getAdvertisements();
		while(advs.hasMoreElements()) {
			T adv = (T) advs.nextElement();
			results.add(adv);
			resultsWithPeerID.add(new Result(pid, adv));
			notifyListeners(adv);
		}
		
	}

}