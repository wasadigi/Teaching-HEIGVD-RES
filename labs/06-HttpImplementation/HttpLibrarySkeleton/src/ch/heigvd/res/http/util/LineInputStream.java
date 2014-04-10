package ch.heigvd.res.http.util;

import ch.heigvd.res.http.interfaces.ILineInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Student First Name Last Name
 */
public class LineInputStream extends FilterInputStream implements ILineInputStream {

	public LineInputStream(InputStream in) {
		super(in);
	}

	@Override
	public String readLine() throws IOException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}



}
