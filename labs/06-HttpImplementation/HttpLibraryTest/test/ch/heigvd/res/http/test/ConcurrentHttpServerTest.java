package ch.heigvd.res.http.test;


import ch.heigvd.res.http.HttpServer;
import ch.heigvd.res.http.interfaces.IHttpServer;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * This test class validates the fact that your server is able to handle
 * multiple clients concurrently (because you are running workers on multiple
 * threads). To implement the test, your server needs to implement a simple
 * "streaming" function. When your server receives requests for a specific url 
 * (/streaming), it should go over a loop of multiple iterations. At each iteration,
 * it should send a line of content and sleep for a specified time. This means that
 * it might take several seconds for the server to return the complete response.
 * By running multiple clients at the same time and measuring the total time, it
 * is possible for the test to check that the requests were handled in parallel and
 * not in sequence.
 * 
 * @author Olivier Liechti
 */
public class ConcurrentHttpServerTest extends HttpServerTest {

	final static Logger LOG = Logger.getLogger(ConcurrentHttpServerTest.class.getName());

	/**
	 * The HTTP server should be able to handle multiple clients in parallel. To test this
	 * behavior, we submit 3 requests in 3 different threads and target the "/steaming" URI.
	 * Read carefully the body of the response, you need to respect that syntax. Note that
	 * System.currentTimeMillis() has been used when sending the first and last line in the
	 * body. Notice that there are 3 lines starting with "Here is one part" because the
	 * query string param "iterations" had a value of 3. Notice that 
	 * 1397669533261-1397669530259 = 3002 (which is a bit more than 3 iterations * 1000 ms
	 * of sleep time per iteration).
	 * 
	 * --------------------------------------------------------------------
	 * The client sends this request (3 iterations, sleep time of 1 second)
	 * --------------------------------------------------------------------
	 * HTTP/1.1 200 OK
	 * Content-type: text/plain
	 * START 
	 * END
	 * GET /streaming?iterations=3&sleepTime=1000 HTTP/1.1
	 * Host: localhost
	 * 
	 * --------------------------------------------------------------------------------------------------------------------
	 * The server sends this response (System.currentTimeMillis() called on first line and last line, 1 line per iteration)
	 * --------------------------------------------------------------------------------------------------------------------
	 * HTTP/1.1 200 OK
	 * Connection: close
	 * Content-type: text/plain
	 * 
	 * START 1397669530259
	 * Here is one part of the message: 0
	 * Here is one part of the message: 1
	 * Here is one part of the message: 2
	 * END 1397669533261
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Test
	public void theHttpServerShouldHandleConcurrentClients() throws IOException, InterruptedException {
		IHttpServer server = new HttpServer(rootDirectory, port);
		server.start();
		int iterations = 5;
		long sleepTime = 500;
		long expectedResponseTime = iterations * sleepTime;
		
		// for the test, we accept a difference of 10% between the estimated time
		// and the measured time
		long margin = expectedResponseTime / 10;
						
		// Your server should detect "streaming" as the target resource. It should extract the value of
		// iterations and sleepTime from the query string in the target URI. It should then use these
		// values when implementing the generation of the response. It should take a little bit more than
		// iterations * sleepTime milliseconds for the server to generate 1 response. It should take a little
		// bit more than iterations * sleepTime milliseconds for the server to generate 3 responses.
		String url = server.getRootUrl() + "streaming?iterations=" + iterations + "&sleepTime=" + sleepTime;

		// We will send the requests in multiple threads
		Thread t1 = createClientThread(url);
		Thread t2 = createClientThread(url);
		Thread t3 = createClientThread(url);
		
		// Let's start the timer and send the requests...
		long startTime = System.currentTimeMillis();
		t1.start();
		t2.start();
		t3.start();
		// Let's wait for the 3 requests to have been handled by the server and take the time...
		t1.join();
		t2.join();
		t3.join();
		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime;
		
		assertTrue(Math.abs(duration - expectedResponseTime) < margin);
		server.stop();
	}

	private Thread createClientThread(final String url) {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					long clientStartTime = System.currentTimeMillis();
					byte[] response = fetchUrl(url);
					BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response)));
					String firstLine = br.readLine();
					String lastLine = null;
					String line = br.readLine();
					while (line != null) {
						lastLine = line;
						line = br.readLine();
					}
					long clientEndTime = System.currentTimeMillis();
					long clientDuration = clientEndTime - clientStartTime;
					
					long serverStartTime = Long.parseLong(firstLine.split(" ")[1]);
					long serverEndTime = Long.parseLong(lastLine.split(" ")[1]);
					long serverDuration = serverEndTime - serverStartTime;
					assertTrue(Math.abs(serverDuration - clientDuration) < 1000);
				} catch (IOException ex) {
					Logger.getLogger(HttpServerTest.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		});
		return t;
	}}
