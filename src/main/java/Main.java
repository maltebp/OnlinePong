import java.io.File;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;


/**
 * Initializes the Tomcat Server on the localhost on
 * port 8080.
 *
 * The applications following applications are hosted on
 * this server:
 *  - Website
 *  - REST API
 *  - Game Server (websocket)
 */
public class Main {

    public static void main(String[] args) throws LifecycleException  {
        Tomcat tomcat;

        tomcat = new Tomcat();
        tomcat.setBaseDir("temp");
        tomcat.setPort(8080);
        tomcat.getConnector();

        tomcat.addWebapp("",new File("src/main/webapp").getAbsolutePath());

        tomcat.start();

        tomcat.getServer().await();
    }
}
