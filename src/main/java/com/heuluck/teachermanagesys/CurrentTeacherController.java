package com.heuluck.teachermanagesys;

import Teacher.Teacher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

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

        /* 班级列表 */
        for(String singleClass : currentTeacher.getClasses()){
            HBox hbox = new HBox();
            hbox.setSpacing(10);
            Label label = new Label(singleClass+"班");
            Button button = new Button("删除");
            button.setOnAction(event -> deleteClass(singleClass+"删除"));

            hbox.getChildren().addAll(label, button);
            teachClasses.getChildren().add(hbox);
        }

        /* 课程列表 */
        for(String singleLesson : currentTeacher.getLessons()){
            HBox hbox = new HBox();
            hbox.setSpacing(10);
            Label label = new Label(singleLesson);
            Button button = new Button("删除");
            button.setOnAction(event -> deleteClass(singleLesson+"删除"));

            hbox.getChildren().addAll(label, button);
            teachLessons.getChildren().add(hbox);
        }
    }

    @FXML
    protected void openList() throws IOException {
        if (Context.allTeachers != null) {
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
    protected static void deleteClass(String singleClass){
        System.out.print(singleClass);
    }
}