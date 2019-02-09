import processing.core.PApplet;
import processing.core.PImage;
import java.util.Arrays;
public class WhiteRook {
    private PApplet applet;
    private PImage wRookImg;
    private int[] wRookX = new int[3];
    private int[] wRookY = new int[3];

    public WhiteRook(PApplet applet_) {
        applet = applet_;
        wRookX[1] = 1;
        wRookX[2] = 8;
        Arrays.fill(wRookY, 8);

        wRookImg = applet.loadImage("wRook.jpg");
    }

    public void display() {
        for (int count = 1; count < 3; count++) {
            applet.image(wRookImg, 71 + (getX(count) - 1) * 84, 76 + (getY(count) - 1) * 83);
        }
    }

    public int getX(int pieceNum) {
        return wRookX[pieceNum];
    }

    public int getY(int pieceNum) {
        return wRookY[pieceNum];
    }
}