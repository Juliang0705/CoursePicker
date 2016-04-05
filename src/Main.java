/**
 * Created by Juliang on 1/6/16.
 */

import CoursePicker.*;
import java.util.*;
import java.util.function.Predicate;

import CoursePicker.ScheduleMaker.*;
public class Main {
    public static void main(String[] args) {
        try {
            ScheduleDataGetter getter = new ScheduleDataGetter("2016",3,"CSCE");
            String data = getter.getCleanData();
            String[] splitedData = data.split("\\n{9}");
            LinkedList<String> courseList = new LinkedList<>(Arrays.asList(splitedData));
            courseList.removeIf(new Predicate<String>() {
                @Override
                public boolean test(String s) {
                    return !s.contains("Associated Term:");
                }
            });

            for (String s: courseList){
                System.out.println("-------------------------------------------------------------");
                System.out.println(s);
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
