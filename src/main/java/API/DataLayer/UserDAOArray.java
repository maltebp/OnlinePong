
package API.DataLayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to act as database, by using a local ArrayList
 * FixMe Has not hashing implemented
 *
 * @author Andreas and Kristian
 */

    public class UserDAOArray implements IUserDAO{

    ArrayList<IUserDTO> userList = createArray();

    /**
     * Creates an ArrayList with 10 UserDTO model
     *
     * @return  ArrayList of 10 UserDTO
     */
    private ArrayList<IUserDTO> createArray() {
        for(int i = 0; i<10; i++){
            userList.add(new UserDTO("Test"+i));
        }
        return null;
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
    public String createUser(String username, String password) throws DALException {
        IUserDTO user = new UserDTO(username);
        user.setPassword(password);
        userList.add(user);
        return "User is created";
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
        return "true";
    }

    @Override
    public String setElo(String username, int elo) throws DALException {
        return null;
    }

    @Override
    public List<IUserDTO> getTopTen() throws DALException {
        return null;
    }
}

