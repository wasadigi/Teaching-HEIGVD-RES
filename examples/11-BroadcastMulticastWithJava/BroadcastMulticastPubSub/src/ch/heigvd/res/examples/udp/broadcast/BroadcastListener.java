package ch.heigvd.res.examples.udp.broadcast;

import ch.heigvd.res.examples.udp.multicast.MulticastPublisher;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class binds a socket on a UDP port and listens for incoming datagrams.
 * Whenever a datagram is received, its content is printed on the console.
 * 
 * @author Olivier Liechti
 */
public class BroadcastListener implements Runnable {
	
	static final Logger LOG = Logger.getLogger(BroadcastListener.class.getName());

	int port;
	
	DatagramSocket socket;
	
	public BroadcastListener(int port) {
		this.port = port;
		try {
			socket = new DatagramSocket(port);
		} catch (SocketException ex) {
			LOG.log(Level.SEVERE, ex.getMessage(), ex);
		}
	}

	@Override
	public void run() {
		while (true) {
			byte[] buffer = new byte[2048];
			DatagramPacket datagram = new DatagramPacket(buffer, buffer.length);
			try {
				socket.receive(datagram);
				String msg = new String(datagram.getData(), datagram.getOffset(), datagram.getLength());
				LOG.log(Level.INFO, "Received broadcasted message: {0}", msg);
			} catch (IOException ex) {
				LOG.log(Level.SEVERE, ex.getMessage(), ex);
			}
		}
	}

}
