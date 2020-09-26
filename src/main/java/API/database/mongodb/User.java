package API.database.mongodb;


import API.database.UserDTO;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;


/**
 *  Class to wrap a user information, so we can
 *  easily insert it into the MongoDB without
 *  the original UserDTO becoming cluttered with
 *  information (annotations), that is only relevant
 *  to the MongoDB (Morphia)
 */
@Entity("users")
public class User {

    @Id
    private String username;
    private String password;
    private int elo;

    // Default constructor required by mongodb
    public User(){}

    public User(String username, int elo) {
        this.username = username;
        this.elo = elo;
    }

    public User(String username, String password, int elo) {
        this.username = username;
        this.password = password;
        this.elo = elo;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public UserDTO toUserDTO(){
        return new UserDTO(username, elo, password);
    }

    public static User fromUserDTO(UserDTO userDTO){
        return new User(userDTO.getUsername(), userDTO.getPassword(), userDTO.getElo());
    }
}
