package API.DataLayer;

import java.util.ArrayList;

/**
 * Class to act as database, by using a local ArrayList
 */
public class UserDAOArray implements IUserDAO {

    ArrayList<IUserDTO> userList = createArray();

    /**
     * Creates an ArrayList with 10 UserDTO objects
     *
     * @return  ArrayList of 10 UserDTO
     */
    private ArrayList<IUserDTO> createArray() {
        for(int i = 0; i<10; i++){
            userList.add(new UserDTO(i, "Test"+i));
        }
        return null;
    }

    @Override
    public IUserDTO getUser(int id) throws DALException {
        IUserDTO user = searchUser(id);
        if (user != null) {
            return user;
        } else {
            throw new DALException("User doesn't exist");
        }
    }

    @Override
    public IUserDTO getScore(IUserDTO user) throws DALException {
        IUserDTO foundUser = searchUser(user.getUserId());
        if (foundUser != null) {
            return foundUser;
        } else {
            throw new DALException("User doesn't exist");
        }
    }

    @Override
    public String newScore(int id, int score) throws DALException {
        try {
            IUserDTO user = searchUser(id);
            user.addScore(score);
            return "Score added to the user";
        } catch (NullPointerException e) {
            throw new DALException("User doesn't exist - " + e.getMessage());
        }
    }

    @Override
    public String createUser(String username, String password) throws DALException {
        IUserDTO user = new UserDTO(userList.size()+1,username);
        user.setPassword(password);
        userList.add(user);
        return "User is created";
    }

    /**
     * Search through the ArrayList for User by its ID
     *
     * @param id    ID of User
     * @return      User object
     */
    private IUserDTO searchUser(int id) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUserId() == i) {
                return userList.get(i);
            }
        }
        return null;
    }
}