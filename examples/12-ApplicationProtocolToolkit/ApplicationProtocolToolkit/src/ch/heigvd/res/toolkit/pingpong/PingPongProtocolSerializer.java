package ch.heigvd.res.toolkit.pingpong;


import ch.heigvd.res.toolkit.impl.InvalidMessageException;
import ch.heigvd.res.toolkit.interfaces.IProtocolSerializer;
import ch.heigvd.res.toolkit.impl.Message;
import ch.heigvd.res.toolkit.pingpong.PingPongProtocol.Command;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class handles the serialization/deserialization process for the Ping Pong
 * protocol. This is where we implement syntax-level considerations defined in the
 * Ping Pong protocol specification.
 * 
 * @author Olivier Liechti
 */
public class PingPongProtocolSerializer implements IProtocolSerializer {

	final static Logger LOG = Logger.getLogger(PingPongProtocolSerializer.class.getName());

	@Override
	public Message deserialize(byte[] data) throws InvalidMessageException {
		LOG.info("Deserializing transport-level data into application-level message");
		String sData = new String(data);
		String[] tokens = sData.split(" ");
		List<String> arguments = new LinkedList<>();
		for (int i=1; i<tokens.length; i++) {
			arguments.add(tokens[i]);
		}
		
		Message message = new Message(PingPongProtocol.MessageType.MESSAGE_TYPE_COMMAND);
		message.setAttribute("command", tokens[0]);
		message.setAttribute("arguments", arguments);				
		message.setAttribute("payload", new String(data));
		
		boolean validCommand = false;
		for (Command protocolCommand: PingPongProtocol.Command.values()) {
			if (protocolCommand.getKeyword().equals(tokens[0])) {
				validCommand = true;
				break;
			}
		}
		if (!validCommand) {
			throw new InvalidMessageException();
		}

		LOG.log(Level.INFO, "-->  From: {0}", new String(data));
		LOG.log(Level.INFO, "-->    To: {0}", message);
		
		return message;
	}

	@Override
	public byte[] serialize(Message message) {
		LOG.info("Serializing application-level message into transport-level data");
		
		StringBuilder sb = new StringBuilder();
		
		switch ((PingPongProtocol.MessageType)message.getType()) {
			case MESSAGE_TYPE_RESULT:
				sb.append("[RESULT]: ");
				sb.append(message.getAttribute("statusCode"));
				sb.append(" ");
				sb.append(message.getAttribute("payload"));
				break;
			case MESSAGE_TYPE_NOTIFICATION:
				sb.append("[NOTIFY]: ");
				sb.append(message.getAttribute("payload"));
				break;
		}
		byte[] data = sb.toString().getBytes(); 
		LOG.log(Level.INFO, "-->  From: {0}", message);
		LOG.log(Level.INFO, "-->    To: {0}", new String(data));
		return data;
	}

}
