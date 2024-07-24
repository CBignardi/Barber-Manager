package com.bignardi.barbermanager.controller;

import com.bignardi.barbermanager.App;
import com.bignardi.barbermanager.model.Appointment;
import com.bignardi.barbermanager.model.Client;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

public class OverviewController {
    @FXML
    private ListView<Appointment> firstListView;
    @FXML
    private ListView<Appointment> secondListView;
    @FXML
    private ListView<Appointment> thirdListView;
    @FXML
    private ListView<Appointment> fourthListView;
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
    private ArrayList<ListView<Appointment>> listViews;
    private ArrayList<Label> labels;
    @FXML
    private ListView<Appointment> listView;
    private Appointment prova = new Appointment();

    @FXML
    public void initialize() {
        appointments = new ArrayList<>();
        usualClients = new ArrayList<>();

        listViews = new ArrayList<>(4);
        labels = new ArrayList<>(4);
        listViews.add(firstListView);
        listViews.add(secondListView);
        listViews.add(thirdListView);
        listViews.add(fourthListView);
        labels.add(textFirstDay);
        labels.add(textSecondDay);
        labels.add(textThirdDay);
        labels.add(textFourthDay);

        addAppointment(new Appointment(new Client("chri", 30), LocalDateTime.of(2024, 2, 2, 15, 10)));
        addAppointment(new Appointment(new Client("chri", 30), LocalDateTime.of(2024, 2, 3, 15, 10)));
        addAppointment(new Appointment(new Client("chri", 30), LocalDateTime.of(2024, 2, 4, 15, 10)));
        addAppointment(new Appointment(new Client("chri", 30), LocalDateTime.of(2024, 2, 5, 15, 10)));
        /*
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Appointment>() {
            @Override
            public void changed(ObservableValue<? extends Appointment> observableValue, Appointment appointment, Appointment t1) {
                prova = listView.getSelectionModel().getSelectedItem();
                System.out.println(prova.toString());
            }
        });
         */

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
        LocalDate day = dayView;

        for (int i = 0; i < 4; i++) {
            listViews.get(i).getItems().addAll(getSubAppointments(day));
            labels.get(i).setText(day.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            day = day.plusDays(1);
        }
    }

    private ArrayList<Appointment> getSubAppointments(LocalDate Day) {
        ArrayList<Appointment> subAppointments = new ArrayList<>();
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
