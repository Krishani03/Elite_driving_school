package com.example.elite_driving_school.controller;

import com.example.elite_driving_school.bo.custom.UserBO;
import com.example.elite_driving_school.bo.custom.impl.UserBOImpl;
import com.example.elite_driving_school.dto.UserDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;


public class Login_FormController {

    private final UserBO userBO = new UserBOImpl();

    @FXML
    private AnchorPane LoginPane;

    @FXML
    private Button btnLogin;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    void btnLogin_Onaction(ActionEvent event) {
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Please enter username and password.");
            return;
        }

        try {
            UserDTO user = userBO.getAllUsers().stream()
                    .filter(u -> u.getUsername().equalsIgnoreCase(username))
                    .findFirst()
                    .orElse(null);

            if (user == null) {
                showAlert(Alert.AlertType.ERROR, "User not found.");
                return;
            }

            // Verify password with BCrypt
            if (!BCrypt.checkpw(password, user.getPassword())) {
                showAlert(Alert.AlertType.ERROR, "Invalid password.");
                return;
            }

            // Check user role
            switch (user.getRole().toUpperCase()) {
                case "ADMIN":
                    showAlert(Alert.AlertType.INFORMATION, "Welcome Admin!");
                    openDashboard("/com/example/elite_driving_school/view/adminForm.fxml", "Admin Dashboard");
                    break;
                case "RECEPTIONIST":
                    showAlert(Alert.AlertType.INFORMATION, "Welcome Receptionist!");
                    openDashboard("/com/example/elite_driving_school/view/recepForm.fxml", "Receptionist Dashboard");
                    break;
                default:
                    showAlert(Alert.AlertType.ERROR, "Unknown role: " + user.getRole());
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Login error: " + e.getMessage());
        }
    }


    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Login");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void openDashboard(String fxmlPath, String title) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();


            Stage currentStage = (Stage) btnLogin.getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Failed to open dashboard: " + e.getMessage());
        }
    }
    }

