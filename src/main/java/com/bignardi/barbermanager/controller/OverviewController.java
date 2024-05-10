package com.bignardi.barbermanager.controller;

import com.bignardi.barbermanager.model.Client;
import com.bignardi.barbermanager.model.DayDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;

import java.io.IOException;

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

    @FXML
    public void initialize() {
        firstDayNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        firstDayHourColumn.setCellValueFactory(new PropertyValueFactory<>("stringHourMin"));
        firstDayTable.setItems(getClientData());
    }

    ObservableList<Client> getClientData() {
        ObservableList<Client> clients = FXCollections.observableArrayList();
        clients.add(new Client("Christian", 2024, 4, 24, 15, 5));
        clients.add(new Client("Alfonso", 2024, 4,24, 16, 15));
        clients.add(new Client("Giovanni", 2024, 4, 24, 16,45));
        clients.add(new Client("Luca", 2024, 4, 24, 14,0));
        // fai sort in base all'ora
        return clients;
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

            dialog.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
