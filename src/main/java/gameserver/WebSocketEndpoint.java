package gameserver;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/gameserver")
public class WebSocketEndpoint {

    private static WebSocketController controller = new WebSocketController();

    @OnOpen
    public void onOpen(Session session){
        controller.addSession(session);
    }

    @OnMessage
    public void onMessage( Session session, String msg ){
        controller.sessionMessage(session, msg);
    }

    @OnClose
    public void onClose( Session session ){
        controller.sessionClosed(session);
    }

    @OnError
    public void onError(Throwable t){
        System.out.println("Some error occured: "+t.getMessage());
    }



}
