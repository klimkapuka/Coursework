package Controllers;

import Server.Main;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;


@Path("user/")
public class UserController {

    //Lists all the fields from the Users table

        @GET
        @Path("list")
        @Consumes(MediaType.MULTIPART_FORM_DATA)
        @Produces(MediaType.APPLICATION_JSON)
        public static String listUsers(
                @CookieParam("token") String token
        ) {
            System.out.println("users/list");
            JSONArray list = new JSONArray();

            try {
                PreparedStatement ps = Main.db.prepareStatement("SELECT FirstName, LastName, Gender, Username, Password, Email, IdealWeight FROM Users WHERE Token = ?");
                ps.setString(1, token);
                ResultSet results = ps.executeQuery();

                while (results.next()){
                    JSONObject item = new JSONObject();
                    item.put("FirstName", results.getString(1));
                    item.put("LastName", results.getString(2));
                    item.put("Gender", results.getString(3));
                    item.put("Username", results.getString(4));
                    item.put("Password", results.getString(5));
                    item.put("Email", results.getString(6));
                    item.put("IdealWeight", results.getDouble(7));
                    list.add(item);
                }
                return  list.toString();

            } catch (Exception exception) {
                System.out.println("Database error: " + exception.getMessage());
                return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
            }

        }

        //Inserts a new user into the Users table
        @POST
        @Path("create")
        @Consumes(MediaType.MULTIPART_FORM_DATA)
        @Produces(MediaType.APPLICATION_JSON)
        public static String insertUser(
                @FormDataParam("firstName")  String firstName, @FormDataParam("lastName") String lastName,
                @FormDataParam("gender") String gender,
                @FormDataParam("username") String username, @FormDataParam("password") String password,
                @FormDataParam("email") String email, @FormDataParam("idealWeight") Double idealWeight){

            try{
                System.out.println("Running");
                if (firstName == null || lastName == null || gender == null || username == null || password == null
                        || email == null || idealWeight == null){
                    throw new Exception("One or more form data parameters are missing in the HTTP request.");
                }
                System.out.println("user/new username=" + username);

                PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Users (FirstName, LastName, Gender," +
                        " Username,Password, Email, IdealWeight) VALUES (?,?,?,?,?,?,?)");

                ps.setString(1, firstName);
                ps.setString(2, lastName);
                ps.setString(3, gender);
                ps.setString(4, username);
                ps.setString(5, password);
                ps.setString(6, email);
                ps.setDouble(7, idealWeight);

                ps.executeUpdate();
                return "{\"status\": \"OK\"}";

            } catch (Exception exception) {
                System.out.println("Database error: " + exception.getMessage());
                return "{\"error\": \"One or more of the required fields are empty! \"}";
            }
        }

        //Updates the username of a user
        @POST
        @Path("change-username")
        @Consumes(MediaType.MULTIPART_FORM_DATA)
        @Produces(MediaType.APPLICATION_JSON)
        public static String updateUsername (
                @FormDataParam("Username") String Username, @FormDataParam("newUsername") String newUsername, @CookieParam("token") String token) {

            try {
                if (Username == null || newUsername == null) {
                    throw new Exception("One or more form data parameters are missing in the HTTP request.");
                }
                System.out.println("user/update-username new username=" + newUsername);

                PreparedStatement ps = Main.db.prepareStatement("UPDATE Users SET Username = ? WHERE Username = ?");

                ps.setString(1, newUsername);
                ps.setString(2, Username);

                ps.executeUpdate();
            return "{\"status\": \"OK\"}";

            } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to update item, please see server console for more info.\"}";
            }
        }


    //Updates the password of a user

        @POST
        @Path("change-password")
        @Consumes(MediaType.MULTIPART_FORM_DATA)
        @Produces(MediaType.APPLICATION_JSON)
        public String updatePassword (
                @FormDataParam("password") String password, @FormDataParam("username") String username, @CookieParam("token") String token){

            try {
                if (password == null || username == null) {
                    throw new Exception("One or more form data parameters are missing in the HTTP request.");
                }
                System.out.println("users/update-password username=" + username);

                PreparedStatement ps = Main.db.prepareStatement("UPDATE Users SET Password = ? WHERE Username = ?");

                ps.setString(1, password);
                ps.setString(2, username);

                ps.executeUpdate();
                return "{\"status\": \"OK\"}";

            } catch (Exception exception) {
                System.out.println("Database error: " + exception.getMessage());
                return "{\"error\": \"Unable to update item, please see server console for more info.\"}";
            }
        }


        //Deletes a user
        @POST
        @Path("delete")
        @Consumes(MediaType.MULTIPART_FORM_DATA)
        @Produces(MediaType.APPLICATION_JSON)
        public static String deleteUser (@FormDataParam("username") String username, @CookieParam("token") String token)
        {
            try {
                if (username == null) {
                    throw new Exception("One or more form data parameters are missing in the HTTP request.");
                }
                System.out.println("users/delete username=" + username);
                PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Users WHERE Username = ?");

                ps.setString(1, username);

                ps.executeUpdate();
                return "{\"status\": \"OK\"}";

            } catch (Exception exception) {
                System.out.println("Database error: " + exception.getMessage());
                return "{\"error\": \"Unable to delete item, please see server console for more info.\"}";
            }
        }


    //Select the ideal weight based on range

        @GET
        @Path("selectIdealWeight")
        @Produces(MediaType.APPLICATION_JSON)
        public static String selectIdealWeight(@FormDataParam("min") int min, @FormDataParam("max") int max ) {
        System.out.println("users/selectidealWeight");
        JSONArray list = new JSONArray();
            try{

                PreparedStatement ps = Main.db.prepareStatement("SELECT FirstName, LastName, Username, IdealWeight FROM Users WHERE IdealWeight > ? AND IdealWeight < ?");
                ps.setInt(1, min);
                ps.setInt(2, max);
                ResultSet results = ps.executeQuery();

                while(results.next()){
                    JSONObject item = new JSONObject();
                    item.put("FirstName", results.getString(1));
                    item.put("LastName", results.getString(2));
                    item.put("Username", results.getString(3));
                    item.put("IdealWeight", results.getString(4));
                    list.add(item);

                }
                return list.toString();

            }catch (Exception exception){
                System.out.println("Database error: " + exception.getMessage());
                return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
            }
        }

    @POST
    @Path("login")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String loginUser(@FormDataParam("username") String username, @FormDataParam("password") String password) {

        try {

            System.out.println("user/login");

            PreparedStatement ps1 = Main.db.prepareStatement("SELECT Password FROM Users WHERE Username = ?");
            ps1.setString(1, username);
            ResultSet loginResults = ps1.executeQuery();
            if (loginResults.next()) {

                String correctPassword = loginResults.getString(1);
                if (password.equals(correctPassword)) {

                    String token = UUID.randomUUID().toString();

                    PreparedStatement ps2 = Main.db.prepareStatement("UPDATE Users SET Token = ? WHERE Username = ?");
                    ps2.setString(1, token);
                    ps2.setString(2, username);
                    ps2.executeUpdate();

                    JSONObject userDetails = new JSONObject();
                    userDetails.put("username", username);
                    userDetails.put("token", token);
                    return userDetails.toString();

                } else {
                    return "{\"error\": \"Incorrect password!\"}";
                }

            } else {
                return "{\"error\": \"Unknown user!\"}";
            }

        } catch (Exception exception){
            System.out.println("Database error during /user/login: " + exception.getMessage());
            return "{\"error\": \"Server side error!\"}";
        }
    }

    @POST
    @Path("logout")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String logoutUser(
            @CookieParam("token") String token) {

        try {

            System.out.println("user/logout");

            PreparedStatement ps1 = Main.db.prepareStatement("SELECT UserID FROM Users WHERE Token = ?");
            ps1.setString(1, token);
            ResultSet logoutResults = ps1.executeQuery();
            if (logoutResults.next()) {

                int id = logoutResults.getInt(1);

                PreparedStatement ps2 = Main.db.prepareStatement("UPDATE Users SET Token = NULL WHERE UserID = ?");
                ps2.setInt(1, id);
                ps2.executeUpdate();

                return "{\"status\": \"OK\"}";
            } else {

                return "{\"error\": \"Invalid token!\"}";

            }

        } catch (Exception exception){
            System.out.println("Database error during /user/logout: " + exception.getMessage());
            return "{\"error\": \"Server side error!\"}";
        }

    }

    public static boolean validToken(String token) {
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT UserID FROM Users WHERE Token = ?");
            ps.setString(1, token);
            ResultSet logoutResults = ps.executeQuery();
            return logoutResults.next();
        } catch (Exception exception) {
            System.out.println("Database error during /user/logout: " + exception.getMessage());
            return false;
        }
    }
}






