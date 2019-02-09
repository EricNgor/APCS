import processing.core.PApplet;
import processing.core.PImage;
public class WhiteKing {
    private PApplet applet;
    private PImage wKingImg;
    private int wKingX;
    private int wKingY;

    public WhiteKing(PApplet applet_) {
        applet = applet_;
        wKingX = 4;
        wKingY = 8;
        wKingImg = applet.loadImage("wKing.jpg");
    }

    public void display() {
        applet.image(wKingImg, 71 + (getX() - 1) * 84, 76 + (getY() - 1) * 83);
    }

    public int getX() {
        return wKingX;
    }

    public int getY() {
        return wKingY;
    }
}