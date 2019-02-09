import processing.core.PApplet;
import processing.core.PImage;

public class Projectile
{  
    protected PApplet applet;
    private PImage bossProjectile;
    private PImage bossProjectileMask;
    private int x;
    private int y;
    private int bossX;
    private int bossY;
    private int xSpeed;
    private int ySpeed;
    private int currTime;

    public Projectile(PApplet applet_, int bossX_, int bossY_)
    {
        applet = applet_;
        x = bossX_;
        y = bossY_;
        bossX = bossX_;
        bossY = bossY_;
        currTime = applet.millis();
    }

    public void setX(int newX) {
        x = newX;
    }

    public int getX() {
        return x;
    }   

    public void setY(int newY) {
        y = newY;
    }   

    public int getY() {
        return y;
    }  

    public void move(int playerX, int playerY) {
        if (applet.millis() - currTime < 4000) {
            if (playerX > x) {
                xSpeed = 4;
                x += xSpeed;
            }
            if (playerY > y) {
                ySpeed = 4;
                y += ySpeed;
            }
            if (playerX < x) {
                xSpeed = -4;
                x += xSpeed;
            }
            if (playerY < y) {
                ySpeed = -4;
                y += ySpeed;
            }
        }
        else {
            x += xSpeed;
            y += ySpeed;
        }

    }  

    public void show() {
        bossProjectile = applet.loadImage("TorchFire.png");
        bossProjectileMask = applet.loadImage("TorchFireMask.png");
        bossProjectile.mask(bossProjectileMask);
        applet.image(bossProjectile, x, y);
    }
} 

