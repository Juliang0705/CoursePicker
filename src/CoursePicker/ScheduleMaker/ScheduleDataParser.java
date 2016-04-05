package CoursePicker.ScheduleMaker;

import java.nio.charset.Charset;
import java.util.*;
import java.io.*;

/**
 * Created by Juliang on 4/4/16.
 */
public class ScheduleDataParser {
    private ScheduleDataGetter dataGetter;
    private List<FutureCourse> parseCourses(){
        InputStream stream = new ByteArrayInputStream(this.dataGetter.getCleanData().getBytes(Charset.forName("UTF-8")));
        Scanner in = new Scanner(stream);
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
        List<FutureCourse> futureCourseList = new ArrayList<>();
        String line;
        String instructor, course,number, section, term;
        int credits;
        List<TimeInterval> time;
        try{
//            while (in.hasNextLine()){ // while there are still some lines left
//
//                do {
//                    line = in.nextLine();
//                }while(!line.contains(this.dataGetter.getSubject()));
//                //the line has the subject and course number
//                course = this.dataGetter.getSubject();
//                number = line.substring(line.indexOf(this.dataGetter.getSubject())+4,line.indexOf(this.dataGetter.getSubject())+7);
//                section = line.substring(line.indexOf(number)+6,line.indexOf(number)+9);
//                term = this.dataGetter.getTerm();
//
//                do{
//                    line = in.nextLine();
//                }while(!line.contains("Instructors:"));
//                instructor = in.next() + " " + in.next();
//
//                do{
//                    line = in.nextLine();
//                }while(!line.contains("Credits"));
//                credits = Integer.parseInt(line.substring(line.indexOf("Credits")-6,line.indexOf("Credits")-5));
//                do {
//                    line = in.nextLine();
//                }while(!(line.contains("am") || line.contains("pm")));
//
//            }
            while (in.hasNextLine()){
                line = in.nextLine();

            }
        }catch(Exception e){
            System.out.println(e);
            return null;
        }
        return futureCourseList;
    }
    public ScheduleDataParser(ScheduleDataGetter getter){
        this.dataGetter = getter;
    }
}
