import java.util.ArrayList;

public interface AdminInterface {
	public abstract void displayMenu();
	
	/* Methods necessary for Course Management Menu */
	public abstract void displayCourseManagementMenu();
	public abstract void createCourse(ArrayList<Course> courses);
	public abstract void deleteCourse(ArrayList<Course> courses, ArrayList<Student> registeredStudents); //search course by id
	public abstract void editCourse(ArrayList<Course> courses, ArrayList<Student> registeredStudents); //search course by id
	public abstract void displayCourse(ArrayList<Course> courses); //search course by id
	public abstract void registerStudent(ArrayList<Student> students); //admin needs its own ArrayList of Students
	
	/* Methods necessary for Admin Reports Menu */
	public abstract void displayReportsForAdminMenu();
	public abstract void viewFullCourses(ArrayList<Course> courses);
	public abstract void writeCourseToFull(ArrayList<Course> courses); //write to a new file called CoursesFull
	public abstract void viewStudentsInCourse(ArrayList<Course> courses); //
	public abstract void viewStudentCourses(ArrayList<Student> students);
	public abstract void sortCourses(ArrayList<Course> courses);
}
