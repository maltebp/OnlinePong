package api.database;


import java.util.List;

public interface IUserDAO {

    User createUser(String username, String password, int elo) throws DALException;

    User getUser(String username) throws DALException;

    void authenticateUser(String username, String password) throws DALException;

    void setElo(String username, int elo) throws DALException;

    List<User> getTopTen() throws DALException;

    void deleteUser(String username, String password) throws DALException;


    // ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
    // Exceptions

    class DALException extends Exception {
        public DALException(String msg){ super(msg); }
    }

    class WrongPasswordException extends DALException {
        private final String username;

        public WrongPasswordException(String username){
            super("Wrong password for user '" + username + "'");
            this.username = username;
        }

        public String getUsername() {
            return username;
        }
    }


    class UserExistsException extends DALException {
        private final String username;

        public UserExistsException(String username){
            super("Unknown user '" + username + "'");
            this.username = username;
        }

        public String getUsername() {
            return username;
        }
    }


    class UnknownUserException extends DALException {
        private final String username;

        public UnknownUserException(String username){
            super("Unknown user '" + username + "'");
            this.username = username;
        }

        public String getUsername() {
            return username;
        }
    }
}
