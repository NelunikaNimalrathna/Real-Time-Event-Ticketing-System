package com.event.ticketing.system.eventticketingsystem.controller;

import com.event.ticketing.system.eventticketingsystem.model.Customer;
import com.event.ticketing.system.eventticketingsystem.model.TicketPool;
import com.event.ticketing.system.eventticketingsystem.model.Vendor;
import com.event.ticketing.system.eventticketingsystem.utils.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class SystemManager {
    private static final Map<String, TicketPool> eventTicketPools = new HashMap<>();
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public void initializePools(Map<String, Configuration> eventConfigurations, Consumer<String> logger) {
        for (Map.Entry<String, Configuration> entry : eventConfigurations.entrySet()) {
            Configuration config = entry.getValue();
            eventTicketPools.put(entry.getKey(), new TicketPool(
                    config.getMaxTicketCapacity(), config.getTotalTickets(), logger));
        }
    }


    public static Map<String, TicketPool> getEventTicketPools() {
        return eventTicketPools;
    }

    public SystemManager(Map<String, Configuration> eventConfigurations, Consumer<String> logger) {
        initializePools(eventConfigurations, logger);
    }

}
