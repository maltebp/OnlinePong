package API.Controller;

import API.DataLayer.IUserDAO;
import API.DataLayer.IUserDTO;
import API.DataLayer.UserDAO;
import API.DataLayer.UserDTO;

import API.DataLayer.IUserDAO.DALException;

public class UserController implements IUserController{

    private IUserDAO UserDAO = new UserDAO();

    /**
     * @Author Simon, Claes
     * calls 'getDBUser' to send a IUserDTO to the API.
     * @param id The Id of the User we desire userdata from
     * @return IUserDTO:
     */
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

        }catch(DALException e){
            user.addScore(-500);
            return user;
        }
    }
    //possible issues with error-handling here.
    //if SQLException at "createUser", return message will be skipped (even though there is error handling at UserDAO level).
    public String checkScore(int id, int score){
        try{
            String returnMessage = UserDAO.newScore(id, score);
            return returnMessage;

        }catch(DALException e){
            e.printStackTrace();
            return "Something went wrong, user-score NOT added" + e.getMessage();
        }
    }

    //possible issues with error-handling here.
    //if SQLException at "createUser", return message will be skipped (even though there is error handling at UserDAO level).
    public String createUser(String username, String password){
        try{
            String returnMessage = UserDAO.createUser(username, password);
            return returnMessage;

        }catch(DALException e){
            e.getMessage();
            return "Soemthing went wrong, user NOT added"+e.getMessage();
        }
    }

    /**
     * @Author Simon
     * Calls 'chechHash' to compare password, with the saved and hashed password.
     * This is for user-validation.
     * @param id
     * @param password
     * @return boolean: whether the password is correct.
     */
    public boolean userValidation(int id, String password){
        try{
            boolean result = UserDAO.checkHash(id, password);
            return result;
        }catch(DALException e){
            e.getMessage();
            return false;
        }
    }
}