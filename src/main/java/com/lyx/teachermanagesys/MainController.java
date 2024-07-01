package com.lyx.teachermanagesys;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML
    protected void onNewButtonClick() throws IOException { //新建教师按钮
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("new-view.fxml"));
        Stage newTeacherStage = new Stage();
        newTeacherStage.setTitle("新建教师 - 教师管理系统");
        newTeacherStage.setScene(new Scene(fxmlLoader.load(),300,600));
        newTeacherStage.show();
    }
}