import processing.core.PApplet;
import processing.core.PImage;
public class BlackKing {
    private PApplet applet;
    private PImage bKingImg;
    private int bKingX;
    private int bKingY;

    public BlackKing(PApplet applet_) {
        applet = applet_;
        bKingX = 4;
        bKingY = 1;
        bKingImg = applet.loadImage("bKing.jpg");
    }

    public void display() {
        applet.image(bKingImg, 71 + (getX() - 1) * 84, 76 + (getY() - 1) * 83);
    }

    public int getX() {
        return bKingX;
    }

    public int getY() {
        return bKingY;
    }
}