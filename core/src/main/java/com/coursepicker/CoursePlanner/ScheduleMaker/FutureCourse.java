package com.coursepicker.courseplanner.schedulemaker;

/**
 * Created by Juliang on 4/4/16.
 * Updated for added features by Jeffrey Cordero 12/16/2016
 */
import com.coursepicker.courseplanner.GradeGetter;
import com.coursepicker.courseplanner.PastCourse;

import java.sql.Time;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.*;
import java.time.DayOfWeek;



/**
 * A representation of future courses
 */
public class FutureCourse {

    /**
     * a class that represents the duration of a class such as MWF 11:30 - 12:20
     */
    static public class TimeInterval{
        private int startHour,startMinute, endHour,endMinute;

        private List<DayOfWeek> days;
        public TimeInterval(int sh,int sm,int eh,int em,List<DayOfWeek> days){
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

        public int getTotalStartTimeInMinute(){
            return startHour * 60 + startMinute;
        }
        public int getTotalEndTimeInMinute(){
            return endHour * 60 + endMinute;
        }
        public List<DayOfWeek> getDays() {
            return days;
        }

        /**
         * Compares the time intervals of two courses
         * @param other TimeInterval - Other course to compare with
         * @return bool - If time conflict occurs
         */
        public boolean hasTimeConflict(TimeInterval other){
            for (DayOfWeek day: this.days)
                for (DayOfWeek otherDay: other.days){
                    if (this.getTotalStartTimeInMinute() <= other.getTotalStartTimeInMinute() &&
                            this.getTotalEndTimeInMinute() >= other.getTotalStartTimeInMinute() && day == otherDay)
                        return true;
                }
            return false;
        }

        public void createTimeInterval(int sh,int sm,int eh,int em,List<DayOfWeek> days){
            startHour = sh;
            startMinute = sm;
            endHour = eh;
            endMinute = em;
            this.days = days;
        }

        /**
         * Like toString, though without formatting for file saving
         * @return String
         */
        public String saveString(){
            String output = "";
            for (DayOfWeek d : days){
                output += (d + " ");
            }
            output += startHour +" "+ startMinute + " " + endHour +" " + endMinute;
            return output;
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



    private String instructor, course, number, section, term;
    private int credits;
    private List<TimeInterval> time;
    private boolean hasFetchedPastCourse = false;
    private PastCourse pastCourse;
    private String getShortenInstructorName(){
        try{
            String[] nameSeparatedbySpace = this.instructor.split("\\s+");
            return (nameSeparatedbySpace[1] + " " + nameSeparatedbySpace[0].charAt(0)).toUpperCase();
        }catch (Exception e){
            return "";
        }
    }
    public FutureCourse(String instructor,String courseName,String number, String section,String term,int credits,
                        List<TimeInterval> time){
        this.instructor = instructor;
        this.course = courseName;
        this.number = number;
        this.section = section;
        this.term = term;
        this.credits = credits;
        this.time = time;
        hasPastCourse();
    }
    public List<TimeInterval> getTime() {
        return time;
    }
    public String getSaveTime() {
        String output = "";
        for(TimeInterval t : time)
             output += t.saveString() + "\n";

        return output;
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
    public boolean isHonor(){
        return getSection().startsWith("2");
    }

    /**
     * check if there is past data associated with the instructor.
     * fetch data from server if necessary
     * @return true if there is one.
     */
    public boolean hasPastCourse(){
        if (hasFetchedPastCourse && pastCourse == null){
            return false;
        }else if (pastCourse != null){
            return true;
        }else{
            pastCourse = GradeGetter.getInstance().getCourseByInstructor(this.getShortenInstructorName(),
                                                                        course,Integer.parseInt(number),
                                                                        Integer.parseInt(section));
            hasFetchedPastCourse = true;
            return pastCourse != null;
        }
    }

    /**
     *
     * @return the past course data associated with the instructor
     * @throws Exception if past course doesn't exist
     */
    public PastCourse getPastCourse() throws Exception{
        if (hasPastCourse())
            return this.pastCourse;
        else
            throw new Exception("Past course does not exist");
    }

    /**
     *
     * @param otherCourse another future course object
     * @return true if there is a time conflict
     */
    public boolean hasTimeConflict(FutureCourse otherCourse){
        for (TimeInterval t1: this.getTime())
            for (TimeInterval t2: otherCourse.getTime()) {
                if (t1.hasTimeConflict(t2))
                    return true;
            }
        return false;
    }

    @Override
    public String toString(){
        return course + "-" + number + " " + section;
    }
    public String getDetail(){
        String output = term + "\n\n" + course + "-" + number + " "+ section + " " +credits +" hours \n\n";
        output += "Instructor: \n"+getInstructor();
        output += "\n\nTime:\n\n";
        for (TimeInterval ti: time)
            output += (ti +"\n");
        output += "\nHistorical grade data:\n\n";
        if (hasPastCourse()){
            output += this.pastCourse + "\n";
        }else{
            output += "Unavailable:(\n";
        }
        return output;
    }

}
