package scu.ood.project.ExpenseProfile;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

import scu.ood.project.CafeClasses.LoginScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Expense profile screen
 */
public class ExpenseProfile extends JFrame {
    private JButton updateWalletBtn;
    private JButton closeBtn;
    private JLabel userWalletLbl;
    private JLabel monthlyBudgetUsedLbl;
    private JLabel monthlyBudgetLbl;
    private ChartPanel expensePieChartPanel;
    private ChartPanel expenseBarChartPanel;
    private JFreeChart barchart;
    private CategoryDataset barDataset;
    private JTextField monthlyBudgetTextField;
    private int uID;
    private String uName;

    /**
     * constructor
     * Creates new form ExpenseProfile
     */
    public ExpenseProfile(int userId, String userName) {
        this.uID = userId;
        this.uName = userName;
        initComponents();
        getUserExpenseProfile();
    }

    /**
     * init screen components
     */
    private void initComponents() {

        userWalletLbl = new JLabel();
        monthlyBudgetUsedLbl = new JLabel();
        monthlyBudgetLbl = new JLabel();
        monthlyBudgetTextField = new JTextField();
        updateWalletBtn = new JButton();
        closeBtn = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(250, 80, 500, 300);

        userWalletLbl.setText("<HTML><b>" + this.uName + "'s wallet</b></HTML>");

        monthlyBudgetUsedLbl.setText("Monthly budget used as of today:  ");

        monthlyBudgetLbl.setText("Monthly Budget ($)");

        updateWalletBtn.setText("Update wallet");
        updateWalletBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                updateWalletBtnActionPerformed(evt);
            }
        });

        closeBtn.setText("Close");
        closeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                closeBtnActionPerformed(evt);
            }
        });

        PieDataset pieDataset = createDataset();
        JFreeChart piechart = createChart(pieDataset, "Spending percentage over last 7 days");
        expensePieChartPanel = new ChartPanel(piechart);
        expensePieChartPanel.setPreferredSize(new Dimension(300, 270));

        GroupLayout expensePieChartPanelLayout = new GroupLayout(expensePieChartPanel);
        expensePieChartPanel.setLayout(expensePieChartPanelLayout);
        expensePieChartPanelLayout.setHorizontalGroup(
                expensePieChartPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 262, Short.MAX_VALUE)
        );
        expensePieChartPanelLayout.setVerticalGroup(
                expensePieChartPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );

        barDataset = createBarDataset();
        barchart = createChart(barDataset);
        expenseBarChartPanel = new ChartPanel(barchart);
        expenseBarChartPanel.setPreferredSize(new Dimension(300, 270));

        GroupLayout expenseBarChartPanelLayout = new GroupLayout(expenseBarChartPanel);
        expenseBarChartPanel.setLayout(expenseBarChartPanelLayout);
        expenseBarChartPanelLayout.setHorizontalGroup(
                expenseBarChartPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 270, Short.MAX_VALUE)
        );
        expenseBarChartPanelLayout.setVerticalGroup(
                expenseBarChartPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 252, Short.MAX_VALUE)
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                .addComponent(userWalletLbl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(monthlyBudgetUsedLbl)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(monthlyBudgetLbl)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(monthlyBudgetTextField)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(updateWalletBtn)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(closeBtn)))
                                .addGap(18, 18, 18)
                                .addComponent(expensePieChartPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(expenseBarChartPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(expenseBarChartPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(expensePieChartPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(userWalletLbl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(monthlyBudgetUsedLbl)
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(monthlyBudgetLbl)
                                                        .addComponent(monthlyBudgetTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                .addGap(19, 19, 19)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(updateWalletBtn)
                                                        .addComponent(closeBtn))))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        this.setVisible(true);
    }

    /**
     * returns user budget info from DB
     */
    private void getUserExpenseProfile() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                String userExpenseQuery = "SELECT budget, used_budget " +
                        "FROM CampusSmartCafe.cafe_users WHERE user_id = " + uID;
                try {
                    Statement statement = LoginScreen.connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(userExpenseQuery);
                    while (resultSet.next()) {
                        monthlyBudgetTextField.setText(resultSet.getString(1));
                        monthlyBudgetUsedLbl.setText(monthlyBudgetUsedLbl.getText() + "$" + resultSet.getString(2));
                    }
                } catch (SQLException exception) {
                    System.out.println(exception);
                }
            }
        });
    }

    /**
     * @param dataset
     * @param title
     * @return pie chart object for budget profile
     */
    private JFreeChart createChart(PieDataset dataset, String title) {

        JFreeChart chart = ChartFactory.createPieChart3D(title,
                dataset,
                true,
                true,
                false);

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        chart.setBackgroundPaint(this.getBackground());
        return chart;

    }

    /**
     * @return pie chart datasets
     */
    private  PieDataset createDataset() {
        DefaultPieDataset result = new DefaultPieDataset();
        
        for (int i = 0; i < 7; i++) {
        	String userExpenseQuery = "SELECT ifnull(sum(cost), 0) as day_cost, DATE_ADD(curdate(), INTERVAL - " + i + " DAY) " + 
        								" as order_date FROM CampusSmartCafe.cafe_orders WHERE user_id = " 
        								+ uID + " and order_date = DATE_ADD(curdate(), INTERVAL - " + i + " DAY) ";
        	
        	try {
                Statement statement = LoginScreen.connection.createStatement();
                ResultSet resultSet = statement.executeQuery(userExpenseQuery);
                while (resultSet.next()) {
                	result.setValue(resultSet.getString("order_date"), Integer.parseInt(resultSet.getString("day_cost")));
                }
            } catch (SQLException exception) {
                System.out.println(exception);
            }
        }
      
        return result;

    }

    /**
     * @return bar chart datasets
     */
    private CategoryDataset createBarDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        for (int i = 0; i < 7; i++) {
        	String userExpenseQuery = "SELECT ifnull(sum(cost), 0) as day_cost, DATE_ADD(curdate(), INTERVAL - " + i + " DAY) " + 
        								" as order_date FROM CampusSmartCafe.cafe_orders WHERE user_id = " 
        								+ uID + " and order_date = DATE_ADD(curdate(), INTERVAL - " + i + " DAY) ";
        	
        	try {
                Statement statement = LoginScreen.connection.createStatement();
                ResultSet resultSet = statement.executeQuery(userExpenseQuery);
                while (resultSet.next()) {
                	//System.out.println("day_cost: " + resultSet.getString("day_cost") + " , order_date: " + resultSet.getString("order_date"));
                	dataset.addValue(Integer.parseInt(resultSet.getString("day_cost")), "Day", resultSet.getString("order_date"));
                }
            } catch (SQLException exception) {
                System.out.println(exception);
            }
        }
        
        return dataset;
    }

    /**
     * @param dataset
     * @return budget bar chart
     */
    private JFreeChart createChart(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                "Spending over last 7 days", null /* x-axis label*/,
                "$$$ Spent" /* y-axis label */, dataset);
        chart.setBackgroundPaint(this.getBackground());
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        chart.getLegend().setFrame(BlockBorder.NONE);
        return chart;
    }

    /**
     * saves user budget info to DB
     */
    private void setUserExpenseProfile() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                String updateUserExpenseQuery = "UPDATE CampusSmartCafe.cafe_users SET budget = " + Integer.parseInt(monthlyBudgetTextField.getText()) +
                        " WHERE user_id = " + uID;
                try {
                    Statement statement = LoginScreen.connection.createStatement();
                    statement.execute(updateUserExpenseQuery);
                    JOptionPane.showMessageDialog(getExpenseScreen(), "Your wallet preferences have been saved successfully!");
                } catch (SQLException exception) {
                    System.out.println(exception);
                }

            }
        });
    }

    /**
     * @return expense screen object
     */
    public ExpenseProfile getExpenseScreen() {
        return this;
    }

    /**
     * @param evt
     */
    private void updateWalletBtnActionPerformed(ActionEvent evt) {
        setUserExpenseProfile();
    }

    /**
     * @param evt
     */
    private void closeBtnActionPerformed(ActionEvent evt) {
        this.dispose();
    }

}
