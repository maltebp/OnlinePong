package API;

import API.DataLayer.IUserDAO;
import org.junit.Test;
import API.DataLayer.IUserDAO;
import API.DataLayer.IUserDTO;
import API.DataLayer.UserDAOArray;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UserDAOArrayTest {

    UserDAOArray testDAOArray = new UserDAOArray();


    /**
     * Testing the statuscodes for the createUser function. If one can create a user in, the return code will be 201, if not
     * the return code will be 409.
     */
    @Test
    public void createUser(){

        try {
            String statusCode = testDAOArray.createUser("testUser", "pass", 500);
            assertEquals("201", statusCode);
            testDAOArray.forceDeleteUser("testUser");

        }catch (IUserDAO.DALException e){
            e.printStackTrace();
        }
    }


    /**
     * Test if it is the wright user that will be extracted from the dataset.
     *
     */
    @Test
    public void getUser(){
        try {
            testDAOArray.createUser("UserToGet","123",100);
            IUserDTO user = testDAOArray.getUser("UserToGet");
            testDAOArray.forceDeleteUser("UserToGet");
            assertEquals("UserToGet",user.getUsername());
        }catch(IUserDAO.DALException e){
            e.printStackTrace();
        }
    }

    /**
     * Test if the uservalidation is working proporly
     * returns 200 if the username and password is valid else it will return 401.
     */
    @Test
    public void checkHash(){
        try {
            testDAOArray.createUser("UserToGet","123",100);
            String returnCode = testDAOArray.checkHash("UserToGet","123");

            assertEquals("200",returnCode);

            testDAOArray.forceDeleteUser("UserToGet");

        }catch(IUserDAO.DALException e){
            e.printStackTrace();
        }

    }

    /**
     * Test if the elo can be set.
     * Fist we inspect the responsecode 200 that indicates that the elo have been set.
     * Then we controles tha the responsecode 410 is sent if the method fails.
     * Finally, we controlls that the elo have been changed to 999 as commanded.
     */
    @Test
    public void setElo(){
        try {
            testDAOArray.createUser("UserToGet","123",100);
            String returnCode = testDAOArray.setElo("UserToGet",999);
            assertEquals("200",returnCode);
            assertEquals(999,testDAOArray.getUser("UserToGet").getElo());
            testDAOArray.forceDeleteUser("UserToGet");

        }catch(IUserDAO.DALException e){
            e.printStackTrace();
        }
    }

    /**
     * Testing retreving of the topTenList
     */
    @Test
    public void getTopTen() {
        try {
            //Here we create 12 users so that we know that we have 12 users in database.
            //The 10 users from Test22 to Test211 (Test2 + 11) will have the largest elo score.
            testDAOArray.createUser("eloTest0", "pass", 999999999);
            testDAOArray.createUser("eloTest1", "pass", 999999998);
            testDAOArray.createUser("eloTest2", "pass", 999999997);
            testDAOArray.createUser("eloTest3", "pass", 999999996);
            testDAOArray.createUser("eloTest4", "pass", 999999995);
            testDAOArray.createUser("eloTest5", "pass", 999999994);
            testDAOArray.createUser("eloTest6", "pass", 999999993);
            testDAOArray.createUser("eloTest7", "pass", 999999992);
            testDAOArray.createUser("eloTest8", "pass", 999999992);
            testDAOArray.createUser("eloTest9", "pass", 999999992);

            List<IUserDTO> users;
            users = testDAOArray.getTopTen();

            int elo = 999999999;

            for (int i = 0; i < 7; i++) {
                assertEquals(elo, users.get(i).getElo());
                elo--;
            }
            assertEquals(elo, users.get(7).getElo());
            assertEquals(elo, users.get(8).getElo());
            assertEquals(elo, users.get(9).getElo());

            testDAOArray.forceDeleteUser("eloTest0");
            testDAOArray.forceDeleteUser("eloTest1");
            testDAOArray.forceDeleteUser("eloTest2");
            testDAOArray.forceDeleteUser("eloTest3");
            testDAOArray.forceDeleteUser("eloTest4");
            testDAOArray.forceDeleteUser("eloTest5");
            testDAOArray.forceDeleteUser("eloTest6");
            testDAOArray.forceDeleteUser("eloTest7");
            testDAOArray.forceDeleteUser("eloTest8");
            testDAOArray.forceDeleteUser("eloTest9");

        } catch (IUserDAO.DALException e) {
            e.printStackTrace();
        }
    }



    @Test
    public void userDeleteUser(){
        try {
            testDAOArray.createUser("deleteTestUser", "pass", 1000);
            String successCode = testDAOArray.userDeleteUser("deleteTestUser", "pass");
            assertEquals("200", successCode);
        }catch(IUserDAO.DALException e){
            fail(e.getErrorCode() + e.getMessage());
            e.printStackTrace();
        }
    }


}
