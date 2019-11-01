import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class WeightsController {

    // Inserts a new log
    public static void addLog(int userID, String date, double weight) {
        @POST
        @Path("new")
        @Consumes(MediaType.MULTIPART_FORM_DATA)
        @Produces(MediaType.APPLICATION_JSON)
        public String insertLog (
                @FormDataParam("userID") Integer userID, @FormDataParam("date") String
        date, @FormDataParam("weight") Double weight){

            try {
                if (userID == null || date == null || weight == null) {
                    throw new Exception("One or more form data parameters are missing in the HTTP request.");
                }
                System.out.println("weights/new userID=" + userID);

                PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Weights (UserID, DateRecorded, CurrentWeight) VALUES (?, ?, ?) ");

                ps.setInt(1, userID);
                ps.setString(2, date);
                ps.setDouble(3, weight);

                ps.executeUpdate();
                return "{\"status\": \"OK\"}";

            } catch (Exception exception) {
                System.out.println("Database error: " + exception.getMessage());
                return "{\"error\": \"Unable to create new item, please see server console for more info.\"}";
            }
        }
    }
        // Outputs the logs of a specific user

    public static void getLog(String username) {
        @GET
        @Path("list")
        @Produces(MediaType.APPLICATION_JSON)
        public String listWeights () {
            System.out.println("Weights/list");
            JSONArray list = new JSONArray();
            try {
                PreparedStatement ps = Main.db.prepareStatement("SELECT DateRecorded, CurrentWeight FROM Weights WHERE UserID = (SELECT UserID FROM Users WHERE Username = ?)");
                ps.setString(1, username);
                ResultSet results = ps.executeQuery();

                while (results.next()) {
                    JSONObject item = new JSONObject();
                    item.put("DateRecorder", results.getString(1));
                    item.put("CurrentWeight", results.getDouble(2));
                    list.add(item);
                }
                return list.toString();

            } catch (Exception exception) {
                System.out.println("Database error: " + exception.getMessage());
                return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
            }

        }
    }

        // Changes date and weight of a log

    public static void updateLog(String date, String newDate, double newWeight) {
        @POST
        @Path("update")
        @Consumes(MediaType.MULTIPART_FORM_DATA)
        @Produces(MediaType.APPLICATION_JSON)
        public String updateWeigths(
                @FormDataParam("date") String date, @FormDataParam("newDate") String newDate, @FormDataParam("newWeight") Double newWeight) {
    }

        try {
            if (date == null || newDate == null || newWeight == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("weights/update date=" + date);
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Weights SET DateRecorded = ?, CurrentWeight = ? WHERE DateRecorded =?");

            ps.setString(1, newDate);
            ps.setDouble(2, newWeight);
            ps.setString(3, date);

            return "{\"status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to update item, please see server console for more info.\"}";
        }
    }


}
