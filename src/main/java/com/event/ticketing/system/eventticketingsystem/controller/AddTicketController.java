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

public class AddTicketController {

    private Map<String, TicketPool> eventTicketPools;
    private Consumer<String> logger;

    @FXML
    private Button backButton;

    public void initialize(Map<String, TicketPool> eventTicketPools, Consumer<String> logger) {
        this.eventTicketPools = eventTicketPools;
        this.logger = logger;

        if (eventTicketPools == null) {
            logger.accept("Error: eventTicketPools is null.");
        } else {
            logger.accept("EventController initialized with events: " + eventTicketPools.keySet());
        }
    }

    @FXML
    public void handleAddTicketsForEvent1() {
        showAddTicketPopup("SOSL Christmas Concert 2024");
    }

    @FXML
    public void handleAddTicketsForEvent2() {
        showAddTicketPopup("Ayaskanthaya");
    }

    @FXML
    public void handleAddTicketsForEvent3() {
        showAddTicketPopup("Ada Hamu Wemuda Api VOL 5.0");
    }

    private void showAddTicketPopup(String eventName) {
        try {
            TicketPool ticketPool = eventTicketPools.get(eventName);
            if (ticketPool == null) {
                logger.accept("Error: Event \"" + eventName + "\" does not exist.");
                return;
            }

            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("/com/event/ticketing/system/eventticketingsystem/add-ticket-popup.fxml"));
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Add Tickets - " + eventName);
            popupStage.setScene(new Scene(loader.load()));

            AddTicketPopupController popupController = loader.getController();
            popupController.initialize(ticketPool, eventName, logger);

            popupStage.showAndWait();
        } catch (Exception e) {
            logger.accept("Failed to load the Add Ticket popup for event: " + eventName);
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBackButton() {
        try {
            Stage stage = (Stage) backButton.getScene().getWindow();
            MainApplication.navigateToMainMenu(stage);
        } catch (Exception e) {
            logger.accept("Failed to navigate to the main menu.");
            e.printStackTrace();
        }
    }
}
