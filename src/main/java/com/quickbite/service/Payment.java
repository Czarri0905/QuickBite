package com.quickbite.service;
import java.sql.Date;

import com.quickbite.model.Order;

public abstract class Payment {
    private int paymentId;
    private double amount;
    private Date paymentDate;

    public abstract boolean process(Order order);
    
    public abstract String getReceiptLine(Order order);
}
