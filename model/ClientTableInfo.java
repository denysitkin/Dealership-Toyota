package toyota_app.model;

import java.math.BigDecimal;

public class ClientTableInfo {
    private int id;
    private String sername;
    private String name;
    private String patronymic;
    private String city;
    private String passport;
    private String phone;

    public ClientTableInfo(int id, String sername, String name, String patronymic, String city, String passport, String phone) {
        this.id = id;
        this.sername = sername;
        this.name = name;
        this.patronymic = patronymic;
        this.city = city;
        this.passport = passport;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getSername() {
        return sername;
    }

    public String getName() {
        return name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getCity() {
        return city;
    }

    public String getPassport() {
        return passport;
    }

    public String getPhone() {
        return phone;
    }
}
