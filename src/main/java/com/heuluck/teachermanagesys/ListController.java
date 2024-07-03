package com.heuluck.teachermanagesys;

import Teacher.Teacher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class ListController {
    @FXML
    private TableView<Teacher> dataTable;

    public void initialize() {
        ArrayList<Teacher> teachers = Context.allTeachers;
        ObservableList<Teacher> teacher = FXCollections.observableArrayList(teachers);
        dataTable.setItems(teacher);
    }
}
