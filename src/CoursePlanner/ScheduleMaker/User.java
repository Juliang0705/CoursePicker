package CoursePlanner.ScheduleMaker;

/**
 * Created by Juliang on 4/10/16.
 */
import java.util.*;

public class User {
    private static User ourInstance = new User();

    public static User currentUser() {
        return ourInstance;
    }

    private List<FutureCourse> selectedCourses;
    public User() {
        selectedCourses = new LinkedList<>();
    }

    /**
     *
     * @return the selected courses
     */
    public List<FutureCourse> getSelectedCourses(){
        return selectedCourses;
    }
    public void addCourse(FutureCourse c) throws Exception{
        for (FutureCourse course: selectedCourses){
            if (!course.getTerm().equals(c.getTerm()))
                throw new Exception("Courses contain different terms.");
            if ((course.getCourse()+course.getNumber()).equals(c.getCourse()+c.getNumber()))
                throw new Exception("Duplicate Course " + course.getCourse() +"-"+ course.getNumber());
            if (course.hasTimeConflict(c))
                throw new Exception("Current Selected Course " + c.toString() +" has time conflict with "+
                course.toString());
        }
        selectedCourses.add(c);
    }
    public void removeCourse(FutureCourse c){
        selectedCourses.remove(c);
    }
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
