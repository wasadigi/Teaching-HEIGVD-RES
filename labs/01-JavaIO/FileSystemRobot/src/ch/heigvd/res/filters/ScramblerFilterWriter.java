package ch.heigvd.res.filters;

import java.io.FilterWriter;
import java.io.Writer;

/**
 * This class is used to automatically apply a transformation to all characters 
 * written to the wrapped Writer. The transformation consists of changing all
 * occurrences of 'a' and 'A' to '@', and all occurrences of 'e' and 'E' to '3'.
 * 
 * Writer writerConnectedToADestination;
 * ...
 * UppercaseFilterWriter wrappingWriter = new ScramblerFilterWriter(writerConnectedToADestination);
 * wrappingWriter.write(aString, off, len); 
 * wrappingWriter.write(aCharArray, off, len);
 * wrappingWriter.write(aDoubleByteCharacter);
 * 
 * @author Olivier Liechti
 */
public class ScramblerFilterWriter extends FilterWriter {

	public ScramblerFilterWriter(Writer out) {
		super(out);
	}	
	
}
