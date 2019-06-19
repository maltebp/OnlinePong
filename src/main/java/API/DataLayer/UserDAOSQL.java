package API.DataLayer;


import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Communication to SQL database
 *
 * @author Claes and Simon
 */
public class UserDAOSQL implements IUserDAO{


    /**@author Claes and Simon
     * Creates a connection to the Database.
     * It is inside a try/catch statment to assure we do not leave open connections.
     * NOTE: "com.mysql.jdbc.Driver" selects the driver for TomCat to use to connect to mySQL server.
     * @return
     * @throws SQLException
     */
    private Connection createConnection() throws DALException {
        String dbUrl = "jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s180943?";
        String dbUsername = "s180943";
        String dbPassword = "UXZTadQzbPrlIosGCZYNF";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        }catch(ClassNotFoundException e){
        e.printStackTrace();
        }
        catch (SQLException e){
            throw new DALException("500", "Couldn't connect to DB server.");
        }
        return null;
    }

    /**@author Claes, Simon
     * This function gets User Data From the Database
     @param username The User, that one wants DB Data from
     */
    @Override
    public IUserDTO getUser(String username) throws DALException {
        try (Connection con = createConnection()) {
            String query = "SELECT * FROM users WHERE username = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet set = preparedStatement.executeQuery();

            if(set.next()){
                return createUserDTO(set);
            }
            else{
                throw new DALException("410", "User doesn't exist.");
            }
        } catch (SQLException e) {
            throw new DALException("500", "Unknown server error.");
        }
    }

    /**@author Claes, Simon
     * This Function Helps Translate the Data from the database
     * To a local object, which makes the Data easier to Access in this local code
     @param set - The set of SQL data you want made into a local object.
      * */
    private IUserDTO createUserDTO(ResultSet set) throws SQLException {
            IUserDTO user = new UserDTO();
            user.setUsername(set.getString("username"));
            user.setElo(set.getInt("elo"));
            return user;
    }


    /**
     * @Author Simon, Claes
     * Create a user in the DB, (with a hashed password ofc.).
     * @param username
     * @param password
     * @return String: Whether successful or not.
     * @throws DALException
     */
    public String createUser(String username, String password, int elo) throws DALException {
        Argon2 argon2 = Argon2Factory.create();
        String hashedPassword = argon2.hash(10, 65536, 1, password);

        try (Connection con = createConnection()) {

            String query = "SELECT * FROM users WHERE username = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet set = preparedStatement.executeQuery();
            if(set.next()){
                throw new DALException("409", "A user with that username already exists.");
            }
            else {
                String query2 = "INSERT INTO users (username, password, elo) VALUES(?,?,?)";
                PreparedStatement preparedStatement2 = con.prepareStatement(query2);
                preparedStatement2.setString(1, username);
                preparedStatement2.setString(2, hashedPassword);
                preparedStatement2.setInt(3, elo);
                preparedStatement2.execute();
                return "200";
            }
        }catch(SQLException e){
            throw new DALException("500", "Unknown server error");
        }
    }

    /**
     * @Author Simon
     * Compare a password, to that user's hashed password in the DB.
     * This is for user-validation.
     * @param password
     * @return boolean: whether successful or not.
     * @throws DALException
     */
    public String checkHash(String username, String password) throws DALException{
        Argon2 argon2 = Argon2Factory.create();

        try (Connection con = createConnection()) {
            String query = "SELECT password FROM users WHERE username = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet set = preparedStatement.executeQuery();

            if(set.next()){
                String dbPass = set.getString("password");
                if(argon2.verify(dbPass, password)){
                    return "200";
                }
                else{
                    throw new DALException("401", "Incorrect passwrord");
                }
            }
            else{
                throw new DALException("410", "User doesn't exist.");
            }
        }catch(SQLException e){
            throw new DALException("500", "Unknown server error.");
        }
    }

    /**
     * @Author Simon
     * set the Elo of a player in the database.
     * @param username
     * @param elo
     * @return String: error message.
     * @throws DALException
     */
    public String setElo(String username, int elo) throws DALException{
        try(Connection con = createConnection()){
           String query = "SELECT * FROM users WHERE username = ?";
           PreparedStatement preparedStatement = con.prepareStatement(query);
           preparedStatement.setString(1, username);
           ResultSet set = preparedStatement.executeQuery();

           if(!set.next()){
               throw new DALException("410", "User doesn't exist.");
           }
           else {
               String query2 = "UPDATE users SET elo = ? WHERE username = ?";
               PreparedStatement preparedStatement2 = con.prepareStatement(query2);
               preparedStatement2.setInt(1, elo);
               preparedStatement2.setString(2, username);
               preparedStatement2.execute();
               return "200";
           }
        }catch(SQLException e){
            throw new DALException("500", "Unknown server error.");
        }
    }
    public List<IUserDTO> getTopTen() throws DALException{
        try(Connection con = createConnection()){
            String query = "SELECT username, elo FROM users ORDER BY elo DESC";
            ResultSet set = con.createStatement().executeQuery(query);
            ArrayList<IUserDTO> users= new ArrayList<>();

            int i = 0;
            while(set.next() && i < 10){
                IUserDTO tempUser = new UserDTO(set.getString("username"), set.getInt("elo"));
                users.add(tempUser);
                i++;
            }
            return users;

        }catch(SQLException e){
            e.printStackTrace();
            throw new DALException("500", "Unknown server error.");

        }
    }

    public String forceDeleteUser(String username) throws DALException{
        try(Connection con = createConnection()){
            String query = "DELETE FROM users WHERE username = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1,username);
            preparedStatement.execute();
            return "200";
            }catch(SQLException e) {
                throw new DALException("500", "Unknown server error.");
        }
    }

    public String userDeleteUser(String username, String password) throws DALException{
        try(Connection con = createConnection()){
            String auth = checkHash(username, password);
            if(auth.equals("200")){
                return forceDeleteUser(username);
            }
            else throw new DALException("401", "Incorrect Password");
        }catch(SQLException e){
            throw new DALException("500", "Unknown server error.");
        }
    }
}



