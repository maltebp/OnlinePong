package DataLayer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface IUserDAO {

    Connection createConnection() throws SQLException;

    IUserDTO getDBUser(int id) throws DALException;

    IUserDTO makeUser(ResultSet set) throws SQLException;

    public class DALException extends Exception {
        //Til Java serialisering...
        private static final long serialVersionUID = 7355418246336739229L;

        public DALException(String msg, Throwable e) {super(msg,e);}
        public DALException(String msg) { super(msg);}
    }
}
