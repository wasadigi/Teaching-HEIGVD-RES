package ch.heigvd.res.labs.roulette.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

/**
 * This interface defines the contract to be fulfilled by the classes that
 * manage student data, whether in memory or persistent storage.
 * 
 * @author Olivier Liechti
 */
public interface IStudentsStore {
	
	/**
	 * This method clears the data store by deleting all of the content
	 */
	public void clear();
	
	/**
	 * This method is used to add a student to the data store
	 * @param student the student to add
	 */
	public void addStudent(Student student);
	
	/**
	 * This method is used to get the list of students currently in the data store
	 * @return the list of students int the data store
	 */
	public List<Student> listStudents();
	
	/**
	 * This method is used to randomly select one student in the data store
	 * @return a student randomly selected in the data store
	 * @throws ch.heigvd.res.labs.data.EmptyStoreException when the store is empty
	 */
	public Student pickRandomStudent() throws EmptyStoreException;
	
	/**
	 * This method returns the number of students currently in the data store
	 * @return the current number of students in the data store
	 */
	public int getNumberOfStudents();
	
	/**
	 * This method is used to import students, by consuming lines from the
	 * BufferedReader passed in argument. Data is read line by line and the whole
	 * line is used as the student full name.
	 * 
	 * @param reader where we read student data from
	 * @throws IOException 
	 */
	public void importData(BufferedReader reader) throws IOException ;

}
