package com.bignardi.barbermanager.controller;

import com.bignardi.barbermanager.model.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;

import java.io.CharArrayReader;
import java.io.IOException;
import java.time.LocalDate;
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
    private TableColumn<Client, String> secondDayColumn;
    @FXML
    private TableColumn<Client, String> secondDayHourColumn;
    @FXML
    private TableColumn<Client, String> thirdDayColumn;
    @FXML
    private TableColumn<Client, String> thirdDayHourColumn;
    @FXML
    private TableColumn<Client, String> fourthDayColumn;
    @FXML
    private TableColumn<Client, String> fourthDayHourColumn;
    private ArrayList<Client> clients;

    @FXML
    public void initialize() {
        clients = new ArrayList<>();
        firstDayNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        firstDayHourColumn.setCellValueFactory(new PropertyValueFactory<>("stringHourMin"));

        showTables(0);
    }

    public void fillClientsExample(){

    }

    public void addClient(Client client) {
        clients.add(client);
        clients.sort(Comparator.comparing(Client::getDate));
    }

    public void removeClient(Client client) {
        clients.remove(client);
    }

    public void showTables(int offsetFirstDay) {
        ObservableList<Client> subClients;
        LocalDate day = clients.getFirst().getDate().toLocalDate();
        day.plusDays(offsetFirstDay);
        ArrayList<TableView<Client>> tableViews = new ArrayList<>(4);
        tableViews.add(firstDayTable);
        tableViews.add(secondDayTable);
        tableViews.add(thirdDayTable);
        tableViews.add(fourthDayTable);

        for (int i = 0; i < 4; i++) {
            subClients = getSubClients(day);
            tableViews.get(i).setItems(subClients);
            day.plusDays(1);
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
                firstDayTable.getItems().add(controller.getClient());
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
                System.out.println(guest.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
