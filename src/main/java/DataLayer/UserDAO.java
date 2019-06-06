package DataLayer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.*;

public class UserDAO implements IUserDAO{

    final String dbUrl = "jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s180943?";
    final String dbUsername = "s180943";
    final String dbPassword = "UXZTadQzbPrlIosGCZYNF";

    //The following 3 functions are dataLayer Accessors.
    public Connection createConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
    }


    public IUserDTO getDBUser(int id) throws DALException {

        try (Connection con = createConnection()) {
/*
            String query = "SELECT * FROM users WHERE user_id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet set = preparedStatement.executeQuery();
 */
            Statement statement = con.createStatement();
            ResultSet set = statement.executeQuery("SELECT * FROM users WHERE user_id = " + id);
            if(set.next()){
                return makeUser(set);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DALException(e.getMessage());
        }
    }

    public IUserDTO makeUser(ResultSet set) throws SQLException {

        IUserDTO user = new UserDTO();
        user.setUserId(set.getInt(1));
        user.setUsername(set.getString(2));

        return user;
    }
}



