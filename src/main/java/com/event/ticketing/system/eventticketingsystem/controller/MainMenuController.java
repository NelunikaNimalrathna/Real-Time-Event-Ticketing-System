package com.event.ticketing.system.eventticketingsystem.controller;

import com.event.ticketing.system.eventticketingsystem.MainApplication;
import com.event.ticketing.system.eventticketingsystem.model.TicketPool;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.Map;
import java.util.function.Consumer;

public class MainMenuController {

    @FXML
    private Button addTicketButton;

    @FXML
    private Button buyTicketButton;

    private Map<String, TicketPool> eventTicketPools;
    private Consumer<String> logger;

    public void initialize(Map<String, TicketPool> eventTicketPools, Consumer<String> logger) {
        this.eventTicketPools = eventTicketPools;
        this.logger = logger;

        if (this.logger == null) {
            System.err.println("Logger is null in MainMenuController.initialize");
        } else {
            System.out.println("Logger successfully initialized in MainMenuController.");
        }
    }


    @FXML
    public void handleAddTicketButton() {
        try {
            Stage stage = (Stage) addTicketButton.getScene().getWindow();
            Map<String, TicketPool> eventTicketPools = SystemManager.getEventTicketPools();
            Consumer<String> logger = message -> System.out.println("Log: " + message);
            MainApplication.navigateToEventScreen(stage, eventTicketPools, logger);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void handleBuyTicketButton() {
        try {
            Stage stage = (Stage) buyTicketButton.getScene().getWindow();
            Map<String, TicketPool> eventTicketPools = SystemManager.getEventTicketPools();

            if (logger == null) {
                System.err.println("Logger in handleBuyTicketButton is null!");
            } else {
                System.out.println("Logger in handleBuyTicketButton is valid.");
            }

            MainApplication.navigateToBuyTicketScreen(stage, eventTicketPools, logger);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
