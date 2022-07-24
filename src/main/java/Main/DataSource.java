package Main;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class DataSource {
    public static final String url = "jdbc:postgresql://localhost:5432/JDBC";
    public static final String username = "postgres";
    public static final String password = "13761376";
    public Connection connection;
    DataSource(){
        try{
            var connection = DriverManager.getConnection(url,username,password);
            System.out.println("Successfully Connected to DB;");
            this.connection = connection;
        }
        catch (SQLException e){
            throw new RuntimeException("Unable to connect to the DB;");
        }
    }
    public Optional<Admin> getAdmin(String username, String password){
        var getAdminSql = """
                select * from admins  \
                where username = ? and password = ?;
                """;
        try(var getAdminStatement = this.connection.prepareStatement(getAdminSql)){
            getAdminStatement.setString(1,username);
            getAdminStatement.setString(2,password);
            var result = getAdminStatement.executeQuery();

            if(result.next()){
                var admin = extractProperties(result);
                return Optional.of(admin);
            }
            else {
                return Optional.empty();
            }
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public Admin getAdmin(String id){
        var getAdminSql = """
                select * from admins  \
                where id = ?;
                """;
        try(var getAdminStatement = this.connection.prepareStatement(getAdminSql)){
            getAdminStatement.setString(1,id);
            var result = getAdminStatement.executeQuery();

            if(result.next()){

               return extractProperties(result);

            }
            else {
                return null;
            }
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public List<Admin> getAllAdmins(){
        List<Admin> allAdmins = new ArrayList<Admin>();
        var getAllAdminsSql = """
                select * from admins;
                """;
        try(var getAllAdminsStatement = this.connection.prepareStatement(getAllAdminsSql)){
            var result = getAllAdminsStatement.executeQuery();
            try{
                while(result.next()){
                    var admin = extractProperties(result);
                    allAdmins.add(admin);
               }
                return allAdmins;
            }
            catch(SQLException e){
                    throw new RuntimeException(e);
                }
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public Admin extractProperties(ResultSet result){
        try{

        var id = result.getString(1);
        var firstName = result.getString(2);
        var lastName = result.getString(3);
        var userName = result.getString(4);
        var userPassword = result.getString(5);
        var nationalCode = result.getString(6);
        var address = result.getString(7);

        return new Admin(
                id,firstName,lastName,
                userName,userPassword,
                nationalCode,address
        );
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public String addAdmin(String firstName,String lastName, String username, String password, String nationalCode,String address){
        var insertSql = """
                insert into admins \
                (first_name, last_name, username, password, national_code, address)
                values(?,?,?,?,?,?)
                """;

        try(var insertStatement = this.connection.prepareStatement(insertSql)){
            insertStatement.setString(1,firstName);
            insertStatement.setString(2,lastName);
            insertStatement.setString(3,username);
            insertStatement.setString(4,password);
            insertStatement.setString(5,nationalCode);
            insertStatement.setString(6,address);
            var result = insertStatement.execute();

            return !result ? "Successfully Added" : "Failed To Add";
        }
        catch (SQLException e){
            System.out.println("Exception occured!!!");
            return e.getMessage();
        }
    }

    public void updateAdmin(){
        var reader = new Scanner(System.in);
        var selectSql = """
                select * from admins where id = ?
                """;
        var updateSql = """
                update admins set first_name = ?,
                last_name = ?, username = ?, password = ?, national_code = ?, address = ?
                where id = ?
                """;

        try(
                var selectStatement = connection.prepareStatement(selectSql);
                var updateStatement = connection.prepareStatement(updateSql);
                ){
            System.out.println("Please Enter Admin ID:");
            var id = reader.nextInt();

            selectStatement.setInt(1,id);
            var result = selectStatement.executeQuery();

            if(result.next()){
                var admin = extractProperties(result);
                var updateMenu = """
                     1. first name   
                     2. last name
                     3. user name
                     4. password
                     5. national code
                     6. address
                     7 . CONFIRM UPDATE
                     """;
                while(true){
                    System.out.println(updateMenu);
                    var option = reader.nextInt();
                    if(option == 7){
                        updateStatement.setString(1,admin.firstName);
                        updateStatement.setString(2,admin.lastName);
                        updateStatement.setString(3,admin.userName);
                        updateStatement.setString(4,admin.password);
                        updateStatement.setString(5,admin.nationalCode);
                        updateStatement.setString(6,admin.address);
                        updateStatement.setInt(7,Integer.parseInt(admin.id));
                        updateStatement.execute();
                        break;
                    }
                    switch (option){
                        case 1:
                            System.out.printf("First Name : %s\nNew First Name:", admin.firstName);
                            var firstName = reader.next();
                            admin.setFirstName(firstName);
                            break;
                        case 2:
                            System.out.printf("Last Name : %s\nNew Last Name:", admin.lastName);
                            var lastName = reader.next();
                            admin.setLastName(lastName);
                            break;
                        case 3:
                            System.out.printf("User Name : %s\nNew Username:", admin.userName);
                            var username = reader.next();
                            admin.setUserName(username);
                            break;
                        case 4:
                            System.out.printf("Password : %s\nNew Password:", admin.password);
                            var password = reader.next();
                            admin.setLastName(password);
                            break;
                        case 5:
                            System.out.printf("National Code : %s\nNew National Code:", admin.nationalCode);
                            var  nationalCode= reader.next();
                            admin.setNationalCode(nationalCode);
                            break;
                        case 6:
                            System.out.printf("Address : %s\nNew Address:", admin.address);
                            var address = reader.next();
                            admin.setAddress(address);
                            break;
                        default:
                            System.out.println("Insert A Valid Option.");
                            break;
                    }
                }
            }
            else{
                System.out.println("Admin Doesn't Exist");
            }
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    void deleteAdmin(){
        var deleteSql = """
                delete from admins \
                where id = ?
                """;
        var reader = new Scanner(System.in);
        System.out.println("Please Enter ID Of Admin:");

        var id = reader.nextInt();

        try(var deleteStatement = this.connection.prepareStatement(deleteSql)){
            deleteStatement.setInt(1,id);
            deleteStatement.execute();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public List<Admin> searchAdmins(String field, String value){
       var admins = getAllAdmins();
       List<Admin> foundAdmins = switch (field) {
           case "first_name" ->
                   admins.stream().filter(admin -> admin.firstName.equals(value)).collect(Collectors.toList());
           case "last_name" ->
                   admins.stream().filter(admin -> admin.lastName.equals(value)).collect(Collectors.toList());
           case "username" ->
                   admins.stream().filter(admin -> admin.userName.equals(value)).collect(Collectors.toList());
           case "password" ->
                   admins.stream().filter(admin -> admin.password.equals(value)).collect(Collectors.toList());
           case "national_code" ->
                   admins.stream().filter(admin -> admin.nationalCode.equals(value)).collect(Collectors.toList());
           case "address" -> admins.stream().filter(admin -> admin.address.equals(value)).collect(Collectors.toList());
           default -> new ArrayList<Admin>();
       };
        return foundAdmins;
       }
}
