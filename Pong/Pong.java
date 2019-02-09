import processing.core.PApplet;
import processing.core.PImage;
public class Pong extends PApplet {
    private Ball ball;
    private Paddle paddle;
    private Paddle paddle2;
    private PImage winImg;
    private boolean game;
    private int score1;
    private int score2;
    private boolean delay;
    private long delayTime;
    private boolean winner;
    private int gameMode;
    public static void main(String[] args) {
        PApplet.main(new String[] {"Pong"});
    }

    public void settings() {
        size(1250, 900); // if processing 3
    }

    public void setup() {
        background(125);
        winImg = loadImage("winner.jpg");
        size(1250, 900); // if processing 2
        paddle = new Paddle(this, 1);
        ball = new Ball(this, paddle);
        game = false;
        delay = true;
        winner = false;
    }

    public void draw() {
        background(0);
        
        if (!game && !winner) { // MENU
            menu();
        }
        else if (winner) { // Winner!
            dispWinner();
            return;
        }

        if (game) { // GAME START
            if (delay) { 
                ball.delayDisplay(delayTime); // delays ball start
                if (ball.delayDisplay(delayTime)) { // when delay is over
                    delay = false;
                }
            }
            else if (!delay) {
                ball.checkCollision();
                ball.move();
                ball.display();
            }

            //Paddle display and move
            paddle.move(gameMode);
            paddle.display();

            //Middle Dashes
            dashes();

            // Adds points on wins
            points();

            //Scores
            dispScores();
        }
    }

    public void menu() {
        textSize(128);
        text("PONG", (width / 2) - 180, 256);
        textSize(32);
        String watermark = "Eric Ngor";
        text(watermark, width - 160, height - 32);
        if (mouseY > height - 64 && mouseY < height - 2 && mouseX > width - 160 && mouseX < width - 2) {
            textSize(24);
            text("hi", 600, 700);
        }
        fill(255);

        textSize(64);
        if (mouseY > 316 && mouseY < 388 && mouseX > 441 && mouseX < 660) { // Normal button
            textSize(74);
            fill(125);
            if (mousePressed) { // Starts game
                game = true;
                delayTime = System.nanoTime();
                delay = true;
                gameMode = 1;
            }
        }
        text("Normal", (width / 2) - 180, 384);
        fill(255);

        textSize(64);
        if (mouseY > 392 && mouseY < 464 && mouseX > 441 && mouseX < 850) { // Acceleration Button
            textSize(74);
            fill(255, 255, 0);
            if (mousePressed) {
                game = true;
                delayTime = System.nanoTime();
                delay = true;
                gameMode = 2;
            }
        }
        text("Acceleration", (width / 2) - 180, 460);
        fill(255);

        textSize(64);
        if (mouseY > 468 && mouseY < 544 && mouseX > 441 && mouseX < 700) { // ARCADE button
            textSize(30);
            textSize(74);
            fill((float)Math.random() * 255, (float)Math.random() * 255, (float)Math.random() * 255);

            if (mousePressed) {
                game = true;
                delayTime = System.nanoTime();
                delay = true;
                gameMode = 3;
            }
        }
        text("ARCADE", (width / 2) - 180, 540);
        fill(255);
        ball.setGameMode(gameMode);
    }

    public void dispWinner() {
        //background(winImg);
        if (gameMode == 1 || gameMode == 2) {
            textSize(128);
            fill(200, 200, 0);
            String winner;
            if (score1 > score2) {
                winner = "Player 1";
            }
            else {
                winner = "Player 2";
            }
            text(winner + " wins!", width / 2 - 425, height / 2);
        }
        // Arcade mode ends 
        else if (gameMode == 3) {
            textSize(128);
            fill((float)Math.random() * 255, (float)Math.random() * 255, (float)Math.random() * 255);
            text("You scored " + Integer.toString(ball.getPoints()) + " points!", width / 2 - 350, height / 3 - 50, 700, 400);
        }
    }

    public void dashes() {
        for (int count = 0; count < height; count += 30) {
            rect(width / 2 - 3, count, 8, 20);
        }
    }

    public void points() {
        if (ball.checkWin() == 1) {
            score1++;
            delayTime = System.nanoTime();
            delay = true;
            ball.resetWin();
        }

        else if (ball.checkWin() == 2) {
            score2++;
            delayTime = System.nanoTime();
            delay = true;
            ball.resetWin();
        }
        else if (ball.checkWin() == -1) { // Arcade life loss
            delayTime = System.nanoTime();
            delay = true;
            ball.resetWin();
        }
        if (score1 >= 7 || score2 >= 7 || ball.checkWin() == 3) { // When someone wins OR if arcade mode ends
            winner = true;
        }   
    }

    public void dispScores() {
        textSize(48);
        if (gameMode == 1 || gameMode == 2) {
            text(Integer.toString(score1), 298, 48);
            text(Integer.toString(score2), 921, 48);
        }
        else if (gameMode == 3) {
            text("Lives: " + Integer.toString(ball.getArcadeLives()), 250, 48);
            text(Integer.toString(ball.getPoints()), 921, 48);
        }
    }
}
