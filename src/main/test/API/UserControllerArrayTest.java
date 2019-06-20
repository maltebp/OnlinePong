package API;

import API.Controller.IUserController;
import API.Controller.UserController;
import API.DataLayer.IUserDAO;
import API.DataLayer.IUserDTO;
import API.DataLayer.UserDAOArray;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserControllerArrayTest {
    private IUserDAO testDAO;
    private IUserController testCon;

    @Before
    public void createUserController(){
        testDAO = new UserDAOArray();
        UserController.useBackup();
        testCon = new UserController();
    }

    @Test
    public void convertUser() {
        try {
            testDAO.createUser("swoldbye", "pass", 1000);
            JSONObject user = testCon.convertUser("swoldbye");
            testDAO.forceDeleteUser("swoldbye");
            assertEquals("200", user.getString("code"));
            assertEquals("swoldbye", user.getString("username"));
            assertEquals(1000, user.getInt("elo"));
        } catch (IUserDAO.DALException e) {
            fail("Code: " +e.getErrorCode() +" Desc: " + e.getMessage());
        }
    }

    @Test
    public void createUser() {
        try {
            JSONObject output;
            JSONObject jUser = new JSONObject("{\"username\":\"swoldbye\",\"password\":\"pass\"}");
            output = testCon.createUser(jUser);
            assertEquals("201", output.getString("code"));
            IUserDTO user;
            user = testDAO.getUser("swoldbye");
            assertEquals("swoldbye", user.getUsername());
            assertEquals(1000, user.getElo());

            String vali = testDAO.checkHash("swoldbye", "pass");
            assertEquals("200", vali);
            testDAO.forceDeleteUser("swoldbye");

        } catch (IUserDAO.DALException e) {
            fail("Code: " +e.getErrorCode() +" Desc: " + e.getMessage());
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

            assertEquals("200", jUserOutput.getString("code"));

        } catch (IUserDAO.DALException e) {
            fail("Code: " +e.getErrorCode() +" Desc: " + e.getMessage());
        }
    }

    @Test
    public void userValidation() {
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


            JSONObject object;
            object = testCon.getTopTen();
            JSONArray jUsers = object.getJSONArray("users");

            JSONObject user;
            int elo = 999999999;
            String name = "eloTest";
            for (int i = 0; i < 7; i++) {
                user = jUsers.getJSONObject(i);
                assertEquals(name + i, user.getString("username"));
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
            }
        } catch (IUserDAO.DALException e) {
            e.printStackTrace();
            fail("Code: " + e.getErrorCode() + " Desc: " + e.getMessage());
        } finally {
            try {
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
            } catch (IUserDAO.DALException e) {
                e.printStackTrace();
                fail("Code: " + e.getErrorCode() + " Desc: " + e.getMessage());
            }
        }
    }


    @Test
    public void deleteUser() {
        try {
            testDAO.createUser("deleteTestUser", "pass", 1000);
            JSONObject user = new JSONObject("{\"username\":\"deleteTestUser\",\"password\":\"pass\"}");
            JSONObject success;
            success = testCon.deleteUser(user);

            assertEquals("200", success.getString("code"));
        }catch(IUserDAO.DALException e){
            fail("Code: " +e.getErrorCode() +" Desc: " + e.getMessage());
        }
    }
}
