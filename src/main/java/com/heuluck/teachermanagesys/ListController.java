package com.heuluck.teachermanagesys;

import Teacher.Teacher;
import com.alibaba.fastjson2.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ListController {
    @FXML
    private TableView<Teacher> dataTable;

    @FXML
    protected void exportAll() throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON文件 (*.json)", "*.json"));
        Stage stage = new Stage();
        File file = fileChooser.showSaveDialog(stage);
        if (file == null)
            return;
        if (file.exists()) {
            file.delete();
        }
        String exportFilePath = file.getAbsolutePath();
        try (java.io.PrintWriter output = new java.io.PrintWriter(file)) {
            output.print(JSON.toJSONString(Context.allTeachers));
        }
    }

    public void initialize() throws IOException {
        ArrayList<Teacher> teachers = Context.allTeachers;
        ObservableList<Teacher> teacher = FXCollections.observableArrayList(teachers);
        dataTable.setRowFactory(singleTableView -> {
            TableRow<Teacher> teacherRow = new TableRow<Teacher>();
            teacherRow.setOnMouseClicked(clickE -> {
                if (clickE.getClickCount() == 2 && !teacherRow.isEmpty()) {
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
