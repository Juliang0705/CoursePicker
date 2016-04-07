/**
 * Created by Juliang on 4/7/16.
 */
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.*;
import javafx.fxml.FXMLLoader;
import jfxtras.scene.control.agenda.*;

import java.time.LocalDateTime;

public class CoursePickerGUIController {
    //elements from fxml
    @FXML
    private TextField yearInputBox;
    @FXML
    private ChoiceBox<String> semesterDropDownList;
    @FXML
    private ChoiceBox<String> subjectDropDownList;
    @FXML
    private TextField courseNumberField;
    @FXML
    private Button fetchDataButton;
    @FXML
    private Button helpButton;
    @FXML
    private Button infoButton;
    @FXML
    private ListView<String> coursesListView;
    @FXML
    private TextArea courseDetailTextView;
    @FXML
    private Button addCourseButton;
    @FXML
    private Pane schedulePane;
    @FXML
    private Pane graphPane;
    @FXML
    private RadioButton pieChartToggleButton;
    @FXML
    private RadioButton barChartToggleButton;
    @FXML
    private ListView<String> selectedCourseListView;
    @FXML
    private Button deleteCourseButton;
    @FXML
    private TextArea summaryTextView;
    private Agenda scheduleView = new Agenda();

    private void initGUI(){
        scheduleView.setMaxSize(schedulePane.getWidth(),schedulePane.getHeight());
        schedulePane.getChildren().addAll(scheduleView);
    }
    private void initAction(){

    }
    public void init(){
        initGUI();
        initAction();
    }
}
