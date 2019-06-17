package API.Jersey;

import API.Controller.IUserController;
import API.Controller.UserController;
import org.json.JSONArray;
import org.json.JSONObject;


import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author Claes, Simon
 * ======================================================
 *          IF(YOU ARE A USER){WELCOME!!!!}
 * ======================================================
 *  - As a User of the API this is
 *  the the most usefull part for you
 *  - Here you fint the services the API provides
 */
@Path("/service")
public class UserService {

    @GET
    @Produces("TEXT/HTML")
    public String publicServiceMessage(){
        return "|||||||||||||||||||||<br>\n" +
                "||API User Manual:||<br>\n" +
                "|||||||||||||||||||||<br>\n" +
                "||<br>\n" +
                "=================================||<br>\n" +
                "Things are working, DON'T WORRY!,||<br>\n" +
                "=================================||<br>\n" +
                "||<br>\n" +
                "However you have couple of options ||<br>\n" +
                "(1.) [GET DATA FROM A USER]: ||<br>\n " +
                "you need to enter a number in the URL. \r Try for example 'service/1 ||<br>\n" +
                "||<br>\n" +
                "(2.) {INSERT A NEW SCORE MADE BY A USER} ||<br>\n " +
                "Try for example 'service/1/[the score you want to insert] ||<br>\n" +
                "||<br>\n" +
                "(3.) [CREATE USER] <br>\n" +
                "Try for example 'service/createUser/[the User Name you want]&[The Password YouWant] <br>\n" +
                "<br>\n";
    }

    /**This function sendes'rest-request'.
     * This returns data on a desired user
     * @param username the user whos data you want
     * @return All the data there is on a given user.
     */


    @Path("/{username}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String requestUser(@PathParam("username") String username){
        IUserController userController = new UserController();
        JSONObject json = userController.convertUser(username);
        return json.toString();
    }

    /**x
     * @Author Simon, Claes
     * Creates a user.
     * @param msg
     * @return String: whether successful or not.
     */
    @Path("/createUser")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String createUser(String msg){
        IUserController userController = new UserController();
        JSONObject output = userController.createUser(new JSONObject(msg));
        return output.toString();
    }

    /**
     * @Author Simon
     * compares given password with the user's hashed database-password.
     * @param msg
     * @return String: whether the password was correct or not.
     */
    @Path("/AuthUser")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String userValidation(String msg){
        IUserController userController = new UserController();
        JSONObject output = userController.userValidation(new JSONObject(msg));
        return output.toString();
    }

    @Path("/setElo")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String setElo(String msg){
        IUserController userController = new UserController();
        JSONObject json = userController.setElo(new JSONObject(msg));
        return json.toString();
    }


    @Path("/get10")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String get10(){
        IUserController userController = new UserController();
        JSONArray json = userController.getTopTen();
        return json.toString();
    }

    @Path("/DeleteUser")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteUser(String msg){
        IUserController userController = new UserController();
        JSONObject output = userController.deleteUser(new JSONObject(msg));
        return output.toString();
    }

}
