package API.DataLayer;

import java.sql.Connection;
import java.sql.ResultSet;
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
    String createUser(String username, String password) throws DALException;

    /**
     * Controls the password is correct through hashing
     *
     * @param username        username of User
     * @param password  Password of User
     * @return          True if password is correct, else false
     * @throws DALException
     */
    String checkHash(String username, String password) throws DALException;

    String setElo(String username, int elo) throws DALException;

    List<IUserDTO> getAll() throws DALException;

    /**
     * Customizable exception for explaining Database Access Layer exceptions
     */
    class DALException extends Exception {

        //Til Java serialisering...
        private static final long serialVersionUID = 7355418246336739229L;

        public DALException(String msg, Throwable e) {super(msg,e);}
        public DALException(String msg) { super(msg);}
    }
}
