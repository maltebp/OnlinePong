package api.database;


import dev.morphia.annotations.Id;
import org.json.JSONObject;

public class User {

    @Id
    private String username;
    private String password = null;
    private int elo = 0;

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

    /**
     * Note: The JSONObject doesn't include the password
     */
    public JSONObject toJSONObject(){
        JSONObject json = new JSONObject();
        json.put("username", username);
        json.put("elo", elo);
        return json;
    }

    /**
     * Note: The JSONObject doesn't include the password
     */
    public static User fromJSONObject(JSONObject json, boolean requirePassword){
        User user = new User();
        user.username = json.getString("username");

        if( json.has("password") || requirePassword )
            user.password = json.getString("password");
        if( json.has("elo") )
            user.elo = json.getInt("elo");

        return user;
    }
}
