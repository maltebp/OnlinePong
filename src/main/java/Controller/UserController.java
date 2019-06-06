package Controller;

import DataLayer.IUserDAO;
import DataLayer.IUserDTO;
import DataLayer.UserDAO;
import DataLayer.UserDTO;

public class UserController implements IUserController{
    private IUserDAO UserDAO = new UserDAO();

    @Override
    public IUserDTO convertUser(int id) {
        IUserDTO user = new UserDTO(4, "this didn't work");
        try{
            user = UserDAO.getDBUser(id);
            if(user == null){
                user = new UserDTO();
                user.setUsername("no user");
            }
        }catch(IUserDAO.DALException e){
            e.getMessage();
            user = new UserDTO(-1, "Something went wrong");
        }
        return user;
    }
}
