package ch.heigvd.res.toolkit.interfaces;

import ch.heigvd.res.toolkit.impl.InvalidMessageException;
import ch.heigvd.res.toolkit.impl.Message;

/**
 * This interface defines a contract between the transport-level layer (interface
 * controller) and the application-level layer (state machine). Implementations of
 * this interface will be notified via callbacks when "interesting" events are
 * fired on the interface controller (e.g. a message has arrived, a new session
 * has started, a session has been closed, etc.).
 * 
 * @author Olivier Liechti
 */
public interface IProtocolHandler {
	
	/**
	 * This callback is invoked when the interface controller has detected that
	 * a new session has been started. This could be the case when a TCP connection
	 * has been established. This could also be the case if a datagram has arrived,
	 * that its payload contains session id and that this session id does not 
	 * identify any existing session.
	 * 
	 * @param sessionId the id of the new session
	 * @param context a context object, which will be passed to the state machine
	 * so that it can send back messages via the interface controller
	 */
	public void onSessionStarted(long sessionId, IContext context);
	
	/**
	 * This callback is invoked when the interface controller has detected that
	 * a session has been closed. This might be the case because of explicit
	 * events (e.g. termination of a TCP connection) or because of timing considerations
	 * (e.g. a session has been inactive for too long).
	 * 
	 * @param sessionId the id of the session that has been closed
	 */
	public void onSessionClosed(long sessionId);
	
	/**
	 * This callback is invoked when a new message has arrived and needs to be 
	 * processed (which typically means notifying the protocol state machine)
	 * 
	 * @param sessionId the session to which the message belongs
	 * @param message the incoming message
	 */
	public void onMessage(long sessionId, Message message);
	
	/**
	 * This callback is invoked when data has arrived but cannot be deserialized
	 * into a valid protocol message
	 * 
	 * @param sessionId the id of the session on which the data has arrived
	 * @param e the exception thrown during the deserialization process
	 */
	public void onInvalidMessage(long sessionId, InvalidMessageException e);

	/**
	 * One responsibility of the classes implementing this contract is to provide
	 * the protocol-specific class that is responsible for convert wire-level data
	 * into application-level messages, and vice versa
	 * 
	 * @return a protocol specific implementation of the IProtocolSerializer interface
	 */
	public IProtocolSerializer getProtocolSerializer();

}
