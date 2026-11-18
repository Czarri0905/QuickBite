
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class PrjRestaurantKioskSystem extends JFrame implements ActionListener {

    // object declaration
    Container con;
    JLabel lblTitle, lblType, lblTable, lblMenu, lblTray, lblTotal;
    JRadioButton rdoDineIn, rdoTakeOut;
    ButtonGroup btgType;
    JComboBox<String> cmbTable;
    JButton btnApp, btnMain, btnDessert, btnDrink;
    JButton btn1, btn2, btn3, btn4, btn5;
    JButton btnMap, btnReset, btnPay, btnBack, btnRemove, btnExit;
    JTextArea txtTray;
    JScrollPane scrTray;
    JPanel panMain, panSub;
    ArrayList<RestaurantRecord> orderList;

    // image placeholders
    JLabel img1, img2, img3, img4, img5;

    String[] tableNames = {"Table 1", "Table 2", "Table 3", "Table 4", "Table 5", "Table 6", "Table 7", "Table 8"};
    boolean[] tableFree = {true, true, true, true, true, true, true, true};

    String currentCat = "";

    Font fTitle = new Font("Segoe UI", Font.BOLD, 28);
    Font fLabel = new Font("Segoe UI", Font.BOLD, 16);
    Font fButton = new Font("Segoe UI", Font.BOLD, 16);
    Font fDish = new Font("Segoe UI", Font.BOLD, 15);
    Font fTray = new Font("Consolas", Font.PLAIN, 15);

    Color bgMain = new Color(255, 248, 240);
    Color bgLeft = new Color(255, 239, 213);
    Color bgRight = new Color(250, 250, 255);
    Color clrTitle = new Color(153, 51, 0);
    Color clrCatBtn = new Color(255, 204, 153);
    Color clrDishBtn = new Color(255, 255, 204);
    Color clrActionBtn = new Color(204, 229, 255);
    Color clrExitBtn = new Color(255, 204, 204);

    public PrjRestaurantKioskSystem() {
        // instantiation
        con = getContentPane();

        lblTitle = new JLabel("Welcome to The Bite of '26!");
        lblType = new JLabel("Order Type");
        lblTable = new JLabel("Table No.");
        lblMenu = new JLabel("Menu Category");
        lblTray = new JLabel("Customer Tray");
        lblTotal = new JLabel("Total: P0.00");

        rdoDineIn = new JRadioButton("Dine-In", true);
        rdoTakeOut = new JRadioButton("Take-Out");
        btgType = new ButtonGroup();

        cmbTable = new JComboBox<String>();

        btnApp = new JButton("Appetizers");
        btnMain = new JButton("Main Course");
        btnDessert = new JButton("Desserts");
        btnDrink = new JButton("Drinks");

        btn1 = new JButton();
        btn2 = new JButton();
        btn3 = new JButton();
        btn4 = new JButton();
        btn5 = new JButton();

        btnMap = new JButton("View Map");
        btnReset = new JButton("Start Over");
        btnPay = new JButton("Pay & Order");
        btnBack = new JButton("Back");
        btnRemove = new JButton("Remove Item");
        btnExit = new JButton("Exit");

        txtTray = new JTextArea();
        scrTray = new JScrollPane(txtTray);

        panMain = new JPanel();
        panSub = new JPanel();

        img1 = new JLabel("IMAGE", SwingConstants.CENTER);
        img2 = new JLabel("IMAGE", SwingConstants.CENTER);
        img3 = new JLabel("IMAGE", SwingConstants.CENTER);
        img4 = new JLabel("IMAGE", SwingConstants.CENTER);
        img5 = new JLabel("IMAGE", SwingConstants.CENTER);

        orderList = new ArrayList<RestaurantRecord>();

        // frame
        setTitle("Restaurant Kiosk System");
        setSize(1120, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // from internet
        setResizable(false);

        // container layout
        con.setLayout(null);
        con.setBackground(bgMain);

        // title
        lblTitle.setFont(fTitle);
        lblTitle.setForeground(clrTitle);
        lblTitle.setBounds(300, 20, 500, 35);
        con.add(lblTitle);

        // order type
        lblType.setFont(fLabel);
        lblType.setBounds(740, 80, 120, 25);
        con.add(lblType);

        rdoDineIn.setBounds(740, 110, 100, 25);
        rdoTakeOut.setBounds(845, 110, 100, 25);
        rdoDineIn.setBackground(bgRight);
        rdoTakeOut.setBackground(bgRight);
        rdoDineIn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        rdoTakeOut.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        btgType.add(rdoDineIn);
        btgType.add(rdoTakeOut);
        con.add(rdoDineIn);
        con.add(rdoTakeOut);

        // table area
        lblTable.setFont(fLabel);
        lblTable.setBounds(740, 150, 100, 25);
        con.add(lblTable);

        cmbTable.setBounds(740, 180, 130, 28);
        cmbTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        con.add(cmbTable);

        btnMap.setBounds(880, 180, 120, 30);
        styleActionButton(btnMap);
        con.add(btnMap);

        // tray area
        lblTray.setFont(fLabel);
        lblTray.setBounds(740, 230, 150, 25);
        con.add(lblTray);

        txtTray.setEditable(false);
        txtTray.setFont(fTray);
        txtTray.setBackground(new Color(255, 255, 250));
        txtTray.setBorder(new LineBorder(new Color(180, 180, 180), 1));

        scrTray.setBounds(740, 260, 310, 250);
        con.add(scrTray);

        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTotal.setBounds(760, 520, 290, 30);
        lblTotal.setForeground(new Color(0, 120, 60));
        lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        con.add(lblTotal);

        // right side buttons
        btnPay.setBounds(740, 560, 145, 38);
        btnRemove.setBounds(740, 608, 145, 38);
        btnReset.setBounds(905, 560, 145, 38);
        btnExit.setBounds(905, 608, 145, 38);

        styleActionButton(btnPay);
        styleActionButton(btnRemove);
        styleActionButton(btnReset);
        styleExitButton(btnExit);

        con.add(btnPay);
        con.add(btnRemove);
        con.add(btnReset);
        con.add(btnExit);

        // menu label
        lblMenu.setFont(fLabel);
        lblMenu.setBounds(70, 80, 150, 25);
        con.add(lblMenu);

        // main panel
        panMain.setLayout(new GridLayout(2, 2, 20, 20));
        panMain.setBounds(50, 120, 640, 500);
        panMain.setBackground(bgLeft);

        styleCategoryButton(btnApp);
        styleCategoryButton(btnMain);
        styleCategoryButton(btnDessert);
        styleCategoryButton(btnDrink);

        panMain.add(btnApp);
        panMain.add(btnMain);
        panMain.add(btnDessert);
        panMain.add(btnDrink);
        con.add(panMain);

        // sub panel
        panSub.setLayout(null);
        panSub.setBounds(50, 120, 640, 500);
        panSub.setBackground(bgLeft);

        btnBack.setBounds(20, 20, 100, 32);
        styleActionButton(btnBack);
        panSub.add(btnBack);

        // image placeholders
        styleImagePlaceholder(img1);
        styleImagePlaceholder(img2);
        styleImagePlaceholder(img3);
        styleImagePlaceholder(img4);
        styleImagePlaceholder(img5);

        img1.setBounds(40, 80, 100, 70);
        img2.setBounds(40, 165, 100, 70);
        img3.setBounds(40, 250, 100, 70);
        img4.setBounds(40, 335, 100, 70);
        img5.setBounds(40, 420, 100, 70);

        // bigger dish buttons
        btn1.setBounds(160, 80, 420, 70);
        btn2.setBounds(160, 165, 420, 70);
        btn3.setBounds(160, 250, 420, 70);
        btn4.setBounds(160, 335, 420, 70);
        btn5.setBounds(160, 420, 420, 70);

        styleDishButton(btn1);
        styleDishButton(btn2);
        styleDishButton(btn3);
        styleDishButton(btn4);
        styleDishButton(btn5);

        panSub.add(img1);
        panSub.add(img2);
        panSub.add(img3);
        panSub.add(img4);
        panSub.add(img5);

        panSub.add(btn1);
        panSub.add(btn2);
        panSub.add(btn3);
        panSub.add(btn4);
        panSub.add(btn5);

        con.add(panSub);

        panMain.setVisible(true);
        panSub.setVisible(false);

        displayTable();
        displayOrder();

        // register components
        rdoDineIn.addActionListener(this);
        rdoTakeOut.addActionListener(this);
        cmbTable.addActionListener(this);
        btnMap.addActionListener(this);
        btnReset.addActionListener(this);
        btnPay.addActionListener(this);
        btnBack.addActionListener(this);
        btnRemove.addActionListener(this);
        btnExit.addActionListener(this);
        btnApp.addActionListener(this);
        btnMain.addActionListener(this);
        btnDessert.addActionListener(this);
        btnDrink.addActionListener(this);
        btn1.addActionListener(this);
        btn2.addActionListener(this);
        btn3.addActionListener(this);
        btn4.addActionListener(this);
        btn5.addActionListener(this);
    }

    public void styleCategoryButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        btn.setBackground(clrCatBtn);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setBorder(new LineBorder(new Color(180, 120, 60), 2, true));
    }

    public void styleDishButton(JButton btn) {
        btn.setFont(fDish);
        btn.setBackground(clrDishBtn);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(new LineBorder(new Color(160, 160, 80), 2, true));
    }

    public void styleActionButton(JButton btn) {
        btn.setFont(fButton);
        btn.setBackground(clrActionBtn);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setBorder(new LineBorder(new Color(100, 140, 200), 2, true));
    }

    public void styleExitButton(JButton btn) {
        btn.setFont(fButton);
        btn.setBackground(clrExitBtn);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setBorder(new LineBorder(new Color(180, 80, 80), 2, true));
    }

    public void styleImagePlaceholder(JLabel lbl) {
        lbl.setOpaque(true);
        lbl.setBackground(Color.WHITE);
        lbl.setForeground(Color.GRAY);
        lbl.setFont(new Font("Arial", Font.BOLD, 12));
        lbl.setBorder(new LineBorder(Color.DARK_GRAY, 2));
    }

    public void setImage(JLabel lbl, String path) {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage().getScaledInstance(lbl.getWidth(), lbl.getHeight(), Image.SCALE_SMOOTH);
        lbl.setText("");
        lbl.setIcon(new ImageIcon(img));
    }

    public void clearImages() {
        img1.setIcon(null);
        img2.setIcon(null);
        img3.setIcon(null);
        img4.setIcon(null);
        img5.setIcon(null);

        img1.setText("IMAGE");
        img2.setText("IMAGE");
        img3.setText("IMAGE");
        img4.setText("IMAGE");
        img5.setText("IMAGE");
    }

    public void displayTable() {
        cmbTable.removeAllItems();

        for (int i = 0; i < tableNames.length; i++) {
            if (tableFree[i] == true) {
                cmbTable.addItem(tableNames[i]);
            }
        }

        if (cmbTable.getItemCount() == 0) {
            cmbTable.addItem("No Available Table");
            cmbTable.setEnabled(false);
        } else {
            if (rdoDineIn.isSelected()) {
                cmbTable.setEnabled(true);
            } else {
                cmbTable.setEnabled(false);
            }
        }
    }

    public double getTotal() {
        double total = 0;

        for (int i = 0; i < orderList.size(); i++) {
            total = total + orderList.get(i).getPrice();
        }

        return total;
    }

    public int getEstimatedTime() {
        int totalTime = 0;

        for (int i = 0; i < orderList.size(); i++) {
            totalTime = totalTime + orderList.get(i).getPrepTime();
        }

        return totalTime;
    }

    public void displayOrder() {
        txtTray.setText("");

        if (rdoDineIn.isSelected()) {
            if (cmbTable.getSelectedItem() != null) {
                txtTray.append("DINE-IN : " + cmbTable.getSelectedItem().toString() + "\n\n");
            } else {
                txtTray.append("DINE-IN : No Table\n\n");
            }
        } else {
            txtTray.append("TAKE-OUT\n\n");
        }

        String[] itemNames = new String[orderList.size()];
        double[] itemPrices = new double[orderList.size()];
        int[] itemQty = new int[orderList.size()];
        int count = 0;
        boolean found;

        for (int i = 0; i < orderList.size(); i++) {
            found = false;

            for (int j = 0; j < count; j++) {
                if (orderList.get(i).getItemName().equals(itemNames[j])) {
                    itemQty[j]++;
                    found = true;
                    break;
                }
            }

            if (found == false) {
                itemNames[count] = orderList.get(i).getItemName();
                itemPrices[count] = orderList.get(i).getPrice();
                itemQty[count] = 1;
                count++;
            }
        }

        for (int i = 0; i < count; i++) {
            if (itemQty[i] == 1) {
                txtTray.append(itemNames[i] + " - P" + itemPrices[i] + "\n");
            } else {
                txtTray.append(itemNames[i] + " x" + itemQty[i] + " - P" + itemPrices[i] + "\n");
            }
        }

        txtTray.append("\nEstimated Time: " + getEstimatedTime() + " mins");
        lblTotal.setText("Total: P" + getTotal());
    }

    public void removeItem() {
        String input;
        int itemNo;

        if (orderList.size() == 0) {
            JOptionPane.showMessageDialog(this, "No item to remove.");
            return;
        }

        input = JOptionPane.showInputDialog(
                this,
                "Enter item number to remove:\n" + getOrderNumberList());

        if (input == null) {
            return;
        }

        try {
            itemNo = Integer.parseInt(input);

            if (itemNo >= 1 && itemNo <= orderList.size()) {
                orderList.remove(itemNo - 1);
                displayOrder();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid item number.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.");
        }
    }

    public String getOrderNumberList() {
        String list = "";
        int i;

        for (i = 0; i < orderList.size(); i++) {
            list = list + (i + 1) + ". " + orderList.get(i).getItemName() + "\n";
        }

        return list;
    }

    public void showSubMenu() {
        panMain.setVisible(false);
        panSub.setVisible(true);
    }

    public void showMainMenu() {
        panMain.setVisible(true);
        panSub.setVisible(false);
    }

    public void actionPerformed(ActionEvent evt) {
        Object src = evt.getSource();

        if (src == rdoTakeOut) {
            cmbTable.setEnabled(false);
            btnMap.setEnabled(false);
            displayOrder();
        } else if (src == rdoDineIn) {
            btnMap.setEnabled(true);
            displayTable();
            displayOrder();
        } else if (src == cmbTable) {
            displayOrder();
        } else if (src == btnMap) {
            JDialog dlg = new JDialog(this, "Table Map", true);
            dlg.setLayout(new GridLayout(4, 2, 10, 10));
            dlg.setSize(350, 300);

            for (int i = 0; i < tableNames.length; i++) {
                JButton btnTable = new JButton();

                if (tableFree[i] == true) {
                    btnTable.setText(tableNames[i] + " - Free");
                    btnTable.setBackground(new Color(204, 255, 204));
                } else {
                    btnTable.setText(tableNames[i] + " - Occupied");
                    btnTable.setBackground(new Color(255, 204, 204));
                }

                btnTable.setFocusPainted(false);

                final int index = i;

                btnTable.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (tableFree[index] == false) {
                            int choice = JOptionPane.showConfirmDialog(
                                    dlg,
                                    tableNames[index] + " is occupied.\nDo you want to free this table?",
                                    "Free Table",
                                    JOptionPane.YES_NO_OPTION);

                            if (choice == JOptionPane.YES_OPTION) {
                                tableFree[index] = true;
                                displayTable();
                                displayOrder();
                                dlg.dispose();
                            }
                        } else {
                            JOptionPane.showMessageDialog(dlg, tableNames[index] + " is already free.");
                        }
                    }
                });

                dlg.add(btnTable);
            }

            dlg.setLocationRelativeTo(this);
            dlg.setVisible(true);
        } else if (src == btnReset) {
            orderList.clear();
            rdoDineIn.setSelected(true);
            btnMap.setEnabled(true);
            displayTable();
            displayOrder();
            showMainMenu();
        } else if (src == btnPay) {
            String location = "";

            if (orderList.size() == 0) {
                JOptionPane.showMessageDialog(this, "No order has been added.");
                return;
            }

            if (rdoDineIn.isSelected()) {
                if (cmbTable.getSelectedItem() == null
                        || cmbTable.getSelectedItem().toString().equals("No Available Table")) {
                    JOptionPane.showMessageDialog(this, "No table available.");
                    return;
                }

                location = cmbTable.getSelectedItem().toString();

                for (int i = 0; i < tableNames.length; i++) {
                    if (tableNames[i].equals(location)) {
                        tableFree[i] = false;
                    }
                }
            } else {
                location = "Take-Out";
            }

            JOptionPane.showMessageDialog(this,
                    "Order placed successfully!"
                    + "\nType: " + location
                    + "\nTotal Amount: P" + getTotal()
                    + "\nEstimated Time: " + getEstimatedTime() + " mins");

            orderList.clear();
            rdoDineIn.setSelected(true);
            btnMap.setEnabled(true);
            displayTable();
            displayOrder();
            showMainMenu();
        } else if (src == btnApp) {
            currentCat = "Appetizers";

            btn1.setText("Lumpiang Shanghai - P45.00");
            btn2.setText("Tokwa't Baboy - P65.00");
            btn3.setText("Chicken Empanada - P35.00");
            btn4.setText("Ukoy Fritters - P50.00");
            btn5.setText("Kwek-Kwek Basket - P40.00");

            clearImages();
            // sample image file names
            setImage(img1, "images/lumpia.jpg");
            setImage(img2, "images/baboy.jpg");
            setImage(img3, "images/empanada.jpg");
            setImage(img4, "images/fritters.jpg");
            setImage(img5, "images/kwekwek.jpg");

            showSubMenu();
        } else if (src == btnMain) {
            currentCat = "Main Course";

            btn1.setText("Chicken Adobo - P150.00");
            btn2.setText("Pork Kare-Kare - P185.00");
            btn3.setText("Beef Caldereta - P195.00");
            btn4.setText("Chicken Inasal - P165.00");
            btn5.setText("Bangus Sisig - P175.00");

            clearImages();
            setImage(img1, "images/chicken adobo.jpg");
            setImage(img2, "images/karekare.jpg");
            setImage(img3, "images/kaldereta.jpg");
            setImage(img4, "images/chicken inasal.jpg");
            setImage(img5, "images/sisegg.jpg");

            showSubMenu();
        } else if (src == btnDessert) {
            currentCat = "Desserts";

            btn1.setText("Halo-Halo Special - P95.00");
            btn2.setText("Leche Flan - P75.00");
            btn3.setText("Ube Halaya Cup - P60.00");
            btn4.setText("Mango Float - P85.00");
            btn5.setText("Turon w/ Caramel - P50.00");

            clearImages();
            setImage(img1, "images/hello.jpg");
            setImage(img2, "images/leche.jpg");
            setImage(img3, "images/ube.jpg");
            setImage(img4, "images/mango fruit.jpg");
            setImage(img5, "images/tukon.jpg");

            showSubMenu();
        } else if (src == btnDrink) {
            currentCat = "Drinks";

            btn1.setText("Calamansi Juice - P45.00");
            btn2.setText("Sago't Gulaman - P50.00");
            btn3.setText("Buko Pandan Cooler - P65.00");
            btn4.setText("Mango Shake - P85.00");
            btn5.setText("Salabat Honey Tea - P40.00");

            clearImages();
            setImage(img1, "images/calamansi.jpg");
            setImage(img2, "images/gulaman.jpg");
            setImage(img3, "images/buko.jpg");
            setImage(img4, "images/mango.jpg");
            setImage(img5, "images/tea.jpg");

            showSubMenu();
        } else if (src == btn1) {
            if (currentCat.equals("Appetizers")) {
                orderList.add(new RestaurantRecord("Lumpiang Shanghai", 45.00, 3));
            } else if (currentCat.equals("Main Course")) {
                orderList.add(new RestaurantRecord("Chicken Adobo", 150.00, 12));
            } else if (currentCat.equals("Desserts")) {
                orderList.add(new RestaurantRecord("Halo-Halo Special", 95.00, 4));
            } else if (currentCat.equals("Drinks")) {
                orderList.add(new RestaurantRecord("Calamansi Juice", 45.00, 2));
            }

            displayOrder();
        } else if (src == btn2) {
            if (currentCat.equals("Appetizers")) {
                orderList.add(new RestaurantRecord("Tokwa't Baboy", 65.00, 6));
            } else if (currentCat.equals("Main Course")) {
                orderList.add(new RestaurantRecord("Pork Kare-Kare", 185.00, 15));
            } else if (currentCat.equals("Desserts")) {
                orderList.add(new RestaurantRecord("Leche Flan", 75.00, 4));
            } else if (currentCat.equals("Drinks")) {
                orderList.add(new RestaurantRecord("Saeto't Gulaman", 50.00, 2));
            }

            displayOrder();
        } else if (src == btn3) {
            if (currentCat.equals("Appetizers")) {
                orderList.add(new RestaurantRecord("Chicken Empanada", 35.00, 5));
            } else if (currentCat.equals("Main Course")) {
                orderList.add(new RestaurantRecord("Beef Caldereta", 195.00, 14));
            } else if (currentCat.equals("Desserts")) {
                orderList.add(new RestaurantRecord("Ube Halaya Cup", 60.00, 3));
            } else if (currentCat.equals("Drinks")) {
                orderList.add(new RestaurantRecord("Buko Pandan Cooler", 65.00, 3));
            }

            displayOrder();
        } else if (src == btn4) {
            if (currentCat.equals("Appetizers")) {
                orderList.add(new RestaurantRecord("Ukoy Fritters", 50.00, 7));
            } else if (currentCat.equals("Main Course")) {
                orderList.add(new RestaurantRecord("Chicken Inasal", 165.00, 13));
            } else if (currentCat.equals("Desserts")) {
                orderList.add(new RestaurantRecord("Mango Float", 85.00, 4));
            } else if (currentCat.equals("Drinks")) {
                orderList.add(new RestaurantRecord("Mango Shake", 85.00, 4));
            }

            displayOrder();
        } else if (src == btn5) {
            if (currentCat.equals("Appetizers")) {
                orderList.add(new RestaurantRecord("Kwek-Kwek Basket", 40.00, 4));
            } else if (currentCat.equals("Main Course")) {
                orderList.add(new RestaurantRecord("Bangus Sisig", 175.00, 12));
            } else if (currentCat.equals("Desserts")) {
                orderList.add(new RestaurantRecord("Turon w/ Caramel", 50.00, 5));
            } else if (currentCat.equals("Drinks")) {
                orderList.add(new RestaurantRecord("Salabat Honey Tea", 40.00, 3));
            }

            displayOrder();
        } else if (src == btnBack) {
            showMainMenu();
        } else if (src == btnRemove) {
            removeItem();
        } else if (src == btnExit) {
            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "This program will exit, are you sure?",
                    "Exit Confirmation",
                    JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) {
        PrjRestaurantKioskSystem obj = new PrjRestaurantKioskSystem();
        obj.setVisible(true);
    }
}
