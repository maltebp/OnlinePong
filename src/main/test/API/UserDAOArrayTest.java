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

    UserDAOArray testDAOArray;


    /**
     * Testing the statuscodes for the createUser function. If one can create a user in, the return code will be 201, if not
     * the return code will be 409.
     */
    @Test
    public void createUser(){

        try {
            testDAOArray  = new UserDAOArray();
            String statusCode = testDAOArray.createUser("testUser", "pass", 500);
            assertEquals("201", statusCode);

            //Test if you can create a user that already exist. If you can't the success code will be 409
            String statusCode2 = testDAOArray.createUser("testUser", "pass", 500);
            assertEquals("409",statusCode2);


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
            testDAOArray = new UserDAOArray();
            testDAOArray.createUser("UserToGet","123",100);
            IUserDTO user = testDAOArray.getUser("UserToGet");
            assertEquals("UserToGet",user.getUsername());

            //Test the retun value when a user does not exist.
            IUserDTO nonExistingUser = testDAOArray.getUser("UserNonExisting");
            assertEquals("User doesn't exist",nonExistingUser.getUsername());

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
            testDAOArray = new UserDAOArray();
            testDAOArray.createUser("UserToGet","123",100);
            String returnCode = testDAOArray.checkHash("UserToGet","123");
            String wrongReturnCode = testDAOArray.checkHash("UserToGet","234");

            assertEquals("200",returnCode);
            assertEquals("401",wrongReturnCode);

            testDAOArray.userDeleteUser("UserToGet","123");

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
            testDAOArray = new UserDAOArray();
            testDAOArray.createUser("UserToGet","123",100);
            String returnCode = testDAOArray.setElo("UserToGet",999);

            String returnCodeNoUser = testDAOArray.setElo("NNonExistingUser",100000);

            assertEquals("200",returnCode);
            assertEquals("410",returnCodeNoUser);

            assertEquals(999,testDAOArray.getUser("UserToGet").getElo());

testDAOArray.userDeleteUser("UserToGet","123");

        }catch(IUserDAO.DALException e){
            e.printStackTrace();
        }
    }

    /**
     * Testing retreving of the topTenList
     */
    @Test
    public void getTopTen(){
        try {
            testDAOArray = new UserDAOArray();
            //Here we create 12 users so that we know that we have 12 users in database.
            //The 10 users from Test22 to Test211 (Test2 + 11) will have the largest elo score.
            List<IUserDTO> controleList = new ArrayList<>();

            for (int i = 0; i< 12; i++){
                testDAOArray.createUser("Test2"+i,"12"+0,i);
                controleList.add(testDAOArray.getUser("Test2"+i));
            }

            //Romoving the two first usrelemets from the userlist.
            controleList.remove(0);
            controleList.remove(0);
            List<IUserDTO> topTenList = testDAOArray.getTopTen();

            for(int i = 0; i < controleList.size(); i++) {
                System.out.println(topTenList.get(i).getUsername()+", "+controleList.get(i).getUsername());


            }
            for(int i = 0; i < controleList.size(); i++) {

                assertEquals(topTenList.get(i),controleList.get(i));

            }

    }catch(IUserDAO.DALException e){
        e.printStackTrace();
    }
    }
    @Test
    public void userDeleteUser(){
        try {
            testDAOArray = new UserDAOArray();
            testDAOArray.createUser("deleteTestUser", "pass", 1000);
            String failCode = testDAOArray.userDeleteUser("deleteTestUser", "wrongPass");
            String successCode = testDAOArray.userDeleteUser("deleteTestUser", "pass");
            assertEquals("200", successCode);
            assertEquals("410", failCode);
        }catch(IUserDAO.DALException e){
            fail(e.getMessage());
        }
    }


}
