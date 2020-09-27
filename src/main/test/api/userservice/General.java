package api.userservice;

import api.database.User;
import com.mongodb.util.JSON;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;


public class General {

    private static final int    PORT = 20000;
    private static final String URL = "http://localhost:" + PORT + "/api";


    @Before
    public void clearData() throws Exception {
        HttpResponse<String> response = Unirest.put(URL + "/testmode/clear")
                .header("Content-Type", "application/json")
                .asString();

        if( response.getStatus() != 200 )
            throw new Exception("Server is not in test mode! (Response was: " + response.getStatus() + ")");
    }


    @Test
    public void createUser(){
        {
            JSONObject body = new JSONObject();
            body.put("username", "TestUser");
            body.put("password", "pass");

            HttpResponse<String> response = Unirest.post(URL + "/user")
                    .header("Content-Type", "application/json")
                    .body(body.toString())
                    .asString();

            assertEquals(201, response.getStatus());
        }

        {// Checking you can't create user twice
            JSONObject body = new JSONObject();
            body.put("username", "TestUser");
            body.put("password", "pass");

            HttpResponse<String> response = Unirest.post(URL + "/user")
                    .header("Content-Type", "application/json")
                    .body(body.toString())
                    .asString();

            assertEquals(403, response.getStatus());
        }

    }


    @Test
    public void getUser(){
        JSONObject body = new JSONObject();
        body.put("username", "TestUser2");
        body.put("password", "pass");

        {
            // Create user
            HttpResponse<String> response = Unirest.post(URL + "/user")
                    .header("Content-Type", "application/json")
                    .body(body.toString())
                    .asString();
            assertEquals(201, response.getStatus());
        }

        // Get user
        {
            HttpResponse<String> response = Unirest.get(URL + "/user/TestUser2")
                    .header("Content-Type", "application/json")
                    .asString();

            assertEquals(200, response.getStatus());

            JSONObject jsonUser = new JSONObject(response.getBody()).getJSONObject("result");

            assertEquals("TestUser2", jsonUser.getString("username"));
            assertEquals(1000, jsonUser.getInt("elo"));
        }
    }




    @Test
    public void deleteUser(){
        // Delete non-existing user
        {
            JSONObject body = new JSONObject();
            body.put("password", "pass");

            HttpResponse<String> response = Unirest.delete(URL + "/user/TestUser")
                    .body(body.toString())
                    .header("Content-Type", "application/json")
                    .asString();

            assertEquals(404, response.getStatus());
        }

        // Create user
        {
            JSONObject body = new JSONObject();
            body.put("username", "TestUser");
            body.put("password", "pass");
            HttpResponse<String> response = Unirest.post(URL + "/user")
                    .header("Content-Type", "application/json")
                    .body(body.toString())
                    .asString();
            assertEquals(201, response.getStatus());
        }

        // Delete user
        {
            JSONObject body = new JSONObject();
            body.put("password", "pass");

            HttpResponse<String> response = Unirest.delete(URL + "/user/TestUser")
                    .body(body.toString())
                    .header("Content-Type", "application/json")
                    .asString();

            assertEquals(204, response.getStatus());
        }
    }


    @Test
    public void authenticateUser(){

        // Authenticate non-existing user
        {
            JSONObject body = new JSONObject();
            body.put("password", "pass");

            HttpResponse<String> response = Unirest.post(URL + "/user/TestUser/authenticate")
                    .body(body.toString())
                    .header("Content-Type", "application/json")
                    .asString();

            assertEquals(404, response.getStatus());
        }

        // Create user
        createUser();

        // Authenticating with wrong password
        {
            JSONObject body = new JSONObject();
            body.put("password", "pas");

            HttpResponse<String> response = Unirest.post(URL + "/user/TestUser/authenticate")
                    .body(body.toString())
                    .header("Content-Type", "application/json")
                    .asString();

            assertEquals(401, response.getStatus());
        }

        // Authenticating with correct password
        {
            JSONObject body = new JSONObject();
            body.put("password", "pass");

            HttpResponse<String> response = Unirest.post(URL + "/user/TestUser/authenticate")
                    .body(body.toString())
                    .header("Content-Type", "application/json")
                    .asString();

            assertEquals(204, response.getStatus());
        }
    }




    @Test
    public void setElo(){

        // Setting elo on non-existing user
        {

            HttpResponse<String> response = Unirest.put(URL + "/user/TestUser/elo/1337")
                    .header("Content-Type", "application/json")
                    .asString();

            assertEquals(404, response.getStatus());
        }

        // Create user
        createUser();

        // Updating elo
        {
            HttpResponse<String> response = Unirest.put(URL + "/user/TestUser/elo/1337")
                    .header("Content-Type", "application/json")
                    .asString();
            assertEquals(204, response.getStatus());
        }

        // Checking elo
        {
            HttpResponse<String> response = Unirest.get(URL + "/user/TestUser")
                    .header("Content-Type", "application/json")
                    .asString();

            JSONObject jsonUser = new JSONObject(response.getBody()).getJSONObject("result");

            assertEquals(200, response.getStatus());

            assertEquals("TestUser", jsonUser.getString("username"));
            assertEquals(1337, jsonUser.getInt("elo"));
        }
    }


    /**
     * Helper method for getTopTen test
     */
    public void createUserWithElo(String username, int elo){
        {
            JSONObject body = new JSONObject();
            body.put("username", username);
            body.put("password", "pass");

            HttpResponse<String> response = Unirest.post(URL + "/user")
                    .header("Content-Type", "application/json")
                    .body(body.toString())
                    .asString();
            assertEquals(201, response.getStatus());
        }

        // Updating elo
        {
            HttpResponse<String> response = Unirest.put(URL + "/user/" + username+ "/elo/" +elo)
                    .header("Content-Type", "application/json")
                    .asString();
            assertEquals(204, response.getStatus());
        }
    }


    @Test
    public void getTopTen(){

        List<User> users  = Arrays.asList(
           new User("User1", 1800),
           new User("User2", 2000),
           new User("User3", 1700),
           new User("User4", 1600),
           new User("User6", 1200),
           new User("User5", 1900),
           new User("User7", 1500),
           new User("User8", 1100),
           new User("User9", 800),
           new User("User10", 1400),
           new User("User11", 1300),
           new User("User12", 900),
           new User("User13", 1000)
        );


        for( User user : users ){
            createUserWithElo(user.getUsername(), user.getElo());
        }

        // Getting top ten
        HttpResponse<String> response = Unirest.get(URL + "/topten")
                .header("Content-Type", "application/json")
                .asString();
        assertEquals(200, response.getStatus());

        // Loading list
        JSONArray toptenJson = new JSONObject(response.getBody()).getJSONObject("result").getJSONArray("users");
        List<User> topten = new ArrayList<>();

        for( int i=0; i<toptenJson.length(); i++){
            JSONObject jsonUser = toptenJson.getJSONObject(i);
            topten.add(User.fromJSONObject(jsonUser, false));
        }

        users.sort(Comparator.comparingInt(User::getElo).reversed());
        List<User> expectedTopTen = users.subList(0, 10);

        for( int i=0; i<10; i++){
            assertEquals(expectedTopTen.get(i).getUsername(), topten.get(i).getUsername());
            assertEquals(expectedTopTen.get(i).getElo(), topten.get(i).getElo());
        }

    }






}
