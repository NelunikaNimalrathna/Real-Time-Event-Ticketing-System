package com.event.ticketing.system.eventticketingsystem.controller;

import com.event.ticketing.system.eventticketingsystem.model.Ticket;
import com.event.ticketing.system.eventticketingsystem.model.TicketPool;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.function.Consumer;

public class BuyTicketPopupController {

    @FXML
    private TextField ticketCountField;


    private TicketPool ticketPool;
    private String eventName;
    private Consumer<String> logger;


    public void initialize(TicketPool ticketPool, String eventName, Consumer<String> logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger cannot be null");
        }

        this.ticketPool = ticketPool;
        this.eventName = eventName;
        this.logger = logger;

        logger.accept("Initialized BuyTicketPopupController for event: " + eventName);
    }



    @FXML
    public void handleBuyButton() {
        try {

            int ticketCount = Integer.parseInt(ticketCountField.getText());

            if (ticketCount <= 0) {
                showAlert("Invalid Input", "Please enter a positive number of tickets.");
                return;
            }

            synchronized (ticketPool) {
                int availableTickets = ticketPool.getCurrentPoolSize();

                if (availableTickets >= ticketCount) {
                    for (int i = 0; i < ticketCount; i++) {
                        Ticket ticket = ticketPool.removeTicket();
                        if (ticket != null) {
                            logger.accept("Ticket purchased successfully: " + ticket.getId());
                        }
                    }
                    logger.accept(ticketCount + " tickets purchased for event: " + eventName);
                    showAlert("Success", ticketCount + " tickets purchased successfully for " + eventName + ".");
                } else {
                    showAlert("Not Enough Tickets Available", "Only " + availableTickets + " tickets left for this event.");
                }
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter a valid number.");
        } catch (Exception e) {
            logger.accept("An error occurred while purchasing tickets: " + e.getMessage());
            showAlert("Error", "An unexpected error occurred. Please try again.");
        }
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
