package ch.heigvd.res.examples.udp;

import ch.heigvd.res.examples.udp.broadcast.BroadcastListener;
import ch.heigvd.res.examples.udp.broadcast.BroadcastPublisher;
import ch.heigvd.res.examples.udp.multicast.MulticastPublisher;
import ch.heigvd.res.examples.udp.multicast.MulticastSubscriber;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This program shows how to write message publishers and subscribers, using
 * UDP broadcast and multicast.
 * 
 * @author Olivier Liechti
 */
public class BroadcastMulticastPubSub {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		
		System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s %n");

		int broadcastPort = 4409;
		int multicatPort = 4605;
		InetAddress group = null;
		try {
			group = InetAddress.getByName("239.255.3.5");
		} catch (UnknownHostException ex) {
			Logger.getLogger(BroadcastMulticastPubSub.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		new Thread(new BroadcastPublisher(broadcastPort)).start();
		new Thread(new BroadcastListener(broadcastPort)).start();
		
		new Thread(new MulticastPublisher(multicatPort, group)).start();
		new Thread(new MulticastSubscriber(multicatPort, group)).start();
	}
	
}
