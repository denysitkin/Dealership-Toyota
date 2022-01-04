package toyota_app.controllers;

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
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SaleInfoController implements Initializable {

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

    @FXML private MFXTextField totalPriceField;

    @FXML private MFXTextField typePaymentField;

    @FXML private MFXTextField dateSaleField;

    @FXML private MFXTextField searchField;

    @FXML private Label hello_user;

    // car table

    @FXML private TableView<CarTableInfo> carTable;

    @FXML private TableColumn<CarTableInfo, String> brand;

    @FXML private TableColumn<CarTableInfo, String> color;

    @FXML private TableColumn<CarTableInfo, String> equipment;

    @FXML private TableColumn<CarTableInfo, Integer> idCar;

    @FXML private TableColumn<CarTableInfo, String> model;

    @FXML private TableColumn<CarTableInfo, String> typePayment;

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
    private void showCarInfo(ActionEvent event) {
        setCarInfo();
    }

    @FXML
    private void showClientInfo(ActionEvent event) {
        setClientInfo();
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
                        System.out.println(client.getId());
                        setClientInfo();
                        setClientCars();
                        searchCarInTable();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    connection = databaseConnector.closeConnection();
                }
            }
        });
    }

    private void setClientCars() {
        carTableInfo.clear();
        idCar.setCellValueFactory(new PropertyValueFactory<CarTableInfo, Integer>("id"));
        brand.setCellValueFactory(new PropertyValueFactory<CarTableInfo, String>("brand"));
        model.setCellValueFactory(new PropertyValueFactory<CarTableInfo, String>("model"));
        year.setCellValueFactory(new PropertyValueFactory<CarTableInfo, String>("year"));
        equipment.setCellValueFactory(new PropertyValueFactory<CarTableInfo, String>("equipment"));
        color.setCellValueFactory(new PropertyValueFactory<CarTableInfo, String>("color"));
        typePayment.setCellValueFactory(new PropertyValueFactory<CarTableInfo, String>("typePayment"));

        carTable.setItems(carTableInfo);

        DatabaseConnector databaseConnector = new DatabaseConnector();
        Connection connection = databaseConnector.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(Query.getClientCars);
            statement.setInt(1, client.getId());
            ResultSet queryResult = statement.executeQuery();

            while (queryResult.next()) {
                int id = queryResult.getInt(1);
                System.out.println(queryResult.getInt(1));
                String brand = queryResult.getString(2);
                String model = queryResult.getString(3);
                String year = queryResult.getString(4);
                String equipment = queryResult.getString(5);
                String color = queryResult.getString(6);
                String typePayment = queryResult.getString(7);
                carTableInfo.add(new CarTableInfo(id, brand, model, year, equipment, color, typePayment));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection = databaseConnector.closeConnection();
        }
    }

    private void setClientInfo() {
        try {
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
                        String nameModel = queryResult.getString(12);
                        String photo = queryResult.getString(13);
                        carEquipment = new Equipment(id, equipment, engine, fuelType, power, transmission, bodyType, materialInterior, color, price, numOfCars, nameModel, photo);
                        setCarInfo();
                    }

                    statement = connection.prepareStatement(Query.getInfoSale);
                    statement.setInt(1, client.getId());
                    statement.setInt(2, temp.getId());
                    queryResult = statement.executeQuery();

                    while (queryResult.next()) {
                        dateSaleField.setText(queryResult.getString(1));
                        typePaymentField.setText(queryResult.getString(2));
                        totalPriceField.setText(queryResult.getString(3));
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    connection = databaseConnector.closeConnection();
                }
            }
        });
    }

    private void setCarInfo() {
        try {
            fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/toyota_app/user interfaces/equipment-pane.fxml"));
            infoPane.setCenter(fxmlLoader.load());
            equipmentController = fxmlLoader.getController();
            equipmentController.setData(carEquipment);
            FXMLLoader fxmlLoaderPhoto = new FXMLLoader();
            fxmlLoaderPhoto.setLocation(getClass().getResource("/toyota_app/user interfaces/car-slider.fxml"));
            photoPane.setCenter(fxmlLoaderPhoto.load());
            carImageController = fxmlLoaderPhoto.getController();
            carImageController.setData(carEquipment.getPhotoSrc(), carEquipment.getNameModel());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void searchClientInTable() {
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

    private void searchCarInTable() {
        FilteredList<CarTableInfo> filteredList = new FilteredList<>(carTableInfo, b -> true);

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
                } else if (info.getTypePayment().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (String.valueOf(info.getId()).indexOf(searchKeyword) > -1) {
                    return true;
                } else {
                    return false;
                }

            });
        });

        SortedList<CarTableInfo> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(carTable.comparatorProperty());

        carTable.setItems(sortedList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hello_user.setText("Добро пожаловать, " + LoginController.nameUser);

        searchCarInTable();
        insertClientIntoTable();
        setClientFromTable();
        setCarFromTable();
        searchClientInTable();

        if (LoginController.position.equals("Менеджер")) {
            orderCar_menuItem.setDisable(true);
            chartSale_menuItem.setDisable(true);
            accounting_menuItem.setDisable(true);
            managers_menuItem.setDisable(true);
            system_menuItem.setDisable(true);
        }

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
