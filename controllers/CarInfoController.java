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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import toyota_app.db.DatabaseConnector;
import toyota_app.model.CarTableInfo;
import toyota_app.model.Equipment;
import toyota_app.queries.Query;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CarInfoController implements Initializable {
    private final String carBrand = "Toyota";
    private static String carModel;
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

    @FXML private MFXTextField engineCapacityField;

    @FXML private MFXTextField equipmentField;

    @FXML private MFXTextField quantityField;

    @FXML private MFXTextField bodyTypeField;

    @FXML private MFXTextField transmissionField;

    @FXML private MFXTextField colorField;

    @FXML private MFXTextField interiorTypeField;

    @FXML private MFXTextField powerField;

    @FXML private MFXTextField priceField;

    @FXML private MFXTextField fuelTypeField;

    @FXML private MFXTextField searchField;

    @FXML private TableView<CarTableInfo> table;

    @FXML private TableColumn<CarTableInfo, String> brand;

    @FXML private TableColumn<CarTableInfo, String> color;

    @FXML private TableColumn<CarTableInfo, String> equipment;

    @FXML private TableColumn<CarTableInfo, Integer> id;

    @FXML private TableColumn<CarTableInfo, String> model;

    @FXML private TableColumn<CarTableInfo, BigDecimal> price;

    @FXML private TableColumn<CarTableInfo, String> year;

    @FXML private ImageView img;

    @FXML private Label hello_user;

    @FXML private Label carLabel;

    ObservableList<CarTableInfo> listTableInfo = FXCollections.observableArrayList();

    private Equipment carEquipment;

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
    public void orderCar(ActionEvent event) {
        Stage stage = (Stage) ScreenController.scene.getWindow();
        ScreenController.change(stage, "car-select.fxml");
    }

    @FXML
    public void infoCar(ActionEvent event) {
        Stage stage = (Stage) ScreenController.scene.getWindow();
        ScreenController.change(stage, "car-available.fxml");
    }


    @FXML
    private void updateCarInfo(ActionEvent event) {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        Connection connection = databaseConnector.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(Query.updateCarInfo);
            statement.setInt(1, Integer.valueOf(quantityField.getText()));
            statement.setBigDecimal(2, new BigDecimal(priceField.getText()));
            statement.setInt(3, carEquipment.getCarID());
            statement.setString(4, carEquipment.getCarColor());

            int update = statement.executeUpdate();
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
    private void deleteCarInfo(ActionEvent event) {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        Connection connection = databaseConnector.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(Query.checkDeleteCar);
            System.out.println(carEquipment.getCarID());
            statement.setInt(1, carEquipment.getCarID());
            ResultSet queryResult = statement.executeQuery();
            while (queryResult.next())
                if (queryResult.getInt(1) == 1) {
                    Alert alertUpdate = new Alert(Alert.AlertType.WARNING);
                    alertUpdate.setContentText("Данное авто не может быть удалено из базы данных");
                    alertUpdate.show();
                } else if (queryResult.getInt(1) == 2) {
                    statement = connection.prepareStatement(Query.deleteCarInfo);
                    statement.setInt(1, carEquipment.getCarID());
                    statement.setString(2, carEquipment.getCarColor());
                    int delete = statement.executeUpdate();
                    System.out.println(delete);
                    Alert alertUpdate = new Alert(Alert.AlertType.WARNING);
                    alertUpdate.setContentText("Данные удалены!");
                    alertUpdate.show();
                    updateTable();
                }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection = databaseConnector.closeConnection();
        }
    }

    public static void initData(String temp) {
        carModel = temp;
    }

    private void setEquipmentInfo() {
        equipmentField.setText(carEquipment.getCarEquipment());
        engineCapacityField.setText(carEquipment.getCarEngineCapacity());
        fuelTypeField.setText(carEquipment.getCarFuelType());
        powerField.setText(carEquipment.getCarPower());
        transmissionField.setText(carEquipment.getCarTransmission());
        bodyTypeField.setText(carEquipment.getCarBodyType());
        interiorTypeField.setText(carEquipment.getCarInteriorMaterial());
        colorField.setText(carEquipment.getCarColor());
        priceField.setText(carEquipment.getCarPrice().toString());
        quantityField.setText(String.valueOf(carEquipment.getNumOfCars()));
        Image image = new Image(getClass().getResourceAsStream(PHOTO_SRC + carEquipment.getPhotoSrc()));
        img.setImage(image);
    }

    private void updateTable() {
        id.setCellValueFactory(new PropertyValueFactory<CarTableInfo, Integer>("id"));
        brand.setCellValueFactory(new PropertyValueFactory<CarTableInfo, String>("brand"));
        model.setCellValueFactory(new PropertyValueFactory<CarTableInfo, String>("model"));
        year.setCellValueFactory(new PropertyValueFactory<CarTableInfo, String>("year"));
        equipment.setCellValueFactory(new PropertyValueFactory<CarTableInfo, String>("equipment"));
        color.setCellValueFactory(new PropertyValueFactory<CarTableInfo, String>("color"));
        price.setCellValueFactory(new PropertyValueFactory<CarTableInfo, BigDecimal>("price"));

        listTableInfo = DatabaseConnector.getCarData(carModel);

        table.setItems(listTableInfo);
    }

    private void searchItemInTable() {
        FilteredList<CarTableInfo> filteredList = new FilteredList<>(listTableInfo, b -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(info -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                if (info.getBrand().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (info.getModel().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (info.getYear().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (info.getEquipment().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (info.getColor().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (info.getPrice().toString().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (String.valueOf(info.getId()).indexOf(searchKeyword) > -1) {
                    return true;
                } else {
                    return false;
                }

            });
        });

        SortedList<CarTableInfo> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(table.comparatorProperty());

        table.setItems(sortedList);
    }

    private void setValueFromTable() {
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                CarTableInfo temp = table.getItems().get(table.getSelectionModel().getSelectedIndex());

                DatabaseConnector databaseConnector = new DatabaseConnector();
                Connection connection = databaseConnector.getConnection();

                try {
                    PreparedStatement statement = connection.prepareStatement(Query.getDealershipEquipment);
                    statement.setString(1, temp.getEquipment());
                    statement.setString(2, temp.getColor());
                    ResultSet queryResult = statement.executeQuery();

                    while (queryResult.next()) {
                        int id = queryResult.getInt(1);
                        String equipment = queryResult.getString(2);
                        String engine = queryResult.getString(3);
                        String fuelType = queryResult.getString(4);
                        String power = queryResult.getString(5);
                        String transmission = queryResult.getString(6);
                        String bodyType = queryResult.getString(7);
                        String materialInterior = queryResult.getString(8);
                        String color = queryResult.getString(9);
                        BigDecimal price = queryResult.getBigDecimal(10);
                        int numOfCars = queryResult.getInt(11);
                        String photo = queryResult.getString(13);
                        carEquipment = new Equipment(id, equipment, engine, fuelType, power, transmission, bodyType, materialInterior, color, price, numOfCars, photo);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    connection = databaseConnector.closeConnection();
                }

                setEquipmentInfo();
            }
        });
    }

    private void insertDataIntoTable() {
        id.setCellValueFactory(new PropertyValueFactory<CarTableInfo, Integer>("id"));
        brand.setCellValueFactory(new PropertyValueFactory<CarTableInfo, String>("brand"));
        model.setCellValueFactory(new PropertyValueFactory<CarTableInfo, String>("model"));
        year.setCellValueFactory(new PropertyValueFactory<CarTableInfo, String>("year"));
        equipment.setCellValueFactory(new PropertyValueFactory<CarTableInfo, String>("equipment"));
        color.setCellValueFactory(new PropertyValueFactory<CarTableInfo, String>("color"));
        price.setCellValueFactory(new PropertyValueFactory<CarTableInfo, BigDecimal>("price"));

        table.setItems(listTableInfo);

        DatabaseConnector databaseConnector = new DatabaseConnector();
        Connection connection = databaseConnector.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(Query.getInfoDealeashipCars);
            statement.setString(1, carModel);
            System.out.println(carModel);
            ResultSet queryResult = statement.executeQuery();

            while (queryResult.next()) {
                int id = queryResult.getInt(1);
                String brand = queryResult.getString(2);
                String model = queryResult.getString(3);
                String year = queryResult.getString(4);
                String equipment = queryResult.getString(5);
                String color = queryResult.getString(6);
                BigDecimal price = queryResult.getBigDecimal(7);
                listTableInfo.add(new CarTableInfo(id, brand, model, year, equipment, color, price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection = databaseConnector.closeConnection();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hello_user.setText("Добро пожаловать, " + LoginController.nameUser);
        carLabel.setText(carBrand.toUpperCase() + " " + carModel.toUpperCase());

        insertDataIntoTable();
        setValueFromTable();
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
