package com.example.elite_driving_school.controller;

import com.example.elite_driving_school.bo.BOFactory;
import com.example.elite_driving_school.bo.custom.InstructorBO;
import com.example.elite_driving_school.dto.InstructorDTO;
import javafx.collections.FXCollections;
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
    private TableColumn<InstructorDTO, String> colId;

    @FXML
    private TableColumn<InstructorDTO, String> colName;

    @FXML
    private TableColumn<InstructorDTO, String> colPhone;

    @FXML
    private TableView<InstructorDTO> tblInstructor;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtInstructorName;

    @FXML
    private TextField txtInstructorID;

    @FXML
    private TextField txtPhone;

    private final InstructorBO instructorBO =
            (InstructorBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.INSTRUCTOR);

    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        loadAllInstructors();
        generateNextId();
    }

    /** Load all instructors into the table */
    private void loadAllInstructors() {
        try {
            List<InstructorDTO> instructors = instructorBO.getAllInstructors();
            tblInstructor.setItems(FXCollections.observableArrayList(instructors));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load instructors: " + e.getMessage()).show();
        }
    }

    /** Generate next instructor ID and set in the ID field */
    private void generateNextId() {
        try {
            String nextId = instructorBO.getNextInstructorId();
            txtInstructorID.setText(nextId);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate Instructor ID: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnAddInstrOnAction(ActionEvent event) {
        try {
            InstructorDTO dto = new InstructorDTO();
            dto.setId(txtInstructorID.getText()); // use generated ID
            dto.setName(txtInstructorName.getText());
            dto.setPhone(txtPhone.getText());
            dto.setEmail(txtEmail.getText());

            InstructorDTO saved = instructorBO.saveInstructor(dto);
            if (saved != null) {
                new Alert(Alert.AlertType.INFORMATION, "Instructor added successfully!").show();
                loadAllInstructors();
                clearFields();
                generateNextId(); // generate next ID automatically
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to add instructor!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateInstrOnAction(ActionEvent event) {
        try {
            InstructorDTO dto = new InstructorDTO();
            dto.setId(txtInstructorID.getText());
            dto.setName(txtInstructorName.getText());
            dto.setPhone(txtPhone.getText());
            dto.setEmail(txtEmail.getText());

            if (instructorBO.updateInstructor(dto)) {
                new Alert(Alert.AlertType.INFORMATION, "Instructor updated successfully!").show();
                loadAllInstructors();
                clearFields();
                generateNextId();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update instructor!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnDeleteInstrOnAction(ActionEvent event) {
        try {
            String id = txtInstructorID.getText();
            if (instructorBO.deleteInstructor(id)) {
                new Alert(Alert.AlertType.INFORMATION, "Instructor deleted successfully!").show();
                loadAllInstructors();
                clearFields();
                generateNextId();
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
            String id = txtInstructorID.getText();
            InstructorDTO dto = instructorBO.searchInstructor(id);
            if (dto != null) {
                txtInstructorID.setText(dto.getId());
                txtInstructorName.setText(dto.getName());
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
    void onClickTable(MouseEvent event) {
        InstructorDTO selected = tblInstructor.getSelectionModel().getSelectedItem();
        if (selected != null) {
            txtInstructorID.setText(selected.getId());
            txtInstructorName.setText(selected.getName());
            txtPhone.setText(selected.getPhone());
            txtEmail.setText(selected.getEmail());
        }
    }

    @FXML
    void btnClearInstrOnAction(ActionEvent event) {
        clearFields();
        generateNextId();
    }

    private void clearFields() {
        txtInstructorName.clear();
        txtPhone.clear();
        txtEmail.clear();
    }
}
