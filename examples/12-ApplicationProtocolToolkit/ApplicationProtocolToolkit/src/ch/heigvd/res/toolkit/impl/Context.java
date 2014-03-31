package ch.heigvd.res.toolkit.impl;


import ch.heigvd.res.toolkit.interfaces.IContext;
import ch.heigvd.res.toolkit.interfaces.IStateMachine;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 */
public class Context implements IContext  {

	final static Logger LOG = Logger.getLogger(Context.class.getName());
	
	/**
	 * Every context is associated to one and only one session
	 */
	private final long sessionId;

	/**
	 * We need an interface handler to be able to send back messages to the other
	 * parties.
	 */
	private final AbstractInterfaceController interfaceHandler;
	
	private IStateMachine stateMachine;
	
	public Context(long sessionId, AbstractInterfaceController interfaceHandler) {
		this.interfaceHandler = interfaceHandler;
		this.sessionId = sessionId;
	}

	@Override
	public long getSessionId() {
		return sessionId;
	}

	@Override
	public void setStateMachine(IStateMachine stateMachine) {
		this.stateMachine = stateMachine;
	}
	
	@Override
	public void sendMessage(Message message) {
		LOG.log(Level.INFO, "Sending a message with context object: {0}", this);
		interfaceHandler.sendMessage(sessionId, message);
	}
		
	@Override
	public void closeSession() {
		interfaceHandler.closeSession(sessionId);
	}

	@Override
	public String toString() {
		return "Context{" + "sessionId=" + sessionId + '}';
	}

}
