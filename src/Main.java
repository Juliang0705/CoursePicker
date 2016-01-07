/*import com.sun.tools.javac.util.Pair;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            List<Course> courseList = GradeGetter.getInstance().getCourse("STAT 211",5,true);
            Pair<List<Course>,List<Course>> listPair = GradeAnalyzer.separateByDistinction(courseList);
            List<Course> regularList = listPair.fst;
            GradeAnalyzer.sortByA(regularList);
            List<Course> honorList = listPair.snd;
            for (Course c: regularList){
                System.out.println(c);
            }
            for (Course c: honorList){
                System.out.println(c);
            }
        } catch (Exception e){
            System.out.println("Exception: " + e);
        }
    }
}*/