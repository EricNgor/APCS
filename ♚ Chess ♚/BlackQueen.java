import processing.core.PApplet;
import processing.core.PImage;
public class BlackQueen {
    private PApplet applet;
    private PImage bQueenImg;
    private int bQueenX;
    private int bQueenY;

    public BlackQueen(PApplet applet_) {
        applet = applet_;
        bQueenX = 5;
        bQueenY = 1;
        bQueenImg = applet.loadImage("bQueen.jpg");
    }

    public void display() {
        applet.image(bQueenImg, 71 + (getX() - 1) * 84, 76 + (getY() - 1) * 83);
    }

    public int getX() {
        return bQueenX;
    }

    public int getY() {
        return bQueenY;
    }
}