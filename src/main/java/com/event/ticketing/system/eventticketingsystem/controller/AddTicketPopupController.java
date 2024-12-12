package com.event.ticketing.system.eventticketingsystem.controller;

import com.event.ticketing.system.eventticketingsystem.model.Ticket;
import com.event.ticketing.system.eventticketingsystem.model.TicketPool;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.function.Consumer;

public class AddTicketPopupController {

    @FXML
    private TextField ticketCountField;

    private TicketPool ticketPool;
    private Consumer<String> logger;
    private String eventName;

    public void initialize(TicketPool ticketPool, String eventName, Consumer<String> logger) {
        this.ticketPool = ticketPool;
        this.eventName = eventName;
        this.logger = logger;
    }

    @FXML
    public void handleAddButton() {
        try {
            int ticketCount = Integer.parseInt(ticketCountField.getText());
            for (int i = 0; i < ticketCount; i++) {
                if (ticketPool.getCurrentPoolSize() >= ticketPool.getMaxCapacity()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Max Capacity Reached");
                    alert.setContentText("The ticket pool has reached its maximum capacity.");
                    alert.showAndWait();
                    return;
                }

                ticketPool.addTicket(new Ticket());
            }
            logger.accept("Added " + ticketCount + " tickets to event: " + eventName);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setContentText("Please enter a valid number.");
            alert.showAndWait();
        }
    }
}
