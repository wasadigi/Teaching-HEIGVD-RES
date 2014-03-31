package ch.heigvd.res.toolkit.impl;


import ch.heigvd.res.toolkit.interfaces.IMessageType;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * This class implements the state and behavior of a protocol message. It is
 * weakly typed, in the sense that it is associated with a map of key-value pairs.
 * When implementing a protocol, it is possible to store message attributes (status
 * code, payload, etc.) with key-value pairs. As an alternative, protocol implementations
 * may prefer to implement subclasses with explicit attributes (strong typing).
 * 
 * @author Olivier Liechti
 */
public class Message {
	
	final static Logger LOG = Logger.getLogger(Message.class.getName());

	private final IMessageType type;
	
	private final  Map<String, Object> attributes = new HashMap<>();

	public Message(IMessageType type) {
		this.type = type;
	}

	public IMessageType getType() {
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
		return "Message{" + "type=" + type + ", attributes=" + attributes + '}';
	}

	
}
