package toyota_app.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import toyota_app.db.DatabaseConnector;
import toyota_app.model.CarTableInfo;
import toyota_app.model.Client;
import toyota_app.model.ClientTableInfo;
import toyota_app.model.Equipment;
import toyota_app.queries.Query;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;


public class SaleController implements Initializable {
    private final String carBrand = "Toyota";
    private static String carModel;

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

    @FXML private MFXTextField totalPrice;

    @FXML private MFXComboBox<String> typePayment;

    @FXML private MFXTextField searchField;

    @FXML private MFXButton btn_sale;

    // car table

    @FXML private MFXDatePicker datePicker;

    @FXML private TableView<CarTableInfo> carTable;

    @FXML private TableColumn<CarTableInfo, String> brand;

    @FXML private TableColumn<CarTableInfo, String> color;

    @FXML private TableColumn<CarTableInfo, String> equipment;

    @FXML private TableColumn<CarTableInfo, Integer> idCar;

    @FXML private TableColumn<CarTableInfo, String> model;

    @FXML private TableColumn<CarTableInfo, BigDecimal> price;

    @FXML private TableColumn<CarTableInfo, String> year;

    // client table

    @FXML private TableView<ClientTableInfo> clientTable;

    @FXML private TableColumn<ClientTableInfo, String> surname;

    @FXML private TableColumn<ClientTableInfo, String> phone;

    @FXML private TableColumn<ClientTableInfo, String> patronymic;

    @FXML private TableColumn<ClientTableInfo, String> passport;

    @FXML private TableColumn<ClientTableInfo, String> name;

    @FXML private TableColumn<ClientTableInfo, Integer> idClient;

    @FXML private TableColumn<ClientTableInfo, String> city;

    @FXML private Label hello_user;

    @FXML private Label isSold;

    @FXML private BorderPane photoPane;
    @FXML private BorderPane infoPane;
    private FXMLLoader fxmlLoader;

    ObservableList<ClientTableInfo> clientTableInfo = FXCollections.observableArrayList();
    ObservableList<CarTableInfo> carTableInfo = FXCollections.observableArrayList();

    private Equipment carEquipment;
    private Client client;
    private ClientController clientController;
    private EquipmentController equipmentController;
    private ClientImageController clientImageController;
    private CarImageController carImageController;

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
    private void makeSale(ActionEvent event) {
        if (carEquipment.getNumOfCars() != 0) {
            DatabaseConnector databaseConnector = new DatabaseConnector();
            Connection connection = databaseConnector.getConnection();
            Date date = java.util.Date.from(datePicker.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());

            try {
                PreparedStatement statement = connection.prepareStatement(Query.insertSale);
                statement.setInt(1, client.getId());
                statement.setInt(2, LoginController.idUser);
                statement.setInt(3, carEquipment.getCarID());
                statement.setString(4, carEquipment.getCarColor());
                statement.setDate(5, sqlDate);
                statement.setString(6, typePayment.getSelectedValue());

                int insert = statement.executeUpdate();

                // update salary

                statement = connection.prepareStatement(Query.updateSalary);
                statement.setBigDecimal(1, carEquipment.getCarPrice());
                statement.setInt(2, LoginController.idUser);

                int update = statement.executeUpdate();

                // update numOfCars

                statement = connection.prepareStatement(Query.updateNumOfCars);
                statement.setInt(1, carEquipment.getCarID());
                statement.setString(2, carEquipment.getCarColor());

                update = statement.executeUpdate();

                // accounting sale
                int idContract = 0;

                statement = connection.prepareStatement(Query.getLastNumOfSale);
                ResultSet queryResult = statement.executeQuery();

                while (queryResult.next()) {
                    idContract = queryResult.getInt(1);
                }

                statement = connection.prepareStatement(Query.accountSale);
                statement.setString(1, "Приход");
                statement.setBigDecimal(2, new BigDecimal(totalPrice.getText()));
                statement.setInt(3, idContract);
                statement.setBigDecimal(4, new BigDecimal(totalPrice.getText()));

                insert = statement.executeUpdate();

                // update budget
                statement = connection.prepareStatement(Query.updateBudgetSale);
                BigDecimal price = new BigDecimal(totalPrice.getText());
                statement.setBigDecimal(1, price);

                update = statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection = databaseConnector.closeConnection();
                Alert alertUpdate = new Alert(Alert.AlertType.INFORMATION);
                alertUpdate.setContentText("Заказ оформлен");
                alertUpdate.show();
            }
        } else {
            Alert alertUpdate = new Alert(Alert.AlertType.WARNING);
            alertUpdate.setContentText("Данный автомобиль уже продан!");
            alertUpdate.show();
        }
    }

    @FXML
    private void showCarInfo(ActionEvent event) {
        setCarInfo();
    }

    @FXML
    private void showClientInfo(ActionEvent event) {
        setClientInfo();
    }

    public static void initData(String temp) {
        carModel = temp;
    }

    private void insertClientIntoTable() {
        idClient.setCellValueFactory(new PropertyValueFactory<ClientTableInfo, Integer>("id"));
        surname.setCellValueFactory(new PropertyValueFactory<ClientTableInfo, String>("sername"));
        name.setCellValueFactory(new PropertyValueFactory<ClientTableInfo, String>("name"));
        patronymic.setCellValueFactory(new PropertyValueFactory<ClientTableInfo, String>("patronymic"));
        city.setCellValueFactory(new PropertyValueFactory<ClientTableInfo, String>("city"));
        passport.setCellValueFactory(new PropertyValueFactory<ClientTableInfo, String>("passport"));
        phone.setCellValueFactory(new PropertyValueFactory<ClientTableInfo, String>("phone"));

        clientTable.setItems(clientTableInfo);

        DatabaseConnector databaseConnector = new DatabaseConnector();
        Connection connection = databaseConnector.getConnection();

        try {
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

                clientTableInfo.add(new ClientTableInfo(id, sername, name, patronymic, city, passport, phone));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection = databaseConnector.closeConnection();
        }
    }

    private void insertCarIntoTable() {
        idCar.setCellValueFactory(new PropertyValueFactory<CarTableInfo, Integer>("id"));
        brand.setCellValueFactory(new PropertyValueFactory<CarTableInfo, String>("brand"));
        model.setCellValueFactory(new PropertyValueFactory<CarTableInfo, String>("model"));
        year.setCellValueFactory(new PropertyValueFactory<CarTableInfo, String>("year"));
        equipment.setCellValueFactory(new PropertyValueFactory<CarTableInfo, String>("equipment"));
        color.setCellValueFactory(new PropertyValueFactory<CarTableInfo, String>("color"));
        price.setCellValueFactory(new PropertyValueFactory<CarTableInfo, BigDecimal>("price"));

        carTable.setItems(carTableInfo);

        DatabaseConnector databaseConnector = new DatabaseConnector();
        Connection connection = databaseConnector.getConnection();

        try {
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
                carTableInfo.add(new CarTableInfo(id, brand, model, year, equipment, color, price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection = databaseConnector.closeConnection();
        }
    }

    private void setClientFromTable() {
        clientTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ClientTableInfo temp = clientTable.getItems().get(clientTable.getSelectionModel().getSelectedIndex());

                DatabaseConnector databaseConnector = new DatabaseConnector();
                Connection connection = databaseConnector.getConnection();

                try {
                    PreparedStatement statement = connection.prepareStatement(Query.getClientInfo);
                    statement.setInt(1, temp.getId());
                    ResultSet queryResult = statement.executeQuery();

                    while (queryResult.next()) {
                        int id = queryResult.getInt(1);
                        String sername = queryResult.getString(2);
                        String name = queryResult.getString(3);
                        String patronymic = queryResult.getString(4);
                        String passport = queryResult.getString(5);
                        String city = queryResult.getString(6);
                        String address = queryResult.getString(7);
                        String phone = queryResult.getString(8);
                        String photo = queryResult.getString(9);
                        client = new Client(id, sername, name, patronymic, city, passport, address, phone, photo);
                        setClientInfo();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    connection = databaseConnector.closeConnection();
                }
            }
        });
    }

    private void setCarFromTable() {
        carTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                CarTableInfo temp = carTable.getItems().get(carTable.getSelectionModel().getSelectedIndex());

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
                        totalPrice.setText(carEquipment.getCarPrice().toString());
                        setCarInfo();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    connection = databaseConnector.closeConnection();
                }
            }
        });
    }

    private void setClientInfo() {
        try {
            btn_sale.setVisible(false);
            isSold.setVisible(false);
            fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/toyota_app/user interfaces/client.fxml"));
            infoPane.setCenter(fxmlLoader.load());
            clientController = fxmlLoader.getController();
            clientController.setData(client);
            FXMLLoader fxmlLoaderPhoto = new FXMLLoader();
            fxmlLoaderPhoto.setLocation(getClass().getResource("/toyota_app/user interfaces/client-photo.fxml"));
            photoPane.setCenter(fxmlLoaderPhoto.load());
            clientImageController = fxmlLoaderPhoto.getController();
            clientImageController.setData(client.getPhoto());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setCarInfo() {
        try {
            System.out.println(carEquipment.getNumOfCars());
            btn_sale.setVisible(true);
            if (carEquipment.getNumOfCars() <= 0) {
                isSold.setVisible(true);
                btn_sale.setDisable(true);
            } else {
                btn_sale.setVisible(true);
                isSold.setVisible(false);
                btn_sale.setDisable(false);
            }
            fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/toyota_app/user interfaces/equipment-pane.fxml"));
            infoPane.setCenter(fxmlLoader.load());
            equipmentController = fxmlLoader.getController();
            equipmentController.setData(carEquipment);
            FXMLLoader fxmlLoaderPhoto = new FXMLLoader();
            fxmlLoaderPhoto.setLocation(getClass().getResource("/toyota_app/user interfaces/car-slider.fxml"));
            photoPane.setCenter(fxmlLoaderPhoto.load());
            carImageController = fxmlLoaderPhoto.getController();
            carImageController.setData(carEquipment.getPhotoSrc(), carModel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void searchItemInTable() {
        FilteredList<ClientTableInfo> filteredList = new FilteredList<>(clientTableInfo, b -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(info -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                if (info.getSername().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (info.getName().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (info.getPatronymic().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (info.getCity().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (info.getPassport().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (info.getPhone().toString().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (String.valueOf(info.getId()).indexOf(searchKeyword) > -1) {
                    return true;
                } else {
                    return false;
                }

            });
        });

        SortedList<ClientTableInfo> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(clientTable.comparatorProperty());

        clientTable.setItems(sortedList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hello_user.setText("Добро пожаловать, " + LoginController.nameUser);

        insertClientIntoTable();
        insertCarIntoTable();
        searchItemInTable();
        setClientFromTable();
        setCarFromTable();

        if (LoginController.position.equals("Менеджер")) {
            orderCar_menuItem.setDisable(true);
            chartSale_menuItem.setDisable(true);
            accounting_menuItem.setDisable(true);
            managers_menuItem.setDisable(true);
            system_menuItem.setDisable(true);
        }

        typePayment.setItems(FXCollections.observableArrayList("Карта", "Наличные", "Кредит"));

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/toyota_app/user interfaces/client.fxml"));
            infoPane.setCenter(fxmlLoader.load());
            FXMLLoader fxmlLoaderPhoto = new FXMLLoader();
            fxmlLoaderPhoto.setLocation(getClass().getResource("/toyota_app/user interfaces/client-photo.fxml"));
            photoPane.setCenter(fxmlLoaderPhoto.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
