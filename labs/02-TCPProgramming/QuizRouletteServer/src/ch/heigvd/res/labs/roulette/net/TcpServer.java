package ch.heigvd.res.labs.roulette.net;

import ch.heigvd.res.labs.roulette.RouletteV1Protocol;
import ch.heigvd.res.labs.roulette.data.IStudentsStore;
import ch.heigvd.res.labs.roulette.data.StudentsStoreImpl;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * @author Olivier Liechti
 */
public class TcpServer {
	
	final static Logger LOG = Logger.getLogger(TcpServer.class.getName());

	IStudentsStore store = new StudentsStoreImpl();
	
	public void startServer() throws IOException {
		ServerSocket serverSocket = new ServerSocket(RouletteV1Protocol.DEFAULT_PORT);
		while (true) {
			LOG.info("Listing for client connection on " + serverSocket.getLocalSocketAddress());
			Socket clientSocket = serverSocket.accept();
			LOG.info("New client has arrived...");
			ClientWorker worker = new ClientWorker(clientSocket, new RouletteClientHandler(store));
			LOG.info("Delegating work to client worker...");
			new Thread(worker).start();
		}
	}
}
