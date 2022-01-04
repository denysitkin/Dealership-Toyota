package toyota_app.controllers;

import io.github.palexdev.materialfx.controls.MFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import toyota_app.db.DatabaseConnector;
import toyota_app.model.Delivery;
import toyota_app.queries.Query;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ResourceBundle;
import java.util.zip.InflaterInputStream;

public class DeliveryController extends CarOrderController implements Initializable {

    @FXML
    private MFXRadioButton checkBoxBarge;

    @FXML
    private MFXRadioButton checkBoxPlane;

    @FXML
    private MFXRadioButton checkBoxTruck;

    @FXML
    private MFXTextField dateField;

    @FXML
    private MFXTextField quantityField;

    @FXML
    private MFXTextField numberField;

    @FXML
    private MFXTextField totalPriceField;

    @FXML
    private Label errorField;

    @FXML
    private Label priceForBarge;

    @FXML
    private Label priceForPlane;

    @FXML
    private Label priceForTruck;

    private String priceCar;

    private String numOfOrder;

    private BigDecimal totalCost;

    public void setPriceCar(String priceCar) {
        this.priceCar = priceCar;
    }

    public String getPriceCar() {
        return priceCar;
    }

    public void setData(Delivery delivery) {
        this.numberField.setText(String.valueOf(Delivery.id));
        this.quantityField.setText(String.valueOf(delivery.getCarQuantity()));
        this.totalPriceField.setText(delivery.getTotalPriceDelivery());
        this.dateField.setText(delivery.getDateDelivery().toString());
    }

    public void setNumOfOrder(String numOfOrder) {
        this.numOfOrder = numOfOrder;
    }

    public MFXRadioButton getCheckBoxBarge() {
        return checkBoxBarge;
    }

    public MFXRadioButton getCheckBoxPlane() {
        return checkBoxPlane;
    }

    public MFXRadioButton getCheckBoxTruck() {
        return checkBoxTruck;
    }

    public MFXTextField getQuantityField() {
        return quantityField;
    }

    public MFXTextField getNumberField() {
        return numberField;
    }

    public MFXTextField getTotalPriceField() {
        return totalPriceField;
    }

    public Label getPriceForBarge() {
        return priceForBarge;
    }

    public Label getPriceForPlane() {
        return priceForPlane;
    }

    public Label getPriceForTruck() {
        return priceForTruck;
    }

    public void setNumberField(MFXTextField numberField) {
        this.numberField = numberField;
    }

    private void getNumberOrder() {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        Connection connection = databaseConnector.getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet queryResult = statement.executeQuery(Query.getNumOfOrder);
            if (!queryResult.next()) {
                numberField.setText("1");
            } else {
                statement = connection.createStatement();
                queryResult = statement.executeQuery(Query.getLastNumOfOrder);
                while (queryResult.next()) {
                    numberField.setText(String.valueOf(queryResult.getInt(1) + 1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        dateField.setText(timestamp.toString());

        ToggleGroup toggleGroup = new ToggleGroup();
        checkBoxBarge.setToggleGroup(toggleGroup);
        checkBoxPlane.setToggleGroup(toggleGroup);
        checkBoxTruck.setToggleGroup(toggleGroup);

        getNumberOrder();

        quantityField.textProperty().addListener((observable, oldValue, newValue) -> {

            if (Integer.valueOf(quantityField.getText()) <  0) {
                checkBoxTruck.setDisable(true);
                checkBoxPlane.setDisable(true);
                checkBoxBarge.setDisable(true);
                errorField.setText("Количество должно быть больше 0");
            } else if (Integer.valueOf(quantityField.getText()) > 20){
                checkBoxTruck.setDisable(true);
                checkBoxPlane.setDisable(true);
                checkBoxBarge.setDisable(true);
                errorField.setText("Количество не может превышать 20");
            } else if (Integer.valueOf(quantityField.getText()) > 12){
                checkBoxTruck.setDisable(true);
                checkBoxBarge.setDisable(true);
            } else if (Integer.valueOf(quantityField.getText()) > 5) {
                checkBoxTruck.setDisable(true);
            } else {
                checkBoxTruck.setDisable(false);
                checkBoxPlane.setDisable(false);
                checkBoxBarge.setDisable(false);
                errorField.setText("");
            }

            BigDecimal currentPrice = new BigDecimal(priceCar);
            BigDecimal itemCost  = BigDecimal.ZERO;
            totalCost = BigDecimal.ZERO;
            int numCars = Integer.parseInt(quantityField.getText());
            itemCost  = currentPrice.multiply(BigDecimal.valueOf(numCars));
            totalCost = totalCost.add(itemCost);
            if (checkBoxTruck.isSelected()) {
                totalCost = totalCost.add(new BigDecimal(priceForTruck.getText()));
            }
            totalPriceField.setText(totalCost.toString());

        });

        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
        {
            public void changed(ObservableValue<? extends Toggle> ob,
                                Toggle o, Toggle n)
            {

                RadioButton rb = (RadioButton)toggleGroup.getSelectedToggle();

                if (rb != null) {
                    int s = 0;
                    BigDecimal currentPrice = new BigDecimal(priceCar);
                    BigDecimal itemCost  = BigDecimal.ZERO;
                    totalCost = BigDecimal.ZERO;
                    int numCars = Integer.parseInt(quantityField.getText());
                    itemCost  = currentPrice.multiply(BigDecimal.valueOf(numCars));
                    totalCost = totalCost.add(itemCost);
                    if (rb.getText().equals("Грузовик")) {
                        s = Integer.parseInt(priceForTruck.getText());
                    } else if (rb.getText().equals("Баржа")) {
                        s = Integer.parseInt(priceForBarge.getText());
                    } else if (rb.getText().equals("Самолет")) {
                        s = Integer.parseInt(priceForPlane.getText());
                    }
                    System.out.println(s);
                    totalCost = totalCost.add(BigDecimal.valueOf(s));
                    System.out.println(totalCost);
                    totalPriceField.setText(totalCost.toString());
                }
            }
        });
    }
}
