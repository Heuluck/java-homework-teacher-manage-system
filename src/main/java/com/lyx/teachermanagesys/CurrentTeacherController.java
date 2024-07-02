package com.lyx.teachermanagesys;

import Teacher.Teacher;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Label;

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
    private Label teachLessons;
    @FXML
    private Label teachClasses;
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
    }
}