package ui.gui;

import ui.PlannerAppGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents the action listener of the planner
public class PlannerListener implements ActionListener {

    private PlannerManager plannerManager;
    private Timer timer;

    // EFFECTS: constructs a listener to user actions of the planner
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
                plannerManager.createNewWorklist();
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
                plannerManager.doAllCourses();
                break;
            case "starredCourses":
                plannerManager.doStarredCourses();
                break;
            case "courseStatistics":
                plannerManager.doCourseStatistics();
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
                plannerManager.doAdd();
                break;
            case "delete":
                plannerManager.doDelete();
                break;
            case "star":
                plannerManager.doStar();
                break;
            case "unstar":
                plannerManager.doUnstar();
                break;
            default:
                break;
        }
    }
}
