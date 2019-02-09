import processing.core.PApplet;
public class Paddle {
    private PApplet applet;
    private float y;
    private float y2;
    private float velY;
    private float velY2;
    private int paddleLength;
    private int paddleHeight;
    public Paddle(PApplet applet_, int paddleNum) {
        applet = applet_;
        paddleLength = 25;
        paddleHeight = 150;
        velY = 9;
        velY2 = 9;
        y = (applet.height / 2) - (paddleHeight / 2);
        y2 = y;
    }

    public void display() {
        applet.rect(0, y, paddleLength, paddleHeight); // left paddle
        applet.rect(applet.width - paddleLength, y2, paddleLength, paddleHeight); // Right Paddle
    }

    public void move(int gameMode) {
        // Left Paddle
        if (applet.keyPressed) {
            if (applet.key == 'w') { // left paddle up
                y -= velY;
            }
            else if (applet.key == 's') { // left paddle down
                y += velY;
            }
        }

        // Top and bottom boundaries [Left]
        if (y < 0) {
            y = 0;
        }
        else if (y + paddleHeight > applet.height) {
            y = applet.height - paddleHeight;
        }

        // Right Paddle (normal & acceleration)
        if (gameMode == 1 || gameMode == 2) {
            if (applet.mouseY < y2 + (paddleHeight / 2) - 9) { // right paddle up
                velY2 = 9;
                y2 -= velY2;
            }
            else if (applet.mouseY > y2 + (paddleHeight / 2) + 9) { // right paddle down
                velY2 = 9;
                y2 += velY2;
            }
            else if (applet.mouseY == y2 + (paddleHeight / 2)) {
                velY2 = 0;
            }
        }

        // Top and boundaries [Right]
        if (y2 < 0) {
            y2 = 0;
        }
        else if (y2 + paddleHeight > applet.height) {
            y2 = applet.height - paddleHeight;
        }

        if (gameMode == 3) {
            y2 = y;
            velY = 16;
            velY2 = velY;
        }
    }

    public int getPaddleLength() {
        return paddleLength;
    }

    public int getPaddleHeight() {
        return paddleHeight;
    }

    public float getY() {
        return y;
    }

    public float getY2() {
        return y2;
    }
}
