package toyota_app.controllers;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import toyota_app.model.Client;

public class ClientController {
    @FXML private MFXTextField addressField;

    @FXML private MFXTextField cityField;

    @FXML private MFXTextField nameField;

    @FXML private MFXTextField passportField;

    @FXML private MFXTextField patronymicField;

    @FXML private MFXTextField phoneField;

    @FXML private MFXTextField surnameField;

    public void setData(Client client) {
        this.surnameField.setText(client.getSername());
        this.nameField.setText(client.getName());
        this.patronymicField.setText(client.getPatronymic());
        this.passportField.setText(client.getPassport());
        this.cityField.setText(client.getCity());
        this.addressField.setText(client.getAddress());
        this.phoneField.setText(client.getPhone());
    }

    public MFXTextField getAddressField() {
        return addressField;
    }

    public MFXTextField getCityField() {
        return cityField;
    }

    public MFXTextField getNameField() {
        return nameField;
    }

    public MFXTextField getPassportField() {
        return passportField;
    }

    public MFXTextField getPatronymicField() {
        return patronymicField;
    }

    public MFXTextField getPhoneField() {
        return phoneField;
    }

    public MFXTextField getSurnameField() {
        return surnameField;
    }
}
