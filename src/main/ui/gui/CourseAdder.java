package ui.gui;

import exception.IllegalDaysException;
import exception.IllegalTimeException;
import model.Schedule;
import model.Time;

import javax.swing.*;
import java.awt.*;

import static ui.gui.FontManager.setFonts;

// Represents the popup window for adding a course
public class CourseAdder extends JPanel {

    private JPanel upperInputPanel;
    private JPanel lowerInputPanel;

    private JTextField subjectInputTextField;
    private JTextField courseInputTextField;
    private JTextField sectionInputTextField;
    private JTextField titleInputTextField;
    private JTextField creditInputTextField;
    private JTextField startTimeInputTextField;
    private JTextField endTimeInputTextField;

    private JCheckBox monCheckBox;
    private JCheckBox tueCheckBox;
    private JCheckBox wedCheckBox;
    private JCheckBox thuCheckBox;
    private JCheckBox friCheckBox;

    private JRadioButton requiredButton;
    private JRadioButton notRequiredButton;

    // EFFECTS: constructs a new window for adding a course
    public CourseAdder() {
        setFonts();
        setLayout(new BorderLayout());
        initComponents();
        displayComponents();
    }

    // MODIFIES: this
    // EFFECTS: initializes all components of this window
    private void initComponents() {
        initUpperInputPanel();
        initLowerInputPanel();
    }

    // MODIFIES: this
    // EFFECTS: initializes the upper input panel
    private void initUpperInputPanel() {
        upperInputPanel = new JPanel(new BorderLayout());

        initTextFields();

        JPanel codePanel = new JPanel(new BorderLayout());
        JPanel subjectPanel = createInputPanel("Subject (e.g. CPSC): ", subjectInputTextField);
        JPanel coursePanel = createInputPanel("Course (e.g. 210): ", courseInputTextField);
        JPanel sectionPanel = createInputPanel("Section (e.g. 921): ", sectionInputTextField);
        codePanel.add(subjectPanel, BorderLayout.NORTH);
        codePanel.add(coursePanel, BorderLayout.CENTER);
        codePanel.add(sectionPanel, BorderLayout.SOUTH);

        JPanel titleCreditPanel = new JPanel(new BorderLayout());
        JPanel titlePanel = createInputPanel("Title: ", titleInputTextField);
        JPanel creditPanel = createInputPanel("Credits (e.g. 4): ", creditInputTextField);
        titleCreditPanel.add(titlePanel, BorderLayout.NORTH);
        titleCreditPanel.add(creditPanel, BorderLayout.SOUTH);

        JPanel timePanel = new JPanel(new BorderLayout());
        JPanel startTimeInputPanel = createInputPanel("Starting time (in 24-hour): ", startTimeInputTextField);
        JPanel endTimeInputPanel = createInputPanel("Ending time (in 24-hour): ", endTimeInputTextField);
        timePanel.add(startTimeInputPanel, BorderLayout.NORTH);
        timePanel.add(endTimeInputPanel, BorderLayout.SOUTH);

        upperInputPanel.add(codePanel, BorderLayout.NORTH);
        upperInputPanel.add(titleCreditPanel, BorderLayout.CENTER);
        upperInputPanel.add(timePanel, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: initializes all text fields on this window
    private void initTextFields() {
        subjectInputTextField = new JTextField(10);
        courseInputTextField = new JTextField(10);
        sectionInputTextField = new JTextField(10);
        titleInputTextField = new JTextField(10);
        creditInputTextField = new JTextField(10);
        startTimeInputTextField = new JTextField(10);
        endTimeInputTextField = new JTextField(10);
    }

    // EFFECTS: returns a JPanel with inputLabel and a text field placed on it
    private JPanel createInputPanel(String inputLabelStr, JTextField inputTextField) {
        JPanel inputPanel = new JPanel(new BorderLayout());
        JLabel inputLabel = new JLabel(inputLabelStr);
        inputLabel.setLabelFor(inputTextField);
        inputPanel.add(inputLabel, BorderLayout.WEST);
        inputPanel.add(inputTextField, BorderLayout.EAST);
        return inputPanel;
    }

    // MODIFIES: this
    // EFFECTS: initializes the lower input panel
    private void initLowerInputPanel() {
        lowerInputPanel = new JPanel(new BorderLayout());

        JPanel daysPanel = makeDaysInputPanel();
        JPanel requiredPanel = makeRequiredInputPanel();

        lowerInputPanel.add(daysPanel, BorderLayout.NORTH);
        lowerInputPanel.add(requiredPanel, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: returns initialized input panel for meeting days
    private JPanel makeDaysInputPanel() {
        JPanel daysInputPanel = new JPanel();
        daysInputPanel.setLayout(new BoxLayout(daysInputPanel, BoxLayout.X_AXIS));
        JLabel daysInputLabel = new JLabel("Meeting days: ");
        monCheckBox = new JCheckBox("Mon ");
        tueCheckBox = new JCheckBox("Tue ");
        wedCheckBox = new JCheckBox("Wed ");
        thuCheckBox = new JCheckBox("Thu ");
        friCheckBox = new JCheckBox("Fri ");
        daysInputPanel.add(daysInputLabel);
        daysInputPanel.add(monCheckBox);
        daysInputPanel.add(tueCheckBox);
        daysInputPanel.add(wedCheckBox);
        daysInputPanel.add(thuCheckBox);
        daysInputPanel.add(friCheckBox);
        return daysInputPanel;
    }

    // MODIFIES: this
    // EFFECTS: returns initialized input panel for requirement
    private JPanel makeRequiredInputPanel() {
        JPanel requiredInputPanel = new JPanel();
        requiredInputPanel.setLayout(new BoxLayout(requiredInputPanel, BoxLayout.X_AXIS));
        JLabel requiredInputLabel = new JLabel("Is this course required for your major? ");
        ButtonGroup isRequiredButtonGroup = new ButtonGroup();
        requiredButton = new JRadioButton("Yes");
        notRequiredButton = new JRadioButton("No");
        notRequiredButton.setSelected(true);
        isRequiredButtonGroup.add(requiredButton);
        isRequiredButtonGroup.add(notRequiredButton);
        requiredInputPanel.add(requiredInputLabel);
        requiredInputPanel.add(requiredButton);
        requiredInputPanel.add(notRequiredButton);
        return requiredInputPanel;
    }

    // MODIFIES: this
    // EFFECTS: displays all components of this window
    private void displayComponents() {
        add(upperInputPanel, BorderLayout.NORTH);
        add(lowerInputPanel, BorderLayout.SOUTH);
    }

    // EFFECTS: returns the text entered in subject field
    public String getSubjectText() {
        return subjectInputTextField.getText();
    }

    // EFFECTS: returns the text entered in course field
    public String getCourseText() {
        return courseInputTextField.getText();
    }

    // EFFECTS: returns the text entered in section field
    public String getSectionText() {
        return sectionInputTextField.getText();
    }

    public String getTitleText() {
        return titleInputTextField.getText();
    }

    // EFFECTS: returns the text entered in credits field
    public Schedule getSchedule() {
        Schedule tempSchedule = null;
        try {
            int tempStartHour = Time.parseHour(startTimeInputTextField.getText());
            int tempStartMinute = Time.parseMinute(startTimeInputTextField.getText());
            Time tempStartTime = new Time(tempStartHour, tempStartMinute);
            int tempEndHour = Time.parseHour(endTimeInputTextField.getText());
            int tempEndMinute = Time.parseHour(endTimeInputTextField.getText());
            Time tempEndTime = new Time(tempEndHour, tempEndMinute);
            tempSchedule = new Schedule(getDays(), tempStartTime, tempEndTime);
        } catch (IllegalTimeException e) {
            String message = "Invalid time!";
            JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalDaysException e) {
            String message = "Invalid meeting days!";
            JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
        }
        return tempSchedule;
    }

    // EFFECTS: returns an array of length 5, containing meeting days,
    //          as the checkbox values selected by user
    private boolean[] getDays() {
        boolean[] days = new boolean[5];
        for (int i = 0; i < 5; i++) {
            days[i] = false;
        }
        if (monCheckBox.isSelected()) {
            days[0] = true;
        }
        if (tueCheckBox.isSelected()) {
            days[1] = true;
        }
        if (wedCheckBox.isSelected()) {
            days[2] = true;
        }
        if (thuCheckBox.isSelected()) {
            days[3] = true;
        }
        if (friCheckBox.isSelected()) {
            days[4] = true;
        }
        return days;
    }

    // EFFECTS: returns the number of credit entered in credits field
    public int getCredit() {
        int credit = 0;
        try {
            credit = Integer.parseInt(creditInputTextField.getText());
        } catch (NumberFormatException e) {
            String message = "Invalid credit number!";
            JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
        }
        return credit;
    }

    // EFFECTS: returns true if the "Yes" button for "required" is selected
    public boolean isRequired() {
        return requiredButton.isSelected();
    }

}
