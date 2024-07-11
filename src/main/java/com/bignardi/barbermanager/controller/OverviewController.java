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
import java.util.Date;
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

    @FXML
    public void initialize() {
        firstDayNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        firstDayHourColumn.setCellValueFactory(new PropertyValueFactory<>("stringHourMin"));
        firstDayTable.setItems(getClientData());
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
        }catch (IllegalArgumentException e){
            showWrongDateAlert();
        }
        clients.sort((a1, a2) -> a1.getDate().compareTo(a2.getDate()));
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

            dialog.showAndWait();

            Optional<ButtonType> clickedButton = dialog.showAndWait();
            if (clickedButton.orElse(ButtonType.CANCEL) == ButtonType.OK) {
                firstDayTable.getItems().add(controller.getClient());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
