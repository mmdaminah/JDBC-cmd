package Main;
import Main.Admin;
public class LoginStatus {
    public Admin admin;
    public boolean isLogined;
    public String message;

    public LoginStatus(Admin admin, boolean isLogined, String message) {
        this.admin = admin;
        this.isLogined = isLogined;
        this.message = message;
    }
}
