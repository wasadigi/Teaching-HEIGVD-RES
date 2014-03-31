package ch.heigvd.res.toolkit.interfaces;

import ch.heigvd.res.toolkit.impl.Message;

/**
 * A context is associated to one and only one interface controller. A context
 * is associated to one and only one session.
 * 
 * @author Olivier Liechti
 */
public interface IContext {
	
	public long getSessionId();
	
	public void setStateMachine(IStateMachine stateMachine);
	
	public void sendMessage(Message m);
	
	public void closeSession();

}
