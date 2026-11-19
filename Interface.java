import javax.swing.JFrame;
import javax.swing.JPanel;


public class Interface extends JFrame {
    public Interface() {
        super("QuickBite");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        add(new JPanel());
    }

    public void showUi() {
        setVisible(true);
    }
}
