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
import toyota_app.model.Client;
import toyota_app.model.ClientTableInfo;
import toyota_app.model.Manager;
import toyota_app.model.ManagerTableInfo;
import toyota_app.queries.Query;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ManagerInfoController implements Initializable {
    private static final String PHOTO_SRC = "/toyota_app/img/managers/";

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

    @FXML private MFXTextField searchField;

    @FXML private MFXTextField patronymicField;

    @FXML private MFXTextField nameField;

    @FXML private MFXTextField surnameField;

    @FXML private MFXTextField salaryField;

    @FXML private MFXTextField positionField;

    @FXML private MFXTextField numOfSalesField;

    @FXML private MFXTextField phoneField;

    @FXML private ImageView imgClient;

    @FXML private TableView<ManagerTableInfo> table;

    @FXML private TableColumn<ManagerTableInfo, String> surname;

    @FXML private TableColumn<ManagerTableInfo, String> salary;

    @FXML private TableColumn<ManagerTableInfo, String> patronymic;

    @FXML private TableColumn<ManagerTableInfo, String> position;

    @FXML private TableColumn<ManagerTableInfo, String> name;

    @FXML private TableColumn<ManagerTableInfo, Integer> id;

    @FXML private TableColumn<ManagerTableInfo, Integer> numOfSales;

    ObservableList<ManagerTableInfo> listTableInfo = FXCollections.observableArrayList();

    private Manager manager;
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
    private void updateInfoManager(ActionEvent event) {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        Connection connection = databaseConnector.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(Query.updateManagerInfo);
            statement.setString(1, surnameField.getText());
            statement.setString(2, nameField.getText());
            statement.setString(3, patronymicField.getText());
            statement.setString(4, salaryField.getText());
            statement.setString(5, positionField.getText());
            statement.setString(6, numOfSalesField.getText());
            statement.setString(7, phoneField.getText());
            statement.setString(8, manager.getPhoto());
            statement.setInt(9, manager.getId());

            int update = statement.executeUpdate();
            updateTable();
            LoginController.nameUser = nameField.getText();
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
        FilteredList<ManagerTableInfo> filteredList = new FilteredList<>(listTableInfo, b -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(info -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                if (info.getSurname().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (info.getName().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (info.getPatronymic().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (info.getPosition().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (info.getNumOfSales().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (info.getSalary().toString().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (String.valueOf(info.getId()).indexOf(searchKeyword) > -1) {
                    return true;
                } else {
                    return false;
                }

            });
        });

        SortedList<ManagerTableInfo> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(table.comparatorProperty());

        table.setItems(sortedList);
    }

    @FXML
    private void addInfoManager(ActionEvent event) {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        Connection connection = databaseConnector.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(Query.insertManager);
            statement.setString(1, surnameField.getText());
            statement.setString(2, nameField.getText());
            statement.setString(3, patronymicField.getText());
            statement.setString(4, positionField.getText());
            statement.setString(5, salaryField.getText());
            statement.setString(6, numOfSalesField.getText());
            statement.setString(7, phoneField.getText());
            statement.setString(8, manager.getPhoto());
            int insert = statement.executeUpdate();
            updateTable();
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
            if (manager == null) {
                manager = new Manager();
                manager.setPhoto(file.getName());
            } else {
                manager.setPhoto(file.getName());
            }
            Image img = new Image(getClass().getResourceAsStream(PHOTO_SRC + file.getName()));
            imgClient.setImage(img);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Ошибка выбора файла!");
        }
    }

    @FXML
    private void deleteInfoManager(ActionEvent event) {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        Connection connection = databaseConnector.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(Query.checkDeleteManager);
            statement.setInt(1, manager.getId());
            ResultSet queryResult = statement.executeQuery();
            while (queryResult.next())
                if (queryResult.getInt(1) == 1) {
                    Alert alertUpdate = new Alert(Alert.AlertType.WARNING);
                    alertUpdate.setContentText("Данный менеджер не может быть удален из базы данных, так как он продавал автомобиль");
                    alertUpdate.show();
                } else if (queryResult.getInt(1) == 2) {
                    statement = connection.prepareStatement(Query.deleteManager);
                    statement.setInt(1, manager.getId());
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
    private void clearInfoManager(ActionEvent event) {
        surnameField.setText("");
        nameField.setText("");
        patronymicField.setText("");
        positionField.setText("");
        salaryField.setText("");
        numOfSalesField.setText("");
        phoneField.setText("");
        Image image = new Image(getClass().getResourceAsStream(PHOTO_SRC + manager.getPhoto()));
        imgClient.setImage(null);
        manager.setPhoto("");
        manager = null;
    }

    private void insertDataIntoTable() {
        id.setCellValueFactory(new PropertyValueFactory<ManagerTableInfo, Integer>("id"));
        surname.setCellValueFactory(new PropertyValueFactory<ManagerTableInfo, String>("surname"));
        name.setCellValueFactory(new PropertyValueFactory<ManagerTableInfo, String>("name"));
        patronymic.setCellValueFactory(new PropertyValueFactory<ManagerTableInfo, String>("patronymic"));
        position.setCellValueFactory(new PropertyValueFactory<ManagerTableInfo, String>("position"));
        salary.setCellValueFactory(new PropertyValueFactory<ManagerTableInfo, String>("salary"));
        numOfSales.setCellValueFactory(new PropertyValueFactory<ManagerTableInfo, Integer>("numOfSales"));

        table.setItems(listTableInfo);

        DatabaseConnector databaseConnector = new DatabaseConnector();
        Connection connection = databaseConnector.getConnection();

        try {
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
                listTableInfo.add(new ManagerTableInfo(id, sername, name, patronymic, position, salary, numOfSales));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection = databaseConnector.closeConnection();
        }
    }

    private void updateTable() {
        id.setCellValueFactory(new PropertyValueFactory<ManagerTableInfo, Integer>("id"));
        surname.setCellValueFactory(new PropertyValueFactory<ManagerTableInfo, String>("surname"));
        name.setCellValueFactory(new PropertyValueFactory<ManagerTableInfo, String>("name"));
        patronymic.setCellValueFactory(new PropertyValueFactory<ManagerTableInfo, String>("patronymic"));
        position.setCellValueFactory(new PropertyValueFactory<ManagerTableInfo, String>("position"));
        salary.setCellValueFactory(new PropertyValueFactory<ManagerTableInfo, String>("salary"));
        numOfSales.setCellValueFactory(new PropertyValueFactory<ManagerTableInfo, Integer>("numOfSales"));

        listTableInfo = DatabaseConnector.getManagerData();

        table.setItems(listTableInfo);
    }

    private void setManagerInfo() {
        surnameField.setText(manager.getSurname());
        nameField.setText(manager.getName());
        patronymicField.setText(manager.getPatronymic());
        positionField.setText(manager.getPosition());
        salaryField.setText(manager.getSalary());
        numOfSalesField.setText(manager.getNumOfSales());
        phoneField.setText(manager.getPhone());
        Image image = new Image(getClass().getResourceAsStream(PHOTO_SRC + manager.getPhoto()));
        imgClient.setImage(image);
    }

    private void setValueFromTable() {
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ManagerTableInfo temp = table.getItems().get(table.getSelectionModel().getSelectedIndex());

                DatabaseConnector databaseConnector = new DatabaseConnector();
                Connection connection = databaseConnector.getConnection();

                try {
                    PreparedStatement statement = connection.prepareStatement(Query.getManagerInfo);
                    statement.setInt(1, temp.getId());
                    ResultSet queryResult = statement.executeQuery();

                    while (queryResult.next()) {
                        int id = queryResult.getInt(1);
                        String sername = queryResult.getString(2);
                        String name = queryResult.getString(3);
                        String patronymic = queryResult.getString(4);
                        String position = queryResult.getString(5);
                        String salary = queryResult.getString(6);
                        String numOfSales = queryResult.getString(7);
                        String phone = queryResult.getString(8);
                        String photo = queryResult.getString(9);

                        manager = new Manager(id, sername, name, patronymic, position, salary, numOfSales, phone, photo);
                        setManagerInfo();
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
