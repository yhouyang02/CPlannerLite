package ui;

import exception.*;
import model.Course;
import model.Schedule;
import model.Time;
import model.Worklist;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Represents the course planner application to manage worklist
// Note: The UI functionality is adjusted from TellerApp.java; Linked below:
//       https://github.students.cs.ubc.ca/CPSC210/TellerAppNotRobust/blob/master/src/main/ca/ubc/cpsc210/bank/ui/TellerApp.java
public class PlannerApp {

    // Path of JSON file for loading and saving worklist
    private static final String JSON_STORE = "./data/worklist.json";

    // Temporary course information for adding a new course
    private Course tempCourse;
    private Schedule tempSchedule;
    private String tempSubjectCode;
    private String tempCourseCode;
    private String tempSectionCode;
    private String tempTitle;
    private boolean[] tempDays;
    private Time tempStartTime;
    private Time tempEndTime;
    private int tempCredits;
    private boolean tempRequired;

    private Worklist worklist;
    private Scanner input;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    // EFFECTS: initializes scanner, JSON reader, JSON writer, and a new worklist, then
    //          runs the course planner application
    public PlannerApp() {
        worklist = new Worklist("New Worklist");
        input = new Scanner(System.in);
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        runPlannerApp();
    }

    // MODIFIES: this
    // EFFECTS: runs the course planner application to process user command
    private void runPlannerApp() {
        boolean toContinue = true;
        String command;

        System.out.println("Welcome to Course Planner!");
        processStartCommand();

        while (toContinue) {
            displayMainMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                processQuitCommand();
                toContinue = false;
            } else {
                processMainCommand(command);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command on the starting menu
    private void processStartCommand() {
        String command;
        System.out.println("Do you want to load existing worklist from file? [Y/N]");
        command = input.next();
        input.nextLine();
        if (command.equalsIgnoreCase("Y")) {
            loadWorklist();
        } else if (command.equalsIgnoreCase("N")) {
            System.out.println("\nPlease enter the name of your worklist:");
            String name = input.nextLine();
            worklist = new Worklist(name);
        } else {
            System.err.println("[ERROR] Invalid selection!");
            System.out.println();
            processStartCommand();
        }
    }

    // EFFECTS: processes user command when quiting
    private void processQuitCommand() {
        String command;
        System.out.println("\nDo you want to save your worklist to file? [Y/N]");
        command = input.next();
        if (command.equalsIgnoreCase("Y")) {
            saveWorklist();
        } else if (!command.equalsIgnoreCase("N")) {
            System.err.println("[ERROR] Invalid selection!");
            System.out.println();
            processQuitCommand();
        }
    }

    // EFFECTS: displays main menu of options to user
    private void displayMainMenu() {
        System.out.println("\nWorklist: " + worklist.getName());
        System.out.println("Main Menu - Select from:");
        System.out.println("\tc -> All courses");
        System.out.println("\tr -> Starred courses");
        System.out.println("\tw -> Worklist statistics");
        System.out.println("\tl -> Load worklist from file");
        System.out.println("\ts -> Save worklist to file");
        System.out.println("\th -> Help");
        System.out.println("\tq -> Quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command on the main menu
    private void processMainCommand(String command) {
        switch (command) {
            case "c":
                doAllCourses();
                break;
            case "r":
                printStarredCourses();
                break;
            case "w":
                printStatistics();
                break;
            case "l":
                loadWorklist();
                break;
            case "s":
                saveWorklist();
                break;
            case "h":
                displayHelpMenu();
                break;
            default:
                System.err.println("[ERROR] Invalid selection!");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: prints out all courses and performs actions regarding courses
    private void doAllCourses() {
        printAllCourses();
        String command;
        System.out.println();
        displayAllCoursesMenu();
        command = input.next();
        processAllCoursesCommand(command);
    }

    // EFFECTS: prints out all courses
    private void printAllCourses() {
        System.out.println("\n### Courses ###");
        if (worklist.getCourses().isEmpty()) {
            System.out.println("[WARNING] No courses in worklist!");
        } else {
            for (Course c : worklist.getCourses()) {
                System.out.println(c.toString());
            }
        }
    }

    // EFFECTS: displays all courses menu of options to user
    private void displayAllCoursesMenu() {
        System.out.println("\nWorklist: " + worklist.getName());
        System.out.println("All Courses - Select from:");
        System.out.println("\tn -> Add a new course");
        System.out.println("\td -> Delete a course");
        System.out.println("\ts -> Star a course");
        System.out.println("\tu -> Unstar a course");
        System.out.println("\tq -> Back to main menu");
    }

    // MODIFIES: this
    // EFFECTS: processes user command on the all courses menu
    private void processAllCoursesCommand(String command) {
        switch (command) {
            case "n":
                doAddCourse();
                break;
            case "d":
                doDeleteCourse();
                break;
            case "s":
                doStarCourse();
                break;
            case "u":
                doUnstarCourse();
                break;
            case "q":
                break;
            default:
                System.err.println("[ERROR] Invalid selection!");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: performs the action of adding a course
    private void doAddCourse() {
        loadCodes();
        loadTitle();
        loadDays();
        loadStartTime();
        loadEndTimes();
        loadSchedule();
        loadCredits();
        loadRequired();
        loadCourse();
        if (worklist.getCourses().size() >= Worklist.COURSE_LIMIT) {
            System.out.println("[WARNING] You already have "
                    + worklist.getCourses().size() + " courses in worklist.");
        }
        try {
            worklist.addCourse(tempCourse);
            System.out.println("\n" + tempCourse.getSubjectCourseCode() + " has been added to worklist.");
        } catch (CourseAlreadyExistsException e) {
            System.err.println("[ERROR] Course adding failed! "
                    + tempCourse.getSubjectCourseCode() + " is already in worklist.");
        } catch (CourseConflictsException e) {
            System.err.println("[ERROR] Course adding failed! "
                    + tempCourse.getSubjectCourseCode() + " conflicts with an existing course.");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads subject, course, and section codes from user input
    private void loadCodes() {
        System.out.println("\nNote: The codes should be in format \"<subject> <course> <section>\". "
                + "E.g., CPSC 210 921");
        System.out.println("Please enter the subject, course, and section codes of the course:");
        input.nextLine();
        String codes = input.nextLine();
        try {
            tempSubjectCode = Course.parseSubjectCode(codes);
            tempCourseCode = Course.parseCourseCode(codes);
            tempSectionCode = Course.parseSectionCode(codes);
        } catch (IllegalCodesException e) {
            System.err.println("[ERROR] Invalid code! Please enter again.");
            loadCodes();
        }
    }

    // MODIFIES: this
    // EFFECTS: loads title of course from user input
    private void loadTitle() {
        System.out.println("\nPlease enter the title of the course:");
        tempTitle = input.nextLine();
    }

    // MODIFIES: this
    // EFFECTS: loads meeting days of course from user input
    private void loadDays() {
        tempDays = new boolean[5];
        System.out.println("\nNote: The meeting days should be five consecutive T/F, with no spaces separated.");
        System.out.println("For example, if the meetings are on Mondays, Wednesdays, and Fridays, "
                + "then enter \"TFTFT\".");
        System.out.println("Please enter the meeting days of the course: ");
        try {
            tempDays = Schedule.parseDays(input.nextLine());
        } catch (IllegalDaysException e) {
            System.err.println("[ERROR] Invalid meeting days! Please enter again.");
            loadDays();
        }
    }

    // MODIFIES: this
    // EFFECTS: loads starting time of course from user input
    private void loadStartTime() {
        System.out.println("\nPlease enter the start time of the course (in 24-hour format):");
        String time = input.nextLine();

        try {
            int hour = Time.parseHour(time);
            int minute = Time.parseMinute(time);
            tempStartTime = new Time(hour, minute);
        } catch (IllegalTimeException e) {
            System.err.println("[ERROR] Invalid time! Please enter again.");
            loadStartTime();
        }
    }

    // MODIFIES: this
    // EFFECTS: loads ending time of course from user input
    private void loadEndTimes() {
        System.out.println("\nPlease enter the end time of the course (in 24-hour format):");
        String time = input.nextLine();

        try {
            int hour = Time.parseHour(time);
            int minute = Time.parseMinute(time);
            tempEndTime = new Time(hour, minute);
        } catch (IllegalTimeException e) {
            System.err.println("[ERROR] Invalid time! Please enter again");
            loadEndTimes();
        }
    }

    // MODIFIES: this
    // EFFECTS: loads schedule of course from temporary values
    private void loadSchedule() {
        try {
            tempSchedule = new Schedule(tempDays, tempStartTime, tempEndTime);
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to create a course schedule! Please check your input and try again.");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads credits of course from user input
    private void loadCredits() {
        System.out.println("\nHow many credit(s) do this course have?");
        try {
            tempCredits = Integer.parseInt(input.nextLine());
            if (tempCredits >= Course.CREDIT_LIMIT) {
                System.out.println("\n[WARNING] Abnormal credit entered! Please make sure the credit is correct.");
            }
        } catch (NumberFormatException e) {
            System.err.println("[ERROR] Invalid credit! Please enter again");
            loadCredits();
        }
    }

    // MODIFIES: this
    // EFFECTS: loads whether the course is required from user input
    private void loadRequired() {
        System.out.println("\nIs this course required for your specialization (T/F)?");
        try {
            tempRequired = Course.parseRequired(input.nextLine());
        } catch (IllegalArgumentException e) {
            System.err.println("[ERROR] Invalid input! Please enter again");
            loadRequired();
        }
    }

    // MODIFIES: this
    // EFFECTS: loads course from temporary values
    private void loadCourse() {
        try {
            tempCourse = new Course(tempSubjectCode, tempCourseCode, tempSectionCode,
                    tempTitle, tempSchedule, tempCredits, tempRequired);
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to create a course! Please check your input and try again.");
        }
    }

    // MODIFIES: this
    // EFFECTS: performs the action of deleting a course
    private void doDeleteCourse() {
        if (worklist.getCourses().isEmpty()) {
            System.err.println("[ERROR] No courses in worklist! Please check your worklist.");
        } else {
            System.out.println("Please enter the subject, course, and section codes of the course you want to delete:");
            input.nextLine();
            String codes = input.nextLine();
            try {
                Course tempCourse = new Course(Course.parseSubjectCode(codes),
                        Course.parseCourseCode(codes), Course.parseSectionCode(codes));
                worklist.deleteCourse(tempCourse);
                System.out.println("\n" + tempCourse.getSubjectCourseCode() + " has been deleted from worklist.");
            } catch (IllegalCodesException e) {
                System.err.println("[ERROR] Invalid code! Please try again.");
                doDeleteCourse();
            } catch (CourseNotFoundException e) {
                System.err.println("[ERROR] Course not found! Please check your worklist.");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: performs the action of starring a course
    private void doStarCourse() {
        if (worklist.getCourses().isEmpty()) {
            System.err.println("[ERROR] No courses in worklist! Please check your worklist.");
        } else {
            System.out.println("Please enter the subject, course, and section codes of the course you want to star:");
            input.nextLine();
            String codes = input.nextLine();
            try {
                Course tempCourse = new Course(Course.parseSubjectCode(codes),
                        Course.parseCourseCode(codes), Course.parseSectionCode(codes));
                worklist.starCourse(tempCourse);
                System.out.println("\n" + tempCourse.getSubjectCourseCode() + " has been starred.");
            } catch (IllegalCodesException e) {
                System.err.println("[ERROR] Invalid code! Please try again.");
                doStarCourse();
            } catch (CourseNotFoundException e) {
                System.err.println("[ERROR] Course not found! Please check your worklist.");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: performs the action of unstarring a course
    private void doUnstarCourse() {
        if (worklist.getCourses().isEmpty()) {
            System.err.println("[ERROR] No courses in worklist! Please check your worklist.");
        } else {
            System.out.println("Please enter the subject, course, and section codes of the course you want to unstar:");
            input.nextLine();
            String codes = input.nextLine();
            try {
                Course tempCourse = new Course(Course.parseSubjectCode(codes),
                        Course.parseCourseCode(codes), Course.parseSectionCode(codes));
                worklist.unstarCourse(tempCourse);
                System.out.println("\n" + tempCourse.getSubjectCourseCode() + " has been unstarred.");
            } catch (IllegalCodesException e) {
                System.err.println("[ERROR] Invalid code! Please try again.");
                doStarCourse();
            } catch (CourseNotFoundException e) {
                System.err.println("[ERROR] Course not found! Please check your worklist.");
            }
        }
    }

    // EFFECTS: prints out the starred courses
    private void printStarredCourses() {
        System.out.println("\n### Starred Courses ###");
        if (worklist.getStarredCourses().isEmpty()) {
            System.out.println("[WARNING] No starred courses in worklist!");
        } else {
            for (Course c : worklist.getStarredCourses()) {
                System.out.println(c.toString());
            }
        }
        System.out.println("\nNote: If you want to make changes to the courses, select 'c' from the main menu.");
    }

    // EFFECTS: prints out the number of all/starred/required/optional courses, courses by subject, and total credits
    private void printStatistics() {
        System.out.println("\n### Worklist Statistics ###");
        if (worklist.getCourses().isEmpty()) {
            System.out.println("[WARNING] No courses in worklist!");
        } else {
            System.out.println("Name: " + worklist.getName());
            System.out.println("Course(s): " + worklist.getCourses().size());
            System.out.println("\tRequired: " + worklist.getNumRequiredCourses());
            System.out.println("\tOptional: " + worklist.getNumOptionalCourses());
            System.out.println("Course(s) by subject: " + worklist.getCourses().size());
            for (String s : worklist.getSubjectCodes()) {
                System.out.println("\t" + s + ": " + worklist.getNumCoursesOfSubject(s));
            }
            System.out.println("Credit(s): " + worklist.getTotalCredits());
        }
    }

    // MODIFIES: this
    // EFFECTS: loads worklist from file
    private void loadWorklist() {
        try {
            worklist = jsonReader.read();
            System.out.println("\nWorklist <" + worklist.getName() + "> has been loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.err.println("[ERROR] Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: saves worklist to file
    private void saveWorklist() {
        try {
            jsonWriter.open();
            jsonWriter.write(worklist);
            jsonWriter.close();
            System.out.println("\nWorklist <" + worklist.getName() + "> has been saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.err.println("[ERROR] Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: prints out help menu of options to user
    private void displayHelpMenu() {
        System.out.println("\n### Help Menu ###");
        System.out.println("c -> All [c]ourses: view, add, delete, star, and modify courses");
        System.out.println("r -> Sta[r]red courses: view starred courses");
        System.out.println("w -> [W]orklist statistics: view detailed course statistics");
        System.out.println("l -> [L]oad worklist from file: "
                + "load existing worklist from file to replace current worklist");
        System.out.println("s -> [S]ave worklist to file: save current worklist to file to replace existing worklist");
        System.out.println("h -> [H]elp: display help menu");
        System.out.println("q -> [Q]uit: quit Course Planner");
    }

}
