package ch.heigvd.res;

import ch.heigvd.res.impl.DFSFileSystemExplorer;
import ch.heigvd.res.impl.WeirdFileProcessor;
import ch.heigvd.res.interfaces.IFileProcessor;
import ch.heigvd.res.interfaces.IFileSystemExplorer;

/**
 * The main class for the application. We create a file system explorer, a
 * file processor and ask the explorer to invoke the processor for each file
 * it encounters during the traversal.
 * 
 * @author Olivier Liechti
 */
public class FileSystemRobot {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		
		IFileSystemExplorer explorer = new DFSFileSystemExplorer();
		IFileProcessor processor = new WeirdFileProcessor();
		
		/*
		Let's assume that we have a 'data' directory in the current working
		directory. We will explore this directory, consider all files that have a
		'.txt' extension and invoke our WeirdFileProcessor on each of them. The
		WeirdFileProcessor will do the text transformation and write results in
		new files with a ".out" extension.
		*/
		explorer.exploreFileSystem("./data", ".txt", processor);
	}
	
}
