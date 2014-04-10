package ch.heigvd.res.http.interfaces;

import ch.heigvd.res.http.HTTP.StatusCode;

/**
 * This interface defines the contract fulfilled by classes that implement the
 * behavior of HTTP responses.
 * 
 * @author Olivier Liechti
 */
public interface IResponse extends IMessage {

	/**
	 * Get the HTTP status code
	 * @return the HTTP status code sent by the server
	 */
	public StatusCode getStatusCode();
	
	/**
	 * Get the body in the response (which can be html, binary data, etc.)
	 * @return the body of the response
	 */
	public byte[] getBody();
	
}
