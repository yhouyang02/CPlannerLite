package model;

import exception.IllegalCodesException;
import org.json.JSONObject;
import persistence.Writable;

import java.util.Objects;

// Represents a university course with subject code, course code, section code,
// title, schedule, credits, and its special attributes for the student
public class Course implements Writable {

    public static final int CREDIT_LIMIT = 8;

    private String subjectCode;
    private String courseCode;
    private String sectionCode;
    private String title;
    private Schedule schedule;
    private int credits;
    private boolean required;
    private boolean starred;

    // EFFECTS: constructs a new course with subject code, subject code;
    //          !!! ONLY used for operations on courses
    public Course(String subject, String course, String section) {
        this.subjectCode = subject;
        this.courseCode = course;
        this.sectionCode = section;
    }

    // EFFECTS: constructs a new course with subject code, subject code, course code,
    //          title, section, and whether it is required for the student, initialized
    //          with unstarred
    public Course(String subject, String course, String section, String title,
                  Schedule schedule, int credits, boolean required) {
        this.subjectCode = subject;
        this.courseCode = course;
        this.sectionCode = section;
        this.title = title;
        this.schedule = schedule;
        this.credits = credits;
        this.required = required;
        this.starred = false;
    }

    // EFFECTS: constructs a new course with subject code, subject code, course code,
    //          title, section, whether it is required for the student, and whether it
    //          is starred by the student;
    //          !!! ONLY used to save/load courses
    public Course(String subject, String course, String section, String title,
                  Schedule schedule, int credits, boolean required, boolean starred) {
        this.subjectCode = subject;
        this.courseCode = course;
        this.sectionCode = section;
        this.title = title;
        this.schedule = schedule;
        this.credits = credits;
        this.required = required;
        this.starred = starred;
    }

    // EFFECTS: returns true if s is "T", and false if s is "F", case-insensitive;
    //          throws IllegalArgumentException when s is neither 'T' nor 'F', case-insensitive
    public static boolean parseRequired(String s) throws IllegalArgumentException {
        if (!s.equalsIgnoreCase("T") && !s.equalsIgnoreCase("F")) {
            throw new IllegalArgumentException();
        }
        return s.equalsIgnoreCase("T");
    }

    // EFFECTS: parses string s as codes and returns the subject code;
    //          throws IllegalCodesException if s is not in format "<subject> <course> <section>"
    public static String parseSubjectCode(String s) throws IllegalCodesException {
        checkLegalCodes(s);
        int i1 = s.indexOf(" ");
        return s.substring(0, i1);
    }

    // EFFECTS: parses string s as codes and returns the course code;
    //          throws IllegalCodesException if s is not in format "<subject> <course> <section>"
    public static String parseCourseCode(String s) throws IllegalCodesException {
        checkLegalCodes(s);
        int i1 = s.indexOf(" ");
        int i2 = s.indexOf(" ", i1 + 1);
        return s.substring(i1 + 1, i2);
    }

    // EFFECTS: parses string s as codes and returns the section code;
    //          throws IllegalCodesException if s is not in format "<subject> <course> <section>"
    public static String parseSectionCode(String s) throws IllegalCodesException {
        checkLegalCodes(s);
        int i1 = s.indexOf(" ");
        int i2 = s.indexOf(" ", i1 + 1);
        return s.substring(i2 + 1);
    }

    // EFFECTS: throws IllegalCodeException if s is not in format "<subject> <course> <section>"
    private static void checkLegalCodes(String s) throws IllegalCodesException {
        if (!s.contains(" ") || !s.substring(s.indexOf(" ") + 1).contains(" ")) {
            throw new IllegalCodesException();
        }
    }

    // EFFECTS: returns subject code of this course
    public String getSubjectCode() {
        return subjectCode;
    }

    // EFFECTS: returns course code of this course
    public String getCourseCode() {
        return courseCode;
    }

    // EFFECTS: returns section code of this course
    public String getSectionCode() {
        return sectionCode;
    }

    // EFFECTS: returns title of this course
    public String getTitle() {
        return title;
    }

    // EFFECTS: returns schedule of this course
    public Schedule getSchedule() {
        return schedule;
    }

    // EFFECTS: returns credits of this course
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

    // EFFECTS: returns a string of subject code and course code in format
    //          "<subjectCode> <courseCode>"; e.g., "CPSC 210"
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

    // EFFECTS: returns true if o has the same subject, course,
    //          and section codes as this course, false otherwise
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Course course = (Course) o;
        return subjectCode.equals(course.subjectCode)
                && courseCode.equals(course.courseCode)
                && sectionCode.equals(course.sectionCode);
    }

    // EFFECTS: returns hash code of this course
    @Override
    public int hashCode() {
        return Objects.hash(subjectCode, courseCode, sectionCode);
    }

    // EFFECTS: returns this course as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("subjectCode", subjectCode);
        json.put("courseCode", courseCode);
        json.put("sectionCode", sectionCode);
        json.put("title", title);
        json.put("schedule", schedule.toJson());
        json.put("credits", credits);
        json.put("required", required);
        json.put("starred", starred);
        return json;
    }

}
