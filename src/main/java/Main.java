import java.io.File;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

public class Main {

private static Tomcat tomcat;

    public static void main(String[] args) throws LifecycleException  {


        tomcat = new Tomcat();
        tomcat.setBaseDir("temp");
        tomcat.setPort(8080);
        tomcat.getConnector();

        tomcat.addWebapp("",new File("src/main/webapp").getAbsolutePath());

        tomcat.start();

        tomcat.getServer().await();
    }
}
