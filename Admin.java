import java.io.*;
import java.util.*;

/*
 * Admin inherits from User. Only one admin should exist in the program.
 * Admin's methods authorize the Admin to manage courses and reports.
 */
public class Admin extends User implements AdminInterface {
	Scanner input = new Scanner(System.in);
	
	public Admin() { //There's only one admin, so there should only be one constructor
		super.username = "Admin";
		super.password = "Admin001";
	}
	
	// Displays Admin's main menu
	@Override
	public void displayMenu() {
		System.out.println("*******ADMIN MENU*******");
		System.out.println("1. View Courses Management Menu\n2. View Reports for the Admin Menu");
	}
	
	// Displays Course Management Menu
	public void displayCourseManagementMenu() {
		System.out.println("~~~ Course Management Menu");
		System.out.println("1. Create a new course\n2. Delete a course\n3. Edit a course\n"
				+ "4. Display information for a given course\n5. Register a student\n6. Go to Admin Reports Menu\n7. Exit");
	}
	
	// Adds a course into the list of courses
	public void createCourse(ArrayList<Course> courses) {
		while (true) { // Keep asking the user if they input an invalid input
			try {
				/* Prompt user for course's attributes */
				System.out.println("Enter name of course: ");
				String name = input.nextLine();
				System.out.println("Enter ID of course: ");
				String id = input.nextLine();
				System.out.println("Enter the capacity of course: ");
				int maxStudents = Integer.parseInt(input.nextLine());
				System.out.println("Enter instructor's full name of course: ");
				String instructorName = input.nextLine();
				System.out.println("Enter section number of course: ");
				int sectionNumber = Integer.parseInt(input.nextLine());
				System.out.println("Enter location of course: ");
				String courseLocation = input.nextLine();
				
				/* Create and add the course */
				Course c = new Course(name, id, maxStudents, 0, instructorName, sectionNumber, courseLocation);
				courses.add(c);
				System.out.println(name + " was successfully added. ");
				return; // If inputs were successful by this point, end the function
			} catch (Exception e) {
				throwInvalid();
			}
		} // end of while
	}
	
	// Delete a course from the list of courses
	public void deleteCourse(ArrayList<Course> courses, ArrayList<Student> registeredStudents) {
		/* Ask and prompt */
		while (true) { 															// Keep asking the user if they input an invalid input
			try {
				/* Prompt user for course's attributes */
				System.out.println("Enter ID of course: ");
				String courseID = input.nextLine();
				System.out.println("Enter section number of course: ");
				int sectionNumber = Integer.parseInt(input.nextLine());
			
				/* Find the index of the course */
				int foundIndex = searchById(courseID, sectionNumber, courses);
				if (foundIndex == -1) {
					System.out.println("Course not found.");
					return;
				}
				
				System.out.println("index: " + foundIndex); // 29, should be 29
				System.out.println("size: " + courses.size()); // 29, should be 30
				
				/* DELETE COURSE FROM THE STUDENTS WHO ARE REGISTERED ON IT */
				Course deletedCourse = courses.get(foundIndex);
				for (Student s : registeredStudents) {
					ArrayList<Course> toRemove = new ArrayList<Course> ();
					for (Course c : s.getCourses()) {
						if (c.getName().equals(deletedCourse.getName()) && c.getSectionNumber() == deletedCourse.getSectionNumber()) {
							toRemove.add(c);
						}
					}
					s.getCourses().removeAll(toRemove);
				}
				
				/* DELETE COURSE FROM COURSELIST */
				courses.remove(foundIndex);
				
				System.out.println("Successfully removed.");
				return; 															// If inputs were successful by this point, end the function
			} catch (Exception e) {
				e.printStackTrace();
				throwInvalid();
			}
		} 																		// end of while
	}
	
	// Edit a course from the list of courses
	public void editCourse(ArrayList<Course> courses, ArrayList<Student> registeredStudents) {
		String courseID = "";
		int sectionNumber = -1;
		int foundIndex = -1; 											// index of the specified course in the list of courses
		Course foundCourse = null;
		int nextAction = 0;
		
		/* Ask the course's attributes (repeat if they input an invalid input) */
		while (true) { 													
			try {
				/* Prompt user for the course's attributes */
				System.out.println("Enter ID of course: ");
				courseID = input.nextLine();
				System.out.println("Enter section number of course: ");
				sectionNumber = Integer.parseInt(input.nextLine());
				
				/* Find the index of the course */
				foundIndex = searchById(courseID, sectionNumber, courses);
				if (foundIndex == -1) {
					System.out.println("Course not found.");
					return;
				} else {
					foundCourse = courses.get(foundIndex);
					break;
				}
			} catch (Exception e) {
				throwInvalid();
			}
		}
		
		/* If the course is valid, ask which attribute to edit */
		while (true) {
			try {
				String newValue = "";
				System.out.println("~~ Choose what you would like to edit: ");
				System.out.println("1. Course name\n2. Course ID\n3. Instructor name");
				nextAction = Integer.parseInt(input.nextLine());
				
				switch (nextAction) {
					case 1: // 1. Course name
						System.out.println("Enter new course name: ");
						newValue = input.nextLine();
						
						/* Change the value in the courses */
						foundCourse.setName(newValue);
						/* Change the value in student's courses */
						for (Student s : registeredStudents) {
							for (Course c : s.getCourses()) {
								if (c.getId().equals(foundCourse.getId()) && c.getSectionNumber() == foundCourse.getSectionNumber()) {
									c.setName(newValue);
								}
							}
						}
						System.out.println("Edit successful.");
						return;
					case 2: // 2. Course ID
						System.out.println("Enter new course ID: ");
						newValue = input.nextLine();
						foundCourse.setId(newValue);
						for (Student s : registeredStudents) {
							for (Course c : s.getCourses()) {
								if (c.getId().equals(foundCourse.getId()) && c.getSectionNumber() == foundCourse.getSectionNumber()) {
									c.setId(newValue);
								}
							}
						}
						System.out.println("Edit successful.");
						return;
					case 3: // 3. Instructor name
						System.out.println("Enter new instructor: ");
						newValue = input.nextLine();
						foundCourse.setInstructorName(newValue);
						for (Student s : registeredStudents) {
							for (Course c : s.getCourses()) {
								if (c.getId().equals(foundCourse.getId()) && c.getSectionNumber() == foundCourse.getSectionNumber()) {
									c.setInstructorName(newValue);
								}
							}
						}
						System.out.println("Edit successful.");
						return;
					default:
						throwInvalid();
				}
				
			} catch (InputMismatchException | NumberFormatException e) {
				throwInvalid();
			}
		} // end of while
	}
	
	// Display the information of a certain course
	public void displayCourse(ArrayList<Course> courses) {
		Scanner input = new Scanner(System.in);
		String courseID = "";
		int sectionNum = -1;
		
		/* Prompt user for the course until valid */
		while (true) {
			try {
				System.out.println("Enter the course ID: ");
				courseID = input.nextLine();
				System.out.println("Enter the section number: ");
				sectionNum = Integer.parseInt(input.nextLine());
				break;
			} catch (InputMismatchException | NumberFormatException e) {
				throwInvalid();
			}
		}
		
		/* Find the course */
		int foundIndex = searchById(courseID, sectionNum, courses);
		if (foundIndex == -1) {
			System.out.println("Course not found.");
			return;
		}
		Course found = courses.get(foundIndex);
		
		/* Display the course */
		System.out.println("~ Name: " 			+ found.getName());
		System.out.println("~ ID: " 			+ found.getId());
		System.out.println("~ Capacity: "      + found.getMaxStudents());
		System.out.println("~ # of students: " + found.getNumOfStudents());
		System.out.println("~ Instructor: "    + found.getInstructorName());
		System.out.println("~ Section #: "     + found.getSectionNumber());
		System.out.println("~ Location: "      + found.getCourseLocation());
	}
	
	// Register a student
	public void registerStudent(ArrayList<Student> students) { // Admin needs its own ArrayList of Students
		try {
			/* Prompt admin what the student's attributes are */
			System.out.println("Enter student's first name: ");
			String fName = input.nextLine();
			System.out.println("Enter student's last name: ");
			String lName = input.nextLine();
			System.out.println("Enter the student's username: ");
			String username = input.nextLine();
			System.out.println("Enter the student's password: ");
			String password = input.nextLine();
			
			/* Create the student and add them to list of registered students */
			Student newStudent = new Student(fName, lName, username, password);
			students.add(newStudent);
			
			/* Write the student onto a csv file */
			try {
				FileWriter fw1 = new FileWriter("RegisteredStudents.csv", true);
				BufferedWriter bw1 = new BufferedWriter(fw1);
				bw1.write(fName + "," + lName + "," + username + "," + password + "\n");
				
				bw1.close();
				fw1.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		} catch (Exception e) {
			throwInvalid();
		}
		System.out.println("Student successfully registered.");
	}
	
	// Display Reports for Admin menu
	public void displayReportsForAdminMenu() {
		System.out.println("~~~ Reports for the Admin Menu");
		System.out.println("1. View all courses\n2. View all full courses\n3. Write to a file a list of full courses\n"
				+ "4. View the names of the students registered in a specific course\n5. View the list of courses"
				+ " that a given student is registered in\n6. Sort courses based on current # of registered students\n7. Go to Course Management Menu\n8. Exit");
	}
	
	// Displays all the courses that are full
	public void viewFullCourses(ArrayList<Course> courses) {
		boolean doFullCoursesExist = false;
		/* Display the course only if it's full */
		for (int i = 0; i < courses.size(); i++) {
			if (courses.get(i).isCourseFull()) {
				System.out.println("* " + courses.get(i));
				doFullCoursesExist = true;
			}
		}
		if (!doFullCoursesExist) {							// If full courses do not exist
			System.out.println("No full courses found.");
		}
	}
	
	// Write full courses to a file called CoursesFull.txt
	public void writeCourseToFull(ArrayList<Course> courses) {
		try {
			File file = new File("CoursesFull.txt");
			FileWriter fw3 = new FileWriter(file);
			BufferedWriter bw3 = new BufferedWriter(fw3);
			ArrayList<Course> fullCourses = new ArrayList<Course>();
			
			for (Course c : courses) {
				if (c.isCourseFull()) {
					fullCourses.add(c);
				}
			}
			
			for (Course c : fullCourses) {
				bw3.write("* " + c);
				bw3.newLine();
			}
			System.out.println("File was successfully written to.");
			bw3.close();
			fw3.close();
		} catch(IOException e) {
			e.printStackTrace();
			System.out.println("File does not exist.");
		}
	}
	
	// View the names of the students being registered in a specific course
	public void viewStudentsInCourse(ArrayList<Course> courses) {
		String id = "";
		int section = -1;
		
		/* Prompt Admin for attributes of course */
		while (true) {
			try {
				System.out.println("Enter the course ID: ");
				id = input.nextLine();
				System.out.println("Enter the course section: ");
				section = Integer.parseInt(input.nextLine());
				break;
			} catch (Exception e) {
				throwInvalid();
			}
		}
		
		/* Find the course */
		int foundIndex = searchById(id, section, courses);
		if (foundIndex == -1) {
			System.out.println("Course not found.");
			return;
		}
		
		/* Display each student registered in the course */
		ArrayList<Student> students = courses.get(foundIndex).getCurrentStudents();
		if (students.size() == 0) {
			System.out.println("There are no students registered in this course.");
		}
		for (Student s : students) {
			System.out.println("* " + s);
		}
	}
	
	// View the list of courses that a given student is being registered on
	public void viewStudentCourses(ArrayList<Student> students) {
		/* Prompt the admin for the student's attributes */
		System.out.println("Enter the student's first name: ");
		String fName = input.nextLine();
		System.out.println("Enter the student's last name: ");
		String lName = input.nextLine();
		
		/* Find the student */
		int found = searchByName(fName, lName, students);
		if (found == -1) {
			System.out.println("Student not found.");
			return;
		}
		/* Display each course of a student */
		ArrayList<Course> studentCourses = students.get(found).getCourses();
		if (studentCourses.size() == 0) {
			System.out.println("Student is not registered for any courses.");
		}
		for (Course c : studentCourses) {
			System.out.println("* " + c);
		}
	}
	
	// Sort the course list from greatest number of students to lowest
	public void sortCourses(ArrayList<Course> courses) {
		Collections.sort(courses);
		System.out.println("Successfully sorted from greatest number of students to lowest.");
	}
	
}
