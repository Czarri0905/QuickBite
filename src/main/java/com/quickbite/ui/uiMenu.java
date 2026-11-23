
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

    // COLORS
    private final Color NAVY = new Color(30, 41, 59);
    private final Color ORANGE = new Color(249, 115, 22);
    private final Color MUTED = new Color(120, 130, 145);
    private final Color LINE = new Color(230, 233, 238);
    private final Color BG = new Color(247, 248, 250);

    // UI COMPONENTS
    private JPanel menuGrid;
    private JPanel orderList;
    private JLabel itemCount;
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

        // DESIGN: Main application layout
        JPanel page = new JPanel(new BorderLayout(20, 20));
        page.setBackground(BG);
        page.setBorder(new EmptyBorder(20, 25, 20, 25));

        page.add(buildHeader(), BorderLayout.NORTH);
        page.add(buildMainContent(), BorderLayout.CENTER);

        setContentPane(page);
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

        JLabel logo = createLabel("QUICKBITE", 18, ORANGE);

        JLabel title = createLabel("Choose Your Food", 27, NAVY);
        JLabel subtitle = createSmallLabel(orderType + " order");

        titlePanel.add(logo);
        titlePanel.add(Box.createVerticalStrut(10));
        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(3));
        titlePanel.add(subtitle);

        JButton back = createButton("Back", Color.WHITE, NAVY);

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

        menuPanel.add(buildCategories(), BorderLayout.NORTH);
        menuPanel.add(buildMenuArea(), BorderLayout.CENTER);

        // RIGHT: ORDER SUMMARY
        JPanel orderPanel = buildOrderPanel();

        content.add(menuPanel, BorderLayout.CENTER);
        content.add(orderPanel, BorderLayout.EAST);

        return content;
    }

    // CATEGORY BUTTONS
    private JPanel buildCategories() {
        JPanel categories = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 8, 0)
        );
        categories.setOpaque(false);

        String[] names = {
            "All",
            "Burgers",
            "Fries",
            "Drinks",
            "Main Dishes",
            "Pasta",
            "Desserts"
        };

        for (String name : names) {
            JButton button = createCategoryButton(name);

            button.addActionListener(e -> {
                selectedCategory = name;
                refreshMenu();
            });

            categories.add(button);
        }

        return categories;
    }

    private JButton createCategoryButton(String name) {
        boolean selected = selectedCategory.equals(name);

        JButton button = new JButton(name);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setForeground(selected ? Color.WHITE : NAVY);
        button.setBackground(selected ? ORANGE : Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        selected ? ORANGE : LINE
                ),
                new EmptyBorder(9, 15, 9, 15)
        ));

        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    // MENU AREA
    private JScrollPane buildMenuArea() {
        menuGrid = new JPanel(new GridLayout(0, 2, 12, 12));
        menuGrid.setBackground(BG);
        menuGrid.setBorder(new EmptyBorder(2, 2, 10, 2));

        JScrollPane scroll = new JScrollPane(menuGrid);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(BG);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

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

        // Recreate category buttons to update selected appearance
        Component[] components = ((JPanel)
                ((JPanel) getContentPane().getComponent(1))
                .getComponent(0)).getComponents();

        if (components.length > 0) {
            // Category panel is refreshed when the menu is rebuilt
        }

        menuGrid.revalidate();
        menuGrid.repaint();
    }

    // MENU CARD
    private JPanel buildMenuCard(MenuItem item) {
        JPanel card = new JPanel(new BorderLayout(0, 12));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(LINE),
                new EmptyBorder(15, 15, 15, 15)
        ));

        // ITEM INFORMATION
        JPanel details = new JPanel();
        details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));
        details.setOpaque(false);

        JLabel category = createSmallLabel(item.getCategory());
        JLabel name = createLabel(item.getName(), 17, NAVY);
        JLabel price = createLabel(formatPrice(item.getPrice()), 15, ORANGE);

        details.add(category);
        details.add(Box.createVerticalStrut(5));
        details.add(name);
        details.add(Box.createVerticalStrut(7));
        details.add(price);

        // QUANTITY CONTROLS
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        controls.setOpaque(false);

        JLabel quantity = createLabel("0", 15, NAVY);
        quantity.setPreferredSize(new Dimension(25, 30));

        quantityLabels.put(item, quantity);

        JButton minus = createButton("-", BG, NAVY);
        JButton plus = createButton("+", ORANGE, Color.WHITE);

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
        panel.setPreferredSize(new Dimension(280, 0));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(LINE),
                new EmptyBorder(18, 18, 18, 18)
        ));

        JLabel title = createLabel("Your Order", 21, NAVY);
        itemCount = createSmallLabel("0 items");

        JPanel heading = new JPanel(new BorderLayout());
        heading.setOpaque(false);
        heading.add(title, BorderLayout.NORTH);
        heading.add(itemCount, BorderLayout.SOUTH);

        orderList = new JPanel();
        orderList.setLayout(new BoxLayout(orderList, BoxLayout.Y_AXIS));
        orderList.setBackground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(orderList);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.getVerticalScrollBar().setUnitIncrement(12);

        JPanel bottom = new JPanel(new BorderLayout(0, 15));
        bottom.setOpaque(false);

        totalLabel = createLabel("PHP 0.00", 22, NAVY);

        JButton review = createButton(
                "Review Order", ORANGE, Color.WHITE
        );

        review.setPreferredSize(new Dimension(230, 45));
        review.addActionListener(e -> reviewOrder());

        bottom.add(totalLabel, BorderLayout.NORTH);
        bottom.add(review, BorderLayout.SOUTH);

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

            JLabel name = createLabel(item.getName(), 13, NAVY);
            JLabel price = createSmallLabel(
                    orderItem.getQuantity() + " x "
                    + formatPrice(item.getPrice())
            );

            info.add(name);
            info.add(Box.createVerticalStrut(4));
            info.add(price);

            JLabel subtotal = createLabel(
                    formatPrice(item.getPrice() * orderItem.getQuantity()),
                    13,
                    ORANGE
            );

            row.add(info, BorderLayout.CENTER);
            row.add(subtotal, BorderLayout.EAST);

            orderList.add(row);
            orderList.add(new JSeparator());
        }

        itemCount.setText(count + (count == 1 ? " item" : " items"));
        totalLabel.setText(formatPrice(order.computeTotal()));

        orderList.revalidate();
        orderList.repaint();
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

        label.setText(orderItem == null
                ? "0"
                : String.valueOf(orderItem.getQuantity()));
    }

    // REVIEW ORDER
    private void reviewOrder() {
        if (orderItems.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Add at least one item to your order.",
                    "Empty Order",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        StringBuilder details = new StringBuilder();

        details.append(orderType).append(" order:\n\n");

        for (Map.Entry<MenuItem, OrderItem> entry : orderItems.entrySet()) {
            details.append(entry.getKey().getName())
                    .append(" x ")
                    .append(entry.getValue().getQuantity())
                    .append("\n");
        }

        details.append("\nTotal: ")
                .append(formatPrice(order.computeTotal()));

        JOptionPane.showMessageDialog(
                this,
                details.toString(),
                "Order Summary",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // DESIGN HELPERS
    private JLabel createLabel(String text, int size, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, size));
        label.setForeground(color);
        return label;
    }

    private JLabel createSmallLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        label.setForeground(MUTED);
        return label;
    }

    private JButton createButton(
            String text, Color background, Color foreground) {

        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setForeground(foreground);
        button.setBackground(background);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        background.equals(Color.WHITE) ? LINE : background
                ),
                new EmptyBorder(9, 15, 9, 15)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    private static String formatPrice(double price) {
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

    private static MenuItem createItem(
            int id, String name, String category, double price) {

        MenuItem item = new MenuItem();

        item.setItemId(id);
        item.setName(name);
        item.setCategory(category);
        item.setPrice(price);
        item.setAvailability(true);

        return item;
    }
}
