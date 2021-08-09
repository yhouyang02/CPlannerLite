package ui.gui;

import exception.CourseAlreadyExistsException;
import exception.CourseConflictsException;
import model.Course;
import model.Worklist;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.PlannerAppGUI;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

// Represents the worklist manager of the planner, to perform actions on worklist
public class PlannerManager {

    // Path of JSON file for loading and saving worklist
    private static final String JSON_STORE = "./data/worklist.json";

    // Temporary course information for adding a new course
    private Course tempCourse;

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
            Toolkit.getDefaultToolkit().beep();
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
            Toolkit.getDefaultToolkit().beep();
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
            Toolkit.getDefaultToolkit().beep();
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
            Toolkit.getDefaultToolkit().beep();
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
            Toolkit.getDefaultToolkit().beep();
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
                Toolkit.getDefaultToolkit().beep();
                String message = "Course adding failed! "
                        + tempCourse.getSubjectCourseCode() + " is already in worklist.";
                JOptionPane.showMessageDialog(planner, message, "Error", JOptionPane.ERROR_MESSAGE);
            } catch (CourseConflictsException e) {
                Toolkit.getDefaultToolkit().beep();
                String message = "Course adding failed! "
                        + tempCourse.getSubjectCourseCode() + " conflicts with an existing course.";
                JOptionPane.showMessageDialog(planner, message, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        doAllCourses();
    }

    // MODIFIES: this
    // EFFECTS: loads course information to temporary values from user input,
    //           and loads course from temporary values
    private void loadCourse() {
        tempCourse = new Course(
                courseAdder.getSubjectText(),
                courseAdder.getCourseText(),
                courseAdder.getSectionText(),
                courseAdder.getTitleText(),
                courseAdder.getSchedule(),
                courseAdder.getCredit(),
                courseAdder.isRequired()
        );
    }

    // EFFECTS: this
    // EFFECTS: performs the action of deleting a course
    public void doDelete() {
        String title = "Delete Course";
        Object[] allCodes = planner.getWorklist().getAllCodes().toArray();
        if (planner.getWorklist().getCourses().isEmpty()) {
            Toolkit.getDefaultToolkit().beep();
            String message = "No course in worklist!";
            JOptionPane.showMessageDialog(planner, message, "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            String message = "Please choose the course you want to delete:";
            String codes = (String) JOptionPane.showInputDialog(planner, message, title,
                    JOptionPane.QUESTION_MESSAGE, null, allCodes, "New Worklist");
            if (!codes.equals("cancel")) {
                try {
                    Course tempCourse = new Course(Course.parseSubjectCode(codes),
                            Course.parseCourseCode(codes), Course.parseSectionCode(codes));
                    planner.getWorklist().deleteCourse(tempCourse);
                } catch (Exception e) {
                    Toolkit.getDefaultToolkit().beep();
                    message = "Failed to delete the course!";
                    JOptionPane.showMessageDialog(planner, message, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        doAllCourses();
    }

    // MODIFIES: this
    // EFFECTS: performs the action of starring a course
    public void doStar() {
        String title = "Star Course";
        Object[] unstarredCodes = planner.getWorklist().getUnstarredCodes().toArray();
        if (planner.getWorklist().getCourses().isEmpty()) {
            Toolkit.getDefaultToolkit().beep();
            String message = "No course in worklist!";
            JOptionPane.showMessageDialog(planner, message, "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            String message = "Please choose the course you want to star:";
            String codes = (String) JOptionPane.showInputDialog(planner, message, title,
                    JOptionPane.QUESTION_MESSAGE, null, unstarredCodes, "New Worklist");
            if (!codes.equals("cancel")) {
                try {
                    Course tempCourse = new Course(Course.parseSubjectCode(codes),
                            Course.parseCourseCode(codes), Course.parseSectionCode(codes));
                    planner.getWorklist().starCourse(tempCourse);
                } catch (Exception e) {
                    Toolkit.getDefaultToolkit().beep();
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
        String title = "Unstar Course";
        Object[] starredCodes = planner.getWorklist().getStarredCodes().toArray();
        if (planner.getWorklist().getStarredCourses().isEmpty()) {
            Toolkit.getDefaultToolkit().beep();
            String message = "No starred course in worklist!";
            JOptionPane.showMessageDialog(planner, message, "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            String message = "Please choose the course you want to unstar:";
            String codes = (String) JOptionPane.showInputDialog(planner, message, title,
                    JOptionPane.QUESTION_MESSAGE, null, starredCodes, "New Worklist");
            if (!codes.equals("cancel")) {
                try {
                    Course tempCourse = new Course(Course.parseSubjectCode(codes),
                            Course.parseCourseCode(codes), Course.parseSectionCode(codes));
                    planner.getWorklist().unstarCourse(tempCourse);
                } catch (Exception e) {
                    Toolkit.getDefaultToolkit().beep();
                    message = "Failed to unstar the course!";
                    JOptionPane.showMessageDialog(planner, message, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        doStarredCourses();
    }

}

