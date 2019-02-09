import processing.core.PApplet;
import processing.core.PImage;
import java.util.Arrays;
public class BlackBishop {
    private PApplet applet;
    private PImage bBishopImg;
    private int[] bBishopX = new int[3];
    private int[] bBishopY = new int[3];

    public BlackBishop(PApplet applet_) {
        applet = applet_;
        bBishopX[1] = 3;
        bBishopX[2] = 6;
        Arrays.fill(bBishopY, 1);

        bBishopImg = applet.loadImage("bBishop.jpg");
    }

    public void display() {
        for (int count = 1; count < 3; count++) {
            applet.image(bBishopImg, 71 + (getX(count) - 1) * 84, 76 + (getY(count) - 1) * 83);
        }
    }

    public int getX(int pieceNum) {
        return bBishopX[pieceNum];
    }

    public int getY(int pieceNum) {
        return bBishopY[pieceNum];
    }
}