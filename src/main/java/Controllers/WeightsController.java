package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("weight/")
public class WeightsController {

    // Inserts a new log
        @POST
        @Path("log-new")
        @Consumes(MediaType.MULTIPART_FORM_DATA)
        @Produces(MediaType.APPLICATION_JSON)
        public String insertLog (
                @FormDataParam("dateRecorded") String date, @FormDataParam("weightRecorded") Double weight, @CookieParam("token") String token){

            try {
                if (date == null || weight == null) {
                    throw new Exception("One or more form data parameters are missing in the HTTP request.");
                }

                PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Weights (UserID, DateRecorded, CurrentWeight) " +
                        "VALUES ((SELECT UserID FROM Users WHERE Token = token), ?, ?) ");

                ps.setString(1, date);
                ps.setDouble(2, weight);

                ps.executeUpdate();
                return "{\"status\": \"OK\"}";

            } catch (Exception exception) {
                System.out.println("Database error: " + exception.getMessage());
                return "{\"error\": \"Unable to create new item, please see server console for more info.\"}";
            }
        }

        // Outputs the logs of a specific user
        @POST
        @Path("track")
        @Produces(MediaType.APPLICATION_JSON)
        public String listWeights (@CookieParam("token") String token) {
            System.out.println("weights/track");
            JSONArray list = new JSONArray();
            try {
                PreparedStatement ps = Main.db.prepareStatement("SELECT DateRecorded, CurrentWeight FROM " +
                        "Weights WHERE UserID = (SELECT UserID FROM Users WHERE Token = ?) ORDER BY DateRecorded ASC ");
                ps.setString(1, token);
                ResultSet results = ps.executeQuery();

                while (results.next()) {
                    JSONObject item = new JSONObject();
                    item.put("DateRecorded", results.getString(1));
                    item.put("CurrentWeight", results.getDouble(2));
                    list.add(item);
                }
                System.out.println(list);
                return list.toString();


            } catch (Exception exception) {
                System.out.println("Database error: " + exception.getMessage());
                return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
            }
        }


        // Changes date and weight of a log
        @POST
        @Path("update")
        @Consumes(MediaType.MULTIPART_FORM_DATA)
        @Produces(MediaType.APPLICATION_JSON)
        public static String updateWeigths(
                @FormDataParam("date") String date, @FormDataParam("newDate") String newDate, @FormDataParam("newWeight") Double newWeight) {


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
