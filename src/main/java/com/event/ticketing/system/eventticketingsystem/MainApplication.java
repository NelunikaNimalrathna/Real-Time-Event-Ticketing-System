package com.event.ticketing.system.eventticketingsystem;

import com.event.ticketing.system.eventticketingsystem.controller.BuyTicketController;
import com.event.ticketing.system.eventticketingsystem.controller.AddTicketController;
import com.event.ticketing.system.eventticketingsystem.controller.MainMenuController;
import com.event.ticketing.system.eventticketingsystem.controller.SystemManager;
import com.event.ticketing.system.eventticketingsystem.model.TicketPool;
import com.event.ticketing.system.eventticketingsystem.utils.Configuration;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class MainApplication extends Application {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 800;
    private static SystemManager systemManager;
    private static Consumer<String> logger;



    @Override
    public void start(Stage primaryStage) {
        try {
            // Initialize logger
            logger = System.out::println;

            // Create event configurations
            Map<String, Configuration> eventConfigurations = new HashMap<>();
            eventConfigurations.put("SOSL Christmas Concert 2024", new Configuration(100, 500, 10, 50)); // Add Event 1
            eventConfigurations.put("Ayaskanthaya", new Configuration(80, 400, 8, 40)); // Add Event 2
            eventConfigurations.put("Ada Hamu Wemuda Api VOL 5.0", new Configuration(120, 600, 12, 60)); // Add Event 3

            // Initialize the SystemManager
            systemManager = new SystemManager(eventConfigurations, logger);

            // Navigate to the login screen
            navigateToLoginScreen(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
            logger.accept("Failed to start the application.");
        }
    }

    public static void navigateToLoginScreen(Stage stage) throws Exception {
        loadScreen(stage, "/com/event/ticketing/system/eventticketingsystem/login-view.fxml", "Login - Event Ticketing System");
    }

    public static void navigateToMainMenu(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("/com/event/ticketing/system/eventticketingsystem/view-main-menu.fxml"));
        Scene scene = new Scene(loader.load(), WIDTH, HEIGHT);

        MainMenuController controller = loader.getController();
        controller.initialize(SystemManager.getEventTicketPools(), logger); // Pass logger here

        stage.setScene(scene);
        stage.setTitle("Main Menu - Event Ticketing System");
        stage.show();
    }


    public static void navigateToEventScreen(Stage stage, Map<String, TicketPool> eventTicketPools, Consumer<String> logger) throws Exception {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("/com/event/ticketing/system/eventticketingsystem/add-ticket-screen.fxml"));
        Scene scene = new Scene(loader.load(), WIDTH, HEIGHT);

        // Initialize EventController with event pools and logger
        AddTicketController controller = loader.getController();
        controller.initialize(systemManager.getEventTicketPools(), logger);

        stage.setTitle("Event Ticketing System - Events");
        stage.setScene(scene);
        stage.show();
    }

    public static void navigateToBuyTicketScreen(Stage stage, Map<String, TicketPool> eventTicketPools, Consumer<String> logger) throws Exception {
        if (logger == null) {
            System.err.println("Logger is null in navigateToBuyTicketScreen!");
            throw new IllegalArgumentException("Logger cannot be null when navigating to BuyTicketScreen.");
        } else {
            System.out.println("Logger is valid in navigateToBuyTicketScreen.");
        }

        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("/com/event/ticketing/system/eventticketingsystem/buy-ticket-screen.fxml"));
        Scene scene = new Scene(loader.load(), WIDTH, HEIGHT);

        BuyTicketController controller = loader.getController();
        controller.initialize(eventTicketPools, logger);

        stage.setScene(scene);
        stage.setTitle("Buy Tickets - Event Ticketing System");
        stage.show();
    }




    private static void loadScreen(Stage stage, String resourcePath, String title) throws Exception {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(resourcePath));
        if (loader.getLocation() == null) {
            throw new IllegalArgumentException("FXML resource not found: " + resourcePath);
        }
        Scene scene = new Scene(loader.load(), WIDTH, HEIGHT);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}