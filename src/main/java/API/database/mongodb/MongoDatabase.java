package API.database.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import keymanager.KeyManager;

import java.util.HashSet;

public class MongoDatabase {

    private static boolean initialized = false;
    private static boolean useTestDb = false;

    private static MongoClient connection;
    private static Datastore db;

    private static void initialize(){
        // Login info
        String url = "onlinepong.s71y3.mongodb.net";
        String username = "api";
        String password = KeyManager.getMongoKey();
        String databaseName = "game_database" + (useTestDb ? "_test" : "");

        // Connect
        String connectionUrl = "mongodb+srv://"+username+":"+password+"@"+ url + "/" + databaseName + "?retryWrites=true&w=majority";
        MongoClientURI uri = new MongoClientURI(connectionUrl);
        connection = new MongoClient(uri);

        // Setup database
        HashSet<Class> classes = new HashSet<>();
        classes.add(User.class);

        Morphia morphia  = new Morphia(classes);
        db = morphia.createDatastore(connection, databaseName);

        // Clean test database
        if( useTestDb ){
            db.getDatabase().drop();
        }

        initialized = true;
    }


    public static void clearConnection(){
        connection.close();
        connection = null;
        db = null;
        initialized = false;
    }


    public static void useNewTestDatabase(){
        if( initialized )
            clearConnection();
        useTestDb = true;
    }

    public static Datastore getDatabase(){
        if( !initialized )
            initialize();
        return db;
    }
}
