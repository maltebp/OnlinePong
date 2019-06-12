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

    Connection createConnection() throws SQLException;

    IUserDTO getDBUser(int id) throws DALException;

    IUserDTO makeUser(ResultSet set) throws SQLException;

    IUserDTO getDBScore(IUserDTO user) throws SQLException;

    String newScore(int id, int score)throws SQLException;

    String createUser(String username, String password) throws SQLException;

    boolean checkHash(int id, String password) throws SQLException;

    class DALException extends Exception {

        //Til Java serialisering...
        private static final long serialVersionUID = 7355418246336739229L;

        public DALException(String msg, Throwable e) {super(msg,e);}
        public DALException(String msg) { super(msg);}
    }
}
