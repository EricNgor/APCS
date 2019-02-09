import processing.core.PApplet;
import processing.core.PImage;
import java.util.Arrays;
public class WhiteBishop {
    private PApplet applet;
    private PImage wBishopImg;
    private int[] wBishopX = new int[3];
    private int[] wBishopY = new int[3];

    public WhiteBishop(PApplet applet_) {
        applet = applet_;
        wBishopX[1] = 3;
        wBishopX[2] = 6;
        Arrays.fill(wBishopY, 8);

        wBishopImg = applet.loadImage("wBishop.jpg");
    }

    public void display() {
        for (int count = 1; count < 3; count++) {
            applet.image(wBishopImg, 71 + (getX(count) - 1) * 84, 76 + (getY(count) - 1) * 83);
        }
    }

    public int getX(int pieceNum) {
        return wBishopX[pieceNum];
    }

    public int getY(int pieceNum) {
        return wBishopY[pieceNum];
    }
}