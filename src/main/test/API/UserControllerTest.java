package API;

import API.Controller.IUserController;
import API.Controller.UserController;
import API.DataLayer.IUserDAO;
import API.DataLayer.IUserDTO;
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
        try {
            testDAO.createUser("swoldbye", "pass", 1000);
            JSONObject user = testCon.convertUser("swoldbye");
            JSONObject wrongUser = testCon.convertUser("doesntExist");
            testDAO.forceDeleteUser("swoldbye");
            assertEquals("1", user.getString("code"));
            assertEquals("swoldbye", user.getString("username"));
            assertEquals(1000, user.getInt("elo"));

            assertEquals("-2", wrongUser.getString("code"));
        } catch (IUserDAO.DALException e) {
            fail(e.getMessage());
        }

    }

    @Test
    public void createUser() {
        try {
            JSONObject output;
            JSONObject jUser = new JSONObject("{\"username\":\"swoldbye\",\"password\":\"pass\"}");
            output = testCon.createUser(jUser);
            assertEquals("1", output.getString("code"));
            IUserDTO user;
            user = testDAO.getUser("swoldbye");
            assertEquals("swoldbye", user.getUsername());
            assertEquals(1000, user.getElo());

            String vali = testDAO.checkHash("swoldbye", "pass");
            assertEquals("1", vali);
            testDAO.forceDeleteUser("swoldbye");

        } catch (IUserDAO.DALException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void setElo() {
        try {
            JSONObject jUser = new JSONObject("{\"username\":\"swoldbye\",\"elo\":\"2000\"}");
            JSONObject jUserOutput;
            testDAO.createUser("swoldbye", "pass", 1000);
            jUserOutput = testCon.setElo(jUser);
            testDAO.forceDeleteUser("swoldbye");

            assertEquals(1, jUserOutput.getInt("code"));

        } catch (IUserDAO.DALException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void userValidation() {
        try {
            JSONObject jUser = new JSONObject("{\"username\":\"swoldbye\",\"password\":\"pass\"}");
            JSONObject jWrongPass = new JSONObject("{\"username\":\"swoldbye\",\"password\":\"wrong\"}");
            JSONObject jWrongUser = new JSONObject("{\"username\":\"doesntExist\",\"password\":\"pass\"}");

            testDAO.createUser("swoldbye", "pass", 1000);
            JSONObject corrPass;
            JSONObject wrongPass;
            JSONObject wrongUser;
            corrPass = testCon.userValidation(jUser);
            wrongPass = testCon.userValidation(jWrongPass);
            wrongUser = testCon.userValidation(jWrongUser);
            testDAO.forceDeleteUser("swoldbye");

            assertEquals("1", corrPass.get("code"));
            assertEquals("-1", wrongPass.get("code"));
            assertEquals("-1", wrongUser.get("code"));


        } catch (IUserDAO.DALException e) {
            fail(e.getMessage());
        }

    }

    @Test
    public void getTopTen() {
        try {
            testDAO.createUser("eloTest0", "pass", 999999999);
            testDAO.createUser("eloTest1", "pass", 999999998);
            testDAO.createUser("eloTest2", "pass", 999999997);
            testDAO.createUser("eloTest3", "pass", 999999996);
            testDAO.createUser("eloTest4", "pass", 999999995);
            testDAO.createUser("eloTest5", "pass", 999999994);
            testDAO.createUser("eloTest6", "pass", 999999993);
            testDAO.createUser("eloTest7", "pass", 999999992);
            testDAO.createUser("eloTest8", "pass", 999999992);
            testDAO.createUser("eloTest9", "pass", 999999992);


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
                boolean failer = true;
                user = jUsers.getJSONObject(i);
                if (user.getString("username").equals("eloTest7")) {
                    failer = false;
                }
                if (user.getString("username").equals("eloTest8")) {
                    failer = false;
                }
                if (user.getString("username").equals("eloTest9")) {
                    failer = false;
                }
                if (failer == true) {
                    fail("Username: " + user.getString("username") + " is the wrong username");
                    assertEquals(elo, user.getInt("elo"));
                }
                testDAO.forceDeleteUser("eloTest0");
                testDAO.forceDeleteUser("eloTest1");
                testDAO.forceDeleteUser("eloTest2");
                testDAO.forceDeleteUser("eloTest3");
                testDAO.forceDeleteUser("eloTest4");
                testDAO.forceDeleteUser("eloTest5");
                testDAO.forceDeleteUser("eloTest6");
                testDAO.forceDeleteUser("eloTest7");
                testDAO.forceDeleteUser("eloTest8");
                testDAO.forceDeleteUser("eloTest9");
            }
            }catch(IUserDAO.DALException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void deleteUser(){
        try {
            testDAO.createUser("deleteTestUser", "pass", 1000);
            JSONObject correctUser = new JSONObject("{\"username\":\"deleteTestUser\",\"password\":\"pass\"}");
            JSONObject success;
            JSONObject wrongUser = new JSONObject("{\"username\":\"deleteTestUser\",\"password\":\"wrongPass\"}");
            JSONObject fail;
            fail = testCon.deleteUser(wrongUser);
            success = testCon.deleteUser(correctUser);

            assertEquals("-2", fail.getString("code"));
            assertEquals("1", success.getString("code"));
        }catch(IUserDAO.DALException e){
            fail(e.getMessage());
        }
    }
}

