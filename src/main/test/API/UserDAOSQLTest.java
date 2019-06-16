package API;

import API.DataLayer.IUserDAO;
import API.DataLayer.IUserDTO;
import API.DataLayer.UserDAOSQL;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserDAOSQLTest {

    UserDAOSQL testDAO = new UserDAOSQL();
    Argon2 argon2 = Argon2Factory.create();


    @Test
    public void getUser() {

        try{
            testDAO.createUser("testUser", "pass");
            testDAO.setElo("testUser", 500);
            IUserDTO user = testDAO.getUser("testUser");
            assertEquals("testUser", user.getUsername());
            assertEquals(500, user.getElo());
        }catch(IUserDAO.DALException e){
            fail(e.getMessage());
        }

    }

    @Test
    public void createUser() {
        try {
            testDAO.createUser("swoldbye", "pass");


        }catch(IUserDAO.DALException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void checkHash() {

        try{
            testDAO.createUser("swoldbye", "pass");
            String output = testDAO.checkHash("swoldbye", "pass");
            assertEquals("1", output);
            testDAO.deleteUser("swoldbye");
            }catch(IUserDAO.DALException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void setElo() {
        try{
            testDAO.createUser("testUser", "pass");
            testDAO.setElo("testUser", 500);
            IUserDTO user = testDAO.getUser("testUser");
            assertEquals(500, user.getElo());
            testDAO.deleteUser("testUser");
        }catch(IUserDAO.DALException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void getTopTen() {

    }
}