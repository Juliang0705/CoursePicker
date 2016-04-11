package CoursePlanner.ScheduleMaker;

/**
 * Created by Juliang on 4/10/16.
 */
import java.util.*;
import java.util.concurrent.Future;

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
            if (course.hasTimeConflict(c))
                throw new Exception("Current Selected Course " + c.getCourse() + c.getNumber() +" has time conflict with "+
                course.getCourse() + course.getNumber());
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
            return totalUnweightedGPA / totalHours;
        }catch(Exception e){
            System.out.println(e);
            return 0;
        }
    }
}
