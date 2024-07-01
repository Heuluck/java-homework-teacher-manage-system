package com.lyx.teachermanagesys;

import Teacher.Teacher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    Teacher currentTeach;
    @FXML
    private NewController newController;
    @FXML
    private CurrentTeacherController currentTeacherController;

    @FXML
    public void initialize() { //让这俩可以访问到MainController
        System.out.print(newController);
        newController.getMainController(this);
        currentTeacherController.getMainController(this);
    }

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
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("current-teacher-view.css"));
        Stage newTeacherStage = new Stage();
        newTeacherStage.setTitle("教师 - 教师管理系统");
        newTeacherStage.setScene(new Scene(fxmlLoader.load(),640,480));
        newTeacherStage.show();
    }
}