package API.Controller;

import API.DataLayer.IUserDAO;
import API.DataLayer.IUserDAO.DALException;
import API.DataLayer.IUserDTO;
import API.DataLayer.UserDAOSQL;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;


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


    //FixMe possible issues with error-handling here.
    //if SQLException at "createUser", return message will be skipped (even though there is error handling at UserDAOSQL level).
    public JSONObject createUser(JSONObject input){
        JSONObject output = new JSONObject();
        try {
            int elo = 1000;
            String username = input.getString("username");
            String password = input.getString("password");
            String code = UserDAO.createUser(username, password, elo);
            output.put("code", code);
            output.put("description", "OK.");
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

    public JSONArray getTopTen(){
        try{
            JSONArray jUsers = new JSONArray();
            List<IUserDTO> iUsers = UserDAO.getTopTen();
            JSONObject codeObj = new JSONObject();
            codeObj.put("code", "200");
            codeObj.put("description", "OK.");
            jUsers.put(codeObj);
            for(IUserDTO x: iUsers){
                JSONObject jObject = new JSONObject();
                jObject.put("username", x.getUsername());
                jObject.put("elo", x.getElo());
                jUsers.put(jObject);
            }
            return jUsers;

        }catch(DALException e){
            JSONArray errorArr = new JSONArray();
            JSONObject errorObj = new JSONObject();
            errorObj.put("code", e.getErrorCode());
            errorObj.put("description", e.getMessage());
            errorArr.put(errorObj);
            return errorArr;
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
}