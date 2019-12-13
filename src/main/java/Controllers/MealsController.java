package Controllers;

import Server.Main;


import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


@Path("meals/")
public class MealsController {

    // Inserting a new meal into the Meals table
    @POST
        @Path("add")
        @Consumes(MediaType.MULTIPART_FORM_DATA)
        @Produces(MediaType.APPLICATION_JSON)
        public static String insertMeal (
            @FormDataParam("name") String name, @FormDataParam("calories") int calories,
            @FormDataParam("carbs") int carbs, @FormDataParam("fat") int fat, @FormDataParam("protein") int protein){

            try {
                                System.out.println("meal/add name=" + name);

                PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Meals (MealName, Calories, Carbs, Fat, Protein) VALUES (?, ?, ?, ?, ?) ");

                ps.setString(1, name);
                ps.setInt(2, calories);
                ps.setInt(3, carbs);
                ps.setInt(4, fat);
                ps.setInt(5, protein);

                ps.executeUpdate();
                return "{\"status\": \"OK\"}";

            } catch (Exception exception) {
                System.out.println("Database error: " + exception.getMessage());
                return "{\"error\": \"Unable to create new item, please see server console for more info.\"}";
            }
        }


    // Outputting a meal based on calorie range


        @POST
        @Path("list")
        @Produces(MediaType.APPLICATION_JSON)
        public static String listMeals (@FormDataParam("lower") int lower, @FormDataParam("upper") int upper) {
            System.out.println("meals/list");
            JSONArray list = new JSONArray();

            try {
                PreparedStatement ps = Main.db.prepareStatement("SELECT MealName, Calories, Carbs, Fat, Protein FROM Meals WHERE Calories > ? AND Calories < ?");

                ps.setInt(1, lower);
                ps.setInt(2, upper);
                ResultSet results = ps.executeQuery();

                while (results.next()) {
                    JSONObject item = new JSONObject();
                    item.put("MealName", results.getString(1));
                    item.put("Calories", results.getInt(2));
                    item.put("Carbs", results.getInt(3));
                    item.put("Fat", results.getInt(4));
                    item.put("Protein", results.getInt(5));
                    list.add(item);
                }

                return list.toString();

            } catch (Exception exception) {
                System.out.println("Database error: " + exception.getMessage());
                return "{\"error\": \"Unable to list items, please see server console for more info.\"}";


            }
        }



}
