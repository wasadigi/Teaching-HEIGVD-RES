package ch.heigvd.res.labs.roulette.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A simple implementation of the IStudentStore contract. The data is managed
 * in memory (no persistent storage).
 * 
 * @author Olivier Liechti
 */
public class StudentsStoreImpl implements IStudentsStore {
	
	static final Logger LOG = Logger.getLogger(StudentsStoreImpl.class.getName());

	private final List<Student> students = new LinkedList<>();
	
	@Override
	public void clear() {
		students.clear();
	}

	@Override
	public void addStudent(Student student) {
		students.add(student);
	}

	@Override
	public List<Student> listStudents() {
		List<Student> result = new LinkedList<>(students);
		return result;
	}

	@Override
	public Student pickRandomStudent() throws EmptyStoreException {
		if (students.isEmpty()) {
			throw new EmptyStoreException();
		}
		int n = (int)(Math.random() * students.size());
		return students.get(n);
	}

	@Override
	public int getNumberOfStudents() {
		return students.size();
	}

	@Override
	public void importData(BufferedReader reader) throws IOException {
		LOG.log(Level.INFO, "Importing data from input reader of type {0}", reader.getClass());
		String record;
		boolean endReached = false;
		while ( !endReached && (record = reader.readLine()) != null) {
			if (record.equalsIgnoreCase("ENDOFDATA")) {
				LOG.log(Level.INFO, "End of stream reached. New students have been added to the store. How many? We'll tell you when the lab is complete...");
				endReached = true;
			} else {
				LOG.log(Level.INFO, "Adding student {0} to the store.", record);
				addStudent(new Student(record));
			}
		}
		LOG.log(Level.INFO, "There are now {0} students in the store.", getNumberOfStudents());
	}

}
