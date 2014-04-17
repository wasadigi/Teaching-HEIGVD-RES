package ch.heigvd.res.http.test;

import ch.heigvd.res.http.HttpClient;
import ch.heigvd.res.http.HTTP.StatusCode;
import ch.heigvd.res.http.impl.Request;
import ch.heigvd.res.http.interfaces.IRequest;
import ch.heigvd.res.http.interfaces.IResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Olivier Liechti
 */
public class HttpClientTest {

	private static final boolean FOLLOW_REDIRECTS = true;
	private static final boolean DO_NOT_FOLLOW_REDIRECTS = false;

	public HttpClientTest() {
	}

	private IResponse submitGETRequest(String url, boolean followRedirects) throws IOException {
		HttpClient client = new HttpClient();
		client.setFollowRedirects(followRedirects);
		IRequest request = new Request();
		request.setUrl(new URL(url));
		IResponse response = client.sendRequest(request);
		return response;
	}

	@Test
	public void theHttpClientShouldHandleContentLengthHeader() throws IOException {
		String url = "http://localhost:3000/test/withContentLength";
		IResponse response = submitGETRequest(url, FOLLOW_REDIRECTS);
		assertNotNull(response.getHeader("Content-Length"));
		assertEquals(Integer.parseInt(response.getHeader("Content-Length")), response.getBody().length);
		assertEquals(StatusCode.SUCCESS, response.getStatusCode());
		assertEquals("Hello, you should have received a Content-length header.", new String(response.getBody()));
		checkAgainstHttpURLConnection(url);
	}

	@Test
	public void theHttpClientShouldHandleConnectionCloseHeader() throws IOException {
		String url = "http://localhost:3000/test/connectionClose";
		IResponse response = submitGETRequest(url, FOLLOW_REDIRECTS);
		assertEquals("CLOSE", response.getHeader("Connection").toUpperCase());
		assertEquals(StatusCode.SUCCESS, response.getStatusCode());
		assertEquals("Hello, you should have NOT have received any Content-length header.", new String(response.getBody()));
		checkAgainstHttpURLConnection(url);
	}

	@Test
	public void theHttpClientShouldHandleChunkedEncoding() throws IOException {
		String url = "http://localhost:3000/test/chunked";
		IResponse response = submitGETRequest(url, FOLLOW_REDIRECTS);
		assertEquals("CHUNKED", response.getHeader("Transfer-Encoding").toUpperCase());
		assertEquals(StatusCode.SUCCESS, response.getStatusCode());
		assertEquals("Hello, this will probably be in the first chunk.And this will be in the second one.I am done. You should have received 3 chunks. Bye.", new String(response.getBody()));
		checkAgainstHttpURLConnection(url);
	}

	@Test
	public void theHttpClientShouldHandleUrlWithoutTrailingSlash() throws IOException {
		String url = "http://www.heig-vd.ch";
		IResponse response = submitGETRequest(url, FOLLOW_REDIRECTS);
		assertEquals(StatusCode.SUCCESS, response.getStatusCode());
		checkAgainstHttpURLConnection(url);
	}

	@Test
	public void theHttpClientShouldHandleUrlWithTrailingSlash() throws IOException {
		String url = "http://www.heig-vd.ch/";
		IResponse response = submitGETRequest(url, FOLLOW_REDIRECTS);
		assertEquals(StatusCode.SUCCESS, response.getStatusCode());
		assertTrue(response.getBody().length != 0);
		checkAgainstHttpURLConnection(url);
	}

	@Test
	public void theHttpClientShouldBeAbleToIgnoreRedirects() throws IOException {
		String url = "http://localhost:3000/test/redirect-absolute";
		IResponse response = submitGETRequest(url, DO_NOT_FOLLOW_REDIRECTS);
		assertEquals(StatusCode.FOUND, response.getStatusCode());
		checkAgainstHttpURLConnection(url);
	}

	@Test
	public void theHttpClientShouldBeAbleToFollowRedirectsWithAbsoluteUrl() throws IOException {
		String url = "http://localhost:3000/test/redirect-absolute";
		IResponse response = submitGETRequest(url, FOLLOW_REDIRECTS);
		assertEquals(StatusCode.SUCCESS, response.getStatusCode());
		assertEquals("You have reached the final destination. Redirect worked.", new String(response.getBody()));
		checkAgainstHttpURLConnection(url);
	}

	@Test
	public void theHttpClientShouldBeAbleToFollowRedirectsWithRelativeRootUrl() throws IOException {
		String url = "http://localhost:3000/test/redirect-relative-root";
		IResponse response = submitGETRequest(url, FOLLOW_REDIRECTS);
		assertEquals(StatusCode.SUCCESS, response.getStatusCode());
		assertEquals("You have reached the final destination. Redirect worked.", new String(response.getBody()));
		checkAgainstHttpURLConnection(url);
	}

	@Test
	public void theHttpClientShouldBeAbleToFollowRedirectsWithRelativeUrl() throws IOException {
		String url = "http://localhost:3000/test/redirect-relative";
		IResponse response = submitGETRequest(url, FOLLOW_REDIRECTS);
		assertEquals(StatusCode.SUCCESS, response.getStatusCode());
		assertEquals("You have reached the final destination. Redirect worked.", new String(response.getBody()));
		checkAgainstHttpURLConnection(url);
	}

	private void checkAgainstHttpURLConnection(String url) throws IOException {
		IResponse response = submitGETRequest(url, FOLLOW_REDIRECTS);
		URL u = new URL(url);
		HttpURLConnection con = (HttpURLConnection) u.openConnection();
		InputStream in = (InputStream) con.getContent();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		int bytesRead;
		byte[] buffer = new byte[2048];
		while ((bytesRead = in.read(buffer, 0, buffer.length)) != -1) {
			bos.write(buffer, 0, bytesRead);
		}
		assertEquals(con.getResponseCode(), response.getStatusCode().getValue());
		assertArrayEquals(bos.toByteArray(), response.getBody());
	}

	@Test
	public void theHttpClientShouldWorkWithHeigVd() throws IOException {
		checkAgainstHttpURLConnection("http://www.heig-vd.ch");
	}

	@Test
	public void theHttpClientShouldWorkWithNodeJsOrg() throws IOException {
		checkAgainstHttpURLConnection("http://www.nodejs.org");
	}

	@Test
	public void theHttpClientShouldWorkWithTsrCh() throws IOException {
		checkAgainstHttpURLConnection("http://www.tsr.ch/");
	}

	@Test
	public void theHttpClientShouldWorkWithLocalServer() throws IOException {
		checkAgainstHttpURLConnection("http://localhost:3000/test/chunked");
	}

	@Test
	public void theHttpClientShouldWorkWithLotaris() throws IOException {
		checkAgainstHttpURLConnection("http://www.lotaris.com/");
	}

	@Test
	public void theHttpClientShouldWorkWithRadar() throws IOException {
		checkAgainstHttpURLConnection("http://radar.oreilly.com/");
	}

}
