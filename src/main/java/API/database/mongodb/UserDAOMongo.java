package API.database.mongodb;


import API.database.IUserDAO;
import API.database.IUserDTO;
import com.mongodb.WriteResult;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import dev.morphia.Datastore;
import dev.morphia.query.Query;
import dev.morphia.query.UpdateOperations;
import dev.morphia.query.UpdateResults;
import java.util.ArrayList;
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
    public IUserDTO getUser(String username) throws DALException {
        User user = MongoDatabase.getDatabase().createQuery(User.class).field("username").equal(username).first();
        if( user == null )
            throw new DALException("410","User doesn't exist");

        return user.toUserDTO();
    }


    /**
     * @author Malte (maltebp)
     *
     */
    public String createUser(String username, String password, int elo) throws DALException {
        Argon2 argon2 = Argon2Factory.create();
        String hashedPassword = argon2.hash(10, 65536, 1, password);

        // Check if user exists
        if( MongoDatabase.getDatabase().createQuery(User.class).field("username").equal(username).count() > 0 )
            throw new DALException("409", "A user with that username already exists.");

        // Create user
        User newUser = new User(username, hashedPassword, elo);
        MongoDatabase.getDatabase().save(newUser);

        return "201"; // Why are we returning this?
    }


    /**
     * @author Malte (maltebp)
     */
    public String checkHash(String username, String password) throws DALException{
        Argon2 argon2 = Argon2Factory.create();
        IUserDTO user = getUser(username);

        boolean validPassword = argon2.verify(user.getPassword(), password);
        if( !validPassword )
            throw new DALException("401", "Incorrect password");

        return "200";
    }


    /**
     * @author Malte (maltebp)
     */
    public String setElo(String username, int elo) throws DALException{
        Datastore db = MongoDatabase.getDatabase();

        Query<User> query = db.createQuery(User.class)
                .field("username")
                .equal(username);

        UpdateOperations<User> operations = db.createUpdateOperations(User.class)
                .set("elo", elo);

        UpdateResults results = db.update(query, operations);

        if( results.getUpdatedCount() == 0 )
            throw new DALException("410", "Cannot update elo for unknown user '" + username + "'");

        return "200";
    }


    public List<IUserDTO> getTopTen() throws DALException{
        List<User> users = MongoDatabase.getDatabase().createQuery(User.class).find().toList();
        users.sort(Comparator.comparingInt(User::getElo).reversed());

        // Conver max 10 users to UserDTO
        List<IUserDTO> iUsers = new ArrayList<>();
        int numUsersToCopy = Math.min(users.size(), 10);
        for( int i=0; i < numUsersToCopy; i++ ){
            iUsers.add(users.get(i).toUserDTO());
        }

        return iUsers;
    }


    public String forceDeleteUser(String username) throws DALException{
        Query<User> query = MongoDatabase.getDatabase().createQuery(User.class).field("username").equal(username);
        WriteResult result = MongoDatabase.getDatabase().delete(query);
        if( result.getN() == 0 )
            throw new DALException("410", "Couldn't delete unknown user '" + username + "'");
        return "200";
    }


    public String userDeleteUser(String username, String password) throws DALException{
            String auth = checkHash(username, password);
            if( !auth.equals("200") )
                throw new DALException("401", "Incorrect Password");
            return forceDeleteUser(username);
    }
}



