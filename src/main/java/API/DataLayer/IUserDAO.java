package API.DataLayer;

/**
 * The purpose of this class is being able to access the UserData.
 * As is also stated in the name Data Access Object (DAO)
 *
 * @author Claes and Simon
 *
 */
public interface IUserDAO {

    /**
     * Retrieves a user by ID
     *
     * @param id    The ID of the user
     * @return      User object
     * @throws DALException
     */
    IUserDTO getUser(int id) throws DALException;

    /**
     * Retrieves an User object with the latest score from database
     *
     * @param user  User object
     * @return      User object
     * @throws DALException
     */
    IUserDTO getScore(IUserDTO user) throws DALException;

    /**
     * //FixMe (KNA) Spørgsmål: Skal det her være elo eller win/loss? Sidstnævnte kræver 2 værdier
     *
     * @param id        ID of User
     * @param score
     * @return          String confirmation
     * @throws DALException
     */
    String newScore(int id, int score) throws DALException;

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
     * @param id        ID of User
     * @param password  Password of User
     * @return          True if password is correct, else false
     * @throws DALException
     */
    boolean checkHash(int id, String password) throws DALException;

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
