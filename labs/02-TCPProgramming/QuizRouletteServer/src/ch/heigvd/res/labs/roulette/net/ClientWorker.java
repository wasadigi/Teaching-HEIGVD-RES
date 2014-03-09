package ch.heigvd.res.labs.roulette.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Instances of this class are created whenever a client has arrived and a
 * connection has been established (the instances are created by the TCPServer
 * class). The class is responsible for setting up and cleaning up the communication
 * streams, but delegates the hard work (i.e. the implementation of our own
 * application protocol) to a class that implements the IClientHandler interface.
 * 
 * This means that we could reuse this class, develop a new class that implements
 * the IClientHandler interface and implement another application protocol.
 * 
 * @author Olivier Liechti
 */
public class ClientWorker implements Runnable {
	
	static final Logger LOG = Logger.getLogger(ClientWorker.class.getName());

	private IClientHandler handler = null;
	private Socket clientSocket = null;
	private InputStream is = null;
	private OutputStream os = null;

	public ClientWorker(Socket clientSocket, IClientHandler handler) throws IOException {
		this.clientSocket = clientSocket;
		this.handler = handler;
		is = clientSocket.getInputStream();
		os = clientSocket.getOutputStream();
	}

	@Override
	public void run() {
		try {
			handler.handleClientConnection(is, os);
		} catch (IOException ex) {
			LOG.log(Level.SEVERE, ex.getMessage(), ex);
		} finally {
			try {
				clientSocket.close();
			} catch (IOException ex) {
				Logger.getLogger(ClientWorker.class.getName()).log(Level.SEVERE, null, ex);
			}
			try {
				is.close();
			} catch (IOException ex) {
				LOG.log(Level.SEVERE, ex.getMessage(), ex);
			}
			try {
				os.close();
			} catch (IOException ex) {
				LOG.log(Level.SEVERE, ex.getMessage(), ex);
			}
		}
	}

}
