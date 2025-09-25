package com.example.elite_driving_school.controller;

import com.example.elite_driving_school.bo.BOFactory;
import com.example.elite_driving_school.bo.custom.CourseBO;
import com.example.elite_driving_school.dto.CourseDTO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.math.BigDecimal;
import java.util.List;

public class Course_FormController {

    @FXML
    private TableColumn<CourseDTO, String> colDuration;

    @FXML
    private TableColumn<CourseDTO, BigDecimal> colFee;

    @FXML
    private TableColumn<CourseDTO, String> colId;

    @FXML
    private TableColumn<CourseDTO, String> colName;

    @FXML
    private TableView<CourseDTO> tblCourse;

    @FXML
    private TextField txtCourse;

    @FXML
    private TextField txtCourseID;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtFee;

    private final CourseBO courseBO =
            (CourseBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.COURSE);

    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));

        loadAllCourses();
    }

    private void loadAllCourses() {
        try {
            List<CourseDTO> courses = courseBO.getAllCourses();
            tblCourse.setItems(FXCollections.observableArrayList(courses));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load courses: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnAddCouOnAction(ActionEvent event) {
        try {
            CourseDTO dto = new CourseDTO();
            dto.setId(txtCourseID.getText()); // use the ID you provide or generated
            dto.setName(txtCourse.getText());
            dto.setDuration(txtDuration.getText());
            dto.setFee(new BigDecimal(txtFee.getText()));

            if (courseBO.saveCourse(dto)) {
                new Alert(Alert.AlertType.INFORMATION, "Course added successfully!").show();
                loadAllCourses();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to add course!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnDeleteCouOnAction(ActionEvent event) {
        try {
            String id = txtCourseID.getText(); // keep as String
            if (courseBO.deleteCourse(id)) {
                new Alert(Alert.AlertType.INFORMATION, "Course deleted successfully!").show();
                loadAllCourses();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete course!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnSearchCouOnAction(ActionEvent event) {
        try {
            String id = txtCourseID.getText(); // keep as String
            CourseDTO dto = courseBO.searchCourse(id);
            if (dto != null) {
                txtCourseID.setText(dto.getId());
                txtCourse.setText(dto.getName());
                txtDuration.setText(dto.getDuration());
                txtFee.setText(dto.getFee().toString());
            } else {
                new Alert(Alert.AlertType.WARNING, "Course not found!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateCouOnAction(ActionEvent event) {
        try {
            CourseDTO dto = new CourseDTO();
            dto.setId(txtCourseID.getText()); // String ID
            dto.setName(txtCourse.getText());
            dto.setDuration(txtDuration.getText());
            dto.setFee(new BigDecimal(txtFee.getText()));

            if (courseBO.updateCourse(dto)) {
                new Alert(Alert.AlertType.INFORMATION, "Course updated successfully!").show();
                loadAllCourses();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update course!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        CourseDTO selected = tblCourse.getSelectionModel().getSelectedItem();
        if (selected != null) {
            txtCourseID.setText(selected.getId());
            txtCourse.setText(selected.getName());
            txtDuration.setText(selected.getDuration());
            txtFee.setText(selected.getFee().toString());
        }
    }

    private void clearFields() {
        txtCourseID.clear();
        txtCourse.clear();
        txtDuration.clear();
        txtFee.clear();
    }
}
