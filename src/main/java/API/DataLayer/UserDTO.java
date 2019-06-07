package API.DataLayer;

import java.util.ArrayList;

public class UserDTO implements IUserDTO {
    private int userId;
    String username;
    String password;
    ArrayList<Integer> scores = new ArrayList<>();

    public UserDTO(int userId, String username) {
        this.userId = userId;
        this.username = username;
    }
    public UserDTO(){}

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<Integer> getScores() {
        return scores;
    }

    public void setScores(ArrayList<Integer> scores) {
        this.scores = scores;
    }

    public void addScore(int score){
        scores.add(score);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
