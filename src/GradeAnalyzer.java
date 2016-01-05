/**
 * Created by Juliang on 1/5/16.
 */
import java.util.*;

public class GradeAnalyzer {
    static List<Course> compress(List<Course> courseList){
        HashMap<String,Course> courseMap = new HashMap<>();
        for (Course c : courseList){
            if (courseMap.containsKey(c.getInstructor())){
                try {
                    courseMap.get(c.getInstructor()).add(c);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }else{
                courseMap.put(c.getInstructor(),c);
            }
        }
        return new ArrayList<>(courseMap.values());
    }
}
