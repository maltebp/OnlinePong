package gameserver.control.databaseconnector;

import gameserver.model.Player;
import org.json.JSONObject;
import java.net.URL;



public class APIConnector implements DatabaseConnector {

    private APIConnection decodeMessages = new APIConnection();

    public void setPlayerInformation(Player player) {

        String resource = "/"+player.getUsername();
        URL urlForResource = decodeMessages.createURL(resource);
        decodeMessages.createConnection(urlForResource, "GET");
        JSONObject jsonObject = decodeMessages.getResponse();

        //Here we can update whatever we want
        player.setRating(jsonObject.getInt("elo"));
    }


    /**
     * Authenticates a given username + password combination
     */
    public boolean authenticatePlayer(String username, String password) {

        //Create connection
        String resource = "/AuthUser";
        URL urlForResource = decodeMessages.createURL(resource);
        decodeMessages.createConnection(urlForResource, "POST");

        //Create JSONObject to send
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username);
        jsonObject.put("password",password);

        //Sending JSONOBject
        decodeMessages.sendMessage(jsonObject);
        jsonObject = decodeMessages.getResponse();

        //Reading response from API
        return jsonObject.getInt("code") == 1;
    }


    /**
     * Updates the Elo-rating of the player in the database
     * to the current rating of the player object.
     * @param player Player object to retrieve Elo-rating from
     */
    public void updateElo(Player player){

        String resource = "/setElo";
        URL urlForResource = decodeMessages.createURL(resource);
        decodeMessages.createConnection(urlForResource, "POST");

        //Create JSONObject to sent
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", player.getUsername());
        jsonObject.put("elo",player.getRating());

        //Sending JSONOBject
        decodeMessages.sendMessage(jsonObject);
    }



}
