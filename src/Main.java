/**
 * Created by Juliang on 1/6/16.
 */

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.*;
import CoursePicker.*;

public class Main extends Application {
    private Pane root;
    private Label inputLabel;
    private TextField inputTextField;
    private Button inputButton;
    private Label yearsLabel;
    private ComboBox<Integer> yearsDropDownList;
    private Label instructorLabel;
    private ListView<String> instructorList;
    private String currentInstructor;
    private HashMap<String,PastCourse> instructorMap;
    private List<PastCourse> courseList;
    private Label exceptionLabel;
    private Chart chartGraph;
    private ToggleGroup chartToggle;
    private RadioButton pieButton;
    private RadioButton barButton;
    private Label sortLabel;
    private ComboBox<String> sortDropDownList;
    private Label gpaLabel;
    private void setPos(Node n, double x, double y){
        n.setLayoutX(x);
        n.setLayoutY(y);
    }
    private void initUI(Pane root){
        //input label
        this.inputLabel = new Label("Course Name:");
        setPos(this.inputLabel,100,30);
        //input textField
        this.inputTextField = new TextField();
        this.inputTextField.setPrefSize(80,30);
        this.inputTextField.setText("MATH-151");
        setPos(this.inputTextField,200,25);
        //year label
        this.yearsLabel = new Label("Year: ");
        setPos(this.yearsLabel,290,30);
        //year ComboBox
        ObservableList<Integer> options = FXCollections.observableArrayList();
        for (int i = 1; i <= Calendar.getInstance().get(Calendar.YEAR) - 2010; ++i)
            options.add(i);
        this.yearsDropDownList = new ComboBox<>(options);
        this.yearsDropDownList.setValue(3);
        setPos(this.yearsDropDownList,330,25);
        //get data button
        this.inputButton = new Button("Get Data");
        setPos(this.inputButton,390,25);
        //instructor label
        this.instructorLabel = new Label("Instructors");
        setPos(this.instructorLabel,30,60);
        //instructor list
        this.instructorList = new ListView<>();
        this.instructorList.setPrefSize(150,250);
        setPos(this.instructorList,20,80);
        //exception label
        this.exceptionLabel = new Label("");
        this.exceptionLabel.setTextFill(Color.RED);
        setPos(this.exceptionLabel,390,60);
        //toggle button
        this.chartToggle = new ToggleGroup();
        this.pieButton = new RadioButton("Pie Chart");
        this.barButton = new RadioButton("Bar Chart");
        pieButton.setUserData("Pie");
        barButton.setUserData("Bar");
        pieButton.setToggleGroup(this.chartToggle);
        barButton.setToggleGroup(this.chartToggle);
        setPos(pieButton,515,320);
        setPos(barButton,515,340);
        //sort label
        this.sortLabel = new Label("Sort by");
        setPos(this.sortLabel,10,355);
        //sort drop down list
        this.sortDropDownList = new ComboBox<>(FXCollections.observableArrayList("Name","GPA",
                                    "GPA w/ Q","As","Bs","Cs","Ds","Fs","Qs"));
        this.sortDropDownList.setValue("Name");
        setPos(this.sortDropDownList,60,350);
        //gpa label
        this.gpaLabel = new Label("");
        this.gpaLabel.setTextFill(Color.MAROON);
        setPos(this.gpaLabel,450,100);
        //attach everything to the root
        this.root.getChildren().addAll(this.inputLabel,this.inputTextField,this.inputButton,this.yearsLabel,
                                this.yearsDropDownList,this.instructorList,this.instructorLabel,this.exceptionLabel,
                                pieButton,barButton,this.sortLabel,this.sortDropDownList,this.gpaLabel);
    }
    private void initActions(){
        //button event -> get data
        this.inputButton.setOnAction(event -> {
            try {
                this.courseList = GradeGetter.getInstance().getCourse(this.inputTextField.getText(),
                                                                    this.yearsDropDownList.getValue(), true);
                if (this.courseList.isEmpty())
                    throw new Exception("The course doesn't exist.");
                GradeAnalyzer.sortByInstructorName(this.courseList);
                this.instructorMap = new HashMap<>();
                ObservableList<String> instructors = FXCollections.observableArrayList();
                for (PastCourse c : this.courseList){
                    this.instructorMap.put(c.getInstructor(),c);
                    instructors.add(c.getInstructor());
                }
                this.instructorList.setItems(instructors);
                this.sortDropDownList.setValue("Name");
                this.exceptionLabel.setText(""); // no exception happened
            }catch (Exception e){
                this.exceptionLabel.setText(e.getMessage());
            }
        });
        //combo box event -> get graph
        this.instructorList.setOnMouseClicked(event ->{
            String selectedInstructor = this.instructorList.getSelectionModel().getSelectedItem();
            if (selectedInstructor == null || this.currentInstructor != null && this.currentInstructor.equals(selectedInstructor))
                return;
            else
                this.currentInstructor = selectedInstructor;
            PastCourse selectedCourse = this.instructorMap.get(selectedInstructor);
            this.makePieChart(selectedCourse);
            this.gpaLabel.setText("                     GPA: " + String.format("%.2f",selectedCourse.getAverage()) +
                                "\nGPA with Q-Drop: " + String.format("%.2f",selectedCourse.getAverageWithQdrop()));
            this.chartToggle.selectToggle(this.pieButton);
        });


        //toggle button event -> draw chart
        this.chartToggle.selectedToggleProperty().addListener((ov,ot,nt)->{
            String selectedInstructor = this.instructorList.getSelectionModel().getSelectedItem();
            Toggle t = this.chartToggle.getSelectedToggle();
            if (selectedInstructor == null || t == null)
                return;
            String data = t.getUserData().toString();
            PastCourse selectedCourse = this.instructorMap.get(selectedInstructor);
            if (data.equals("Pie")){
                this.makePieChart(selectedCourse);
            }else if (data.equals("Bar")){
                this.makeBarChart(selectedCourse);
            }

        });
        //sort combo box event -> sort instructor list
        this.sortDropDownList.getSelectionModel().selectedItemProperty().addListener((ov,ot,nt)->{
            if (this.courseList == null)
                return;
            //"GPA w/ Q","As","Bs","Cs","Ds","Fs","Qs"
            if (nt.equals("Name")){
                GradeAnalyzer.sortByInstructorName(this.courseList);
            }else if (nt.equals("GPA")){
                GradeAnalyzer.sortByGPA(this.courseList);
            }
            else if (nt.equals("GPA w/ Q")){
                GradeAnalyzer.sortByGPAWithQdrop(this.courseList);
            }else if (nt.equals("As")){
                GradeAnalyzer.sortByA(this.courseList);
            }else if (nt.equals("Bs")){
                GradeAnalyzer.sortByB(this.courseList);
            }else if (nt.equals("Cs")){
                GradeAnalyzer.sortByC(this.courseList);
            }else if (nt.equals("Ds")){
                GradeAnalyzer.sortByD(this.courseList);
            }else if (nt.equals("Fs")){
                GradeAnalyzer.sortByF(this.courseList);
            }else if (nt.equals("Qs")){
                GradeAnalyzer.sortByQ(this.courseList);
            }else
                System.out.println("Unexpected Selection: " + nt);
            ObservableList<String> instructors = FXCollections.observableArrayList();
            for (PastCourse c: this.courseList){
                instructors.add(c.getInstructor());
            }
            this.instructorList.setItems(instructors);
            this.chartToggle.selectToggle(null);

        });
    }
    private void makePieChart(PastCourse c){
        this.root.getChildren().remove(this.chartGraph); // remove old graph
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("A(" + String.format( "%.2f",c.getAPercentage()*100) +"%)", c.getA()),
                        new PieChart.Data("B(" + String.format( "%.2f",c.getBPercentage()*100) +"%)", c.getB()),
                        new PieChart.Data("C(" + String.format( "%.2f",c.getCPercentage()*100) +"%)", c.getC()),
                        new PieChart.Data("D(" + String.format( "%.2f",c.getDPercentage()*100) +"%)", c.getD()),
                        new PieChart.Data("F(" + String.format( "%.2f",c.getFPercentage()*100) +"%)", c.getF()),
                        new PieChart.Data("Q(" + String.format( "%.2f",c.getQPercentage()*100) +"%)", c.getQ())
                );
        PieChart chart = new PieChart(pieChartData);
        chart.setTitle(c.getInstructor());
        chart.setLabelsVisible(true);
        chart.setLabelLineLength(10);
        chart.setLegendVisible(true);
        chart.setLegendSide(Side.BOTTOM);
        chart.setPrefSize(350,300);
        setPos(chart,170,70);
        this.chartGraph = chart;
        this.root.getChildren().add(this.chartGraph);       // add new graph
    }
    private void makeBarChart(PastCourse c){
        this.root.getChildren().remove(this.chartGraph); // remove old graph
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        BarChart<String,Number> bc = new BarChart<>(xAxis,yAxis);
        xAxis.setLabel("Grade");
        yAxis.setLabel("Head Count");
        // A bar
        XYChart.Series seriesA = new XYChart.Series();
        seriesA.setName("A(" + String.format( "%.2f",c.getAPercentage()*100) +"%)");
        seriesA.getData().add(new XYChart.Data("A", c.getA()));
        //B bar
        XYChart.Series seriesB = new XYChart.Series();
        seriesB.setName("B(" + String.format( "%.2f",c.getBPercentage()*100) +"%)");
        seriesB.getData().add(new XYChart.Data("B", c.getB()));
        //C bar
        XYChart.Series seriesC = new XYChart.Series();
        seriesC.setName("C(" + String.format( "%.2f",c.getCPercentage()*100) +"%)");
        seriesC.getData().add(new XYChart.Data("C", c.getC()));
        //D bar
        XYChart.Series seriesD = new XYChart.Series();
        seriesD.setName("D(" + String.format( "%.2f",c.getDPercentage()*100) +"%)");
        seriesD.getData().add(new XYChart.Data("D", c.getD()));
        //F bar
        XYChart.Series seriesF = new XYChart.Series();
        seriesF.setName("F(" + String.format( "%.2f",c.getFPercentage()*100) +"%)");
        seriesF.getData().add(new XYChart.Data("F", c.getF()));
        //Q bar
        XYChart.Series seriesQ = new XYChart.Series();
        seriesQ.setName("Q(" + String.format( "%.2f",c.getQPercentage()*100) +"%)");
        seriesQ.getData().add(new XYChart.Data("Q", c.getQ()));
        //style
        bc.setTitle(c.getInstructor());
        bc.setLegendVisible(true);
        bc.setLegendSide(Side.BOTTOM);
        bc.setPrefSize(350,300);
        bc.setBarGap(-25);
        bc.setCategoryGap(20);
        setPos(bc,170,70);
        //add all
        bc.getData().addAll(seriesA,seriesB,seriesC,seriesD,seriesF,seriesQ);
        this.chartGraph = bc;
        this.root.getChildren().add(this.chartGraph);       // add new graph
    }
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("CoursePicker.PastCourse Picker");
        this.root = new Pane();
        this.initUI(root);
        this.initActions();
        primaryStage.setScene(new Scene(root,600,400,Color.rgb(80,0,0,0.5)));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
