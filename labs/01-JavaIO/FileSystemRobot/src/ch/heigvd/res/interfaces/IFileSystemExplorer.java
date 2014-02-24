package ch.heigvd.res.interfaces;

/**
 * Classes that implement this interface should traverse the file system, starting
 * at the "startinPoint" root directory. It is up to the class to define the
 * traversal algorithm (such as depth-first, breadth-first, etc.). For each
 * file encountered during the traversal, a processor provided by the client will
 * be invoked and given the opportunity to do whatever it wants with the file.
 *
 * @author Olivier Liechti
 */
public interface IFileSystemExplorer {
	
	/**
	 * This method starts the exploration of the file system. The implementation is
	 * responsible for going through all the files in the file system and to invoke
	 * a method on the passed processor object
	 * 
	 * @param startingPoint the root directory where to start the exploration
	 * @param extension only files with a name ending with this string will be processed
	 * @param processor the method processFile of this argument will be invoked for every encountered file
	 */
	public void exploreFileSystem(String startingPoint, String extension, IFileProcessor processor);
	
}
