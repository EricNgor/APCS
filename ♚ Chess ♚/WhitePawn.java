import processing.core.PApplet;
import processing.core.PImage;
import java.util.Arrays;
public class WhitePawn {
    private PApplet applet;
    private PImage wPawnImg;
    private int[] wPawnX = new int[9];
    private int[] wPawnY = new int[9];

    public WhitePawn(PApplet applet_) {
        applet = applet_;
        for (int count = 1; count < 9; count++) {
            wPawnX[count] = count;
        }
        Arrays.fill(wPawnY, 7);

        wPawnImg = applet.loadImage("wPawn.jpg");
    }

    public void display() {
        for (int count = 1; count < 9; count++) {
            applet.image(wPawnImg, 71 + (getX(count) - 1) * 84, 76 + (getY(count) - 1) * 83);
        }
    }

    public void move(int pieceNum, int x, int y) {
        wPawnX[pieceNum] = x;
        wPawnY[pieceNum] = y;
    }
    
    public void die(int pieceNum) {
        wPawnX[pieceNum] = -1;
        wPawnY[pieceNum] = -1;
    }
    
    public int getX(int pieceNum) {
        return wPawnX[pieceNum];
    }

    public int getY(int pieceNum) {
        return wPawnY[pieceNum];
    }
}