package gameserver.control.databaseconnector;

import gameserver.model.Player;
import org.json.JSONObject;

public class APIConnector implements DatabaseConnector {


    public void setPlayerInformation(Player player) {

        String resource = "/"+player.getUsername();

        APIConnection connection = new APIConnection(resource, "GET");
        JSONObject jsonObject = connection.getResponse();
        connection.close();

        //Here we can update whatever we want
        player.setRating(jsonObject.getInt("elo"));
    }


    /**
     * Authenticates a given username + password combination
     */
    public boolean authenticatePlayer(String username, String password) {

        //Create connection
        String resource = "/AuthUser";
        APIConnection connection = new APIConnection(resource, "POST");

        //Create JSONObject to send
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username);
        jsonObject.put("password",password);

        //Sending JSONOBject
        connection.sendMessage(jsonObject);
        jsonObject = connection.getResponse();

        connection.close();

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
        APIConnection connection = new APIConnection(resource, "POST");

        //Create JSONObject to sent
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", player.getUsername());
        jsonObject.put("elo",player.getRating());

        //Sending JSONOBject
        connection.sendMessage(jsonObject);
        connection.getResponse(); // We need to read response before the database will update
        connection.close();
    }



}
