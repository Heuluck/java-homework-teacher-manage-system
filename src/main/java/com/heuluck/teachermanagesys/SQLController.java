package com.heuluck.teachermanagesys;

import Database.MysqlConnection;
import Teacher.Teacher;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import utils.validate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class SQLController {
    @FXML
    private TextField DBIp;
    @FXML
    private TextField DBPort;
    @FXML
    private TextField DBName;
    @FXML
    private TextField DBUser;
    @FXML
    private TextField DBPwd;

    public void initialize() {
        DBPort.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.matches("\\d*")) {
                DBPort.setText(oldText);
            }
        });
    }

    @FXML
    protected void onSubmit() throws IOException { //提交新老师表单
        if (validate.isTextAllFilled(DBIp, DBName, DBPort, DBName, DBUser, DBPwd)) {
            MysqlConnection.setDBPassword(DBPwd.getText());
            MysqlConnection.setDBURL("jdbc:mysql://" + DBIp.getText() + ":" + DBPort.getText() + "/" + DBName.getText());
            MysqlConnection.setDBUser(DBUser.getText());
            MysqlConnection connection = new MysqlConnection();
            if (connection.isExist()) {
                connection.getAll();
                onLoadSQLSuccess();
                Context.isSQLConnect = true;
            } else {
                if (connection.createTable()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("初始化成功");
                    alert.setHeaderText("表teacher不存在，已自动创建");
                    alert.setContentText("数据库初始化成功");
                    alert.showAndWait();
                    Context.isSQLConnect = true;
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("初始化失败");
                    alert.setHeaderText("表teacher不存在，且自动创建失败");
                    alert.setContentText("数据库初始化失败");
                    alert.showAndWait();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("请填写所有内容");
            alert.setHeaderText("请填写所有内容");
            alert.setContentText("存在未填写的内容");
            alert.showAndWait();
        }
    }

    private void onLoadSQLSuccess() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("数据库加载成功");
        alert.setHeaderText("数据库加载成功");
        alert.setContentText("合并还是覆盖当前本地数据？（请谨慎选择）");

        ButtonType buttonMerge = new ButtonType("合并");
        ButtonType buttonReplace = new ButtonType("覆盖所有");
        ButtonType buttonTypeCancel = new ButtonType("取消连接", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonMerge, buttonReplace, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonMerge) {
            handleMerge();
        } else if (result.isPresent() && result.get() == buttonReplace) {
            Context.allTeachers = Context.SQLTeachers;
        } else {
            Context.SQLTeachers = new ArrayList<Teacher>();
            Context.isSQLConnect = false;
        }
    }

    private void handleMerge() {
        ArrayList<String> conflicts = new ArrayList<>();//id冲突的数据

        Context.allTeachers.forEach(localData -> {
            String localId = localData.getId();
            Context.SQLTeachers.forEach(sqlData -> {
                String sqlId = sqlData.getId();
                if (localId.equals(sqlId))
                    conflicts.add(localId);
            });
        });

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        if (!conflicts.isEmpty()) {
            alert.setTitle("合并冲突");
            alert.setHeaderText("以下教师号的数据冲突");
            alert.setContentText(String.join(",", conflicts));

            ButtonType buttonSQL = new ButtonType("以数据库为准");
            ButtonType buttonLocal = new ButtonType("以本地数据为准");
            ButtonType buttonTypeCancel = new ButtonType("取消连接", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonSQL, buttonLocal, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == buttonSQL) {
                Context.allTeachers.removeIf(localData -> {
                    String localId = localData.getId();
                    return conflicts.stream().anyMatch(conflict -> localId.equals(conflict));
                });
                Context.allTeachers.addAll(Context.SQLTeachers);
            } else if (result.isPresent() && result.get() == buttonLocal) {
                Context.SQLTeachers.removeIf(SQLData -> {
                    String SQLId = SQLData.getId();
                    return conflicts.stream().anyMatch(conflict -> SQLId.equals(conflict));
                });
                Context.allTeachers.addAll(Context.SQLTeachers);
            } else {
                Context.SQLTeachers = new ArrayList<Teacher>();
                Context.isSQLConnect = false;
            }
        } else {
            Context.allTeachers.addAll(Context.SQLTeachers);
        }
    }
}
