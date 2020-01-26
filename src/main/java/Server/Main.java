package Server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.sqlite.SQLiteConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {
    public static Connection db = null;
// main part of the program
    public static void main(String[] args) {

        openDatabase("Database.db");

        //preparing the Jersey Servlet
        ResourceConfig config = new ResourceConfig();
        config.packages("Controllers");
        config.register(MultiPartFeature.class);

        //Instantiating the Servlet
        ServletHolder servlet = new ServletHolder(new ServletContainer(config));

        Server server = new Server(8081);

        //Connecting server to the Servlet
        ServletContextHandler context = new ServletContextHandler(server, "/");
        context.addServlet(servlet, "/*");

        //staring server, catching errors
        try {
            server.start();
            System.out.println("Server successfully started.");
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

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

}

