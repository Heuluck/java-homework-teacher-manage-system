package com.heuluck.teachermanagesys;

import Teacher.Teacher;
import com.alibaba.fastjson2.JSON;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class SearchController {
    @FXML
    private TableView<Teacher> dataTable;
    @FXML
    private TextField teacherIDSearch;

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
        try (PrintWriter output = new PrintWriter(file)) {
            output.print(JSON.toJSONString(Context.allTeachers));
        }
    }

    @FXML
    protected void linkDB() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("SQL-view.fxml"));
        Stage listStage = new Stage();
        listStage.setTitle("连接数据库 - 教师管理系统");
        listStage.setScene(new Scene(fxmlLoader.load(), 300, 600));
        listStage.show();
        listStage.setOnHidden(e->{
            try {
                initialize();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    @FXML
    protected void searchID() throws IOException {
        if (!teacherIDSearch.getText().isEmpty()) {
            ArrayList<Teacher> teachers = new ArrayList<Teacher>();
            Context.allTeachers.forEach(teacher -> {
                if(teacher.getId().contains(teacherIDSearch.getText()))
                    teachers.add(teacher);
            });
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
            dataTable.refresh();
        }else initialize();
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
        dataTable.refresh();
    }
}
