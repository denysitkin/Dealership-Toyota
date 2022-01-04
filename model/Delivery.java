package toyota_app.model;

import io.github.palexdev.materialfx.controls.MFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;

import java.sql.Timestamp;
import java.util.Date;

public class Delivery {

    public static int id = 1;
    private int carQuantity;
    private Timestamp dateDelivery;
    private String typeDelivery;
    private String totalPriceDelivery;

    public Delivery(int carQuantity, Timestamp dateDelivery, String typeDelivery, String totalPriceDelivery) {
        this.carQuantity = carQuantity;
        this.dateDelivery = dateDelivery;
        this.typeDelivery = typeDelivery;
        this.totalPriceDelivery = totalPriceDelivery;
    }

    public int getCarQuantity() {
        return carQuantity;
    }

    public Timestamp getDateDelivery() {
        return dateDelivery;
    }

    public String getTypeDelivery() {
        return typeDelivery;
    }

    public String getTotalPriceDelivery() {
        return totalPriceDelivery;
    }
}
