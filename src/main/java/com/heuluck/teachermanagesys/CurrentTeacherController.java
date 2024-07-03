package com.heuluck.teachermanagesys;

import Teacher.Teacher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
//        teacher.add(currentTeacher);
//        id.setCellValueFactory(new PropertyValueFactory<Teacher,String>("id"));
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
    protected static void deleteClass(String singleClass){
        System.out.print(singleClass);
    }
}