module com.event.ticketing.system.eventticketingsystem {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.event.ticketing.system.eventticketingsystem.controller to javafx.fxml;
    exports com.event.ticketing.system.eventticketingsystem;
}
