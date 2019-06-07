package DataLayer;


import java.sql.*;

public class UserDAO implements IUserDAO{


    /**@author Claes
     * Creates a connection to the Database.
     * It is inside a try/catch statment to assure we do not leave open connections.
     * @return
     * @throws SQLException
     */
    @Override
    public Connection createConnection() throws SQLException {
        String dbUrl = "jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s180943?";
        String dbUsername = "s180943";
        String dbPassword = "UXZTadQzbPrlIosGCZYNF";
        try{
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



