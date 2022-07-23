package Main;

public class Admin {
    public String id = null;
    public String firstName = null;
    public String lastName = null;
    public String userName = null;
    public String password = null;
    public String nationalCode = null;
    public String address = null;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", nationalCode='" + nationalCode + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public Admin(String id, String firstName, String lastName, String userName, String password, String nationalCode, String address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.nationalCode = nationalCode;
        this.address = address;
    }
}
