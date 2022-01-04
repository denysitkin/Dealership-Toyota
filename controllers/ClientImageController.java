package toyota_app.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientImageController {
    private static final String PHOTO_SRC = "/toyota_app/img/clients/";

    private String photoSrc;
    private Image clientImg;

    @FXML private ImageView img;

    public void setData(String photoSrc) {
        this.photoSrc = photoSrc;
        clientImg = new Image(getClass().getResourceAsStream(PHOTO_SRC + photoSrc));
        img.setImage(clientImg);
    }
}
