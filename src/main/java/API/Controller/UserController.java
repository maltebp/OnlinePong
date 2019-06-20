package API.Controller;

import API.DataLayer.IUserDAO;
import API.DataLayer.IUserDAO.DALException;
import API.DataLayer.IUserDTO;
import API.DataLayer.UserDAOArray;
import API.DataLayer.UserDAOSQL;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;


public class UserController implements IUserController{

    private static boolean backup = false;

    //Initialize which Database you want the controller to connect to (ARRAY-backup or SQL)
    //Defaulted to SQL.
    private IUserDAO UserDAO = backup ? new UserDAOArray() : new UserDAOSQL();

    /**
     * @Author Simon, Claes
     * calls 'getDBUser' to send a IUserDTO to the API.
     * @param username The Id of the User we desire userdata from
     * @return IUserDTO:
     */
    @Override
    public JSONObject convertUser(String username) {
        JSONObject json = new JSONObject();
        try {
            IUserDTO user = UserDAO.getUser(username);
            String name = user.getUsername();
            int elo = user.getElo();
            json.put("code", "200");
            json.put("description", "OK.");
            json.put("username", name);
            json.put("elo", elo);
            return json;
        }catch(IUserDAO.DALException e){
            json.put("code", e.getErrorCode());
            json.put("description", e.getMessage());
            return json;
        }
    }


    //if SQLException at "createUser", return message will be skipped (even though there is error handling at UserDAOSQL level).
    public JSONObject createUser(JSONObject input){
        JSONObject output = new JSONObject();
        try {
            int elo = 1000;
            String username = input.getString("username");
            String password = input.getString("password");
            String code = UserDAO.createUser(username, password, elo);
            output.put("code", code);
            output.put("description", "User created.");
            return output;
        }catch(DALException e){
            output.put("code", e.getErrorCode());
            output.put("description", e.getMessage());
            return output;
        }
    }


    public JSONObject setElo(JSONObject input){
        JSONObject output = new JSONObject();
        try {
            String username = input.getString("username");
            int elo = input.getInt("elo");
            String code = UserDAO.setElo(username, elo);
            output.put("code", code);
            return output;
        }catch(DALException e){
            output.put("code", e.getErrorCode());
            output.put("description", e.getMessage());
            return output;
        }
    }

    /**
     * @Author Simon
     * Calls 'chechHash' to compare password, with the saved and hashed password.
     * This is for user-validation.
     * @param input
     * @return boolean: whether the password is correct.
     */
    public JSONObject userValidation(JSONObject input){
        JSONObject output = new JSONObject();

        try {
            String username = input.getString("username");
            String password = input.getString("password");
            String result = UserDAO.checkHash(username, password);
            return output.put("code", result);
        }catch(DALException e){
            output.put("code", e.getErrorCode());
            output.put("description", e.getMessage());
            return output;
        }
    }

    public JSONObject getTopTen(){
        JSONObject jUsers = new JSONObject();
        try{
            List<IUserDTO> iUsers = UserDAO.getTopTen();
            jUsers.put("code", "200");
            jUsers.put("description", "OK.");
            jUsers.put("users", iUsers);
            return jUsers;

        }catch(DALException e){
            jUsers.put("code", e.getErrorCode());
            jUsers.put("description", e.getMessage());
            return jUsers;
        }
    }

    public JSONObject deleteUser(JSONObject input){
        JSONObject output = new JSONObject();
        try{
            String username = input.getString("username");
            String password = input.getString("password");
            String code = UserDAO.userDeleteUser(username, password);
            output.put("code", code);
            output.put("description", "OK.");
            return output;
        }catch(DALException e){
            output.put("code", e.getErrorCode());
            output.put("description", e.getMessage());
            return output;
        }
    }

    /**
     * Sets the backup value to true, such that
     * a newly created UserController object will
     * use an array implementation of the IUserDAO
     * rather than the MySQL.
     */
    public static void useBackup(boolean toggle){
        backup = toggle;
    }
}