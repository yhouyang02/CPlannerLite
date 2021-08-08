package ui;

import model.Worklist;
import ui.gui.MenuBar;
import ui.gui.PlannerListener;
import ui.gui.PlannerManager;

import javax.swing.*;
import java.awt.*;

import static ui.gui.FontManager.setFonts;

// Represents the course planner application with GUI
// Note: GUI is adjusted from DrawingEditor.java and The Java™ Tutorials; Linked below:
// https://github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Complete/blob/master/src/ui/DrawingEditor.java
// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
public class PlannerAppGUI extends JFrame {

    // Dimensions of the content area
    private static final int ROWS = 30;
    private static final int COLUMNS = 40;

    private MenuBar menuBar;
    private JPanel contentPanel;
    private JLabel contentLabel;
    private JTextArea contentText;
    private JScrollPane scrollPane;

    private Worklist worklist;
    private PlannerListener plannerListener;
    private PlannerManager plannerManager;

    // EFFECTS: initializes and runs the application
    public PlannerAppGUI() {
        super("Course Planner Lite");
        initFields();
        initGraphics();
        start();
    }

    // MODIFIES: this
    // EFFECTS:  creates a default worklist, and initializes listener and manager
    private void initFields() {
        worklist = new Worklist("New Worklist");
        plannerListener = new PlannerListener(this);
        plannerManager = new PlannerManager(this);
    }

    // MODIFIES: this
    // EFFECTS:  draws the JFrame window where this PlannerApp will operate, and populates the tools to be used
    //           to perform actions on this worklist
    private void initGraphics() {
        setFonts();
        setLayout(new BorderLayout());
        initComponents();
        displayComponents();
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes all components of the application
    private void initComponents() {
        menuBar = new MenuBar(plannerListener);

        // Creates the content panel for label and text
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setOpaque(true);

        // Creates the label of the text
        contentLabel = new JLabel("Welcome to Course Planner!");
        contentLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Creates a scrollable text area
        contentText = new JTextArea(ROWS, COLUMNS);
        contentText.setEditable(false);
        scrollPane = new JScrollPane(contentText);

        // Adds content label and text area to the panel
        contentPanel.add(contentLabel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: displays all components of the application
    private void displayComponents() {
        setJMenuBar(menuBar);
        add(contentPanel);
    }

    // MODIFIES: this
    // EFFECTS: displays the start menu and lets user load or create a worklist
    private void start() {
        JLabel message = new JLabel("Do you want to load existing worklist from file?");
        String title = "Welcome to Course Planner!";

        int command = JOptionPane.showConfirmDialog(this,
                message, title, JOptionPane.YES_NO_OPTION);

        if (command == JOptionPane.YES_OPTION) {
            plannerManager.loadWorklist();
        } else {
            plannerManager.createNewWorklist();
        }
    }

    // EFFECTS: returns current worklist of this planner
    public Worklist getWorklist() {
        return worklist;
    }

    // MODIFIES: this
    // EFFECTS: sets current worklist to worklist
    public void setWorklist(Worklist worklist) {
        this.worklist = worklist;
    }

    // MODIFIES: this
    // EFFECTS: sets content label to label
    public void setContentLabel(String label) {
        contentLabel.setText(label);
    }

    // MODIFIES: this
    // EFFECTS: sets content text to text
    public void setContentText(String text) {
        contentText.setText(text);
    }
}
