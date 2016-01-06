/**
 * Created by Juliang on 1/5/16.
 */
import java.util.*;

public class GradeAnalyzer {

    /**
     * this method takes a list of Course objects and combine all the sections that have the same professors
     * honor sections and regular sections are separate
     * @param courseList a List of Courses
     * @return a List of non-duplicate Courses
     * @throws Exception if the list contains different courses
     */
    static List<Course> compress(List<Course> courseList) throws Exception {
        HashMap<String,List<Course>> courseMap = new HashMap<>();
        for (Course c : courseList){
            //add honor course and regular course separately
            if (courseMap.containsKey(c.getInstructor())){
                try {
                    List<Course> instructorList = courseMap.get(c.getInstructor());
                    boolean hasAdded = false;
                    for (Course cls: instructorList){
                        if (cls.isHonor() == c.isHonor()) {
                            cls.add(c);
                            hasAdded = true;
                        }
                    }
                    if (!hasAdded)
                        instructorList.add(c);
                } catch (Exception e) {
                    System.out.println(e);
                    throw new Exception("List contains different courses");
                }
            }else{
                List<Course> newList = new ArrayList<>();
                newList.add(c);
                courseMap.put(c.getInstructor(),newList);
            }
        }
        List<Course> compressedList = new ArrayList<>();
        for (List<Course> list: courseMap.values()){
            for (Course c: list){
                compressedList.add(c);
            }
        }
        return compressedList;
    }
}
