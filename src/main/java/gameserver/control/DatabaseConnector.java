package gameserver.control;

import gameserver.model.Player;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DatabaseConnector {
Decoder decodeMessages = new Decoder();
static String url = "";

    /**
     * Retrieve information about the user from the databse
     * and updates the given player object with the information
     *
     * @param player Player object to put the data into
     */
    public void setPlayerInformation(Player player) {
        //TODO: Implement this
    }


    /**
     * Authenticates a given username + password combination
     */
    public boolean authenticatePlayer(String username, String password){



        //TODO: Implement this
        return true;
    }


    /**
     * Updates the Elo-rating of the player in the database
     * to the current rating of the player object.
     * @param player Player object to retrieve Elo-rating from
     */
    public void updateElo(Player player){
        //TODO: Implement this
    }
}
/*

    public static boolean getUserStats(int id) {
        url = url + id;

        try {
            URL urlForGetRequest = new URL(url);


            HttpURLConnection connection = (HttpURLConnection) urlForGetRequest.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");



            JSONObject respons = getResponseFromRest(connection);

            System.out.println(respons.get("userId"));

            return true;

        }catch (IOException e){
            e.getMessage();
        }
        return false;




        try {
            URL urlForGetRequest = new URL(url);

            HttpURLConnection connection = (HttpURLConnection) urlForGetRequest.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("userid", Integer.toString(id) );


            //If the HTTP responsecode is 202
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String readLine = null;
                BufferedReader in = new BufferedReader(

                        new InputStreamReader(connection.getInputStream()));

                StringBuffer response = new StringBuffer();

                while ((readLine = in.readLine()) != null) {

                    response.append(readLine);

                }
                in.close();

                String res = response.toString();


                JSONObject ob1 = generateObject(res);

                System.out.println(ob1.getString("username"));

                System.out.println(res);
                return true;
            }

        } catch (IOException e) {
            e.getMessage();
        }
        return false;



    }


    private static JSONObject getResponseFromRest(HttpURLConnection conn) {
        HttpURLConnection connection = conn;
        try {
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String readLine = null;
                BufferedReader in = new BufferedReader(

                        new InputStreamReader(connection.getInputStream()));

                StringBuffer responded = new StringBuffer();

                while ((readLine = in.readLine()) != null) {

                    responded.append(readLine);

                }
                in.close();

                System.out.println(responded.toString());

                JSONObject obj = new JSONObject(responded.toString());

                return obj;
            }

        } catch (IOException e) {
            e.getMessage();
        }
        return null;
    }
 */