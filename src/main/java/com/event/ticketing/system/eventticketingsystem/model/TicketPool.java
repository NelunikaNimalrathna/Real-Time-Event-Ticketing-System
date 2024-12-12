package com.event.ticketing.system.eventticketingsystem.model;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

public class TicketPool {
    private final Queue<Ticket> ticketQueue = new LinkedList<>();
    private final int maxCapacity;
    private int totalTicketsAdded = 0;
    private int totalTicketsRemoved = 0;
    private int totalTickets;
    private final Consumer<String> logger;

    public TicketPool(int maxCapacity, int totalTickets, Consumer<String> logger) {
        this.maxCapacity = maxCapacity;
        this.totalTickets = totalTickets;
        this.logger = logger;
    }

    public synchronized void addTicket(Ticket ticket) {
        if (ticketQueue.size() < maxCapacity) {
            ticketQueue.add(ticket);
            totalTicketsAdded++;
            logger.accept("Ticket added to pool. Current size: " + ticketQueue.size());
            notifyAll();
        } else {
            logger.accept("Cannot add ticket. Pool at max capacity.");
        }
    }


    public synchronized int getCurrentPoolSize() {
        return ticketQueue.size();
    }

    public synchronized int getMaxCapacity() {
        return maxCapacity;
    }

    public synchronized boolean isFinished() {
        return totalTicketsAdded >= totalTickets && totalTicketsRemoved >= totalTickets;
    }

    public synchronized Ticket removeTicket() {
        if (!ticketQueue.isEmpty()) {
            Ticket ticket = ticketQueue.poll();
            totalTicketsRemoved++;
            logger.accept("Ticket removed from pool. Remaining size: " + ticketQueue.size());
            notifyAll();
            return ticket;
        } else {
            logger.accept("No tickets available in the pool.");
            return null;
        }
    }

}
