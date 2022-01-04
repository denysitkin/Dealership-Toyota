package toyota_app.model;

public class Manager {
    private int id;
    private String surname;
    private String name;
    private String patronymic;
    private String position;
    private String salary;
    private String numOfSales;
    private String photo;
    private String phone;

    public Manager(int id, String surname, String name, String patronymic, String position, String salary, String numOfSales, String phone, String photo) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.position = position;
        this.salary = salary;
        this.numOfSales = numOfSales;
        this.phone = phone;
        this.photo = photo;
    }

    public Manager() {}

    public String getPhone() {
        return phone;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public int getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getPosition() {
        return position;
    }

    public String getSalary() {
        return salary;
    }

    public String getNumOfSales() {
        return numOfSales;
    }
}
