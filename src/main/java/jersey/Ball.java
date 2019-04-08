package jersey;

public class Ball {
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getxVel() {
        return xVel;
    }

    public void setxVel(double xVel) {
        this.xVel = xVel;
    }

    public double getyVel() {
        return yVel;
    }

    public void setyVel(double yVel) {
        this.yVel = yVel;
    }

    private double x;
    private double y;
    private double xVel;
    private double yVel;

    public Ball(){

    }
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
