import com.company.data.interfaces.IDB;
import com.company.controllers.PersonController;
import com.company.data.PostgresDB;
import com.company.repositories.PersonRepository;
import com.company.repositories.interfaces.IPersonRepository;
import java.sql.*;

public class Main {

    public static void main(String[] args) {
        String connectionUrl = "jdbc:postgresql://localhost:5432/Kino";
        Connection con = null;
        ResultSet rs = null;
        Statement stmt = null;
        try {
            // Here we load the driver’s class file into memory at the runtime
            Class.forName("org.postgresql.Driver");
            // Establish the connection
            con = DriverManager.getConnection(connectionUrl, "postgres", "457810nursem");

            // The object of statement is responsible to execute queries with the database
            stmt = con.createStatement();

            // The executeQuery() method of Statement interface is used to execute queries
            // to the database. This method returns the object of ResultSet that can be
            // used to get all the records of a table that matches the sql statement
            rs = stmt.executeQuery("select mistake * from Persons");
            while (rs.next()) {
                System.out.println("Film ID: " + rs.getInt("film_id")
                        + ", Title: " + rs.getString("title")
                        + ", Release Year: " + rs.getInt("release_year"));
            }
            // Display information about tickets
            rs = stmt.executeQuery("SELECT * FROM Tickets");
            System.out.println("\nTicket Information:");
            while (rs.next()) {
                System.out.println("Ticket ID: " + rs.getInt("ticket_id")
                        + ", Film ID: " + rs.getInt("film_id")
                        + ", Price: " + rs.getDouble("price"));
            }
        catch (Exception e) {
            System.out.println("Exception occurred!");
        } finally {
            try { // Close connection - clean up the system resources
                con.close();
            } catch (Exception e) {
                System.out.println("Exception occurred!");
            }
        }
        System.out.println("Finished!");


        // Here you specify which DB and PersonRepository to u// And changing DB should not affect to whole code
        IDB db = new PostgresDB();
        IPersonRepository repo = new PersonRepository(db);
        PersonController controller = new PersonController(repo);
        MyApplication app = new MyApplication(controller);
        app.start();
    }
}
