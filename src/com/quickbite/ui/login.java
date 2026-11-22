package com.quickbite.ui;
import java.awt.*;
import javax.swing.*;

/** Kiosk screen for choosing an order type. */
class Login extends JFrame {

    public Login() {
        super("QuickBite");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 900);
        setMinimumSize(new Dimension(420, 700));
        setLocationRelativeTo(null);

        // DESIGN: Shared page styling and reusable components come from Design.
        JPanel page = Design.createPage();
        page.add(buildWelcome(), BorderLayout.NORTH);
        page.add(buildChoices(), BorderLayout.CENTER);
        page.add(buildFooter(), BorderLayout.SOUTH);
        setContentPane(page);
    }

    private JPanel buildWelcome() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        panel.add(Design.createLogo());
        panel.add(Box.createVerticalStrut(12));
        panel.add(Design.createLabel("QuickBite", Font.BOLD, 24, Design.ORANGE));
        panel.add(Box.createVerticalStrut(28));
        panel.add(Design.createLabel("Welcome!", Font.BOLD, 40, Design.NAVY));
        panel.add(Box.createVerticalStrut(8));
        panel.add(Design.createLabel("How would you like your order?", Font.PLAIN, 19, Design.MUTED));
        return panel;
    }

    private JPanel buildChoices() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 0, 20));
        panel.setOpaque(false);

        JButton dineIn = Design.createCardButton(
                "Dine In", "Enjoy your meal here", Design.NAVY, Design.NAVY_HOVER);
        JButton takeOut = Design.createCardButton(
                "Take Out", "Take your order to go", Design.ORANGE, Design.ORANGE_HOVER);

        dineIn.addActionListener(event -> openMenu("Dine In"));
        takeOut.addActionListener(event -> openMenu("Take Out"));

        panel.add(dineIn);
        panel.add(takeOut);
        return panel;
    }

    private JPanel buildFooter() {
        JPanel panel = new JPanel(new BorderLayout(12, 0));
        panel.setOpaque(false);

        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, Design.LINE),
                BorderFactory.createEmptyBorder(18, 0, 0, 0)));

        JComboBox<String> language = Design.createComboBox("English", "Español", "Français");
        language.setToolTipText("Choose a language");

        JButton accessibility = Design.createPillButton("Accessibility");
        accessibility.addActionListener(event -> showAccessibilityHelp());

        panel.add(language, BorderLayout.WEST);
        panel.add(accessibility, BorderLayout.EAST);
        return panel;
    }

    private void showAccessibilityHelp() {
        UIManager.put("OptionPane.messageFont", Design.font(Font.PLAIN, 18));
        JOptionPane.showMessageDialog(this,
                "For assistance using the kiosk, please ask a team member.",
                "Accessibility", JOptionPane.INFORMATION_MESSAGE);
    }

    /** Opens the menu window and carries the selected order type into it. */
    private void openMenu(String orderType) {
        new uiMenu(orderType).setVisible(true);
        dispose();
    }

    public void showUi() {
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login().showUi());
    }
}
