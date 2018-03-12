import java.util.ArrayList;

public interface UserInterface {
	public abstract void displayMenu();
	public abstract void viewAllCourses(ArrayList<Course> courseList);
	public abstract int searchById(String id, int section, ArrayList<Course> courseList);
	public abstract int searchByCourseName(String courseName, int section, ArrayList<Course> courseList);
	public abstract int searchByName(String fName, String lName, ArrayList<Student> registeredStudents);
	public abstract void throwInvalid();
}
