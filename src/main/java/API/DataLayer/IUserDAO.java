package API.DataLayer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface IUserDAO {
    /**@author Claes
     * The purpose of this class is being able to access the UserData.
     * As is also stated in the name Data Access Object (DAO)
     * @return Userdata
     * @throws SQLException
     */

    Connection createConnection() throws DALException;

    IUserDTO getDBUser(int id) throws DALException;

    IUserDTO makeUser(ResultSet set) throws DALException;

    IUserDTO getDBScore(IUserDTO user) throws DALException;

    String newScore(int id, int score)throws DALException;

    String createUser(String username, String password) throws DALException;

    boolean checkHash(int id, String password) throws DALException;

    class DALException extends Exception {

        //Til Java serialisering...
        private static final long serialVersionUID = 7355418246336739229L;

        public DALException(String msg, Throwable e) {super(msg,e);}
        public DALException(String msg) { super(msg);}
    }
}
