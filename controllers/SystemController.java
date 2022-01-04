package toyota_app.controllers;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import toyota_app.db.DatabaseConnector;
import toyota_app.model.Account;
import toyota_app.model.Client;
import toyota_app.model.ClientTableInfo;
import toyota_app.queries.Query;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SystemController implements Initializable {

    // Car menu

    @FXML private MenuItem infoCar_menuItem;

    @FXML private MenuItem orderCar_menuItem;

    // Client menu

    @FXML private MenuItem infoClient_menuItem;

    // Sale menu

    @FXML private MenuItem chartSale_menuItem;

    @FXML private MenuItem createSale_menuItem;

    @FXML private MenuItem infoSale_menuItem;

    // Dealership menu

    @FXML private MenuItem accounting_menuItem;

    @FXML private MenuItem system_menuItem;

    @FXML private MenuItem managers_menuItem;

    @FXML private MFXTextField budgetField;

    @FXML private MFXTextField loginField;

    @FXML private MFXTextField numCarsField;

    @FXML private MFXTextField numUsersField;

    @FXML private MFXTextField passwordField;

    @FXML private MFXTextField nameField;

    @FXML private MFXTextField surnameField;

    @FXML private MFXTextField idManagerField;

    @FXML private Label hello_user;

    @FXML private TableColumn<Account, Integer> idUser;

    @FXML private TableColumn<Account, String> loginUser;

    @FXML private TableColumn<Account, String> nameUser;

    @FXML private TableColumn<Account, String> passwordUser;

    @FXML private TableColumn<Account, String> surnameUser;

    @FXML private TableView<Account> table;

    @FXML private LineChart<String, BigDecimal> chartBudget;

    private Account account;

    private ObservableList<Account> listAccounts = FXCollections.observableArrayList();

    private void updateTable() {
        idUser.setCellValueFactory( new PropertyValueFactory<Account, Integer>("idUser"));
        surnameUser.setCellValueFactory( new PropertyValueFactory<Account, String>("surnameUser"));
        nameUser.setCellValueFactory( new PropertyValueFactory<Account, String>("nameUser"));
        loginUser.setCellValueFactory( new PropertyValueFactory<Account, String>("loginUser"));
        passwordUser.setCellValueFactory( new PropertyValueFactory<Account, String>("passwordUser"));

        listAccounts = DatabaseConnector.getAccountData();

        table.setItems(listAccounts);
    }

    @FXML
    void addUser(ActionEvent event) {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        Connection connection = databaseConnector.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(Query.insertAccount);
            statement.setInt(1, Integer.valueOf(idManagerField.getText()));
            statement.setString(2, surnameField.getText());
            statement.setString(3, nameField.getText());
            statement.setString(4, loginField.getText());
            statement.setString(5, passwordField.getText());
            int insert = statement.executeUpdate();
            updateTable();
            Alert alertUpdate = new Alert(Alert.AlertType.INFORMATION);
            alertUpdate.setContentText("Данные добавлены!");
            alertUpdate.show();
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alertUpdate = new Alert(Alert.AlertType.INFORMATION);
            alertUpdate.setContentText("Менеджера под номером " + idManagerField.getText() + " не существует");
            alertUpdate.show();
        } finally {
            connection = databaseConnector.closeConnection();
        }
    }

    @FXML
    void updateUser(ActionEvent event) {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        Connection connection = databaseConnector.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(Query.updateAccount);
            statement.setString(1, surnameField.getText());
            statement.setString(2, nameField.getText());
            statement.setString(3, loginField.getText());
            statement.setString(4, passwordField.getText());
            statement.setInt(5, account.getIdUser());
            int update = statement.executeUpdate();
            updateTable();
            System.out.println(update);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection = databaseConnector.closeConnection();
            Alert alertUpdate = new Alert(Alert.AlertType.INFORMATION);
            alertUpdate.setContentText("Данные обновленны");
            alertUpdate.show();
        }
    }

    @FXML
    void deleteUser(ActionEvent event) {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        Connection connection = databaseConnector.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(Query.deleteAccount);
            statement.setInt(1, account.getIdUser());
            int delete = statement.executeUpdate();
            Alert alertUpdate = new Alert(Alert.AlertType.WARNING);
            alertUpdate.setContentText("Данные удалены!");
            alertUpdate.show();
            updateTable();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection = databaseConnector.closeConnection();
        }
    }

    @FXML
    void clearUser(ActionEvent event) {
        idManagerField.setText("");
        surnameField.setText("");
        nameField.setText("");
        loginField.setText("");
        passwordField.setText("");
    }

    @FXML
    public void infoClient(ActionEvent event) {
        Stage stage = (Stage) ScreenController.scene.getWindow();
        ScreenController.change(stage, "client-info.fxml");
    }

    @FXML
    private void infoManagers(ActionEvent event) {
        Stage stage = (Stage) ScreenController.scene.getWindow();
        ScreenController.change(stage, "manager-info.fxml");
    }

    @FXML
    public void createSale(ActionEvent event) {
        Stage stage = (Stage) ScreenController.scene.getWindow();
        ScreenController.change(stage, "car-sale.fxml");
    }

    @FXML
    public void chartSale(ActionEvent event) {
        Stage stage = (Stage) ScreenController.scene.getWindow();
        ScreenController.change(stage, "chart.fxml");
    }

    @FXML
    public void infoSale(ActionEvent event) {
        Stage stage = (Stage) ScreenController.scene.getWindow();
        ScreenController.change(stage, "sale-info.fxml");
    }

    @FXML
    public void accounting(ActionEvent event) {
        Stage stage = (Stage) ScreenController.scene.getWindow();
        ScreenController.change(stage, "accounting.fxml");
    }

    @FXML
    public void system(ActionEvent event) {
        Stage stage = (Stage) ScreenController.scene.getWindow();
        ScreenController.change(stage, "system.fxml");
    }

    @FXML
    public void home(ActionEvent event) {
        Stage stage = (Stage) ScreenController.scene.getWindow();
        ScreenController.change(stage, "main-page.fxml");
    }

    @FXML
    public void exit(ActionEvent event) {
        Stage stage = (Stage) ScreenController.scene.getWindow();
        ScreenController.change(stage, "login.fxml");
    }

    @FXML
    public void infoCar(ActionEvent event) {
        Stage stage = (Stage) ScreenController.scene.getWindow();
        ScreenController.change(stage, "car-available.fxml");
    }

    @FXML
    public void orderCar(ActionEvent event) {
        Stage stage = (Stage) ScreenController.scene.getWindow();
        ScreenController.change(stage, "car-select.fxml");
    }

    private void insertTableInfo() {
        idUser.setCellValueFactory( new PropertyValueFactory<Account, Integer>("idUser"));
        surnameUser.setCellValueFactory( new PropertyValueFactory<Account, String>("surnameUser"));
        nameUser.setCellValueFactory( new PropertyValueFactory<Account, String>("nameUser"));
        loginUser.setCellValueFactory( new PropertyValueFactory<Account, String>("loginUser"));
        passwordUser.setCellValueFactory( new PropertyValueFactory<Account, String>("passwordUser"));

        table.setItems(listAccounts);

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

                listAccounts.add(new Account(id, surname, name, login, password));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setValueFromTable() {
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Account temp = table.getItems().get(table.getSelectionModel().getSelectedIndex());

                DatabaseConnector databaseConnector = new DatabaseConnector();
                Connection connection = databaseConnector.getConnection();

                try {
                    PreparedStatement statement = connection.prepareStatement(Query.getAccount);
                    statement.setInt(1, temp.getIdUser());
                    ResultSet queryResult = statement.executeQuery();

                    while (queryResult.next()) {
                        int id = queryResult.getInt(1);
                        String sername = queryResult.getString(2);
                        String name = queryResult.getString(3);
                        String login = queryResult.getString(4);
                        String password = queryResult.getString(5);

                        account = new Account(id, sername, name, login, password);
                        setAccountInfo();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    connection = databaseConnector.closeConnection();
                }
            }
        });
    }

    private void setAccountInfo() {
        idManagerField.setText(String.valueOf(account.getIdUser()));
        surnameField.setText(account.getSurnameUser());
        nameField.setText(account.getNameUser());
        loginField.setText(account.getLoginUser());
        passwordField.setText(account.getPasswordUser());
    }

    private void initSystemData() {
        updateNumCars();
        updateNumAccounts();

        DatabaseConnector databaseConnector = new DatabaseConnector();
        Connection connection = databaseConnector.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(Query.getSystemData);
            ResultSet queryResult = statement.executeQuery();

            while (queryResult.next()) {
                budgetField.setText(queryResult.getString(1) + "₴");
                numCarsField.setText(queryResult.getString(2));
                numUsersField.setText(queryResult.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection = databaseConnector.closeConnection();
        }
    }

    private void updateNumCars() {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        Connection connection = databaseConnector.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(Query.updateNumCars);
            int update = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection = databaseConnector.closeConnection();
        }
    }

    private void updateNumAccounts() {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        Connection connection = databaseConnector.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(Query.updateNumAccounts);
            int update = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection = databaseConnector.closeConnection();
        }
    }

    private void initChart() {
        XYChart.Series<String, BigDecimal> series = new XYChart.Series<String, BigDecimal>();

        DatabaseConnector databaseConnector = new DatabaseConnector();
        Connection connection = databaseConnector.getConnection();

        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(Query.getBudgetChart);
            ResultSet queryResult = statement.executeQuery();

            while (queryResult.next()) {
                series.getData().add(new XYChart.Data<String, BigDecimal>(String.valueOf(queryResult.getInt(1)), queryResult.getBigDecimal(2)));
            }

            series.setName("Бюджет автосалона");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection = databaseConnector.closeConnection();
            chartBudget.getData().add(series);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hello_user.setText("Добро пожаловать, " + LoginController.nameUser);

        initSystemData();
        initChart();

        if (LoginController.position.equals("Менеджер")) {
            orderCar_menuItem.setDisable(true);
            chartSale_menuItem.setDisable(true);
            accounting_menuItem.setDisable(true);
            managers_menuItem.setDisable(true);
            system_menuItem.setDisable(true);
        }

        insertTableInfo();
        setValueFromTable();
    }
}
