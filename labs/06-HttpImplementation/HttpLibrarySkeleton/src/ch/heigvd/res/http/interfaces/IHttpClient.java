package ch.heigvd.res.http.interfaces;

import java.io.IOException;

/**
 * This interface defines the contract fulfilled by the classes that implement
 * the behavior of simple HTTP clients.
 * 
 * @author Olivier Liechti
 */
public interface IHttpClient {

	/**
	 * This method submits an HTTP request to the host defined in the request's
	 * target URL, parses the bytes returned by the server and generates a response
	 * object that is passed to the caller. The caller can then retrieve response
	 * headers and body.
	 * 
	 * @param request the request to be submitted (previously built by the caller)
	 * @return the response sent back by the server
	 * @throws IOException 
	 */
	IResponse sendRequest(IRequest request) throws IOException;

	/**
	 * When the caller invokes sendRequest on a target URL, the server may respond
	 * with a 301 or a 302 status code. If the client is set to follow redirects,
	 * then it will automatically (without requiring any action from the caller)
	 * submit a new request by using the content of the "Location" header in the
	 * response. If the client is NOT set to follow redirects, it will simply
	 * return the 301 or 302 status code to the caller.
	 * 
	 * @param follow if true, the client will automatically follow redirects
	 */
	void setFollowRedirects(boolean follow);

}
