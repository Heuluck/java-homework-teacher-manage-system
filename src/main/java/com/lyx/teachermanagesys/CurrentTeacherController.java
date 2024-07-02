package com.lyx.teachermanagesys;

import Teacher.Teacher;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class CurrentTeacherController {
    @FXML
    private Label titleTeachName;
    @FXML
    private Label teachId;
    @FXML
    private Label teachName;
    @FXML
    private Label teachSex;
    @FXML
    private Label teachRank;
    @FXML
    private VBox teachLessons;
    @FXML
    private VBox teachClasses;
    @FXML
    private Label theoryLength;
    @FXML
    private Label labLength;
    @FXML
    private Label totalLength;
    
    public void initialize() {
        Teacher currentTeacher = Context.currentTeacher;
        titleTeachName.setText(currentTeacher.getName());
        teachId.setText(currentTeacher.getId());
        teachName.setText(currentTeacher.getName());
        teachSex.setText(currentTeacher.getSex());
        teachRank.setText(currentTeacher.getRank());
        theoryLength.setText(String.format("%d",currentTeacher.getTheoryClassLength()));
        labLength.setText(String.format("%d",currentTeacher.getLabClassLength()));
        totalLength.setText(String.format("%.1f",currentTeacher.getTotalLength()));

        /* 班级列表 */
        for(String singleClass : currentTeacher.getClasses()){
            HBox hbox = new HBox();
            hbox.setSpacing(10);
            Label label = new Label(singleClass+"班");
            Button button = new Button("删除");
            button.setOnAction(event -> deleteClass(singleClass+"删除"));

            hbox.getChildren().addAll(label, button);
            teachClasses.getChildren().add(hbox);
        }

        /* 课程列表 */
        for(String singleLesson : currentTeacher.getLessons()){
            HBox hbox = new HBox();
            hbox.setSpacing(10);
            Label label = new Label(singleLesson);
            Button button = new Button("删除");
            button.setOnAction(event -> deleteClass(singleLesson+"删除"));

            hbox.getChildren().addAll(label, button);
            teachLessons.getChildren().add(hbox);
        }
    }
    @FXML
    protected static void deleteClass(String singleClass){
        System.out.print(singleClass);
    }
}