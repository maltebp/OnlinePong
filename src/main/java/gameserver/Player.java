package gameserver;

import org.json.JSONObject;

import javax.websocket.Session;
import java.io.IOException;

public class Player {

    Session ses;
    String name;
    GameSuite suite = null;


    public Player(Session ses, String name){
        this.ses = ses;
        this.name = name;
    }


    public void sendMessage( JSONObject msg ){
        try{
            ses.getBasicRemote().sendText(msg.toString());
        }catch(IOException e){
            System.out.println("ERROR OCCURED:\n");
            e.printStackTrace();
        }
    }

    public Session getSession(){
        return this.ses;
    }

    public String getName(){
        return this.name;
    }

    public void tooString(){
        System.out.println("The ID of this session is: "+getSession().getId() + ", The name of the creator is: "+ getName() );
    }


    public void setSuite(GameSuite suite){
        this.suite = suite;
    }

    public GameSuite getSuite(){
        return suite;
    }

}
