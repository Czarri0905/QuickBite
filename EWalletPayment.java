public class EWalletPayment extends Payment {
    private String provider;
    private String referenceNo;

    @Override
    public boolean process(Order order) {
        // Implement e-wallet payment processing logic here
        // For example, validate the provider and reference number, and check if the payment is successful
        return true; // Return true if payment is successful, false otherwise
    }
    @Override
    public String getReceiptLine(Order order) {
        // Implement logic to generate a receipt line for the e-wallet payment
        return "E-Wallet Payment - Provider: " + provider + ", Reference No: " + referenceNo;
    }
}
