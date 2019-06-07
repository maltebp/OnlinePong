package jersey.APIServices;

import API.Controller.IUserController;
import API.Controller.UserController;
import API.DataLayer.IUserDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author claes
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
     * @param id the user whos data you want
     * @return All the data there is on a given user.
     */
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public IUserDTO requestUser(@PathParam("id") int id){
        IUserController userController = new UserController();
        IUserDTO user = userController.convertUser(id);
        return user;
    }

    @Path("/{id}/{score}")
    @GET
    @Produces("text/HTML")
    public String requestUser(@PathParam("id") int id, @PathParam("score") int score){
        IUserController userController = new UserController();
        String returnStatement = userController.checkScore(id, score);
        return returnStatement;
    }

    @Path("/createUser/{username}&{password}")
    @GET
    @Produces("text/HTML")
    public String createUser(@PathParam("username") String username, @PathParam("password") String password){
        IUserController userController = new UserController();
        String returnStatement = userController.createUser(username, password);
        return returnStatement;
    }


    @Path("/checkUser/{id}&{password}")
    @GET
    @Produces("text/HTML")
    public String userValidation(@PathParam("id") int id, @PathParam("password") String password){
        IUserController userController = new UserController();
        boolean result = userController.userValidation(id, password);
        if(result == true)
            return "Access Granted";
        else
            return "Access Denied";
    }
}
