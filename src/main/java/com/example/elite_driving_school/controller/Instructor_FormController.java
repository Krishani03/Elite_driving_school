package com.example.elite_driving_school.controller;

import com.example.elite_driving_school.bo.BOFactory;
import com.example.elite_driving_school.bo.custom.InstructorBO;
import com.example.elite_driving_school.dto.InstructorDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class Instructor_FormController {
    @FXML
    private TableColumn<InstructorDTO, String> colEmail;

    @FXML
    private TableColumn<InstructorDTO, Long> colId;

    @FXML
    private TableColumn<InstructorDTO, String> colLName;

    @FXML
    private TableColumn<InstructorDTO, String> colPhone;

    @FXML
    private TableView<InstructorDTO> tblInstructor;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtInstructor;

    @FXML
    private TextField txtInstructorID; // ✅ replaces lblId

    @FXML
    private TextField txtPhone;

    private final InstructorBO instructorBO =
            (InstructorBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.INSTRUCTOR);

    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colLName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        loadAllInstructors();
    }

    private void loadAllInstructors() {
        try {
            tblInstructor.getItems().clear();
            List<InstructorDTO> instructors = instructorBO.getAllInstructors();
            tblInstructor.getItems().addAll(instructors);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load instructors: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnAddInstrOnAction(ActionEvent event) {
        try {
            InstructorDTO dto = new InstructorDTO();
            dto.setName(txtInstructor.getText());
            dto.setPhone(txtPhone.getText());
            dto.setEmail(txtEmail.getText());

            if (instructorBO.saveInstructor(dto)) {
                txtInstructorID.setText(String.valueOf(dto.getId()));
                new Alert(Alert.AlertType.INFORMATION, "Instructor added successfully!").show();

                loadAllInstructors();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to add instructor!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }


    @FXML
    void btnClearInstrOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteInstrOnAction(ActionEvent event) {
        try {
            Long id = Long.parseLong(txtInstructorID.getText());
            if (instructorBO.deleteInstructor(id)) {
                new Alert(Alert.AlertType.INFORMATION, "Instructor deleted successfully!").show();
                loadAllInstructors();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete instructor!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnSearchInstrOnAction(ActionEvent event) {
        try {
            Long id = Long.parseLong(txtInstructorID.getText());
            InstructorDTO dto = instructorBO.searchInstructor(id);
            if (dto != null) {
                txtInstructorID.setText(String.valueOf(dto.getId()));
                txtInstructor.setText(dto.getName());
                txtPhone.setText(dto.getPhone());
                txtEmail.setText(dto.getEmail());
            } else {
                new Alert(Alert.AlertType.WARNING, "Instructor not found!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateInstrOnAction(ActionEvent event) {
        try {
            InstructorDTO dto = new InstructorDTO();
            dto.setId(Long.parseLong(txtInstructorID.getText()));
            dto.setName(txtInstructor.getText());
            dto.setPhone(txtPhone.getText());
            dto.setEmail(txtEmail.getText());

            if (instructorBO.updateInstructor(dto)) {
                new Alert(Alert.AlertType.INFORMATION, "Instructor updated successfully!").show();
                loadAllInstructors();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update instructor!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        InstructorDTO selected = tblInstructor.getSelectionModel().getSelectedItem();
        if (selected != null) {
            txtInstructorID.setText(String.valueOf(selected.getId()));
            txtInstructor.setText(selected.getName());
            txtPhone.setText(selected.getPhone());
            txtEmail.setText(selected.getEmail());
        }
    }

    private void clearFields() {
        txtInstructorID.clear();
        txtInstructor.clear();
        txtPhone.clear();
        txtEmail.clear();
    }
}
