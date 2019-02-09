import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.*;
import java.io.*;
//http://www.classicgaming.cc/classics/asteroids/sounds
public class AsteroidsGame extends PApplet
{
    private boolean gameOver;
    private int score;

    private SpaceShip ship;
    private boolean rotateLeft; private boolean rotateRight; private boolean accelerate;
    private boolean hyperspace;
    private boolean dead;

    private GhostShip fire;
    private long flicker;

    private GhostShip afterimage;
    private int opacityI;

    private List<GhostShip> lives;

    private UFO ufo;

    private Star[] stars;

    private List<Asteroids> asteroids;
    private boolean resetting;
    private long resetTime;

    private List<Debris> debris;

    private List<Bullet> bullets;
    private int bulletLimit;

    private List<PowerUp> powerups;
    private int displayIndexCounter;
    private boolean triShot;

    private File bulletFireFile;
    private Clip bulletFire;
    private File thrustFile;
    private Clip thrust;
    private File explosionFile;
    private Clip explosion;
    public static void main(String[] args)
    {
        PApplet.main(new String[] {"AsteroidsGame"});
    }

    public void settings() 
    {
        size(1000,1000);
    }

    public void setup() {
        score = 0;
        ship = new SpaceShip(this);
        thrustFile = new File("thrust.wav");
        try {
            AudioInputStream thrustIn = AudioSystem.getAudioInputStream(thrustFile);
            thrust = AudioSystem.getClip();
            thrust.open(thrustIn);
        }
        catch (Exception e) { 
            System.out.println("Unsupported file type");
        }
        opacityI = 100;
        fire = new GhostShip(this, "fire");
        afterimage = new GhostShip(this, "hyper");
        stars = new Star[100];
        asteroids = new ArrayList<Asteroids>();
        for (int i = 0; i < 7; i++) {
            asteroids.add(new Asteroids(this, "big", 0, 0));
        }
        debris = new ArrayList<Debris>();
        explosionFile = new File("bangLarge.wav");
        try {
            AudioInputStream explosionIn = AudioSystem.getAudioInputStream(explosionFile);
            explosion = AudioSystem.getClip();
            explosion.open(explosionIn);
        }
        catch (Exception e) { 
            System.out.println("Unsupported file type");
        }
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(this);
        }
        bullets = new ArrayList<Bullet>();
        bulletFireFile = new File("fire.wav");
        try {
            AudioInputStream bulletFireIn = AudioSystem.getAudioInputStream(bulletFireFile);
            bulletFire = AudioSystem.getClip();
            bulletFire.open(bulletFireIn);
        }
        catch (Exception e) { 
            System.out.println("Unsupported file type");
        }

        bulletLimit = 5;    
        lives = new ArrayList<GhostShip>();
        for (int i = 0; i < 3; i++) {
            lives.add(new GhostShip(this, "life"));
            lives.get(i).setX(15 + (i * 30));
            lives.get(i).setY(25);
            lives.get(i).setPointDirection(-90);
        }
        powerups = new ArrayList<PowerUp>();
        powerups.add(new PowerUp(this, "triShot"));
        powerups.add(new PowerUp(this, "triShot"));
        powerups.add(new PowerUp(this, "extraLife"));
        ufo = new UFO(this);
    }

    public void draw() 
    {
        background(0);
        if (!gameOver) {
            textSize(32);
            fill(255);
            text("Score: " + Integer.toString(score), 7, 75);
            if (!dead) {
                ship.show();
                ship.move();
            }
            for (int i = 0; i < stars.length; i++) {
                stars[i].show();
            }
            for (int i = 0; i < asteroids.size(); i++) {
                asteroids.get(i).show();
                asteroids.get(i).move();
                // Collision with asteroid
                if (!dead && dist(asteroids.get(i).getX(), asteroids.get(i).getY(), ship.getX(), ship.getY()) < asteroids.get(i).getSize() ) {
                    playExplosion();
                    addScore(i);
                    splitAsteroid(i);
                    makeDebris(asteroids.get(i).getX(), asteroids.get(i).getY());
                    asteroids.remove(i);
                    i--;
                    makeDebris(ship.getX(), ship.getY());
                    lives.remove(lives.size() - 1);
                    dead = true;
                    powerUpsOff();
                    if (lives.size() == 0) {
                        gameOver = true;
                    }
                    ship.deathTrack(System.nanoTime()); // Starts death timer
                }
            }

            //Revival
            if (dead && ship.deathTimer(asteroids)) {
                dead = false;
                ship = new SpaceShip(this);
                fire = new GhostShip(this, "fire");
            }

            //Reset asteroids when all destroyed
            if (asteroids.size() == 0) {
                if (!resetting) {
                    resetAsteroids();
                    resetTime = System.nanoTime();
                    resetting = true;
                }
                if (resetAsteroids()) {
                    for (int i = 0; i < 7; i++) {
                        asteroids.add(new Asteroids(this, "big", 0, 0));
                    }
                }
            }

            //Ship movements
            if (!dead) {
                if (accelerate) { // Forward
                    ship.accelerate(0.15);
                    if (!thrust.isRunning()) {
                        thrust.stop();
                        thrust.setFramePosition(0);
                        thrust.start();
                    }
                    if (flicker % 2 == 0) {
                        fire.show();
                    }
                    flicker++;
                    fire.move();
                    fire.accelerate(0.15);
                }
                else {
                    fire.move();
                }
                if (rotateLeft) { // Left
                    ship.rotate(-5);
                    fire.rotate(-5);
                }
                if (rotateRight) { // Right
                    ship.rotate(5);
                    fire.rotate(5);
                }

                if (!accelerate) { // Decelerate
                    ship.setDirectionX(ship.getDirectionX() * 0.975);
                    ship.setDirectionY(ship.getDirectionY() * 0.975);
                    fire.setDirectionX(fire.getDirectionX() * 0.975);
                    fire.setDirectionY(fire.getDirectionY() * 0.975);
                    if (Math.abs(Math.max(ship.myDirectionX, ship.myDirectionY)) < 0.001) {
                        ship.setDirectionX(0.0); 
                        ship.setDirectionY(0.0);
                        fire.setDirectionX(0.0);
                        fire.setDirectionY(0.0);
                    }
                }

                //Hyperspace opacity
                if (hyperspace && opacityI >= 0) {
                    afterimage.show();
                    afterimage.setOpacity(afterimage.getOpacity() - 1);
                    if (afterimage.getOpacity() <= 0) {
                        hyperspace = false;
                        afterimage.setOpacity(100);
                    }
                }
            }

            //Bullet collision with asteroid
            for (int i = 0; i < bullets.size(); i++) {
                boolean collisionFlag = false;
                for (int a = 0; a < asteroids.size(); a++) {
                    if (dist(bullets.get(i).getX(), bullets.get(i).getY(), asteroids.get(a).getX(), asteroids.get(a).getY()) < asteroids.get(a).getSize()) {
                        playExplosion();
                        splitAsteroid(a);
                        addScore(a);
                        makeDebris(asteroids.get(a).getX(), asteroids.get(a).getY());
                        asteroids.remove(a);
                        bullets.remove(i);
                        collisionFlag = true;
                        break;
                    }
                }
                if (collisionFlag) {
                    break;
                }
                bullets.get(i).show();
                bullets.get(i).move();
            }

            //Bullet Time
            for (int i = 0; i < bullets.size(); i++) {
                if (bullets.get(i).lifeSpan()) { // if expired
                    bullets.remove(bullets.get(i));
                    i--;
                }
            }

            //Life display
            for (int i = 0; i < lives.size(); i++) {
                lives.get(i).show();
            }

            //Powerups
            for (int i = 0; i < powerups.size(); i++) {
                powerups.get(i).show();

                if (powerups.get(i).collision(ship) && powerups.get(i).isActive()) { 
                    score += 100;
                    if (powerups.get(i).getType().equals("triShot")) {
                        triShot = true;
                        bulletLimit = 15;
                    }
                    if (powerups.get(i).getType().equals("extraLife")) {
                        lives.add(new GhostShip(this, "life"));
                        lives.get(lives.size() - 1).setX(15 + ((lives.size() - 1) * 30));
                        lives.get(lives.size() - 1).setY(25);
                        lives.get(lives.size() - 1).setPointDirection(-90);
                        powerups.remove(i);
                        i--;
                    }
                    else {
                        powerups.get(i).setActivity(false);
                        powerups.get(i).setDisplayIndex(++displayIndexCounter);
                    }
                }
                if (!powerups.get(i).isActive() && !powerups.get(i).getType().equals("extraLife")) {
                    powerups.get(i).toSideDisplay();
                }
            }

            //Debris
            for (int i = 0; i < debris.size(); i++) {
                if (debris.get(i).despawn()) {
                    debris.remove(i);
                    i--;
                    continue;
                }
                debris.get(i).show();
                debris.get(i).move();
            }

            //UFO
            //ufo.show();

        }
        else if (gameOver) {

            textSize(80);
            fill(255);
            text("Game Over", 285, 400);
            textSize(64);   
            textAlign(CENTER);
            text("Score: " + Integer.toString(score), 285, 500);
            if (mouseX > 377 && mouseX < 622 && mouseY > 657 && mouseY < 706) {
                noFill();
                stroke(255);
                strokeWeight(5);
                rect(377, 657, 245, 49);
                if (mousePressed) {
                    gameOver = false;
                    setup();
                }
            }
            textAlign(0);
            textSize(48);
            text("Play Again", 380, 700);
            fill(255);
            strokeWeight(0);
        }
    }

    public void keyPressed() {
        if (!dead) {
            // Firing
            if ((key == 's' || key == ' ') && bullets.size() < bulletLimit) {
                //https://www.ntu.edu.sg/home/ehchua/programming/java/J8c_PlayingSound.html
                playBulletFire();
                bullets.add(new Bullet(this, ship, 0)); // Straight shots
                bullets.get(bullets.size() - 1).bulletTime(System.nanoTime()); //Start most recent bullet's lifetime
                if (triShot) {
                    for (int i = -1; i <= 1; i += 2) {
                        bullets.add(new Bullet(this, ship, i)); // 2 bullets at angles
                        bullets.get(bullets.size() - 1).bulletTime(System.nanoTime());
                    }
                }
            }
            if (keyCode == UP) {
                accelerate = true;
                keyCode = 0;
            }
            if (keyCode == LEFT) {
                rotateLeft = true;
                keyCode = 0;
            }
            if (keyCode == RIGHT) {
                rotateRight = true;
                keyCode = 0;
            }
            // Hyperspace
            if (key == 'd') {
                hyperspace = true;
                //Create afterimage
                afterimage.setX(ship.getX());
                afterimage.setY(ship.getY());
                afterimage.setPointDirection((int)ship.getPointDirection());
                //Move ship
                ship.setDirectionX(0.0);
                ship.setDirectionY(0.0);
                ship.setX((int)(Math.random() * width));
                ship.setY((int)(Math.random() * height));
                ship.setPointDirection((int)(Math.random() * 360));
                //Adjust fire
                fire.setDirectionX(0.0);
                fire.setDirectionY(0.0);
                fire.setX(ship.getX());
                fire.setY(ship.getY());
                fire.setPointDirection((int)ship.getPointDirection());
            }

        }
    }

    public void keyReleased() {
        if (keyCode == UP) {
            accelerate = false;
        }
        if (keyCode == LEFT) {
            rotateLeft = false;
        }
        if (keyCode == RIGHT) {
            rotateRight = false;
        }
    }

    public void mouseClicked() {
        System.out.println("mouseX: " + mouseX);
        System.out.println("mouseY: " + mouseY);
    }

    public void powerUpsOff() {
        triShot = false;
        bulletLimit = 5;
        //remove display
        for (int i = 0; i < powerups.size(); i++) {
            if (!powerups.get(i).isActive()) {
                powerups.remove(i);
                i--;
            }
        }
        displayIndexCounter = 0;
    }

    public int numInactive() {
        int numInactive = 0;
        for (PowerUp powerup: powerups) {
            if (!powerup.isActive()) {
                numInactive++;
            }
        }
        return numInactive;
    }

    public void splitAsteroid(int index) {
        if (asteroids.get(index).getSize() == 50) {
            asteroids.add(new Asteroids(this, "medium", asteroids.get(index).getX(), asteroids.get(index).getY()));
            asteroids.add(new Asteroids(this, "medium", asteroids.get(index).getX(), asteroids.get(index).getY()));
        }
        if (asteroids.get(index).getSize() == 25) {
            asteroids.add(new Asteroids(this, "small", asteroids.get(index).getX(), asteroids.get(index).getY()));
            asteroids.add(new Asteroids(this, "small", asteroids.get(index).getX(), asteroids.get(index).getY()));
        }
    }

    public void addScore(int index) {
        score += asteroids.get(index).scoreValue();
    }

    public boolean resetAsteroids() {
        if ((System.nanoTime() * Math.pow(10, -9)) - (resetTime * Math.pow(10, -9)) > 2) {
            resetting = false;
            return true;
        }
        return false;
    }

    public void makeDebris(int xGrave, int yGrave) {
        for (int i = 0; i < (int)(Math.random() * 3) + 2; i++) {
            debris.add(new Debris(this, xGrave, yGrave));
            debris.get(debris.size() - 1).setStartTime(System.currentTimeMillis());
        }
    }

    public void playBulletFire() {
        bulletFire.stop();
        bulletFire.setFramePosition(0);
        bulletFire.start();
    }

    public void playExplosion() {
        explosion.stop();
        explosion.setFramePosition(0);
        explosion.start();
    }
}