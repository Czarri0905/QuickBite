import java.sql.Date;

public class Payment {
    private int paymentId;
    private double amount;
    private Date paymentDate;

    public boolean process() {
        // Implement payment processing logic here
        return true; // Return true if payment is successful, false otherwise
    }

    public String getReceiptLine() {
        // Implement logic to generate a receipt line for the payment
        return "Payment ID: " + paymentId + ", Amount: " + amount + ", Date: " + paymentDate;
    }
}
