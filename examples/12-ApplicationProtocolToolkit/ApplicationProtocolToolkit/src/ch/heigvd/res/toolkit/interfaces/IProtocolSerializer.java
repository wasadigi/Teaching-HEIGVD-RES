package ch.heigvd.res.toolkit.interfaces;

import ch.heigvd.res.toolkit.impl.InvalidMessageException;
import ch.heigvd.res.toolkit.impl.Message;

/**
 * This interface defines the functionality of protocol de-/serializers, which
 * essentially consists of transforming raw data into application-level messages,
 * and vice-versa. 
 * 
 * Implementations of this interface need to know the structure of application-level 
 * messages defined in a particular protocol (i.e. what are the attributes / fields
 * defined in the protocol specification). They also need to know how the messages
 * are encoded (binary formats, textual formats such as JSON or XML, etc.).
 * 
 * @author Olivier Liechti
 */
public interface IProtocolSerializer {
	
	/**
	 * This method converts raw data (obtained at the transport level) into
	 * an application-level message. This is used for incoming messages.
	 * 
	 * @param data raw data
	 * @return the corresponding application-level message
	 * @throws ch.heigvd.res.toolkit.impl.InvalidMessageException
	 */
	public Message deserialize(byte[] data) throws InvalidMessageException;
	
	/**
	 * This method converts an application-level message into wire-level data.
	 * This is used for outgoing messages.
	 * 
	 * @param message an application-level message
	 * @return the corresponding raw data
	 */
	public byte[] serialize(Message message);

}
