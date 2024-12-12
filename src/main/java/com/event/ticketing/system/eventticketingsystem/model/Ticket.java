package com.event.ticketing.system.eventticketingsystem.model;

public class Ticket {
    private static int counter = 1;
    private final int id;

    public Ticket() {
        this.id = counter++;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Ticket ID: " + id;
    }
}
