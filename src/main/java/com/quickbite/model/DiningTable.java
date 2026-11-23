package com.quickbite.model;
import java.sql.Date;

import com.quickbite.model.enums.TableStatus;

public class DiningTable {
    private int tableNo;
    private int capacity;
    private TableStatus status;
    private Date expectedFreeTime;

    public DiningTable(int tableNo, int capacity) {
        this.tableNo = tableNo;
        this.capacity = capacity;
        this.status = TableStatus.AVAILABLE;
    }

    public void reserve() {
        status = TableStatus.RESERVED;
    }

    public void occupy() {
        status = TableStatus.UNAVAILABLE;
    }

    public void release() {
        status = TableStatus.AVAILABLE;
    }

    public int getTableNo() {
        return tableNo;
    }

    public boolean isAvailable(int partySize) {
        return status == TableStatus.AVAILABLE && capacity >= partySize;
    }
}
