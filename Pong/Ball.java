import processing.core.PApplet;
public class Ball {
    private PApplet applet;
    private Paddle paddle;
    private int ballDiameter;
    private float x;
    private float y;
    private int speedX;
    private int speedY;
    private int gameMode;
    private int win; // player that wins
    private int points; // Arcade points
    private int lives;
    private boolean arcadeDeath;
    private float r, g, b;
    public Ball(PApplet applet_, Paddle paddle_) {
        applet = applet_;
        paddle = paddle_;
        ballDiameter = 30;
        win = 0;
        points = 0;
        lives = 3; // Arcade lives
        r = (float)Math.random() * 255;
        g = (float)Math.random() * 255;
        b = (float)Math.random() * 255;
    }

    public boolean delayDisplay(long delayTime) {
        if (gameMode == 1|| gameMode == 2) {
            x = applet.width / 2;
                }
        else if (gameMode == 3) {
            x = paddle.getPaddleLength() + (ballDiameter / 2);
        }
        y = applet.height / 2;
        applet.ellipse(x, y, ballDiameter, ballDiameter);
        long time = System.nanoTime();
        if ((time - delayTime) * (Math.pow(10, -9)) > 2) {
            return true;
        }

        // Reset speed
        if ((int)(Math.random() * 2) + 1 == 1) { 
            speedX = 8;
        }
        else { 
            speedX = -8;
        }
        if ((int)(Math.random() * 2) + 1 == 1) {
            speedY = 8;
        }
        else {
            speedY = -8;
        }

        if (gameMode == 3) {
            speedX = 20;
            if ((int)(Math.random() * 2) + 1 == 1) {
                speedY = 20;
            }
            else {
                speedY = -20;  
            }
        }
        return false;
    }

    public void display() {
        applet.ellipse(x, y, ballDiameter, ballDiameter);

        //ARCADE LIGHTS
        if (gameMode == 3) {
            applet.fill(r, g, b);
            applet.stroke(255);
            applet.strokeWeight(3);
        }
    }

    public void move() {
        x += speedX;
        y += speedY;
    }

    public void checkCollision() {
        //Top and Bottom
        if (y + (ballDiameter / 2) >= applet.height || y - (ballDiameter / 2) <= 0) {
            speedY *= -1;
        }

        //Left Paddle Hit
        if (x - (ballDiameter / 2) <= paddle.getPaddleLength() && y >= paddle.getY() && y <= paddle.getY() + paddle.getPaddleHeight()) { 
            //Spaghetti Code
            if (y < paddle.getY() + 12 && y > paddle.getY() + paddle.getPaddleHeight() - 12) {
                if (y < paddle.getY() + 12) {
                    y = paddle.getY() + (ballDiameter / 2);
                }
                if (y > paddle.getY() + paddle.getPaddleHeight() - 12) {
                    y = paddle.getY() + paddle.getPaddleHeight() - (ballDiameter / 2);
                }

                speedY *= -1;
            }

            if (speedX == 8 || speedX == -8) { // speeds up X to 12 after first hit
                speedX *= 1.5;
                speedY *= 1.5;
            }
            speedX *= -1;
            x = paddle.getPaddleLength() + (ballDiameter / 2);

            //Acceleration
            if (gameMode == 2) {
                speedX *= 1.1;
            }

            if (gameMode == 3) {
                points++;
                r = (float)Math.random() * 255;
                g = (float)Math.random() * 255;
                b = (float)Math.random() * 255;
            }
        }
        // Top/Bottom of paddle 
        else if (x <= paddle.getPaddleLength() && (y + (ballDiameter / 2) >= paddle.getY() && y - (ballDiameter / 2) <= paddle.getY() + paddle.getPaddleHeight())) {
            speedY *= -1;
            speedX *= -1;
        }

        //Right Paddle Hit
        if (x + (ballDiameter / 2) >= applet.width - paddle.getPaddleLength() && y + (ballDiameter / 2) >= paddle.getY2() && y - (ballDiameter / 2) <= paddle.getY2() + paddle.getPaddleHeight()) {
            if (y < paddle.getY2() + 2 && y > paddle.getY2() + paddle.getPaddleHeight() - 2) {
                if (y < paddle.getY2() + 12) {
                    y = paddle.getY2();
                }
                if (y > paddle.getY2() + paddle.getPaddleHeight() - 12) {
                    y = paddle.getY2() + paddle.getPaddleHeight();
                }
                speedY *= -1;
            }

            if (speedX == 8 || speedX == -8) { // speeds up X to 12 after first hit
                speedX *= 1.5;
                speedY *= 1.5;
            }
            speedX *= -1;
            x = applet.width - paddle.getPaddleLength() - (ballDiameter / 2);

            //Acceleration
            if (gameMode == 2) {
                speedX *= 1.1;
            }

            //Arcade score increase
            if (gameMode == 3) {
                points++;
                r = (float)Math.random() * 255;
                g = (float)Math.random() * 255;
                b = (float)Math.random() * 255;
            }
        }
        // Top/Bottom Right paddle (MISS)
        else if (x >= applet.width - paddle.getPaddleLength() && y + (ballDiameter / 2) >= paddle.getY2() && y - (ballDiameter / 2) <= paddle.getY2() + paddle.getPaddleHeight()) {
            speedY *= -1;
            speedX *= -1;
        }   
        //  Borders for testing
        //         if (x + (ballDiameter / 2) >= applet.width) { // Right Border
        //             speedX *= -1;
        //         }
        //         if (x - (ballDiameter / 2) <= 0) { // Left Border
        //             speedX *= -1;
        //         }

    }

    public int checkWin() {
        if (gameMode == 1 || gameMode == 2) {
            if (x < 0) { // goes through left; Player 2 Wins
                win = 2;
                x = applet.width / 2;
                y = applet.height / 2;
            }
            else if (x > applet.width) { // goes through right; Player 1 Wins
                win = 1;
                x = applet.width / 2;
                y = applet.height / 2;
            }
        }
        else if (gameMode == 3) {
            if (x < 0 || x > applet.width) {
                lives--;
                x = paddle.getPaddleLength() + (ballDiameter / 2);
                y = applet.height / 2;
                win = -1;
                if (lives == 0) {
                    win = 3; // Ends arcade game
                }
            }
        }
        return win;
    }

    public int getPoints() {
        return points;
    }

    public void resetWin() {
        win = 0;
    }

    public int getArcadeLives() {
        return lives;
    }

    public void setGameMode(int gameMode_) {
        gameMode = gameMode_;
    }

    public boolean getArcadeDeath() {
        return arcadeDeath;
    }
}
