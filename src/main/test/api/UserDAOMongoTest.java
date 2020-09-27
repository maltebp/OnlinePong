package api;

import api.database.IUserDAO;
import api.database.User;
import api.database.mongodb.MongoDatabase;
import api.database.mongodb.UserDAOMongo;
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
    public void createUser() throws IUserDAO.DALException {
        testDAO.createUser("swoldbye", "pass", 500);
        User user = testDAO.getUser("swoldbye");
        assertEquals("swoldbye", user.getUsername());
        assertEquals(500, user.getElo());
    }

    @Test
    public void authenticate() throws IUserDAO.DALException {
        testDAO.createUser("swoldbye", "pass", 1000);
        testDAO.authenticateUser("swoldbye", "pass");
    }

    @Test
    public void setElo() throws IUserDAO.DALException {
        testDAO.createUser("testUser", "pass", 1000);
        testDAO.setElo("testUser", 500);
        User user = testDAO.getUser("testUser");
        assertEquals(500, user.getElo());
    }

    @Test
    public void getTopTen() throws IUserDAO.DALException {
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

        List<User> users;
        users = testDAO.getTopTen();

        int elo = 999999999;
        for (int i = 0; i < 7; i++) {
            assertEquals(elo, users.get(i).getElo());
            elo--;
        }
        assertEquals(elo, users.get(7).getElo());
        assertEquals(elo, users.get(8).getElo());
        assertEquals(elo, users.get(9).getElo());

    }

    @Test
    public void userDeleteUser() throws IUserDAO.DALException {
        testDAO.createUser("deleteTestUser", "pass", 1000);
        testDAO.deleteUser("deleteTestUser", "pass");
    }
}
