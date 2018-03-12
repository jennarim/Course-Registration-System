import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/* 
 * Student represents a user who is a student and is able to view, register, and withdraw courses. 
 */
public class Student extends User implements StudentInterface, java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6552460147923006905L;
	private ArrayList<Course> courses;
	
	/* Default constructor */
	Student() {
		this("N/A", "N/A", "N/A", "N/A");
	}
	
	/* Constructor that takes in name only */
	public Student(String fName, String lName) {
		this.fName = fName;
		this.lName = lName;
		courses = new ArrayList<Course>();
	}
	
	/* Constructor that takes in name, username, and password */
	public Student(String fName, String lName, String username, String password) {
		super(fName, lName, username, password);
		courses = new ArrayList<Course>();
	}
	
	// Display the student menu
	@Override
	public void displayMenu() {
		System.out.println("*******STUDENT MENU*******");
		System.out.println("~~~ Course Management Menu");
		System.out.println("1. View all courses that are available\n2. View all courses that are not FULL\n"
				+ "3. Register  on  a  course\n4. Withdraw from a course\n5. View all courses that I am registered in\n"
				+ "6. Exit");
	}
	
	// Display the courses that are open (not full)
	public void viewNonFullCourses(ArrayList<Course> courseList) {
		boolean doNonFullCoursesExist = false;
		for (int i = 0; i < courseList.size(); i++) {
			if (!courseList.get(i).isCourseFull()) {
				System.out.println("* " + courseList.get(i));
				doNonFullCoursesExist = true;
			}
		}
		if (!doNonFullCoursesExist) {
			System.out.println("All courses are full.");
		}
	}
	
	// Register for a course
	public void registerForCourse(ArrayList<Student> registeredStudents, ArrayList<Course> courseList) {
		Scanner input = new Scanner(System.in);
		int section = -1;
		String courseName = "";
		
		try {
			/* Check if user has max courses */
			if (courses.size() == 3){
				System.out.println("You are already registered for 3 courses. You cannot exceed this number. Course registration failed.");
				return;
			}
			
			/* Ask for course attributes */
			while (true) {
				try {
					System.out.println("Enter course name: ");
					courseName = input.nextLine();
					System.out.println("Enter course section: ");
					section = Integer.parseInt(input.nextLine());
					break;
				} catch (Exception e) {
					throwInvalid();
				}
			}
			
			/* Find the course */
			int found = searchByCourseName(courseName, section, courseList);
			if (found == -1) {
				System.out.println("Course not found.");
				return;
			} else {
				System.out.println("Course found.");
			}
			Course c = courseList.get(found);
			
			/* If course is full, end registration */
			if (c.isCourseFull()) {
				System.out.println("This course is full.");
				return;
			}
			
			/* If the course's list of students is null, initialize the list to avoid NullPointer */
			if (c.getCurrentStudents() == null)
				c.setCurrentStudents(new ArrayList<Student>());
				
			/* Check if student is already in the course */
			for (Student s : c.getCurrentStudents()) {
				if (s.getfName().equals(fName) && s.getlName().equals(lName)) {
					System.out.println("You are already registered on this course. Course registration failed.");
					return;
				}
			}
			
			
			
			/* If everything is valid... */
			/* ... Add the student into the course's student list*/
			ArrayList<Student> cs = c.getCurrentStudents();
			cs.add(this);
			c.setCurrentStudents(cs);
			System.out.println("You have been successfully registered to " + courseName + " Section " + section);
			c.setNumOfStudents(c.getNumOfStudents() + 1); // increase the course's number of students
			
			/* ... Add the course into the student's course list */
			courses.add(c);
			/* ... Update the student in registered students to include the new course */
			for (Student s : registeredStudents) {
				if (s.getfName().equals(fName) && s.getlName().equals(lName)) {
					s.setCourses(courses);
				}
			}
			
			return;
		} catch (InputMismatchException e) {
			throwInvalid();
		}
	}
	
	// Withdraw from a course
	public void withdrawFromCourse(ArrayList<Student> registeredStudents, ArrayList<Course> courseList) {
		Scanner input = new Scanner(System.in);
		
		String courseName = "";
		int section = -1;
		
		/* Prompt student for course */
		while (true) {
			try {
				System.out.println("Enter the course name: ");
				courseName = input.nextLine();
				System.out.println("Enter the course section: ");
				section = Integer.parseInt(input.nextLine());
				break;
			} catch (Exception e) {
				throwInvalid();
			}
		}
		
		/* Find the course in the student's courses */
		int courseIndexFound = searchByCourseName(courseName, section, courses);
		if (courseIndexFound == -1) {
			System.out.println("You are not registered in this course.");
			return;
		}

		/* Find the course in the overall course list */
		int courseInCourseList = searchByCourseName(courseName, section, courseList);
		Course courseFound = courseList.get(courseInCourseList);

		/* Find the student in the course's list of student */
		int studentIndexFound = searchByName(fName, lName, courseFound.getCurrentStudents());
		if (studentIndexFound == -1) {
			System.out.println("You are not registered in this course.");
			return;
		}
		
		/* If everything is valid ... */
		/* DELETE THE STUDENT FROM THE COURSE */
		courseFound.getCurrentStudents().remove(studentIndexFound);
		courseFound.setNumOfStudents(courseFound.getNumOfStudents() - 1);
		for (Student bbb :courseFound.getCurrentStudents()) {
			System.out.println(bbb);
		}
		
		/* DELETE THE COURSE FROM THE STUDENT'S COURSES */	
		courses.remove(courseIndexFound);
		for (Student s : registeredStudents) {
			if (s.getfName().equals(fName) && s.getlName().equals(lName)) {
				s.setCourses(courses);
			}
		}
		
		System.out.println("You have been removed from " + courseName + " " + " section " + section);
	}
	
	// View all the student's registered courses
	public void viewAllRegisteredCourses() {
		if (courses.size() == 0) {
			System.out.println("You have not registered in any courses. ");
			return;
		}
		for (Course c : courses) {
			System.out.println("* " + c);
		}
	}
	
	/* Getters and Setters */
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
	
	public ArrayList<Course> getCourses() {
		return courses;
	}
	
	public void setCourses(ArrayList<Course> courses) {
		this.courses = courses;
	}
	
	public int getCurrentNumberCourses() {
		if (courses == null) {
			return 0;
		}
		return courses.size();
	}
	
	public String toString() {
		return "Student: " + fName + " " + lName + " " + username + " " + password;
	}
}
