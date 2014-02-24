package ch.heigvd.res.interfaces;

import java.io.File;

/**
 * Classes that implement this interface do some processing on a file. For instance,
 * a processor could count the number of words in a file. Another processor could
 * generate a copy of the file, changing all characters to uppercase.
 * 
 * @author Olivier Liechti
 */
public interface IFileProcessor {
	
	/**
	 * This method does some processing on the file passed in parameter
	 * @param file the file to process
	 */
	public void processFile(File file);
	
}
