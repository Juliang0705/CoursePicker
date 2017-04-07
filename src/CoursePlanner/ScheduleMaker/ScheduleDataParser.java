package CoursePlanner.ScheduleMaker;
/**
 * Created by Juliang on 4/4/16.
 * Updated to bring to working condition by Jeffrey Cordero 12/16/2016
 */
import java.nio.charset.Charset;
import java.time.DayOfWeek;
import java.util.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ScheduleDataParser {
    private ScheduleDataGetter dataGetter;
    private List<FutureCourse> courseList;

    /**
     *
     * @param source a string
     * @return an array of string separately by 9 white spaces
     */
    private List<String> splitData(String source){
        String[] splitedSource = source.split("\\n{9}");
        LinkedList<String> courseDataList = new LinkedList<>(Arrays.asList(splitedSource));
        courseDataList.removeIf((String s) -> {
            return !s.contains("Associated Term:");
        });
        return courseDataList;
    }

    /**
     * parse the data associated with dataGetter to a list of FutureCourse Objects
     * @return a list of FutureCourse Objects
     */
    private List<FutureCourse> parseCourses(){

         /**
         * Parsing rules
         * 1. Lots of lines
         * 2. One line with subject name in it
         * 3. Lots of lines
         * 3. Line with "Instructor:"
         * 4. Instructor name
         * 5. Lots of lines
         * 6. Lines with "Credits" in it
         * 7. Lots of lines
         * 8. Line with time
         * 9. Line with days
         * 10. Line with location
         * 11. Lots of Lines
         * 12. If Line with time occurs again, go to 8, else go to 1.
         */
        List<String> dataList = this.splitData(this.dataGetter.getCleanData());
        List<FutureCourse> futureCourseList = new ArrayList<>();
        try{
            for (String data: dataList){
                if (!data.contains("Traditional"))
                    continue
                String line;
                String instructor = "TBA";
                String course = null;
                String number = null;
                String section = null;
                String term = null;
                int credits = -1;
                List<FutureCourse.TimeInterval> time = new ArrayList<>();
                InputStream stream = new ByteArrayInputStream(data.getBytes(Charset.forName("UTF-8")));
                Scanner in = new Scanner(stream);

                while (in.hasNextLine()){
                    line = in.nextLine();
                    if (line.isEmpty()){
                        continue;
                    }else if (line.contains(this.dataGetter.getSubject()) && course == null){
                        course = this.dataGetter.getSubject();
                        term = this.dataGetter.getTerm();

                        String patternStr = course + "+ +([0-9]{3})+ - +([0-9]{3})";  //Matches the course with the following number and section
                        Pattern pattern = Pattern.compile(patternStr);
                        Matcher matcher = pattern.matcher(line);
                        if(matcher.find()){
                            number = matcher.group(1);
                            section = matcher.group(2);
                            System.out.println("Found Course: " + matcher.group(0)); //TODO: Remove (Testing)
                        }
                    }else if (line.contains("Instructors:")){
                        String fullname[] = in.nextLine().split("\\s+");
                        if (fullname.length == 2){ //  Hyunyoung Lee
                            instructor = fullname[0] + " " + fullname[1];
                        }else if (fullname.length == 3 && !fullname[2].contains("(P)")){ // Hyunyoung j Lee(she doesn't actually have middle name)
                            instructor = fullname[0] + " " + fullname[2];
                        }else if (fullname.length == 3 && fullname[2].contains("(P)")){ // Hyunyoung Lee (P)
                            instructor = fullname[0] + " " + fullname[1];
                        }else if (fullname.length == 4){ //Hyunyoung j Lee (P)
                            instructor = fullname[0] + " " + fullname[2];
                        }
                    }else if (line.contains("Credits")){
                        credits = Integer.parseInt(line.substring(line.indexOf("Credits")-6,line.indexOf("Credits")-5));
                    }else if (line.contains(" am") || line.contains(" pm")){
                        String[] interval = line.split(" - ");
                        int startHour = Integer.parseInt(interval[0].substring(0,interval[0].indexOf(':')));
                        int startMinute = Integer.parseInt(interval[0].substring(interval[0].indexOf(':')+1,interval[0].indexOf(':')+3));
                        if (interval[0].contains("pm") && startHour != 12){
                            startHour += 12;
                        }
                        int endHour = Integer.parseInt(interval[1].substring(0,interval[1].indexOf(':')));
                        int endMinute = Integer.parseInt(interval[1].substring(interval[1].indexOf(':')+1,interval[1].indexOf(':')+3));
                        if (interval[1].contains("pm") && endHour != 12){
                            endHour += 12;
                        }
                        String dayString = in.nextLine();
                        List<DayOfWeek> days= new ArrayList<>();
                        if (dayString.indexOf('M') != -1){
                            days.add(DayOfWeek.MONDAY);
                        }
                        if (dayString.indexOf('T') != -1){
                            days.add(DayOfWeek.TUESDAY);
                        }
                        if (dayString.indexOf('W') != -1){
                            days.add(DayOfWeek.WEDNESDAY);
                        }
                        if (dayString.indexOf('R') != -1){
                            days.add(DayOfWeek.THURSDAY);
                        }
                        if (dayString.indexOf('F') != -1){
                            days.add(DayOfWeek.FRIDAY);
                        }
                        FutureCourse.TimeInterval newTime = new FutureCourse.TimeInterval(startHour,startMinute,endHour,endMinute,days);
                        if (!time.contains(newTime))
                            time.add(newTime);
                    }
                }
                if (instructor != null && course != null && number != null && section != null && term != null
                        && credits != -1 && !time.isEmpty()){
                    futureCourseList.add(new FutureCourse(instructor, course, number, section, term, credits, time));
                }
            }
        }catch(Exception e){
            System.out.println(e);
            System.out.println(futureCourseList.size());
            return null;
        }
        return futureCourseList;
    }


    public ScheduleDataParser(ScheduleDataGetter getter){
        this.dataGetter = getter;
    }

    public List<FutureCourse> getCourseList() throws Exception{
        if (this.courseList == null) {
            this.courseList = parseCourses();
        }
        if (this.courseList == null || this.courseList.isEmpty())
            throw new Exception("Cannot parse course list correctly\n");
        return courseList;
    }
}
