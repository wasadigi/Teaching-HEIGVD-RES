package ch.heigvd.res.http;

import ch.heigvd.res.http.interfaces.IHttpServer;
import java.io.File;
import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 */
public class HttpServer implements IHttpServer {

	final static Logger LOG = Logger.getLogger(HttpServer.class.getName());

	public HttpServer(File docRoot, int port) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.		
	}
	
	@Override
	public int getPort() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public File getWebContentRootDirectory() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public boolean isRunning() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void start() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void stop() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public String getRootUrl() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}


}
