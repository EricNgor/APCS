import processing.core.PApplet;
import processing.core.PImage;
import java.util.Arrays;
public class WhiteKnight {
    private PApplet applet;
    private PImage wKnightImg;
    private int[] wKnightX = new int[3];
    private int[] wKnightY = new int[3];

    public WhiteKnight(PApplet applet_) {
        applet = applet_;
        wKnightX[1] = 2;
        wKnightX[2] = 7;
        Arrays.fill(wKnightY, 8);

        wKnightImg = applet.loadImage("wKnight.jpg");
    }

    public void display() {
        for (int count = 1; count < 3; count++) {
            applet.image(wKnightImg, 71 + (getX(count) - 1) * 84, 76 + (getY(count) - 1) * 83);
        }
    }

    public int getX(int pieceNum) {
        return wKnightX[pieceNum];
    }

    public int getY(int pieceNum) {
        return wKnightY[pieceNum];
    }
}