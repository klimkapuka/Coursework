import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserController {

    //Lists all the fields from the Users table
    public static void ListUsers() {

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
    public static void InsertUser(String FirstName, String LastName, String Gender, String Username, String Password, String Email, float IdealWeight)
    {
        try{
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Users (FirstName, LastName, Gender, Username, Password, Email, IdealWeight) VALUES (?,?,?,?,?,?,?)");

            ps.setString(1, FirstName);
            ps.setString(2, LastName);
            ps.setString(3, Gender);
            ps.setString(4, Username);
            ps.setString(5, Password);
            ps.setString(6, Email);
            ps.setFloat(7, IdealWeight);

            ps.executeUpdate();
            System.out.println("User added successfully");

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println("Error, something has gone wrong. Please contact the administrator.");

        }
    }

    //Updates the username of a user
    public static void UpdateUsername(String Email, String Username)
    {
        try{
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Users SET Username = ? WHERE Email = ?");

            ps.setString(1, Username);
            ps.setString(2, Email);

            ps.executeUpdate();
            System.out.println("Username updated");

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println("Error, something has gone wrong. Please contact the administrator.");
        }
    }
}
