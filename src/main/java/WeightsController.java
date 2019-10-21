import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class WeightsController {

    // Inserts a new log
    public static void addLog(int userID, String date, double weight) {

        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Weights (UserID, DateRecorded, CurrentWeight) VALUES (?, ?, ?) ");

            ps.setInt(1, userID);
            ps.setString(2, date);
            ps.setDouble(3, weight);

            ps.executeUpdate();
            System.out.println("Log successfully inserted");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println("Error, something has gone wrong. Please contact the administrator");
        }
    }

    // Outputs the logs of a specific user
    public static void getLog(String username) {
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT DateRecorded, CurrentWeight FROM Weights WHERE UserID = (SELECT UserID FROM Users WHERE Username = ?)");
            ps.setString(1, username);
            ResultSet results = ps.executeQuery();

            while (results.next()) {
                String Date = results.getString(1);
                Double Weight = results.getDouble(2);
                System.out.println(Date + " - " + Weight);
            }

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println("Error, something has gone wrong. Please contact the administrator");
        }

    }

    // Changes date and weight of a log
    public static void updateLog(String date, String newDate, double newWeight) {

        try{
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Weights SET DateRecorded = ?, CurrentWeight = ? WHERE DateRecorded =?");

            ps.setString(1, newDate);
            ps.setDouble(2, newWeight);
            ps.setString(3, date);
        } catch (Exception exception){
            System.out.println(exception.getMessage());
            System.out.println("Error, something has gone wrong. Please contact the administrator");
        }
    }
}
