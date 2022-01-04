package toyota_app.controllers;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import toyota_app.model.Equipment;

public class EquipmentController {
    @FXML
    private MFXTextField bodyTypeField;

    @FXML
    private MFXTextField colorField;

    @FXML
    private MFXTextField priceField;

    @FXML
    private MFXTextField engineCapacityField;

    @FXML
    private MFXTextField equipmentField;

    @FXML
    private MFXTextField fuelTypeField;

    @FXML
    private MFXTextField interiorTypeField;

    @FXML
    private MFXTextField powerField;

    @FXML
    private MFXTextField transmissionField;

    public void setData(Equipment equipment) {
        this.equipmentField.setText(equipment.getCarEquipment());
        this.engineCapacityField.setText(equipment.getCarEngineCapacity());
        this.fuelTypeField.setText(equipment.getCarFuelType());
        this.powerField.setText(equipment.getCarPower());
        this.transmissionField.setText(equipment.getCarTransmission());
        this.bodyTypeField.setText(equipment.getCarBodyType());
        this.interiorTypeField.setText(equipment.getCarInteriorMaterial());
        this.colorField.setText(equipment.getCarColor());
        this.priceField.setText(equipment.getCarPrice().toString());
    }

    public void setBodyTypeField(String bodyTypeField) {
        this.bodyTypeField.setText(bodyTypeField);
    }

    public void setColorField(String colorField) {
        this.colorField.setText(colorField);
    }

    public void setPriceField(String priceField) {
        this.priceField.setText(priceField);
    }

    public void setEngineCapacityField(String engineCapacityField) {
        this.engineCapacityField.setText(engineCapacityField);
    }

    public void setEquipmentField(String equipmentField) {
        this.equipmentField.setText(equipmentField);
    }

    public void setFuelTypeField(String fuelTypeField) {
        this.fuelTypeField.setText(fuelTypeField);
    }

    public void setInteriorTypeField(String interiorTypeField) {
        this.interiorTypeField.setText(interiorTypeField);
    }

    public void setPowerField(String powerField) {
        this.powerField.setText(powerField);
    }

    public void setTransmissionField(String transmissionField) {
        this.transmissionField.setText(transmissionField);
    }

    public MFXTextField getBodyTypeField() {
        return bodyTypeField;
    }

    public MFXTextField getColorField() {
        return colorField;
    }

    public MFXTextField getPriceField() {
        return priceField;
    }

    public MFXTextField getEngineCapacityField() {
        return engineCapacityField;
    }

    public MFXTextField getEquipmentField() {
        return equipmentField;
    }

    public MFXTextField getFuelTypeField() {
        return fuelTypeField;
    }

    public MFXTextField getInteriorTypeField() {
        return interiorTypeField;
    }

    public MFXTextField getPowerField() {
        return powerField;
    }

    public MFXTextField getTransmissionField() {
        return transmissionField;
    }
}
