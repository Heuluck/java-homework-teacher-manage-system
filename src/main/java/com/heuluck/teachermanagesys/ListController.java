package com.heuluck.teachermanagesys;

import Teacher.Teacher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ListController {
    @FXML
    private TableView<Teacher> dataTable;

    public void initialize() throws IOException {
        ArrayList<Teacher> teachers = Context.allTeachers;
        ObservableList<Teacher> teacher = FXCollections.observableArrayList(teachers);
        dataTable.setRowFactory(singleTableView -> {
            TableRow<Teacher> teacherRow = new TableRow<Teacher>();
            teacherRow.setOnMouseClicked(clickE -> {
                if(clickE.getClickCount() == 2 && !teacherRow.isEmpty()) {
                    Context.currentTeacher = teacherRow.getItem();
                    //打开教师详情
                    FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("current-teacher-view.fxml"));
                    Stage currentTeacherStage = new Stage();
                    currentTeacherStage.setTitle(Context.currentTeacher.getName() + "老师 - 教师管理系统");
                    try {
                        currentTeacherStage.setScene(new Scene(fxmlLoader.load(), 920, 640));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    currentTeacherStage.show();
                    //关闭当前页面
                    Stage stage = (Stage) dataTable.getScene().getWindow();
                    stage.close();
                }
            });
            return teacherRow;
        });
        dataTable.setItems(teacher);
    }
}
