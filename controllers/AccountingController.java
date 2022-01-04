package toyota_app.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import toyota_app.db.DatabaseConnector;
import toyota_app.model.AccountingTableInfo;
import toyota_app.model.ClientTableInfo;
import toyota_app.model.ManagerTableInfo;
import toyota_app.queries.Query;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AccountingController implements Initializable {

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

    @FXML private Label hello_user;

    @FXML private TableColumn<AccountingTableInfo, Integer> idDelivery;

    @FXML private TableColumn<AccountingTableInfo, Integer> idOperation;

    @FXML private TableColumn<AccountingTableInfo, Integer> idSale;

    @FXML private MFXTextField searchField;

    @FXML private TableColumn<AccountingTableInfo, BigDecimal> sum;

    @FXML private TableColumn<AccountingTableInfo, BigDecimal> currentBudget;

    @FXML private TableView<AccountingTableInfo> table;

    @FXML private TableColumn<AccountingTableInfo, String> typeOperation;

    ObservableList<AccountingTableInfo> listTableInfo = FXCollections.observableArrayList();

    @FXML
    private void infoClient(ActionEvent event) {
        Stage stage = (Stage) ScreenController.scene.getWindow();
        ScreenController.change(stage, "client-info.fxml");
    }

    @FXML
    private void infoCar(ActionEvent event) {
        Stage stage = (Stage) ScreenController.scene.getWindow();
        ScreenController.change(stage, "car-available.fxml");
    }


    @FXML
    private void infoManagers(ActionEvent event) {
        Stage stage = (Stage) ScreenController.scene.getWindow();
        ScreenController.change(stage, "manager-info.fxml");
    }

    @FXML
    private void createSale(ActionEvent event) {
        Stage stage = (Stage) ScreenController.scene.getWindow();
        ScreenController.change(stage, "car-sale.fxml");
    }

    @FXML
    private void chartSale(ActionEvent event) {
        Stage stage = (Stage) ScreenController.scene.getWindow();
        ScreenController.change(stage, "chart.fxml");
    }

    @FXML
    private void infoSale(ActionEvent event) {
        Stage stage = (Stage) ScreenController.scene.getWindow();
        ScreenController.change(stage, "sale-info.fxml");
    }

    @FXML
    private void accounting(ActionEvent event) {
        Stage stage = (Stage) ScreenController.scene.getWindow();
        ScreenController.change(stage, "accounting.fxml");
    }

    @FXML
    private void system(ActionEvent event) {
        Stage stage = (Stage) ScreenController.scene.getWindow();
        ScreenController.change(stage, "system.fxml");
    }

    @FXML
    private void home(ActionEvent event) {
        Stage stage = (Stage) ScreenController.scene.getWindow();
        ScreenController.change(stage, "main-page.fxml");
    }

    @FXML
    private void exit(ActionEvent event) {
        Stage stage = (Stage) ScreenController.scene.getWindow();
        ScreenController.change(stage, "login.fxml");
    }

    @FXML
    private void orderCar(ActionEvent event) {
        Stage stage = (Stage) ScreenController.scene.getWindow();
        ScreenController.change(stage, "car-select.fxml");
    }

    private void insertDataIntoTable() {
        idOperation.setCellValueFactory(new PropertyValueFactory<AccountingTableInfo, Integer>("idOperation"));
        typeOperation.setCellValueFactory(new PropertyValueFactory<AccountingTableInfo, String>("typeOperation"));
        sum.setCellValueFactory(new PropertyValueFactory<AccountingTableInfo, BigDecimal>("sum"));
        idDelivery.setCellValueFactory(new PropertyValueFactory<AccountingTableInfo, Integer>("idDelivery"));
        idSale.setCellValueFactory(new PropertyValueFactory<AccountingTableInfo, Integer>("idSale"));
        currentBudget.setCellValueFactory(new PropertyValueFactory<AccountingTableInfo, BigDecimal>("currentBudget"));

        table.setItems(listTableInfo);

        DatabaseConnector databaseConnector = new DatabaseConnector();
        Connection connection = databaseConnector.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(Query.getAccounting);
            ResultSet queryResult = statement.executeQuery();

            while (queryResult.next()) {
                int idOperation = queryResult.getInt(1);
                String typeOperation = queryResult.getString(2);
                BigDecimal sum = queryResult.getBigDecimal(3);
                int idDelivery = queryResult.getInt(4);
                int idSale = queryResult.getInt(5);
                BigDecimal currentBudget = queryResult.getBigDecimal(6);
                listTableInfo.add(new AccountingTableInfo(idOperation, typeOperation, sum, idDelivery, idSale, currentBudget));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection = databaseConnector.closeConnection();
        }
    }

    private void searchItemInTable() {
        FilteredList<AccountingTableInfo> filteredList = new FilteredList<>(listTableInfo, b -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(info -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                if (info.getTypeOperation().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else {
                    return false;
                }

            });
        });

        SortedList<AccountingTableInfo> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(table.comparatorProperty());

        table.setItems(sortedList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hello_user.setText("Добро пожаловать, " + LoginController.nameUser);

        insertDataIntoTable();
        searchItemInTable();

        if (LoginController.position.equals("Менеджер")) {
            orderCar_menuItem.setDisable(true);
            chartSale_menuItem.setDisable(true);
            accounting_menuItem.setDisable(true);
            managers_menuItem.setDisable(true);
            system_menuItem.setDisable(true);
        }
    }
}
