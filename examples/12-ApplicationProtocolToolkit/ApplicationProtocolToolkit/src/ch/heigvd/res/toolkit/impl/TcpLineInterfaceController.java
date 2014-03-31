package ch.heigvd.res.toolkit.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class implements a specific interface controller, which 1) uses TCP as
 * an underlying transport protocol and 2) uses a simple line-by-line message
 * demarcation method.
 * 
 * In other words, the server reads the data sent by the client and considers that 
 * each line contains data that can be transformed into one application-level
 * message.
 * 
 * The interface controller is multi-threaded. One thread is used to accept
 * connection requests, one more thread is used for each connected client.
 * 
 * @author Olivier Liechti
 */
public class TcpLineInterfaceController extends AbstractInterfaceController {

	final static Logger LOG = Logger.getLogger(TcpLineInterfaceController.class.getName());

	int port;

	boolean shouldRun = true;

	ServerSocket serverSocket;

	private final Map<Long, ClientWorker> clientWorkers = new HashMap<>();

	/**
	 * This class implements the method that runs on the "listen" thread and
	 * is responsible for accepting connection requests from clients.
	 */
	private class ListenWorker implements Runnable {
		@Override
		public void run() {
			try {
				serverSocket = new ServerSocket(port);
				LOG.info("Bound socket on TCP port " + port + ". Ready to accept connection requests.");
				while (shouldRun) {
					Socket socket = serverSocket.accept();
					new Thread(new ClientWorker(socket)).start();
				}
			} catch (IOException ex) {
				LOG.log(Level.SEVERE, ex.getMessage(), ex);
			}
		}
	}

	/**
	 * This class is responsible for managing a client connection. It provides a
	 * method that executes on its own thread. The class exposes two methods that
	 * are invoked via the IContext and the IInterfaceController interfaces: 
	 * 1) sendMessage and 2) closeConnection.
	 */
	private class ClientWorker implements Runnable {

		private final Socket socket;
		private final BufferedReader in;
		private final PrintWriter out;
		private final long sessionId;

		public ClientWorker(Socket socket) throws IOException {
			this.socket = socket;
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

			Session newSession = createSession();
			sessionId = newSession.getSessionId();
			TcpLineInterfaceController.this.clientWorkers.put(sessionId, this);
			startSession(sessionId);
		}

		@Override
		public void run() {
			String line;
			try {
				while ((line = in.readLine()) != null) {
					try {
						Message msg = getProtocolHandler().getProtocolSerializer().deserialize(line.getBytes());
						TcpLineInterfaceController.this.onMessage(sessionId, msg);
					} catch (InvalidMessageException e) {
						TcpLineInterfaceController.this.onInvalidMessage(sessionId, e);
					}
				}
				TcpLineInterfaceController.this.closeSession(sessionId);
			} catch (IOException ex) {
				Logger.getLogger(TcpLineInterfaceController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
			}
		}

		/**
		 * Used to send raw data back to the connected client
		 * 
		 * @param data the wire-level data, obtained after serializing an application-level message
		 */
		public void send(String data) {
			LOG.log(Level.INFO, "Sending wire-level data over TCP (session:{0}) : {1}", new Object[]{sessionId, data});
			out.println(data);
			out.flush();
		}

		/**
		 * Used to close the TCP connection
		 */
		public void closeConnection() {
			LOG.log(Level.INFO, "Closing TCP connection (session:{0})", sessionId);
			out.flush();
			try {
				socket.close();
			} catch (IOException ex) {
				Logger.getLogger(TcpLineInterfaceController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
			}
		}

	}

	public TcpLineInterfaceController(int port) {
		this.port = port;
	}

	@Override
	public void startup() {
		new Thread(new ListenWorker()).start();
	}

	@Override
	public void sendMessage(long sessionId, Message message) {
		// Retrieve the worker who is taking care of the session and connected with the client
		ClientWorker worker = clientWorkers.get(sessionId);
		
		if (worker == null) {
			LOG.severe("Could not find worker for session " + sessionId);
		}
		
		// Serialize the message into wire-level data
		byte[] data = getProtocolHandler().getProtocolSerializer().serialize(message);
		
		// Send the wire-level data
		worker.send(new String(data));
	}

	@Override
	public void closeSession(long sessionId) {
		super.closeSession(sessionId);
		
		// Retrieve the worker who is taking care of the session and connected with the client
		ClientWorker worker = clientWorkers.get(sessionId);
		
		// Ask the worker to close the connection
		worker.closeConnection();
	}

}
