package com.bignardi.barbermanager.controller;

import com.bignardi.barbermanager.model.Client;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Modality;

import java.io.IOException;
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
    private TableColumn<Client, Integer> firstDayHourColumn;
    @FXML
    private TableColumn<Client, String> secondDayColumn;
    @FXML
    private TableColumn<Client, String> thirdDayColumn;
    @FXML
    private TableColumn<Client, String> fourthDayColumn;

    @FXML
    public void initialize() {


    }

    ObservableList<Cl>

    private void showClientTable(Client client){

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

        }catch (IOException e){
            e.printStackTrace();
        }

    }


}
