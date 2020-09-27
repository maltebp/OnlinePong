package gameserver.model;


/**
 * Objects identifying player connections to
 * in the Game server.Server
 */
public class Player {

    private String username;
    private int rating;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRating(int rating){
        this.rating = rating;
    }

    public int getRating(){
        return rating;
    }
}
