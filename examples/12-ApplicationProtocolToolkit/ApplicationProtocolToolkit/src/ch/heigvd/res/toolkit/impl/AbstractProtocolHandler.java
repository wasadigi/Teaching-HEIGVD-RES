package ch.heigvd.res.toolkit.impl;


import ch.heigvd.res.toolkit.interfaces.IContext;
import ch.heigvd.res.toolkit.interfaces.IProtocolHandler;
import ch.heigvd.res.toolkit.interfaces.IProtocolSerializer;
import ch.heigvd.res.toolkit.interfaces.IStateMachine;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 */
public abstract class AbstractProtocolHandler implements IProtocolHandler {

	final static Logger LOG = Logger.getLogger(AbstractProtocolHandler.class.getName());
	
	private final Map<Long, IStateMachine> stateMachines = new HashMap<>();
	
	private final IProtocolSerializer protocolSerializer;

	public AbstractProtocolHandler(IProtocolSerializer protocolSerializer) {
		this.protocolSerializer = protocolSerializer;
	}
	
	@Override
	public final void onSessionStarted(long sessionId, IContext context) {
		LOG.log(Level.INFO, "onSessionStarted called in {0}", getClass());
		IStateMachine sm = getProtocolStateMachine(context);
		context.setStateMachine(sm);
		stateMachines.put(sessionId, sm);
		sm.init();
	}

	@Override
	public final void onSessionClosed(long sessionId) {
		IStateMachine sm = (IStateMachine)stateMachines.get(sessionId);
		sm.destroy();
		stateMachines.remove(sessionId);
	}

	@Override
	public final void onMessage(long sessionId, Message m) {
		IStateMachine sm = stateMachines.get(sessionId);
		Event messageArrivedEvent = new Event(Event.EventType.EVENT_TYPE_MESSAGE_ARRIVED);
		messageArrivedEvent.setAttribute("message", m);
		sm.onEvent(messageArrivedEvent);
	}

	@Override
	public void onInvalidMessage(long sessionId, InvalidMessageException e) {
		IStateMachine sm = stateMachines.get(sessionId);
		Event invalidMessageArrivedEvent = new Event(Event.EventType.EVENT_TYPE_INVALID_MESSAGE_ARRIVED);
		invalidMessageArrivedEvent.setAttribute("error", e);
		sm.onEvent(invalidMessageArrivedEvent);
	}

	@Override
	public IProtocolSerializer getProtocolSerializer() {
		return protocolSerializer;
	}
	
	
	public abstract IStateMachine getProtocolStateMachine(IContext context);

}
