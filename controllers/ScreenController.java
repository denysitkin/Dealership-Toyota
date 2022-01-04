package toyota_app.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;


public class ScreenController {
    private static final String URL = "/toyota_app/user interfaces/";

    public static Parent root = null;
    public static Scene scene = null;
    public static String fxmlName = null;

    public static void change(final Stage stage, final String fxml) {
        try {
            fxmlName = fxml;
            root = FXMLLoader.load(ScreenController.class.getResource(URL + fxml));
            scene =  new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
