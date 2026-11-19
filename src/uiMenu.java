import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.*;

/** Food menu and order summary for the kiosk. */
class uiMenu extends JFrame {

    private final String orderType;
    private final MenuCatalog catalog = createCatalog();
    private final Order order;
    private final Map<MenuItem, OrderItem> orderItems = new LinkedHashMap<>();
    private final JLabel itemCount = Design.createLabel("0 items", Font.PLAIN, 15, Design.MUTED);
    private final JLabel totalLabel = Design.createLabel("PHP 0.00", Font.BOLD, 22, Design.NAVY);

    uiMenu(String orderType) {
        super("QuickBite Menu");
        this.orderType = orderType;
        this.order = new Order(1, orderType);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 900);
        setMinimumSize(new Dimension(420, 700));
        setLocationRelativeTo(null);

        // DESIGN: Shared page, label, logo, and button styling comes from Design.
        JPanel page = Design.createPage();
        page.add(buildHeader(), BorderLayout.NORTH);
        page.add(buildMenu(), BorderLayout.CENTER);
        page.add(buildOrderFooter(), BorderLayout.SOUTH);
        setContentPane(page);
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout(12, 0));
        header.setOpaque(false);

        JPanel heading = new JPanel();
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));
        heading.setOpaque(false);
        heading.add(Design.createLogo());
        heading.add(Box.createVerticalStrut(10));
        heading.add(Design.createLabel("Our Menu", Font.BOLD, 30, Design.NAVY));
        heading.add(Box.createVerticalStrut(5));
        heading.add(Design.createLabel(orderType + " order", Font.PLAIN, 16, Design.MUTED));

        JButton changeOrder = Design.createPillButton("Back");
        changeOrder.setToolTipText("Return to order type selection");
        changeOrder.addActionListener(event -> {
            new Login().showUi();
            dispose();
        });

        header.add(heading, BorderLayout.CENTER);
        header.add(changeOrder, BorderLayout.EAST);
        return header;
    }

    private JScrollPane buildMenu() {
        JPanel menuList = new JPanel();
        menuList.setLayout(new BoxLayout(menuList, BoxLayout.Y_AXIS));
        menuList.setOpaque(false);

        for (MenuItem item : catalog.getItems()) {
            menuList.add(buildMenuItem(item));
            menuList.add(Box.createVerticalStrut(12));
        }

        JScrollPane scroll = new JScrollPane(menuList);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }

    private JPanel buildMenuItem(MenuItem item) {
        JPanel card = new JPanel(new BorderLayout(12, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Design.LINE),
                BorderFactory.createEmptyBorder(14, 16, 14, 12)));

        JPanel details = new JPanel();
        details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));
        details.setOpaque(false);

        JLabel name = Design.createLabel(item.getName(), Font.BOLD, 18, Design.NAVY);
        name.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel category = Design.createLabel(item.getCategory(), Font.PLAIN, 14, Design.MUTED);
        category.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel price = Design.createLabel(formatPrice(item.getPrice()), Font.BOLD, 16, Design.ORANGE);
        price.setAlignmentX(Component.LEFT_ALIGNMENT);
        details.add(name);
        details.add(Box.createVerticalStrut(3));
        details.add(category);
        details.add(Box.createVerticalStrut(5));
        details.add(price);

        JPanel quantityControls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        quantityControls.setOpaque(false);
        JButton remove = Design.createPillButton("-");
        remove.setToolTipText("Remove one " + item.getName());
        JLabel quantity = Design.createLabel("0", Font.BOLD, 16, Design.NAVY);
        quantity.setPreferredSize(new Dimension(24, 36));
        JButton add = Design.createPillButton("+");
        add.setToolTipText("Add one " + item.getName());
        remove.addActionListener(event -> decrementItem(item, quantity));
        add.addActionListener(event -> incrementItem(item, quantity));
        quantityControls.add(remove);
        quantityControls.add(quantity);
        quantityControls.add(add);

        card.add(details, BorderLayout.CENTER);
        card.add(quantityControls, BorderLayout.EAST);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        return card;
    }

    private JPanel buildOrderFooter() {
        JPanel footer = new JPanel(new BorderLayout(12, 10));
        footer.setOpaque(false);
        footer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, Design.LINE),
                BorderFactory.createEmptyBorder(14, 0, 0, 0)));

        JPanel summary = new JPanel(new BorderLayout());
        summary.setOpaque(false);
        summary.add(itemCount, BorderLayout.WEST);
        summary.add(totalLabel, BorderLayout.EAST);

        JButton review = Design.createCardButton(
                "Review Order", "Check your items and total", Design.ORANGE, Design.ORANGE_HOVER);
        review.setPreferredSize(new Dimension(300, 82));
        review.addActionListener(event -> reviewOrder());

        footer.add(summary, BorderLayout.NORTH);
        footer.add(review, BorderLayout.CENTER);
        return footer;
    }

    private void incrementItem(MenuItem item, JLabel quantity) {
        OrderItem orderItem = orderItems.get(item);
        if (orderItem == null) {
            orderItem = new OrderItem(item, 1, "Regular", "");
            orderItems.put(item, orderItem);
            order.addItem(orderItem);
        } else {
            orderItem.updateQuantity(orderItem.getQuantity() + 1);
        }
        quantity.setText(String.valueOf(orderItem.getQuantity()));
        updateOrderSummary();
    }

    private void decrementItem(MenuItem item, JLabel quantity) {
        OrderItem orderItem = orderItems.get(item);
        if (orderItem == null) {
            return;
        }
        if (orderItem.getQuantity() == 1) {
            orderItems.remove(item);
            order.removeItem(orderItem);
            quantity.setText("0");
        } else {
            orderItem.updateQuantity(orderItem.getQuantity() - 1);
            quantity.setText(String.valueOf(orderItem.getQuantity()));
        }
        updateOrderSummary();
    }

    private void updateOrderSummary() {
        int count = 0;
        for (OrderItem item : order.getItems()) {
            count += item.getQuantity();
        }
        itemCount.setText(count + (count == 1 ? " item" : " items"));
        totalLabel.setText(formatPrice(order.computeTotal()));
    }

    private void reviewOrder() {
        if (orderItems.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Add at least one item to your order.",
                    "Your order is empty", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder details = new StringBuilder(orderType).append(" order:\n");
        for (Map.Entry<MenuItem, OrderItem> entry : orderItems.entrySet()) {
            details.append(entry.getKey().getName())
                    .append(" x ")
                    .append(entry.getValue().getQuantity())
                    .append('\n');
        }
        details.append("\nTotal: ").append(formatPrice(order.computeTotal()));
        JOptionPane.showMessageDialog(this, details.toString(),
                "Order summary", JOptionPane.INFORMATION_MESSAGE);
    }

    private static String formatPrice(double price) {
        return String.format("PHP %,.2f", price);
    }

    private static MenuCatalog createCatalog() {
        MenuCatalog catalog = new MenuCatalog();
        catalog.addItem(createItem(1, "Classic Burger", "Burgers", 129.00));
        catalog.addItem(createItem(2, "Crispy Chicken Meal", "Meals", 159.00));
        catalog.addItem(createItem(3, "Spaghetti", "Pasta", 139.00));
        catalog.addItem(createItem(4, "French Fries", "Sides", 59.00));
        catalog.addItem(createItem(5, "Iced Tea", "Drinks", 39.00));
        catalog.addItem(createItem(6, "Chocolate Sundae", "Desserts", 49.00));
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

