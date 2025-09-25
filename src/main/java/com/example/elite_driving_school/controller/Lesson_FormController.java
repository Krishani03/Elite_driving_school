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
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Lesson_FormController {

    @FXML private TableView<LessonDTO> tblLesson;
    @FXML private TableColumn<LessonDTO, String> colId, colStudent, colCourse, colInstructor;
    @FXML private TableColumn<LessonDTO, LocalDateTime> colSTime, colETime;

    @FXML private TextField txtLessonId, txtStartTime, txtEndTime;
    @FXML private ComboBox<StudentDTO> comStudent;
    @FXML private ComboBox<CourseDTO> comCourse;
    @FXML private ComboBox<InstructorDTO> comInstr;

    private final LessonBO lessonBO = (LessonBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.LESSON);
    private final StudentBO studentBO = (StudentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.STUDENT);
    private final CourseBO courseBO = (CourseBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.COURSE);
    private final InstructorBO instructorBO = (InstructorBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.INSTRUCTOR);

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    public void initialize() throws Exception {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colStudent.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colCourse.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        colInstructor.setCellValueFactory(new PropertyValueFactory<>("instructorName"));
        colSTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        colETime.setCellValueFactory(new PropertyValueFactory<>("endTime"));

        loadAllLessons();
        loadCombos();
        txtLessonId.setDisable(true); // ID is auto-generated
    }

    private void loadCombos() throws Exception {
        comStudent.setItems(FXCollections.observableArrayList(studentBO.getAllStudents()));
        comCourse.setItems(FXCollections.observableArrayList(courseBO.getAllCourses()));
        comInstr.setItems(FXCollections.observableArrayList(instructorBO.getAllInstructors()));
    }

    private void loadAllLessons() {
        List<LessonDTO> lessons = lessonBO.getAllLessons();
        tblLesson.setItems(FXCollections.observableArrayList(lessons));
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

            LessonDTO savedLesson = lessonBO.saveLesson(dto); // DAO handles ID generation

            if (savedLesson != null) {
                txtLessonId.setText(savedLesson.getId());
                new Alert(Alert.AlertType.INFORMATION, "Lesson added successfully!").show();
                loadAllLessons();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to add lesson!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateLesOnAction(ActionEvent event) {
        try {
            LessonDTO dto = new LessonDTO();
            dto.setId(txtLessonId.getText());
            dto.setStudentId(comStudent.getValue().getId());
            dto.setCourseId(comCourse.getValue().getId());
            dto.setInstructorId(comInstr.getValue().getId());
            dto.setStartTime(LocalDateTime.parse(txtStartTime.getText(), formatter));
            dto.setEndTime(LocalDateTime.parse(txtEndTime.getText(), formatter));

            if (lessonBO.updateLesson(dto)) {
                new Alert(Alert.AlertType.INFORMATION, "Lesson updated successfully!").show();
                loadAllLessons();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update lesson!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnDeleteLesOnAction(ActionEvent event) {
        try {
            if (lessonBO.deleteLesson(txtLessonId.getText())) {
                new Alert(Alert.AlertType.INFORMATION, "Lesson deleted successfully!").show();
                loadAllLessons();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete lesson!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnSearchLesOnAction(ActionEvent event) {
        try {
            LessonDTO dto = lessonBO.searchLesson(txtLessonId.getText());
            if (dto != null) {
                txtLessonId.setText(dto.getId());
                txtStartTime.setText(dto.getStartTime().format(formatter));
                txtEndTime.setText(dto.getEndTime().format(formatter));
                selectComboValue(comStudent, dto.getStudentId());
                selectComboValue(comCourse, dto.getCourseId());
                selectComboValue(comInstr, dto.getInstructorId());
            } else {
                new Alert(Alert.AlertType.WARNING, "Lesson not found!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        LessonDTO selected = tblLesson.getSelectionModel().getSelectedItem();
        if (selected != null) {
            txtLessonId.setText(selected.getId());
            txtStartTime.setText(selected.getStartTime().format(formatter));
            txtEndTime.setText(selected.getEndTime().format(formatter));
            selectComboValue(comStudent, selected.getStudentId());
            selectComboValue(comCourse, selected.getCourseId());
            selectComboValue(comInstr, selected.getInstructorId());
        }
    }

    private void clearFields() {
        txtLessonId.clear();
        txtStartTime.clear();
        txtEndTime.clear();
        comStudent.getSelectionModel().clearSelection();
        comCourse.getSelectionModel().clearSelection();
        comInstr.getSelectionModel().clearSelection();
    }

    private <T> void selectComboValue(ComboBox<T> comboBox, String id) {
        if (id == null) return;
        for (T item : comboBox.getItems()) {
            if ((item instanceof StudentDTO && ((StudentDTO) item).getId().equals(id)) ||
                    (item instanceof InstructorDTO && ((InstructorDTO) item).getId().equals(id)) ||
                    (item instanceof CourseDTO && ((CourseDTO) item).getId().equals(id))) {
                comboBox.setValue(item);
                break;
            }
        }
    }

    public void btnClearLesOnAction(ActionEvent actionEvent) {
        clearFields();
    }
}
