import processing.core.PApplet;
import processing.core.PImage;
public class Terrain {
    private PApplet applet;
    private PImage wall;
    private int x; private int y;
    public Terrain(PApplet applet, int xLoc, int yLoc) {
        this.applet = applet;
        wall = applet.loadImage("Wall.png");
        x = xLoc; y = yLoc;
    }

    public void show() {
        applet.image(wall, x, y);
    }

    public void setX(int xLoc) {
        x = xLoc;
    }

    public int getX() {
        return x;
    }

    public void setY(int yLoc) {
        y = yLoc;
    }

    public int getY() {
        return y;   
    }
}



