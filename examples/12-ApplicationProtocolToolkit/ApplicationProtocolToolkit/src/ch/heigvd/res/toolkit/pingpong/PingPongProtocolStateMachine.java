package ch.heigvd.res.toolkit.pingpong;

import ch.heigvd.res.toolkit.interfaces.IContext;
import ch.heigvd.res.toolkit.interfaces.IState;
import ch.heigvd.res.toolkit.interfaces.IStateMachine;
import ch.heigvd.res.toolkit.impl.Event;
import ch.heigvd.res.toolkit.impl.Message;
import ch.heigvd.res.toolkit.impl.AbstractStateMachine;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class implements the state machine for the Ping Pong protocol.
 *
 * @author Olivier Liechti
 */
public class PingPongProtocolStateMachine extends AbstractStateMachine implements IStateMachine {

	final static Logger LOG = Logger.getLogger(PingPongProtocolStateMachine.class.getName());

	/**
	 * This object is used to detect that the client has been inactive for too long
	 */
	private final InactivityGuard inactivityGuard = new InactivityGuard();

	/**
	 * These three fields are part of the state for the state machine, i.e. their
	 * value evolves in reaction to events and state transitions
	 */
	private int successCount = 0;
	private int missedCount = 0;
	private int lateCount = 0;

	/**
	 * This class detects when the client has not sent valid commands for too long
	 */
	private class InactivityGuard {

		private Timer timer;

		private long lastClientActivityTime;

		public void start() {
			lastClientActivityTime = System.currentTimeMillis();
			timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					Event maxIdleTimeReachedEvent = new Event(PingPongProtocol.EventType.EVENT_TYPE_MAX_IDLE_TIME_REACHED);
					PingPongProtocolStateMachine.this.onEvent(maxIdleTimeReachedEvent);
				}
			}, PingPongProtocol.MAX_INACTIVE_TIMEOUT, PingPongProtocol.MAX_INACTIVE_TIMEOUT);
		}

		public void stop() {
			if (timer != null) {
				timer.cancel();
			}
		}

		public void notifyClientActivity() {
			if (timer != null) {
				timer.cancel();
			}
			start();
		}
	}

	public PingPongProtocolStateMachine(IContext context) {
		super(context);
	}

	@Override
	public void destroy() {
		inactivityGuard.stop();
	}

	@Override
	public IState getInitialState() {
		return PingPongProtocol.State.STATE_START;
	}

	@Override
	public void onStateEntered(IState state) {
		super.onStateEntered(state);

		/*
		 When we enter STATE_START, we send a notification to the client to say hello. We
		 then immediately transition to STATE_PING, as per protocol specification.
		 */
		if (state == PingPongProtocol.State.STATE_START) {
			Message msg = new Message(PingPongProtocol.MessageType.MESSAGE_TYPE_NOTIFICATION);
			msg.setAttribute("payload", PingPongProtocol.NOTIFICATION_WELCOME_TEXT);
			getContext().sendMessage(msg);
			triggerTransitionToState(PingPongProtocol.State.STATE_PING);
			inactivityGuard.notifyClientActivity();
		}

		/*
		 When we enter STATE_END, we send a notification to inform the client that we are
		 closing the connection. We then close the connection.
		 */
		if (state == PingPongProtocol.State.STATE_END) {
			inactivityGuard.stop();
			Message msg = new Message(PingPongProtocol.MessageType.MESSAGE_TYPE_NOTIFICATION);
			msg.setAttribute("payload", PingPongProtocol.NOTIFICATION_CLOSING_TEXT);
			getContext().sendMessage(msg);
			getContext().closeSession();
		}
	}

	@Override
	public void onEvent(Event e) {
		LOG.log(Level.INFO, "State Machine has received an event: {0} - {1}", new Object[]{e.getType(), e});

		if (e.getType() == PingPongProtocol.EventType.EVENT_TYPE_MAX_IDLE_TIME_REACHED) {
			Message timerNotification = new Message(PingPongProtocol.MessageType.MESSAGE_TYPE_NOTIFICATION);
			timerNotification.setAttribute("payload", PingPongProtocol.NOTIFICATION_IDLE_TEXT);
			getContext().sendMessage(timerNotification);
			lateCount++;
			return;
		}

		// If we have received an invalid message from the client we return an error message
		if (e.getType() == Event.EventType.EVENT_TYPE_INVALID_MESSAGE_ARRIVED) {
			Message reply = new Message(PingPongProtocol.MessageType.MESSAGE_TYPE_RESULT);
			reply.setAttribute("statusCode", 400);
			reply.setAttribute("payload", PingPongProtocol.RESULT_INVALID_CMD_TEXT);
			getContext().sendMessage(reply);
			return;
		}

		// If we have received a message from the client, we have to update the last activity timestamp
		if (e.getType() == Event.EventType.EVENT_TYPE_MESSAGE_ARRIVED) {
			inactivityGuard.notifyClientActivity();
		}

		// Whatever the state, if we receive a BYE command, we know what to do
		if (e.getType() == Event.EventType.EVENT_TYPE_MESSAGE_ARRIVED) {
			Message incomingMessage = (Message) (e.getAttribute("message"));
			String command = (String) incomingMessage.getAttribute("command");
			if (PingPongProtocol.Command.CMD_BYE.getKeyword().equals(command)) {
				Message lastMessage = new Message(PingPongProtocol.MessageType.MESSAGE_TYPE_RESULT);
				lastMessage.setAttribute("payload", PingPongProtocol.RESULT_BYE_TEXT);
				getContext().sendMessage(lastMessage);
				triggerTransitionToState(PingPongProtocol.State.STATE_END);
				return;
			}
		}

		// Whatever the state, if we receive a SCORE command, we know what to do
		if (e.getType() == Event.EventType.EVENT_TYPE_MESSAGE_ARRIVED) {
			Message incomingMessage = (Message) (e.getAttribute("message"));
			String command = (String) incomingMessage.getAttribute("command");
			if (PingPongProtocol.Command.CMD_SCORE.getKeyword().equals(command)) {
				Message scoreMessage = new Message(PingPongProtocol.MessageType.MESSAGE_TYPE_RESULT);
				StringBuilder scoreMessagePayload = new StringBuilder();
				scoreMessagePayload.append("Missed: ")
								.append(missedCount)
								.append(" Successful: ")
								.append(successCount)
								.append(" Late: ")
								.append(lateCount);

				scoreMessage.setAttribute("statusCode", "200");
				scoreMessage.setAttribute("payload", scoreMessagePayload.toString());
				getContext().sendMessage(scoreMessage);
				return;
			}
		}

		// Whatever the state, if we receive a HELP command, we know what to do
		if (e.getType() == Event.EventType.EVENT_TYPE_MESSAGE_ARRIVED) {
			Message incomingMessage = (Message) (e.getAttribute("message"));
			String command = (String) incomingMessage.getAttribute("command");
			if (PingPongProtocol.Command.CMD_HELP.getKeyword().equals(command)) {
				Message message = new Message(PingPongProtocol.MessageType.MESSAGE_TYPE_RESULT);

				StringBuilder helpMessagePayload = new StringBuilder("You can use the following commands: ");
				for (PingPongProtocol.Command cmd : PingPongProtocol.Command.values()) {
					helpMessagePayload.append("['")
									.append(cmd.getKeyword())
									.append("': ")
									.append(cmd.getHelp())
									.append("] ");
				}
				message.setAttribute("statusCode", "200");
				message.setAttribute("payload", helpMessagePayload.toString());
				getContext().sendMessage(message);
				return;
			}
		}

		switch ((PingPongProtocol.State) getCurrentState()) {
			case STATE_PING:
				onEventInStatePingOrPong(e);
				break;
			case STATE_PONG:
				onEventInStatePingOrPong(e);
				break;
		}

	}

	/**
	 * This method handles events when the state machine is either in STATE_PING or STATE_PONG
	 * @param e the event
	 */
	private void onEventInStatePingOrPong(Event e) {
		switch ((Event.EventType) e.getType()) {
			case EVENT_TYPE_MESSAGE_ARRIVED:
				Message reply = new Message(PingPongProtocol.MessageType.MESSAGE_TYPE_RESULT);
				Message request = (Message) e.getAttribute("message");
				String command = (String) request.getAttribute("command");

				boolean successful;
				IState targetState;
				
				if (getCurrentState() == PingPongProtocol.State.STATE_PING) {
					successful = (command.equals(PingPongProtocol.Command.CMD_PING.getKeyword()));
					targetState = PingPongProtocol.State.STATE_PONG;
				} else {
					successful = (command.equals(PingPongProtocol.Command.CMD_PONG.getKeyword()));
					targetState = PingPongProtocol.State.STATE_PING;
				}

				if (successful) {
					successCount++;
					reply.setAttribute("statusCode", "200");
					reply.setAttribute("payload", PingPongProtocol.RESULT_SUCCESS_TEXT);
					getContext().sendMessage(reply);
					triggerTransitionToState(targetState);
				} else {
					missedCount++;
					reply.setAttribute("statusCode", "200");
					reply.setAttribute("payload", PingPongProtocol.RESULT_FAIL_TEXT);
					getContext().sendMessage(reply);
				}
				break;
		}
	}

}
