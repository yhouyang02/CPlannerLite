package ui.gui;

import ui.PlannerAppGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents the action listener of the planner
public class PlannerListener implements ActionListener {

    private PlannerManager plannerManager;

    // EFFECTS: constructs a listener to user actions
    public PlannerListener(PlannerAppGUI planner) {
        plannerManager = new PlannerManager(planner);
    }

    // MODIFIES: this
    // EFFECTS: performs the actions when selecting items on the menu bar of planner
    @Override
    public void actionPerformed(ActionEvent e) {
        actionPerformedFile(e);
        actionPerformedView(e);
        actionPerformedCourse(e);
    }

    // MODIFIES: this
    // EFFECTS: performs the actions when selecting items in the menu bar entry "File"
    private void actionPerformedFile(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "new":
                plannerManager.newWorklist();
                break;
            case "load":
                plannerManager.loadWorklist();
                break;
            case "save":
                plannerManager.saveWorklist();
                break;
            case "exit":
                plannerManager.exit();
            default:
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: performs the actions when selecting items in the menu bar entry "View"
    private void actionPerformedView(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "allCourses":
                plannerManager.viewAllCourses();
                break;
            case "starredCourses":
                plannerManager.viewStarredCourses();
                break;
            case "courseStatistics":
                plannerManager.viewCourseStatistics();
                break;
            default:
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: performs the actions when selecting items in the menu bar entry "Course"
    private void actionPerformedCourse(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "add":
                plannerManager.addCourse();
                break;
            case "delete":
                plannerManager.deleteCourse();
                break;
            case "star":
                plannerManager.starCourse();
                break;
            case "unstar":
                plannerManager.unstarCourse();
                break;
            default:
                break;
        }
    }

}
