package api.services;


import api.database.IUserDAO.*;
import api.database.User;
import api.database.mongodb.UserDAOMongo;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.plugin.openapi.annotations.ContentType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class UserService {

    private final UserDAOMongo dao = new UserDAOMongo();


    public UserService(Javalin server) {
        server.post("user", this::createUser);
        server.get("user/:username", this::getUser);
        server.delete("user/:username", this::deleteUser);
        server.post("user/:username/authenticate", this::authenticateUser);
        server.put("user/:username/elo/:elo", this::updateElo);
        server.get("topten", this::getTopTen);
    }


    public void createUser(Context context ) throws DALException {
        JSONObject body = new JSONObject(context.body());
        User createdUser = User.fromJSONObject(body, true);
        dao.createUser(createdUser.getUsername(), createdUser.getPassword(), 1000);
        Response.setResult(context, 201, "User created", createdUser.toJSONObject());
    }


    public void getUser(Context context) throws DALException {
        String username = context.pathParam("username");
        User requestedUser = dao.getUser(username);
        Response.setResult(context, 200, "User found", requestedUser.toJSONObject());
    }


    public void deleteUser(Context context) throws DALException {
        String username = context.pathParam("username");
        String password = new JSONObject(context.body()).getString("password");
        dao.deleteUser(username, password);
        Response.setResult(context, 204, "User '" + username + "' was deleted", null);
    }


    public void authenticateUser(Context context) throws DALException {
        String username = context.pathParam("username");
        String password = new JSONObject(context.body()).getString("password");
        dao.authenticateUser(username, password);
        Response.setResult(context, 204, "Successfuly authentication as '" + username + "'", null);
    }


    public void updateElo(Context context) throws DALException {
        String username = context.pathParam("username");
        int elo = Integer.parseInt(context.pathParam("elo"));
        dao.setElo(username, elo);
        Response.setResult(context, 204, "The elo of user '" + username + "' was updated to " + elo, null);
    }


    public void getTopTen(Context context) throws DALException {
        List<User> users = dao.getTopTen();

        JSONArray jsonUsers = new JSONArray();
        for( User user : users ){
            jsonUsers.put(user.toJSONObject());
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("users", jsonUsers);
        Response.setResult(context, 200, "Found " + users.size() + " users", jsonObject);
    }
}
