module com.heuluck.teachermanagesys {
    requires javafx.controls;
    requires javafx.fxml;
    requires fastjson;
    requires com.alibaba.fastjson2;

    opens com.heuluck.teachermanagesys to javafx.fxml, fastjson, com.alibaba.fastjson2, com.alibaba.fastjson2.extension;
    opens Teacher to javafx.base, fastjson, com.alibaba.fastjson2, com.alibaba.fastjson2.extension;
    exports com.heuluck.teachermanagesys;
    exports Teacher;
}

