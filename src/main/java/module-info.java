module com.example.elite_driving_school {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires jbcrypt;
    requires static lombok;

    opens com.example.elite_driving_school to javafx.fxml;
    exports com.example.elite_driving_school;
}