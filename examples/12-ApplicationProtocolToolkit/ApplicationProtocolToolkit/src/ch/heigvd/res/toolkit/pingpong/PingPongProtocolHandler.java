package ch.heigvd.res.toolkit.pingpong;


import ch.heigvd.res.toolkit.interfaces.IContext;
import ch.heigvd.res.toolkit.interfaces.IProtocolSerializer;
import ch.heigvd.res.toolkit.interfaces.IStateMachine;
import ch.heigvd.res.toolkit.impl.AbstractProtocolHandler;
import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 */
public class PingPongProtocolHandler extends AbstractProtocolHandler {

	final static Logger LOG = Logger.getLogger(PingPongProtocolHandler.class.getName());

	public PingPongProtocolHandler(IProtocolSerializer protocolSerializer) {
		super(protocolSerializer);
	}

	@Override
	public IStateMachine getProtocolStateMachine(IContext context) {
		return new PingPongProtocolStateMachine(context);
	}

}
