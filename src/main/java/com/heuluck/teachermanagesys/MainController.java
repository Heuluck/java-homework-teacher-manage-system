package com.heuluck.teachermanagesys;

import Teacher.Teacher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    Teacher currentTeach;
    @FXML
    protected void onNewButtonClick() throws IOException { //新建教师按钮
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("new-view.fxml"));
        Stage newTeacherStage = new Stage();
        newTeacherStage.setTitle("新建教师 - 教师管理系统");
        newTeacherStage.setScene(new Scene(fxmlLoader.load(),300,600));
        newTeacherStage.show();
    }
    @FXML
    protected void onCurrentTeachButtonClick() throws IOException { //新建教师按钮
        if (Context.currentTeacher != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("current-teacher-view.fxml"));
            Stage newTeacherStage = new Stage();
            newTeacherStage.setTitle(Context.currentTeacher.getName() + "老师 - 教师管理系统");
            newTeacherStage.setScene(new Scene(fxmlLoader.load(), 640, 800));
            newTeacherStage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("未选择教师");
            alert.setHeaderText("当前未选择任何教师");
            alert.setContentText("请新建教师或搜索教师");
            alert.showAndWait();
        }
    }
    @FXML
    protected void onLoadDefaultTeachButtonClick(){
        Context.currentTeacher = new Teacher("9109111111","张三","男","正高级教师",
                "JavaScript程序设计;Web开发;Web开发","231;232",2,2);
    }
}