package ch.heigvd.res.http.interfaces;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * This interface defines the contract fulfilled by classes that implement the
 * behavior of HTTP requests.
 * 
 * @author Olivier Liechti
 */
public interface IRequest extends IMessage {

	/**
	 * Getter for the HTTP method (GET, POST, etc.)
	 * @return the HTTP method in the request
	 */
	public String getMethod();
	
	public void setMethod(String method);

	/**
	 * Getter for the HTTP method (GET, POST, etc.)
	 * @return the HTTP method in the request
	 */
	URL getUrl();

	void setUrl(URL url) throws MalformedURLException;

	/**
	 * Getter for the hostname, as part of the target URL
	 * @return the hostname of the target server
	 */
	String getHost();

	/**
	 * Getter for the port, as part of the target URL
	 * @return the TCP port of the target server
	 */
	int getPort();

}
