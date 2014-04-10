package ch.heigvd.res.http;

/**
 *
 * @author Olivier Liechti
 */
public class HTTP {

	public enum StatusCode {
		SUCCESS(200),
		MOVED_PERMANENTLY(301),
		FOUND(302),
		NOT_MODIFIED(304),
		BAD_REQUEST(400),
		NOT_FOUND(404);
		
		private final int value;
		
		StatusCode(int value) {
			this.value = value;
		}
		
		public int getValue() {
			return value;
		}
		
		public static StatusCode valueOf(int code) {
			for(StatusCode c: values()) {
				if (c.value == code) {
					return c;
				}
			}
			return null;
		}
		
	}
	
	

}
