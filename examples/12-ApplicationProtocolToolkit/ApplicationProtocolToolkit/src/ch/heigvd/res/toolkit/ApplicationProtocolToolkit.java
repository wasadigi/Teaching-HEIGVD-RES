
package ch.heigvd.res.toolkit;

import ch.heigvd.res.toolkit.interfaces.IProtocolHandler;
import ch.heigvd.res.toolkit.interfaces.IProtocolSerializer;
import ch.heigvd.res.toolkit.interfaces.IInterfaceController;
import ch.heigvd.res.toolkit.pingpong.PingPongInterfaceController;
import ch.heigvd.res.toolkit.pingpong.PingPongProtocol;
import ch.heigvd.res.toolkit.pingpong.PingPongProtocolHandler;
import ch.heigvd.res.toolkit.pingpong.PingPongProtocolSerializer;

/**
 * This class only defines the main() method to start a server. The server uses
 * the components that deal with protocol-specific issues at different layers
 * (transport layer, syntactic layer, semantic layer).
 * 
 * @author Olivier Liechti
 */
public class ApplicationProtocolToolkit {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		//System.setProperty("java.util.logging.SimpleFormatter.format", "%2$s %n---> %5$s %n %n");
		System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s %n");
		
		// We will use a particular communication interface to interact with peers.
		// (the inteface may rely on TCP, UDP but maybe also on HTTP, E-MAIL, etc.)
		IInterfaceController interfaceController = new PingPongInterfaceController(PingPongProtocol.DEFAULT_PORT);

		// We will exchange "raw" serialized data on an interface. Therefore, we need 
		// a class to take care of the serialization/deserialization of this raw data
		// from/into application-level messages
		IProtocolSerializer protocolSerializer = new PingPongProtocolSerializer();
		
		// We use a protocol to communicate with other parties. We need a class to
		// be responsible for the semantics of the protocol (the class knows what
		// needs to be done when certain messages are received via a communication
		// interface
		IProtocolHandler protocolHandler = new PingPongProtocolHandler(protocolSerializer);
		
		// We need the inteface controller to be connected to the protocol handler,
		// so that messages arriving on the communication interface can be processed
		// by the protocol handler, and so that the results produced by the protocol
		// handler can be sent back via the interface controller
		interfaceController.registerProcotolHandler(protocolHandler);
		
		// We are ready, so let us start the interface controller and accept incoming
		// messages
		interfaceController.startup();
	}
	
}
