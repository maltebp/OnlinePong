package gameserver.control.databaseconnector;

import gameserver.model.Player;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.json.JSONObject;

public class APIConnector implements DatabaseConnector {

    private static final String API_URL = "http://localhost:20000/api";


    public void getPlayerInformation(Player player) throws DatabaseException {
        HttpResponse<String> response = Unirest.get(API_URL + "/user/" + player.getUsername())
                .header("Content-Type", "application/json")
                .asString();

        if( response.getStatus() == 404 )
            return; // TODO: Do something meaningful here

        if( response.getStatus() == 500 )
            throw new DatabaseException("An internal exception occured at the API " + response.getBody());

        if( response.getStatus() != 200 )
            throw new DatabaseException("An unexpected status was received from API: " + response.getStatus());

        JSONObject result = new JSONObject(response.getBody()).getJSONObject("result");

        //Here we can update whatever we want
        player.setRating(result.getInt("elo"));
    }


    /**
     * Authenticates a given username + password combination
     */
    public boolean authenticatePlayer(String username, String password) throws DatabaseException{
        JSONObject body = new JSONObject();
        body.put("password", password);

        HttpResponse<String> response = Unirest.post(API_URL + "/user/" + username + "/authenticate")
                .body(body.toString())
                .header("Content-Type", "application/json")
                .asString();

        if( response.getStatus() == 204 )
            return true;

        if( response.getStatus() == 401 )
            return false;

        if( response.getStatus() == 500 )
            throw new DatabaseException("An internal exception occured at the API " + response.getBody());

        throw new DatabaseException("An unexpected response was received from API: " + response.getStatus());
    }


    /**
     * Updates the Elo-rating of the player in the database
     * to the current rating of the player object.
     * @param player Player object to retrieve Elo-rating from
     */
    public void updateElo(Player player) throws DatabaseException {
        HttpResponse<String> response = Unirest.put(API_URL + "/user/" + player.getUsername() + "/elo/" + player.getRating() )
                .header("Content-Type", "application/json")
                .asString();

        if( response.getStatus() != 204 )
            throw new DatabaseException("Couldn't update player rating (unexpected status code from server: " + response.getBody() + ")");
    }



}
