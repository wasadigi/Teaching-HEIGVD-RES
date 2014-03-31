package ch.heigvd.res.toolkit.pingpong;


import ch.heigvd.res.toolkit.impl.TcpLineInterfaceController;
import java.util.logging.Logger;

/**
 * This class defines an interface controller for the Ping Pong protocol. We use
 * the TCP line-by-line interface controller "as is", but have defined a class to
 * have a complete set of classes in the pingpong package (for clarity).
 * 
 * @author Olivier Liechti
 */
public class PingPongInterfaceController extends TcpLineInterfaceController {

	final static Logger LOG = Logger.getLogger(PingPongInterfaceController.class.getName());

	public PingPongInterfaceController(int port) {
		super(port);
	}

}
