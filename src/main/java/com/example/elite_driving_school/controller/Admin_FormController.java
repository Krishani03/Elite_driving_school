package com.example.elite_driving_school.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Admin_FormController {
    @FXML
    private AnchorPane ancMainContainer;

    @FXML
    private Button btnCourse;

    @FXML
    private Button btnInstruc;

    @FXML
    private Button btnLesson;

    @FXML
    private Button btnPayment;

    @FXML
    private Button btnStudent;

    @FXML
    private Button btnUser;

    @FXML
    void btnCourseOnAction(ActionEvent event) {

    }

    @FXML
    void btnInstructorOnAction(ActionEvent event) {
        navigateTo("/com/example/elite_driving_school/view/instructorForm.fxml");
    }

    @FXML
    void btnLessonOnAction(ActionEvent event) {

    }

    @FXML
    void btnPaymentOnAction(ActionEvent event) {

    }

    @FXML
    void btnStudentOnAction(ActionEvent event) {
        navigateTo("/com/example/elite_driving_school/view/studentForm.fxml");
    }

    @FXML
    void btnUserOnAction(ActionEvent event) {

    }
    private void navigateTo(String path) {
        try {
            ancMainContainer.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancMainContainer.widthProperty());
            anchorPane.prefHeightProperty().bind(ancMainContainer.heightProperty());

            ancMainContainer.getChildren().add(anchorPane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Page not found..!").show();
            e.printStackTrace();
        }
    }
}
