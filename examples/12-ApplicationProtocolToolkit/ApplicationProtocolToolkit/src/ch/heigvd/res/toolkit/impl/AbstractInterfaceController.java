package ch.heigvd.res.toolkit.impl;

import ch.heigvd.res.toolkit.interfaces.IInterfaceController;
import ch.heigvd.res.toolkit.interfaces.IProtocolHandler;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Responsibilities:
 * - manage transport-level interfaces
 * - detect session start and end
 * - manage sessions
 * - create a context object and pass it to the protocol handler
 * 
 * @author Olivier Liechti
 */
public abstract class AbstractInterfaceController implements IInterfaceController {

	final static Logger LOG = Logger.getLogger(AbstractInterfaceController.class.getName());
	
	private final Map<Long, Session> sessions = new HashMap();
	
	private IProtocolHandler protocolHandler;

	
	public final Session createSession() {
		Session newSession = new Session();
		long sessionId = newSession.getSessionId();
		sessions.put(sessionId, newSession);
		return newSession;
	}
	
	public final void startSession(long sessionId) {
		Context newContext = new Context(sessionId, this);
		protocolHandler.onSessionStarted(sessionId, newContext);
		LOG.log(Level.INFO, "Starting a new session in interface controller: {0}, session id: {1}", new Object[]{this, sessionId});
	}

	@Override
	public final void registerProcotolHandler(IProtocolHandler protocolHandler) {
		this.protocolHandler = protocolHandler;
	}

	@Override
	public IProtocolHandler getProtocolHandler() {
		return protocolHandler;
	}
	
	@Override
	public void closeSession(long sessionId) {
		sessions.remove(sessionId);
		protocolHandler.onSessionClosed(sessionId);
	}

	public final void onMessage(long sessionId, Message message) {
		protocolHandler.onMessage(sessionId, message);
	}
	
	public final void onInvalidMessage(long sessionId, InvalidMessageException e) {
		protocolHandler.onInvalidMessage(sessionId, e);
	}

	@Override
	public abstract void sendMessage(long sessionId, Message message);

	@Override
	public String toString() {
		return "InterfaceController{" + "active sessions:" + sessions.size() + ", class=" + getClass() + '}';
	}
	
}
