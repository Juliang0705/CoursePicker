package CoursePlanner.ScheduleMaker;

/**
 * Created by Juliang on 4/4/16.
 */
import java.util.*;
import java.time.DayOfWeek;

/**
 * a class that represents the duration of a class such as MWF 11:30 - 12:20
 */
class TimeInterval{
    private int startHour,startMinute, endHour,endMinute;

    private List<DayOfWeek> days;
    TimeInterval(int sh,int sm,int eh,int em,List<DayOfWeek> days){
        startHour = sh;
        startMinute = sm;
        endHour = eh;
        endMinute = em;
        this.days = days;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public int getEndHour() {
        return endHour;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public List<DayOfWeek> getDays() {
        return days;
    }

    @Override
    public String toString(){
        String output = "";
        for (DayOfWeek d : days){
            output += (d + " ");
        }
        output += "\n" + startHour +":"+ startMinute + " - " + endHour +":" + endMinute;
        return output;
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof TimeInterval){
            TimeInterval other = (TimeInterval) o;
            return this.startHour == other.startHour &&
                    this.startMinute == other.startMinute &&
                    this.endHour == other.endHour &&
                    this.endMinute == other.endMinute &&
                    this.days.equals(other.days);
        }else{
            return false;
        }
    }
}

/**
 * an representation of future courses
 */
public class FutureCourse {
    private String instructor, course, number, section, term;
    private int credits;
    private List<TimeInterval> time;

    public FutureCourse(String instructor,String courseName,String number, String section,String term,int credits,
                        List<TimeInterval> time){
        this.instructor = instructor;
        this.course = courseName;
        this.number = number;
        this.section = section;
        this.term = term;
        this.credits = credits;
        this.time = time;
    }
    public List<TimeInterval> getTime() {
        return time;
    }

    public String getInstructor() {
        return instructor;
    }

    public String getCourse() {
        return course;
    }

    public String getNumber() {
        return number;
    }

    public String getSection() {
        return section;
    }

    public String getTerm() {
        return term;
    }

    public int getCredits() {
        return credits;
    }

    @Override
    public String toString(){
        String output = term + "\n" + course + "-" + number + " "+ section + " " +credits +" hours \n" + instructor + "\n";
        for (TimeInterval ti: time)
            output += (ti +"\n");
        return output;
    }
}
