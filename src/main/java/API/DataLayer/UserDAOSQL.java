package API.DataLayer;


import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Constants;
import de.mkammerer.argon2.Argon2Factory;

import java.sql.*;

public class UserDAOSQL implements IUserDAO{

    /**@author Claes and Simon
     * Creates a connection to the Database.
     * It is inside a try/catch statment to assure we do not leave open connections.
     * NOTE: "com.mysql.jdbc.Driver" selects the driver for TomCat to use to connect to mySQL server.
     * @return
     * @throws SQLException
     */
    private Connection createConnection() throws SQLException {
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
    public IUserDTO getUser(int id) throws DALException {

        try (Connection con = createConnection()) {
            String query = "SELECT * FROM users WHERE user_id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet set = preparedStatement.executeQuery();

            if(set.next()){
                return createUserDTO(set);
            }
            return null;
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    /**@author Claes
     * This Function Helps Translate the Data from the database
     * To a local object, which make the Data easier to Access in this local code
     @param set - The set of SQL data you want made into a local object.
      * */
    private IUserDTO createUserDTO(ResultSet set) throws SQLException {
        try {
            IUserDTO user = new UserDTO();
            user.setUserId(set.getInt("user_id"));
            user.setUsername(set.getString("username"));
            user.setPassword(set.getString("password"));
            return user;
        }
    }

    public IUserDTO getScore(IUserDTO user) throws DALException{
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
            throw new DALException("Error in retrieving updated score: " + e.getMessage());
        }
    }

    public String newScore(int id, int score) throws DALException {
        try (Connection con = createConnection()) {

            String query = "INSERT INTO score VALUES(?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, score);
            preparedStatement.execute();
            return "It was added, much wow";
        } catch (SQLException e) {
            throw new DALException("There was an error: " + e.getMessage());
        }
    }

    public String createUser(String username, String password) throws DALException {
        Argon2 argon2 = Argon2Factory.create();
        String hashedPassword = argon2.hash(10, 65536, 1, password);

        try (Connection con = createConnection()) {

            //user_id is on AUTO_INCREMENT.
            String query = "INSERT INTO users (username, password) VALUES(?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, hashedPassword);
            preparedStatement.execute();
            return"User has been added.";
        }catch(SQLException e){
            throw new DALException("There was an error: " + e.getMessage());
        }
    }

    public boolean checkHash(int id, String password) throws DALException{
        Argon2 argon2 = Argon2Factory.create();

        try (Connection con = createConnection()) {
            String query = "SELECT password FROM users WHERE user_id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet set = preparedStatement.executeQuery();

            if(set.next()){
                String dbPass = set.getString("password");
                if(argon2.verify(dbPass, password));
                    return true;
                }
        }catch(SQLException e){
            throw new DALException(e.getMessage());
        }
        return false;
    }
}



