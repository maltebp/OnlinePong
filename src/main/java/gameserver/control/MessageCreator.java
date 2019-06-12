package gameserver.control;

import org.json.JSONObject;

public class MessageCreator {

    private JSONObject getCodeMsg(int code){
        JSONObject msg = new JSONObject();
        msg.put("code", code);
        return msg;
    }

    public String startGame( boolean initUpdate ){
        JSONObject msg = getCodeMsg(103);

        msg.put( "initUpdate", (initUpdate) ? 1 : 0 );

        return msg.toString();
    }

    public String foundGame( ){
        JSONObject msg = getCodeMsg(102);
        return msg.toString();
    }

    public String findingGame(int timeEstimate){
        JSONObject msg = getCodeMsg(101);
        msg.put("timeEstimate", timeEstimate);
        return msg.toString();
    }


    public String wrongMessageFormat(){
        return wrongMessageFormat("");
    }

    public String wrongMessageFormat(String optionalMessage){
        JSONObject msg = getCodeMsg(204);
        if(optionalMessage != null && optionalMessage.equals("") ){
            msg.put("msg", optionalMessage);
        }
        return msg.toString();
    }

    public String alreadyLoggedIn(){
        JSONObject msg = getCodeMsg(202);
        return msg.toString();
    }

    public String wrongUsernamePassword(){
        return getCodeMsg(201).toString();
    }


    public String opponentDisconnected(){
        JSONObject msg = getCodeMsg(210);
        return msg.toString();
    }

    public String notAuthenticated() {
        JSONObject msg = getCodeMsg(205);
        return msg.toString();
    }
}
