import java.sql.PreparedStatement;
import java.util.Date;

public class WeightsController {

    // Inserts a new log
    public static void addLog(String username, Date date, double weight)
    {

        try{
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Weights (DateRecorded, CurrentWeight) VALUES (?, ?) ");

            ps.setDate(1, (java.sql.Date) date);
            ps.setDouble(2, weight);

            ps.executeUpdate();
            System.out.println("Log successfully inserted");
        } catch (Exception exception){
            System.out.println(exception.getMessage());
            System.out.println("Error, something has gone wrong. Please contact the administrator");
        }
    }



}
