package com.coursepicker.courseplanner;
/**
 * Created by Juliang on 1/5/16.
 */
import java.util.*;

public class GradeAnalyzer {

    /**
     * this method takes a list of CoursePicker.PastCourse objects and combine all the sections that have the same professors
     * honor sections and regular sections are separate
     * @param pastCourseList a List of Courses
     * @return a List of non-duplicate Courses
     * @throws Exception if the list contains different courses
     */
    static public List<PastCourse> compress(List<PastCourse> pastCourseList) throws Exception {
        HashMap<String,List<PastCourse>> courseMap = new HashMap<>();
        for (PastCourse c : pastCourseList){
            //add honor course and regular course separately
            if (courseMap.containsKey(c.getInstructor())){
                try {
                    List<PastCourse> instructorList = courseMap.get(c.getInstructor());
                    boolean hasAdded = false;
                    for (PastCourse cls: instructorList){
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
                List<PastCourse> newList = new ArrayList<>();
                newList.add(c);
                courseMap.put(c.getInstructor(),newList);
            }
        }
        List<PastCourse> compressedList = new ArrayList<>();
        for (List<PastCourse> list: courseMap.values()){
            for (PastCourse c: list){
                compressedList.add(c);
            }
        }
        return compressedList;
    }

    /**
     * separate courses by honor distinction
     * @param pastCourseList a List of CoursePicker.PastCourse
     * @return a pair of List of CoursePicker.PastCourse. The first one is regular CoursePicker.PastCourse. The Second one if honor CoursePicker.PastCourse
     */
    static public Pair<List<PastCourse>,List<PastCourse>> separateByDistinction(List<PastCourse> pastCourseList){
        List<PastCourse> regularList = new ArrayList<>();
        List<PastCourse> honorList = new ArrayList<>();
        for (PastCourse c : pastCourseList){
            if (c.isHonor())
                honorList.add(c);
            else
                regularList.add(c);
        }
        return new Pair<>(regularList,honorList);
    }

    static public void  sortByGPA(List<PastCourse> pastCourseList){
        pastCourseList.sort((a, b) -> Float.compare(b.getAverage(),a.getAverage()));
    }
    static public void sortByGPAWithQdrop(List<PastCourse> pastCourseList){
        pastCourseList.sort((a, b) -> Float.compare(b.getAverageWithQdrop(),a.getAverageWithQdrop()));
    }
    static public void sortByA(List<PastCourse> pastCourseList){
        pastCourseList.sort((a, b) -> Float.compare(b.getAPercentage(),a.getAPercentage()));
    }
    static public void sortByB(List<PastCourse> pastCourseList){
        pastCourseList.sort((a, b) -> Float.compare(b.getBPercentage(),a.getBPercentage()));
    }
    static public void sortByC(List<PastCourse> pastCourseList){
        pastCourseList.sort((a, b) -> Float.compare(b.getCPercentage(),a.getCPercentage()));
    }
    static public void sortByD(List<PastCourse> pastCourseList){
        pastCourseList.sort((a, b) -> Float.compare(b.getDPercentage(),a.getDPercentage()));
    }
    static public void sortByF(List<PastCourse> pastCourseList){
        pastCourseList.sort((a, b) -> Float.compare(b.getFPercentage(),a.getFPercentage()));
    }
    static public void sortByQ(List<PastCourse> pastCourseList){
        pastCourseList.sort((a, b) -> Float.compare(b.getQPercentage(),a.getQPercentage()));
    }
    static public void sortByInstructorName(List<PastCourse> pastCourseList){
        pastCourseList.sort((a, b)->a.getInstructor().compareTo(b.getInstructor()));
    }
}
