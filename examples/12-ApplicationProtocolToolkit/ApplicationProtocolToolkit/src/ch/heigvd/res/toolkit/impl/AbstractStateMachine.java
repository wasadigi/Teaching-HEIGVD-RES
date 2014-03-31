package ch.heigvd.res.toolkit.impl;


import ch.heigvd.res.toolkit.interfaces.IContext;
import ch.heigvd.res.toolkit.interfaces.IState;
import ch.heigvd.res.toolkit.interfaces.IStateMachine;
import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 */
public abstract class AbstractStateMachine implements IStateMachine {

	final static Logger LOG = Logger.getLogger(AbstractStateMachine.class.getName());

	private final IContext context;
	
	private IState currentState;
	
	private enum State implements IState {
		INITIAL_STATE;
	}
	
	public AbstractStateMachine(IContext context) {
		this.context = context;
	}

	@Override
	public final void init() {
		triggerTransitionToState(getInitialState());
	}
	
	//@Override
	public final IContext getContext() {
		return context;
	}

	public abstract IState getInitialState();
	
	//@Override
	public final IState getCurrentState() {
		return currentState;
	}
	
	public final void triggerTransitionToState(IState state) {
		onStateExit(currentState);
		currentState = state;
		onStateEntered(currentState);
	}

	@Override
	public void onStateEntered(IState state) {
		LOG.info("State machine has transitioned into state " + state + " for session " + getContext().getSessionId());
	}

	@Override
	public void onStateExit(IState state) {
		LOG.info("State machine has transitioned out of state " + state + " for session " + getContext().getSessionId());
	}

}
