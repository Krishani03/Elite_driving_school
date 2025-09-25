package com.example.elite_driving_school.controller;

import com.example.elite_driving_school.bo.custom.PaymentBO;
import com.example.elite_driving_school.bo.custom.impl.PaymentBOImpl;
import com.example.elite_driving_school.dto.PaymentDTO;
import com.example.elite_driving_school.dto.StudentDTO;
import com.example.elite_driving_school.entity.Payment;
import com.example.elite_driving_school.entity.Student;
import com.example.elite_driving_school.exception.PaymentException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Payment_FormController {
    @FXML
    private TextField cmbAmount;

    @FXML
    private ComboBox<String> cmbMethod;

    @FXML
    private TableColumn<PaymentDTO, BigDecimal> colAmount;

    @FXML
    private TableColumn<PaymentDTO, LocalDateTime> colDate;

    @FXML
    private TableColumn<PaymentDTO, Long> colId;

    @FXML
    private TableColumn<PaymentDTO, String> colMethod;

    @FXML
    private TableColumn<PaymentDTO, String> colStudent;

    @FXML
    private ComboBox<Student> comStudent;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Label lblId;

    @FXML
    private TableView<PaymentDTO> tblPayment;

    private PaymentBO paymentBO = new PaymentBOImpl();
    private SessionFactory sessionFactory;

    public void initialize(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colStudent.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colMethod.setCellValueFactory(new PropertyValueFactory<>("method"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("payment_date"));

        loadPayments();
        loadStudents();
        loadNextPaymentId();

        cmbMethod.setItems(FXCollections.observableArrayList("Cash", "Card", "Online"));

    }


    @FXML
    void btnAddPayOnAction(ActionEvent event) {
        Student student = comStudent.getValue();
        if (student == null) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Select a student!");
            return;
        }

        BigDecimal amount;
        try {
            amount = new BigDecimal(cmbAmount.getText());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Enter a valid amount!");
            return;
        }

        String method = cmbMethod.getValue();
        if (method == null || method.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Select a payment method!");
            return;
        }

        LocalDate date = datePicker.getValue();
        LocalDateTime paymentDate = (date != null) ? LocalDateTime.of(date, LocalTime.now()) : LocalDateTime.now();

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setAmount(amount);
        paymentDTO.setMethod(method);
        paymentDTO.setPayment_date(paymentDate);

        try (Session session = sessionFactory.openSession()) {
            paymentBO.processPayment(paymentDTO, student, session);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Payment added successfully!");
            loadPayments();
            clearForm();
            loadNextPaymentId();
        } catch (PaymentException e) {
            showAlert(Alert.AlertType.ERROR, "Payment Error", e.getMessage());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML
    void btnClearPayOnAction(ActionEvent event) {
        clearForm();
    }

    @FXML
    void onClickTable(MouseEvent event) {
        PaymentDTO selected = tblPayment.getSelectionModel().getSelectedItem();
        if (selected != null) {
            lblId.setText(String.valueOf(selected.getId()));
            cmbAmount.setText(selected.getAmount().toString());
            cmbMethod.setValue(selected.getMethod());
            datePicker.setValue(selected.getPayment_date().toLocalDate());
            comStudent.getSelectionModel().select(selected.getStudent());
        }
    }
    private void clearForm() {
        cmbAmount.clear();
        cmbMethod.getSelectionModel().clearSelection();
        comStudent.getSelectionModel().clearSelection();
        datePicker.setValue(null);
    }
    private void loadPayments() {
        try (Session session = sessionFactory.openSession()) {
            ObservableList<PaymentDTO> payments = FXCollections.observableArrayList();
            List<Student> students = comStudent.getItems();
            for (Student s : students) {
                List<PaymentDTO> studentPayments = paymentBO.getPaymentsByStudent(s, session);
                for (PaymentDTO dto : studentPayments) {
                    dto.setStudent(s); // set student reference
                    payments.add(dto);
                }
            }
            tblPayment.setItems(payments);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadNextPaymentId() {
        try (Session session = sessionFactory.openSession()) {
            String nextId = paymentBO.getNextPaymentId(session);
            lblId.setText(nextId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadStudents() {
        try (Session session = sessionFactory.openSession()) {
            List<Student> students = session.createQuery("FROM Student", Student.class).list();
            comStudent.setItems(FXCollections.observableArrayList(students));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
