/**
 * Created by Juliang on 1/4/16.
 * parse the grade reports from tamu registrar
 */

import java.util.*;
import java.io.*;
import java.nio.charset.Charset;

class Course{
    private String instructor, course;
    private int A,B,C,D,F,Q,section;
    public Course(String courseName, int section, String instructor,int A, int B, int C, int D, int F, int Q){
        this.instructor = instructor;
        this.A = A;
        this.B = B;
        this.C = C;
        this.D = D;
        this.F = F;
        this.Q = Q;
        this.course = courseName;
        this.section = section;
    }
    public int total(){
        return A + B + C + D + F + Q;
    }
    public String getInstructor(){
        return this.instructor;
    }
    public String getCourse(){
        return this.course;
    }
    public int getSection(){
        return this.section;
    }
    public int getA(){
        return A;
    }
    public int getB(){
        return B;
    }
    public int getC(){
        return C;
    }
    public int getD(){
        return D;
    }
    public int getF(){
        return F;
    }
    public int getQ(){
        return Q;
    }
    @Override
    public String toString(){
        return course + "-" + section + ": " + instructor + "\n" +
                "A: " + A + " B: " + B + " C: " + C + " D: " + D +
                " F: " + F + " Q: " + Q;
    }
}


public class GradeParser{

    private String data;
    private List<Course> courseList;

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
                while (true) {
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
    private boolean parseToCourses(String source){
        try {
            InputStream stream = new ByteArrayInputStream(source.getBytes(Charset.forName("UTF-8")));
            Scanner in = new Scanner(stream);
            this.courseList = new ArrayList<>();
            String courseName,instructor;
            int section,A,B,C,D,F,Q;
            do {
                String courseAndSection = in.next();
                if (courseAndSection.isEmpty())
                    break;
                courseName = courseAndSection.substring(0, 8);
                section = Integer.parseInt(courseAndSection.substring(9));
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
                System.out.println(course);
                this.courseList.add(course);
            }while (in.hasNext());
        }
        catch (Exception e) {
            this.courseList = null;
            return false;
        }
        return true;
    }

    public GradeParser(String source){
        this.data = source;
    }
    public List<Course> getCourse(String courseAbbr, String number) throws Exception {
        if (this.parse(courseAbbr,number)){
            return this.courseList;
        }else
            throw new Exception("GradeParser Error: Something wrong with the source or the course doesn't exist");
    }

}
