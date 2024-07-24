package com.bignardi.barbermanager.controller;

import com.bignardi.barbermanager.model.Appointment;
import com.bignardi.barbermanager.model.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

public class OverviewController {
    @FXML
    private TableView<Appointment> firstDayTable;
    @FXML
    private TableView<Appointment> secondDayTable;
    @FXML
    private TableView<Appointment> thirdDayTable;
    @FXML
    private TableView<Appointment> fourthDayTable;
    @FXML
    private TableColumn<Client, String> firstDayNameColumn;
    @FXML
    private TableColumn<Appointment, String> firstDayHourColumn;
    @FXML
    private TableColumn<Client, String> secondDayNameColumn;
    @FXML
    private TableColumn<Appointment, String> secondDayHourColumn;
    @FXML
    private TableColumn<Client, String> thirdDayNameColumn;
    @FXML
    private TableColumn<Appointment, String> thirdDayHourColumn;
    @FXML
    private TableColumn<Client, String> fourthDayNameColumn;
    @FXML
    private TableColumn<Appointment, String> fourthDayHourColumn;
    @FXML
    private Label textFirstDay;
    @FXML
    private Label textSecondDay;
    @FXML
    private Label textThirdDay;
    @FXML
    private Label textFourthDay;
    @FXML
    private DatePicker goToDay;
    private static ArrayList<Appointment> appointments = new ArrayList<>();
    private static ArrayList<Client> usualClients = new ArrayList<>();
    private LocalDate dayView;
    private ArrayList<TableView<Appointment>> tableViews;
    private ArrayList<Label> labels;

    @FXML
    public void initialize() {
        appointments = new ArrayList<>();
        usualClients = new ArrayList<>();
        firstDayNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        firstDayHourColumn.setCellValueFactory(new PropertyValueFactory<>("timeString"));
        secondDayNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        secondDayHourColumn.setCellValueFactory(new PropertyValueFactory<>("timeString"));
        thirdDayNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        thirdDayHourColumn.setCellValueFactory(new PropertyValueFactory<>("timeString"));
        fourthDayNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        fourthDayHourColumn.setCellValueFactory(new PropertyValueFactory<>("timeString"));

        tableViews = new ArrayList<>(4);
        labels = new ArrayList<>(4);
        tableViews.add(firstDayTable);
        tableViews.add(secondDayTable);
        tableViews.add(thirdDayTable);
        tableViews.add(fourthDayTable);
        labels.add(textFirstDay);
        labels.add(textSecondDay);
        labels.add(textThirdDay);
        labels.add(textFourthDay);

        if (!appointments.isEmpty()) {
            dayView = appointments.getFirst().getDate().toLocalDate();
        } else {
            dayView = LocalDate.now();
        }

        updateTables();
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
        appointments.sort(Comparator.comparing(Appointment::getDate));
        updateTables();
    }

    public void removeAppointment(Appointment appointment) {
        appointments.remove(appointment);
        updateTables();
    }

    public void addUsualClient(Client usualClient) {
        if (usualClient != null) {
            usualClients.add(usualClient);
            usualClients.sort(Comparator.comparing(Client::getName));
        }
    }

    public void removeUsualClient(Client usualClient) {
        usualClients.remove(usualClient);
    }

    public static ArrayList<Client> getArrayUsualClient() {
        return usualClients;
    }

    public void updateTables() {
        ObservableList<Appointment> subAppointments;
        LocalDate day = dayView;
        for (int i = 0; i < 4; i++) {
            subAppointments = getSubAppointments(day);
            tableViews.get(i).setItems(subAppointments);

            labels.get(i).setText(day.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            day = day.plusDays(1);
        }
    }

    private ObservableList<Appointment> getSubAppointments(LocalDate Day) {
        ObservableList<Appointment> subAppointments = FXCollections.observableArrayList();
        for (Appointment appointment : appointments) {
            if (appointment.getDate().toLocalDate().equals(Day)) {
                subAppointments.add(appointment);
            }
        }
        return subAppointments;
    }

    void showAlert(String title, String explanation) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Alert");
        alert.setHeaderText(title);
        alert.setContentText(explanation);
        alert.showAndWait();
    }

    /* da correggere apointment in client
    ObservableList<Appointment> getClientData() {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        try {
            appointments.add(new Appointment("Christian", 2024, 4, 24, 15, 5));
            appointments.add(new Appointment("Alfonso", 2024, 4, 24, 16, 15));
            appointments.add(new Appointment("Giovanni", 2024, 4, 24, 16, 45));
            appointments.add(new Appointment("Luca", 2024, 4, 24, 14, 0));
        } catch (IllegalArgumentException e) {
            showWrongDateAlert();
        }
        return appointments;
    }
    */


    @FXML
    public void handleNewCustomer() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("newcustomer-view.fxml"));
            DialogPane view = loader.load();
            NewCustomerController controller = loader.getController();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("New Customer");
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.setDialogPane(view);

            Optional<ButtonType> clickedButton = dialog.showAndWait();
            if (clickedButton.orElse(ButtonType.CANCEL) == ButtonType.OK) {
                addUsualClient(controller.getUsualClient());
            }

        } catch (NullPointerException e) {
            showAlert("Wrong Customer value", "All the field has to be filled, please insert a correct value in each field.");
        } catch (NumberFormatException e) {
            showAlert("Wrong Customer value", "The duration value is wrong, please insert a correct duration.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleNewAppointment() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("newappointment-view.fxml"));
            DialogPane view = loader.load();
            NewAppointmentController controller = loader.getController();

            controller.setArrayClientsName(usualClients);

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("New Appointment");
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.setDialogPane(view);

            Optional<ButtonType> clickedButton = dialog.showAndWait();
            if (clickedButton.orElse(ButtonType.CANCEL) == ButtonType.OK) {
                addAppointment(controller.getAppointment());
            }
        } catch (NullPointerException e) {
            showAlert("Wrong Appointment value", "All the field has to be filled, please insert a correct value in each field.");
        } catch (DateTimeException e) {
            showAlert("Wrong Appointment value", "The date value is wrong, please insert a correct date. The correct form is: hour minute (example: 15 35).");
        } catch (NumberFormatException e) {
            showAlert("Wrong Appointment value", "The duration value is wrong, please insert a correct duration.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleRemoveAppointment() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("removeappointment-view.fxml"));
            DialogPane view = loader.load();
            RemoveAppointmentController controller = loader.getController();

            controller.setArrayClientsName(appointments);

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Remove Appointment");
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.setDialogPane(view);

            Optional<ButtonType> clickedButton = dialog.showAndWait();
            if (clickedButton.orElse(ButtonType.CANCEL) == ButtonType.OK) {
                removeAppointment(controller.getAppointment());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void setPreviousFourDay() {
        dayView = dayView.minusDays(4);
        updateTables();
    }

    @FXML
    public void setPreviousDay() {
        dayView = dayView.minusDays(1);
        updateTables();
    }

    @FXML
    public void setToday() {
        dayView = LocalDate.now();
        updateTables();
    }

    @FXML
    public void setNextDay() {
        dayView = dayView.plusDays(1);
        updateTables();
    }

    @FXML
    public void setNextFourDay() {
        dayView = dayView.plusDays(4);
        updateTables();
    }

    @FXML
    public void setGoToDay() {
        dayView = goToDay.getValue();
        updateTables();
    }
}
