import processing.core.PApplet;
import processing.core.PImage;
import java.util.Arrays;
public class BlackRook {
    private PApplet applet;
    private PImage bRookImg;
    private int[] bRookX = new int[3];
    private int[] bRookY = new int[3];

    public BlackRook(PApplet applet_) {
        applet = applet_;
        bRookX[1] = 1;
        bRookX[2] = 8;
        Arrays.fill(bRookY, 1);

        bRookImg = applet.loadImage("bRook.jpg");
    }

    public void display() {
        for (int count = 1; count < 3; count++) {
            applet.image(bRookImg, 71 + (getX(count) - 1) * 84, 76 + (getY(count) - 1) * 83);
        }
    }

    public int getX(int pieceNum) {
        return bRookX[pieceNum];
    }

    public int getY(int pieceNum) {
        return bRookY[pieceNum];
    }
    
    
}