package ch.heigvd.res.labs.roulette;

import ch.heigvd.res.labs.roulette.net.TcpServer;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 */
public class QuizRouletteServer {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s %n");
		TcpServer server = new TcpServer();
		try {
			server.startServer();
		} catch (IOException ex) {
			Logger.getLogger(QuizRouletteServer.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
		}
	}
	
}
