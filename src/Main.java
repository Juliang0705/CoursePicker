/**
 * Created by Juliang on 1/6/16.
 */

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.*;
import javafx.fxml.FXMLLoader;

public class Main extends Application{
    private Parent root;
    @Override
    public void start(Stage primaryStage) throws Exception{
        final FXMLLoader guiLoader = new FXMLLoader(getClass().getResource("CoursePickerGUI.fxml"));
        this.root = guiLoader.load();
        primaryStage.setScene(new Scene(root, 1000, 700));
        primaryStage.setTitle("Course Picker 2.0");
        primaryStage.setResizable(false);
        primaryStage.show();
        CoursePickerGUIController controller = guiLoader.getController();
        controller.init(primaryStage);
        // show it
    }
    public static void main(String[] args) {
        launch(args);
    }
}
