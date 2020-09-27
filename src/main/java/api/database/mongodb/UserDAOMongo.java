package api.database.mongodb;


import api.database.IUserDAO;
import api.database.User;
import com.mongodb.WriteResult;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import dev.morphia.Datastore;
import dev.morphia.query.Query;
import dev.morphia.query.UpdateOperations;
import dev.morphia.query.UpdateResults;
import java.util.Comparator;
import java.util.List;


/**
 *
 * Implementation of IUserDAO which uses a Mongo database
 *
 * @author Malte (maltebp)
 */
public class UserDAOMongo implements IUserDAO {


    /**
     * @author Malte (maltebp)
     *
     * This function gets User Data From the Database
     *
     * @param username The User, that one wants DB Data from
     */
    @Override
    public User getUser(String username) throws DALException {
        User user = MongoDatabase.getDatabase().createQuery(User.class).field("username").equal(username).first();
        if( user == null )
            throw new UnknownUserException(username);
        return user;
    }


    /**
     * @author Malte (maltebp)
     *
     */
    public User createUser(String username, String password, int elo) throws DALException {
        Argon2 argon2 = Argon2Factory.create();
        String hashedPassword = argon2.hash(10, 65536, 1, password);

        // Check if user exists
        if( MongoDatabase.getDatabase().createQuery(User.class).field("username").equal(username).count() > 0 )
            throw new UserExistsException(username);

        // Create user
        User newUser = new User(username, hashedPassword, elo);
        MongoDatabase.getDatabase().save(newUser);

        return newUser;
    }


    /**
     * @author Malte (maltebp)
     */
    public void authenticateUser(String username, String password) throws DALException{
        Argon2 argon2 = Argon2Factory.create();
        User user = getUser(username);

        boolean validPassword = argon2.verify(user.getPassword(), password);
        if( !validPassword )
            throw new WrongPasswordException(username);
    }


    /**
     * @author Malte (maltebp)
     */
    public void setElo(String username, int elo) throws DALException{
        Datastore db = MongoDatabase.getDatabase();

        Query<User> query = db.createQuery(User.class)
                .field("username")
                .equal(username);

        UpdateOperations<User> operations = db.createUpdateOperations(User.class)
                .set("elo", elo);

        UpdateResults results = db.update(query, operations);

        if( results.getUpdatedCount() == 0 )
            throw new UnknownUserException(username);
    }


    public List<User> getTopTen() throws DALException {
        List<User> users = MongoDatabase.getDatabase().createQuery(User.class).find().toList();
        users.sort(Comparator.comparingInt(User::getElo).reversed());

        if( users.size() <= 10 )
            return users;

        return users.subList(0, 10);
    }


    public void deleteUser(String username, String password) throws DALException{
            authenticateUser(username, password);

            Query<User> query = MongoDatabase.getDatabase().createQuery(User.class).field("username").equal(username);
            WriteResult result = MongoDatabase.getDatabase().delete(query);
            if( result.getN() == 0 )
                throw new DALException("Couldn't delete user '" + username + "' for unknown reasons");
    }
}



