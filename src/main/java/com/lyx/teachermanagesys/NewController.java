package com.lyx.teachermanagesys;

import Teacher.Teacher;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class NewController {
    @FXML
    private TextField teacherId;
    @FXML
    private TextField teacherName;
    @FXML
    private TextField teacherSex;
    @FXML
    private TextField teacherRank;
    @FXML
    private TextField teacherLessons;
    @FXML
    private TextField teacherClasses;
    @FXML
    private TextField teacherTheoryClassLength;
    @FXML
    private TextField teacherLabClassLength;

    public void initialize() {
        teacherTheoryClassLength.textProperty().addListener((obs, oldText, newText) -> { //禁止理论课时输入数字以外的字符
            if (!newText.matches("\\d*"))
            {
                teacherTheoryClassLength.setText(oldText);
            }
        });
        teacherLabClassLength.textProperty().addListener((obs, oldText, newText) -> { //禁止实验课时输入数字以外的字符
            if (!newText.matches("\\d*"))
            {
                teacherLabClassLength.setText(oldText);
            }
        });
    }
    @FXML
    protected void onNewTeacherSubmit() throws IOException { //提交新老师表单
        Context.currentTeacher = new Teacher(teacherName.getText());
    }
}
