package toyota_app.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import toyota_app.db.DatabaseConnector;
import toyota_app.model.CarItem;
import toyota_app.model.CarTableInfo;
import toyota_app.model.Delivery;
import toyota_app.model.Equipment;
import toyota_app.queries.Query;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.logging.Filter;

public class CarOrderController implements Initializable {

    private final String carBrand = "Toyota";
    private static String carModel;
    private CarTableInfo car;
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

    @FXML
    private TableView<CarTableInfo> table;

    @FXML
    private TableColumn<CarTableInfo, String> brand;

    @FXML
    private TableColumn<CarTableInfo, String> color;

    @FXML
    private TableColumn<CarTableInfo, String> equipment;

    @FXML
    private TableColumn<CarTableInfo, Integer> id;

    @FXML
    private TableColumn<CarTableInfo, String> model;

    @FXML
    private TableColumn<CarTableInfo, BigDecimal> price;

    @FXML
    private TableColumn<CarTableInfo, String> year;

    @FXML
    private Label hello_user;

    @FXML
    private Label carLabel;

    @FXML
    private ImageView carImage;

    @FXML
    private MFXTextField searchField;

    @FXML
    protected static MFXButton btn_createOrder;

    @FXML
    private BorderPane changePane;

    @FXML
    private Label errorField;

    ObservableList<CarTableInfo> listTableInfo = FXCollections.observableArrayList();

    private FXMLLoader fxmlLoader;
    private EquipmentController equipmentController;
    private DeliveryController deliveryController;

    private Equipment carEquipment;
    private Delivery delivery;

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
    public void infoCar(ActionEvent event) {
        Stage stage = (Stage) ScreenController.scene.getWindow();
        ScreenController.change(stage, "car-available.fxml");
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
    private void createOrder(ActionEvent event) {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        Connection connection = databaseConnector.getConnection();

        try {
            double budget = 0;
            PreparedStatement statement = connection.prepareStatement(Query.checkBudget);
            statement.setInt(1, carEquipment.getCarID());
            statement.setString(2, carEquipment.getCarColor());
            ResultSet queryResult = statement.executeQuery();
            while (queryResult.next()) {
                budget = queryResult.getDouble(1);
            }

            System.out.println(budget);

            if (budget - Double.valueOf(deliveryController.getTotalPriceField().getText()) < 0) {
                Alert alertUpdate = new Alert(Alert.AlertType.ERROR);
                alertUpdate.setContentText("Недостаточно бюджета для оформления поставки!");
                alertUpdate.show();
            } else {
                statement = connection.prepareStatement(Query.checkCarAvailability);
                statement.setInt(1, carEquipment.getCarID());
                statement.setString(2, carEquipment.getCarColor());
                queryResult = statement.executeQuery();
                while (queryResult.next()) {
                    if (queryResult.getInt(1) == 1) {
                        statement = connection.prepareStatement(Query.updateCar);
                        statement.setInt(1, Integer.valueOf(deliveryController.getQuantityField().getText()));
                        statement.setInt(2, carEquipment.getCarID());
                        statement.setString(3, carEquipment.getCarColor());

                        int update = statement.executeUpdate();
                        System.out.println(update);
                    } else {
                        statement = connection.prepareStatement(Query.addCarDealeship);
                        statement.setInt(1, carEquipment.getCarID());
                        statement.setString(2, carEquipment.getCarColor());
                        statement.setString(3, deliveryController.getQuantityField().getText());
                        statement.setBigDecimal(4, new BigDecimal(deliveryController.getPriceCar()));
                        int insert = statement.executeUpdate();
                    }
                }

                // insert delivery

                statement = connection.prepareStatement(Query.insertDeliveryInfo);
                statement.setInt(1, carEquipment.getCarID());
                statement.setString(2, carEquipment.getCarColor());
                statement.setInt(3, Integer.valueOf(deliveryController.getQuantityField().getText()));

                if (deliveryController.getCheckBoxTruck().isSelected()) statement.setString(4, deliveryController.getCheckBoxTruck().getText());
                if (deliveryController.getCheckBoxBarge().isSelected()) statement.setString(4, deliveryController.getCheckBoxBarge().getText());
                if (deliveryController.getCheckBoxPlane().isSelected()) statement.setString(4, deliveryController.getCheckBoxPlane().getText());

                java.util.Date date = new java.util.Date();
                statement.setDate(5, new java.sql.Date(date.getTime()));

                int result = statement.executeUpdate();

                // accounting order

                statement = connection.prepareStatement(Query.accountOrder);
                statement.setInt(1, Integer.valueOf(deliveryController.getNumberField().getText()));
                statement.setString(2, "Уход");
                statement.setBigDecimal(3, new BigDecimal(deliveryController.getTotalPriceField().getText()));
                statement.setBigDecimal(4, new BigDecimal(deliveryController.getTotalPriceField().getText()));

                int insert = statement.executeUpdate();

                // update budget

                statement = connection.prepareStatement(Query.updateBudget);
                BigDecimal price = new BigDecimal(deliveryController.getTotalPriceField().getText());
                statement.setBigDecimal(1, price);


                int update = statement.executeUpdate();

                Alert alertUpdate = new Alert(Alert.AlertType.INFORMATION);
                alertUpdate.setContentText("Поставка автомобиля оформлена!");
                alertUpdate.show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection = databaseConnector.closeConnection();
        }
    }

    @FXML
    private void setEquipment(ActionEvent event) {
        try {
            fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/toyota_app/user interfaces/equipment-pane.fxml"));
            changePane.setCenter(fxmlLoader.load());
            changePane.setTop(null);
            equipmentController = fxmlLoader.getController();
            equipmentController.setData(carEquipment);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void setOrder(ActionEvent event) {
        try {
            fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/toyota_app/user interfaces/delivery-registration.fxml"));
            changePane.setTop(fxmlLoader.load());
            changePane.setCenter(null);
            deliveryController = fxmlLoader.getController();
            deliveryController.getTotalPriceField().setText(carEquipment.getCarPrice().toString());
            deliveryController.setPriceCar(carEquipment.getCarPrice().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void initData(String temp) {
        carModel = temp;
    }

    private void setValueFromTable() {
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                changePane.setTop(null);
                try {
                    CarTableInfo temp = table.getItems().get(table.getSelectionModel().getSelectedIndex());

                    DatabaseConnector databaseConnector = new DatabaseConnector();
                    Connection connection = databaseConnector.getConnection();

                    try {
                        PreparedStatement statement = connection.prepareStatement(Query.getEquipment);
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
                            String photo = queryResult.getString(11);
                            carEquipment = new Equipment(id, equipment, engine, fuelType, power, transmission, bodyType, materialInterior, color, price, photo);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        connection = databaseConnector.closeConnection();
                    }

                    fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/toyota_app/user interfaces/equipment-pane.fxml"));
                    changePane.setCenter(fxmlLoader.load());
                    equipmentController = fxmlLoader.getController();
                    equipmentController.setData(carEquipment);
                    Image image = new Image(getClass().getResourceAsStream(PHOTO_SRC + carEquipment.getPhotoSrc()));
                    carImage.setImage(image);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
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
            PreparedStatement statement = connection.prepareStatement(Query.getModelCars);
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

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/toyota_app/user interfaces/equipment-pane.fxml"));
            changePane.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (LoginController.position.equals("Менеджер")) {
            orderCar_menuItem.setDisable(true);
            chartSale_menuItem.setDisable(true);
            accounting_menuItem.setDisable(true);
            managers_menuItem.setDisable(true);
            system_menuItem.setDisable(true);
        }
    }
}
