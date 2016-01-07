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

    /**
     * separate courses by honor distinction
     * @param courseList a List of Course
     * @return a pair of List of Course. The first one is regular Course. The Second one if honor Course
     */
    static Pair<List<Course>,List<Course>> separateByDistinction(List<Course> courseList){
        List<Course> regularList = new ArrayList<>();
        List<Course> honorList = new ArrayList<>();
        for (Course c : courseList){
            if (c.isHonor())
                honorList.add(c);
            else
                regularList.add(c);
        }
        return new Pair<>(regularList,honorList);
    }

    static void sortByGPA(List<Course> courseList){
        courseList.sort((a,b) -> Float.compare(b.getAverage(),a.getAverage()));
    }
    static void sortByGPAWithQdrop(List<Course> courseList){
        courseList.sort((a,b) -> Float.compare(b.getAverageWithQdrop(),a.getAverageWithQdrop()));
    }
    static void sortByA(List<Course> courseList){
        courseList.sort((a,b) -> Float.compare(b.getAPercentage(),a.getAPercentage()));
    }
    static void sortByB(List<Course> courseList){
        courseList.sort((a,b) -> Float.compare(b.getBPercentage(),a.getBPercentage()));
    }
    static void sortByC(List<Course> courseList){
        courseList.sort((a,b) -> Float.compare(b.getCPercentage(),a.getCPercentage()));
    }
    static void sortByD(List<Course> courseList){
        courseList.sort((a,b) -> Float.compare(b.getDPercentage(),a.getDPercentage()));
    }
    static void sortByF(List<Course> courseList){
        courseList.sort((a,b) -> Float.compare(b.getFPercentage(),a.getFPercentage()));
    }
    static void sortByQ(List<Course> courseList){
        courseList.sort((a,b) -> Float.compare(b.getQPercentage(),a.getQPercentage()));
    }
    static void sortByInstructorName(List<Course> courseList){
        courseList.sort((a,b)->a.getInstructor().compareTo(b.getInstructor()));
    }
}
