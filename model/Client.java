package toyota_app.model;

public class Client {
    private int id;
    private String sername;
    private String name;
    private String patronymic;
    private String city;
    private String passport;
    private String phone;
    private String address;
    private String photo;

    public Client() { }

    public Client(int id, String sername, String name, String patronymic, String city, String passport, String address, String phone, String photo) {
        this.id = id;
        this.sername = sername;
        this.name = name;
        this.patronymic = patronymic;
        this.city = city;
        this.passport = passport;
        this.phone = phone;
        this.address = address;
        this.photo = photo;
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

    public String getAddress() {
        return address;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
