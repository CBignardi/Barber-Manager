package com.bignardi.barbermanager.controller;

import com.bignardi.barbermanager.model.Appointment;
import com.bignardi.barbermanager.model.Client;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private static final ArrayList<Client> usualClients = new ArrayList<>();
    private LocalDate dayView;
    final int numberOfTables = 4;
    private final ArrayList<ListView<Appointment>> listViews = new ArrayList<>(numberOfTables);
    private final ArrayList<Label> labels = new ArrayList<>(numberOfTables);
    private Appointment selectedItem;


    @FXML
    public void initialize() {
        listViews.add(firstListView);
        listViews.add(secondListView);
        listViews.add(thirdListView);
        listViews.add(fourthListView);
        labels.add(textFirstDay);
        labels.add(textSecondDay);
        labels.add(textThirdDay);
        labels.add(textFourthDay);

        for(ListView<Appointment> listView : listViews){
            listView.getSelectionModel().selectedItemProperty().addListener(this::changed);
            listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        }

        EXAMPLE();
        updateTables();
    }

    private void changed(ObservableValue<? extends Appointment> observable, Appointment oldValue, Appointment newValue) {
        selectedItem = newValue;
    }

    public void EXAMPLE() {
        dayView = LocalDate.now();
        Client c =new Client("chri", 30);
        addUsualClient(c);
        addAppointment(new Appointment(c, LocalDateTime.of(LocalDate.now(), LocalTime.of(16 ,0))));
        addAppointment(new Appointment(c, LocalDateTime.of(LocalDate.now(), LocalTime.of(16 ,0))));
        addAppointment(new Appointment(c, LocalDateTime.of(LocalDate.now(), LocalTime.of(15 ,0))));
        addAppointment(new Appointment(c, LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(14 ,0))));
        addAppointment(new Appointment(c, LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(14 ,0))));
        addAppointment(new Appointment(c, LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(14 ,0))));
        /*
        for (Appointment a : appointments) {
            System.out.println(a.toStringFull());
        }
        */
        updateTables();
    }


    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
        appointments.sort(Comparator.comparing(Appointment::getDate));
    }

    public void removeAppointment() {
        appointments.remove(selectedItem);
        updateTables();
    }

    public void addUsualClient(Client usualClient) {
        if (usualClient != null) {
            usualClients.add(usualClient);
            usualClients.sort(Comparator.comparing(Client::getName));
        }
    }

    public void updateTables() {
        LocalDate day = dayView;

        for (int i = 0; i < 4; i++) {
            listViews.get(i).setItems(getSubListAppointment(day));
            labels.get(i).setText(day.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            day = day.plusDays(1);
        }
    }

    private ObservableList<Appointment> getSubListAppointment(LocalDate day) {
        ObservableList<Appointment> subAppointments = FXCollections.observableArrayList();
        for (Appointment appointment : appointments) {
            if (appointment.getDate().toLocalDate().equals(day)) {
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
                updateTables();
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
    private void handleOpen() {
        try {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
            fileChooser.getExtensionFilters().add(extFilter);

            File file = fileChooser.showOpenDialog(null);

            if (file != null) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                appointments = mapper.readValue(file, new TypeReference<>() {
                });
            }
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Could not load data").showAndWait();
        }


    }

    @FXML
    private void handleSaveAs() {
        try {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Select a directory");
            directoryChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));
            File directory = directoryChooser.showDialog(null);
            if(directory != null){
                File fileAppointment = new File(directory.toURI());

            }

            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
            fileChooser.getExtensionFilters().add(extFilter);

            File fileAppointment = fileChooser.showSaveDialog(null);
            fileAppointment = fileChooser.
            if (fileAppointment != null) {
                File fileUsualClient = fileAppointment.
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                mapper.writerWithDefaultPrettyPrinter().writeValue(fileAppointment, appointments);
                mapper.writerWithDefaultPrettyPrinter().writeValue();
            }
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Could not save data").showAndWait();
        }
    }

    @FXML
    public void handleSave(){

    }

    @FXML
    public void handleNew(){
        appointments.clear();
        usualClients.clear();
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

    @FXML
    public void clearSelectedItems(){
        for (ListView<Appointment> listView : listViews){
            listView.getSelectionModel().clearSelection();
        }
    }
}
