
package API.database;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to act as database, by using a local ArrayList
 *
 * @author Andreas and Kristian
 */

public class UserDAOArray implements IUserDAO{

    private static ArrayList<IUserDTO> userList = new ArrayList<>();
    private static boolean isInstantiated = false;


    public UserDAOArray(){
        if( !isInstantiated) {
            try {
                // Creating standard users for database
                createUser("Torben", "1234", 1243);
                createUser("Karsten", "1234", 1304);
                createUser("Bent", "1234", 780);
                createUser("Kenneth", "1234", 1803);
                isInstantiated = true;
            } catch (DALException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public IUserDTO getUser(String username) throws DALException {
        IUserDTO user = searchUser(username);
        if (user != null) {
            return user;
        } else {
            throw new DALException("410","User doesn't exist");
        }
    }



    @Override
    public String createUser(String username, String password, int elo) throws DALException {
        if(!(searchUser(username)==null)) {
            throw new DALException("409", "A user with that username already exists.");
        }
        else {
            Argon2 argon2 = Argon2Factory.create();
            String hashedPassword = argon2.hash(10, 65536, 1, password);
            IUserDTO user = new UserDTO(username, elo);
            user.setPassword(hashedPassword);
            userList.add(user);
            return "201";
        }
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
    public String checkHash(String username, String password) throws DALException {
        Argon2 argon2 = Argon2Factory.create();

        IUserDTO user = searchUser(username);
        if (user == null) {
            throw new DALException("410", "User doesn't exist.");
        } else {
            if (argon2.verify(user.getPassword(), password)) {
                return "200";
            } else throw new DALException("401", "Incorrect password.");

        }
    }

    /**
     * set the Elo of a player in the userList.
     * @return String: error message.
     */
    @Override
    public String setElo(String username, int elo) throws DALException {
        if ((searchUser(username) == null)) {
            throw new DALException("410", "User doesn't exist.");
        } else for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUsername().equals(username)) {
                userList.get(i).setElo(elo);
                break;
            }
        }
        return "200";
    }

    /**
     * Top ten users (based on ELO) in desc order.
     * @return  gereric List.
     */
    @Override
    public List<IUserDTO> getTopTen(){
        ArrayList<IUserDTO> topUsers = new ArrayList<>();
        int size = userList.size() >= 10 ? 10: (userList.size());
        IUserDTO blankUser = new UserDTO("", 0);
        for(int y = 0; y < size; y++){
            topUsers.add(blankUser);
        }
        for(IUserDTO n : userList){
            for(int j = 0; j < size; j++){
                if(n.getElo() > topUsers.get(j).getElo()){
                    for(int x = (size - 1); x > j;x--){
                        topUsers.set(x, topUsers.get(x-1));
                    }
                    topUsers.set(j, n);
                    break;
                }
            }
        }
        return topUsers;
    }

    @Override
    public String forceDeleteUser(String username) throws DALException {
        for(int i = 0; i < userList.size();i++){
            if(userList.get(i).getUsername().equals(username)){
                userList.remove(i);
                return "200";
            }
        }
        throw new DALException("410", "User doesn't exist.");
    }

    @Override
    public String userDeleteUser(String username, String password) throws DALException {
        String auth = checkHash(username, password);
        if(auth.equals("200")){
            return forceDeleteUser(username);
        }
        else throw new DALException("410", "User doesn't exist.");
    }
}

