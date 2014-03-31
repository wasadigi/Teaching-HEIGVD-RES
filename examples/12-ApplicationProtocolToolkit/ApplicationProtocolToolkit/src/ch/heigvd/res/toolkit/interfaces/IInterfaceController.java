package ch.heigvd.res.toolkit.interfaces;

import ch.heigvd.res.toolkit.impl.Message;

/**
 * This interface defines the contract fulfilled by interface controllers, which
 * have the responsibility to deal with transport-level issues and to manage
 * sessions.
 * 
 * @author Olivier Liechti
 */
public interface IInterfaceController {
	
	/**
	 * This method is used to start the interface controller
	 */
	public void startup();
	
	/**
	 * This method is used to connect a protocol handler with the interface
	 * controller. As a result, when data arrives on the interface, the controller
	 * will ask the protocol handler to provide a message deserializer. Once it
	 * has obtained a protocol message from the raw data, it will pass it to
	 * the protocol handler
	 * 
	 * @param handler the protocol handler
	 */
	public void registerProcotolHandler(IProtocolHandler handler);
	
	/**
	 * This method is used to get the registered protocol handler
	 * @return the protocol handler connected with the interface controller
	 */
	public IProtocolHandler getProtocolHandler();
		
	/**
	 * This method is used to send a protocol message to a client, in the context
	 * of a particular session.
	 * 
	 * @param sessionId the id of the session to which the message belongs
	 * @param m the message to send
	 */
	public void sendMessage(long sessionId, Message m);
	
	/**
	 * This method is used to close a session
	 * 
	 * @param sessionId the id of the session to close
	 */
	public void closeSession(long sessionId);
	
}
