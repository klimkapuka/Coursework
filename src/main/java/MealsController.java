import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MealsController {

    // Inserting a new meal into the Meals table
    public static void addMeal(String name, int calories, int carbs, int fat, int protein) {

        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Meals (MealName, Calories, Carbs, Fat, Protein) VALUES (?, ?, ?, ?, ?) ");

            ps.setString(1, name);
            ps.setInt(2, calories);
            ps.setInt(3, carbs);
            ps.setInt(4, fat);
            ps.setInt(5, protein);

            ps.executeUpdate();

            System.out.println("New meal successfully inserted");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println("Error, something has gone wrong. Please contact the administrator");
        }
    }

    // Outputting a meal based on calorie range
    public static void getMeal(int lower, int upper) {

        try{
            PreparedStatement ps = Main.db.prepareStatement("SELECT MealName, Calories, Carbs, Fat, Protein FROM Meals WHERE Calories > ? AND Calories < ?");

            ps.setInt(1, lower);
            ps.setInt(2, upper);
            ResultSet results = ps.executeQuery();

            while (results.next()) {
                System.out.println(results.getString(1) + ", " + " Calories: " + results.getInt(2) + " Carbs: "
                        + results.getInt(3) + "g Fat: " + results.getInt(4) + "g Protein: " + results.getInt(5) + "g");
            }

        }catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println("Error, something has gone wrong. Please contact the administrator");
        }
    }
}
