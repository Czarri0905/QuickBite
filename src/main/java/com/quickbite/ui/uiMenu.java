package com.quickbite.ui;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.quickbite.model.MenuItem;
import com.quickbite.model.Order;
import com.quickbite.model.OrderItem;
import com.quickbite.service.MenuCatalog;

class uiMenu extends JFrame {

    // ORDER DATA
    private final String orderType;
    private final MenuCatalog catalog = createCatalog();
    private final Order order;
    private final Map<MenuItem, OrderItem> orderItems = new LinkedHashMap<>();
    private final Map<MenuItem, JLabel> quantityLabels = new LinkedHashMap<>();

    // UI COMPONENTS
    private JPanel categoryPanel;
    private JPanel menuGrid;
    private JPanel orderList;
    private JLabel itemCount;
    private JLabel estTimeLabel;
    private JLabel totalLabel;

    private String selectedCategory = "All";

    // CONSTRUCTOR
    uiMenu(String orderType) {
        super("QuickBite Menu");

        this.orderType = orderType;
        this.order = new Order(1, orderType);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 750);
        setMinimumSize(new Dimension(850, 600));
        setLocationRelativeTo(null);

        // DESIGN: Main application layout using shared Design palette
        JPanel page = new JPanel(new BorderLayout(20, 20));
        page.setBackground(Design.BACKGROUND);
        page.setBorder(new EmptyBorder(20, 25, 20, 25));

        page.add(buildHeader(), BorderLayout.NORTH);
        page.add(buildMainContent(), BorderLayout.CENTER);

        setContentPane(page);
        refreshCategories();
        refreshMenu();
        refreshOrder();
    }

    // HEADER
    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);

        JLabel logo = createLabel("QUICKBITE", 18, Design.ORANGE);
        JLabel title = createLabel("Choose Your Food", 27, Design.NAVY);
        JLabel subtitle = createSmallLabel(orderType + " order");

        titlePanel.add(logo);
        titlePanel.add(Box.createVerticalStrut(8));
        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(3));
        titlePanel.add(subtitle);

        JButton back = createSolidButton("Back", Color.WHITE, Design.NAVY, true);
        back.addActionListener(e -> {
            new Login().showUi();
            dispose();
        });

        header.add(titlePanel, BorderLayout.CENTER);
        header.add(back, BorderLayout.EAST);

        return header;
    }

    // MAIN CONTENT
    private JPanel buildMainContent() {
        JPanel content = new JPanel(new BorderLayout(20, 0));
        content.setOpaque(false);

        // LEFT: MENU
        JPanel menuPanel = new JPanel(new BorderLayout(0, 15));
        menuPanel.setOpaque(false);

        categoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        categoryPanel.setOpaque(false);

        menuPanel.add(categoryPanel, BorderLayout.NORTH);
        menuPanel.add(buildMenuArea(), BorderLayout.CENTER);

        // RIGHT: ORDER SUMMARY
        JPanel orderPanel = buildOrderPanel();

        content.add(menuPanel, BorderLayout.CENTER);
        content.add(orderPanel, BorderLayout.EAST);

        return content;
    }

    // CATEGORY BUTTONS
    private void refreshCategories() {
        categoryPanel.removeAll();

        String[] names = {
            "All", "Burgers", "Fries", "Drinks", "Main Dishes", "Pasta", "Desserts"
        };

        for (String name : names) {
            boolean selected = selectedCategory.equals(name);
            JButton button = createSolidButton(
                    name,
                    selected ? Design.ORANGE : Color.WHITE,
                    selected ? Color.WHITE : Design.NAVY,
                    !selected
            );

            button.addActionListener(e -> {
                selectedCategory = name;
                refreshCategories();
                refreshMenu();
            });

            categoryPanel.add(button);
        }

        categoryPanel.revalidate();
        categoryPanel.repaint();
    }

    // MENU AREA
    private JScrollPane buildMenuArea() {
        menuGrid = new JPanel(new GridLayout(0, 2, 12, 12));
        menuGrid.setBackground(Design.BACKGROUND);
        menuGrid.setBorder(new EmptyBorder(2, 2, 10, 2));

        JScrollPane scroll = new JScrollPane(menuGrid);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Design.BACKGROUND);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        return scroll;
    }

    // REFRESH MENU
    private void refreshMenu() {
        menuGrid.removeAll();
        quantityLabels.clear();

        for (MenuItem item : catalog.getItems()) {
            if (!selectedCategory.equals("All")
                    && !item.getCategory().equalsIgnoreCase(selectedCategory)) {
                continue;
            }
            menuGrid.add(buildMenuCard(item));
        }

        menuGrid.revalidate();
        menuGrid.repaint();
    }

    // MENU CARD
    private JPanel buildMenuCard(MenuItem item) {
        JPanel card = new JPanel(new BorderLayout(0, 12));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Design.LINE),
                new EmptyBorder(15, 15, 15, 15)
        ));

        // ITEM INFORMATION
        JPanel details = new JPanel();
        details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));
        details.setOpaque(false);

        JLabel category = createSmallLabel(item.getCategory());
        JLabel name = createLabel(item.getName(), 17, Design.NAVY);
        JLabel price = createLabel(formatPrice(item.getPrice()), 15, Design.ORANGE);

        details.add(category);
        details.add(Box.createVerticalStrut(5));
        details.add(name);
        details.add(Box.createVerticalStrut(7));
        details.add(price);

        // QUANTITY CONTROLS
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        controls.setOpaque(false);

        OrderItem existing = orderItems.get(item);
        String initialQty = existing == null ? "0" : String.valueOf(existing.getQuantity());

        JLabel quantity = createLabel(initialQty, 15, Design.NAVY);
        quantity.setHorizontalAlignment(SwingConstants.CENTER);
        quantity.setPreferredSize(new Dimension(28, 30));

        quantityLabels.put(item, quantity);

        JButton minus = createSolidButton("-", Design.BACKGROUND, Design.NAVY, true);
        JButton plus = createSolidButton("+", Design.ORANGE, Color.WHITE, false);

        minus.addActionListener(e -> decrementItem(item));
        plus.addActionListener(e -> incrementItem(item));

        controls.add(minus);
        controls.add(quantity);
        controls.add(plus);

        card.add(details, BorderLayout.CENTER);
        card.add(controls, BorderLayout.SOUTH);
        card.setPreferredSize(new Dimension(200, 145));

        return card;
    }

    // ORDER PANEL
    private JPanel buildOrderPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setPreferredSize(new Dimension(300, 0));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Design.LINE),
                new EmptyBorder(18, 18, 18, 18)
        ));

        JLabel title = createLabel("Your Order", 21, Design.NAVY);
        itemCount = createSmallLabel("0 items");

        // ESTIMATED PREP TIME BADGE
        estTimeLabel = new JLabel("Est. Time: --") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 237, 213));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        estTimeLabel.setFont(Design.font(Font.BOLD, 12));
        estTimeLabel.setForeground(Design.ORANGE);
        estTimeLabel.setBorder(new EmptyBorder(4, 10, 4, 10));

        JPanel metaRow = new JPanel(new BorderLayout());
        metaRow.setOpaque(false);
        metaRow.setBorder(new EmptyBorder(6, 0, 0, 0));
        metaRow.add(itemCount, BorderLayout.WEST);
        metaRow.add(estTimeLabel, BorderLayout.EAST);

        JPanel heading = new JPanel(new BorderLayout());
        heading.setOpaque(false);
        heading.add(title, BorderLayout.NORTH);
        heading.add(metaRow, BorderLayout.SOUTH);

        orderList = new JPanel();
        orderList.setLayout(new BoxLayout(orderList, BoxLayout.Y_AXIS));
        orderList.setBackground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(orderList);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.getVerticalScrollBar().setUnitIncrement(12);

        JPanel bottom = new JPanel(new BorderLayout(0, 12));
        bottom.setOpaque(false);

        totalLabel = createLabel("PHP 0.00", 22, Design.NAVY);

        JButton proceed = createSolidButton("Proceed to Payment", Design.ORANGE, Color.WHITE, false);
        proceed.setPreferredSize(new Dimension(240, 46));
        proceed.addActionListener(e -> proceedToPayment());

        bottom.add(totalLabel, BorderLayout.NORTH);
        bottom.add(proceed, BorderLayout.SOUTH);

        panel.add(heading, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);

        return panel;
    }

    // REFRESH ORDER SUMMARY
    private void refreshOrder() {
        orderList.removeAll();

        int count = 0;

        for (Map.Entry<MenuItem, OrderItem> entry : orderItems.entrySet()) {
            MenuItem item = entry.getKey();
            OrderItem orderItem = entry.getValue();

            count += orderItem.getQuantity();

            JPanel row = new JPanel(new BorderLayout(5, 5));
            row.setOpaque(false);
            row.setBorder(new EmptyBorder(8, 0, 8, 0));

            JPanel info = new JPanel();
            info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
            info.setOpaque(false);

            JLabel name = createLabel(item.getName(), 13, Design.NAVY);
            JLabel price = createSmallLabel(
                    orderItem.getQuantity() + " x " + formatPrice(item.getPrice())
            );

            info.add(name);
            info.add(Box.createVerticalStrut(4));
            info.add(price);

            JLabel subtotal = createLabel(
                    formatPrice(item.getPrice() * orderItem.getQuantity()),
                    13,
                    Design.ORANGE
            );

            row.add(info, BorderLayout.CENTER);
            row.add(subtotal, BorderLayout.EAST);

            orderList.add(row);
            orderList.add(new JSeparator());
        }

        int estMinutes = calculateEstimatedTime(count);

        itemCount.setText(count + (count == 1 ? " item" : " items"));
        estTimeLabel.setText(count == 0 ? "Est. Time: --" : "Est. ~" + estMinutes + " mins");
        totalLabel.setText(formatPrice(order.computeTotal()));

        orderList.revalidate();
        orderList.repaint();
    }

    private int calculateEstimatedTime(int totalItems) {
        if (totalItems <= 0) {
            return 0;
        }
        // Base kitchen prep time of 5 mins + 3 mins per item
        return 5 + (totalItems * 3);
    }

    // ADD ITEM
    private void incrementItem(MenuItem item) {
        OrderItem orderItem = orderItems.get(item);

        if (orderItem == null) {
            orderItem = new OrderItem(item, 1, "Regular", "");
            orderItems.put(item, orderItem);
            order.addItem(orderItem);
        } else {
            orderItem.updateQuantity(orderItem.getQuantity() + 1);
        }

        updateQuantityLabel(item);
        refreshOrder();
    }

    // REMOVE ITEM
    private void decrementItem(MenuItem item) {
        OrderItem orderItem = orderItems.get(item);

        if (orderItem == null) {
            return;
        }

        int newQuantity = orderItem.getQuantity() - 1;

        if (newQuantity <= 0) {
            orderItems.remove(item);
            order.removeItem(orderItem);
        } else {
            orderItem.updateQuantity(newQuantity);
        }

        updateQuantityLabel(item);
        refreshOrder();
    }

    // UPDATE QUANTITY LABEL
    private void updateQuantityLabel(MenuItem item) {
        JLabel label = quantityLabels.get(item);
        if (label == null) {
            return;
        }

        OrderItem orderItem = orderItems.get(item);
        label.setText(orderItem == null ? "0" : String.valueOf(orderItem.getQuantity()));
    }

    // PROCEED TO PAYMENT
    private void proceedToPayment() {
        if (orderItems.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Add at least one item to your order.",
                    "Empty Order",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        int totalItems = orderItems.values().stream()
                .mapToInt(OrderItem::getQuantity)
                .sum();
        int estMinutes = calculateEstimatedTime(totalItems);

        new uiPayment(orderType, order, orderItems, estMinutes).setVisible(true);
        dispose();
    }

    // DESIGN HELPERS
    private JLabel createLabel(String text, int size, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(Design.font(Font.BOLD, size));
        label.setForeground(color);
        return label;
    }

    private JLabel createSmallLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(Design.font(Font.PLAIN, 12));
        label.setForeground(Design.MUTED);
        return label;
    }

    /**
     * Custom-painted button that bypasses WindowsLookAndFeel background overrides.
     */
    private JButton createSolidButton(String text, Color bg, Color fg, boolean withBorder) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color fill = bg;
                if (getModel().isPressed()) {
                    fill = bg.darker();
                } else if (getModel().isRollover()) {
                    fill = bg.equals(Design.ORANGE) ? Design.ORANGE_HOVER : new Color(238, 242, 246);
                }

                g2.setColor(fill);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

                if (withBorder) {
                    g2.setColor(Design.LINE);
                    g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
                }

                g2.dispose();
                super.paintComponent(g);
            }
        };

        button.setFont(Design.font(Font.BOLD, 13));
        button.setForeground(fg);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setRolloverEnabled(true);
        button.setBorder(new EmptyBorder(9, 15, 9, 15));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        return button;
    }

    static String formatPrice(double price) {
        return String.format("PHP %,.2f", price);
    }

    // MENU CATALOG
    private static MenuCatalog createCatalog() {
        MenuCatalog catalog = new MenuCatalog();

        // BURGERS
        catalog.addItem(createItem(1, "Classic Burger", "Burgers", 129));
        catalog.addItem(createItem(2, "Cheese Burger", "Burgers", 149));
        catalog.addItem(createItem(3, "Double Burger", "Burgers", 189));

        // FRIES
        catalog.addItem(createItem(4, "Regular Fries", "Fries", 59));
        catalog.addItem(createItem(5, "Cheese Fries", "Fries", 79));
        catalog.addItem(createItem(6, "Loaded Fries", "Fries", 99));

        // DRINKS
        catalog.addItem(createItem(7, "Iced Tea", "Drinks", 39));
        catalog.addItem(createItem(8, "Coke", "Drinks", 45));
        catalog.addItem(createItem(9, "Lemon Juice", "Drinks", 49));

        // MAIN DISHES
        catalog.addItem(createItem(10, "Crispy Chicken Meal", "Main Dishes", 159));
        catalog.addItem(createItem(11, "Chicken Fillet", "Main Dishes", 139));
        catalog.addItem(createItem(12, "Beef Steak Meal", "Main Dishes", 199));

        // PASTA
        catalog.addItem(createItem(13, "Spaghetti", "Pasta", 139));
        catalog.addItem(createItem(14, "Carbonara", "Pasta", 149));

        // DESSERTS
        catalog.addItem(createItem(15, "Chocolate Sundae", "Desserts", 49));
        catalog.addItem(createItem(16, "Vanilla Ice Cream", "Desserts", 39));

        return catalog;
    }

    private static MenuItem createItem(int id, String name, String category, double price) {
        MenuItem item = new MenuItem();
        item.setItemId(id);
        item.setName(name);
        item.setCategory(category);
        item.setPrice(price);
        item.setAvailability(true);
        return item;
    }
}
