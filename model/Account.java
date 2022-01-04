package toyota_app.model;

public class Account {
    private int idUser;
    private String surnameUser;
    private String nameUser;
    private String loginUser;
    private String passwordUser;

    public Account(int idUser, String surnameUser, String nameUser, String loginUser, String passwordUser) {
        this.idUser = idUser;
        this.surnameUser = surnameUser;
        this.nameUser = nameUser;
        this.loginUser = loginUser;
        this.passwordUser = passwordUser;
    }

    public int getIdUser() {
        return idUser;
    }

    public String getSurnameUser() {
        return surnameUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public String getLoginUser() {
        return loginUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }
}
