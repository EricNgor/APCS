//PLAYER
import processing.core.PApplet;
import java.util.List;
import java.util.ArrayList;
import processing.core.PImage;

public class Player {
    private PApplet applet;
    private int health;
    private int mana;
    private int x; private int y;
    private int hpPotCount;
    private int mpPotCount;
    private Skill[] skills;
    private String direction;
    private PImage playerImage;
    private PImage playerBattle;
    //private int experience;

    public Player(PApplet applet, int xLoc, int yLoc) {
        this.applet = applet;
        health = 100;
        mana = 100;
        hpPotCount = 1;
        mpPotCount = 1;
        x = xLoc;
        y = yLoc;
        skills = new Skill[4];
        playerImage = applet.loadImage("Player_Up.png");
        playerBattle = applet.loadImage("Player_Battle.png");

        playerImage.mask(applet.loadImage("Player_Up_Mask.png"));
        playerBattle.mask(applet.loadImage("Player_Battle_Mask.png"));
    }

    public void show(boolean showPlayer) {
        if (showPlayer) {
            applet.image(playerImage, x, y);
        }
        else {
            applet.image(playerBattle, 100, 285);
        }
    }

    public void move(String direction) {
        if (direction.equals("up")) {
            y -= RPG.cellSize();
        } else if (direction.equals("down")) {
            y += RPG.cellSize();
        } else if (direction.equals("left")) {
            x -= RPG.cellSize();
        } else if (direction.equals("right")) {
            x += RPG.cellSize();
        }
    }

    public void takeDamage(int val) {
        health -= val;
        if (health <= 0) {
            health = 0;
        }
    }

    public void heal(int val) {
        health += val;
    }

    public int getHealth() {
        return health;
    }

    public void spendMana(int val) {
        mana -= val;
    }

    public void restoreMana(int val) {
        mana += val;
    }

    public int getMana() {
        return mana;
    }

    /**
     * Increases health by 50; returns false if no potions
     */
    public boolean useHpPot() {
        if (hpPotCount <= 0) {
            return false;
        }
        heal(50);
        hpPotCount--;
        if (health > 100) {
            health = 100;
        }
        return true;
    }

    /**
     * Increases mana by 40; returns false if no potions
     */
    public boolean useMpPot() {
        if (mpPotCount <= 0) {
            return false;
        }
        restoreMana(40);
        mpPotCount--;
        if (mana > 100) {
            mana = 100;
        }
        return true;
    }

    public void addHpPot() {
        hpPotCount++;
    }

    public void addMpPot() {
        mpPotCount++;
    }

    public int getHpPotCount() {
        return hpPotCount;
    }

    public int getMpPotCount() {
        return mpPotCount;
    }

    public void dispHpPotError() {
        applet.textAlign(applet.CENTER, applet.CENTER);
        applet.textSize(40);
        applet.text("You don't have any more HEALTH POTIONs!", 370, applet.height-93);
    }

    public void dispMpPotError() {
        applet.textAlign(applet.CENTER, applet.CENTER);
        applet.textSize(40);
        applet.text("You don't have any more MANA POTIONs!", 370, applet.height-93);
    }

    public void dispHpPotUse() {
        applet.textAlign(applet.CENTER, applet.CENTER);
        applet.textSize(40);
        applet.text("You gained 50 HEALTH!", applet.width/2, applet.height-93);
    }

    public void dispMpPotUse() {
        applet.textAlign(applet.CENTER, applet.CENTER);
        applet.textSize(40);
        applet.text("You gained 40 MANA!", applet.width/2, applet.height-93);
    }

    public void addSkill(Skill skill) {
        for (int i = 0; i < skills.length; i++) {
            if (skills[i] == null) {
                skills[i] = skill;
                return;
            }
        }
    }

    public Skill getSkill(int skillLoc) {
        return skills[skillLoc];
    }

    public boolean rightBlocked(List<Terrain> walls) {
        for (Terrain wall : walls) {
            if (wall.getX() - RPG.cellSize() == x && wall.getY() == y) {
                return true;
            }
        }
        if (x + RPG.cellSize() == applet.width) {
            return true;
        }
        return false;
    }

    public boolean leftBlocked(List<Terrain> walls) {
        for (Terrain wall : walls) {
            if (wall.getX() + RPG.cellSize() == x && wall.getY() == y) {
                return true;
            }
        }
        if (x == 0) {
            return true;
        }
        return false;
    }

    public boolean upBlocked(List<Terrain> walls) {
        for (Terrain wall : walls) {
            if (wall.getX() == x && wall.getY() + RPG.cellSize() == y) {
                return true;
            }
        }
        if (y == 0) {
            return true;
        }
        return false;
    }

    public boolean downBlocked(List<Terrain> walls) {
        for (Terrain wall : walls) {
            if (wall.getX() == x && wall.getY() - RPG.cellSize() == y) {
                return true;
            }
        }
        if (y + RPG.cellSize() == applet.height) {
            return true;
        }
        return false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int xLoc) {
        x = xLoc;
    }

    public void setY(int yLoc) {
        y = yLoc;
    }

    public void setPlayerImage(PImage playerImage_) {
        playerImage = playerImage_;
    }

    public void setPlayerMask(PImage playerMask_) {
        playerImage.mask(playerMask_);
    }

    public void setDirection(String str) {
        direction = str;
    }

    public String getDirection() {
        return direction;
    }
    // public int getExperience() {
    // return experience;
    // }

    // public void addExperience(int experienceGain) {
    // experience += experienceGain;
    // }
}

