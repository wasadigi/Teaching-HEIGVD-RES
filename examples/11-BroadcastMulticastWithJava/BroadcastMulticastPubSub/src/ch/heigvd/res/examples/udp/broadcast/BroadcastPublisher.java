package ch.heigvd.res.examples.udp.broadcast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class broadcasts messages on a regular basis.
 * 
 * @author Olivier Liechti
 */
public class BroadcastPublisher implements Runnable {

	static final Logger LOG = Logger.getLogger(BroadcastPublisher.class.getName());

	private int port;
	private DatagramSocket socket;
	
	
	public BroadcastPublisher(int port) {
		try {
			this.port = port;
			socket = new DatagramSocket();
			socket.setBroadcast(true);
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
				DatagramPacket datagram = new DatagramPacket(payload, payload.length, InetAddress.getByName("255.255.255.255"), port);
				socket.send(datagram);
				LOG.log(Level.INFO, "Datagram broadcasted to local network nodes.");
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
