package keymanager;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class KeyManager {

    private static final String FILE_NAME = "keys.json";

    private static JSONObject json = null;
    private static String mongoKey = null;
    private static String mysqlKey = null;


    public static void loadKeys() {
        InputStream in = KeyManager.class.getResourceAsStream("/keys.json");

        if( in == null ){
            System.out.println("WARNING: " + FILE_NAME + " could not be found or accessed by the keymanager.KeyManager. Necessary keys weren't loaded");
            return;
        }

        String input = "input";

        try{
            // Read the file
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder inputBuilder = new StringBuilder();
            int c = 0;
            while((c = reader.read()) != -1){
                inputBuilder.append((char) c);
            }
            input = inputBuilder.toString();

            // Convert to JSON
            json = new JSONObject(input);
            mongoKey = loadKey("mongo_db");

            // NOTE: SQL IS NOT BEING LOADED YET!

            in.close();
        }catch(IOException e){
            System.out.println("Unexpected error when loading keys!");
            e.printStackTrace();
        }


    }


    private static String loadKey(String keyName){
        if( !json.has(keyName) ){
            System.out.println("WARNING: Couldn't find key '" + keyName + "'!");
            return null;
        }
        return json.getString(keyName);
    }


    public static String getMongoKey(){
        if( mongoKey == null )
            throw new NullPointerException("Mongo key has not been loaded!");
        return mongoKey;
    }


    public static String getMySQLKey() {
        if( mysqlKey == null )
            throw new NullPointerException("MySQL key has not been loaded!");
        return mysqlKey;
    }
}
