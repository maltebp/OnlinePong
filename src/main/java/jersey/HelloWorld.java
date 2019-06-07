package jersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


@Path("/hello")
public class HelloWorld {

    @GET
    @Produces("text/html")


    public String getMessage() {
        return "Hello world!";
    }

    
}