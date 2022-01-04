package toyota_app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import toyota_app.controllers.ScreenController;
import toyota_app.db.DatabaseConnector;

import java.io.IOException;

public class CarDealearshipDB extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        try {
            Font.loadFont(getClass().getResourceAsStream("/toyota_app/font/Montserrat-Regular.ttf"), 14);
            Font.loadFont(getClass().getResourceAsStream("/toyota_app/font/Montserrat-Medium.ttf"), 14);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/toyota_app/user interfaces/login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setResizable(false);
            stage.getIcons().add(new Image(getClass().getResourceAsStream( "/toyota_app/img/icon.png" )));
            stage.setTitle("Toyota Management System");
            scene.getStylesheets().add(getClass().getResource("/toyota_app/css/menu.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("/toyota_app/css/main.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}