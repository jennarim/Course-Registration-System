import java.io.*;
import java.util.*;

import javax.print.DocFlavor.SERVICE_FORMATTED;

public class CourseRegistrationSystem {
	public static void main(String[] args) throws IOException {
		Scanner in = new Scanner(System.in);
		ArrayList<Course> courseInfo = new ArrayList<Course>();
		ArrayList<Student> registeredStudents = new ArrayList<Student>();
		
		/* NOTES ON HOW TO RUN THIS PROGRAM:
		 * 1. Every time an admin (**only one admin should ever exist, as said in the homework)
		 * registers a student, the student's information is written onto RegisteredStudents.csv.
		 * The info in this csv file is then stored in an ArrayList of students, and when
		 * the user exits the program, this ArrayList gets serialized. When the user reopens the
		 * program, this program deserializes the ArrayList to use when necessary. The initial
		 * student in RegisteredStudent.csv should always exist, so you can run as the student
		 * with username Student without an admin actually having to officially register it.
		 * 
		 * 2. Admin has two different menus: Course Management Menu and Reports for the Admin Menu.
		 * Please note that even if you're on the course management menu as an admin, you can still
		 * switch to the reports for the admin menu by inputting 6 (7 if you're on the reports menu)
		 * 
		 * 3. The data only serializes if you press exit. Please don't restart the program without
		 * exiting the program first. Simply pressing 'run' again will not save the changes.
		 * 
		 * 4. Two courses cannot have the same name AND the same section. Similarly, two people can
		 * not have the same first name and last name. 
		 * 
		 * 5. "Available" courses mean all courses, full or not -- mentioned during class. 
		 */
		
		// De-serialize the ArrayList of courses
		File coursesCSV = new File("MyUniversityCourses.csv");
		File serializedCourses = new File("SerializedCourses.ser");
		deserialize(courseInfo, coursesCSV, serializedCourses);
		// De-serialize the ArrayList of students
		File studentsCSV = new File("RegisteredStudents.csv");
		File serializedStudents = new File("SerializedStudents.ser");
		deserialize(registeredStudents, studentsCSV, serializedStudents);

		System.out.println("Welcome to the course registration system.");
		Admin admin = new Admin();
		Student loggedInStudent = new Student();
		int status = checkUserType(admin, registeredStudents, loggedInStudent);

		/* If the user is a student */
		if (status == 1) {
			int userChoice = -1;
			
			// After the end of every action, display the menu and prompt the user 
			while (true) { 
				while (true) { // Display the menu until valid input
					try {
						loggedInStudent.displayMenu();
						userChoice = Integer.parseInt(in.nextLine());
						break;
					} catch (InputMismatchException | NumberFormatException e) {
						loggedInStudent.throwInvalid();
					}
				}
				// Prompt the user
				switch (userChoice) {
					case 1: 																	// 1. View all courses
						loggedInStudent.viewAllCourses(courseInfo);
						break;
					case 2: 																	// 2. View all courses that are NOT full
						loggedInStudent.viewNonFullCourses(courseInfo);
						break;
					case 3:																	// 3. Register for a course
						loggedInStudent.registerForCourse(registeredStudents, courseInfo);
						break;
					case 4:																	// 4. Withdraw from a course
						loggedInStudent.withdrawFromCourse(registeredStudents, courseInfo);
						break;
					case 5:																	// 5. View student's currently registered courses
						loggedInStudent.viewAllRegisteredCourses();
						break;
					case 6:																	// 6. Exit
						exitMenu(courseInfo, registeredStudents);
					default:
						System.out.println("Incorrect input. Try again. ");
						break;
				}
			} // end of while; stops displaying the menu and prompting the user  
		}
		
		/* If the user is an admin */
		else {

			int userChoice = -1;
			int whichMenu = -1;
			
			// Display the menu until valid input
			while (true) {
				try {
					admin.displayMenu();
					whichMenu = Integer.parseInt(in.nextLine());
					break;
				} catch (InputMismatchException | NumberFormatException e){
					admin.throwInvalid();
				}
			} 
			
			
			// After the end of every action, display the menu and prompt the user 
			while (true) {
				/* In the Course Management Menu */
				while (whichMenu == 1) { 
					// Display the menu until valid input
					while (true) {
						try {
							admin.displayCourseManagementMenu();
							userChoice = Integer.parseInt(in.nextLine());
							break;
						} catch (InputMismatchException | NumberFormatException e) {
							admin.throwInvalid();
						}
					}
					
					switch (userChoice) {
						case 1: 												// 1. Create a new course
							admin.createCourse(courseInfo);
							break; 												// break returns to the Course Management Menu
						case 2: 												// 2. Delete a course
							admin.deleteCourse(courseInfo, registeredStudents);
							break;
						case 3: 												// 3. Edit a course
							admin.editCourse(courseInfo, registeredStudents);
							break;
						case 4:  											// 4. Display information for a given course
							admin.displayCourse(courseInfo);
							break;
						case 5:  											// 5. Register a student
							admin.registerStudent(registeredStudents);
							break;
						case 6:  											// 6. Switch to the Display Reports Menu
							whichMenu = 2;
							break;
						case 7:  											// 7. Exit
							exitMenu(courseInfo, registeredStudents);
						default:
							admin.throwInvalid();
						
					}
				} // end of while; stops displaying the course management menu
				
				/* In the Reports for Admin Menu */
				while (whichMenu == 2) {
					// Display the menu until valid input
					while (true) {
						try {
							admin.displayReportsForAdminMenu();
							userChoice = Integer.parseInt(in.nextLine());
							break;
						} catch (InputMismatchException | NumberFormatException e) {
							admin.throwInvalid();
						}
					}
					switch (userChoice) {
						case 1:  											// 1. View all courses
							admin.viewAllCourses(courseInfo);
							break;
						case 2:  											// 2. View all courses that are FULL
							admin.viewFullCourses(courseInfo);
							break;
						case 3:  											// 3. Write to a file the list of courses that are full
							admin.writeCourseToFull(courseInfo);
							break;
						case 4:  											// 4. View the names of the students being registered in a specific course
							admin.viewStudentsInCourse(courseInfo);
							break;
						case 5:  											// 5. View the list of courses that a given student is being registered on
							admin.viewStudentCourses(registeredStudents);
							break;
						case 6:  											// 6. Sort courses based on the current number of students registered.
							admin.sortCourses(courseInfo);
							break;
						case 7:  											// 7. Display information for a given course menu
							whichMenu = 1;
							break;
						case 8:  											// 8. Exit
							exitMenu(courseInfo, registeredStudents);
						default:
							System.out.println("Incorrect input. Try again. ");
							break;
					}
			
				} // end of while; stops displaying the reports for admin menu
				
				if (whichMenu != 1 && whichMenu != 2) {
					System.out.println("Incorrect input. Try again. ");
					whichMenu = Integer.parseInt(in.nextLine());
				}
			}

		} // end of admin
	}
	
	// De-serialize a serialized object
	public static void deserialize(ArrayList arr, File csvFile, File serFile) {
		if (serFile.exists()) {
//			System.out.println("serialized file exists, so we now deserialize");
			
			try {
				FileInputStream fis = new FileInputStream(serFile);
				ObjectInputStream ois = new ObjectInputStream(fis);
		
				if (csvFile.getName().equals("MyUniversityCourses.csv")) {
					arr.addAll((ArrayList<Course>) ois.readObject());
				} else if (csvFile.getName().equals("RegisteredStudents.csv")) {
					arr.addAll((ArrayList<Student>) ois.readObject());
//					System.out.println("after deserialize: ");
//					ArrayList<Student> arr2 = arr;
//					for (Student s : arr2) {
//						System.out.println(s.getCourses());
//					}
				}
				
				ois.close();
				fis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
		else {
//			System.out.println("serialized file does not exists");
			try {
				FileReader fr = new FileReader(csvFile);
				BufferedReader bf = new BufferedReader(fr);
				
				if (csvFile.getName().equals("MyUniversityCourses.csv")) {
					String line = bf.readLine();  // By calling readLine(), the reader skips the first line of .csv file	
					while ((line = bf.readLine()) != null) {
						String[] split = line.split(",");
						// Constructor: String name, String id, int maxStudents, int numOfStudents, 				     String instructorName, int sectionNumber, 	  String courseLocation)
						// The file:    Course_Name, Course_Id, Maximum_Students,Current_Students,	List_Of_Names	,Course_Instructor,		Course_Section_Number,Course_Location
						Course c = new Course(split[0], split[1], (int) Integer.parseInt(split[2]), (int) Integer.parseInt(split[3]), split[5], (int) Integer.parseInt(split[6]), split[7]);
						arr.add(c);
					}
				} else if (csvFile.getName().equals("RegisteredStudents.csv")) {
					String line = "";
					while ((line = bf.readLine()) != null) {
						String[] splitStudent = line.split(",");
						Student s = new Student(splitStudent[0], splitStudent[1], splitStudent[2], splitStudent[3]);
						arr.add(s);
					}
				}

				bf.close(); // close inner
				fr.close(); // close outer
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
	
	// Prompt the user for username and password, then verifies whether user is a valid admin
	public static boolean verifyIfAdmin(Admin admin) {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter username for admin: ");
		String attemptedName = in.nextLine();
		System.out.println("Enter password for admin: ");
		String attemptedPW = in.nextLine();
		if (attemptedName.equals(admin.getUsername()) && attemptedPW.equals(admin.getPassword())) {
			System.out.println("Permissions granted.");
			return true;
		}
		return false;
	}
	
	// Prompt the user for username and password, then verifies whether user is a valid student who is registered
	public static boolean verifyIfStudent(ArrayList<Student> registeredStudents, Student loggedInStudent) {
		Scanner in = new Scanner(System.in);
		
		System.out.println("Enter username for student: ");
		String attemptedName = in.nextLine();
		System.out.println("Enter password for student: ");
		String attemptedPW = in.nextLine();
		for (Student s : registeredStudents) {
			if (attemptedName.equals(s.getUsername()) && attemptedPW.equals(s.getPassword())) {
				loggedInStudent.setfName(s.getfName());
				loggedInStudent.setlName(s.getlName());
				loggedInStudent.setUsername(s.getUsername());
				loggedInStudent.setPassword(s.getPassword());
				loggedInStudent.setCourses(s.getCourses());
				System.out.println("Permissions granted.");
				return true;
			}
		}
		return false;
	}
	
	// Returns whether the user is an admin or a student
	public static int checkUserType(Admin admin, ArrayList<Student> registeredStudents, Student loggedInStudent) {
		Scanner in = new Scanner(System.in);
		int status = -1;
		
		while (true) {
			try {
				System.out.println("Input whether you are a(n):\n1. Student\t2. Admin");
				status = Integer.parseInt(in.nextLine());
				if (status == 1 || status == 2)
					break;
			} catch (InputMismatchException | NumberFormatException e) {
				System.out.println("Invalid input. Try again.");
			}
		}
		
		if (status == 1) { // If user claims they are a student
			while (true) {
				if (verifyIfStudent(registeredStudents, loggedInStudent)) {
					return 1; 
				}
				else
					System.out.println("Login for student failed. Try again.");
			}
		}
		else { // If user claims they are an admin
			while (true) {
				if (verifyIfAdmin(admin))
					return 2;
				else
					System.out.println("Login for admin failed. Try again.");
			}
		}
	}
	
	// Serializes an ArrayList
	public static void serialize(File file, ArrayList arr) {
//		System.out.println("Now serializing...");	
//		System.out.println("Info being serialized: ");
//		System.out.println("before serialize: ");
		if (file.getName().equals("SerializedStudents.ser")) {
//			System.out.println("writing to student file");
//			ArrayList<Student> arr2 = arr;
//			for (Student s : arr2) {
//				System.out.println(s);
//				ArrayList<Course> co = s.getCourses();
//				System.out.println(co.size());
//				for (Course c : co) 
//					System.out.println(c);
//			}
		}

		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(arr);
			
			oos.close();
			fos.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	
	// Exits the menu; serialized the course list and list of registered students
	public static void exitMenu(ArrayList<Course> courseInfo, ArrayList<Student> registeredStudents) {
		File serializedCourses = new File("SerializedCourses.ser");
		File serializedStudents = new File("SerializedStudents.ser");
		serialize(serializedCourses, courseInfo);
		serialize(serializedStudents, registeredStudents);
		
		System.out.println("You have left the system. Goodbye!");
		System.exit(0);
	}
	
}

