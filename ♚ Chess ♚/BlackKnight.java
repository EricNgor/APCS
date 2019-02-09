import processing.core.PApplet;
import processing.core.PImage;
import java.util.Arrays;
public class BlackKnight {
    private PApplet applet;
    private PImage bKnightImg;
    private int[] bKnightX = new int[3];
    private int[] bKnightY = new int[3];

    public BlackKnight(PApplet applet_) {
        applet = applet_;
        bKnightX[1] = 2;
        bKnightX[2] = 7;
        Arrays.fill(bKnightY, 1);

        bKnightImg = applet.loadImage("bKnight.jpg");
    }

    public void display() {
        for (int count = 1; count < 3; count++) {
            applet.image(bKnightImg, 71 + (getX(count) - 1) * 84, 76 + (getY(count) - 1) * 83);
        }
    }

    public int getX(int pieceNum) {
        return bKnightX[pieceNum];
    }

    public int getY(int pieceNum) {
        return bKnightY[pieceNum];
    }
}