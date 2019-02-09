import processing.core.PApplet;
public class Debris extends Floater {
    private PApplet applet;
    private long startTime;
    public Debris(PApplet applet, int xGrave, int yGrave) {
        super(applet);
        this.applet = applet;
        setX(xGrave + (int)(Math.random() * 4) - 2);
        setY(yGrave + (int)(Math.random() * 4) - 2);
        setDirectionX(randomPosNeg() * Math.random());
        setDirectionY(randomPosNeg() * Math.random());
        
    }

    @Override
    public void show() {
        applet.fill(255, 0, 0);
        applet.stroke(255, 128, 0);
        applet.ellipse(getX(), getY(), 2, 2);
    }
    
    public boolean despawn() {
        if (System.currentTimeMillis() - startTime > (int)(Math.random() * 1000) + 1000) {
            return true;
        }
        return false;
    }
    
    public void setStartTime(long time) {
        startTime = time;
    }
    
    public double randomPosNeg() {
        if ((int)(Math.random() * 2) == 0) {
            return 1.0;
        }
        return -1.0;
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