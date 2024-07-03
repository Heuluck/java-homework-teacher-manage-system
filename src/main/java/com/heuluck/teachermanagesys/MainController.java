package com.heuluck.teachermanagesys;

import Teacher.Teacher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class MainController {
    Teacher currentTeach;

    @FXML
    protected void onNewButtonClick() throws IOException { //新建教师按钮
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("new-view.fxml"));
        Stage newTeacherStage = new Stage();
        newTeacherStage.setTitle("新建教师 - 教师管理系统");
        newTeacherStage.setScene(new Scene(fxmlLoader.load(), 300, 600));
        newTeacherStage.show();
    }

    @FXML
    protected void onCurrentTeachButtonClick() throws IOException { //新建教师按钮
        if (Context.currentTeacher != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("current-teacher-view.fxml"));
            Stage currentTeacherStage = new Stage();
            currentTeacherStage.setTitle(Context.currentTeacher.getName() + "老师 - 教师管理系统");
            currentTeacherStage.setScene(new Scene(fxmlLoader.load(), 920, 640));
            currentTeacherStage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("未选择教师");
            alert.setHeaderText("当前未选择任何教师");
            alert.setContentText("请新建教师或搜索教师");
            alert.showAndWait();
        }
    }

    @FXML
    protected void onOpenListButtonClick() throws IOException {
        if (Context.allTeachers != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("list-view.fxml"));
            Stage listStage = new Stage();
            listStage.setTitle("所有教师 - 教师管理系统");
            listStage.setScene(new Scene(fxmlLoader.load(), 920, 640));
            listStage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("没有教师");
            alert.setHeaderText("当前没有任何教师");
            alert.setContentText("请新建教师或搜索教师");
            alert.showAndWait();
        }
    }

    @FXML
    protected void onLinkDBButtonClick() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.showAndWait();
    }

    /* 加载默认示例教师 */
    @FXML
    protected void onLoadDefaultTeachButtonClick() {
        Context.currentTeacher = new Teacher("9109111111", "张三", "男", "教授",
                "JavaScript程序设计;Web开发;Web开发", "231;232", 2, 2);
        Context.allTeachers = new ArrayList<Teacher>();
        Context.allTeachers.add(new Teacher("9109111111", "张三", "男", "教授",
                "JavaScript程序设计;Web开发;Web开发", "231;232", 2, 2));
        Context.allTeachers.add(new Teacher("9109224121", "李四", "女", "副教授",
                "高等数学;离散数学", "233;234", 3, 1));
        Context.allTeachers.add(new Teacher("9109220164", "王五", "男", "讲师",
                "大学英语;国家社会", "231;237", 5, 0));
    }
}