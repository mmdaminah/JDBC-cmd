package Main;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
      var dataSource = new DataSource();
      var numberOfTries = 0;
      Admin admin;
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

          admin = dataSource.getAdmin(username,password).orElse(null);

          if(admin != null)
              break;

          numberOfTries++;
      }while(numberOfTries != 3);
      if(admin != null){
          while(true){
              System.out.println(menu);
              var option = reader.nextInt();
              switch (option) {
                  case 1 -> {
                      var allAdmins = dataSource.getAllAdmins();
                      allAdmins.forEach(admin1 -> System.out.println(admin1.toString()));
                  } case 2 -> dataSource.updateAdmin();
                  case 3 -> {
                      System.out.println("Please Select Field You Want To Search In:");
                      var searchMenu = """
                                    1. first name   
                                    2. last name
                                    3. user name
                                    4. password
                                    5. national code
                                    6. address 
                              """;
                      System.out.println(searchMenu);
                      var fieldNumber = reader.nextInt();
                      switch (fieldNumber) {
                          case 1 -> {
                              System.out.println("Please insert :");
                              var firstName = reader.next();
                              var fNameResult = dataSource.searchAdmins("first_name", firstName);
                              fNameResult.forEach(item -> System.out.println(item.toString()));
                          }
                          case 2 -> {
                              System.out.println("Please insert :");
                              var lastName = reader.next();
                              var lNameResult = dataSource.searchAdmins("last_name", lastName);
                              lNameResult.forEach(item -> System.out.println(item.toString()));
                          }
                          case 3 -> {
                              System.out.println("Please insert :");
                              var username = reader.next();
                              var usernameResult = dataSource.searchAdmins("username", username);
                              usernameResult.forEach(item -> System.out.println(item.toString()));
                          }
                          case 4 -> {
                              System.out.println("Please insert :");
                              var password = reader.next();
                              var passResult = dataSource.searchAdmins("password", password);
                              passResult.forEach(item -> System.out.println(item.toString()));
                          }
                          case 5 -> {
                              System.out.println("Please insert :");
                              var nationalCode = reader.next();
                              var natResult = dataSource.searchAdmins("national_code", nationalCode);
                              natResult.forEach(item -> System.out.println(item.toString()));
                          }
                          case 6 -> {
                              System.out.println("Please insert :");
                              var address = reader.next();
                              var addResult = dataSource.searchAdmins("address", address);
                              addResult.forEach(item -> System.out.println(item.toString()));
                          }
                          default -> System.out.println("Wrong Input!");
                      }
                  }
                  case 4 -> dataSource.deleteAdmin();
                  case 5 -> {
                      System.out.println("Please Enter First name:");
                      var firstName = reader.next();
                      System.out.println("Please Enter Last name:");
                      var lastName = reader.next();
                      System.out.println("Please Enter Username:");
                      var username = reader.next();
                      System.out.println("Please Enter Password:");
                      var password = reader.next();
                      System.out.println("Please Enter National Code:");
                      var nationalCode = reader.next();
                      System.out.println("Please Enter Address:");
                      var address = reader.next();
                      var result = dataSource.addAdmin(firstName,lastName,username,password,nationalCode,address);
                      System.out.println(result);
                  }
                  case 6 -> System.exit(0);
                  default -> System.out.println("Please insert a valid Option.");
              }
          }
      }
  }
}
