/**
 * Created by Juliang on 1/6/16.
 */

import CoursePlanner.ScheduleMaker.*;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.*;
import javafx.fxml.FXMLLoader;
import jfxtras.scene.control.agenda.*;
import java.time.LocalDate;

public class Main extends Application{
    private Parent root;
    @Override
    public void start(Stage primaryStage) throws Exception{
        final FXMLLoader guiLoader = new FXMLLoader(getClass().getResource("CoursePickerGUI.fxml"));
        this.root = guiLoader.load();
        primaryStage.setScene(new Scene(root, 1000, 700));
        primaryStage.setTitle("Course Picker");
        primaryStage.setResizable(false);
        primaryStage.show();
        CoursePickerGUIController controller = guiLoader.getController();
        controller.init();
//        scheduleView.appointments().addAll(
//                new Agenda.AppointmentImplLocal()
//                        .withStartLocalDateTime(LocalDate.now().atTime(4, 00))
//                        .withEndLocalDateTime(LocalDate.now().atTime(15, 30))
//                        .withDescription("It's time")
//                        .withAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group1")) // you should use a map of AppointmentGroups
//        );

        // show it
    }
    public static void main(String[] args) {
        launch(args);
    }
}


//public class Main {
//    public static void main(String[] args) {
//        try {
//            ScheduleDataGetter getter = new ScheduleDataGetter("2016",3,"CSCE");
//            ScheduleDataParser parser = new ScheduleDataParser(getter);
//            for (FutureCourse s: parser.getCourseList()){
//                System.out.println("-------------------------------------------------------------");
//                System.out.println(s);
//            }
//        }catch(Exception e){
//            System.out.println(e);
//        }
//    }
//}

