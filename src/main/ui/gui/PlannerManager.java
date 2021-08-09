package ui.gui;

import exception.CourseAlreadyExistsException;
import exception.CourseConflictsException;
import model.Course;
import model.Schedule;
import model.Time;
import model.Worklist;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.PlannerAppGUI;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;

// Represents the worklist manager of the planner, to perform actions on worklist
public class PlannerManager {

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

    private PlannerAppGUI planner;
    private CourseAdder courseAdder;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    // EFFECTS: constructs a manager to manage worklist of the planner
    public PlannerManager(PlannerAppGUI plannerApp) {
        planner = plannerApp;
        courseAdder = new CourseAdder();
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: lets user create a new worklist
    public void newWorklist() {
        JLabel message = new JLabel("Please enter the name of your worklist:");
        String title = "New Worklist...";

        String name = (String) JOptionPane.showInputDialog(planner, message, title,
                JOptionPane.QUESTION_MESSAGE, null, null, "New Worklist");
        if (!name.equals("cancel")) {
            planner.setWorklist(new Worklist(name));
        } else {
            planner.setWorklist(new Worklist("New Worklist"));
        }
        planner.setContentLabel("Worklist: " + planner.getWorklist().getName());
        planner.setContentText(null);
    }

    // MODIFIES: this
    // EFFECTS: loads worklist from file
    public void loadWorklist() {
        try {
            planner.setWorklist(jsonReader.read());

            String message = "Worklist <" + planner.getWorklist().getName() + "> has been loaded from " + JSON_STORE;
            JOptionPane.showMessageDialog(planner, message);
        } catch (IOException e) {
            String message = "Unable to read from file: " + JSON_STORE;
            JOptionPane.showMessageDialog(planner, message, "Error", JOptionPane.ERROR_MESSAGE);
        }
        planner.setContentLabel("Worklist: " + planner.getWorklist().getName());
        planner.setContentText(null);
    }

    // EFFECTS: saves worklist to file
    public void saveWorklist() {
        try {
            jsonWriter.open();
            jsonWriter.write(planner.getWorklist());
            jsonWriter.close();
            String message = "Worklist <" + planner.getWorklist().getName() + "> has been saved to " + JSON_STORE;
            JOptionPane.showMessageDialog(planner, message);
        } catch (FileNotFoundException e) {
            String message = "Unable to write to file: " + JSON_STORE;
            JOptionPane.showMessageDialog(planner, message, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // EFFECTS: lets user choose to save the worklist or not, then exits the planner
    public void exit() {
        JLabel message = new JLabel("Do you want to save your worklist to file?");
        String title = "Exit...";

        int command = JOptionPane.showConfirmDialog(planner, message, title, JOptionPane.YES_NO_OPTION);

        if (command == 0) {
            saveWorklist();
        }

        System.exit(0);
    }

    // MODIFIES: this
    // EFFECTS: displays all courses on planner
    public void doAllCourses() {
        planner.setContentLabel(planner.getWorklist().getName() + " - All Courses");
        StringBuilder content = new StringBuilder();
        if (planner.getWorklist().getCourses().isEmpty()) {
            String message = "No course in worklist!";
            JOptionPane.showMessageDialog(planner, message, "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            for (Course c : planner.getWorklist().getCourses()) {
                content.append(c.toString()).append("\n");
            }
        }
        planner.setContentText(content.toString());
    }

    // MODIFIES: this
    // EFFECTS: displays starred courses on planner
    public void doStarredCourses() {
        planner.setContentLabel(planner.getWorklist().getName() + " - Starred Courses");
        StringBuilder content = new StringBuilder();
        if (planner.getWorklist().getStarredCourses().isEmpty()) {
            String message = "No starred course in worklist!";
            JOptionPane.showMessageDialog(planner, message, "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            for (Course c : planner.getWorklist().getStarredCourses()) {
                content.append(c.toString()).append("\n");
            }
        }
        planner.setContentText(content.toString());
    }

    // MODIFIES: this
    // EFFECTS: displays course statistics on planner
    public void doCourseStatistics() {
        planner.setContentLabel(planner.getWorklist().getName() + " - Course Statistics");
        StringBuilder content = new StringBuilder();
        if (planner.getWorklist().getCourses().isEmpty()) {
            String message = "No course in worklist!";
            JOptionPane.showMessageDialog(planner, message, "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            content.append("Name: ").append(planner.getWorklist().getName()).append("\n");
            content.append("Course(s): ").append(planner.getWorklist().getCourses().size()).append("\n");
            content.append("-------- Required: ").append(planner.getWorklist().getNumRequiredCourses()).append("\n");
            content.append("-------- Optional: ").append(planner.getWorklist().getNumOptionalCourses()).append("\n");
            content.append("Course(s) by subject: \n");
            for (String s : planner.getWorklist().getSubjectCodes()) {
                content.append("-------- ").append(s).append(": ").append(
                        planner.getWorklist().getNumCoursesOfSubject(s)).append("\n");
            }
            planner.setContentText(content.toString());
        }
    }

    // MODIFIES: this
    // EFFECTS: performs the action of adding a course
    public void doAdd() {
        courseAdder = new CourseAdder();
        int command = JOptionPane.showConfirmDialog(planner, courseAdder, "Add Course",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (command == 0) {
            loadCourse();
            try {
                planner.getWorklist().addCourse(tempCourse);
            } catch (CourseAlreadyExistsException e) {
                System.err.println("[ERROR] Course adding failed! "
                        + tempCourse.getSubjectCourseCode() + " is already in worklist.");
            } catch (CourseConflictsException e) {
                System.err.println("[ERROR] Course adding failed! "
                        + tempCourse.getSubjectCourseCode() + " conflicts with an existing course.");
            }
        }
        doAllCourses();
    }

    // MODIFIES: this
    // EFFECTS: loads course information to temporary values from user input,
    //           and loads course from temporary values
    private void loadCourse() {
        loadCodes();
        loadTitle();
        loadDays();
        loadTimes();
        loadSchedule();
        loadCredits();
        loadRequired();
    }

    // MODIFIES: this
    // EFFECTS: loads subject, course, and section codes from user input
    private void loadCodes() {
        
    }

    // MODIFIES: this
    // EFFECTS: loads course title from user input
    private void loadTitle() {

    }

    // MODIFIES: this
    // EFFECTS: loads meeting days from user input
    private void loadDays() {

    }

    // MODIFIES: this
    // EFFECTS: loads starting and ending times from user input
    private void loadTimes() {

    }

    // MODIFIES: this
    // EFFECTS: loads course schedule from temporary values
    private void loadSchedule() {

    }

    // MODIFIES: this
    // EFFECTS: loads course credits from user input
    private void loadCredits() {

    }

    // MODIFIES: this
    // EFFECTS: loads whether the course is required from user input
    private void loadRequired() {

    }

    // EFFECTS: this
    // EFFECTS: performs the action of deleting a course
    public void doDelete() {
        String message;
        String title = "Delete Course";
        Object[] allCodes = planner.getWorklist().getStarredCodes().toArray();
        if (planner.getWorklist().getCourses().isEmpty()) {
            message = "No course in worklist!";
            JOptionPane.showMessageDialog(planner, message, "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            message = "Please choose the course you want to delete:";
            String codes = (String) JOptionPane.showInputDialog(planner, message, title,
                    JOptionPane.QUESTION_MESSAGE, null, allCodes, "New Worklist");
            if (!codes.equals("cancel")) {
                try {
                    Course tempCourse = new Course(Course.parseSubjectCode(codes),
                            Course.parseCourseCode(codes), Course.parseSectionCode(codes));
                    planner.getWorklist().deleteCourse(tempCourse);
                } catch (Exception e) {
                    message = "Failed to delete the course!";
                    JOptionPane.showMessageDialog(planner, message, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        doStarredCourses();
    }

    // MODIFIES: this
    // EFFECTS: performs the action of starring a course
    public void doStar() {
        String message;
        String title = "Star Course";
        Object[] unstarredCodes = planner.getWorklist().getUnstarredCodes().toArray();
        if (planner.getWorklist().getCourses().isEmpty()) {
            message = "No course in worklist!";
            JOptionPane.showMessageDialog(planner, message, "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            message = "Please choose the course you want to star:";
            String codes = (String) JOptionPane.showInputDialog(planner, message, title,
                    JOptionPane.QUESTION_MESSAGE, null, unstarredCodes, "New Worklist");
            if (!codes.equals("cancel")) {
                try {
                    Course tempCourse = new Course(Course.parseSubjectCode(codes),
                            Course.parseCourseCode(codes), Course.parseSectionCode(codes));
                    planner.getWorklist().starCourse(tempCourse);
                } catch (Exception e) {
                    message = "Failed to star the course!";
                    JOptionPane.showMessageDialog(planner, message, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        doStarredCourses();
    }

    // MODIFIES: this
    // EFFECTS: performs the action of unstarring a course
    public void doUnstar() {
        String message;
        String title = "Unstar Course";
        Object[] starredCodes = planner.getWorklist().getStarredCodes().toArray();
        if (planner.getWorklist().getStarredCourses().isEmpty()) {
            message = "No starred course in worklist!";
            JOptionPane.showMessageDialog(planner, message, "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            message = "Please choose the course you want to unstar:";
            String codes = (String) JOptionPane.showInputDialog(planner, message, title,
                    JOptionPane.QUESTION_MESSAGE, null, starredCodes, "New Worklist");
            if (!codes.equals("cancel")) {
                try {
                    Course tempCourse = new Course(Course.parseSubjectCode(codes),
                            Course.parseCourseCode(codes), Course.parseSectionCode(codes));
                    planner.getWorklist().unstarCourse(tempCourse);
                } catch (Exception e) {
                    message = "Failed to unstar the course!";
                    JOptionPane.showMessageDialog(planner, message, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        doStarredCourses();
    }

}

