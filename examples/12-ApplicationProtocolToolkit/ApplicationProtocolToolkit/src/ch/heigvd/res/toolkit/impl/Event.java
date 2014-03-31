package ch.heigvd.res.toolkit.impl;


import ch.heigvd.res.toolkit.interfaces.IEventType;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 */
public class Event {
	
	public enum EventType implements IEventType {
		EVENT_TYPE_MESSAGE_ARRIVED,
		EVENT_TYPE_INVALID_MESSAGE_ARRIVED;
	}

	final static Logger LOG = Logger.getLogger(Event.class.getName());

	private final IEventType type;
	
	private final  Map<String, Object> attributes = new HashMap<>();

	public Event(IEventType type) {
		this.type = type;
	}
	
	public IEventType getType() {
		return type;
	}
	
	public Object getAttribute(String key) {
		return attributes.get(key);
	}
	
	public void setAttribute(String key, Object value) {
		attributes.put(key, value);
	}

	@Override
	public String toString() {
		return "Event{" + "type=" + type + ", attributes=" + attributes + '}';
	}
	
}
