module com.example.elite_driving_school {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.elite_driving_school to javafx.fxml;
    exports com.example.elite_driving_school;
}