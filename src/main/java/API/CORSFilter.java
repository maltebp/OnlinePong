package API;


import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Created: 17-02-2018
 * Owner: Runi
 */
@Priority(500) //Before AuthorizationFilter (1000) to make sure that headers always get injected
@Provider
public class CORSFilter implements ContainerRequestFilter {
    @Context
    private HttpServletRequest request;
    @Context
    private HttpServletResponse response;

    public CORSFilter(){
        System.out.println("Cors filter");
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        setCORSHeaders();
    }


    private void setCORSHeaders() {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS, PATCH");
        String requestAllowHeader = request.getHeader("Access-Control-Request-Headers");
        response.setHeader("Access-Control-Allow-Headers",requestAllowHeader);
        response.setHeader("Access-Control-Allow-Credentials:", "true");
        response.setHeader("Access-Control-Expose-Headers","Authorization");
        response.setHeader("encoding", "utf-8");
    }

}