package API.DataLayer;

import java.util.ArrayList;

public class UserDTO implements IUserDTO {
    String username;
    String password;
    int elo;

    public UserDTO(String username) {
        this.username = username;
    }
    public UserDTO(){}

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
}
