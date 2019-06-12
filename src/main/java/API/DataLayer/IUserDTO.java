package API.DataLayer;

import java.util.ArrayList;

public interface IUserDTO {
    /**@author Claes and Simon
     * this class is an object that carries data between processes.
     * it Encapsulates data while it is being transfered
     * between two subsystems(Databse and api in this case)
     * As is also stated in the name Data Transfer Object (DTO)
     */
    public int getUserId();

    public void setUserId(int userId);

    public String getUsername();

    public void setUsername(String username);

    public ArrayList<Integer> getScores();

    public void setScores(ArrayList<Integer> scores);

    public void addScore(int score);

    public void setPassword(String password);

    public String getPassword();
}
