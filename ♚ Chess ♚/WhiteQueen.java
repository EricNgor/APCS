import processing.core.PApplet;
import processing.core.PImage;
public class WhiteQueen {
    private PApplet applet;
    private PImage wQueenImg;
    private int wQueenX;
    private int wQueenY;

    public WhiteQueen(PApplet applet_) {
        applet = applet_;
        wQueenX = 5;
        wQueenY = 8;
        wQueenImg = applet.loadImage("wQueen.jpg");
    }

    public void display() {
        applet.image(wQueenImg, 71 + (getX() - 1) * 84, 76 + (getY() - 1) * 83);
    }

    public int getX() {
        return wQueenX;
    }

    public int getY() {
        return wQueenY;
    }
}