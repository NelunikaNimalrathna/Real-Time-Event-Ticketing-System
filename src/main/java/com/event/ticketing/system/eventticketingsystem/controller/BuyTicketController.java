package com.event.ticketing.system.eventticketingsystem.controller;

import com.event.ticketing.system.eventticketingsystem.MainApplication;
import com.event.ticketing.system.eventticketingsystem.model.TicketPool;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Map;
import java.util.function.Consumer;

public class BuyTicketController {

    private Map<String, TicketPool> eventTicketPools;
    private Consumer<String> logger;

    @FXML
    private Button backButton;


    public void initialize(Map<String, TicketPool> eventTicketPools, Consumer<String> logger) {
        this.eventTicketPools = eventTicketPools;
        this.logger = logger;

        if (eventTicketPools == null) {
            System.err.println("Error: eventTicketPools is null");
        }
        if (logger == null) {
            System.err.println("Error: logger is null");
        }
    }

    @FXML
    public void handleBuyTicketsForEvent1() {
        openBuyTicketPopup("SOSL Christmas Concert 2024");
    }

    @FXML
    public void handleBuyTicketsForEvent2() {
        openBuyTicketPopup("Ayaskanthaya");
    }

    @FXML
    public void handleBuyTicketsForEvent3() {
        openBuyTicketPopup("Ada Hamu Wemuda Api VOL 5.0");
    }

    @FXML
    public void handleBackButton() {
        try {
            Stage stage = (Stage) backButton.getScene().getWindow();
            MainApplication.navigateToMainMenu(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openBuyTicketPopup(String eventName) {
        try {
            TicketPool ticketPool = eventTicketPools.get(eventName);
            if (ticketPool == null) {
                logger.accept("Error: Event \"" + eventName + "\" does not exist.");
                return;
            }

            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("/com/event/ticketing/system/eventticketingsystem/buy-ticket-popup.fxml"));
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Buy Tickets - " + eventName);
            popupStage.setScene(new Scene(loader.load()));

            BuyTicketPopupController popupController = loader.getController();

            if (logger == null) {
                throw new IllegalStateException("Logger is null in BuyTicketController");
            }

            popupController.initialize(ticketPool, eventName, logger);
            popupStage.showAndWait();
        } catch (Exception e) {
            if (logger != null) {
                logger.accept("Failed to load the Buy Ticket popup for event: " + eventName);
            }
            e.printStackTrace();
        }
    }



}
