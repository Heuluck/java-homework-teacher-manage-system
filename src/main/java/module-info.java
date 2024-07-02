module com.lyx.teachermanagesys {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.heuluck.teachermanagesys to javafx.fxml;
    exports com.heuluck.teachermanagesys;
}