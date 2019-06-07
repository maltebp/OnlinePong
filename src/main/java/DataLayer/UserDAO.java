package DataLayer;


import Controller.IUserController;

import javax.validation.constraints.Null;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class UserDAO implements IUserDAO{


    /**@author Claes
     * Creates a connection to the Database.
     * It is inside a try/catch statment to assure we do not leave open connections.
     * NOTE: "com.mysql.jdbc.Driver" selects the driver for TomCat to use to connect to mySQL server.
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

    /**@author Claes
     * This function gets User Data From the Database
     @param id The User, that one wants DB Data from
     */
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

    /**@author Claes
     * This Function Helps Translate the Data from the database
     * To a local object, which make the Data easier to Access in this local code
     @param set - The set of SQL data you want made into a local object.
      * */
    @Override
    public IUserDTO makeUser(ResultSet set) throws SQLException {
        IUserDTO user = new UserDTO();
        user.setUserId(set.getInt("user_id"));
        user.setUsername(set.getString("username"));
        user.setPassword(set.getString("password"));
        return user;
    }

    public IUserDTO getDBScore(IUserDTO user) throws SQLException{

        try (Connection con = createConnection()) {

            String query = "SELECT * FROM score WHERE user_id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, user.getUserId());
            ResultSet set = preparedStatement.executeQuery();

            while(set.next()){
                user.addScore(set.getInt(2));
            }
            return user;

        }catch(SQLException e){
            e.getMessage();
            return null;
            }
    }

    public void newScore(int id, int score) throws SQLException {

        try (Connection con = createConnection()) {

            String query = "INSERT INTO score VALUES(?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, score);
            preparedStatement.execute();

        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public void createUser(String username, String password) throws SQLException {
        try (Connection con = createConnection()) {

            //user_id is on AUTO_INREMENT.
            String query = "INSERT INTO users (username, password) VALUES(?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.execute();
        }catch(SQLException e){
            e.getMessage();
        }
    }
}



