package Main;
import java.util.Scanner;
import Main.LoginStatus;
import Main.DataSource;

public class Main {
  public static void main(String[] args) {
      var dataSource = new DataSource();
      var numberOfTries = 0;
      LoginStatus status;
      var menu = """
              1. Get All admins 
              2. Update admin
              3. Search
              4. Delete admin
              5. Add Admin
              6. Exit
              """;

      var reader = new Scanner(System.in);
      do{
          System.out.println("Please insert your username:");
          var username = reader.next();

          System.out.println("Please insert your password:");
          var password = reader.next();

          status = dataSource.getAdmin(username,password);
          System.out.println(status.message);

          if(status.isLogined)
              break;

          numberOfTries++;
      }while(numberOfTries != 3);
      if(status.isLogined){
          while(true){
              System.out.printf("User:%s %s\n",status.admin.firstName,status.admin.lastName);
              System.out.println(menu);
              var option = reader.nextInt();
              switch (option){
                  case 1:
                      dataSource.getAllAdmins();
                      break;
                  case 2:
                      System.out.println("Please insert Admin id");
                      var id = reader.next();
                      var admin = dataSource.getAdmin(id);
                      System.out.println(admin.toString());
                      break;
                  case 3:
                      break;
                  case 4:
                      dataSource.deleteAdmin();
                      break;
                  case 5:
                      var result = dataSource.addAdmin();
                      System.out.println(result);
                      break;
                  case 6:
                      System.exit(0);
                      break;
                  default:
                      System.out.println("Please insert a valid Option.");
                      break;
              }
          }
      }
  }
}
