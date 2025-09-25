package com.example.elite_driving_school.controller;

import com.example.elite_driving_school.bo.BOFactory;
import com.example.elite_driving_school.bo.custom.UserBO;
import com.example.elite_driving_school.dto.UserDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class UserFormController {

    @FXML
    private Button btnAdd, btnClear, btnDelete, btnUpdate;

    @FXML
    private CheckBox chkActive;

    @FXML
    private ComboBox<String> cmbRole;

    @FXML
    private TableColumn<UserDTO, Boolean> colActive;

    @FXML
    private TableColumn<UserDTO, String> colId;

    @FXML
    private TableColumn<UserDTO, String> colRole;

    @FXML
    private TableColumn<UserDTO, String> colUsername;

    @FXML
    private TableView<UserDTO> tblUsers;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtUserId;

    private final UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    public void initialize() {
        // Set table columns
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colActive.setCellValueFactory(new PropertyValueFactory<>("active"));

        // Set role options
        cmbRole.setItems(FXCollections.observableArrayList("ADMIN", "RECEPTIONIST"));

        loadNextUserId();
        loadUsers();
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        if (!validateInputs()) return;

        try {
            UserDTO dto = new UserDTO();
            dto.setId(txtUserId.getText());
            dto.setUsername(txtUsername.getText());
            dto.setPassword(txtPassword.getText());
            dto.setRole(cmbRole.getValue());
            dto.setActive(chkActive.isSelected());

            UserDTO savedUser = userBO.saveUser(dto);

            if (savedUser != null) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "User added successfully");
                clearForm();
                loadUsers();
                loadNextUserId();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed", "Failed to add user");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if (!validateInputs()) return;

        try {
            UserDTO dto = new UserDTO();
            dto.setId(txtUserId.getText());
            dto.setUsername(txtUsername.getText());
            dto.setPassword(txtPassword.getText());
            dto.setRole(cmbRole.getValue());
            dto.setActive(chkActive.isSelected());

            if (userBO.updateUser(dto)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "User updated successfully");
                clearForm();
                loadUsers();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed", "Failed to update user");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            String id = txtUserId.getText();
            if (userBO.deleteUser(id)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "User deleted successfully");
                clearForm();
                loadUsers();
                loadNextUserId();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed", "Failed to delete user");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearForm();
    }

    @FXML
    void onClickTable(MouseEvent event) {
        UserDTO selected = tblUsers.getSelectionModel().getSelectedItem();
        if (selected != null) {
            txtUserId.setText(selected.getId());
            txtUsername.setText(selected.getUsername());
            txtPassword.setText(selected.getPassword());
            cmbRole.setValue(selected.getRole());
            chkActive.setSelected(selected.isActive());
        }
    }

    private void clearForm() {
        txtUsername.clear();
        txtPassword.clear();
        cmbRole.getSelectionModel().clearSelection();
        chkActive.setSelected(true);
    }

    private void loadUsers() {
        try {
            ObservableList<UserDTO> users = FXCollections.observableArrayList(userBO.getAllUsers());
            tblUsers.setItems(users);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load users: " + e.getMessage());
        }
    }

    private void loadNextUserId() {
        try {
            String nextId = userBO.getNextUserId();
            txtUserId.setText(nextId);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to generate next User ID: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean validateInputs() {
        if (txtUsername.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Enter username");
            return false;
        }
        if (txtPassword.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Enter password");
            return false;
        }
        if (cmbRole.getValue() == null || cmbRole.getValue().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Select a role");
            return false;
        }
        return true;
    }
}
