package com.example.elite_driving_school.controller;

import com.example.elite_driving_school.bo.BOFactory;
import com.example.elite_driving_school.bo.custom.CourseBO;
import com.example.elite_driving_school.bo.custom.InstructorBO;
import com.example.elite_driving_school.bo.custom.LessonBO;
import com.example.elite_driving_school.bo.custom.StudentBO;
import com.example.elite_driving_school.dto.CourseDTO;
import com.example.elite_driving_school.dto.InstructorDTO;
import com.example.elite_driving_school.dto.LessonDTO;
import com.example.elite_driving_school.dto.StudentDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Lesson_FormController {
    @FXML
    private TableColumn<LessonDTO, String> colCourse;

    @FXML
    private TableColumn<LessonDTO, LocalDateTime> colETime;

    @FXML
    private TableColumn<LessonDTO, Long> colId;

    @FXML
    private TableColumn<LessonDTO, String> colInstructor;

    @FXML
    private TableColumn<LessonDTO, LocalDateTime> colSTime;

    @FXML
    private TableColumn<LessonDTO, String> colStudent;

    @FXML
    private Label lblId;

    @FXML
    private TableView<LessonDTO> tblLesson;

    @FXML
    private TextField txtEndTime;

    @FXML
    private TextField txtStartTime;

    @FXML
    private ComboBox<CourseDTO> comCourse;

    @FXML
    private ComboBox<InstructorDTO> comInstr;

    @FXML
    private ComboBox<StudentDTO> comStudent;

    private final LessonBO lessonBO = (LessonBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.LESSON);
    private final StudentBO studentBO = (StudentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.STUDENT);
    private final CourseBO courseBO = (CourseBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.COURSE);
    private final InstructorBO instructorBO = (InstructorBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.INSTRUCTOR);

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");


    public void initialize(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colStudent.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colCourse.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        colInstructor.setCellValueFactory(new PropertyValueFactory<>("instructorName"));
        colSTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        colETime.setCellValueFactory(new PropertyValueFactory<>("endTime"));

        loadAllLessons();
        loadCombos();
    }

    private void loadCombos() {
        try {
            comStudent.setItems(FXCollections.observableArrayList(studentBO.getAllStudents()));
            comCourse.setItems(FXCollections.observableArrayList(courseBO.getAllCourses()));
            comInstr.setItems(FXCollections.observableArrayList(instructorBO.getAllInstructors()));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load combo data: " + e.getMessage()).show();
        }
    }

    private void loadAllLessons() {
        try {
            ArrayList<LessonDTO> lessons = lessonBO.getAllLessons();
            tblLesson.setItems(FXCollections.observableArrayList(lessons));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load lessons: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnAddLesOnAction(ActionEvent event) {
        try {
            LessonDTO dto = new LessonDTO();
            dto.setStudentId(comStudent.getValue().getId());
            dto.setCourseId(comCourse.getValue().getId());
            dto.setInstructorId(comInstr.getValue().getId());
            dto.setStartTime(LocalDateTime.parse(txtStartTime.getText(), formatter));
            dto.setEndTime(LocalDateTime.parse(txtEndTime.getText(), formatter));

            if (lessonBO.saveLesson(dto)) {
                new Alert(Alert.AlertType.INFORMATION, "Lesson added successfully!").show();
                loadAllLessons();
                clearFields();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    private void clearFields() {
        lblId.setText("");
        txtStartTime.clear();
        txtEndTime.clear();
        comStudent.getSelectionModel().clearSelection();
        comCourse.getSelectionModel().clearSelection();
        comInstr.getSelectionModel().clearSelection();
    }

    @FXML
    void btnClearLesOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteLesOnAction(ActionEvent event) {
        try {
            if (lessonBO.deleteLesson(Long.parseLong(lblId.getText()))) {
                new Alert(Alert.AlertType.INFORMATION, "Lesson deleted successfully!").show();
                loadAllLessons();
                clearFields();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnSearchLesOnAction(ActionEvent event) {
        try {
            LessonDTO dto = lessonBO.searchLesson(Long.parseLong(lblId.getText()));
            if (dto != null) {
                lblId.setText(String.valueOf(dto.getId()));
                txtStartTime.setText(dto.getStartTime().format(formatter));
                txtEndTime.setText(dto.getEndTime().format(formatter));
                selectComboValue(comStudent, dto.getStudentId());
                selectComboValue(comCourse, dto.getCourseId());
                selectComboValue(comInstr, dto.getInstructorId());
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateLesOnAction(ActionEvent event) {
        try {
            LessonDTO dto = new LessonDTO();
            dto.setId(Long.parseLong(lblId.getText()));
            dto.setStudentId(comStudent.getValue().getId());
            dto.setCourseId(comCourse.getValue().getId());
            dto.setInstructorId(comInstr.getValue().getId());
            dto.setStartTime(LocalDateTime.parse(txtStartTime.getText(), formatter));
            dto.setEndTime(LocalDateTime.parse(txtEndTime.getText(), formatter));

            if (lessonBO.updateLesson(dto)) {
                new Alert(Alert.AlertType.INFORMATION, "Lesson updated successfully!").show();
                loadAllLessons();
                clearFields();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        LessonDTO selected = tblLesson.getSelectionModel().getSelectedItem();
        if (selected != null) {
            lblId.setText(String.valueOf(selected.getId()));
            txtStartTime.setText(selected.getStartTime().format(formatter));
            txtEndTime.setText(selected.getEndTime().format(formatter));
            selectComboValue(comStudent, selected.getStudentId());
            selectComboValue(comCourse, selected.getCourseId());
            selectComboValue(comInstr, selected.getInstructorId());
        }
    }
    private <T> void selectComboValue(ComboBox<T> comboBox, Long id) {
        if (id == null) return;

        for (T item : comboBox.getItems()) {
            if (item instanceof StudentDTO && ((StudentDTO) item).getId().equals(id)) {
                comboBox.setValue(item);
                break;
            } else if (item instanceof InstructorDTO && Long.valueOf(((InstructorDTO) item).getId()).equals(id)) {
                comboBox.setValue(item);
                break;
            } else if (item instanceof CourseDTO && ((CourseDTO) item).getId().equals(id)) {
                comboBox.setValue(item);
                break;
            }
        }
    }


}
