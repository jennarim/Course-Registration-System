import java.util.ArrayList;

public interface StudentInterface {
	public abstract void viewNonFullCourses(ArrayList<Course> courseList);
	public abstract void registerForCourse(ArrayList<Student> registeredStudents, ArrayList<Course> courseList);
	 
	public abstract void withdrawFromCourse(ArrayList<Student> registeredStudents, ArrayList<Course> courseList);
	public abstract void viewAllRegisteredCourses();
	
}
