package com.example.elite_driving_school.controller;

import com.example.elite_driving_school.bo.BOFactory;
import com.example.elite_driving_school.bo.BOFactory.BOTypes;
import com.example.elite_driving_school.bo.custom.StudentBO;
import com.example.elite_driving_school.dto.StudentDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;

public class Student_FormController {

    @FXML
    private TableColumn<StudentDTO, LocalDate> colDate;
    @FXML
    private TableColumn<StudentDTO, String> colEmail;
    @FXML
    private TableColumn<StudentDTO, String> colFName;
    @FXML
    private TableColumn<StudentDTO, String> colId;
    @FXML
    private TableColumn<StudentDTO, String> colLName;
    @FXML
    private TableColumn<StudentDTO, String> colPhone;
    @FXML
    private TableView<StudentDTO> tblStudent;

    @FXML
    private DatePicker txtDate;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPhone;
    @FXML
    private TextField txtStudent;
    @FXML
    private TextField txtStudentLast;
    @FXML
    private TextField txtStudentId;

    private final StudentBO studentBO =
            (StudentBO) BOFactory.getBoFactory().getBO(BOTypes.STUDENT);

    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFName.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        colLName.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("registration_date"));

        loadAllStudents();
    }

    private void loadAllStudents() {
        try {
            ObservableList<StudentDTO> students = FXCollections.observableArrayList(studentBO.getAllStudents());
            tblStudent.setItems(students);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load students: " + e.getMessage()).show();
        }
    }
    @FXML
    void btnAddStudentOnAction(ActionEvent event) {
        try {
            String firstName = txtStudent.getText().trim();
            String lastName = txtStudentLast.getText().trim();
            String email = txtEmail.getText().trim();
            String phone = txtPhone.getText().trim();
            LocalDate regDate = txtDate.getValue();

            // --- Validations ---
            if (firstName.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "First name is required!").show();
                return;
            }

            if (lastName.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Last name is required!").show();
                return;
            }

            if (email.isEmpty() || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                new Alert(Alert.AlertType.WARNING, "Valid email is required!").show();
                return;
            }

            if (phone.isEmpty() || !phone.matches("^\\d{10}$")) {
                new Alert(Alert.AlertType.WARNING, "Phone number must be 10 digits!").show();
                return;
            }

            if (regDate == null) {
                new Alert(Alert.AlertType.WARNING, "Registration date is required!").show();
                return;
            }


            StudentDTO student = new StudentDTO(
                    null,
                    firstName,
                    lastName,
                    email,
                    phone,
                    regDate
            );

            StudentDTO savedStudent = studentBO.saveStudent(student);

            if (savedStudent != null) {
                txtStudentId.setText(savedStudent.getId());
                new Alert(Alert.AlertType.INFORMATION, "Student added successfully!").show();
                loadAllStudents();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to add student!").show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }



    private void clearFields() {
        txtStudentId.clear();
        txtStudent.clear();
        txtStudentLast.clear();
        txtEmail.clear();
        txtPhone.clear();
        txtDate.setValue(null);
    }

    @FXML
    void btnClearStuOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteStuOnAction(ActionEvent event) {
        try {
            if (studentBO.deleteStudent((txtStudentId.getText()))) {
                new Alert(Alert.AlertType.INFORMATION, "Student deleted successfully!").show();
                loadAllStudents();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete student!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnSearchStuOnAction(ActionEvent event) {
        try {
            StudentDTO student = studentBO.searchStudent((txtStudentId.getText()));
            if (student != null) {
                txtStudentId.setText(String.valueOf(student.getId()));
                txtStudent.setText(student.getFirst_name());
                txtStudentLast.setText(student.getLast_name());
                txtEmail.setText(student.getEmail());
                txtPhone.setText(student.getPhone());
                txtDate.setValue(student.getRegistration_date());
            } else {
                new Alert(Alert.AlertType.WARNING, "Student not found!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateStudentOnAction(ActionEvent event) {
        try {
            StudentDTO student = new StudentDTO(
                    (txtStudentId.getText()),
                    txtStudent.getText(),
                    txtStudentLast.getText(),
                    txtEmail.getText(),
                    txtPhone.getText(),
                    txtDate.getValue()
            );

            if (studentBO.updateStudent(student)) {
                new Alert(Alert.AlertType.INFORMATION, "Student updated successfully!").show();
                loadAllStudents();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update student!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        StudentDTO selected = tblStudent.getSelectionModel().getSelectedItem();
        if (selected != null) {
            txtStudentId.setText(String.valueOf(selected.getId()));
            txtStudent.setText(selected.getFirst_name());
            txtStudentLast.setText(selected.getLast_name());
            txtEmail.setText(selected.getEmail());
            txtPhone.setText(selected.getPhone());
            txtDate.setValue(selected.getRegistration_date());
        }
    }
}
