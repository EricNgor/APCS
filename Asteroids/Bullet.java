import processing.core.PApplet;
public class Bullet extends Floater {
    private PApplet applet;
    private long bulletTime;
    public Bullet(PApplet applet, SpaceShip ship, int angle) {
        super(applet);
        this.applet = applet;
        myColor = 255;
        setX(ship.getX());
        setY(ship.getY());
        setPointDirection((int)ship.getPointDirection());
        double dRadians = (myPointDirection * (Math.PI / 180)) + ((double)angle * 43.5);

        setDirectionX(5 * Math.cos(dRadians) + ship.getDirectionX());
        setDirectionY(5 * Math.sin(dRadians) + ship.getDirectionY());
    }

    @Override
    public void show() {
        applet.fill(myColor);
        applet.stroke(0, 255, 0);
        applet.ellipse(getX(), getY(), 5, 5);
    }

    public boolean lifeSpan() {
        if ((System.nanoTime() * Math.pow(10, -9)) - (bulletTime * Math.pow(10, -9)) > 1.5) {
            return true;
        }
        return false;
    }

    public long getLife() {
        return bulletTime - System.nanoTime();
    }

    //Set creation time
    public void bulletTime(long creation) {
        bulletTime = creation;
    }

    public void setX(int x) {
        myCenterX = (double)x;
    }

    public int getX() {
        return (int)myCenterX;
    }

    public void setY(int y) {
        myCenterY = (double)y;
    }

    public int getY() {
        return (int)myCenterY;
    }

    public void setDirectionX(double x) {
        myDirectionX = x;
    }

    public double getDirectionX() {
        return myDirectionX;
    }

    public void setDirectionY(double y) {
        myDirectionY = y;
    }

    public double getDirectionY() {
        return myDirectionY;
    }

    public void setPointDirection(int degrees) {
        myPointDirection = degrees;
    }

    public double getPointDirection() {
        return myPointDirection;
    }
}