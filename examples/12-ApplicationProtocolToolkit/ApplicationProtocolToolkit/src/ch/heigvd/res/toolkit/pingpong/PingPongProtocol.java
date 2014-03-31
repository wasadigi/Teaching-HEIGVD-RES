package ch.heigvd.res.toolkit.pingpong;

import ch.heigvd.res.toolkit.interfaces.IEventType;
import ch.heigvd.res.toolkit.interfaces.IMessageType;
import ch.heigvd.res.toolkit.interfaces.IState;

/**
 * This class is used to define constants and enum types specific to the
 * Ping Pong protocol.
 *
 * @author Olivier Liechti
 */
public class PingPongProtocol {

	/**
	 * The default TCP port on which the server is accepting connection requests
	 */
	public final static int DEFAULT_PORT = 2904;
	
	/**
	 * The maximum inactivity period, after which a notification will be sent
	 * to the client
	 */
	public final static long MAX_INACTIVE_TIMEOUT = 3000;

	/**
	 * This enum type defines the possible states for the Ping Pong prococol
	 * state machine
	 */
	public enum State implements IState {
		STATE_START,
		STATE_PING, 
		STATE_PONG, 
		STATE_END;
	}
	
	/**
	 * This enum type defines the specific event types understood by the Ping Pong
	 * protocol state machine. Note that there is another, generic enum type defined
	 * in ch.heigvd.res.toolkit.impl.EventType
	 */
	public enum EventType implements IEventType {
		EVENT_TYPE_MAX_IDLE_TIME_REACHED;		
	}
	
	/**
	 * This enum type defines the protocol commands defined by the Ping Pong protocol
	 * The commands are encapsulated in messages
	 */
	public enum Command {
		CMD_PING("ping", "Return the ball when the session is in Pong state."),
		CMD_PONG("pong", "Return the ball when the session is in Ping state."),
		CMD_SCORE("score", "Get the current score."),
		CMD_BYE("bye", "End the game and close the session."),
		CMD_HELP("help", "Display help message.");
		
		private final String keyword;
		private final String help;
		
		Command(String keyword, String help) {
			this.keyword = keyword;
			this.help = help;
		}

		public String getKeyword() {
			return keyword;
		}

		public String getHelp() {
			return help;
		}
		
	}

	/**
	 * This enum type defines the types of messages that can be exchanged by
	 * clients and servers using the Ping Pong protocol. A "command" is a message
	 * sent by a client to a server. A "result" is a message sent by a server to
	 * a client, in response to a previous "command". A "notification" is a message
	 * sent by a server to a client (without a previous "command")
	 */
	public enum MessageType implements IMessageType {
		MESSAGE_TYPE_COMMAND,
		MESSAGE_TYPE_RESULT,
		MESSAGE_TYPE_NOTIFICATION
	}
	
	public static String NOTIFICATION_WELCOME_TEXT = "Welcome to the Ping Pong Server. Do you want to ping?";
	public static String NOTIFICATION_IDLE_TEXT = "You have been inactive for too long!";
	public static String NOTIFICATION_BYE_TEXT = "bye bye, nice playing with you";
	public static String NOTIFICATION_CLOSING_TEXT = "closing the connection...";
	
	public static String RESULT_SUCCESS_TEXT = "Nice one!";
	public static String RESULT_FAIL_TEXT = "Ooops...";
	public static String RESULT_BYE_TEXT = "bye bye, nice playing with you";
	public static String RESULT_INVALID_CMD_TEXT = "Invalid command";
	
	public static String MESSAGE_ATTRIBUTE_PAYLOAD = "payload";
	public static String MESSAGE_ATTRIBUTE_STATUS_CODE = "statusCode";

}
