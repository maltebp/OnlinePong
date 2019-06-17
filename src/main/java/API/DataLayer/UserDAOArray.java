
package API.DataLayer;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class to act as database, by using a local ArrayList
 * FixMe Has not hashing implemented
 *
 * @author Andreas and Kristian
 */

    public class UserDAOArray implements IUserDAO{

    ArrayList<IUserDTO> userList = new ArrayList<>();

    /**
     * Creates an ArrayList with 10 UserDTO model
     *
     * @return  ArrayList of 10 UserDTO
     */
    private UserDAOArray() throws DALException {

            for (int i = 0; i < 100; i++) {
               createUser("Test" + i, "123" + i, 1000 + i);
            }
    }



    @Override
    public IUserDTO getUser(String username) throws DALException {
        IUserDTO user = searchUser(username);
        if (user != null) {
            return user;
        } else {
            throw new DALException("User doesn't exist");
        }
    }



    @Override
    public String createUser(String username, String password, int elo) throws DALException {
        if(searchUser(username)==null) {
            Argon2 argon2 = Argon2Factory.create();
            String hashedPassword = argon2.hash(10, 65536, 1, password);
            IUserDTO user = new UserDTO(username, elo);
            user.setPassword(hashedPassword);
            userList.add(user);
            return "201";
        }else return "409";

    }

    /**
     * Search through the ArrayList for User by its ID
     *
     * @param username    username of User
     * @return      User object
     */
    private IUserDTO searchUser(String username) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUsername().equals(username)) {
                return userList.get(i);
            }
        }
        return null;
    }

    public String checkHash(String username, String password) {
        Argon2 argon2 = Argon2Factory.create();

        IUserDTO user = searchUser(username);
        if(argon2.verify(user.getPassword(),password)){
            return "1";
        }else return "-1";

    }

    @Override
    public String setElo(String username, int elo) throws DALException {
        for(int i = 0; i<userList.size();i++){
            if(userList.get(i).getUsername().equals(username)){
                userList.get(i).setElo(elo);
                return "200";
            }
            return "410";
        }


        return null;
    }

    @Override
    public List<IUserDTO> getTopTen() throws DALException {

        ArrayList<IUserDTO> tempUserList = new ArrayList<>();
        int count = 0;

        for(int i = 0; i < userList.size();i++){
            for(int j = i+1; j < userList.size(); j++){
                if(userList.get(i).getElo()>userList.get(j).getElo()){

                }else count++;

            }
            if(count<10){tempUserList.add(userList.get(i));}
            count = 0;
        }
        return tempUserList;
    }

    @Override
    public String forceDeleteUser(String username) throws DALException {
        return null;
    }

    @Override
    public String userDeleteUser(String username, String password) throws DALException {
        return null;
    }




    public static void main(String[] args) {




        try {
            UserDAOArray arr = new UserDAOArray();
            //List<IUserDTO> test = arr.getTopTen();
            arr.createUser("Andreas","Hans", 10000);
            System.out.println(arr.checkHash("Andreas","per"));
/*for(int i = 0; i< test.size(); i++) {
    System.out.println(test.get(i).getElo() +", "+test.get(i).getUsername()+", "+test.get(i).getPassword() );
}*/
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

