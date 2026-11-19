import javax.swing.*;
import java.awt.*;



public class Interface extends JFrame {
    private static final Color NAVY = new Color(31, 48, 69);
    private static final Color ORANGE = new Color(239, 133, 59);

    public Interface() {
        super("QuickBite");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 900);
        setMinimumSize(new Dimension(420, 700));
        setLocationRelativeTo(null);

        JPanel page = new JPanel(new BorderLayout(24, 24));
        page.setBackground(Color.WHITE);
        page.setBorder(BorderFactory.createEmptyBorder(48, 44, 28, 44));

        JPanel welcome = new JPanel(new GridLayout(0, 1, 0, 12));
        welcome.setOpaque(false);
        JLabel brand = new JLabel("QuickBite", SwingConstants.CENTER);
        brand.setFont(new Font("SansSerif", Font.BOLD, 30));
        brand.setForeground(ORANGE);
        JLabel title = new JLabel("Welcome!", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 38));
        title.setForeground(NAVY);
        JLabel subtitle = new JLabel("How would you like your order?", SwingConstants.CENTER);
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 20));
        subtitle.setForeground(NAVY);
        welcome.add(brand);
        welcome.add(title);
        welcome.add(subtitle);

        JPanel choices = new JPanel(new GridLayout(2, 1, 0, 20));
        choices.setOpaque(false);
        javax.swing.JButton dineIn = createChoiceButton("Dine In", "Enjoy your meal here");
        javax.swing.JButton takeOut = createChoiceButton("Take Out", "Take your order to go");
        dineIn.addActionListener(event -> openMenu("Dine In"));
        takeOut.addActionListener(event -> openMenu("Take Out"));
        choices.add(dineIn);
        choices.add(takeOut);

        JPanel footer = new JPanel(new BorderLayout(12, 0));
        footer.setOpaque(false);
        footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(225, 228, 232)));
        footer.setBorder(BorderFactory.createCompoundBorder(footer.getBorder(),
                BorderFactory.createEmptyBorder(18, 0, 0, 0)));
        JComboBox<String> language = new JComboBox<>(new String[]{"English", "Español", "Français"});
        language.setFont(new Font("SansSerif", Font.PLAIN, 16));
        language.setToolTipText("Choose a language");
        javax.swing.JButton accessibility = new javax.swing.JButton("Accessibility");
        accessibility.setFont(new Font("SansSerif", Font.BOLD, 16));
        accessibility.addActionListener(event -> {
            UIManager.put("OptionPane.messageFont", new Font("SansSerif", Font.PLAIN, 18));
            JOptionPane.showMessageDialog(this,
                    "For assistance using the kiosk, please ask a team member.",
                    "Accessibility", JOptionPane.INFORMATION_MESSAGE);
        });
        footer.add(language, BorderLayout.WEST);
        footer.add(accessibility, BorderLayout.EAST);

        page.add(welcome, BorderLayout.NORTH);
        page.add(choices, BorderLayout.CENTER);
        page.add(footer, BorderLayout.SOUTH);
        setContentPane(page);
    }

    private javax.swing.JButton createChoiceButton(String title, String detail) {
        javax.swing.JButton button = new javax.swing.JButton("<html><div style='text-align:center'>"
                + title + "<br><span style='font-size:14pt;font-weight:normal'>" + detail
                + "</span></div></html>");
        button.setFont(new Font("SansSerif", Font.BOLD, 26));
        button.setForeground(Color.WHITE);
        button.setBackground(NAVY);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(NAVY, 2, true),
                BorderFactory.createEmptyBorder(24, 16, 24, 16)));
        button.setOpaque(true);
        return button;
    }

    private void openMenu(String orderType) {
        uiMenu menu = new uiMenu();
        menu.setVisible(true);
        dispose();
    }

    public void showUi() {
        setVisible(true);
    }
}
