package toyota_app.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class CarImageController {
    private final String carBrand = "Toyota";
    private static final String PHOTO_SRC = "/toyota_app/img/";

    private String carPhotoSrc;
    private Image carImg;
    private String carModel;

    @FXML private Label carLabel;

    @FXML private ImageView img;

    public void setData(String photoSrc, String carModel) {
        carPhotoSrc = photoSrc;
        carImg = new Image(getClass().getResourceAsStream(PHOTO_SRC + carPhotoSrc));
        img.setImage(carImg);
        carLabel.setText(carBrand.toUpperCase() + " " + carModel.toUpperCase());
    }
}
