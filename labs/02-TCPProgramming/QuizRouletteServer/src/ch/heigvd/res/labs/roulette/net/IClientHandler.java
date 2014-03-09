package ch.heigvd.res.labs.roulette.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Olivier Liechti
 */
public interface IClientHandler {
	
	public void handleClientConnection(InputStream is, OutputStream os) throws IOException ;

}
