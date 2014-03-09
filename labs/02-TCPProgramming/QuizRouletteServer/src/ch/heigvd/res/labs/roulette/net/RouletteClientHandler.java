package ch.heigvd.res.labs.roulette.net;

import ch.heigvd.res.labs.roulette.RouletteV1Protocol;
import ch.heigvd.res.labs.roulette.data.EmptyStoreException;
import ch.heigvd.res.labs.roulette.data.IStudentsStore;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class implements the Roulette protocol.
 * @author Olivier Liechti
 */
public class RouletteClientHandler implements IClientHandler {

	final static Logger LOG = Logger.getLogger(RouletteClientHandler.class.getName());

	private IStudentsStore store;

	public RouletteClientHandler(IStudentsStore store) {
		this.store = store;
	}

	@Override
	public void handleClientConnection(InputStream is, OutputStream os) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(os));

		writer.println("Hello. Online HELP is available. Will you find it?");
		writer.flush();

		String command;
		boolean done = false;
		while (!done && ((command = reader.readLine()) != null)) {
			LOG.log(Level.INFO, "COMMAND: {0}", command);
			switch (command.toUpperCase()) {
				case RouletteV1Protocol.CMD_RANDOM:
					try {
						writer.println(store.pickRandomStudent());
					} catch (EmptyStoreException ex) {
						writer.println("{'error' : 'store is empty'}");
					}
					break;
				case RouletteV1Protocol.CMD_HELP:
					writer.println("Commands: " + Arrays.toString(RouletteV1Protocol.SUPPORTED_COMMANDS));
					break;
				case RouletteV1Protocol.CMD_INFO:
					writer.println("{'protocol version' : '1.0', numberOfStudents' : " + store.getNumberOfStudents() + "}");
					break;
				case RouletteV1Protocol.CMD_LOAD:
					writer.println(RouletteV1Protocol.RESPONSE_LOAD_START);
					writer.flush();
					store.importData(reader);
					writer.println(RouletteV1Protocol.RESPONSE_LOAD_DONE);
					break;
				case RouletteV1Protocol.CMD_BYE:
					done = true;
					break;
				default:
					writer.println("Huh? please use HELP if you don't know what commands are available.");
					break;
			}
			writer.flush();
		}

	}

}
