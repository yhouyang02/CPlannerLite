package model;

// Represents a university course with course code, subject code, title,
// section number, comments, and its special attributes for a student
public class Course {

    private String subjectCode;
    private String courseCode;
    private String sectionCode;
    private String title;
    private String comments;
    private int credits;
    private boolean required;
    private boolean starred;

    // EFFECTS: constructs a new course with subject code, subject code, course code, title,
    //          section, and whether it is required for the student
    public Course(String subject, String course, String section, String title, int credits, boolean required) {
        // stub
    }

    public String getSubjectCode() {
        return null; // stub
    }

    public String getCourseCode() {
        return null; // stub
    }

    public String getSectionCode() {
        return null; // stub
    }

    public String getTitle() {
        return null; // stub
    }

    public String getComments() {
        return null; //stub
    }

    public int getCredits() {
        return 0; // stub
    }

    public boolean isRequired() {
        return false; // stub
    }

    // MODIFIES: this
    // EFFECTS: sets this.required to required
    public void setRequired(boolean required) {
        // stub
    }

    public boolean isStarred() {
        return false; // stub
    }

    // MODIFIES: this
    // EFFECTS: sets this.starred to true if it is not, then returns true;
    //          otherwise, does nothing and returns false
    public boolean star() {
        return false; // stub
    }

    // MODIFIES: this
    // EFFECTS: sets this.starred to false if it is not, then returns true;
    //          otherwise, does nothing and returns false
    public boolean unstar() {
        return false; // stub
    }

    // EFFECTS: returns a string of format "<subjectCode> <courseCode>";
    //          e.g., "CPSC 210"
    public String getSubjectCourseCode() {
        return null; // stub
    }

    // EFFECTS: returns a string of format "<subjectCode> <courseCode> <sectionCode> - <title>"
    //          e.g., "CPSC 210 921 - Software Construction"
    @Override
    public String toString() {
        return null;
    }

}
