package API;

import API.DataLayer.IUserDAO;
import API.DataLayer.IUserDTO;
import API.DataLayer.UserDAOSQL;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class UserDAOSQLTest {

    UserDAOSQL testDAO = new UserDAOSQL();
    Argon2 argon2 = Argon2Factory.create();


    @Test
    public void getUser() {

        try {
            testDAO.createUser("testUser", "pass", 500);
            IUserDTO user = testDAO.getUser("testUser");
            testDAO.deleteUser("testUser");
            assertEquals("testUser", user.getUsername());
            assertEquals(500, user.getElo());
        } catch (IUserDAO.DALException e) {
            fail(e.getMessage());
        }

    }

    @Test
    public void createUser() {
        try {
            testDAO.createUser("swoldbye", "pass", 500);
            IUserDTO user = testDAO.getUser("swoldbye");
            testDAO.deleteUser("swoldbye");
            assertEquals("swoldbye", user.getUsername());
            assertEquals(500, user.getElo());
        } catch (IUserDAO.DALException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void checkHash() {
        try {
            testDAO.createUser("swoldbye", "pass", 1000);
            String output = testDAO.checkHash("swoldbye", "pass");
            testDAO.deleteUser("swoldbye");
            assertEquals("1", output);
            testDAO.deleteUser("swoldbye");
        } catch (IUserDAO.DALException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void setElo() {
        try {
            testDAO.createUser("testUser", "pass", 1000);
            testDAO.setElo("testUser", 500);
            IUserDTO user = testDAO.getUser("testUser");
            testDAO.deleteUser("testUser");
            assertEquals(500, user.getElo());
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
