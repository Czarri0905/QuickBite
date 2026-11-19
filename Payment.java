import java.sql.Date;

public abstract class Payment {
    private int paymentId;
    private double amount;
    private Date paymentDate;

    public abstract boolean process(Order order);
    
    public abstract String getReceiptLine(Order order);
}
