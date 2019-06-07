package DataLayer;


import java.sql.*;

public class UserDAO implements IUserDAO{



    //The following 3 functions are dataLayer Accessors.

    /**
     * createConnection() is a helper-function that creates an open connection to the mySQL server.
     * NOTE: "com.mysql.jdbc.Driver" selects the driver to use to connect.
     * @return Connection
     * @throws SQLException
     */
    @Override
    public Connection createConnection() throws SQLException {
        String dbUrl = "jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s180943?";
        String dbUsername = "s180943";
        String dbPassword = "UXZTadQzbPrlIosGCZYNF";
        try{
            //this specifies the driver for tomcat to use to communicate to mySQL server
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        }catch(ClassNotFoundException e){
            e.getMessage();
        }
        return null;
    }

    @Override
    public IUserDTO getDBUser(int id) throws DALException {

        try (Connection con = createConnection()) {
            String query = "SELECT * FROM users WHERE user_id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet set = preparedStatement.executeQuery();

            if(set.next()){
                return makeUser(set);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DALException(e.getMessage());
        }
    }
    @Override
    public IUserDTO makeUser(ResultSet set) throws SQLException {

        IUserDTO user = new UserDTO();
        user.setUserId(set.getInt("user_id"));
        user.setUsername(set.getString("username"));
        return user;
    }
}



