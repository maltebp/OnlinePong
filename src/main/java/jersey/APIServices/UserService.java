package jersey.APIServices;

import Controller.IUserController;
import Controller.UserController;
import DataLayer.IUserDAO;
import DataLayer.IUserDTO;
import DataLayer.UserDTO;

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
        return "=================================||<br>\n" +
                "Things are working, DON'T WORRY!,||<br>\n" +
                "=================================||<br>\n" +
                "||<br>\n" +
                "However you have couple of options ||<br>\n" +
                "(1.) [GET DATA FROM A USER]: ||<br>\n " +
                "you need to enter a number in the URL. \r Try for example 'service/1 ||<br>\n" +
                "(2.) {INSERT A NEW SCORE MADE BY A USER} ||<br>\n " +
                "Try for example 'service/1/[the score you want to insert] ||<br>";
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

}
