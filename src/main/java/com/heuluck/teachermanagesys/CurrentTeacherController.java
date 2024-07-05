package com.heuluck.teachermanagesys;

import Database.MysqlConnection;
import Teacher.Teacher;
import com.alibaba.fastjson2.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.Optional;

import static com.heuluck.teachermanagesys.Context.allTeachers;
import static com.heuluck.teachermanagesys.Context.currentTeacher;

public class CurrentTeacherController {
    @FXML
    private Label titleTeachName;
    @FXML
    private VBox teachLessons;
    @FXML
    private VBox teachClasses;

    @FXML
    private TableView<Teacher> dataTable;

    public void initialize() {
        Teacher currentTeacher = Context.currentTeacher;
        titleTeachName.setText(currentTeacher.getName());
        ObservableList<Teacher> teacher = FXCollections.observableArrayList(currentTeacher);
        dataTable.setItems(teacher);
        dataTable.refresh();

        /* 班级列表 */
        teachClasses.getChildren().clear();
        for (String singleClass : currentTeacher.getClasses()) {
            HBox hbox = new HBox();
            hbox.setSpacing(10);
            Label label = new Label(singleClass + "班");
            Button button = new Button("删除");
            button.setOnAction(event -> {
                currentTeacher.deleteClass(singleClass);
                allTeachers.forEach((teacher1)->{
                    if(teacher1.getId().equals(currentTeacher.getId())){
                        teacher1.deleteClass(singleClass);
                    }
                });
                if(Context.isSQLConnect){
                    MysqlConnection connection = new MysqlConnection();
                    connection.insert(Context.currentTeacher);
                }
                initialize();
            });

            hbox.getChildren().addAll(label, button);
            teachClasses.getChildren().add(hbox);
        }
        /* 课程列表 */
        teachLessons.getChildren().clear();
        for (String singleLesson : currentTeacher.getLessons()) {
            HBox hbox = new HBox();
            hbox.setSpacing(10);
            Label label = new Label(singleLesson);
            Button button = new Button("删除");
            button.setOnAction(event -> {
                currentTeacher.deleteLesson(singleLesson);
                allTeachers.forEach((teacher1)->{
                    if(teacher1.getId().equals(currentTeacher.getId())){
                        teacher1.deleteLesson(singleLesson);
                    }
                });
                if(Context.isSQLConnect){
                    MysqlConnection connection = new MysqlConnection();
                    connection.insert(Context.currentTeacher);
                }
                initialize();
            });

            hbox.getChildren().addAll(label, button);
            teachLessons.getChildren().add(hbox);
        }
    }

    @FXML
    protected void openList() throws IOException {
        if (allTeachers != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("list-view.fxml"));
            Stage listStage = new Stage();
            listStage.setTitle("所有教师 - 教师管理系统");
            listStage.setScene(new Scene(fxmlLoader.load(), 920, 640));
            listStage.show();
            Stage stage = (Stage) dataTable.getScene().getWindow();
            stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("没有教师");
            alert.setHeaderText("当前没有任何教师");
            alert.setContentText("请新建教师或搜索教师");
            alert.showAndWait();
        }
    }

    @FXML
    protected void addClasses(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("新增班级");
        dialog.setHeaderText("新建教学班级");
        dialog.setContentText("新增的教学班级（多个班级以英文分号;分割）");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(classes -> {
            currentTeacher.addClasses(classes);
            allTeachers.forEach((teacher1)->{
                if(teacher1.getId().equals(currentTeacher.getId())){
                    teacher1.addClasses(classes);
                }
            });
            if(Context.isSQLConnect){
                MysqlConnection connection = new MysqlConnection();
                connection.insert(Context.currentTeacher);
            }
            initialize();
        });
    }

    @FXML
    protected void addLessons(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("新增课程");
        dialog.setHeaderText("新建教学课程");
        dialog.setContentText("新增的教学课程（多个课程以英文分号;分割）");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(lessons -> {
            currentTeacher.addLessons(lessons);
            allTeachers.forEach((teacher1)->{
                if(teacher1.getId().equals(currentTeacher.getId())){
                    teacher1.addLessons(lessons);
                }
            });
            if(Context.isSQLConnect){
                MysqlConnection connection = new MysqlConnection();
                connection.insert(Context.currentTeacher);
            }
            initialize();
        });
    }
}