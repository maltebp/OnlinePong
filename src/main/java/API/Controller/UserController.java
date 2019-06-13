package API.Controller;

import API.DataLayer.IUserDAO;
import API.DataLayer.IUserDTO;
import API.DataLayer.UserDAOSQL;
import API.DataLayer.UserDTO;
import API.DataLayer.IUserDAO.DALException;
import com.sun.deploy.cache.DefaultLocalApplicationProperties;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;


public class UserController implements IUserController{

    private IUserDAO UserDAO = new UserDAOSQL();

    /**
     * @Author Simon, Claes
     * calls 'getDBUser' to send a IUserDTO to the API.
     * @param username The Id of the User we desire userdata from
     * @return IUserDTO:
     */
    @Override
    public JSONObject convertUser(String username) {
        JSONObject json = new JSONObject();
        try{
           IUserDTO user = UserDAO.getUser(username);
            if(user == null){
                json.put("code", "-2");
                json.put("ERROR Msg", "User is null");
            }
            else{
                String name = user.getUsername();
                int elo = user.getElo();
                json.put("code", "1");
                json.put("username", name);
                json.put("elo", elo);
            }
            return json;
        }catch(IUserDAO.DALException e){
            json.put("code", "-3");
            json.put("ERROR Msg", "Something went wrong");
            json.put("Stack-Trace", e.getMessage());
            return json;
        }
    }


    //FixMe possible issues with error-handling here.
    //if SQLException at "createUser", return message will be skipped (even though there is error handling at UserDAOSQL level).
    public JSONObject createUser(JSONObject input){
        String username = input.getString("username");
        String password = input.getString("password");

        JSONObject output = new JSONObject();
        try{
            String code = UserDAO.createUser(username,password);
            output.put("code", code);
            return output;
        }catch(DALException e){
            output.put("code", "-2");
            output.put("ERROR Msg", "Something went wrong, user not added");
            output.put("Stack-Trace", e.getMessage());
            return output;
        }
    }

    public JSONObject setElo(JSONObject input){
        String username = input.getString("username");
        int elo = input.getInt("elo");

        JSONObject output = new JSONObject();
        try{
            String code = UserDAO.setElo(username, elo);
            output.put("code", code);
            return output;
        }catch(DALException e){
            output.put("code", "-2");
            output.put("ERROR Msg", "Something went wrong, elo not found");
            output.put("Stack-Trace", e.getMessage());
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
        String username = input.getString("username");
        String password = input.getString("password");
        JSONObject output = new JSONObject();
        try{
            String result = UserDAO.checkHash(username, password);
            return output.put("code", result);
        }catch(DALException e){
            output.put("code", "-2");
            output.put("ERROR Msg", "Something went wrong, validation incomplete");
            output.put("Stack-Trace", e.getMessage());
            return output;
        }
    }

    public JSONArray getAll(){
        JSONArray jUsers = new JSONArray();

        try{
            List<IUserDTO> iUsers = UserDAO.getAll();
            for(IUserDTO x: iUsers){
                JSONObject jObject = new JSONObject();
                jObject.put("username", x.getUsername());
                jObject.put("elo", x.getElo());
                jUsers.put(jObject);
            }
            return jUsers;

        }catch(DALException e){
            return null;
        }
    }
}