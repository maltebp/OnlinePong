package API.database;

public interface IUserDTO {
    /**@author Claes and Simon
     * this class is an object that carries data between processes.
     * it Encapsulates data while it is being transfered
     * between two subsystems(Databse and api in this case)
     * As is also stated in the name Data Transfer Object (DTO)
     */

    public String getUsername();

    public void setUsername(String username);

    public void setPassword(String password);

    public String getPassword();

    public int getElo();

    public void setElo(int elo);
}
