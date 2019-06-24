import org.sqlite.SQLiteConfig;

import javax.jws.soap.SOAPBinding;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {
    public static Connection db = null;
// main part of the program
    public static void main(String[] args) {
        openDatabase("Database.db");
// code to get data from, write to the database etc goes here!
        closeDatabase();
    }
// opens the database
    private static void openDatabase(String dbFile) {
        try  {
            // loads the database driver
            Class.forName("org.sqlite.JDBC");
            // does database settings to maintain database integrity
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            // opens the database file
            db = DriverManager.getConnection("jdbc:sqlite:resources/" + dbFile, config.toProperties());
            System.out.println("Database connection successfully established.");
            // catches any errors
        } catch (Exception exception) {
            System.out.println("Database connection error: " + exception.getMessage());
        }

    }
//closes the database
    private static void closeDatabase(){
        try {
            db.close();
            System.out.println("Disconnected from database.");
            // catches any errors
        } catch (Exception exception) {
            System.out.println("Database disconnection error: " + exception.getMessage());
        }
    }

    public static void Select() {

        openDatabase("Database.db");

        try {
            PreparedStatement ps = db.prepareStatement("SELECT * FROM Users");
            ResultSet results = ps.executeQuery();

            //while loop
            while (results.next()){
                int UserID = results.getInt(1);
                String FirstName = results.getString(2);
                String LastName = results.getString(3);
                String Gender = results.getString(4);
                String Username = results.getString(5);
                String Password = results.getString(6);
                String Email = results. getString(7);
                System.out.println(UserID + " " + FirstName + " "  + LastName + " " + Username);
            }

        } catch (Exception exception) {
            System.out.println("Database error " + exception.getMessage());
        }
        closeDatabase();
    }


}
