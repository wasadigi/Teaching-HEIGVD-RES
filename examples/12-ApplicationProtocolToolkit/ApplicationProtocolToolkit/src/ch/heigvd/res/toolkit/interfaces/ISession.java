package ch.heigvd.res.toolkit.interfaces;

/**
 * This interface represents an application-level session, which defines a
 * bounded interaction between two components. Having an application-level
 * session does not necessarily mean that we are using a connection-oriented
 * transport protocol (e.g. TCP). For instance, if a datagram-oriented transport
 * protocol is used (UDP) and if the payload of datagrams contains a session id,
 * then it is also possible to manage application-level sessions.
 *
 * @author Olivier Liechti
 */
public interface ISession {

	/**
	 * Every session has a unique id
	 * @return the unique id for the session
	 */
	public long getSessionId();

}
