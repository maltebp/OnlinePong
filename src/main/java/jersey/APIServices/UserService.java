package jersey.APIServices;

import Controller.IUserController;
import Controller.UserController;
import DataLayer.IUserDAO;
import DataLayer.IUserDTO;
import DataLayer.UserDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
@Path("/xyz")
public class UserService {

    @GET
    @Produces("TEXT/HTML")
    public String publicServiceMessage(){
        return "Things are working, don't worry, but you need to enter a number in the URL. \r Try for example 'xyz/1' ";
    }

    //the Following function(s) are for 'rest-request'.
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public IUserDTO requestUser(@PathParam("id") int id){
        IUserController userController = new UserController();
        IUserDTO user = userController.convertUser(id);
        return user;
    }
}
