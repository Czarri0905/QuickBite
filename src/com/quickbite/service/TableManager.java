package com.quickbite.service;
import java.util.ArrayList;
import java.util.List;

import com.quickbite.model.DiningTable;
import com.quickbite.model.Order;
import com.quickbite.model.enums.TableStatus;

public class TableManager {
    private final List<DiningTable> tables;

    public TableManager() {
        tables = new ArrayList<>();
    }

    public List<DiningTable> getAvailableTables(int partySize) {
        if(partySize <= 0) 
            throw new IllegalArgumentException("Party size must be greater than zero.");

        List<DiningTable> availableTables = new ArrayList<>();
        for (DiningTable table : tables) {
            if(table.isAvailable(partySize)) {
                availableTables.add(table);
            }
        }
        return availableTables;
    }

    public void addTable(DiningTable table) {
        if (table != null)
            tables.add(table);
    }

    public void assignTable(Order order, DiningTable table, int partySize) {
        List<DiningTable> availableTables = getAvailableTables(partySize);

        if (order == null || table == null)
            throw new IllegalArgumentException("Order and table cannot be null.");

        if(partySize <= 0)
            throw new IllegalArgumentException("Party size must be greater than zero.");

        if(partySize > table.getCapacity()) 
            throw new IllegalArgumentException("Table cannot accommodate the party size.");

        if(!availableTables.contains(table)) 
            throw new IllegalArgumentException("Table is not available for the given party size.");

        order.setTable(table);
        table.occupy();
        
    }

    public void estimateTableWait() {
        //Implement method here to calculate waiting time
    }

    public void setTableStatus(DiningTable table, TableStatus status) {
        //Implement method here to set table status
    }
}
