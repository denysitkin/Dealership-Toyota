package toyota_app.model;

public class ManagerTableInfo {
    private int id;
    private String surname;
    private String name;
    private String patronymic;
    private String position;
    private String salary;
    private String numOfSales;

    public ManagerTableInfo(int id, String surname, String name, String patronymic, String position, String salary, String numOfSales) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.position = position;
        this.salary = salary;
        this.numOfSales = numOfSales;
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
