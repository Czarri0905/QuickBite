package main.java.com.quickbite.quickbite.model;
import java.sql.Date;

public class TransactionRecord {
    private String transactionNo;
    private Date dateTime;
    private double total;

    public TransactionRecord(String transactionNo, Date dateTime, double total) {
        this.transactionNo = transactionNo;
        this.dateTime = dateTime;
        this.total = total;
    }

    public String generateReceipt() {
        // Implement logic to generate a receipt for the transaction
        return "Transaction No: " + transactionNo + ", Date: " + dateTime + ", Total: " + total;
    }
}
