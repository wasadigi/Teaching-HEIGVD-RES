package ch.heigvd.res.http.interfaces;

import java.io.File;

/**
 * This interface defines the contract to be fulfilled by the HttpServer
 * @author Olivier Liechti
 */
public interface IHttpServer {
	
	/**
	 * Returns the port on which the server is accepting client requests
	 * @return the TCP port
	 */
	public int getPort();
	
	/**
	 * The server is serving static files (html, etc) from a local directory.
	 * When a client sends a request to /static/a.html, the server looks for a
	 * file name 'a.html' in the web content root directory. This method is used
	 * to know what is the web content root directory in the file system.
	 * @return the directory where static files are stored
	 */
	public File getWebContentRootDirectory();
	
	/**
	 * Indicates whether the HTTP server is running (accepting requests) or not.
	 * @return true if the server is running
	 */
	public boolean isRunning();
	
	/**
	 * This method is used to start the server. When this method returns, calling
	 * isRunning() should return true AND it should be possible to establish
	 * TCP connections with the server
	 */
	public void start();
	
	/**
	 * This method is used to stop the server. When this method returns, calling
	 * isRunning() should return false AND the TCP port should be free. Hence,
	 * it should be possible to call start() right after to create a new server
	 * on the same port.
	 */
	public void stop();

	/**
	 * This method returns the absolute URL that a client can use to send a
	 * request to the server (including the hostname and the port number). If
	 * the server is run on a machine called "maguro.wasabi-tech.com" and listens
	 * on port 9090, then the method returns http://maguro.wasabi-tech.com:9090/
	 * @return 
	 */
	public String getRootUrl();


}
