module com.example.btl_tkxdpm {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;

    opens com.btl_tkxdpm to javafx.fxml;
    exports com.btl_tkxdpm;

    opens com.btl_tkxdpm.home to javafx.fxml;
    exports com.btl_tkxdpm.home;

    opens com.btl_tkxdpm.export to javafx.fxml;
    exports com.btl_tkxdpm.export;
}