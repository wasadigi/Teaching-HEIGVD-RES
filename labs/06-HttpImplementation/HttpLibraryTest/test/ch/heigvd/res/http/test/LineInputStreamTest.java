package ch.heigvd.res.http.test;

import ch.heigvd.res.http.util.LineInputStream;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Olivier Liechti
 */
public class LineInputStreamTest {
	
		private final String l1 = "this is the first line";
		private final String l2 = "and this is the second one";
		private final String l3 = "and finally, the third one";

	public LineInputStreamTest() {
	}
	

	@Test
	public void theLineInputStreamShouldDetectLinesOnCRLF() throws IOException {
		String CRLF = "\r\n";
		String testData = prepareTestString(CRLF);
		
		ByteArrayInputStream bis = new ByteArrayInputStream(testData.getBytes());
		LineInputStream lis = new LineInputStream(new BufferedInputStream(bis));
		
		String tl1 = lis.readLine();
		String tl2 = lis.readLine();
		String tl3 = lis.readLine();
		
		assertEquals(l1, tl1);
		assertEquals(l2, tl2);
		assertEquals(l3, tl3);
	}
	
	@Test
	public void theLineInputStreamShouldNotDetectLinesOnCR() throws IOException {
		StringBuilder sb = new StringBuilder();
		String CR = "\r";
		String testData = prepareTestString(CR);
		
		ByteArrayInputStream bis = new ByteArrayInputStream(testData.getBytes());
		LineInputStream lis = new LineInputStream(new BufferedInputStream(bis));
		
		String tl1 = lis.readLine();
		
		assertEquals(tl1, testData);
	}
	
	@Test
	public void theLineInputStreamShouldNotDetectLinesOnLF() throws IOException {
		StringBuilder sb = new StringBuilder();
		String LF = "\n";
		String testData = prepareTestString(LF);
		
		ByteArrayInputStream bis = new ByteArrayInputStream(testData.getBytes());
		LineInputStream lis = new LineInputStream(new BufferedInputStream(bis));
		
		String tl1 = lis.readLine();
		
		assertEquals(tl1, testData);
	}

	private String prepareTestString(String lineSeparator) {
		StringBuilder sb = new StringBuilder();
		sb.append(l1);
		sb.append(lineSeparator);
		sb.append(l2);
		sb.append(lineSeparator);
		sb.append(l3);
		return sb.toString();
	}

}
