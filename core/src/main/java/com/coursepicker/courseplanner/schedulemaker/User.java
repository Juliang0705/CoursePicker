package com.coursepicker.courseplanner.schedulemaker;

/**
 * Created by Juliang on 4/10/16.
 * Updated by Jeffrey Cordero on 1/19/2017
 */
import java.util.*;

public class User {
    private static User ourInstance = new User();

    /**
     * Returns current user object (same for entire session)
     * @return ourInstance
     */
    public static User currentUser() {
        return ourInstance;
    }

    //List of selected (added) courses specific to the user
    private List<FutureCourse> selectedCourses;
    public User() {
        selectedCourses = new LinkedList<>();
    }

    /**
     * @return the selected courses
     */
    public List<FutureCourse> getSelectedCourses(){
        return selectedCourses;
    }

    /**
     * Checks for possible conflicts between selected (adding) course and current (selectedCourses) list
     * If no conflicts -> add new course to selectedCourses list
     */
    public void addCourse(FutureCourse c) throws Exception{
        for (FutureCourse course: selectedCourses){
            if ((course.getCourse()+course.getNumber()).equals(c.getCourse()+c.getNumber()))
                throw new Exception("Duplicate Course " + course.getCourse() +"-"+ course.getNumber());
            if (!course.getTerm().equals(c.getTerm()))
                throw new Exception("Selected courses occur in different terms.");
            if (course.hasTimeConflict(c))
                throw new Exception("Current Selected Course " + c.toString() +" has time conflict with "+
                course.toString());
        }
        selectedCourses.add(c);
    }
    public void removeCourse(FutureCourse c){
        selectedCourses.remove(c);
    }

    /**
     * @return ExpectedAverageGPA
     */
    public float getExpectedAverageGPA(){
        int totalHours = 0;
        float totalUnweightedGPA = 0;
        try {
            for (FutureCourse course : selectedCourses) {
                if (course.hasPastCourse()) {
                    totalHours += course.getCredits();
                    totalUnweightedGPA += course.getPastCourse().getAverage() * course.getCredits();
                }
            }
            if (totalHours == 0)
                return 0;
            else
                return totalUnweightedGPA / totalHours;
        }catch(Exception e){
            return 0;
        }
    }

    /**
     * Sums credit hours from selectedCourses list
     * @return totalHours
     */
    public int getTotalCreditHours(){
        int totalHours = 0;
        for (FutureCourse course: selectedCourses){
            totalHours += course.getCredits();
        }
        return totalHours;
    }
    @Override
    public String toString(){
        return "Total Credits: \n" + getTotalCreditHours() + "\n" +
                "Expected Semester GPA: \n" + getExpectedAverageGPA() +"\n";
    }
}
