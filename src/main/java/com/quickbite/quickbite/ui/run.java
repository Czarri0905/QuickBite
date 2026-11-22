package main.java.com.quickbite.quickbite.ui;
public class run {
    public static void main(String[] args) {
        // Set the look and feel to the system default
        try {
            javax.swing.UIManager.setLookAndFeel(
                javax.swing.UIManager.getSystemLookAndFeelClassName()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create and show the login window
        javax.swing.SwingUtilities.invokeLater(() -> {
            Login loginWindow = new Login();
            loginWindow.showUi();
        });
    }
    
}
