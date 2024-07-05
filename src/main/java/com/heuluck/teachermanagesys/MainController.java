package com.heuluck.teachermanagesys;

import Database.MysqlConnection;
import Teacher.Teacher;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONException;
import com.alibaba.fastjson2.TypeReference;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utils.CustomArrayList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    protected void onLinkDBButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("SQL-view.fxml"));
        Stage listStage = new Stage();
        listStage.setTitle("连接数据库 - 教师管理系统");
        listStage.setScene(new Scene(fxmlLoader.load(), 300, 600));
        listStage.show();
    }

    @FXML
    protected void onImportButtonClick() throws IOException {
        Alert comfirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        comfirmAlert.setTitle("覆盖");
        comfirmAlert.setHeaderText("将覆盖当前数据");
        comfirmAlert.setContentText("是否继续导入？");
        Optional<ButtonType> result = comfirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {//确定
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON文件 (*.json)", "*.json"));
            Stage stage = new Stage();
            File file = fileChooser.showOpenDialog(stage);
            if (file == null)
                return;
            if (file.exists()) {
                List<String> lines = Files.readAllLines(Paths.get(file.getAbsolutePath()), StandardCharsets.UTF_8);
                String a = String.join("", lines);
                try {
                    ArrayList<Teacher> data = JSON.parseObject(a, new TypeReference<ArrayList<Teacher>>() {
                    });
                    if (data.isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("文件为空");
                        alert.setHeaderText("JSON文件为空");
                        alert.setContentText("未导入任何有效的教师");
                        alert.showAndWait();
                    } else {
                        data.forEach(teacher -> {
                            if (!teacher.getLessons().isEmpty()) {
                                final String arrayString = teacher.getLessons().getFirst();//["231","232"]
                                final String subString = arrayString.substring(2,arrayString.length()-2);//231","232
                                teacher.setLessons(CustomArrayList.toList(subString.split("\",\"")));
                            }
                            if (!teacher.getClasses().isEmpty()) {
                                final String arrayString = teacher.getClasses().getFirst();
                                final String subString = arrayString.substring(2,arrayString.length()-2);
                                teacher.setClasses(CustomArrayList.toList(subString.split("\",\"")));
                            }
                        });
                        Context.allTeachers = data;
                        Context.currentTeacher = data.getFirst();
                    }
                } catch (JSONException e) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("格式错误");
                    alert.setHeaderText("JSON格式有误");
                    alert.setContentText("JSON格式错误或非本程序导出的文件");
                    alert.showAndWait();
                }
            }
        } else return;
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