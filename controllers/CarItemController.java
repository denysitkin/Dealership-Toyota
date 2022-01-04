package toyota_app.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import toyota_app.model.CarItem;

public class CarItemController {

    @FXML private ImageView carImg;

    @FXML private Label carModel;

    @FXML private Label bodyType;

    public void setData(CarItem carItem) {
        Image image = new Image(getClass().getResourceAsStream(carItem.getImgSrc()));
        carImg.setImage(image);

        carModel.setText(carItem.getCarModel());
        bodyType.setText(carItem.getBodyType());
    }

    @FXML
    public void openCarInfo(MouseEvent event) {
        if (ScreenController.fxmlName == "car-select.fxml") {
            CarOrderController.initData(carModel.getText());
            Stage stage = (Stage) ScreenController.scene.getWindow();
            ScreenController.change(stage, "car-order.fxml");
        } else if (ScreenController.fxmlName == "car-available.fxml") {
            CarInfoController.initData(carModel.getText());
            Stage stage = (Stage) ScreenController.scene.getWindow();
            ScreenController.change(stage, "car-info.fxml");
        } else if (ScreenController.fxmlName == "car-sale.fxml") {
            SaleController.initData(carModel.getText());
            Stage stage = (Stage) ScreenController.scene.getWindow();
            ScreenController.change(stage, "sale-registration.fxml");
        }
    }

}
