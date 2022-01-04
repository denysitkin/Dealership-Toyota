package toyota_app.model;

import java.math.BigDecimal;

public class Equipment {
    private String carEquipment;
    private String carEngineCapacity;
    private String carFuelType;
    private String carPower;
    private String carTransmission;
    private String carBodyType;
    private String carInteriorMaterial;
    private String carColor;
    private BigDecimal carPrice;
    private String photoSrc;
    private int carID;
    private int numOfCars;
    private String nameModel;
    private String dateSale;
    private String typePayment;

    public Equipment(int carID, String carEquipment, String carEngineCapacity, String carFuelType, String carPower, String carTransmission, String carBodyType, String carInteriorMaterial, String carColor, BigDecimal carPrice, String photoSrc) {
        this.carID = carID;
        this.carEquipment = carEquipment;
        this.carEngineCapacity = carEngineCapacity;
        this.carFuelType = carFuelType;
        this.carPower = carPower;
        this.carTransmission = carTransmission;
        this.carBodyType = carBodyType;
        this.carInteriorMaterial = carInteriorMaterial;
        this.carColor = carColor;
        this.carPrice = carPrice;
        this.photoSrc = photoSrc;
    }

    public Equipment(int carID, String carEquipment, String carEngineCapacity, String carFuelType, String carPower, String carTransmission, String carBodyType, String carInteriorMaterial, String carColor, BigDecimal carPrice, String typePayment, String dateSale, String nameModel, String photoSrc) {
        this.carID = carID;
        this.carEquipment = carEquipment;
        this.carEngineCapacity = carEngineCapacity;
        this.carFuelType = carFuelType;
        this.carPower = carPower;
        this.carTransmission = carTransmission;
        this.carBodyType = carBodyType;
        this.carInteriorMaterial = carInteriorMaterial;
        this.carColor = carColor;
        this.carPrice = carPrice;
        this.photoSrc = photoSrc;
        this.dateSale = dateSale;
        this.typePayment = typePayment;
        this.nameModel = nameModel;
    }

    public Equipment(int carID, String carEquipment, String carEngineCapacity, String carFuelType, String carPower, String carTransmission, String carBodyType, String carInteriorMaterial, String carColor, BigDecimal carPrice, int numOfCars, String photoSrc) {
        this.carID = carID;
        this.carEquipment = carEquipment;
        this.carEngineCapacity = carEngineCapacity;
        this.carFuelType = carFuelType;
        this.carPower = carPower;
        this.carTransmission = carTransmission;
        this.carBodyType = carBodyType;
        this.carInteriorMaterial = carInteriorMaterial;
        this.carColor = carColor;
        this.carPrice = carPrice;
        this.photoSrc = photoSrc;
        this.numOfCars = numOfCars;
    }

    public Equipment(int carID, String carEquipment, String carEngineCapacity, String carFuelType, String carPower, String carTransmission, String carBodyType, String carInteriorMaterial, String carColor, BigDecimal carPrice, int numOfCars, String nameModel, String photoSrc) {
        this.carID = carID;
        this.carEquipment = carEquipment;
        this.carEngineCapacity = carEngineCapacity;
        this.carFuelType = carFuelType;
        this.carPower = carPower;
        this.carTransmission = carTransmission;
        this.carBodyType = carBodyType;
        this.carInteriorMaterial = carInteriorMaterial;
        this.carColor = carColor;
        this.carPrice = carPrice;
        this.photoSrc = photoSrc;
        this.numOfCars = numOfCars;
        this.nameModel = nameModel;
    }

    public String getNameModel() {
        return nameModel;
    }

    public String getDateSale() {
        return dateSale;
    }

    public String getTypePayment() {
        return typePayment;
    }

    public int getCarID() {
        return carID;
    }

    public int getNumOfCars() {
        return numOfCars;
    }

    public String getCarEquipment() {
        return carEquipment;
    }

    public String getCarEngineCapacity() {
        return carEngineCapacity;
    }

    public String getCarFuelType() {
        return carFuelType;
    }

    public String getCarPower() {
        return carPower;
    }

    public String getCarTransmission() {
        return carTransmission;
    }

    public String getCarBodyType() {
        return carBodyType;
    }

    public String getCarInteriorMaterial() {
        return carInteriorMaterial;
    }

    public String getCarColor() {
        return carColor;
    }

    public BigDecimal getCarPrice() {
        return carPrice;
    }

    public String getPhotoSrc() {
        return photoSrc;
    }

    public void setCarEquipment(String carEquipment) {
        this.carEquipment = carEquipment;
    }

    public void setCarEngineCapacity(String carEngineCapacity) {
        this.carEngineCapacity = carEngineCapacity;
    }

    public void setCarFuelType(String carFuelType) {
        this.carFuelType = carFuelType;
    }

    public void setCarPower(String carPower) {
        this.carPower = carPower;
    }

    public void setCarTransmission(String carTransmission) {
        this.carTransmission = carTransmission;
    }

    public void setCarBodyType(String carBodyType) {
        this.carBodyType = carBodyType;
    }

    public void setCarInteriorMaterial(String carInteriorMaterial) {
        this.carInteriorMaterial = carInteriorMaterial;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public void setCarPrice(BigDecimal carPrice) {
        this.carPrice = carPrice;
    }

    public void setPhotoSrc(String photoSrc) {
        this.photoSrc = photoSrc;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public void setNumOfCars(int numOfCars) {
        this.numOfCars = numOfCars;
    }
}
