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
import java.time.format.DateTimeFormatter;
import java.util.*;

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

        EXAMPLE();
        updateTables();
    }

    private void EXAMPLE() {
        try {
            List<File> files = new ArrayList<>();
            files.add(new File("C:/Users/chrib/Desktop/EsempioBarber/UsualClients.json"));
            files.add(new File("C:/Users/chrib/Desktop/EsempioBarber/Appointments.json"));

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            for (File file : files) {
                if (file.getName().equals("Appointments.json")) {
                    appointments = mapper.readValue(file, new TypeReference<>() {
                    });
                } else {
                    usualClients = mapper.readValue(file, new TypeReference<>() {
                    });
                }
            }
            updateTables();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void changed(ObservableValue<? extends Appointment> observable, Appointment oldValue, Appointment newValue) {
        selectedItem = newValue;
    }

    public void removeAppointmentSelected() {
        if (selectedItem != null) {
            removeAppointment(selectedItem);
            removeUsualClient(selectedItem.getClient());
            updateTables();
        }
    }

    public void addAppointment(Appointment appointment, boolean isAddUsualClientSelected) {
        System.out.println("start add");
        appointments.add(appointment);
        System.out.println("add");
        if (isAddUsualClientSelected) {
            addUsualClient(appointment.getClient());
        }
        appointments.sort(Comparator.comparing(Appointment::getDate));
        System.out.println("sort");
        checkSameTimeAppointment(appointment);
    }

    public void removeAppointment(Appointment appointment) {
        if (appointment != null) {
            appointments.remove(appointment);
        }
    }

    private void checkSameTimeAppointment(Appointment appointment) {
        System.out.println("appoits " + appointments.toString());
        int appointmentIndex = getAppointmentIndex(appointment);
        if (appointmentIndex != 0) {
            Appointment prevoiusAppointment = appointments.get(appointmentIndex - 1);
            System.out.println("prec " + prevoiusAppointment.toStringFull());
            System.out.println(prevoiusAppointment.getClient().toString());
            if (prevoiusAppointment.getDate().plusMinutes(prevoiusAppointment.getClient().getDuration()).isAfter(appointment.getDate())) {
                showInfoSameAppointmentTime();
            }
        }
    }

    private int getAppointmentIndex(Appointment appointment) {
        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).equals(appointment)) {
                return i;
            }
        }
        return appointments.size() + 1;
    }

    private void showInfoSameAppointmentTime() {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Information");
        info.setHeaderText(null);
        info.setContentText("The appointment inserted is in the same time of another appointment, check if you put the correct time and date.");
        info.showAndWait();
    }

    public void addUsualClient(Client usualClient) {
        if (usualClient != null) {
            if (!containsUsualClient(usualClient)) {
                usualClients.add(usualClient);
                usualClients.sort(Comparator.comparing(Client::getName));
            }
        }
    }

    public void removeUsualClient(Client client) {
        if (client != null) {
            usualClients.remove(client);
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
                addAppointment(controller.getAppointment(), controller.getIsUsualCustomerSelected());
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
    public void handleEditCustomer() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("editcustomer-view.fxml"));
            DialogPane view = loader.load();
            EditCustomerController controller = loader.getController();

            ObservableList<Client> observableClients = FXCollections.observableArrayList();
            observableClients.addAll(usualClients);
            controller.setArrayClientsName(observableClients);

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Edit Customer");
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.setDialogPane(view);

            Optional<ButtonType> clickedButton = dialog.showAndWait();
            if (clickedButton.orElse(ButtonType.CANCEL) == ButtonType.OK) {
                usualClients = controller.getArrayClient();
                updateTables();
            }
        } catch (IOException e) {
            showAlert("Wrong Edit configuration", "");
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
                        appointments = mapper.readValue(file, new TypeReference<>() {
                        });
                    } else {
                        usualClients = mapper.readValue(file, new TypeReference<>() {
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
