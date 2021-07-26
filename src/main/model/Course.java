package model;

// Represents a university course with course code, subject code, title,
// section number, comments, and its special attributes for a student
public class Course {

    private String subjectCode;
    private String courseCode;
    private String sectionCode;
    private String title;
    private String comments;
    private Schedule schedule;
    private int credits;
    private boolean required;
    private boolean starred;

    // EFFECTS: constructs a new course with subject code, subject code, course code,
    //          title, section, and whether it is required for the student, initialized
    //          with empty comments and unstarred
    public Course(String subject, String course, String section, String title,
                  Schedule schedule, int credits, boolean required) {
        this.subjectCode = subject;
        this.courseCode = course;
        this.sectionCode = section;
        this.title = title;
        this.comments = "";
        this.schedule = schedule;
        this.credits = credits;
        this.required = required;
        this.starred = false;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    public String getTitle() {
        return title;
    }

    public String getComments() {
        return comments;
    }

    // MODIFIES: this
    // EFFECTS: sets this.comments to comments
    public void setComments(String comments) {
        this.comments = comments;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public int getCredits() {
        return credits;
    }

    public boolean isRequired() {
        return required;
    }

    // MODIFIES: this
    // EFFECTS: sets this.required to required
    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isStarred() {
        return starred;
    }

    // MODIFIES: this
    // EFFECTS: sets this.starred to true if it is not, then returns true;
    //          otherwise, does nothing and returns false
    public boolean star() {
        if (!starred) {
            starred = true;
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: sets this.starred to false if it is not, then returns true;
    //          otherwise, does nothing and returns false
    public boolean unstar() {
        if (starred) {
            starred = false;
            return true;
        }
        return false;
    }

    // EFFECTS: returns a string of format "<subjectCode> <courseCode>";
    //          e.g., "CPSC 210"
    public String getSubjectCourseCode() {
        return subjectCode + " " + courseCode;
    }

    // EFFECTS: returns a string of format "<subjectCode> <courseCode> <sectionCode> - <title>\t <schedule>"
    //          e.g., "CPSC 210 921 - Software Construction Tue Thu 9:30-13:00"
    @Override
    public String toString() {
        return getSubjectCourseCode() + " " + sectionCode + " - "
                + title + "\t" + schedule.toString();
    }

}
