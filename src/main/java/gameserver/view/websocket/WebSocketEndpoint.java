package gameserver.view.websocket;


import io.javalin.Javalin;

public class WebSocketEndpoint {

    private static final String URL = "/gameserver";
    private final WebSocketController controller = new WebSocketController();

    public WebSocketEndpoint(Javalin server){
        server.ws("/gameserver", ws -> {
            ws.onConnect((ctx) -> controller.openSession(ctx.session) );
            ws.onMessage((ctx -> controller.messageRecieved(ctx.session, ctx.message())));
            ws.onClose((ctx) -> controller.closeSession(ctx.session));
            ws.onError(ctx -> System.out.println("Some error occured: "+ctx.error()));
        });
    }

}
