package ch.heigvd.res.labs.roulette.data;

/**
 * A class that represents a student. We only use a single attribute to store
 * the full name of the student.
 * 
 * @author Olivier Liechti
 */
public class Student {
	
	private String fullname;

	public Student(String name) {
		this.fullname = name;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String name) {
		this.fullname = name;
	}

	@Override
	public String toString() {
		return "{'fullname' : '" + fullname + "'}";
	}
	
}
