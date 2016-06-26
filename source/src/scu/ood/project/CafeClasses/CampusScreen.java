package scu.ood.project.CafeClasses;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import scu.ood.project.DietProfile.DietProfile;
import scu.ood.project.ExpenseProfile.ExpenseProfile;

import java.awt.event.*;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Campus screen class
 */
public class CampusScreen extends JFrame {
	private JButton signOutBtn;
	private JButton dietProfileBtn;
	private JButton expenseProfileBtn;
	private JButton buyNowBtn;
	private JLabel welcomeUsrLbl;
	private JLabel mapInstructionLbl;
	private JLabel paymentLbl;
	private JLabel paymentMethodLbl;
	private JList cafeItemList;
	private JList jCafeList;
	private JPanel cafeMapPanel;
	private JPasswordField cardPasswordField;
	private JScrollPane purchaseScrollPane;
	private JScrollPane cafeListScrollPane;
	private JTextField cardNumberField;
	private int uID;
	private String uName;
	public DefaultListModel<FoodItemList> foodItemsModel;
	private DefaultListModel<Cafes> cafeList;
	public static Cafes selectedCafe = null;
	private DietProfile dietProfileScreen;
	private ExpenseProfile expenseProfileScreen;

	/**
	 * Constructor Creates new form CampusScreen
	 */
	public CampusScreen(int userId, String userName) throws IOException {
		this.uID = userId;
		this.uName = userName;
		initComponents();
	}

	/**
	 * init campus screen components
	 * 
	 * @throws IOException
	 */
	private void initComponents() throws IOException {
		welcomeUsrLbl = new JLabel();
		signOutBtn = new JButton();
		dietProfileBtn = new JButton();
		expenseProfileBtn = new JButton();
		cafeMapPanel = new CampusMap();
		purchaseScrollPane = new JScrollPane();
		mapInstructionLbl = new JLabel();
		paymentLbl = new JLabel();
		paymentMethodLbl = new JLabel();
		cardNumberField = new JTextField();
		cardPasswordField = new JPasswordField();
		buyNowBtn = new JButton();
		cafeListScrollPane = new JScrollPane();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Welcome to Campus Smart Cafe - " + this.uName);
		setBounds(200, 50, 500, 300);

		welcomeUsrLbl.setText("Hello " + this.uName + "!");
		cardNumberField.setVisible(false);
		cardPasswordField.setVisible(false);
		signOutBtn.setText("Sign out");
		signOutBtn.setToolTipText("");
		signOutBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				signOutBtnActionPerformed(evt);
			}
		});

		dietProfileBtn.setText("Your Diet Profile");
		dietProfileBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				dietProfileBtnActionPerformed(evt);
			}
		});

		expenseProfileBtn.setText("Your Expense Profile");
		expenseProfileBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				expenseProfileBtnActionPerformed(evt);
			}
		});

		cafeMapPanel.addMouseListener(new CafeListener());

		GroupLayout cafeMapPanelLayout = new GroupLayout(cafeMapPanel);
		cafeMapPanel.setLayout(cafeMapPanelLayout);
		cafeMapPanelLayout.setHorizontalGroup(
				cafeMapPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
		cafeMapPanelLayout.setVerticalGroup(
				cafeMapPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 366, Short.MAX_VALUE));

		purchaseScrollPane.setBorder(BorderFactory.createTitledBorder("Select an item to purchase"));

		foodItemsModel = new DefaultListModel<>();
		cafeItemList = new JList<>(foodItemsModel);
		cafeItemList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		cafeItemList.setBackground(this.getBackground());
		purchaseScrollPane.setViewportView(cafeItemList);
		purchaseScrollPane.setBackground(this.getBackground());

		mapInstructionLbl.setText("Choose Cafe/Vending Machine on map to continue.");

		 paymentLbl.setText("");

		 paymentMethodLbl.setText("");
		buyNowBtn.setText("Buy Now");
		buyNowBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				buyNowBtnActionPerformed(evt);
			}
		});

		cafeList = new DefaultListModel<>();
		jCafeList = new JList<>(cafeList);

		jCafeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jCafeList.setBackground(this.getBackground());
		jCafeList.setBorder(BorderFactory.createTitledBorder("Select Cafe/Vending Machine"));

		jCafeList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				selectedCafe = null;

				JList list = (JList) e.getSource();
				selectedCafe = ((Cafes) list.getSelectedValue());

				String query = "select food_item.item_name, food_item.item_price, food_item.calories, food_item.vegan, food_item.low_sodium, food_item.low_cholesterol "
						+ "from CampusSmartCafe.cafes as cafe "
						+ "join CampusSmartCafe.food_items as food_item on food_item.cafe_id = cafe.cafe_id "
						+ "where cafe.cafe_id = " + selectedCafe.getCafeID() + ";";

				try {
					Statement statement = LoginScreen.connection.createStatement();
					ResultSet resultSet = statement.executeQuery(query);

					foodItemsModel.removeAllElements();
					cafeItemList.removeAll();

					while (resultSet.next()) {
						addItemToJList(new FoodItem(resultSet.getString(1), resultSet.getInt(2), resultSet.getInt(3),
								resultSet.getBoolean(4), resultSet.getBoolean(5), resultSet.getBoolean(6)));
					}

					cafeItemList.repaint();
					mapInstructionLbl.setText("<HTML><b>" + selectedCafe.cafeName + "</b></HTML>");
				} catch (SQLException exc) {
					exc.printStackTrace();
				}
			}
		});

		cafeListScrollPane.setViewportView(jCafeList);
		cafeListScrollPane.setBackground(this.getBackground());

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(
						GroupLayout.Alignment.LEADING)
				.addGroup(GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup().addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(welcomeUsrLbl, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
								.addGap(18, 18, 18).addComponent(dietProfileBtn).addGap(18, 18, 18)
								.addComponent(expenseProfileBtn).addGap(18, 18, 18).addComponent(signOutBtn)
								.addGap(28, 28, 28))
				.addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(cafeMapPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(layout.createSequentialGroup().addGroup(layout
								.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(mapInstructionLbl)
								.addGroup(layout.createSequentialGroup()
										.addComponent(purchaseScrollPane, GroupLayout.PREFERRED_SIZE, 318,
												GroupLayout.PREFERRED_SIZE)
										.addGap(45, 45, 45)
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addComponent(buyNowBtn)
												.addGroup(layout.createSequentialGroup().addGroup(layout
														.createParallelGroup(GroupLayout.Alignment.LEADING)
														.addComponent(paymentLbl, GroupLayout.PREFERRED_SIZE, 132,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(paymentMethodLbl, GroupLayout.PREFERRED_SIZE, 186,
																GroupLayout.PREFERRED_SIZE)
														.addGroup(layout
																.createParallelGroup(GroupLayout.Alignment.TRAILING,
																		false)
																.addComponent(cardPasswordField,
																		GroupLayout.Alignment.LEADING,
																		GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
																.addComponent(cardNumberField,
																		GroupLayout.Alignment.LEADING)))
														.addGap(51, 51, 51).addComponent(cafeListScrollPane,
																GroupLayout.PREFERRED_SIZE, 213,
																GroupLayout.PREFERRED_SIZE)))))
								.addGap(0, 135, Short.MAX_VALUE)))
						.addContainerGap()));
		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup().addContainerGap()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(welcomeUsrLbl, GroupLayout.PREFERRED_SIZE, 41,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(signOutBtn).addComponent(dietProfileBtn)
										.addComponent(expenseProfileBtn))
								.addGap(3, 3, 3).addComponent(mapInstructionLbl)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(cafeMapPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addGroup(layout.createSequentialGroup().addGap(33, 33, 33)
												.addComponent(purchaseScrollPane))
										.addGroup(layout.createSequentialGroup()
												.addComponent(paymentLbl, GroupLayout.PREFERRED_SIZE, 35,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
												.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
														.addGroup(layout.createSequentialGroup()
																.addComponent(paymentMethodLbl,
																		GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE)
																.addGap(18, 18, 18)
																.addComponent(cardNumberField,
																		GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE)
																.addGap(18, 18, 18)
																.addComponent(cardPasswordField,
																		GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE)
																.addGap(18, 18, 18).addComponent(buyNowBtn,
																		GroupLayout.PREFERRED_SIZE, 45,
																		GroupLayout.PREFERRED_SIZE))
														.addComponent(cafeListScrollPane, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
												.addGap(0, 28, Short.MAX_VALUE)))));

		pack();
	}

	/**
	 * @param evt
	 */
	private void signOutBtnActionPerformed(ActionEvent evt) {
		JOptionPane.showMessageDialog(this, "Thanks for shopping at Campus Smart Cafe.");
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}

	/**
	 * @param evt
	 */
	private void dietProfileBtnActionPerformed(ActionEvent evt) {
		dietProfileScreen = new DietProfile(this.uID, this.uName);
	}

	/**
	 * @param evt
	 */
	private void buyNowBtnActionPerformed(ActionEvent evt) {
		List<FoodItemList> selectedItems = cafeItemList.getSelectedValuesList();
		int totalAmount = 0;
		int totalCalories = 0;
		int updatedRecordCount = 0;
		Boolean vegan_pref = false;
		Boolean sodium_pref = false;
		Boolean cholesterol_pref = false;
		int calorie_limit = 0;
		int calorie_consumed = 0;
		
		try {
			String query1 = "select * from CampusSmartCafe.cafe_users where user_id = " + uID;
			Statement st = LoginScreen.connection.createStatement();
			ResultSet resultSet = st.executeQuery(query1);
            
			while(resultSet.next()) {
            	vegan_pref = resultSet.getBoolean("vegan");
            	sodium_pref = resultSet.getBoolean("low_sodium");
            	cholesterol_pref = resultSet.getBoolean("low_cholesterol");
            	calorie_limit = resultSet.getInt("calories_intake");
			}
			
			query1 = "select ifnull(sum(calories), 0) as day_calories from CampusSmartCafe.cafe_orders where user_id = " + 
					  uID + " and order_date = curdate()";
			resultSet = st.executeQuery(query1);
			while(resultSet.next()) {
				calorie_consumed = resultSet.getInt("day_calories");
			}
		} catch (SQLException exception) {
			System.out.println(exception);
		}

		if (selectedItems.size() > 0) {
			for (FoodItemList item : selectedItems) {
				totalAmount += item.cafeFoodItem.itemPrice;
				totalCalories += item.cafeFoodItem.calories;
				
				/* Validations for user food preferences */
				Boolean isVegan = item.cafeFoodItem.isVegan;
				Boolean lowSodium = item.cafeFoodItem.isLowSodium;
				Boolean lowCholesterol = item.cafeFoodItem.isLowCholesterol;
				
				if (vegan_pref == true && isVegan == false) {
            		JOptionPane.showMessageDialog(this, "Item: " + item.cafeFoodItem.itemName + " is not vegan");
            	}
            	if (sodium_pref == true && lowSodium == false) {
            		JOptionPane.showMessageDialog(this, "Item: " + item.cafeFoodItem.itemName + " has high sodium");
            	}
            	if (cholesterol_pref == true && lowCholesterol == false) {
            		JOptionPane.showMessageDialog(this, "Item: " + item.cafeFoodItem.itemName + " has high cholesterol");
            	}	
			}
			
			if (calorie_limit < (calorie_consumed + totalCalories)) {
				JOptionPane.showMessageDialog(this, "You are exceeding your calories limit");
			}
			
			JOptionPane.showMessageDialog(this, "You have to pay totalAmount: " + totalAmount + ", Itemcount: " + selectedItems.size());
			String updateUserQuery = "Update CampusSmartCafe.cafe_users set calories_consumed = calories_consumed + "
					+ totalCalories + ", used_budget = used_budget + " + totalAmount + " where user_id = " + uID + ""
					+ " and used_budget < budget;";
			String insertOrderDetailsQuery = "INSERT INTO CampusSmartCafe.cafe_orders (user_id, calories, cost, order_date)" +
	                						" VALUES (" + uID + ", " + totalCalories + ", " + totalAmount + ", curdate());";
			// System.out.println(updateUserQuery);
			// System.out.println(insertOrderDetailsQuery);

			try {
				Statement st = LoginScreen.connection.createStatement();
				updatedRecordCount = st.executeUpdate(updateUserQuery);
				st.executeUpdate(insertOrderDetailsQuery);
			} catch (SQLException exception) {
				System.out.println(exception);
			}
			if (updatedRecordCount < 1) {
				
JOptionPane.showMessageDialog(this, "You are low on your available funds. Please refill your card.");
			} else {
				if (selectedCafe instanceof VendingMachine) {
					JOptionPane.showMessageDialog(this,
							"Thanks for your order. Your order items are being dispensed and ready to pick up in no time.");
				} else {
					JOptionPane.showMessageDialog(this, "Thanks for your oder. " + " Your order will be ready at "
							+ selectedCafe.cafeName + " within " + ((Cafe) selectedCafe).cafeWaitTime + " minutes");
				}
			}
		} else {
			JOptionPane.showMessageDialog(this, "Please select an item from list and try again.");
		}
	}

	/**
	 * @param evt
	 */
	private void expenseProfileBtnActionPerformed(ActionEvent evt) {
		expenseProfileScreen = new ExpenseProfile(this.uID, this.uName);
	}

	/**
	 * Listener for map points
	 */
	public class CafeListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			try {

				selectedCafe = null;

				CampusMap campusMap = (CampusMap) e.getComponent();
				for (Cafe cafe : campusMap.cafes) {
					if (Point2D.distance(e.getX(), e.getY(), cafe.getX(), cafe.getY()) < 10) {
						selectedCafe = cafe;
						break;
					}
				}
				for (VendingMachine vendingMachine : campusMap.vendingMachines) {
					if (Point2D.distance(e.getX(), e.getY(), vendingMachine.getX(), vendingMachine.getY()) < 10) {
						selectedCafe = vendingMachine;
						break;
					}
				}
				String query = "select food_item.item_name, food_item.item_price, food_item.calories, food_item.vegan, food_item.low_sodium, food_item.low_cholesterol "
						+ "from CampusSmartCafe.cafes as cafe "
						+ "join CampusSmartCafe.food_items as food_item on food_item.cafe_id = cafe.cafe_id "
						+ "where cafe.cafe_id = " + selectedCafe.getCafeID() + ";";

				try {
					Statement statement = LoginScreen.connection.createStatement();
					ResultSet resultSet = statement.executeQuery(query);

					foodItemsModel.removeAllElements();
					cafeItemList.removeAll();

					while (resultSet.next()) {
						addItemToJList(new FoodItem(resultSet.getString(1), resultSet.getInt(2), resultSet.getInt(3),
								resultSet.getBoolean(4), resultSet.getBoolean(5), resultSet.getBoolean(6)));
					}

					cafeItemList.repaint();
					mapInstructionLbl.setText("<HTML><b>" + selectedCafe.cafeName + "</b></HTML>");
				} catch (SQLException exc) {
					exc.printStackTrace();
				}
			} catch (Exception exception) {

			}
		}
	}

	/**
	 * @param item
	 */
	public void addItemToJList(FoodItem item) {
		foodItemsModel.addElement(new FoodItemList(item));
	}

	/**
	 * @return Map Panel
	 */
	public CampusMap getCampusScreen() {
		return (CampusMap) cafeMapPanel;
	}

	/**
	 * @return list of cafes (both cafe and vending machines)
	 */
	public DefaultListModel<Cafes> getCafeList() {
		return this.cafeList;
	}

}
