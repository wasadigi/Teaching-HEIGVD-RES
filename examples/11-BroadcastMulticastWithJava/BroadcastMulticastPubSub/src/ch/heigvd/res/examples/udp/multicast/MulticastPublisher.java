package ch.heigvd.res.examples.udp.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class publishes messages towards a multicast group, on a regular basis.
 * Notice that a DatagramSocket (not a MulticastSocket) can be used to send the
 * datagrams.
 * 
 * @author Olivier Liechti
 */
public class MulticastPublisher implements Runnable {

	static final Logger LOG = Logger.getLogger(MulticastPublisher.class.getName());

	private int port;
	private InetAddress multicastGroup;
	private DatagramSocket socket;
	
	public MulticastPublisher(int port, InetAddress multicastGroup) {
		try {
			this.multicastGroup = multicastGroup;
			this.port = port;
			socket = new DatagramSocket();
		} catch (SocketException ex) {
			LOG.log(Level.SEVERE, ex.getMessage(), ex);
		}
	}
	@Override
	public void run() {
		int counter = 1;
		while (true) {
			try {
				counter++;
				byte[] payload = ("{'id': " + counter + ", msg':'Java is cool (only interested people should know)'}").getBytes();
				DatagramPacket datagram = new DatagramPacket(payload, payload.length, multicastGroup, port);
				socket.send(datagram);
				LOG.log(Level.INFO, "Datagram published on multicast group {0}", multicastGroup);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					LOG.log(Level.SEVERE, ex.getMessage(), ex);
				}
			} catch (IOException ex) {
				LOG.log(Level.SEVERE, ex.getMessage(), ex);
			}
		}
	}
	
	

}
