package ch.heigvd.res.toolkit.interfaces;

import ch.heigvd.res.toolkit.impl.Event;

/**
 *
 * @author Olivier Liechti
 */
public interface IStateMachine {
		
	/**
	 * This method must be called after the state machine has been registered by
	 * the ProtocolHandler
	 */
	public void init();
	
	/**
	 * This callback is invoked when an event has been notified. There are different
	 * types of events: the reception of a protocol message, the signal from a timer
	 * 
	 * @param event 
	 */
	public void onEvent(Event event);
	
	/**
	 * This callback is invoked after the state machine has transitioned into a new
	 * state.
	 * @param state the new state for the state machine 
	 */
	public void onStateEntered(IState state);
	
	/**
	 * This callback is invoked before the state machine transitions into a new
	 * state.
	 * @param state the old state for the state machine 
	 */
	public void onStateExit(IState state);

	/**
	 * This method is called after the state machine has been unregistered by
	 * the ProtocolHandler. Used to destroy resources and stop timers.
	 */
	public void destroy();
	
}
