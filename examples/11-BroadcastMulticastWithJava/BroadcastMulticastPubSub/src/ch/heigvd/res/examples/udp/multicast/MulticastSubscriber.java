package ch.heigvd.res.examples.udp.multicast;

import ch.heigvd.res.examples.udp.broadcast.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class binds a multicast socket on a UDP port and listens for incoming datagrams.
 * It also joins the specified multicast group. Whenever a datagram is received, its content 
 * is printed on the console.
 * 
 * @author Olivier Liechti
 */
public class MulticastSubscriber implements Runnable {
	
	static final Logger LOG = Logger.getLogger(MulticastSubscriber.class.getName());
	
	private final InetAddress multicastGroup;
	int port;
	
	MulticastSocket socket;
	
	public MulticastSubscriber(int port, InetAddress multicastGroup) {
		this.port = port;
		this.multicastGroup = multicastGroup;
		try {
			socket = new MulticastSocket(port);
			socket.joinGroup(multicastGroup);
		} catch (IOException ex) {
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
				LOG.log(Level.INFO, "Received a message on a multicast group: {0}", msg);
			} catch (IOException ex) {
				LOG.log(Level.SEVERE, ex.getMessage(), ex);
			}
		}
	}

}
