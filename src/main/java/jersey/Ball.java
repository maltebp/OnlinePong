package jersey;

public class Ball {
    double x;
    double y;
    double xVel;
    double yVel;

    public Ball(double x, double y, double xVel, double yVel){
        this.x = x;
        this.y = y;
        this.xVel = xVel;
        this.yVel = yVel;
    }

    public String toString(){
        return "{ \"x\":\""+x+", \"y\":\""+y+"\"}";
    }
}
