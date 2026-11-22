package com.quickbite.ui;
import java.awt.*;
import javax.swing.*;

// Shared colors, fonts, and reusable UI components. 
class Design {

    // Colors and fonts

    static final Color NAVY = new Color(31, 48, 69);
    static final Color NAVY_HOVER = new Color(46, 69, 99);
    static final Color ORANGE = new Color(239, 133, 59);
    static final Color ORANGE_HOVER = new Color(246, 150, 84);
    static final Color BACKGROUND = new Color(245, 247, 250);
    static final Color MUTED = new Color(100, 112, 128);
    static final Color LINE = new Color(220, 225, 232);

    static Font font(int style, int size) {
        return new Font("SansSerif", style, size);
    }

    private static final int LOGO_SIZE = 68;
    private static final int CARD_ROUNDNESS = 30;
    private static final int CARD_SHADOW = 6;
    private static final int CARD_PADDING = 30;

    private Design() {
    }

    // UI components

    static JPanel createPage() {
        JPanel page = new JPanel(new BorderLayout(0, 24));
        page.setBackground(BACKGROUND);
        page.setBorder(BorderFactory.createEmptyBorder(48, 40, 28, 40));
        return page;
    }

    static JLabel createLabel(String text, int style, int size, Color color) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(font(style, size));
        label.setForeground(color);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    static JComponent createLogo() {
        JComponent logo = new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = smooth(g);

                g2.setColor(ORANGE);
                g2.fillRoundRect(0, 0, LOGO_SIZE, LOGO_SIZE, LOGO_SIZE * 2 / 3, LOGO_SIZE * 2 / 3);

                g2.setColor(Color.WHITE);
                g2.setFont(font(Font.BOLD, LOGO_SIZE / 2));
                FontMetrics metrics = g2.getFontMetrics();
                int x = (LOGO_SIZE - metrics.stringWidth("Q")) / 2;
                int y = (LOGO_SIZE - metrics.getHeight()) / 2 + metrics.getAscent();
                g2.drawString("Q", x, y);

                g2.dispose();
            }
        };

        Dimension size = new Dimension(LOGO_SIZE, LOGO_SIZE);
        logo.setPreferredSize(size);
        logo.setMinimumSize(size);
        logo.setMaximumSize(size);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        return logo;
    }

    static JButton createCardButton(String title, String detail, Color normalColor, Color hoverColor) {
        JButton card = new JButton(title) {
            @Override
            protected void paintComponent(Graphics g) {
                paintCard(g, this, detail, normalColor, hoverColor);
            }
        };
        removeDefaultLook(card);
        card.setPreferredSize(new Dimension(300, 150));
        return card;
    }

    static JButton createPillButton(String text) {
        JButton pill = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                paintPill(g, this);
                super.paintComponent(g);
            }
        };
        removeDefaultLook(pill);
        pill.setFont(font(Font.BOLD, 15));
        pill.setForeground(NAVY);
        pill.setBorder(BorderFactory.createEmptyBorder(10, 22, 10, 22));
        return pill;
    }

    static JComboBox<String> createComboBox(String... items) {
        JComboBox<String> box = new JComboBox<>(items);
        box.setFont(font(Font.PLAIN, 16));
        box.setBackground(Color.WHITE);
        box.setForeground(NAVY);
        box.setPreferredSize(new Dimension(150, 42));
        return box;
    }

    // Drawing helpers

    private static void removeDefaultLook(JButton button) {
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setRolloverEnabled(true);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private static Graphics2D smooth(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        return g2;
    }

    private static void paintCard(Graphics g, JButton card, String detail, Color normalColor, Color hoverColor) {
        Graphics2D g2 = smooth(g);
        int width = card.getWidth();
        int height = card.getHeight() - CARD_SHADOW;

        g2.setColor(new Color(0, 0, 0, 28));
        g2.fillRoundRect(2, CARD_SHADOW, width - 4, height, CARD_ROUNDNESS, CARD_ROUNDNESS);

        Color color = normalColor;
        if (card.getModel().isPressed()) {
            color = normalColor.darker();
        } else if (card.getModel().isRollover()) {
            color = hoverColor;
        }
        g2.setColor(color);
        g2.fillRoundRect(0, 0, width, height, CARD_ROUNDNESS, CARD_ROUNDNESS);

        drawCardText(g2, card.getText(), detail, height);
        drawCardArrow(g2, width, height);

        if (card.hasFocus()) {
            g2.setStroke(new BasicStroke(2f));
            g2.setColor(new Color(255, 255, 255, 200));
            g2.drawRoundRect(5, 5, width - 11, height - 11, CARD_ROUNDNESS - 8, CARD_ROUNDNESS - 8);
        }

        g2.dispose();
    }

    private static void drawCardText(Graphics2D g2, String title, String detail, int height) {
        Font titleFont = font(Font.BOLD, 30);
        Font detailFont = font(Font.PLAIN, 17);
        FontMetrics titleMetrics = g2.getFontMetrics(titleFont);
        FontMetrics detailMetrics = g2.getFontMetrics(detailFont);

        int gap = 6;
        int textHeight = titleMetrics.getHeight() + gap + detailMetrics.getHeight();
        int top = (height - textHeight) / 2;

        g2.setFont(titleFont);
        g2.setColor(Color.WHITE);
        g2.drawString(title, CARD_PADDING, top + titleMetrics.getAscent());

        g2.setFont(detailFont);
        g2.setColor(new Color(255, 255, 255, 215));
        g2.drawString(detail, CARD_PADDING, top + titleMetrics.getHeight() + gap + detailMetrics.getAscent());
    }

    private static void drawCardArrow(Graphics2D g2, int width, int height) {
        int diameter = 46;
        int x = width - CARD_PADDING - diameter;
        int y = (height - diameter) / 2;
        g2.setColor(new Color(255, 255, 255, 45));
        g2.fillOval(x, y, diameter, diameter);

        int centerX = x + diameter / 2;
        int centerY = y + diameter / 2;
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawLine(centerX - 3, centerY - 9, centerX + 6, centerY);
        g2.drawLine(centerX + 6, centerY, centerX - 3, centerY + 9);
    }

    private static void paintPill(Graphics g, JButton pill) {
        Graphics2D g2 = smooth(g);
        int width = pill.getWidth() - 1;
        int height = pill.getHeight() - 1;

        if (pill.getModel().isRollover()) {
            g2.setColor(new Color(232, 236, 242));
        } else {
            g2.setColor(Color.WHITE);
        }
        g2.fillRoundRect(0, 0, width, height, height, height);

        if (pill.hasFocus()) {
            g2.setColor(ORANGE);
            g2.setStroke(new BasicStroke(2f));
        } else {
            g2.setColor(LINE);
            g2.setStroke(new BasicStroke(1f));
        }
        g2.drawRoundRect(0, 0, width, height, height, height);

        g2.dispose();
    }
}
