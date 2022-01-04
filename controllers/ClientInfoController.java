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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import toyota_app.db.DatabaseConnector;
import toyota_app.model.CarTableInfo;
import toyota_app.model.Client;
import toyota_app.model.ClientTableInfo;
import toyota_app.model.Equipment;
import toyota_app.queries.Query;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ClientInfoController implements Initializable {

    private static final String PHOTO_SRC = "/toyota_app/img/clients/";

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
    private Label hello_user;

    @FXML
    private MFXTextField phoneField;

    @FXML
    private MFXTextField searchField;

    @FXML
    private MFXTextField patronymicField;

    @FXML
    private MFXTextField nameField;

    @FXML
    private MFXTextField sernameField;

    @FXML
    private MFXTextField cityField;

    @FXML
    private MFXTextField passportField;

    @FXML
    private MFXTextField addressField;

    @FXML
    private ImageView imgClient;

    @FXML
    private TableView<ClientTableInfo> table;

    @FXML
    private TableColumn<ClientTableInfo, String> sername;

    @FXML
    private TableColumn<ClientTableInfo, String> phone;

    @FXML
    private TableColumn<ClientTableInfo, String> patronymic;

    @FXML
    private TableColumn<ClientTableInfo, String> passport;

    @FXML
    private TableColumn<ClientTableInfo, String> name;

    @FXML
    private TableColumn<ClientTableInfo, Integer> id;

    @FXML
    private TableColumn<ClientTableInfo, String> city;

    ObservableList<ClientTableInfo> listTableInfo = FXCollections.observableArrayList();

    private Client client;
    private final FileChooser fileChooser = new FileChooser();

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

    @FXML
    private void updateInfoClient(ActionEvent event) {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        Connection connection = databaseConnector.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(Query.updateClientInfo);
            statement.setString(1, sernameField.getText());
            statement.setString(2, nameField.getText());
            statement.setString(3, patronymicField.getText());
            statement.setString(4, passportField.getText());
            statement.setString(5, cityField.getText());
            statement.setString(6, addressField.getText());
            statement.setString(7, phoneField.getText());
            statement.setString(8, client.getPhoto());
            statement.setInt(9, client.getId());

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

    private void searchItemInTable() {
        FilteredList<ClientTableInfo> filteredList = new FilteredList<>(listTableInfo, b -> true);

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
        sortedList.comparatorProperty().bind(table.comparatorProperty());

        table.setItems(sortedList);
    }

    @FXML
    private void addInfoClient(ActionEvent event) {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        Connection connection = databaseConnector.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(Query.insertClient);
            statement.setString(1, sernameField.getText());
            statement.setString(2, nameField.getText());
            statement.setString(3, patronymicField.getText());
            statement.setString(4, cityField.getText());
            statement.setString(5, passportField.getText());
            statement.setString(6, phoneField.getText());
            statement.setString(7, addressField.getText());
            statement.setString(8, client.getPhoto());
            int insert = statement.executeUpdate();
            updateTable();
            System.out.println(insert);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection = databaseConnector.closeConnection();
            Alert alertUpdate = new Alert(Alert.AlertType.INFORMATION);
            alertUpdate.setContentText("Данные добавлены!");
            alertUpdate.show();
        }
    }

    @FXML
    private void changePhoto(ActionEvent event) {
        fileChooser.setTitle("Выберите картинку");
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG image", "*.png"),
                                                 new FileChooser.ExtensionFilter("JPG image", "*.jpg"));

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            if (client == null) {
                client = new Client();
                client.setPhoto(file.getName());
            } else {
                client.setPhoto(file.getName());
            }
            Image img = new Image(getClass().getResourceAsStream(PHOTO_SRC + file.getName()));
            imgClient.setImage(img);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Ошибка выбора файла!");
        }
    }

    @FXML
    private void deleteInfoClient(ActionEvent event) {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        Connection connection = databaseConnector.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(Query.checkDeleteClient);
            System.out.println(client.getId());
            statement.setInt(1, client.getId());
            ResultSet queryResult = statement.executeQuery();
            while (queryResult.next())
                if (queryResult.getInt(1) == 1) {
                    Alert alertUpdate = new Alert(Alert.AlertType.WARNING);
                    alertUpdate.setContentText("Данный клиент не может быть удален из базы данных, так как он покупал автомобиль");
                    alertUpdate.show();
                } else if (queryResult.getInt(1) == 2) {
                    statement = connection.prepareStatement(Query.deleteClient);
                    statement.setInt(1, client.getId());
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

    @FXML
    private void clearInfoClient(ActionEvent event) {
        sernameField.setText("");
        nameField.setText("");
        patronymicField.setText("");
        passportField.setText("");
        cityField.setText("");
        addressField.setText("");
        phoneField.setText("");
        Image image = new Image(getClass().getResourceAsStream(PHOTO_SRC + client.getPhoto()));
        imgClient.setImage(null);
        client.setPhoto("");
        client = null;
    }

    private void insertDataIntoTable() {
        id.setCellValueFactory(new PropertyValueFactory<ClientTableInfo, Integer>("id"));
        sername.setCellValueFactory(new PropertyValueFactory<ClientTableInfo, String>("sername"));
        name.setCellValueFactory(new PropertyValueFactory<ClientTableInfo, String>("name"));
        patronymic.setCellValueFactory(new PropertyValueFactory<ClientTableInfo, String>("patronymic"));
        city.setCellValueFactory(new PropertyValueFactory<ClientTableInfo, String>("city"));
        passport.setCellValueFactory(new PropertyValueFactory<ClientTableInfo, String>("passport"));
        phone.setCellValueFactory(new PropertyValueFactory<ClientTableInfo, String>("phone"));

        table.setItems(listTableInfo);

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

                listTableInfo.add(new ClientTableInfo(id, sername, name, patronymic, city, passport, phone));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection = databaseConnector.closeConnection();
        }
    }

    private void updateTable() {
        id.setCellValueFactory(new PropertyValueFactory<ClientTableInfo, Integer>("id"));
        sername.setCellValueFactory(new PropertyValueFactory<ClientTableInfo, String>("sername"));
        name.setCellValueFactory(new PropertyValueFactory<ClientTableInfo, String>("name"));
        patronymic.setCellValueFactory(new PropertyValueFactory<ClientTableInfo, String>("patronymic"));
        city.setCellValueFactory(new PropertyValueFactory<ClientTableInfo, String>("city"));
        passport.setCellValueFactory(new PropertyValueFactory<ClientTableInfo, String>("passport"));
        phone.setCellValueFactory(new PropertyValueFactory<ClientTableInfo, String>("phone"));

        listTableInfo = DatabaseConnector.getClientData();

        table.setItems(listTableInfo);
    }

    private void setClientInfo() {
        sernameField.setText(client.getSername());
        nameField.setText(client.getName());
        patronymicField.setText(client.getPatronymic());
        passportField.setText(client.getPassport());
        cityField.setText(client.getCity());
        addressField.setText(client.getAddress());
        phoneField.setText(client.getPhone());
        Image image = new Image(getClass().getResourceAsStream(PHOTO_SRC + client.getPhoto()));
        imgClient.setImage(image);
    }

    private void setValueFromTable() {
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ClientTableInfo temp = table.getItems().get(table.getSelectionModel().getSelectedIndex());

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hello_user.setText("Добро пожаловать, " + LoginController.nameUser);

        if (LoginController.position.equals("Менеджер")) {
            orderCar_menuItem.setDisable(true);
            chartSale_menuItem.setDisable(true);
            accounting_menuItem.setDisable(true);
            managers_menuItem.setDisable(true);
            system_menuItem.setDisable(true);
        }

        insertDataIntoTable();
        setValueFromTable();
        searchItemInTable();
    }
}
