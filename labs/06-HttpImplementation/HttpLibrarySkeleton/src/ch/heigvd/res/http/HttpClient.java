package ch.heigvd.res.http;

import ch.heigvd.res.http.interfaces.IHttpClient;
import ch.heigvd.res.http.interfaces.IRequest;
import ch.heigvd.res.http.interfaces.IResponse;
import java.io.IOException;

/**
 * This is class that you have to implement
 * 
 * @author Student First Name Last Name
 */
public class HttpClient implements IHttpClient{

	@Override
	public IResponse sendRequest(IRequest request) throws IOException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void setFollowRedirects(boolean follow) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}


}
