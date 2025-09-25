package com.example.elite_driving_school.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Recep_FormController {
    @FXML
    private AnchorPane ancMainContainer;

    @FXML
    private Button btnLesson;

    @FXML
    private Button btnPayment;

    @FXML
    private Button btnStudent;

    @FXML
    void btnLessonOnAction(ActionEvent event) {
        navigateTo("/com/example/elite_driving_school/view/lessonForm.fxml");

    }

    @FXML
    void btnPaymentOnAction(ActionEvent event) {
        navigateTo("/com/example/elite_driving_school/view/paymentForm.fxml");

    }

    @FXML
    void btnStudentOnAction(ActionEvent event) {
        navigateTo("/com/example/elite_driving_school/view/studentForm.fxml");
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
