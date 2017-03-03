package com.coursepicker.courseplanner;

/**
 * Created by Jeffrey Cordero on 12/19/2016.
 * Allows for saving and loading course lists
 */
import com.coursepicker.courseplanner.schedulemaker.*;

import java.io.*;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataFileIO {
    private File file;
    private String content;                     //Text of save files
    private User user;                          //User object
    private List<FutureCourse> selectedCourses; //User current course list
    private ScheduleDataGetter dataGetter;

    /**
     * Generates text output of currently selected class list
     * @throws IOException
     */
    public void saveFile() throws IOException{
        //Generate content output string from course list
        selectedCourses = user.getSelectedCourses();
        content = "CoursePicker course list:\n";
        for(FutureCourse course : selectedCourses)
            content += course.getCourse() + " "
                    + course.getNumber() + " "
                    + course.getSection() + " "
                    + course.getTerm() + " "
                    + course.getInstructor() + " "
                    + course.getCredits() + "\n"
                    + course.getSaveTime();

        //Write content to file
        FileWriter fw = new FileWriter(file.getPath());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(content);

        if (bw != null) bw.close();
        if (fw != null) fw.close();
    }

    /**
     * Reads text of save file and uses it to build a new sessions with the saved classes
     * @throws IOException
     */
    public List<FutureCourse> readLoadFile() throws IOException{  //TODO: Clean up
        /*
        * Parse Rules:
        * 1. If line contains subject:
        *           Read for: Course, Number, Section, Term, Instructor, Credits
        * 2. Else (Is time row):
        *           For each (until next subject) add as timeInterval*/

        //Parsing Variables:
        String line;
        String instructor = "TBA";
        String course = null;
        String number = null;
        String section = null;
        String term = null;
        int credits = -1;
        List<DayOfWeek> days = new ArrayList<>();
        List<FutureCourse.TimeInterval> time = new ArrayList<>();
        List<FutureCourse> futureCourseList = new ArrayList<>();

        // Open the file
        FileInputStream fs = new FileInputStream(file.getPath());
        BufferedReader br = new BufferedReader(new InputStreamReader(fs));

        //Read File Line By Line
        while ((line = br.readLine()) != null) {

            if (line.isEmpty() || line == "CoursePicker course list:") continue;
            else if (line.contains("MONDAY")
                    || line.contains("TUESDAY")
                    || line.contains("WEDNESDAY")
                    || line.contains("THURSDAY")
                    || line.contains("FRIDAY")){

                //For each (until next subject) add as timeInterval
                //Create new timeInterval for each line

                //Get line days
                if (line.contains("MONDAY"))       days.add(DayOfWeek.MONDAY);
                if (line.contains("TUESDAY"))      days.add(DayOfWeek.TUESDAY);
                if (line.contains("WEDNESDAY"))    days.add(DayOfWeek.WEDNESDAY);
                if (line.contains("THURSDAY"))     days.add(DayOfWeek.THURSDAY);
                if (line.contains("FRIDAY"))       days.add(DayOfWeek.FRIDAY);


                //Use Regex to parse time:
                String patternStr = "([0-9]+)+ ([0-9]+)+ ([0-9]+)+ ([0-9]+)+";  //4 Integers representing times
                Pattern pattern = Pattern.compile(patternStr);
                Matcher matcher = pattern.matcher(line);

                if(matcher.find()){
                    FutureCourse.TimeInterval newTime = new FutureCourse.TimeInterval(
                            Integer.parseInt(matcher.group(1)),
                            Integer.parseInt(matcher.group(2)),
                            Integer.parseInt(matcher.group(3)),
                            Integer.parseInt(matcher.group(4)),
                            days);
                    if (!time.contains(newTime)) {
                        time.add(newTime);
                        days = new ArrayList<>();
                    }
                }

                //Create a new FutureCourse object if all needed info has been read
                if (instructor != null && course != null && number != null && section != null && term != null
                        && credits != -1 && !time.isEmpty()){
                    futureCourseList.add(new FutureCourse(instructor, course, number, section, term, credits, time));
                    instructor = "TBA";
                    course = null;
                    number = null;
                    section = null;
                    term = null;
                    credits = -1;
                    time = new ArrayList<>();
                }
            } else {
                //Read for: Course, Number, Section, Term, Instructor, Credits
                String patternStr = "(\\w{4}) (\\d{3}) (\\d{3}) (Fall|Spring|Summer|FALL|SPRING|SUMMER) (\\d{4}) ([A-z, ]*) (\\d{1})";  //Strings of day names followed by integers of times
                Pattern pattern = Pattern.compile(patternStr);
                Matcher matcher = pattern.matcher(line);

                if(matcher.find()){
                    course = matcher.group(1);
                    number = matcher.group(2);
                    section = matcher.group(3);
                    term = matcher.group(4) + " " + matcher.group(5);
                    instructor = matcher.group(6);
                    credits = Integer.parseInt(matcher.group(7));
                }
            }
        }
        //Close the input stream
        br.close();
        return futureCourseList;
    }

    /**
     * @param file a File object for path access
     */
    public DataFileIO (File file){
        this.file = file;
        user = User.currentUser();
    }
}
