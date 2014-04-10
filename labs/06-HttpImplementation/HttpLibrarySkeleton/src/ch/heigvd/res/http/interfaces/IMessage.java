package ch.heigvd.res.http.interfaces;

/**
 * This interface defines the contract that is fulfilled by all HTTP messages,
 * ie both requests and responses.
 * 
 * @author Olivier Liechti
 */
public interface IMessage {

	/**
	 * Returns the value of a header. The name is case INSENSITIVE.
	 * @param headerName the name of the header (e.g. Content-Length). 
	 * @return the value of the header, or null if the header is not defined in the message
	 */
	String getHeader(String headerName);


}
