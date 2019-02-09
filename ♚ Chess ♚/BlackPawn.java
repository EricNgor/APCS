import processing.core.PApplet;
import processing.core.PImage;
import java.util.Arrays;
public class BlackPawn {
    private PApplet applet;
    private PImage bPawnImg;
    private int[] bPawnX = new int[9];
    private int[] bPawnY = new int[9];

    public BlackPawn(PApplet applet_) {
        applet = applet_;
        for (int count = 1; count < 9; count++) {
            bPawnX[count] = count;
        }
        Arrays.fill(bPawnY, 2);

        bPawnImg = applet.loadImage("bPawn.jpg");
    }

    public void display() {
        for (int count = 1; count < 9; count++) {
            applet.image(bPawnImg, 71 + (getX(count) - 1) * 84, 76 + (getY(count) - 1) * 83);
        }
    }

    public void move(int pieceNum, int x, int y) {
        bPawnX[pieceNum] = x;
        bPawnY[pieceNum] = y;
    }
    
    public void die(int pieceNum) {
        bPawnX[pieceNum] = -1;
        bPawnY[pieceNum] = -1;
    }
    
    public int getX(int pieceNum) {
        return bPawnX[pieceNum];
    }

    public int getY(int pieceNum) {
        return bPawnY[pieceNum];
    }
}