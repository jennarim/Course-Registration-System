import java.util.ArrayList;
/* 
 * User is a parent class that describes the most type user of the program.
 * It is an abstract class, so it cannot be instantiated.
 * A user is defined simply as one with a first name, last name, username, and password
 * 
 */

public abstract class User implements UserInterface, java.io.Serializable {
	protected String fName;
	protected String lName;
	protected String username;
	protected String password;
	
	/* User is an abstract class, but I created constructors because constructors of Admin and Student call them */
	public User() {
		
	}
	
	/* Constructor */
	public User(String fName, String lName, String username, String password) {
		this.fName = fName;
		this.lName = lName;
		this.username = username;
		this.password = password;
	}
	
	// Abstract method: Admin and Student will override this method
	public abstract void displayMenu();
	
	// Displays all the courses
	public void viewAllCourses(ArrayList<Course> courseList) {
		for (Course c : courseList) {
			System.out.println("~ " + c);
		}
	}
	
	// Given a course's ID and section number, return the course's index in course list
	public int searchById(String id, int section, ArrayList<Course> courseList) {
		for (int i = 0; i < courseList.size(); i++) {
			Course c = courseList.get(i);
			if (c.getId().equals(id) && c.getSectionNumber() == section)
				return i;
		}
		return -1; // If course is not found, return -1
	}
	
	// Given a course's name and section number, return the course's index in course list
	public int searchByCourseName(String courseName, int section, ArrayList<Course> courseList) {
		for (int i = 0; i < courseList.size(); i++) {
			Course c = courseList.get(i);
			if (c.getName().equals(courseName) && c.getSectionNumber() == section) {
				return i;
			}
		}
		return -1; // If course is not found, return -1
	}
	
	// Given a student's name, return the student's index in list of registered students
	public int searchByName(String fName, String lName, ArrayList<Student> registeredStudents) {
		for (int i = 0; i < registeredStudents.size(); i++) {
			Student currStudent = registeredStudents.get(i);
			if (currStudent.getfName().equals(fName) && currStudent.getlName().equals(lName))
				return i;
		}
		return -1; // If student is not found, return -1
	}
	
	// Display invalid message
	public void throwInvalid() {
		System.out.println("There was an incorrect input. Try again.");
	}
	
	/* Getters and Setters */
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}
	
}
