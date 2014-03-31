package ch.heigvd.res.toolkit.impl;

import ch.heigvd.res.toolkit.interfaces.ISession;

/**
 *
 * @author Olivier Liechti
 */
public class Session implements ISession {

	private static long sessionIdCounter = 10000;

	private final long sessionId;
	
	private final long startTime;
	
	
	public Session() {
		sessionIdCounter++;
		sessionId = sessionIdCounter;
		startTime = System.currentTimeMillis();
	}

	@Override
	public long getSessionId() {
		return sessionId;
	}	

	@Override
	public String toString() {
		return "Session{" + "sessionId=" + sessionId + '}';
	}

}
