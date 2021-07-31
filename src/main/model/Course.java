package model;

import exception.IllegalCodesException;

// Represents a university course with course code, subject code, title,
// section number, comments, and its special attributes for a student
public class Course {

    public static final int CREDIT_LIMIT = 8;

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

    // EFFECTS: returns true when s is "T", and false when s is "F", case-insensitive;
    //          throws IllegalArgumentException when s is neither 'T' nor 'F', case-insensitive
    public static boolean parseRequired(String s) throws IllegalArgumentException {
        if (!s.equalsIgnoreCase("T") && !s.equalsIgnoreCase("F")) {
            throw new IllegalArgumentException();
        }
        return s.equalsIgnoreCase("T");
    }

    // EFFECTS: parses string s as codes and returns the subject code;
    //          throws IllegalCodesException when s is not in format <subject> <course> <section>
    public static String parseSubjectCode(String s) throws IllegalCodesException {
        checkLegalCodes(s);
        int i1 = s.indexOf(" ");
        return s.substring(0, i1);
    }

    // EFFECTS: parses string s as codes and returns the course code;
    //          throws IllegalCodesException when s is not in format <subject> <course> <section>
    public static String parseCourseCode(String s) throws IllegalCodesException {
        checkLegalCodes(s);
        int i1 = s.indexOf(" ");
        int i2 = s.indexOf(" ", i1 + 1);
        return s.substring(i1 + 1, i2);
    }

    // EFFECTS: parses string s as codes and returns the section code;
    //          throws IllegalCodesException when s is not in format <subject> <course> <section>
    public static String parseSectionCode(String s) throws IllegalCodesException {
        checkLegalCodes(s);
        int i1 = s.indexOf(" ");
        int i2 = s.indexOf(" ", i1 + 1);
        return s.substring(i2 + 1);
    }

    // EFFECTS: throws IllegalCodeException when s is not in format <subject> <course> <section>;
    //          otherwise, does nothing
    private static void checkLegalCodes(String s) throws IllegalCodesException {
        if (!s.contains(" ") || !s.substring(s.indexOf(" ") + 1).contains(" ")) {
            throw new IllegalCodesException();
        }
    }

    // EFFECTS: returns the subject code of this course
    public String getSubjectCode() {
        return subjectCode;
    }

    // EFFECTS: returns the course code of this course
    public String getCourseCode() {
        return courseCode;
    }

    // EFFECTS: returns the section code of this course
    public String getSectionCode() {
        return sectionCode;
    }

    // EFFECTS: returns the title of this course
    public String getTitle() {
        return title;
    }

    // EFFECTS: returns the comment of this course
    public String getComments() {
        return comments;
    }

    // MODIFIES: this
    // EFFECTS: sets this.comments to comments
    public void setComments(String comments) {
        this.comments = comments;
    }

    // EFFECTS: returns the schedule of this course
    public Schedule getSchedule() {
        return schedule;
    }

    // EFFECTS: returns the credits of this course
    public int getCredits() {
        return credits;
    }

    // EFFECTS: returns true if the course is required for the user's specialization, false otherwise
    public boolean isRequired() {
        return required;
    }

    // MODIFIES: this
    // EFFECTS: sets this.required to required
    public void setRequired(boolean required) {
        this.required = required;
    }

    // EFFECTS: returns true if the course is starred by the user
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
