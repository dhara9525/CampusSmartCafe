package scu.ood.project.DietProfile;

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
import java.util.Random;

/**
 * User diet profile screen
 */
public class DietProfile extends JFrame {
    private JButton updateDietBtn;
    private JButton closeBtn;
    private JCheckBox veganCheckBox;
    private JCheckBox lowSodiumCheckBox;
    private JCheckBox lowCholCheckBox;
    private JLabel usrDietLbl;
    private JLabel dailyCalLbl;
    private JLabel veganLbl;
    private JLabel lowSodiumLbl;
    private JLabel lowCholLbl;
    private JLabel calConsumptionLbl;
    private ChartPanel pieChartPanel;
    private JTextField calIntakeTextField;
    private int uID;
    private String uName;
    private ChartPanel barChartPanel;
    private double uCaloriesConsumed;
    private JFreeChart barchart;
    private CategoryDataset barDataset;


    /**
     * constructor
     * Creates new form DietProfile
     */
    public DietProfile(int userId, String userName) {
        this.uID = userId;
        this.uName = userName;
        initComponents();
        getUserDietInfo();
    }

    /**
     * init screen components
     */
    private void initComponents() {

        usrDietLbl = new JLabel();
        dailyCalLbl = new JLabel();
        calIntakeTextField = new JTextField();
        veganLbl = new JLabel();
        veganCheckBox = new JCheckBox();
        lowSodiumLbl = new JLabel();
        lowSodiumCheckBox = new JCheckBox();
        lowCholLbl = new JLabel();
        lowCholCheckBox = new JCheckBox();
        updateDietBtn = new JButton();
        calConsumptionLbl = new JLabel();
        closeBtn = new JButton();

        PieDataset pieDataset = createDataset();
        JFreeChart piechart = createChart(pieDataset, "Percentage distribution by amount of calories spent over last 7 days");
        pieChartPanel = new ChartPanel(piechart);

        barDataset = createBarDataset();
        barchart = createChart(barDataset);
        barChartPanel = new ChartPanel(barchart);

        pieChartPanel.setPreferredSize(new Dimension(300, 270));
        barChartPanel.setPreferredSize(new Dimension(300, 270));

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(250, 80, 500, 300);

        usrDietLbl.setText("<html><b>" + this.uName + "'s Diet</b></html>");

        dailyCalLbl.setText("Daily Calories ");

        veganLbl.setText("Vegan");

        lowSodiumLbl.setText("Low Sodium");

        lowCholLbl.setText("Low Cholesterol");

        updateDietBtn.setText("Update your diet! ");
        updateDietBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                updateDietBtnActionPerformed(evt);
            }
        });

        calConsumptionLbl.setText("Today's calories consumed: ");

        GroupLayout pieChartPanelLayout = new GroupLayout(pieChartPanel);
        pieChartPanel.setLayout(pieChartPanelLayout);
        pieChartPanelLayout.setHorizontalGroup(
                pieChartPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 295, Short.MAX_VALUE)
        );
        pieChartPanelLayout.setVerticalGroup(
                pieChartPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );

        GroupLayout barChartPanelLayout = new GroupLayout(barChartPanel);
        barChartPanel.setLayout(barChartPanelLayout);
        barChartPanelLayout.setHorizontalGroup(
                barChartPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 270, Short.MAX_VALUE)
        );
        barChartPanelLayout.setVerticalGroup(
                barChartPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 252, Short.MAX_VALUE)
        );

        closeBtn.setText("Close");
        closeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                closeBtnActionPerformed(evt);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(27, 27, 27)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(usrDietLbl, GroupLayout.PREFERRED_SIZE, 235, GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addComponent(dailyCalLbl)
                                                                        .addComponent(veganLbl)
                                                                        .addComponent(lowSodiumLbl)
                                                                        .addComponent(lowCholLbl))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(lowCholCheckBox, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(lowSodiumCheckBox, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(veganCheckBox, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(calIntakeTextField)))
                                                        .addComponent(calConsumptionLbl)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(updateDietBtn)
                                                .addGap(18, 18, 18)
                                                .addComponent(closeBtn)))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                              //  .addGap(18,18,18)
                                .addComponent(pieChartPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(barChartPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(usrDietLbl, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(calConsumptionLbl)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(dailyCalLbl, GroupLayout.Alignment.TRAILING)
                                        .addComponent(calIntakeTextField, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(veganLbl, GroupLayout.Alignment.TRAILING)
                                        .addComponent(veganCheckBox, GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lowSodiumLbl)
                                        .addComponent(lowSodiumCheckBox))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lowCholLbl)
                                        .addComponent(lowCholCheckBox))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(updateDietBtn)
                                        .addComponent(closeBtn))
                                .addGap(45, 45, 45))
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(barChartPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(pieChartPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );

        pack();
        this.setVisible(true);
    }

    /**
     * gets user diet info from DB
     */
    private void getUserDietInfo() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                String userDietQuery = "SELECT calories_intake, calories_consumed, vegan, low_sodium,low_cholesterol " +
                        "from CampusSmartCafe.cafe_users where user_id = " + uID;

                try {
                    Statement statement = LoginScreen.connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(userDietQuery);
                    while (resultSet.next()) {
                        calIntakeTextField.setText(resultSet.getString(1));
                        calConsumptionLbl.setText(calConsumptionLbl.getText() + resultSet.getString(2));
                        uCaloriesConsumed = resultSet.getDouble(2);
                        veganCheckBox.setSelected(resultSet.getBoolean(3));
                        lowSodiumCheckBox.setSelected(resultSet.getBoolean(4));
                        lowCholCheckBox.setSelected(resultSet.getBoolean(5));
                        
                    }
                } catch (SQLException exception) {
                    System.out.println(exception);
                }
            }
        });
    }

    /**
     * @param evt
     */
    private void updateDietBtnActionPerformed(ActionEvent evt) {
        setUserDietInfo();
    }

    /**
     * @return diet pie chart dataset
     */
    private  PieDataset createDataset() {
        DefaultPieDataset result = new DefaultPieDataset();
        for (int i = 0; i < 7; i++) {
        	String userExpenseQuery = "SELECT ifnull(sum(calories), 0) as day_calories, DATE_ADD(curdate(), INTERVAL - " + i + " DAY) " + 
        								" as order_date FROM CampusSmartCafe.cafe_orders WHERE user_id = " 
        								+ uID + " and order_date = DATE_ADD(curdate(), INTERVAL - " + i + " DAY) ";
        	
        	try {
                Statement statement = LoginScreen.connection.createStatement();
                ResultSet resultSet = statement.executeQuery(userExpenseQuery);
                while (resultSet.next()) {
                	result.setValue(resultSet.getString("order_date"), Integer.parseInt(resultSet.getString("day_calories")));
                }
            } catch (SQLException exception) {
                System.out.println(exception);
            }
        }
        return result;

    }

    /**
     * @param dataset
     * @param title
     * @return diet pie chart
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
     * @return diet bar chart dataset
     */
    private CategoryDataset createBarDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        for (int i = 0; i < 7; i++) {
        	String userExpenseQuery = "SELECT ifnull(sum(calories), 0) as day_calories, DATE_ADD(curdate(), INTERVAL - " + i + " DAY) " + 
        								" as order_date FROM CampusSmartCafe.cafe_orders WHERE user_id = " 
        								+ uID + " and order_date = DATE_ADD(curdate(), INTERVAL - " + i + " DAY) ";
        	
        	try {
                Statement statement = LoginScreen.connection.createStatement();
                ResultSet resultSet = statement.executeQuery(userExpenseQuery);
                while (resultSet.next()) {
                	// System.out.println("day_calories: " + resultSet.getString("day_calories") + " , order_date: " + resultSet.getString("order_date"));	
                	dataset.addValue(Integer.parseInt(resultSet.getString("day_calories")), "Day", resultSet.getString("order_date"));

                }
            } catch (SQLException exception) {
                System.out.println(exception);
            }
        }
        
        return dataset;
    }

    /**
     * @param dataset
     * @return diet bar chart object
     */
    private JFreeChart createChart(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                "Calories consumtion over last 7 days", null /* x-axis label*/,
                "Calories" /* y-axis label */, dataset);
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
     * saves user diet preferences to DB
     */
    private void setUserDietInfo() {
        int calories_intake = Integer.parseInt(calIntakeTextField.getText());
        int vegan = veganCheckBox.isSelected() ? 1 : 0;
        int low_sodium = lowSodiumCheckBox.isSelected() ? 1 : 0;
        int low_chol = lowCholCheckBox.isSelected() ? 1 : 0;

        final String updateUserDietQuery = "UPDATE `CampusSmartCafe`.`cafe_users` " +
                "SET `calories_intake`=" + calories_intake + ", `vegan`=" + vegan + "," +
                " `low_sodium`=" + low_sodium + ", `low_cholesterol`=" + low_chol + " WHERE `user_id`=" + this.uID + ";";

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Statement statement = LoginScreen.connection.createStatement();
                    statement.execute(updateUserDietQuery);
                    JOptionPane.showMessageDialog(getDietScreen(), "Your diet preferences have been saved successfully!");
                } catch (SQLException exception) {
                    System.out.println(exception);
                }
            }
        });
    }

    /**
     * @param evt
     */
    private void closeBtnActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    /**
     * @return diet screen object
     */
    public DietProfile getDietScreen() {
        return this;
    }

}
