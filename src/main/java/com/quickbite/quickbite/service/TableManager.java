package main.java.com.quickbite.quickbite.service;
import java.util.ArrayList;
import java.util.List;

import main.java.com.quickbite.quickbite.model.DiningTable;
import main.java.com.quickbite.quickbite.model.Order;
import main.java.com.quickbite.quickbite.model.enums.TableStatus;

public class TableManager {
    private final List<DiningTable> tables;

    public TableManager() {
        tables = new ArrayList<>();
    }

    public List<DiningTable> getAvailableTables(int partySize) {
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

    public void assignTable(Order order, DiningTable table) {
        //Implement method here to assign tables based on available tables
    }

    public void estimateTableWait() {
        //Implement method here to calculate waiting time
    }

    public void setTableStatus(DiningTable table, TableStatus status) {
        //Implement method here to set table status
    }
}
