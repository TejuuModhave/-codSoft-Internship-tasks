import java.util.*;

// Course class to store course details
class Course {
    String courseCode, title, description, schedule;
    int capacity, enrolled;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.enrolled = 0;
    }

    public boolean registerStudent() {
        if (enrolled < capacity) {
            enrolled++;
            return true;
        }
        return false;
    }

    public boolean dropStudent() {
        if (enrolled > 0) {
            enrolled--;
            return true;
        }
        return false;
    }
}

// Student class to store student details
class Student {
    String studentID, name;
    List<Course> registeredCourses;

    public Student(String studentID, String name) {
        this.studentID = studentID;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public void registerCourse(Course course) {
        if (course.registerStudent()) {
            registeredCourses.add(course);
            System.out.println(name + " successfully registered for " + course.title);
        } else {
            System.out.println("Registration failed. Course full.");
        }
    }

    public void dropCourse(Course course) {
        if (registeredCourses.remove(course) && course.dropStudent()) {
            System.out.println(name + " successfully dropped " + course.title);
        } else {
            System.out.println("Course removal failed.");
        }
    }
}

// Registration System
public class CourseRegistrationSystem {
    private static List<Course> courses = new ArrayList<>();
    private static List<Student> students = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeCourses();
        menu();
    }

    private static void initializeCourses() {
        courses.add(new Course("CS101", "Intro to Programming", "Basic programming concepts", 2, "Mon & Wed 10:00 AM"));
        courses.add(new Course("MTH102", "Calculus I", "Differentiation and Integration", 2, "Tue & Thu 2:00 PM"));
    }

    private static void menu() {
        while (true) {
            System.out.println("\n1. Register Student");
            System.out.println("2. List Courses");
            System.out.println("3. Register for Course");
            System.out.println("4. Drop Course");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    registerStudent();
                    break;
                case 2:
                    listCourses();
                    break;
                case 3:
                    registerForCourse();
                    break;
                case 4:
                    dropCourse();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void registerStudent() {
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        students.add(new Student(id, name));
        System.out.println("Student registered successfully.");
    }

    private static void listCourses() {
        for (Course c : courses) {
            System.out.println(
                    c.courseCode + " - " + c.title + " (" + c.enrolled + "/" + c.capacity + ") - " + c.schedule);
        }
    }

    private static void registerForCourse() {
        Student student = findStudent();
        if (student == null)
            return;

        listCourses();
        System.out.print("Enter course code: ");
        String code = scanner.nextLine();
        Course course = findCourse(code);
        if (course != null) {
            student.registerCourse(course);
        } else {
            System.out.println("Course not found.");
        }
    }

    private static void dropCourse() {
        Student student = findStudent();
        if (student == null)
            return;

        if (student.registeredCourses.isEmpty()) {
            System.out.println("No registered courses to drop.");
            return;
        }

        System.out.println("Registered Courses:");
        for (Course c : student.registeredCourses) {
            System.out.println(c.courseCode + " - " + c.title);
        }

        System.out.print("Enter course code to drop: ");
        String code = scanner.nextLine();
        Course course = findCourse(code);
        if (course != null) {
            student.dropCourse(course);
        } else {
            System.out.println("Course not found.");
        }
    }

    private static Student findStudent() {
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();
        for (Student s : students) {
            if (s.studentID.equals(id))
                return s;
        }
        System.out.println("Student not found.");
        return null;
    }

    private static Course findCourse(String code) {
        for (Course c : courses) {
            if (c.courseCode.equalsIgnoreCase(code))
                return c;
        }
        return null;
    }
}
