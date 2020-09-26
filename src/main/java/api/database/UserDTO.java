package API.database;

public class UserDTO implements IUserDTO {
    String username;
    String password;
    int elo;

    public UserDTO(String username) {
        this.username = username;
    }
    public UserDTO(){}
    public UserDTO(String username, int elo) {this. username = username; this.elo = elo;}
    public UserDTO(String username, int elo, String password) {this. username = username; this.elo = elo; this.password = password;}

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
