package toyota_app.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import toyota_app.db.DatabaseConnector;
import toyota_app.queries.Query;
import java.sql.*;

public class LoginController {

    public static String nameUser;
    public static int idUser;
    public static String position;

    @FXML
    private MFXButton btnLogin;

    @FXML
    private Label labelToyota;

    @FXML
    private MFXTextField loginField;

    @FXML
    private MFXPasswordField passField;

    @FXML
    private AnchorPane loginMessage;

    @FXML
    public void login(ActionEvent event) {

        if(loginField.getText().isBlank() == false && passField.getText().isBlank() == false ) {
            validateLogin();
        } else {
            loginMessage.setVisible(true);
        }

    }

    private void validateLogin() {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        Connection connection = databaseConnector.getConnection();

        loginMessage.setVisible(true);

        try {
            PreparedStatement statement = connection.prepareStatement(Query.verifyLogin);
            statement.setString(1, loginField.getText());
            statement.setString(2, passField.getPassword());
            ResultSet queryResult = statement.executeQuery();

            while (queryResult.next()) {
                if (queryResult.getInt(4) == 1) {
                    position = queryResult.getString(1);
                    idUser = queryResult.getInt(2);
                    nameUser = queryResult.getString(3);
                    Stage stage = (Stage) btnLogin.getScene().getWindow();
                    ScreenController.change(stage, "main-page.fxml");
                } else {
                    System.out.println("error");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection = databaseConnector.closeConnection();
        }
    }

}
