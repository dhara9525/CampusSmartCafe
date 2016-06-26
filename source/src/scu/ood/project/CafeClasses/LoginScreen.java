package scu.ood.project.CafeClasses;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;

/**
 * LoginScreen class
 */
public class LoginScreen extends JFrame {
    private JButton submitBtn;
    private JButton closeBtn;
    private JLabel welcomeLbl;
    private JLabel userNameLbl;
    private JLabel passwordLbl;
    private JPanel loginPanel;
    private JPasswordField passwordField;
    private JTextField uNameField;
    public static Boolean isUserLoggedIn = false;
    public static String loggedInUserName = null;
    public static int loggedInUserId = -1;
    public static Connection connection;
    public static String connectionString = "jdbc:mysql://localhost/CampusSmartCafe?user=sqluser&password=sqluserpw";
    private CampusScreen campusScreen;

    /**
     * Constructor
     */
    public LoginScreen() {
        initComponents();
    }

    /**
     * init login screen components
     */
    private void initComponents() {

        loginPanel = new JPanel();
        welcomeLbl = new JLabel();
        userNameLbl = new JLabel();
        uNameField = new JTextField();
        passwordLbl = new JLabel();
        passwordField = new JPasswordField();
        submitBtn = new JButton();
        closeBtn = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Welcome to the Campus Smart Cafe");
        setBounds(300,100,500,300);

        loginPanel.setBorder(BorderFactory.createTitledBorder("Login to place order, view/change your preferences"));

        
        welcomeLbl.setIcon(new ImageIcon(LoginScreen.class.getResource("/welcome.jpg")));
        welcomeLbl.setText("welcomeLbl");

        userNameLbl.setText("Card Number");

        passwordLbl.setText("Password");

        submitBtn.setText("Submit");
        submitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    submitBtnActionPerformed(evt);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        closeBtn.setText("Close");
        closeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                closeBtnActionPerformed(evt);
            }
        });

        GroupLayout loginPanelLayout = new GroupLayout(loginPanel);
        loginPanel.setLayout(loginPanelLayout);
        loginPanelLayout.setHorizontalGroup(
                loginPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(loginPanelLayout.createSequentialGroup()
                                .addGroup(loginPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(loginPanelLayout.createSequentialGroup()
                                                .addGap(28, 28, 28)
                                                .addComponent(welcomeLbl, GroupLayout.PREFERRED_SIZE, 626, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(loginPanelLayout.createSequentialGroup()
                                                .addGap(211, 211, 211)
                                                .addGroup(loginPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addGroup(loginPanelLayout.createSequentialGroup()
                                                                .addGroup(loginPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addComponent(userNameLbl)
                                                                        .addComponent(passwordLbl))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(loginPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(uNameField, GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                                                                        .addComponent(passwordField)))
                                                        .addGroup(loginPanelLayout.createSequentialGroup()
                                                                .addComponent(submitBtn)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(closeBtn)))))
                                .addContainerGap(30, Short.MAX_VALUE))
        );
        loginPanelLayout.setVerticalGroup(
                loginPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(loginPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(welcomeLbl, GroupLayout.PREFERRED_SIZE, 265, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(loginPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(uNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(userNameLbl))
                                .addGap(18, 18, 18)
                                .addGroup(loginPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(passwordLbl)
                                        .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(loginPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(submitBtn)
                                        .addComponent(closeBtn))
                                .addContainerGap(58, Short.MAX_VALUE))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(loginPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(27, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(loginPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(13, Short.MAX_VALUE))
        );

        pack();
    }

    /**
     * Populates cafe list from DB and adds to
     * relevant object based on cafe/vending machine
     */
    public void populateCafeList() {
        Point cafe_location;
        String cafe_name;
        int cafe_wait_time;
        int cafe_id;
        String cafe_query = "SELECT * FROM CampusSmartCafe.cafes;";
        Statement statement;
        ResultSet resultSet;


        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(cafe_query);

            while (resultSet.next()) {
                cafe_name = resultSet.getString("cafe_name");
                cafe_id = resultSet.getInt("cafe_id");
                cafe_wait_time = resultSet.getInt("cafe_wait_time");
                cafe_location = new Point(resultSet.getInt("cafe_coordinate_x"), resultSet.getInt("cafe_coordinate_y"));

                if (resultSet.getBoolean("vending_machine")) {
                    VendingMachine vendingMachine = new VendingMachine(cafe_location);
                    vendingMachine.cafeName = cafe_name;
                    vendingMachine.setCafeID(cafe_id);
                    campusScreen.getCampusScreen().insertVendingMachine(vendingMachine);
                    campusScreen.getCafeList().addElement(vendingMachine);
                } else {
                    Cafe cafe = new Cafe(cafe_location);
                    cafe.cafeName = cafe_name;
                    cafe.cafeWaitTime = cafe_wait_time;
                    cafe.setCafeID(cafe_id);
                    campusScreen.getCampusScreen().insertCafe(cafe);
                    campusScreen.getCafeList().addElement(cafe);
                }
            }
            statement.close();
        } catch (SQLException exception) {
            System.out.println(exception);
        }
    }

    /**
     * @param evt
     * @throws IOException
     */
    private void submitBtnActionPerformed(ActionEvent evt) throws IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(connectionString);
            Statement st = connection.createStatement();

            String uname = uNameField.getText().toString();
            String passw = new String(passwordField.getPassword());
            String getUserQuery = "SELECT * FROM CampusSmartCafe.cafe_users where user_name='" + uname + "' and password='" + passw + "';";

            ResultSet res = st.executeQuery(getUserQuery);
            if (res.next()) {
                isUserLoggedIn = true;
                loggedInUserName = res.getString("user_name");
                loggedInUserId = res.getInt("user_id");
                campusScreen = new CampusScreen(loggedInUserId, loggedInUserName);
                this.setVisible(false);
                populateCafeList();
                campusScreen.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid User Name or Password. Please try again.");
            }

        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Sql Error");
        }
    }

    /**
     * @param evt
     */
    private void closeBtnActionPerformed(ActionEvent evt) {
        System.exit(0);
    }
}
