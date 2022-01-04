package toyota_app.controllers;

import io.github.palexdev.materialfx.controls.MFXCheckbox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import toyota_app.db.DatabaseConnector;
import toyota_app.model.CarItem;
import toyota_app.queries.Query;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CarDealershipController implements Initializable {

    private static final String PHOTO_SRC = "/toyota_app/img/";

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

    @FXML private GridPane carsGrid;

    @FXML private MFXCheckbox checkBoxCrossover;

    @FXML private MFXCheckbox checkBoxHatchback;

    @FXML private MFXCheckbox checkBoxPickup;

    @FXML private MFXCheckbox checkBoxSUV;

    @FXML private MFXCheckbox checkBoxSedan;

    @FXML private MFXCheckbox checkBoxVan;

    private List<CarItem> carItemList;

    private int columnGrid;
    private int rowGrid;

    @FXML
    public void infoClient(ActionEvent event) {
        Stage stage = (Stage) ScreenController.scene.getWindow();
        ScreenController.change(stage, "client-info.fxml");
    }

    @FXML
    public void infoCar(ActionEvent event) {
        Stage stage = (Stage) ScreenController.scene.getWindow();
        ScreenController.change(stage, "car-available.fxml");
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
    public void orderCar(ActionEvent event) {
        Stage stage = (Stage) ScreenController.scene.getWindow();
        ScreenController.change(stage, "car-select.fxml");
    }

    @FXML
    void searchBodyType(ActionEvent event) {

        columnGrid = 0;
        rowGrid = 1;

        carsGrid.getChildren().clear();

        if (checkBoxVan.isSelected() ||
                checkBoxSedan.isSelected() ||
                checkBoxHatchback.isSelected() ||
                checkBoxSUV.isSelected() ||
                checkBoxPickup.isSelected() ||
                checkBoxCrossover.isSelected())  {

            if (checkBoxSedan.isSelected()) setGridPaneSelected(checkBoxSedan);

            if (checkBoxHatchback.isSelected()) setGridPaneSelected(checkBoxHatchback);

            if (checkBoxSUV.isSelected()) setGridPaneSelected(checkBoxSUV);

            if (checkBoxPickup.isSelected()) setGridPaneSelected(checkBoxPickup);

            if (checkBoxCrossover.isSelected()) setGridPaneSelected(checkBoxCrossover);

            if (checkBoxVan.isSelected()) setGridPaneSelected(checkBoxVan);

        } else {
            setGridPaneDefault();
        }

    }

    private void setGridPaneDefault() {
        int column = 0;
        int row = 1;
        try {
            for (CarItem item : carItemList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/toyota_app/user interfaces/car-item.fxml"));
                VBox carItem = fxmlLoader.load();
                CarItemController carItemController = fxmlLoader.getController();
                carItemController.setData(item);

                if (column == 4) {
                    column = 0;
                    ++row;
                }

                carsGrid.add(carItem, column++, row);
                GridPane.setMargin(carItem, new Insets(0, 35, 45, 5));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setGridPaneSelected(MFXCheckbox checkbox) {
        try {
            for (CarItem item : carItemList) {
                if (item.getBodyType().equals(checkbox.getText().toUpperCase())) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/toyota_app/user interfaces/car-item.fxml"));
                    VBox carItem = fxmlLoader.load();
                    CarItemController carItemController = fxmlLoader.getController();
                    carItemController.setData(item);

                    if (columnGrid == 4) {
                        columnGrid = 0;
                        ++rowGrid;
                    }
                    carsGrid.add(carItem, columnGrid++, rowGrid);
                    GridPane.setMargin(carItem, new Insets(0, 35, 45, 5));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hello_user.setText("Добро пожаловать, " + LoginController.nameUser);
        carItemList = new ArrayList<>(getData());

        if (LoginController.position.equals("Менеджер")) {
            orderCar_menuItem.setDisable(true);
            chartSale_menuItem.setDisable(true);
            accounting_menuItem.setDisable(true);
            managers_menuItem.setDisable(true);
            system_menuItem.setDisable(true);
        }

        int column = 0;
        int row = 1;
        try {
            for (CarItem item : carItemList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/toyota_app/user interfaces/car-item.fxml"));
                VBox carItem = fxmlLoader.load();
                CarItemController carItemController = fxmlLoader.getController();
                carItemController.setData(item);


                if (column == 4) {
                    column = 0;
                    ++row;
                }

                carsGrid.add(carItem, column++, row);
                GridPane.setMargin(carItem, new Insets(0, 35, 45, 5));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<CarItem> getData() {
        List<CarItem> temp = new ArrayList<>();
        DatabaseConnector databaseConnector = new DatabaseConnector();
        Connection connection = databaseConnector.getConnection();

        try {
            Statement statement = connection.createStatement();

            ResultSet queryResult = statement.executeQuery(Query.getDealershipCars);

            while (queryResult.next()) {
                String carModel = queryResult.getString(1);
                String bodyType = queryResult.getString(2);
                temp.add(new CarItem(PHOTO_SRC + carModel.toLowerCase()+"-white-m.png", carModel.toUpperCase(), bodyType.toUpperCase()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection = databaseConnector.closeConnection();
        }

        return temp;
    }
}
