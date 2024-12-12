package com.event.ticketing.system.eventticketingsystem.model;

import java.util.Map;
import java.util.function.Consumer;

public class Vendor implements Runnable {
    private final Map<String, TicketPool> eventTicketPools;
    private final String eventName;
    private final Consumer<String> logger;
    private final int ticketReleaseRate;

    public Vendor(Map<String, TicketPool> eventTicketPools, String eventName, Consumer<String> logger, int ticketReleaseRate) {
        this.eventTicketPools = eventTicketPools;
        this.eventName = eventName;
        this.logger = logger;
        this.ticketReleaseRate = ticketReleaseRate;
    }

    @Override
    public void run() {
        TicketPool ticketPool = eventTicketPools.get(eventName);
        if (ticketPool == null) {
            logger.accept("Error: Event \"" + eventName + "\" does not exist.");
            return;
        }

        while (!ticketPool.isFinished()) {
            try {
                for (int i = 0; i < ticketReleaseRate; i++) {
                    if (ticketPool.isFinished()) break;

                    Ticket ticket = new Ticket();
                    ticketPool.addTicket(ticket);
                }
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.accept("Vendor thread interrupted.");
                break;
            }
        }
        logger.accept("Vendor finished adding tickets for \"" + eventName + "\".");
    }
}
