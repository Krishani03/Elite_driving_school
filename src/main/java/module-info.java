module com.example.elite_driving_school {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires jbcrypt;
    requires org.slf4j;
    requires java.naming;
    requires java.sql;

    requires static lombok;

    // JavaFX
    opens com.example.elite_driving_school to javafx.fxml, org.hibernate.orm.core;
    opens com.example.elite_driving_school.controller to javafx.fxml;
    opens com.example.elite_driving_school.entity to org.hibernate.orm.core;

    // Export base package
    exports com.example.elite_driving_school;
}
