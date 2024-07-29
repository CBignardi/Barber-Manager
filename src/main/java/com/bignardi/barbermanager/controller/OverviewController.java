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

import java.io.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedDeque;

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
    private LocalDate dayView = LocalDate.now();
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

        for (ListView<Appointment> listView : listViews) {
            listView.getSelectionModel().selectedItemProperty().addListener(this::changed);
            listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        }

        updateTables();
    }

    private void changed(ObservableValue<? extends Appointment> observable, Appointment oldValue, Appointment newValue) {
        selectedItem = newValue;
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
        addUsualClient(appointment.getClient());
        appointments.sort(Comparator.comparing(Appointment::getDate));
    }

    public void removeAppointment() {
        if (selectedItem != null) {
            appointments.remove(selectedItem);
            removeUsualClient();
            updateTables();
        }
    }

    public void removeUsualClient() {
        usualClients.remove(selectedItem.getClient());
    }

    public void addUsualClient(Client usualClient) {
        if (usualClient != null) {
            if (!containsUsualClient(usualClient)) {
                usualClients.add(usualClient);
                usualClients.sort(Comparator.comparing(Client::getName));
            }
        }
    }

    private boolean containsUsualClient(Client usualClient) {
        for (Client client : usualClients) {
            if (usualClient.getName().equals(client.getName()) && usualClient.getDuration() == client.getDuration()) {
                return true;
            }
        }
        return false;
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
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));

            List<File> files = fileChooser.showOpenMultipleDialog(null);
            if (files.size() == 2) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                for (File file : files) {
                    if (file.getName().equals("Appointments.json")) {
                        appointments = mapper.readValue(file, new TypeReference<ArrayList<Appointment>>() {
                        });
                    } else {
                        usualClients = mapper.readValue(file, new TypeReference<ArrayList<Client>>() {
                        });
                    }
                }
                updateTables();
            } else {
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException e) {
            showAlert("Open failed", "Wrong file selected, please select two files called: \"Appointments.json\" and \"UsualClients.json\".");
        } catch (IOException e) {
            showAlert("Open failed", "The loading of the file has failed");
        }
    }

    @FXML
    private void handleSave() {
        try {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Select a directory");
            directoryChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));
            File directory = directoryChooser.showDialog(null);
            if (directory != null) {
                File fileAppointment = new File(directory.getAbsolutePath() + "/Appointments.json");
                File fileClient = new File(directory.getAbsolutePath() + "/UsualClients.json");
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                mapper.writeValue(fileAppointment, appointments);
                mapper.writeValue(fileClient, usualClients);
            }
        } catch (IOException e) {
            showAlert("Save failed", "The saving of the file has failed");
        }
    }

    @FXML
    public void handleNew() {
        appointments.clear();
        usualClients.clear();
        updateTables();
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
    public void clearSelectedItems() {
        for (ListView<Appointment> listView : listViews) {
            listView.getSelectionModel().clearSelection();
        }
    }
}
