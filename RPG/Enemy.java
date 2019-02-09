import processing.core.PApplet;
import processing.core.PImage;
import java.util.List;
import java.util.ArrayList;
/*
 * Generic Enemy
 */
public abstract class Enemy
{
    protected static PApplet applet;
    protected String name;
    protected int health;
    protected int maxHealth;
    protected int damage;
    //protected int experience;
    //Image
    protected PImage enemyImage;
    protected String enemyUp;
    protected String enemyDown;
    protected String enemyLeft;
    protected String enemyRight;
    protected String enemyBattle;
    protected PImage enemyBattleImage;
    //Mask
    protected PImage enemyMask;
    protected String maskUp;
    protected String maskDown;
    protected String maskLeft;
    protected String maskRight;
    protected String maskBattle;
    protected PImage enemyBattleMask;

    protected int enemyDirection;
    protected int x; int y;

    public Enemy(String name, int health, int damage, String enemyUp, String enemyDown, String enemyLeft, String enemyRight, String enemyBattle, String maskUp, String maskDown, String maskLeft, String maskRight, String maskBattle /*int experience*/) {
        this.name = name;
        this.health = health;
        maxHealth = health;
        this.damage = damage;
        
        // Load in initial facing position
        int x = (int)(Math.random() * 4);
        switch(x) { 
            case 0: enemyImage = applet.loadImage(enemyUp);  enemyMask = applet.loadImage(maskUp); break;
            case 1: enemyImage = applet.loadImage(enemyDown); enemyMask = applet.loadImage(maskDown); break;
            case 2: enemyImage = applet.loadImage(enemyLeft); enemyMask = applet.loadImage(maskLeft); break;
            case 3: enemyImage = applet.loadImage(enemyRight); enemyMask = applet.loadImage(maskRight); break;

            default:
        }
        //Enemy Images
        enemyBattleImage = applet.loadImage(enemyBattle);
        this.enemyUp = enemyUp;
        this.enemyDown = enemyDown;
        this.enemyLeft = enemyLeft;
        this.enemyRight = enemyRight;
        this.enemyBattle = enemyBattle;
        //Enemy Masks
        enemyBattleMask = applet.loadImage(maskBattle);
        this.maskUp = maskUp;
        this.maskDown = maskDown;
        this.maskLeft = maskLeft;
        this.maskRight = maskRight;
        this.maskBattle = maskBattle;

        //this.experience = experience;
    }

    /*
     * Generates random Enemy
     */
    public static Enemy generateMob(PApplet applet_, boolean bossBattle) {
        applet = applet_;
        int mobNum = (int)(Math.random() * 3) + 1; // 1 - 2
        if (bossBattle) { // only spawn torchlings in boss battle
            mobNum = 4;
        }
        switch(mobNum) {
            case 1: return new Goblin();
            case 2: return new Goblin();
            case 3: return new Skeleton();
            case 4: return new Torchling();

            default : return null;
        }

    }

    public void show() {
        enemyImage.mask(enemyMask);
        applet.image(enemyImage, x, y);
    }

    public void showEnemyBattle() {
        enemyBattleImage.mask(enemyBattleMask);
        applet.image(enemyBattleImage, applet.width - 300, (applet.height/2)-100);
    }

    public void move(List<Terrain> walls, List<Enemy> enemies, int playerX, int playerY) {
        enemyDirection = (int)(Math.random() * 4);

        if (!upBlocked(walls, enemies) && (x == playerX && y - RPG.cellSize() == playerY)) {
            y -= RPG.cellSize();
            enemyImage = applet.loadImage(enemyUp);
            enemyMask = applet.loadImage(maskUp);
        }
        else if (!downBlocked(walls, enemies) && (x == playerX && y + RPG.cellSize() == playerY)) {
            y += RPG.cellSize();
            enemyImage = applet.loadImage(enemyDown);
            enemyMask = applet.loadImage(maskDown);
        }
        else if (!leftBlocked(walls, enemies) && (x - RPG.cellSize() == playerX && y == playerY)) {
            x -= RPG.cellSize();
            enemyImage = applet.loadImage(enemyLeft);
            enemyMask = applet.loadImage(maskLeft);
        }
        else if (!rightBlocked(walls, enemies) && (x + RPG.cellSize() == playerX && y == playerY)) {
            x += RPG.cellSize();
            enemyImage = applet.loadImage(enemyRight);
            enemyMask = applet.loadImage(maskRight);
        }
        else if (!upBlocked(walls, enemies) && y > playerY && enemyDirection == 0) {
            y -= RPG.cellSize();
            enemyImage = applet.loadImage(enemyUp);
            enemyMask = applet.loadImage(maskUp);
        }
        else if (!downBlocked(walls, enemies) && y < playerY && enemyDirection == 1) {
            y += RPG.cellSize();
            enemyImage = applet.loadImage(enemyDown);
            enemyMask = applet.loadImage(maskDown);
        }
        else if (!leftBlocked(walls, enemies) && x > playerX && enemyDirection == 2) {
            x -= RPG.cellSize();
            enemyImage = applet.loadImage(enemyLeft);
            enemyMask = applet.loadImage(maskLeft);
        }
        else if (!rightBlocked(walls, enemies) && x < playerX && enemyDirection == 3) {
            x += RPG.cellSize();
            enemyImage = applet.loadImage(enemyRight);
            enemyMask = applet.loadImage(maskRight);
        }
    }

    public String getName() {
        return name;
    }

    public int getHealthPercent() {
        double hpDouble = (double)health;
        double maxhpDouble = (double)maxHealth;
        return (int)((hpDouble/maxhpDouble)*100);

    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public boolean vowelName() {
        return (name.charAt(0) == 'A' || name.charAt(0) == 'E' || name.charAt(0) == 'I' || name.charAt(0) == 'O' || name.charAt(0) == 'U');
    }

    public void takeDamage(int val) {
        health -= val;
        if (health < 0) {
            health = 0;
        }
    }

    public void attack(Player player) {
        player.takeDamage(damage);
    }

    public void dispAttack() {
        applet.textAlign(applet.CENTER, applet.CENTER);
        applet.textSize(40);
        applet.text(name + " hit you for " + damage + " damage!", applet.width/2, applet.height-93);
    }
    // public int getExperience() {
    // return experience;
    // }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int newX) {
        x = newX;
    }

    public void setY(int newY) {
        y = newY;
    }

    public boolean rightBlocked(List<Terrain> walls, List<Enemy> enemies) {
        for (Terrain wall : walls) {
            if (wall.getX() - RPG.cellSize() == x && wall.getY() == y) {
                return true;
            }
        }
        for (Enemy enemy : enemies) {
            if (enemy.getX() - RPG.cellSize() == x && enemy.getY() == y) {
                return true;
            }
        }
        if (x + RPG.cellSize() == applet.width) {
            return true;
        }
        return false;
    }

    public boolean leftBlocked(List<Terrain> walls, List<Enemy> enemies) {
        for (Terrain wall : walls) {
            if (wall.getX() + RPG.cellSize() == x && wall.getY() == y) {
                return true;
            }
        }
        for (Enemy enemy : enemies) {
            if (enemy.getX() + RPG.cellSize() == x && enemy.getY() == y) {
                return true;
            }
        }
        if (x == 0) {
            return true;
        }
        return false;
    }

    public boolean upBlocked(List<Terrain> walls, List<Enemy> enemies) {
        for (Terrain wall : walls) {
            if (wall.getX() == x && wall.getY() + RPG.cellSize() == y) {
                return true;
            }
        }
        for (Enemy enemy : enemies) {
            if (enemy.getX() == x && enemy.getY() + RPG.cellSize() == y) {
                return true;
            }
        }
        if (y == 0) {
            return true;
        }
        return false;
    }

    public boolean downBlocked(List<Terrain> walls, List<Enemy> enemies) {
        for (Terrain wall : walls) {
            if (wall.getX() == x && wall.getY() - RPG.cellSize() == y) {
                return true;
            }
        }
        for (Enemy enemy : enemies) {
            if (enemy.getX() == x && enemy.getY() - RPG.cellSize() == y) {
                return true;
            }
        }
        if (y + RPG.cellSize() == applet.height) {
            return true;
        }
        return false;
    }
}

