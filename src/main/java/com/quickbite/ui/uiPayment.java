package com.quickbite.ui;

import java.awt.*;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.quickbite.model.MenuItem;
import com.quickbite.model.Order;
import com.quickbite.model.OrderItem;

public class uiPayment extends JFrame {

    private final String orderType;
    private final Order order;
    private final Map<MenuItem, OrderItem> orderItems;
    private final int estimatedMinutes;

    private String paymentMethod = "Cashless (GCash / Card)";
    private JButton cashlessBtn;
    private JButton counterBtn;
    private JLabel methodNoteLabel;

    public uiPayment(String orderType, Order order, Map<MenuItem, OrderItem> orderItems, int estimatedMinutes) {
        super("QuickBite Payment");
        this.orderType = orderType;
        this.order = order;
        this.orderItems = orderItems;
        this.estimatedMinutes = estimatedMinutes;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 650);
        setMinimumSize(new Dimension(700, 550));
        setLocationRelativeTo(null);

        JPanel page = new JPanel(new BorderLayout(20, 20));
        page.setBackground(Design.BACKGROUND);
        page.setBorder(new EmptyBorder(25, 30, 25, 30));

        page.add(buildHeader(), BorderLayout.NORTH);
        page.add(buildBody(), BorderLayout.CENTER);

        setContentPane(page);
        updateMethodSelection();
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);

        JLabel logo = createLabel("QUICKBITE", 18, Design.ORANGE);
        JLabel title = createLabel("Checkout & Payment", 27, Design.NAVY);
        JLabel subtitle = createSmallLabel(orderType + " Order  •  Estimated Prep Time: ~" + estimatedMinutes + " mins");

        titlePanel.add(logo);
        titlePanel.add(Box.createVerticalStrut(8));
        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(4));
        titlePanel.add(subtitle);

        JButton back = createSolidButton("Back to Menu", Color.WHITE, Design.NAVY, true);
        back.addActionListener(e -> {
            new uiMenu(orderType).setVisible(true);
            dispose();
        });

        header.add(titlePanel, BorderLayout.CENTER);
        header.add(back, BorderLayout.EAST);
        return header;
    }

    private JPanel buildBody() {
        JPanel body = new JPanel(new GridLayout(1, 2, 20, 0));
        body.setOpaque(false);

        body.add(buildReceiptCard());
        body.add(buildPaymentCard());
        return body;
    }

    private JPanel buildReceiptCard() {
        JPanel card = new JPanel(new BorderLayout(0, 15));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Design.LINE),
                new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel heading = createLabel("Order Summary", 20, Design.NAVY);

        JPanel list = new JPanel();
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        list.setBackground(Color.WHITE);

        for (Map.Entry<MenuItem, OrderItem> entry : orderItems.entrySet()) {
            MenuItem item = entry.getKey();
            OrderItem oi = entry.getValue();

            JPanel row = new JPanel(new BorderLayout());
            row.setOpaque(false);
            row.setBorder(new EmptyBorder(8, 0, 8, 0));

            JLabel itemLabel = createLabel(oi.getQuantity() + "x  " + item.getName(), 14, Design.NAVY);
            JLabel priceLabel = createLabel(
                    uiMenu.formatPrice(item.getPrice() * oi.getQuantity()), 14, Design.NAVY);

            row.add(itemLabel, BorderLayout.WEST);
            row.add(priceLabel, BorderLayout.EAST);

            list.add(row);
            list.add(new JSeparator());
        }

        JScrollPane scroll = new JScrollPane(list);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);

        JPanel footer = new JPanel(new BorderLayout(0, 8));
        footer.setOpaque(false);
        footer.setBorder(new EmptyBorder(10, 0, 0, 0));

        JLabel timeRow = createLabel("Estimated Prep Time: ~" + estimatedMinutes + " mins", 14, Design.ORANGE);
        JLabel totalRow = createLabel("Total Due: " + uiMenu.formatPrice(order.computeTotal()), 22, Design.NAVY);

        footer.add(timeRow, BorderLayout.NORTH);
        footer.add(totalRow, BorderLayout.SOUTH);

        card.add(heading, BorderLayout.NORTH);
        card.add(scroll, BorderLayout.CENTER);
        card.add(footer, BorderLayout.SOUTH);
        return card;
    }

    private JPanel buildPaymentCard() {
        JPanel card = new JPanel(new BorderLayout(0, 15));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Design.LINE),
                new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel heading = createLabel("Select Payment Method", 20, Design.NAVY);

        JPanel options = new JPanel();
        options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));
        options.setOpaque(false);

        cashlessBtn = createSolidButton("Cashless (GCash / Maya / Card)", Design.NAVY, Color.WHITE, false);
        cashlessBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        cashlessBtn.addActionListener(e -> {
            paymentMethod = "Cashless (GCash / Card)";
            updateMethodSelection();
        });

        counterBtn = createSolidButton("Pay at Counter (Cash)", Color.WHITE, Design.NAVY, true);
        counterBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        counterBtn.addActionListener(e -> {
            paymentMethod = "Pay at Counter (Cash)";
            updateMethodSelection();
        });

        methodNoteLabel = createSmallLabel("");
        methodNoteLabel.setBorder(new EmptyBorder(12, 4, 0, 4));

        options.add(Box.createVerticalStrut(10));
        options.add(cashlessBtn);
        options.add(Box.createVerticalStrut(12));
        options.add(counterBtn);
        options.add(methodNoteLabel);

        JButton confirmBtn = createSolidButton("Confirm & Place Order", Design.ORANGE, Color.WHITE, false);
        confirmBtn.setPreferredSize(new Dimension(240, 50));
        confirmBtn.addActionListener(e -> completeOrder());

        card.add(heading, BorderLayout.NORTH);
        card.add(options, BorderLayout.CENTER);
        card.add(confirmBtn, BorderLayout.SOUTH);
        return card;
    }

    private void updateMethodSelection() {
        boolean isCashless = paymentMethod.startsWith("Cashless");

        cashlessBtn.setBackground(isCashless ? Design.NAVY : Color.WHITE);
        cashlessBtn.setForeground(isCashless ? Color.WHITE : Design.NAVY);

        counterBtn.setBackground(!isCashless ? Design.NAVY : Color.WHITE);
        counterBtn.setForeground(!isCashless ? Color.WHITE : Design.NAVY);

        methodNoteLabel.setText(isCashless
                ? "<html>Scan the QR code or tap your card on the kiosk terminal after confirming.</html>"
                : "<html>Please present your queue slip and pay at the counter to begin preparation.</html>");

        cashlessBtn.repaint();
        counterBtn.repaint();
    }

    private void completeOrder() {
        String receipt = "Order Confirmed!\n\n"
                + "Order Type: " + orderType + "\n"
                + "Payment Method: " + paymentMethod + "\n"
                + "Total Paid: " + uiMenu.formatPrice(order.computeTotal()) + "\n"
                + "Estimated Ready Time: ~" + estimatedMinutes + " mins\n\n"
                + "Thank you for ordering with QuickBite!";

        JOptionPane.showMessageDialog(
                this,
                receipt,
                "Order Complete",
                JOptionPane.INFORMATION_MESSAGE
        );

        new Login().showUi();
        dispose();
    }

    private JLabel createLabel(String text, int size, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(Design.font(Font.BOLD, size));
        label.setForeground(color);
        return label;
    }

    private JLabel createSmallLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(Design.font(Font.PLAIN, 13));
        label.setForeground(Design.MUTED);
        return label;
    }

    private JButton createSolidButton(String text, Color bg, Color fg, boolean withBorder) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color currentBg = getBackground();
                if (getModel().isPressed()) {
                    currentBg = currentBg.darker();
                } else if (getModel().isRollover() && currentBg.equals(Design.ORANGE)) {
                    currentBg = Design.ORANGE_HOVER;
                }

                g2.setColor(currentBg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);

                if (withBorder || currentBg.equals(Color.WHITE)) {
                    g2.setColor(Design.LINE);
                    g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
                }

                g2.dispose();
                super.paintComponent(g);
            }
        };

        button.setFont(Design.font(Font.BOLD, 14));
        button.setBackground(bg);
        button.setForeground(fg);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setRolloverEnabled(true);
        button.setBorder(new EmptyBorder(10, 16, 10, 16));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }
}
