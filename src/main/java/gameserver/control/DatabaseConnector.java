package gameserver.control;

import gameserver.model.Player;
import org.json.JSONObject;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class DatabaseConnector {
    Decoder decodeMessages = new Decoder();


    /**
     * Retrieve information about the user from the databse
     * and updates the given player object with the information
     *
     * @param player Player object to put the data into
     */
    public void setPlayerInformation(Player player) {


        String resource = "/"+player.getUsername();
        URL urlForResource = decodeMessages.createURL(resource);
        HttpURLConnection connection = decodeMessages.createConnection(urlForResource, "GET");

        JSONObject jsonObject = decodeMessages.readInputStream(connection);

        //Here we can update whatever we want
        player.setRating(jsonObject.getInt("elo"));

        System.out.println(jsonObject.get("username"));
        System.out.println(player.getRating());

    }


    /**
     * Authenticates a given username + password combination
     */
    public boolean authenticatePlayer(String username, String password) {

        //Create connection
        String resource = "/AuthUser";
        URL urlForResource = decodeMessages.createURL(resource);
        HttpURLConnection connection = decodeMessages.createConnection(urlForResource, "POST");


        //Create JSONObject to sent
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username);
        jsonObject.put("password",password);

        String jsonInputString = jsonObject.toString();


        //Sending JSONOBject
        try(OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);


            jsonObject = decodeMessages.readInputStream(connection);

            //The message from the API is decoded
            return decodeMessages.decodeMessage(jsonObject);

        }catch (IOException e){
            e.getMessage();
        }
          return false;

    }


    /**
     * Updates the Elo-rating of the player in the database
     * to the current rating of the player object.
     * @param player Player object to retrieve Elo-rating from
     */
    public void updateElo(Player player){

        String resource = "/setElo";
        URL urlForResource = decodeMessages.createURL(resource);
        HttpURLConnection connection = decodeMessages.createConnection(urlForResource, "POST");

        //Create JSONObject to sent
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", player.getUsername());
        jsonObject.put("elo",player.getRating());

        String jsonInputString = jsonObject.toString();

        //Sending JSONOBject
        try(OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);

            jsonObject = decodeMessages.readInputStream(connection);


        }catch (IOException e){
            e.printStackTrace();
        }


    }


    public static void main(String[] args) {

        Player malte = new Player();


        malte.setUsername("Andreas");
        DatabaseConnector con = new DatabaseConnector();

        System.out.println("authendicate Player");
        con.authenticatePlayer("Andreas","123");

        System.out.println("setPlayerInformation");
        con.setPlayerInformation(malte);
        malte.setRating(333);

        System.out.println("updateElo");
        con.updateElo(malte);

        System.out.println("setPlayerInformation");
        con.setPlayerInformation(malte);
    }
}
