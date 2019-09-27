import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserController {

    //Lists all the fields from the Users table
    public static void listUsers() {

        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT * FROM Users");
            ResultSet results = ps.executeQuery();


            while (results.next()){
                int UserID = results.getInt(1);
                String FirstName = results.getString(2);
                String LastName = results.getString(3);
                String Gender = results.getString(4);
                String Username = results.getString(5);
                String Password = results.getString(6);
                String Email = results. getString(7);
                System.out.println(UserID + " " + FirstName + " "  + LastName + " " + Username + " " + Gender + " " + Password + " " + Email);
            }

        } catch (Exception exception) {
            System.out.println("Database error " + exception.getMessage());
        }

    }

    //Inserts a new user into the Users table
    public static void addUser(String firstName, String lastName, String gender, String username, String password, String email, double idealWeight)
    {
        try{
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Users (FirstName, LastName, Gender, Username, Password, Email, IdealWeight) VALUES (?,?,?,?,?,?,?)");

            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, gender);
            ps.setString(4, username);
            ps.setString(5, password);
            ps.setString(6, email);
            ps.setDouble(7, idealWeight);

            ps.executeUpdate();
            System.out.println("User added successfully");

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println("Error, something has gone wrong. Please contact the administrator.");

        }
    }

    //Updates the username of a user
    public static void updateUsername(String newUsername, String username)
    {
        try{
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Users SET Username = ? WHERE Username = ?");

            ps.setString(1, newUsername);
            ps.setString(2, username);

            ps.executeUpdate();
            System.out.println("Username updated");

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println("Error, something has gone wrong. Please contact the administrator.");
        }
    }

    //Updates the password of a user
    public static void updatePassword(String password, String username)
    {
        try{
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Users SET Password = ? WHERE Username = ?");

            ps.setString(1, password);
            ps.setString(2, username);

            ps.executeUpdate();
            System.out.println("Password updated");

        }catch (Exception exception){
            System.out.println(exception.getMessage());
            System.out.println("Error, something has gone wrong. Please contact the administrator");
        }
    }

    //Deletes a user
    public  static  void deleteUser(String username)
    {
        try{
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Users WHERE Username = ?");

            ps.setString(1, username);

            ps.executeUpdate();
            System.out.println("User deleted");
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            System.out.println("Error, something has gone wrong. Please contact the administrator");
        }
    }

    //Select the ideal weight
    public static void idealWeight(int min, int max)
    {
        try{
            PreparedStatement ps = Main.db.prepareStatement("SELECT FirstName, LastName, Username, IdealWeight FROM Users WHERE IdealWeight > ? AND IdealWeight < ?");
            ps.setInt(1, min);
            ps.setInt(2, max);
            ResultSet results = ps.executeQuery();

            while(results.next()){
                String firstName = results.getString(1);
                String lastName = results.getString(2);
                String Username = results.getString(3);
                int idealWeight = results.getInt(4);

            }

        }catch (Exception exception){
            System.out.println(exception.getMessage());
            System.out.println("Error, something has gone wrong. Please contact the administrator");
        }
    }
}
