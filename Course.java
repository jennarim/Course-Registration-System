import java.util.ArrayList;
import java.util.List;

/* 
 * Course class represents a course with several information such as name, id, section, and students.
 * 
 * Assumptions:
 * - no two courses have the same name AND the same section number
 */
public class Course implements Comparable<Course>, java.io.Serializable {
	private String name;
	private String id;
	private int maxStudents;
	private int numOfStudents;
	private ArrayList<Student> currentStudents;
	private String instructorName;
	private int sectionNumber;
	private String courseLocation;
	private static final long serialVersionUID = 4651480552078461252L;
	
	/* Default Constructor */
	Course() {
		this("N/A","N/A",0,0,"N/A",0,"N/A");
	}

	/* Constructor that takes in course attributes */
	public Course(String name, String id, int maxStudents, int numOfStudents, String instructorName, int sectionNumber, String courseLocation) {
		this.name = name;
		this.id = id;
		this.maxStudents = maxStudents;
		this.numOfStudents = 0;
		this.instructorName = instructorName;
		this.sectionNumber = sectionNumber;
		this.courseLocation = courseLocation;
		currentStudents = new ArrayList<Student>();
	}

	/* Getters and Setters */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getNumOfStudents() {
		return numOfStudents;
	}

	public void setNumOfStudents(int numOfStudents) {
		this.numOfStudents = numOfStudents;
	}

	public int getMaxStudents() {
		return maxStudents;
	}

	public void setMaxStudents(int maxStudents) {
		this.maxStudents = maxStudents;
	}

	public ArrayList<Student> getCurrentStudents() {
		return currentStudents;
	}

	public void setCurrentStudents(ArrayList<Student> currentStudents) {
		this.currentStudents = currentStudents;
	}

	public String getInstructorName() {
		return instructorName;
	}

	public void setInstructorName(String instructorName) {
		this.instructorName = instructorName;
	}

	public int getSectionNumber() {
		return sectionNumber;
	}

	public void setSectionNumber(int sectionNumber) {
		this.sectionNumber = sectionNumber;
	}
	
	public String getCourseLocation() {
		return courseLocation;
	}

	public void setCourseLocation(String courseLocation) {
		this.courseLocation = courseLocation;
	}
	
	public String toString() {
		return "\'" + name + "\'" + " Section " + sectionNumber + " " + id + " is taught by " 
				+ instructorName + " at " + courseLocation + ", and currently has "
				+ numOfStudents + " students out of a capacity of " + maxStudents;
	}
	
	/* Returns whether the course is full */
	public boolean isCourseFull() {
		return this.getNumOfStudents() == this.getMaxStudents();
	}
	
	@Override
	public int compareTo(Course c) {
		return (c.getNumOfStudents() - this.getNumOfStudents());
	}
}
