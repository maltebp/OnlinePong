package server;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import api.database.mongodb.MongoDatabase;
import api.services.API;
import api.services.UserService;
import gameserver.view.websocket.WebSocketEndpoint;
import io.javalin.Javalin;
import keymanager.KeyManager;


public class Server {

    private static final int PORT = 20000;
    private static boolean testMode = false;

    private static Javalin server = null;


    public static void main(String[] args) {
        KeyManager.loadKeys();

        List<String> argList = Arrays.asList(args);

        if (argList.contains("--test")){
            testMode = true;
            MongoDatabase.useNewTestDatabase();
            System.out.println("Running in test mode");
        }

        start();
    }

    public static void start(){

        // Setup generic config
        server = Javalin.create(config -> {
            config.contextPath = "/";
            config.enableCorsForAllOrigins();
            config.addStaticFiles("webapp");
        });

        server.start(PORT);

        // Setup API
        API.initialize(server);

        // Start Game server.Server
        WebSocketEndpoint gameServerEndpoint = new WebSocketEndpoint(server);

    }


    private static String getWebAppFolder() throws FileNotFoundException {
        String workingDir = System.getProperty("user.dir");
        File f;

        f = new File(workingDir + "/webapp");
        if (f.exists() && f.isDirectory()) {
            return f.getAbsolutePath();
        }

        f = new File("src/main/webapp");
        if (f.exists() && f.isDirectory()) {
            return f.getAbsolutePath();
        }

        f = new File("webapp");
        if (f.exists() && f.isDirectory()) {
            return f.getAbsolutePath();
        }

        throw new FileNotFoundException("Cannot find webapp folder");
    }



    public static boolean isTestMode(){
        return testMode;
    }


}
