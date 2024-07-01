module com.lyx.teachermanagesys {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.lyx.teachermanagesys to javafx.fxml;
    exports com.lyx.teachermanagesys;
}