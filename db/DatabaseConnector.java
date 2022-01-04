package toyota_app.db;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import toyota_app.model.*;
import toyota_app.queries.Query;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.sql.*;

public class DatabaseConnector {
    public static final String DB_FILE = "Dealership";
    public static final String DB_URL = "jdbc:sqlserver://localhost:1433;database=" + DB_FILE;
    private static  final String DB_USERNAME = "sa";
    private static final String DB_PASSWORD = "1202307";

    private static Connection connection;

    public Connection getConnection() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("База данных подключена!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка подключения базы данных");
        }

        return connection;
    }

    public Connection closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static ObservableList<CarTableInfo> getCarData(String carModel) {
        ObservableList<CarTableInfo> list = FXCollections.observableArrayList();
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            PreparedStatement statement = connection.prepareStatement(Query.getInfoDealeashipCars);
            statement.setString(1, carModel);
            ResultSet queryResult = statement.executeQuery();

            while (queryResult.next()) {
                int id = queryResult.getInt(1);
                String brand = queryResult.getString(2);
                String model = queryResult.getString(3);
                String year = queryResult.getString(4);
                String equipment = queryResult.getString(5);
                String color = queryResult.getString(6);
                BigDecimal price = queryResult.getBigDecimal(7);

                list.add(new CarTableInfo(id, brand, model, year, equipment, color, price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static ObservableList<ClientTableInfo> getClientData() {
        ObservableList<ClientTableInfo> list = FXCollections.observableArrayList();
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            PreparedStatement statement = connection.prepareStatement(Query.getClients);
            ResultSet queryResult = statement.executeQuery();

            while (queryResult.next()) {
                int id = queryResult.getInt(1);
                String sername = queryResult.getString(2);
                String name = queryResult.getString(3);
                String patronymic = queryResult.getString(4);
                String city = queryResult.getString(5);
                String passport = queryResult.getString(6);
                String phone = queryResult.getString(7);
                list.add(new ClientTableInfo(id, sername, name, patronymic, city, passport, phone));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static ObservableList<ManagerTableInfo> getManagerData() {
        ObservableList<ManagerTableInfo> list = FXCollections.observableArrayList();
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            PreparedStatement statement = connection.prepareStatement(Query.getManagers);
            ResultSet queryResult = statement.executeQuery();

            while (queryResult.next()) {
                int id = queryResult.getInt(1);
                String sername = queryResult.getString(2);
                String name = queryResult.getString(3);
                String patronymic = queryResult.getString(4);
                String position = queryResult.getString(5);
                String salary = queryResult.getString(6);
                String numOfSales = queryResult.getString(7);
                list.add(new ManagerTableInfo(id, sername, name, patronymic, position, salary, numOfSales));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static ObservableList<Account> getAccountData() {
        ObservableList<Account> list = FXCollections.observableArrayList();
        DatabaseConnector databaseConnector = new DatabaseConnector();
        Connection connection = databaseConnector.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(Query.getAccounts);
            ResultSet queryResult = statement.executeQuery();

            while (queryResult.next()) {
                int id = queryResult.getInt(1);
                String surname = queryResult.getString(2);
                String name = queryResult.getString(3);
                String login = queryResult.getString(4);
                String password = queryResult.getString(5);

                list.add(new Account(id, surname, name, login, password));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


}
