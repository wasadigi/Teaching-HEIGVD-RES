package ch.heigvd.res.http.test;

import ch.heigvd.res.http.HTTP.StatusCode;
import ch.heigvd.res.http.HttpServer;
import ch.heigvd.res.http.interfaces.IHttpServer;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 * This class contains the integration tests that validate the behavior of your
 * HTTP server
 *
 * @author Olivier Liechti
 */
public class HttpServerTest {

	final static Logger LOG = Logger.getLogger(HttpServerTest.class.getName());

	/**
	 * The TCP port on which our server will listen
	 */
	protected int port = 9099;

	/**
	 * The file system directory where we will store static files served by our
	 * HTTP server
	 */
	protected File rootDirectory = new File("./test-data/webroot");


	/**
	 * The HTTP server should provide methods to start and stop it. After the start
	 * method has been invoked, the server should accept client requests. After the
	 * stop method has been invoked, it should not.
	 */
	@Test
	public void itShouldBePossibleToStartAndStopTheHttpServer() {
		IHttpServer server = new HttpServer(rootDirectory, port);
		assertFalse(server.isRunning());
		server.start();
		assertTrue(server.isRunning());
		server.stop();
		assertFalse(server.isRunning());
	}

	/**
	 * The HTTP server should handle GET requests. To validate the basic behavior,
	 * this test sends a request targeting the "/hello" URI and expects to receive
	 * a response with a body containing the string "world".
	 * 
	 * @throws IOException
	 */
	@Test
	public void theHttpServerShouldHandleBasicGETRequests() throws IOException {
		IHttpServer server = new HttpServer(rootDirectory, port);
		server.start();
		
		// We send "GET /hello HTTP/1.1"...
		String url = server.getRootUrl() + "hello";
		byte[] response = fetchUrl(url);
		
		// ...and expect to receive "world" in the response body
		assertEquals("world", new String(response));
		server.stop();
	}



	/**
	 * The server should be able to serve static files, stored in a file system
	 * directory. When the client sends a request with a URL starting with
	 * '/static', the server should use the rest of the URL to lookup the file in
	 * the file system. It should then send the content in the response body
	 *
	 * @throws IOException
	 */
	@Test
	public void theHttpServerShouldServeStaticFiles() throws IOException {
		IHttpServer server = new HttpServer(rootDirectory, port);
		server.start();
		
		// We generate a file, with a random name and random content; we store it in
		// the server web doc root
		File testFile = createTestFile(rootDirectory, generateRandomInt(1000, 2000));
		String testUrl = "http://localhost:" + port + "/static/" + testFile.getName();

		// We get the content of the file, by reading it directly...
		byte[] fileContent = loadFile(testFile);

		// We get the content, by sending an HTTP request to the server...
		byte[] webContent = fetchUrl(testUrl);
		
		// ... the content should be the same!
		assertArrayEquals(fileContent, webContent);
		server.stop();

	}

	/**
	 * Content negotiation is an important aspect of the HTTP specification. For
	 * instance, the client can specify its preference for one or more media types
	 * (text/html, application/xml, etc.). It uses the "Accept" header for that
	 * purpose. The server indicates the media type it was able to generate with 
	 * the "Content-type" header. This test validates the fact that your server
	 * knows how to interpret the Accept header for a test url, namely::
	 * 
	 *   GET /dynamic/whoIsMyFavoriteProfessor HTTP/1.1
	 *   Host: localhost
	 *   Accept: application/json
	 * 
	 * The server should look in the Accept header and return the appropriate
	 * payload (see the source of the test)
	 * 
	 * @throws IOException
	 */
	@Test
	public void theHttpServerShouldHandleAcceptHeader() throws IOException {
		IHttpServer server = new HttpServer(rootDirectory, port);
		server.start();

		// The server will respond to this particular test URL
		String testUrl = "http://localhost:" + port + "/dynamic/whoIsMyFavoriteProfessor";
		URL targetUrl = new URL(testUrl);
		HttpURLConnection con = (HttpURLConnection) targetUrl.openConnection();
		
		// Let's first request a json representation of the resource...
		con.setRequestProperty("Accept", "application/json");
		InputStream is = (InputStream) con.getContent();
		byte[] result = loadDataFromStream(is);
		is.close();
		// ... and compare with what we expect
		assertEquals("{\"firstName\" : \"Olivier\", \"lastName\" : \"Liechti\"}", new String(result));

		// Let's now request an xml representation of the resource...
		con = (HttpURLConnection) targetUrl.openConnection();
		con.setRequestProperty("Accept", "application/xml");
		is = (InputStream) con.getContent();
		result = loadDataFromStream(is);
		is.close();
		// ... and compare with what we expect
		assertEquals("<FullName>Olivier Liechti<FullName>", new String(result));

		server.stop();

	}

	/**
	 * HTTP clients may send request with a URI that does not identify any valid
	 * resource. In that case, the server should respond with a 404 status code.
	 * To test this behavior, we generate a random string and use it as the target
	 * URL. The server will not know about it, for sure.
	 *
	 * @throws IOException
	 */
	@Test
	public void theHttpServerShouldRespondWith404WhenAResourceIsNotFound() throws IOException {
		IHttpServer server = new HttpServer(rootDirectory, port);
		server.start();
		
		// Let's generate a random URL - we are sure that the server does not know about it
		String unknownResourceUrl = generateRandomString(30);
		String url = "http://localhost:" + port + "/" + unknownResourceUrl;
		URL u = new URL(url);
		HttpURLConnection con = (HttpURLConnection) u.openConnection();
		
		// We should get back a 404 status code
		assertEquals(StatusCode.NOT_FOUND.getValue(), con.getResponseCode());
		server.stop();
	}

	/**
	 * This method creates a test file, by generating a random name and random
	 * content. The file is created within the specified directory.
	 * @param directory the dir where to create the file
	 * @param length the length of the file to generate
	 * @return the file that has been created
	 * @throws IOException 
	 */
	private File createTestFile(File directory, int length) throws IOException {
		String filename = generateRandomString(20) + ".txt";
		File file = new File(directory + File.separator + filename);
		directory.mkdirs();
		file.createNewFile();
		PrintStream ps = new PrintStream(file);
		ps.print(generateRandomString(length));
		ps.close();
		return file;
	}

	/**
	 * Generates a random string
	 * @param length the length of the string
	 * @return the generated string
	 */
	private String generateRandomString(int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append((char) generateRandomInt(65, 90));
		}
		return sb.toString();
	}

	/**
	 * Generates a random int value, within the lower and upper bounds (inclusive)
	 *
	 * @param lowerBound
	 * @param upperBound
	 * @return
	 */
	private int generateRandomInt(int lowerBound, int upperBound) {
		return lowerBound + (int) (Math.random() * (upperBound - lowerBound + 1));
	}

	protected byte[] fetchUrl(String url) throws IOException {
		URL u = new URL(url);
		HttpURLConnection con = (HttpURLConnection) u.openConnection();
		InputStream is = (InputStream) con.getContent();
		byte[] result = loadDataFromStream(is);
		is.close();
		return result;
	}

	private byte[] loadFile(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		byte[] result = loadDataFromStream(fis);
		fis.close();
		return result;
	}

	private byte[] loadDataFromStream(InputStream is) throws IOException {
		BufferedInputStream in = new BufferedInputStream(is);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int bytesRead;
		while ((bytesRead = in.read(buffer)) != -1) {
			bos.write(buffer, 0, bytesRead);
		}
		return bos.toByteArray();
	}

}
