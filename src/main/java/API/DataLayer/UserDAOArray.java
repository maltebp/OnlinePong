package API.DataLayer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAOArray implements IUserDAO {

    ArrayList<IUserDTO> userList;

    @Override
    public Connection createConnection() throws SQLException {

        for(int i = 0; i<10; i++){
            userList.add(new UserDTO(i, "Test"+i));
        }

        return null;
    }

    @Override
    public IUserDTO getDBUser(int id) throws DALException {
       IUserDTO user = searchUser(id);
           if(user!=null){

               return user;

           }else{
                System.out.println("User does not Exist");

        }
        return null;
    }

    @Override
    public IUserDTO makeUser(ResultSet set) throws SQLException {
        return null;
    }


    @Override
    public IUserDTO getDBScore(IUserDTO user) throws SQLException {
        return user;


    }

    @Override
    public String newScore(int id, int score) throws SQLException {
        IUserDTO user = searchUser(id);
        user.addScore(score);

        return "Score added to the user";
    }


    @Override
    public boolean validateUserByName(String username) throws SQLException {
        return false;
    }

    @Override
    public String createUser(String username, String password) throws SQLException {
        IUserDTO user = new UserDTO(userList.size()+1,username);
        user.setPassword(password);
        userList.add(user);

        return "User is created";
    }

    private IUserDTO searchUser(int id) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUserId() == i) {
                return userList.get(i);
            }
        }
        return null;
    }
}
