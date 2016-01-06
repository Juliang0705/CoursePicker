/**
 * Created by Juliang on 1/4/16.
 * parse the grade reports from tamu registrar
 */

import java.util.*;
import java.io.*;
import java.nio.charset.Charset;


public class GradeParser{

    private String data;
    private List<Course> courseList;

    /**
     *
     * @param course a string for course abbreviation
     * @param number a string for section number
     * @return true if the course can be found and the document is in correct format. otherwise false
     */
    private boolean parse(String course, String number){
        int begin = this.data.indexOf(course+"-"+number);
        if (begin == -1){
            return false;
        }else{
            int end = this.data.indexOf("COURSE",begin);
            if (end == -1){
                return false;
            }else{
                String result = this.data.substring(begin,end);
                while (true) { // for courses in multiple page
                    int startOfGarbage = result.indexOf("COLLEGE");
                    if (startOfGarbage != -1) {
                        int endOfGarbage = result.indexOf("STATION", startOfGarbage);
                        if (endOfGarbage == -1)
                            return false;
                        else {
                            result = result.substring(0, startOfGarbage) + result.substring(endOfGarbage+7);
                        }
                    }else {
                        break;
                    }
                }
                return this.parseToCourses(result);
            }
        }
    }

    /**
     *
     * @param source a post-parsed string
     * @return true if the document is formatted correctly
     */
    private boolean parseToCourses(String source){
        try {
            InputStream stream = new ByteArrayInputStream(source.getBytes(Charset.forName("UTF-8")));
            Scanner in = new Scanner(stream);
            this.courseList = new ArrayList<>();
            String courseName,instructor, section;
            int A,B,C,D,F,Q;
            do {
                String courseAndSection = in.next();
                if (courseAndSection.isEmpty())
                    break;
                courseName = courseAndSection.substring(0, 8);
                section = courseAndSection.substring(9);
                float gpa = in.nextFloat();
                instructor = in.nextLine();
                in.next(); // garbage percentage of A
                A = in.nextInt();
                B = in.nextInt();
                C = in.nextInt();
                D = in.nextInt();
                F = in.nextInt();
                in.nextInt(); //garbage total
                in.nextInt(); //garbage incomplete
                in.nextInt();
                in.nextInt();
                Q = in.nextInt();
                in.nextLine();
                in.nextLine();
                Course course = new Course(courseName,section,instructor,A,B,C,D,F,Q);
                this.courseList.add(course);
            }while (in.hasNext());
        }
        catch (Exception e) {
            this.courseList = null;
            return false;
        }
        return true;
    }

    /**
     *
     * @param source a string raw data
     */
    public GradeParser(String source){ this.data = source;}

    /**
     *
     * @param courseAbbr a string such as "MEEN"
     * @param number a string such as "220"
     * @return a list of courses according to the parameters
     * @throws Exception if document cannot be parsed or the course not found
     */
    public List<Course> getCourse(String courseAbbr, String number) throws Exception {
        if (this.parse(courseAbbr,number)){
            return this.courseList;
        }else
            throw new Exception("GradeParser Error: Something wrong with the source or the course doesn't exist");
    }

    /**
     *
     * @param course a string such as "MEEN-121" or "MEEN121"
     * @return a list of courses according to the parameters
     * @throws Exception if document cannot be parsed or the course not found
     */
    public List<Course> getCourse(String course) throws Exception{
        String courseAbbr = course.substring(0,4);
        String number = null;
        if (course.charAt(4) == '-' || course.charAt(4) == ' '){
            number = course.substring(5);
        }else{
            number = course.substring(4);
        }
        return getCourse(courseAbbr,number);
    }

    /**
     *
     * @param courseAbbr a string such as "MEEN"
     * @param number a string such as "220"
     * @return a list of courses according to the parameters
     * @throws Exception if document cannot be parsed or the course not found
     */
    public List<Course> getCourse(String courseAbbr, int number) throws Exception{
        return getCourse(courseAbbr,String.valueOf(number));
    }

}
