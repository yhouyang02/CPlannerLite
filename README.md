# CPSC 210 Personal Project:  Course Planner

## Project Proposal

This is a course planning application that allows users to maintain several worklists on their local directories, in
which users may customize and manage their own university courses. The application also allows users to check and
modifies the properties of the courses in their worklists, as well as providing useful statistics and tips for course
registration.

This application is designed to facilitate undergraduate students during their course registration process. As an
undergrad myself, I always spent too many hours in planning my course schedules. I hope this application could help
students to simplify their course planning processes at the start of every term.

*Note: The properties of a course (e.g., course code, course name, credits, etc.) and some test cases are referenced
from
[Vancouver Academic Calendar 2021/22](http://www.calendar.ubc.ca/vancouver/), published by The University of British
Columbia (UBC) Student Services.*

## User Stories

- As a user, I want to be able to add a course to my worklist.
- As a user, I want to be able to delete a course from my worklist.
- As a user, I want to be able to set whether a course is required for my specialization.
- As a user, I want to be able to "star" or "unstar" a course to show my personal preference.
- As a user, I want to be able to view the list of courses in my worklist.
- As a user, I want to be able to view the list of only the starred courses in my worklist.
- As a user, I want to be able to see the number of total credits in my worklist.
- As a user, I want to be able to see the number of courses of each subject in my worklist.
- As a user, I want to be able to receive a reminder when I have added too many courses in my worklist.
- As a user, I want to be able to see the number of required and optional (non-required) courses in my worklist.
- As a user, I want to be able to save my worklist to file.
- As a user, I want to be able to load my worklist from file.
- As a user, I want to be able to have the option to save my worklist to file or not when quitting the application.
- As a user, I want to be able to have the option to load my worklist from file or not when starting the application.

## Phase 4: Task 2 (Robust Design)

### Throwing Exceptions

Exceptions are thrown from `src/main/model` when illegal actions are performed against the worklist (e.g. trying to add
a course conflicting with existing ones, trying to delete a non-existing course from the worklist, trying to add a
course with an impossible schedule, etc.) The below list contains all methods that throw an exception declared
in `src/main/exception`.

- `src/main/model/Time.java`
    - `Time(int hour, int minute)`
        - throws `IllegalHourException` if `hour` is not between 0 and 24
        - throws `IllegalMinuteException` if `minute` is not between 0 and 60
    - `parseHour(String s)` and
    - `parseMinute(String s)`
        - throws `IllegalTimeException` if `s` is not a valid representation of time
- `src/main/model/Schedule.java`
    - `Schedule(boolean[] days, Time start, Time end)` and
    - `parseDays(String s)`
        - throws `IllegalDaysException` if `s` is not a valid representation of meeting days
- `src/main/model/Course.java`
    - `parseSubjectCode(String s)`,
    - `parseCourseCode(String s)`, and
    - `parseSectionCode(String s)`
        - throws `IllegalCodesException` if `s` is not a valid representation of course-related codes
- `src/main/model/Worklist.java`
    - `addCourse(Course course)`
        - throws `CourseAlreadyExistsException` if `course` is already existed in the worklist
        - throws `CourseConflictsException` if the schedule of `course` conflicts with existing courses
    - `deleteCourse(Course course)`,
    - `setRequired(Course course, boolean required)`,
    - `starCourse(Course course)`, and
    - `unstarCourse(Course course)`
        - throws `CourseNotFoundException` if `course` is not found in the worklist

### Testing Exceptions

Exceptions are tested in `src/test/model`. Each test class contains tests for the corresponding class
in `src/main/model`.

### Handling Exceptions

1. Exceptions for the console version are caught and handled in `src/main/ui/PlannerApp.java`.
2. Exceptions for the GUI version are caught and handled in `src/main/ui/gui/PlannerManager.java`. 
