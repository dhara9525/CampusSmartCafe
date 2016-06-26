package scu.ood.project.database;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;

public class InitiateDB {
    private String connString = "jdbc:mysql://localhost/CampusSmartCafe?user=sqluser&password=sqluserpw";
    private String dbClassName = "com.mysql.jdbc.Driver";
    Connection connection = null;

    public InitiateDB() {

    }

    public void fillDBwithSampleData() {
        try {
            if (connection.isClosed()) {
                connection = DriverManager.getConnection(connString);
            }
            insertUsers();
            insertCafes();
            insertFoodItems();
            insertCafeOrders();
            closeConnection();
        } catch (Exception exception) {
            System.out.println(exception);

        }

    }

    private void insertUsers() throws IOException, SQLException {
        String insertUserQuery = "INSERT INTO `CampusSmartCafe`.`cafe_users` (`user_name`, `password`, `calories_intake`, `calories_consumed`, `vegan`, `low_sodium`, `low_cholesterol`, `budget`, `used_budget`)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

        PreparedStatement insertUserPreparedStatement;

        insertUserPreparedStatement = connection.prepareStatement(insertUserQuery);

        for (String line : Files.readAllLines(Paths.get("./dbData/cafeUsers.csv"), Charset.defaultCharset())) {
            int count = 1;
            for (String part : line.split(",+")) {
                insertUserPreparedStatement.setString(count, part);
                count++;
            }
            insertUserPreparedStatement.execute();
        }
    }

    private void insertCafes() throws SQLException, IOException {
        String insertCafeQuery = "INSERT INTO `CampusSmartCafe`.`cafes` (`cafe_name`, `vending_machine`, `cafe_wait_time`, `cafe_coordinate_x`, `cafe_coordinate_y`) " +
                "VALUES (?, ?, ?, ?, ?);";

        PreparedStatement insertCafesPreparedStatement;

        insertCafesPreparedStatement = connection.prepareStatement(insertCafeQuery);

        for (String line : Files.readAllLines(Paths.get("./dbData/cafes.csv"), Charset.defaultCharset())) {
            int count = 1;
            for (String part : line.split(",+")) {
                try {
                    int num = Integer.parseInt(part);
                    insertCafesPreparedStatement.setInt(count, num);
                } catch (Exception e) {
                    insertCafesPreparedStatement.setString(count, part);
                }
                count++;
            }
            insertCafesPreparedStatement.execute();
        }
    }

    private void insertFoodItems() throws SQLException, IOException {
        String insertFoodItemQuery = "INSERT INTO `CampusSmartCafe`.`food_items` (`item_name`, `item_price`, `calories`, `cafe_id`, `vegan`, `low_sodium`, `low_cholesterol`)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?);";

        PreparedStatement insertFoodItemPreparedStatement;

        insertFoodItemPreparedStatement = connection.prepareStatement(insertFoodItemQuery);
        for (String line : Files.readAllLines(Paths.get("./dbData/foodItems.csv"), Charset.defaultCharset())) {
            int count = 1;
            for (String part : line.split(",+")) {
                insertFoodItemPreparedStatement.setString(count, part);
                count++;
            }
            insertFoodItemPreparedStatement.execute();
        }

    }
    
    private void insertCafeOrders() throws SQLException, IOException {
    	String insertOrderDetailsQuery = "INSERT INTO `CampusSmartCafe`.`cafe_orders` (`user_id`, `calories`, `cost`, `order_date`)" +
                " VALUES (?, ?, ?, ?);";

        PreparedStatement insertOrderDetailsPreparedStatement;

        insertOrderDetailsPreparedStatement = connection.prepareStatement(insertOrderDetailsQuery);
        for (String line : Files.readAllLines(Paths.get("./dbData/cafeOrders.csv"), Charset.defaultCharset())) {
            int count = 1;
            for (String part : line.split(",+")) {
            	insertOrderDetailsPreparedStatement.setString(count, part);
                count++;
            }
            insertOrderDetailsPreparedStatement.execute();
        }
    }

    public void createDB() {

        ArrayList<String> createQueries = new ArrayList<String>();

        createQueries.add("DROP DATABASE CampusSmartCafe;");

        createQueries.add("CREATE DATABASE CampusSmartCafe;");

        createQueries.add("USE CampusSmartCafe;");

        createQueries.add("CREATE TABLE `CampusSmartCafe`.`cafe_users` (\n" +
                "  `user_id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `user_name` VARCHAR(128) NOT NULL,\n" +
                "  `password` VARCHAR(45) NOT NULL,\n" +
                "  `calories_intake` INT NULL,\n" +
                "  `calories_consumed` INT NULL,\n" +
                "  `vegan` INT NULL,\n" +
                "  `low_sodium` INT NULL,\n" +
                "  `low_cholesterol` INT NULL,\n" +
                "  `budget` INT NULL,\n" +
                "  `used_budget` INT NULL,\n" +
                "  PRIMARY KEY (`user_id`));");

        createQueries.add("CREATE TABLE `CampusSmartCafe`.`cafes` (\n" +
                "  `cafe_id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `cafe_name` VARCHAR(128) NOT NULL,\n" +
                "  `vending_machine` INT NULL,\n" +
                "  `cafe_wait_time` INT NULL,\n" +
                "  `cafe_coordinate_x` INT NULL,\n" +
                "  `cafe_coordinate_y` INT NULL,\n" +
                "  PRIMARY KEY (`cafe_id`));");

        createQueries.add("CREATE TABLE `CampusSmartCafe`.`food_items` (\n" +
                "  `item_id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `item_name` VARCHAR(128) NOT NULL,\n" +
                "  `item_price` INT NULL,\n" +
                "  `calories` INT NULL,\n" +
                "  `cafe_id` INT NULL,\n" +
                "  `vegan` INT NULL,\n" +
                "  `low_sodium` INT NULL,\n" +
                "  `low_cholesterol` INT NULL,\n" +
                "  PRIMARY KEY (`item_id`));");
        
        createQueries.add("CREATE TABLE `CampusSmartCafe`.`cafe_orders` (\n" +
                "  `order_id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `user_id` INT NOT NULL,\n" +
                "  `calories` INT NOT NULL,\n" +
                "  `cost` INT NOT NULL,\n" + 
                "  `order_date` DATE NOT NULL,\n" +
                "  `order_time` TIMESTAMP NOT NULL default now(),\n" +
                "  PRIMARY KEY (`order_id`));");

        try {
            Class.forName(this.dbClassName);
            connection = DriverManager.getConnection(connString);

            Statement createStatement = connection.createStatement();

            for (String query : createQueries) {
                createStatement.executeUpdate(query);
            }

            createStatement.close();
            closeConnection();
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed())
                connection.close();
        } catch (Exception exception) {
            System.out.println("Something went wrong! Can not close connection.");
        }
    }

    public static void main(String[] args) {
        InitiateDB initializer = new InitiateDB();
        initializer.createDB();
        initializer.fillDBwithSampleData();
    }
}
