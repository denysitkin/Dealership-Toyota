package toyota_app.model;

import java.math.BigDecimal;

public class CarTableInfo {

    private int id;
    private String brand;
    private String model;
    private String year;
    private String equipment;
    private String color;
    private BigDecimal price;
    private String typePayment;

    public CarTableInfo(int id, String brand, String model, String year, String equipment, String color, BigDecimal price) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.equipment = equipment;
        this.color = color;
        this.price = price;
    }

    public CarTableInfo(int idContract, String brand, String model, String year, String equipment, String color, String typePayment) {
        this.id = idContract;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.equipment = equipment;
        this.color = color;
        this.typePayment = typePayment;
    }

    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getYear() {
        return year;
    }

    public String getEquipment() {
        return equipment;
    }

    public String getColor() {
        return color;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getTypePayment() {
        return typePayment;
    }
}
