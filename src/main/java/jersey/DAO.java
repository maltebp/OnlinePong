package jersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Path("/insertdata")
public class DAO {

    final String dbUrl = "jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s180943?";
    final String dbUsername = "s180943";
    final String dbPassword = "UXZTadQzbPrlIosGCZYNF";


    public Connection createConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
    }

    @GET
    public void createUser(UserDTO user) throws SQLException {

        try (Connection con = createConnection()) {

            String query = "INSERT INTO users VALUES(?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, "maltebp");
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void addScore(int id, int score) throws SQLException {

        try (Connection con = createConnection()) {

            String query = "INSERT INTO score VALUES(?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, 1);
            preparedStatement.setInt(2, 500);
            preparedStatement.execute();


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Du leder muligvis efter en bruger der ikke eksistere");
        }

    }

}


