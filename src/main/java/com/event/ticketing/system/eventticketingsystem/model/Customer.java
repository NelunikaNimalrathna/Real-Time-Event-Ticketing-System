package com.event.ticketing.system.eventticketingsystem.model;

import java.util.Map;
import java.util.function.Consumer;

public class Customer implements Runnable {
    private final Map<String, TicketPool> eventTicketPools;
    private final Consumer<String> logger;
    private final int customerRetrievalRate;
    private final String eventName;

    public Customer(Map<String, TicketPool> eventTicketPools, String eventName, Consumer<String> logger, int customerRetrievalRate) {
        this.eventTicketPools = eventTicketPools;
        this.eventName = eventName;
        this.logger = logger;
        this.customerRetrievalRate = customerRetrievalRate;
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
                for (int i = 0; i < customerRetrievalRate; i++) {
                    Ticket ticket;
                    synchronized (ticketPool) {
                        while (ticketPool.getCurrentPoolSize() == 0 && !ticketPool.isFinished()) {
                            logger.accept("Customer is waiting to buy tickets for \"" + eventName + "\". Pool is empty.");
                            ticketPool.wait();
                        }

                        ticket = ticketPool.removeTicket();
                    }

                    if (ticket != null) {
                        logger.accept("Customer purchased ticket ID: " + ticket.getId() +
                                " for \"" + eventName + "\". Remaining in pool: " + ticketPool.getCurrentPoolSize());
                    }
                }
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.accept("Customer thread interrupted for \"" + eventName + "\".");
                break;
            }
        }
        logger.accept("Customer finished ticket purchase for \"" + eventName + "\".");
    }
}
