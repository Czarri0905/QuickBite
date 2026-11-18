import java.sql.Date;

public abstract class Payment {
    private int paymentId;
    private double amount;
    private Date paymentDate;

    public abstract boolean process();
    
    public abstract String getReceiptLine();
}
