package ch.heigvd.res.filters;

import java.io.FilterWriter;
import java.io.Writer;

/**
 * This class is used to automatically apply a transformation to all characters 
 * written to the wrapped Writer. The transformation consists of setting the 
 * character to uppercase.
 * 
 * Writer writerConnectedToADestination;
 * ...
 * UppercaseFilterWriter wrappingWriter = new UpperCaseFilterWriter(writerConnectedToADestination);
 * wrappingWriter.write(aString, off, len); 
 * wrappingWriter.write(aCharArray, off, len);
 * wrappingWriter.write(aDoubleByteCharacter);
 * 
 * @author Olivier Liechti
 */
public class UppercaseFilterWriter extends FilterWriter {

	public UppercaseFilterWriter(Writer out) {
		super(out);
	}	
	
	
}
