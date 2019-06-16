package API;

import API.Controller.IUserController;
import API.Controller.UserController;
import API.DataLayer.IUserDAO;
import API.DataLayer.UserDAOSQL;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserControllerTest {
    UserDAOSQL testDAO = new UserDAOSQL();
    IUserController testCon = new UserController();


    @Test
    public void convertUser() {

    }

    @Test
    public void createUser() {
    }

    @Test
    public void setElo() {
    }

    @Test
    public void userValidation() {
        try {
            JSONObject jUser = new JSONObject("{\"username\":\"swoldbye\",\"password\":\"pass\"}");
            JSONObject jWrongPass = new JSONObject("{\"username\":\"swoldbye\",\"password\":\"wrong\"}");
            JSONObject jWrongUser = new JSONObject("{\"username\":\"doesntExist\",\"password\":\"pass\"}");

            testDAO.createUser("swoldbye", "pass");
            JSONObject corrPass;
            JSONObject wrongPass;
            JSONObject wrongUser;
            corrPass = testCon.userValidation(jUser);
            wrongPass = testCon.userValidation(jWrongPass);
            wrongUser = testCon.userValidation(jWrongUser);

            assertEquals("1", corrPass.get("code"));
            assertEquals("0", wrongPass.get("code"));
            assertEquals("0", wrongUser.get("code"));


        }catch(IUserDAO.DALException e){
            fail(e.getMessage());
        }

    }

    @Test
    public void getTopTen() {
        try {
            testDAO.createUser("eloTest0", "pass");
            testDAO.setElo("eloTest0", 999999999);
            testDAO.createUser("eloTest1", "pass");
            testDAO.setElo("eloTest1", 999999998);
            testDAO.createUser("eloTest2", "pass");
            testDAO.setElo("eloTest2", 999999997);
            testDAO.createUser("eloTest3", "pass");
            testDAO.setElo("eloTest3", 999999996);
            testDAO.createUser("eloTest4", "pass");
            testDAO.setElo("eloTest4", 999999995);
            testDAO.createUser("eloTest5", "pass");
            testDAO.setElo("eloTest5", 999999994);
            testDAO.createUser("eloTest6", "pass");
            testDAO.setElo("eloTest6", 999999993);
            testDAO.createUser("eloTest7", "pass");
            testDAO.setElo("eloTest7", 999999992);
            testDAO.createUser("eloTest8", "pass");
            testDAO.setElo("eloTest8", 999999992);
            testDAO.createUser("eloTest9", "pass");
            testDAO.setElo("eloTest9", 999999992);

            JSONArray jUsers;
            jUsers = testCon.getTopTen();

            JSONObject user;
            int elo = 999999999;
            String name = "eloTest";
            for (int i = 0; i < 7; i++) {
                user = jUsers.getJSONObject(i);
                assertEquals(name + Integer.toString(i), user.getString("username"));
                assertEquals(elo, user.getInt("elo"));
                elo--;
            }
            for (int i = 7; i < 10; i++) {
                user = jUsers.getJSONObject(i);
                assertEquals(name + Integer.toString(i), user.getString("username"));
                assertEquals(elo, user.getInt("elo"));
            }
            testDAO.deleteUser("eloTest0");
            testDAO.deleteUser("eloTest1");
            testDAO.deleteUser("eloTest2");
            testDAO.deleteUser("eloTest3");
            testDAO.deleteUser("eloTest4");
            testDAO.deleteUser("eloTest5");
            testDAO.deleteUser("eloTest6");
            testDAO.deleteUser("eloTest7");
            testDAO.deleteUser("eloTest8");
            testDAO.deleteUser("eloTest9");
        } catch (IUserDAO.DALException e) {
            fail(e.getMessage());
        }
    }
}


