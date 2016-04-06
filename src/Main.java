/**
 * Created by Juliang on 1/6/16.
 */

import CoursePicker.*;
import java.util.*;
import CoursePicker.ScheduleMaker.*;

public class Main {
    public static void main(String[] args) {
        try {
            ScheduleDataGetter getter = new ScheduleDataGetter("2016",3,"ACCT");
           // System.out.println(getter.getCleanData());
            ScheduleDataParser parser = new ScheduleDataParser(getter);
            for (FutureCourse s: parser.getCourseList()){
                System.out.println("-------------------------------------------------------------");
                System.out.println(s);
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
