package com.bignardi.barbermanager.controller;

import com.bignardi.barbermanager.model.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

public class OverviewController {
    @FXML
    private TableView<Client> firstDayTable;
    @FXML
    private TableView<Client> secondDayTable;
    @FXML
    private TableView<Client> thirdDayTable;
    @FXML
    private TableView<Client> fourthDayTable;
    @FXML
    private TableColumn<Client, String> firstDayNameColumn;
    @FXML
    private TableColumn<Client, String> firstDayHourColumn;
    @FXML
    private TableColumn<Client, String> secondDayNameColumn;
    @FXML
    private TableColumn<Client, String> secondDayHourColumn;
    @FXML
    private TableColumn<Client, String> thirdDayNameColumn;
    @FXML
    private TableColumn<Client, String> thirdDayHourColumn;
    @FXML
    private TableColumn<Client, String> fourthDayNameColumn;
    @FXML
    private TableColumn<Client, String> fourthDayHourColumn;
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
    private ArrayList<Client> clients;
    private ArrayList<Client> usualClients;
    private LocalDate dayView;
    private ArrayList<TableView<Client>> tableViews;
    private ArrayList<Label> labels;

    @FXML
    public void initialize() {
        clients = new ArrayList<>();
        usualClients = new ArrayList<>();
        firstDayNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        firstDayHourColumn.setCellValueFactory(new PropertyValueFactory<>("stringHourMin"));
        secondDayNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        secondDayHourColumn.setCellValueFactory(new PropertyValueFactory<>("stringHourMin"));
        thirdDayNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        thirdDayHourColumn.setCellValueFactory(new PropertyValueFactory<>("stringHourMin"));
        fourthDayNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        fourthDayHourColumn.setCellValueFactory(new PropertyValueFactory<>("stringHourMin"));

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

        if (!clients.isEmpty()) {
            dayView = clients.getFirst().getDate().toLocalDate();
        } else {
            dayView = LocalDate.now();
        }

        updateTables();
    }

    public void addClient(Client client) {
        clients.add(client);
        clients.sort(Comparator.comparing(Client::getDate));
    }

    public void removeClient(Client client) {
        clients.remove(client);
    }

    public void addUsualClient(Client client) {
        usualClients.add(client);
        usualClients.sort(Comparator.comparing(Client::getName));
    }

    public void removeUsualClient(Client client) {
        usualClients.remove(client);
    }

    public ArrayList<Client> getArrayUsualClient(){
        return  usualClients;
    }

    public void updateTables() {
        ObservableList<Client> subClients;
        LocalDate day = dayView;
        for (int i = 0; i < 4; i++) {
            subClients = getSubClients(dayView);
            tableViews.get(i).setItems(subClients);
            labels.get(i).setText(day.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            day = day.plusDays(1);
        }
    }

    private ObservableList<Client> getSubClients(LocalDate Day) {
        ObservableList<Client> subClients = FXCollections.observableArrayList();
        for (Client client : clients) {
            if (client.getDate().toLocalDate().equals(Day)) {
                subClients.add(client);
            }
        }
        return subClients;
    }

    void showWrongDateAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Alert");
        alert.setHeaderText("Wrong date");
        alert.setContentText("Please insert a correct date, you put a date that doesn't exist!");
        alert.showAndWait();
    }

    ObservableList<Client> getClientData() {
        ObservableList<Client> clients = FXCollections.observableArrayList();
        try {
            clients.add(new Client("Christian", 2024, 4, 24, 15, 5));
            clients.add(new Client("Alfonso", 2024, 4, 24, 16, 15));
            clients.add(new Client("Giovanni", 2024, 4, 24, 16, 45));
            clients.add(new Client("Luca", 2024, 4, 24, 14, 0));
        } catch (IllegalArgumentException e) {
            showWrongDateAlert();
        }
        return clients;
    }

    @FXML
    public void handleNewCustomer() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("newcustomer-view.fxml"));
            DialogPane view = loader.load();
            NewCustomerController controller = loader.getController();

            //controller.setClient(new Client("Name", 20);

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("New Customer");
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.setDialogPane(view);

            Optional<ButtonType> clickedButton = dialog.showAndWait();
            if (clickedButton.orElse(ButtonType.CANCEL) == ButtonType.OK) {
                addUsualClient(controller.getUsualClient());
            }

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

            //controller.setClient(new Client("Name", 20);

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("New Appointment");
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.setDialogPane(view);

            Optional<ButtonType> clickedButton = dialog.showAndWait();
            if (clickedButton.orElse(ButtonType.CANCEL) == ButtonType.OK) {
                Client guest = controller.getClient();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleRemoveAppointment() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("removenewcustomer-view.fxml"));
            DialogPane view = loader.load();
            RemoveAppointmentController controller = loader.getController();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Remove Appointment");
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.setDialogPane(view);

            Optional<ButtonType> clickedButton = dialog.showAndWait();
            if (clickedButton.orElse(ButtonType.CANCEL) == ButtonType.OK) {
                removeUsualClient(controller.getClient());
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
