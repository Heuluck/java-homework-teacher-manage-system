module com.lyx.teachermanagesys {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.heuluck.teachermanagesys to javafx.fxml;
    opens Teacher to javafx.base;
    exports com.heuluck.teachermanagesys;
}