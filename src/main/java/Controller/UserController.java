package Controller;

import DataLayer.IUserDAO;
import DataLayer.IUserDTO;
import DataLayer.UserDAO;
import DataLayer.UserDTO;

import java.sql.SQLException;

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
            //WARING: BAD PRATICE: [nice to have:] make this error statement say something about what went wrong.
            user = new UserDTO(-1, "Something went wrong");
        }
        try {
            IUserDTO newUser = UserDAO.getDBScore(user);
            return newUser;

        }catch(SQLException e){
            user.addScore(-500);
            return user;
        }
    }
    //TODO: This error check DOES NOT WORK.
    public String checkScore(int id, int score){
        try{
            UserDAO.newScore(id, score);
            return "User-score has been added to the database.";
        }catch(SQLException e){
            e.printStackTrace();
            return "Something went wrong, user-score NOT added.";
        }
    }

    //TODO: This error checck DOES NOT WORK.
    public String createUser(String username, String password){
        try{
            UserDAO.createUser(username, password);
            return "user has been added to the databse.";

        }catch(SQLException e){
            e.getMessage();
            return "Soemthing went wrong, user NOT  added";
        }
    }
}
