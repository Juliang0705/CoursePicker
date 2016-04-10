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

//public class Main extends Application{
//    private Parent root;
//    @Override
//    public void start(Stage primaryStage) throws Exception{
//        final FXMLLoader guiLoader = new FXMLLoader(getClass().getResource("CoursePickerGUI.fxml"));
//        this.root = guiLoader.load();
//        primaryStage.setScene(new Scene(root, 1000, 700));
//        primaryStage.setTitle("Course Picker");
//        primaryStage.setResizable(false);
//        primaryStage.show();
//        CoursePickerGUIController controller = guiLoader.getController();
//        controller.init();
//        // show it
//    }
//    public static void main(String[] args) {
//        launch(args);
//    }
//}


public class Main {
    public static void main(String[] args) {
        try {
            for (FutureCourse s: SchedulePlanner.getInstance().getCourse("2016",ScheduleDataGetter.Semester.FALL,"CSCE","121")){
                System.out.println("-------------------------------------------------------------");
                System.out.println(s);
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
}

