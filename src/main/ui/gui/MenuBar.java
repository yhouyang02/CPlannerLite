package ui.gui;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

// Represents the menu bar of main window
public class MenuBar extends JMenuBar {

    private JMenu menu;
    private JMenuItem menuItem;
    private PlannerListener listener;

    // EFFECTS: constructs the menu bar for main window and sets up a listener
    public MenuBar(PlannerListener listener) {
        super();
        this.listener = listener;
        initMenuFile();
        initMenuView();
        initMenuCourse();
    }

    // MODIFIES: this
    // EFFECTS: initializes the menu bar entry "File"
    private void initMenuFile() {
        menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);
        add(menu);

        menuItem = new JMenuItem("New...", KeyEvent.VK_N);
        initMenuItem(menuItem, KeyEvent.VK_N, InputEvent.CTRL_MASK, "new", menu);

        menuItem = new JMenuItem("Load...", KeyEvent.VK_L);
        initMenuItem(menuItem, KeyEvent.VK_L, InputEvent.CTRL_MASK, "load", menu);


        menuItem = new JMenuItem("Save...", KeyEvent.VK_S);
        initMenuItem(menuItem, KeyEvent.VK_S, InputEvent.CTRL_MASK, "save", menu);

        menu.addSeparator();

        menuItem = new JMenuItem("Exit", KeyEvent.VK_E);
        initMenuItem(menuItem, KeyEvent.VK_E, InputEvent.CTRL_MASK, "exit", menu);
    }

    // MODIFIES: this
    // EFFECTS: initializes the menu bar entry "View"
    private void initMenuView() {
        menu = new JMenu("View");
        menu.setMnemonic(KeyEvent.VK_V);
        add(menu);

        menuItem = new JMenuItem("All Courses", KeyEvent.VK_A);
        initMenuItem(menuItem, KeyEvent.VK_F1, "allCourses", menu);

        menuItem = new JMenuItem("Starred Courses", KeyEvent.VK_S);
        initMenuItem(menuItem, KeyEvent.VK_F2, "starredCourses", menu);

        menuItem = new JMenuItem("Course Statistics");
        initMenuItem(menuItem, KeyEvent.VK_F3, "courseStatistics", menu);
    }

    // MODIFIES: this
    // EFFECTS: initializes the menu bar entry "Course"
    private void initMenuCourse() {
        menu = new JMenu("Course");
        menu.setMnemonic(KeyEvent.VK_C);
        add(menu);

        menuItem = new JMenuItem("Add...", KeyEvent.VK_A);
        initMenuItem(menuItem, KeyEvent.VK_A, InputEvent.CTRL_MASK, "add", menu);

        menuItem = new JMenuItem("Delete...", KeyEvent.VK_D);
        initMenuItem(menuItem, KeyEvent.VK_D, InputEvent.CTRL_MASK, "delete", menu);

        menuItem = new JMenuItem("Star...", KeyEvent.VK_S);
        initMenuItem(menuItem, KeyEvent.VK_S, InputEvent.CTRL_MASK + InputEvent.ALT_MASK, "star", menu);

        menuItem = new JMenuItem("Unstar...", KeyEvent.VK_U);
        initMenuItem(menuItem, KeyEvent.VK_U, InputEvent.CTRL_MASK + InputEvent.ALT_MASK, "unstar", menu);

    }

    // MODIFIES: this, menuItem, menu
    // EFFECTS: initializes the identifier and accelerator of menuItem,
    //          adds listener to it, and adds it to menu
    private void initMenuItem(JMenuItem menuItem, int keyCode, String actionCommand, JMenu menu) {
        menuItem.setAccelerator(KeyStroke.getKeyStroke(keyCode, 0));
        menuItem.setActionCommand(actionCommand);
        menuItem.addActionListener(listener);
        menu.add(menuItem);
    }

    // MODIFIES: this, menuItem, menu
    // EFFECTS: initializes the identifier and accelerator of menuItem,
    //          adds listener to it, and adds it to menu
    private void initMenuItem(JMenuItem menuItem, int keyCode, int modifiers,
                              String actionCommand, JMenu menu) {
        menuItem.setAccelerator(KeyStroke.getKeyStroke(keyCode, modifiers));
        menuItem.setActionCommand(actionCommand);
        menuItem.addActionListener(listener);
        menu.add(menuItem);
    }

}
