package com.heuluck.teachermanagesys;

import Database.MysqlConnection;
import Teacher.Teacher;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.validate;

import java.io.IOException;

public class AlterController {
    @FXML
    private TextField teacherId;
    @FXML
    private TextField teacherName;
    @FXML
    private ChoiceBox<String> teacherSex;
    @FXML
    private TextField teacherRank;
    @FXML
    private TextField teacherLessons;
    @FXML
    private TextField teacherClasses;
    @FXML
    private TextField teacherTheoryClassLength;
    @FXML
    private TextField teacherLabClassLength;

    public void initialize() {
        teacherId.setText(Context.currentTeacher.getId());
        teacherId.setText(Context.currentTeacher.getId());
        teacherName.setText(Context.currentTeacher.getName());
        teacherSex.setValue(Context.currentTeacher.getSex());
        teacherRank.setText(Context.currentTeacher.getRank());
        teacherLessons.setText(String.join(";", Context.currentTeacher.getLessons()));
        teacherClasses.setText(String.join(";", Context.currentTeacher.getClasses()));
        teacherTheoryClassLength.setText(String.valueOf(Context.currentTeacher.getTheoryClassLength()));
        teacherLabClassLength.setText(String.valueOf(Context.currentTeacher.getLabClassLength()));


        teacherId.textProperty().addListener((obs, oldText, newText) -> { //禁止理论课时输入数字以外的字符
            if (!newText.matches("\\d*")) {
                teacherId.setText(oldText);
            }
        });
        teacherTheoryClassLength.textProperty().addListener((obs, oldText, newText) -> { //禁止理论课时输入数字以外的字符，禁止输入太长
            if (!newText.matches("\\d*") || newText.length() > 6) {
                teacherTheoryClassLength.setText(oldText);
            }
        });
        teacherLabClassLength.textProperty().addListener((obs, oldText, newText) -> { //禁止id输入数字以外的字符，禁止输入太长
            if (!newText.matches("\\d*") || newText.length() > 6) {
                teacherLabClassLength.setText(oldText);
            }
        });
    }

    @FXML
    protected void onAlterTeacherSubmit() throws IOException { //提交新老师表单
        if (teacherSex.getValue() != null &&
                validate.isTextAllFilled(teacherId, teacherName, teacherRank, teacherLessons,
                        teacherClasses, teacherTheoryClassLength, teacherLabClassLength)) {

            Context.currentTeacher = new Teacher(teacherId.getText(), teacherName.getText(), teacherSex.getValue(), teacherRank.getText(),
                    teacherLessons.getText(), teacherClasses.getText(),
                    Integer.parseInt(teacherTheoryClassLength.getText()), Integer.parseInt(teacherLabClassLength.getText()));
            for (int i = 0; i < Context.allTeachers.size(); i++) {
                Teacher teacher = Context.allTeachers.get(i);
                if (teacher.getId().equals(Context.currentTeacher.getId())) {
                    Context.allTeachers.set(i, Context.currentTeacher);
                    break;
                }
            }
            if (Context.isSQLConnect) {
                MysqlConnection connection = new MysqlConnection();
                connection.insert(Context.currentTeacher);
            }
            Stage stage = (Stage) teacherName.getScene().getWindow();
            stage.close();

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("请填写所有数据");
            alert.setHeaderText("请填写所有数据");
            alert.setContentText("存在未填写的数据");
            alert.showAndWait();
        }
    }

    private boolean isConflict(String id) {
        return Context.allTeachers.stream().anyMatch(teacher -> id.equals(teacher.getId()));
    }
}
