package API.DataLayer;

import java.sql.SQLException;
import java.util.List;

public interface IUserDAO {
    /**@author Claes, Simon
     * The purpose of this class is being able to access the UserData.
     * As is also stated in the name Data Access Object (DAO)
     * @return Userdata
     * @throws SQLException
     */

    /**
     * Retrieves a user by ID
     *
     * @param username    The username of the user
     * @return      User object
     * @throws DALException
     */
    IUserDTO getUser(String username) throws DALException;

    /**
     * Inserts a user into the database
     *
     * @param username      Users username
     * @param password      Users password
     * @return              String confirmation
     * @throws DALException
     */
    String createUser(String username, String password, int elo) throws DALException;

    /**
     * Controls the password is correct through hashing
     *
     * @param username        username of User
     * @param password  Password of User
     * @return          True if password is correct, else false
     * @throws DALException
     */
    String checkHash(String username, String password) throws DALException;

    /**
     * updates the elo of a player in the database
     * @param username
     * @param elo
     * @return String: error message
     * @throws DALException
     */
    String setElo(String username, int elo) throws DALException;

    /**
     * @author Simon
     * Get the 10 users with highest elo rating.
     * @return IUserDTO List.
     * @throws DALException
     */
    List<IUserDTO> getTopTen() throws DALException;

    /**
     * @author Simon
     * Deleting users in the DB.
     * This function is exclusively for testing purposes.
     * @param username
     * @throws DALException
     */
    String forceDeleteUser(String username) throws DALException;

    String userDeleteUser(String username, String password) throws DALException;
    /**
     * Customizable exception for explaining Database Access Layer exceptions
     */
    class DALException extends Exception {

        public String errorCode;

        public DALException(String msg, Throwable e) {super(msg,e);}
        public DALException(String code, String msg) { super(msg); this.errorCode = code;}

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }
    }
}
