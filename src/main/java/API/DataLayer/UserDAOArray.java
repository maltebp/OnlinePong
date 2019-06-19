
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

    static ArrayList<IUserDTO> userList = new ArrayList<>();

    /**
     * Creates an ArrayList with 10 UserDTO model
     *
     * @return  ArrayList of 10 UserDTO
     */
    public UserDAOArray() throws DALException {

           /* for (int i = 0; i < 2; i++) {
               createUser("Test" + i, "123" + i, 1000 + i);
            }*/
    }



    @Override
    public IUserDTO getUser(String username) throws DALException {
        IUserDTO user = searchUser(username);
        if (user != null) {
            return user;
        } else {
            throw new DALException("1","User doesn't exist");
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


    /**
     * Compare a password, to that user's hashed password in the userList.
     * This is for user-validation.
     * @param password
     * @return boolean: whether successful or not.
     * @throws DALException
     */
    public String checkHash(String username, String password) {
        Argon2 argon2 = Argon2Factory.create();

        IUserDTO user = searchUser(username);
        if(argon2.verify(user.getPassword(),password)){
            return "200";
        }else return "401";

    }

    /**
     * set the Elo of a player in the userList.
     * @param username
     * @param elo
     * @return String: error message.
     * @throws DALException
     */
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


    /**
     * Recieves at top ten list, based on the best elo scores.
     * @return  gereric List.
     * @throws DALException
     */
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
        for(int i = 0; i < userList.size();i++){
            if(userList.get(i).getUsername().equals(username)){
                userList.remove(i);
                return "200";
            }
        }
        return "410";
    }

    @Override
    public String userDeleteUser(String username, String password) throws DALException {
        String auth = checkHash(username, password);
        if(auth.equals("200")){
            return forceDeleteUser(username);
        }
        else return "410";
    }
}

