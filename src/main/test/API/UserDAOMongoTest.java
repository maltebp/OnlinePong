package API;

import API.database.IUserDAO;
import API.database.IUserDTO;
import API.database.UserDAOSQL;
import API.database.mongodb.MongoDatabase;
import API.database.mongodb.UserDAOMongo;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import keymanager.KeyManager;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;


public class UserDAOMongoTest {

    IUserDAO testDAO = new UserDAOMongo();

    {
        KeyManager.loadKeys();
    }

    @Before
    public void clearDatabase(){
        MongoDatabase.useNewTestDatabase();
    }

    @Test
    public void createUser() {
        try {
            testDAO.createUser("swoldbye", "pass", 500);
            IUserDTO user = testDAO.getUser("swoldbye");
            assertEquals("swoldbye", user.getUsername());
            assertEquals(500, user.getElo());
        } catch (IUserDAO.DALException e) {
            fail("Code: " +e.getErrorCode() +" Desc: " + e.getMessage());
        }
    }

    @Test
    public void checkHash() {
        try {
            testDAO.createUser("swoldbye", "pass", 1000);
            String output = testDAO.checkHash("swoldbye", "pass");
            assertEquals("200", output);
        } catch (IUserDAO.DALException e) {
            fail("Code: " +e.getErrorCode() +" Desc: " + e.getMessage());
        }
    }

    @Test
    public void setElo() {
        try {
            testDAO.createUser("testUser", "pass", 1000);
            testDAO.setElo("testUser", 500);
            IUserDTO user = testDAO.getUser("testUser");
            testDAO.forceDeleteUser("testUser");
            assertEquals(500, user.getElo());
        } catch (IUserDAO.DALException e) {
            fail("Code: " +e.getErrorCode() +" Desc: " + e.getMessage());
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
            testDAO.createUser("eloTest10", "pass", 999999991);
            testDAO.createUser("eloTest11", "pass", 999999233);
            testDAO.createUser("eloTest12", "pass", 999999123);
            testDAO.createUser("eloTest13", "pass", 999999423);

            List<IUserDTO> users;
            users = testDAO.getTopTen();

            int elo = 999999999;
            for (int i = 0; i < 7; i++) {
                assertEquals(elo, users.get(i).getElo());
                elo--;
            }
            assertEquals(elo, users.get(7).getElo());
            assertEquals(elo, users.get(8).getElo());
            assertEquals(elo, users.get(9).getElo());

        } catch (IUserDAO.DALException e) {
            fail("Code: " +e.getErrorCode() +" Desc: " + e.getMessage());
        }
    }

    @Test
    public void userDeleteUser(){
        try {
            testDAO.createUser("deleteTestUser", "pass", 1000);
            String successCode = testDAO.userDeleteUser("deleteTestUser", "pass");
            assertEquals("200", successCode);
        }catch(IUserDAO.DALException e){
            fail("Code: " +e.getErrorCode() +" Desc: " + e.getMessage());
        }
    }
}
