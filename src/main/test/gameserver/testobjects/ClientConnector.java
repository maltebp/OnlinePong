package gameserver.testobjects;

import org.json.JSONObject;


/**
 * Implementing web socket client side functionality
 * for testing purposes.
 */
public class ClientConnector {

    private String username;
    private String password;

    public ClientState state = ClientState.IDLE;


    private ServerConnector connection;

    public ClientConnector(String username, String password, int rating, ServerConnector connection){
        this.username = username;
        this.password = password;
        this.connection = connection;
        connection.createConnection(this, rating);

    }

    public void recieveMessage(String msg){
        System.out.println(username+": "+msg);

        JSONObject json = new JSONObject(msg);
        switch(json.getInt("code")){
            case 101:
                state = ClientState.WAITING_FOR_GAME;
                break;
            case 102:
                state = ClientState.MATCHED;
                sendMessage("{ \"code\" : 002 }");
                break;
            case 103:
                state = ClientState.IN_GAME;
                break;
            case 104:
                state = (json.getBoolean("hasWon")) ? ClientState.WON_GAME : ClientState.LOST_GAME;
                break;
            case 210:
                state = ClientState.OPPONENT_DISC;
                break;
        }
    }

    public void closeConnection(){
        connection.closeConnection(this);
    }

    public void sendMessage(String msg){
        connection.recieveMessage(this, msg);
    }

    public void findGame(){
        sendMessage("{   \"code\"  :   001," +
                "   \"username\" : "+username+"," +
                "   \"password\"   : "+password +
                "}\n");
    }


    public ClientState getState() {
        return state;
    }
}


