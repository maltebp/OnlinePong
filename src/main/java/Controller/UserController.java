package Controller;

import DataLayer.IUserDAO;
import DataLayer.IUserDTO;
import DataLayer.UserDAO;
import DataLayer.UserDTO;

public class UserController implements IUserController{
    private IUserDAO UserDAO = new UserDAO();

    public IUserDTO convertUser(int id) {
        IUserDTO user;
        try{
            user = UserDAO.getDBUser(id);
            if(user == null){
                user = new UserDTO();
                user.setUsername("no user");
            }
        }catch(IUserDAO.DALException e){
            e.getMessage();
            user = new UserDTO();
            user.setUsername("Something went wrong");
        }
        return user;
    }
}
