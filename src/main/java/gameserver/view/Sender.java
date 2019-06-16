package gameserver.view;

import gameserver.model.Player;
import org.json.JSONObject;

import javax.websocket.Session;


/**
 * Defines methods for sending messages to a Player. Is to be implemented
 * such that it fits the Player connection type (i.e. WebSocket).
 *
 * Implementation:
 * Implement sendMessage() method, such that it sends a String message to
 * connection specified by the Player object.
 *
 * Other Methods:
 * The non-abstract methods creates String messages such that they fit
 * the message system's JSON message format
 */
public abstract class Sender {


    /** Sends message to the Player via a chosen connection
     *  technology (i.e. WebSocket).
     * @param player The player uniquely defining the connection
     * @param message Message which the game client is to recieve
     */
    public abstract void sendMessage(Player player, String message);


    /**
     *  CODE: 101
     *  Informs client that server is finding a match for the
     *  player.
     *  Implicit signals that user was authenticated
     *
     * @param timeEstimate  Estimated minutes for finding game
     */
    public void sendFindingGame(Player player, int timeEstimate){
        JSONObject msg = getCodeMsg(101);
        msg.put("timeEstimate", timeEstimate);
        sendMessage(player, msg.toString());
    }


    /**
     * CODE: 102
     * Informs client that a game has been found
     */
    public void sendFoundGame( Player player, Player opponent ){
        JSONObject msg = getCodeMsg(102);
        msg.put("username",opponent.getUsername());
        msg.put("rating",opponent.getRating());
        sendMessage(player, msg.toString());
    }


    /**
     * CODE: 103
     * Inform client that game is to be started
     *
     * @param initUpdate If the client is to start data transmission
     */
    public void sendStartGame( Player player, boolean initUpdate ){
        JSONObject msg = getCodeMsg(103);
        msg.put( "initUpdate", initUpdate );
        sendMessage(player, msg.toString());
    }


    // ERROR MESSAGES ---------------------------------------------------

    /**
     * CODE: 201
     * Informs user that the given username and/or password
     * wasn't registered correctly.
     * @param player
     */
    public void sendWrongUsernamePassword(Player player){
        sendMessage(player, getCodeMsg(201).toString());
    }


    /**
     * CODE: 202
     * Informs client that player is already logged in,
     * denying player to log in again.
     */
    public void sendAlreadyLoggedIn(Player player){
        sendMessage(player, getCodeMsg(202).toString());
    }


    /**
     * CODE: 204
     * Informs client that the message format of a recieved
     * message was not correct.
     * Uses sendWrongMessageFormat(player, optionalMessage}
     */
    public void sendWrongMessageFormat(Player player){
        sendWrongMessageFormat(player, "");
    }


    /**
     * CODE: 204
     * Informs client that the message format of a recieved
     * message was not correct.
     *
     * @param optionalMessage A message to append to the JSON objecct being sent:
     *                         { "code" : CODE, "msg" : optionalMessage }
     */
    public void sendWrongMessageFormat(Player player, String optionalMessage){
        JSONObject msg = getCodeMsg(204);
        if(optionalMessage != null && optionalMessage.equals("") ){
            msg.put("msg", optionalMessage);
        }
        sendMessage(player, msg.toString());
    }


    /**
     * CODE: 205
     * Informs client that request given in a message was denied
     * because Player hasn't been authenticated as a result of a
     * 001 message.
     */
    public void sendNotAuthenticated(Player player) {
        sendMessage(player, getCodeMsg(205).toString());
    }


    /**
     * CODE: 210
     * Informs client that Player's opponent disconnected
     * during the match.
     * Match will be stopped and connection to client closed.
     */
    public void sendOpponentDisconnected(Player player){
        sendMessage(player, getCodeMsg(210).toString());
    }


    /**
     * Creates an initial JSON object containing a "code" field:
     *  { "code" : CODE }
     */
    private JSONObject getCodeMsg(int code){
        JSONObject msg = new JSONObject();
        msg.put("code", code);
        return msg;
    }

}
