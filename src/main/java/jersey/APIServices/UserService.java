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
        return "Things are working, don't worry, but you need to enter a number in the URL. \n For example 'xyz/1' ";
    }

    //the Following function(s) are for 'rest-request'.
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public IUserDTO requestUser(@PathParam("id") int id) throws IUserDAO.DALException {
        IUserController userController = new UserController();
        IUserDTO test = userController.convertUser(id);
        return test;
    }

}
