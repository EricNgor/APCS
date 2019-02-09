//MAIN
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PFont;
import java.util.List;
import java.util.ArrayList;

public class RPG extends PApplet {
    private List<Terrain> walls;
    private static final int cellSize = 32;
    private PFont font;

    //Player
    private Player player;

    //Enemy
    private List<Enemy> enemies;
    private Enemy currEnemy;
    private boolean showEnemy;
    private boolean enemyAttackFlag;

    //Skill
    private Skill currSkill; // used for displaying skill used

    //Battling
    private boolean battling;
    private int selectorYPos;
    private int selectorXPos;
    private boolean battleMain;
    private boolean battleFight;
    private boolean battleInv;
    private boolean battleRun;
    private boolean battleIntro;
    private boolean runSuccess;
    private boolean responseTriangle;

    //Display flags
    private boolean hpPotError;
    private boolean mpPotError;
    private boolean hpPotUsed;
    private boolean mpPotUsed;
    private boolean playerAttack;
    private boolean dispManaError;
    private boolean enemyAttack;
    private boolean enemySlain;
    private boolean enemySlainFlag;
    private boolean slayExitFlag;
    private boolean playerKilled;
    private boolean dispGameOver;
    private boolean lootFound;
    private boolean win;

    private boolean showPlayer;
    private int playerXLoc;
    private int playerYLoc;
    private PImage playerImage;

    private boolean playerTurn;

    private PImage stair;
    private int stairXLoc;
    private int stairYLoc;

    private boolean flag;
    private boolean addExp;
    private String lootName;

    //Boss
    private boolean bossBattle;
    private List<Projectile> projectiles;
    private int bossX;
    private int bossY;
    private PImage bossImg;
    private PImage bossMask;
    private int enemiesXLoc;
    private int enemiesYLoc;
    private boolean projectileShow;
    private int currIndex;
    private int numFrames;
    private List<Integer> wallXLoc = new ArrayList<Integer>();
    private List<Integer> wallYLoc = new ArrayList<Integer>();
    private int currTime;
    private PImage spellCircleTopLeft;
    private PImage spellCircleTopLeftMask;
    private PImage spellCircleTopRight;
    private PImage spellCircleTopRightMask;
    private PImage spellCircleBottomLeft;
    private PImage spellCircleBottomLeftMask;
    private PImage spellCircleBottomRight;
    private PImage spellCircleBottomRightMask;
    private PImage spellCircleBottomMiddle;
    private PImage spellCircleBottomMiddleMask;
    private PImage spellCircle;
    private PImage spellCircleMask;
    private boolean spellCircleTopLeftLit;
    private boolean spellCircleTopRightLit;
    private boolean spellCircleBottomLeftLit;
    private boolean spellCircleBottomRightLit;
    private boolean spellCircleBottomMiddleLit;
    private boolean spellCircleLit;
    private PImage warningSign;
    private PImage warningSignMask;
    private int floorNum;
    private int numEnemies;
    private int currTorchFireTime;

    /*
     * Main method
     */
    public static void main(String[] args) {
        PApplet.main(new String[] {"RPG"});
    }

    public void settings() {
        size(cellSize*32, cellSize*20); // (1024, 640)

        playerXLoc = cellSize*15;
        playerYLoc = cellSize*11;
        player = new Player(this, playerXLoc, playerYLoc);
        playerTurn = true;
        showPlayer = true;
        player.addSkill(Skill.generateSkill("Spark"));
        player.addSkill(Skill.generateSkill("Thunderbolt"));
        player.addSkill(Skill.generateSkill("Splash"));

        makeMap();

        flag = false;
        addExp = true;

        projectiles = new ArrayList<Projectile>();
        projectileShow = true;

        bossImg = loadImage("Boss_Left.png");
        bossMask = loadImage("Boss_Left_Mask.png");

    }

    public void setup() {
        font = createFont("8-Bit Madness.ttf", 32);
        textFont(font);
    }

    public void draw() {
        background(0);
        if (!win) {
            player.show(showPlayer);
        }
        else {
            dispWin();
        }

        if (!battling) {
            for (Terrain wall : walls) {
                wall.show();
                image(stair, stairXLoc, stairYLoc);
            }
            for (int i = 0; i < enemies.size(); i++) {
                enemies.get(i).show();
            }
        }
        if (!bossBattle && (player.getX() == stairXLoc && player.getY() == stairYLoc)) {
            floorNum++;
            if (floorNum == 3) {
                bossBattle = true;
                removeEnemies();
                removeWalls();
            }
            makeMap();
        }

        //gotobossbattle
        if (bossBattle) {
            if (!battling) {
                spellCircle();
                moveTorchFire();
            }
            if (!spellCircleLit) {
                if (millis() - currTorchFireTime >= 1000 && !battling) {
                    throwTorchFire();
                }
                if (!battling && numFrames >= 120) {
                    numFrames = 0;
                    spawnFireWall();
                }
                if (!battling) {
                    if (player.getY() > bossY && player.getX() >= bossX - 160 && player.getX() <= bossX + 160) {
                        bossImg = loadImage("Boss_Down.png");
                        bossMask = loadImage("Boss_Down_Mask.png");
                        bossImg.mask(bossMask);
                    }
                    if (player.getY() <= bossY && player.getX() >= bossX - 160 && player.getX() <= bossX + 160) {
                        bossImg = loadImage("Boss_Up.png");
                        bossMask = loadImage("Boss_Up_Mask.png");
                        bossImg.mask(bossMask);
                    }
                    if (player.getX() > bossX + 160) {
                        bossImg = loadImage("Boss_Right.png");
                        bossMask = loadImage("Boss_Right_Mask.png");
                        bossImg.mask(bossMask);
                    }
                    if (player.getX() < bossX - 160) {
                        bossImg = loadImage("Boss_Left.png");
                        bossMask = loadImage("Boss_Left_Mask.png");
                        bossImg.mask(bossMask);
                    }
                    image(bossImg, bossX, bossY);
                    showFireWall();
                }
            }

            if (!battling) {
                for (int i = 0; i < enemies.size(); i++) {
                    enemies.get(i).show();
                }

                if (player.getHealth() > 0 && !spellCircleLit) {
                    textAlign(LEFT, CENTER);
                    textSize(32);
                    fill(255);
                    text("HEALTH: " + player.getHealth(), width - 155, 16);
                }
            }

            for (int i = 0; i < wallXLoc.size(); i++) {
                if (player.getX() == wallXLoc.get(i) && player.getY() == wallYLoc.get(i)) {
                    player.takeDamage(1);
                }
                if (player.getHealth() <= 0) {
                    playerKilled = true;
                    dispGameOver = true;
                }
            }

        }
        if (battling) {
            textPrompts();
            dispHUD();
            currEnemy.showEnemyBattle();

            if (responseTriangle) {
                responseTriangle();
            }

            //Battle Intro
            if (battleIntro) {
                battleIntro();
            }

            // Running away selected
            if (battleRun) {
                runResult();
            }

            // Fight Actions
            if (battleFight) {
                battleFight();
            }

            // Display using attaack
            if (playerAttack && !enemySlainFlag) {
                dispAttack();
            }

            //Trying to use skill with no mana
            if (dispManaError) {
                dispManaError();
            }

            // Display Inv
            if (battleInv) {
                battleInv();
            }

            //HP and MP Potion errors
            if (hpPotError) {
                player.dispHpPotError();
            }
            else if (hpPotUsed) {
                player.dispHpPotUse();
            }
            if (mpPotError) {
                player.dispMpPotError();
            }
            else if (mpPotUsed) {
                player.dispMpPotUse();
            }

            if (lootFound) {
                dispLootFound();
            }
            else if (enemySlainFlag) {
                dispEnemySlain();
            }

            if (!playerTurn && !enemySlain) {
                if (enemyAttackFlag) {
                    currEnemy.attack(player); 
                    enemyAttackFlag = false;
                    if (player.getHealth() <= 0) {
                        playerKilled = true;
                        responseTriangle = true;
                    }

                }

                if (enemyAttack) {
                    currEnemy.dispAttack();
                }
            }
        }

        if (!spellCircleLit) {
            player.show(showPlayer);
        }

        if (dispGameOver) {
            gameOver();
        }

        numFrames++;
        debug();
    }

    public void makeMap() {
        if (bossBattle) {
            player.setX(cellSize);
            player.setY(cellSize);
            bossX = cellSize * 16;
            bossY = cellSize * 10;
            warningSign = loadImage("WarningSign.png");
            warningSignMask = loadImage("WarningSignMask.png");
            warningSign.mask(warningSignMask);
            spellCircleTopLeft = loadImage("SpellCircle_Top_Left_Unlit.png");
            spellCircleTopRight = loadImage("SpellCircle_Top_Right_Unlit.png");
            spellCircleBottomLeft = loadImage("SpellCircle_Bottom_Left_Unlit.png");
            spellCircleBottomRight = loadImage("SpellCircle_Bottom_Right_Unlit.png");
            spellCircleBottomMiddle = loadImage("SpellCircle_Bottom_Middle_Unlit.png");
            spellCircle = loadImage("SpellCircle_Unlit.png");
        }
        else {
            stair = loadImage("Stair.png");
            stairXLoc = cellSize * (int)(Math.random() * 32);
            stairYLoc = cellSize * (int)(Math.random() * 20);

            playerXLoc = cellSize*15;
            playerYLoc = cellSize*11;
            player.setX(playerXLoc);
            player.setY(playerYLoc);

            walls = new ArrayList<Terrain>();
            for (int i = 0; i < (int)(Math.random() * 50) + 50; i++) {
                int randomGenX = cellSize * (int)(Math.random() * 32);
                int randomGenY = cellSize * (int)(Math.random() * 20);
                while ((randomGenX == playerXLoc && randomGenY == playerYLoc) || (randomGenX == stairXLoc && randomGenY == stairYLoc)) {
                    randomGenX = cellSize * (int)(Math.random() * 32);
                    randomGenY = cellSize * (int)(Math.random() * 20);
                }
                walls.add(new Terrain(this, randomGenX, randomGenY));

                for (int j = 0; j < (int)(Math.random() * 30); j++) {
                    int randomDirectionX = cellSize * ((int)(Math.random() * 4) -2);
                    int randomDirectionY = cellSize * ((int)(Math.random() * 4) -2);
                    while (((randomGenX + randomDirectionX == playerXLoc) || (randomGenY + randomDirectionY == playerYLoc)) || ((randomGenX + randomDirectionX == stairXLoc) || (randomGenY + randomDirectionY == stairYLoc))) {
                        randomDirectionX = cellSize * ((int)(Math.random() * 4) -2);
                        randomDirectionY = cellSize * ((int)(Math.random() * 4) -2);
                    }
                    walls.add(new Terrain(this, randomGenX + randomDirectionX, randomGenY + randomDirectionY));
                }
            }

            enemies = new ArrayList<Enemy>();
            int numEnemies = (int)(Math.random() * 8) + 2; // 2-10

            for (int i = 0; i < numEnemies; i++) {
                enemies.add(Enemy.generateMob(this, false));
                int enemiesXLoc = cellSize * (int)(Math.random() * 32);
                int enemiesYLoc = cellSize * (int)(Math.random() * 20);
                for(int j = 0; j < walls.size(); j++) {
                    while (enemiesXLoc == walls.get(j).getX() && enemiesYLoc == walls.get(j).getY()) {
                        enemiesXLoc = cellSize * (int)(Math.random() * 32);
                        enemiesYLoc = cellSize * (int)(Math.random() * 20);
                    }
                }
                if ((enemiesXLoc != walls.get(i).getX()) && (enemiesYLoc != walls.get(i).getY())) {
                    enemies.get(i).setX(enemiesXLoc);
                    enemies.get(i).setY(enemiesYLoc);
                }
            }
        }
    }

    public void keyPressed() {
        //In Battle
        if (battling) {
            if ((battleInv || battleFight) && key == 'b') { // Back to main from battle
                battleFight = false;
                battleInv = false;
                battleMain = true;
                selectorXPos = 0;
                selectorYPos = 0;
            }
            //Move selector triangle
            if (battleMain || battleInv) {
                if (keyCode == UP) {
                    if (selectorYPos == 0) {
                        selectorYPos = 2;
                    }
                    else {
                        selectorYPos--;
                    }
                }
                if (keyCode == DOWN) {
                    if (selectorYPos == 2) {
                        selectorYPos = 0;
                    }
                    else {
                        selectorYPos++;
                    }
                }
            }

            //Different Selector positiions for battleFight
            if (battleFight) {
                if (keyCode == UP) {
                    if (selectorYPos == 0) {
                        selectorYPos = 1;
                    }else if (selectorYPos == 1) {
                        selectorYPos = 0;
                    }
                }
                if (keyCode == DOWN) {
                    if (selectorYPos == 1) {
                        selectorYPos = 0;
                    }else if (selectorYPos == 0) {
                        selectorYPos = 1;
                    }
                }
                if (keyCode == LEFT) {
                    if (selectorXPos == 0) {
                        selectorXPos = 1;
                    }else if (selectorXPos == 1) {
                        selectorXPos = 0;
                    }
                }
                if (keyCode == RIGHT) {
                    if (selectorXPos == 0) {
                        selectorXPos = 1;
                    }else if (selectorXPos == 1) {
                        selectorXPos = 0;  
                    }
                }
                if (key == ENTER) {
                    boolean skillUsed = false;
                    int skillNum = -1;
                    if (selectorXPos == 0 && selectorYPos == 0) {
                        skillNum = 0;
                    }
                    if (selectorXPos == 1 && selectorYPos == 0) {
                        skillNum = 1;
                    }
                    if (selectorXPos == 0 && selectorYPos == 1) {
                        skillNum = 2;
                    }
                    if (selectorXPos == 1 && selectorYPos == 1) {
                        skillNum = 3;
                    }
                    if (player.getMana() >= player.getSkill(skillNum).getCost()) {
                        skillUsed = true;
                    }
                    else {
                        dispManaError = true;
                        responseTriangle = true;
                    }

                    if (skillUsed) {
                        currEnemy.takeDamage(player.getSkill(skillNum).getDamage());
                        player.spendMana(player.getSkill(skillNum).getCost());
                        currSkill = player.getSkill(skillNum);
                        playerAttack = true;
                    }
                    battleFight = false;
                    responseTriangle = true;

                    //Enemy Slain
                    if (currEnemy.getHealth() <= 0) {
                        enemySlain = true;
                        enemies.remove(currEnemy);
                        if (bossBattle) {
                            currIndex--;   
                        }
                    }
                }
            }
            else {
                if (key == ENTER) {
                    // Respond to triangle
                    //gotoresponseevents
                    if (responseTriangle) {
                        responseTriangle = false;
                        hpPotError = false;
                        mpPotError = false;
                        if (!playerTurn) {
                            switchTurn();
                        }
                        else if (battleRun && runSuccess) { // run away
                            exitBattle();
                        }
                        else if (battleRun && !runSuccess) { // fail to run
                            battleRun = false;
                            switchTurn();
                        }
                        else if (battleIntro) { // intro to menu
                            battleIntro = false;
                            battleMain = true;
                        }
                        else if (playerAttack && !enemySlain) { // after displaying your attack
                            playerAttack = false;
                            switchTurn();
                        }
                        else if (dispManaError) { // after displaying not enough mana
                            battleFight = true;
                            dispManaError = false;
                        }
                        else if (hpPotUsed || mpPotUsed) { // using a potion
                            switchTurn();
                        }
                        else if (enemySlain && !enemySlainFlag) { // after killing enemy; goes to displaying that enemy has been slain
                            enemySlainFlag = true;
                        }
                        else if (slayExitFlag && !lootFound) { // after displaying enemy slain
                            generateLoot();
                            if (!lootFound) {
                                exitBattle();
                            }
                        }
                        else if (lootFound) { // after slaying enemy and loot found
                            exitBattle();
                        }
                        if (playerKilled) { // getting killed
                            dispGameOver = true;
                        }
                    }
                    //Select battle action
                    else if (battleMain) {
                        battleAction();
                        battleMain = false;
                        selectorYPos = 0;
                    }
                    else if (battleInv) {
                        battleAction();
                    }
                    //Through potion usage
                    else if (hpPotUsed || mpPotUsed) {
                        hpPotUsed = false;
                        mpPotUsed = false;
                        switchTurn();
                    }
                }
            }
        }

        //Not in battle
        if (!battling) {
            if (key == CODED) {
                //Move player
                boolean moved = false;
                if (keyCode == RIGHT) {
                    if (!player.rightBlocked(walls)) {
                        player.move("right");
                        moved = true;
                    }
                    playerImage = loadImage("Player_Right.png");
                    player.setDirection("right");
                    player.setPlayerImage(playerImage);
                    player.setPlayerMask(loadImage("Player_Right_Mask.png"));

                }
                if (keyCode == LEFT) {
                    if (!player.leftBlocked(walls)) {
                        player.move("left");
                        moved = true;
                    }
                    playerImage = loadImage("Player_Left.png");
                    player.setDirection("left");
                    player.setPlayerImage(playerImage);
                    player.setPlayerMask(loadImage("Player_Left_Mask.png"));
                }
                if (keyCode == UP) {
                    if (!player.upBlocked(walls)) {
                        player.move("up");
                        moved = true;
                    }
                    playerImage = loadImage("Player_Up.png");
                    player.setDirection("up");
                    player.setPlayerImage(playerImage);
                    player.setPlayerMask(loadImage("Player_Up_Mask.png"));
                }
                if (keyCode == DOWN) {
                    if (!player.downBlocked(walls)) {
                        player.move("down");
                        moved = true;
                    }
                    playerImage = loadImage("Player_Down.png");
                    player.setDirection("down");
                    player.setPlayerImage(playerImage);
                    player.setPlayerMask(loadImage("Player_Down_Mask.png"));
                }
                if (moved) {
                    encounterEnemy();
                    enemyMove();
                    encounterEnemy();
                }
            }
        }

        // TESTING COMMANDS ONLY
        if (key == 'r') { // resets game
            makeMap();
        }
        if (key == 's') { // enters battle
            battling = true;
            currEnemy = enemies.get(0);
            battleIntro = true;
            responseTriangle = true;
            showPlayer = false;
            showEnemy = false;
        }
        if (key == 32) {
            for(int i = 0; i < walls.size(); i++) {
                if (player.getDirection().equals("up") && (walls.get(i).getX() == player.getX() && walls.get(i).getY() + cellSize == player.getY())) {
                    walls.remove(i);
                }
                if (player.getDirection().equals("down") && (walls.get(i).getX() == player.getX() && walls.get(i).getY() - cellSize == player.getY())) {
                    walls.remove(i);
                }
                if (player.getDirection().equals("left") && (walls.get(i).getX() == player.getX() - cellSize && walls.get(i).getY() == player.getY())) {
                    walls.remove(i);
                }
                if (player.getDirection().equals("right") && (walls.get(i).getX() == player.getX() + cellSize && walls.get(i).getY() == player.getY())) {
                    walls.remove(i);
                }
            }
        }
    }

    public void dispHUD() {
        textSize(40);
        textAlign(LEFT, CENTER);

        //PLAYER
        stroke(255);
        strokeWeight(3);
        fill(0, 0);
        //White boxes
        rect(93, 134, 263, 95);
        strokeWeight(3);
        line(93, 181, 353, 181);
        strokeWeight(0);
        //line(148, 137, 148, 225);

        fill(255);
        strokeWeight(0);

        text("PLAYER", 100, 100);

        fill(255, 0, 0);
        rect(150, 138, player.getHealth()*2, 40); // 50 Right of PLAYER, 42 down of PLAYER
        fill(255);
        text("HP", 100, 157); // 57 below PLAYER; 15 below bar; 50 Left of bar
        text(Integer.toString(player.getHealth()), 225, 157); // 125 Right of HP

        fill(0, 0, 255);
        rect(150, 185, player.getMana()*2, 40); // 42 lower than HP bar
        fill(255);
        text("MP", 100, 199); // 15 lower than bar
        text(Integer.toString(player.getMana()), 225, 199);

        //-----------------------------------------------------------------------------------------
        //ENEMY
        //White box
        stroke(255);
        strokeWeight(3);
        fill(0, 0);
        rect(width-303, 137, 260, 47);
        strokeWeight(0);
        fill(255);

        text(currEnemy.getName(), width-300, 100);
        fill(255, 0, 0);
        rect(width-250, 142, currEnemy.getHealthPercent()*2, 40);

        fill(255);
        text("HP", width-300, 157);
        text(Integer.toString(currEnemy.getHealth()), width-175, 157);

    }

    //gototextprompts
    /**
     * Displays text frame and prompts for action
     */
    public void textPrompts() {
        //Text Frame
        fill(0, 0);
        stroke(255);
        strokeWeight(3);
        rect(20, height - 150, width - 40, 120);
        fill(255);
        strokeWeight(3);
        if (battleMain || battleInv) {
            fill(255);
            line(730, 491, 730, 609);
        }
        strokeWeight(1);

        if (playerTurn) {
            //Encounter Text
            if ((battleMain || !battleRun) && !responseTriangle && !battleFight && !enemySlain/*&& player.getExperience() < 10*/) {
                textAlign(CENTER, CENTER);
                textSize(40);
                text("What will you do?", 375, height - 93);
            }

            //Action Text
            if (battleMain) {
                textSize(48);
                textAlign(LEFT, CENTER);
                text("FIGHT", 780, 510);
                text("INVENTORY", 780, 545);
                text("RUN", 780, 580);
            }

            //Selector
            if (battleMain || battleInv) {
                triangle(776, 513 + (selectorYPos*35), 762, 503 + (selectorYPos*35), 762, 523 + (selectorYPos*35));
            }

            if (battleFight) {
                triangle(80 + (selectorXPos*400), 530 + (selectorYPos*45), 66 + (selectorXPos*400), 520 + (selectorYPos*45), 66 + (selectorXPos*400), 540 + (selectorYPos*45));
            }
        }

    }

    //gotobattleaction
    /**
     * Runs when action is selected from battleMain
     */
    public void battleAction() {
        //Main Selection
        if (battleMain) {
            if (selectorYPos == 0) {
                battleFight = true;
            } else if (selectorYPos == 1) {
                battleInv = true;
            } else if (selectorYPos == 2) {
                runSuccess = run(); // set true if ran away successfully
                battleRun = true;
            }
        }
        //Inventory Selection
        else if (battleInv) {
            if (selectorYPos == 0) {
                hpPotError = !player.useHpPot(); // set to true if no more potions
                hpPotUsed = !hpPotError;
                responseTriangle = true;
                if (!hpPotError) { // stays in inventory if error occurred, else leave
                    battleInv = false;
                }
            } else if (selectorYPos == 1) {
                mpPotError = !player.useMpPot();
                mpPotUsed = !mpPotError;
                responseTriangle = true;
                if (!mpPotError) {
                    battleInv = false;
                }
            } else if (selectorYPos == 2) {
                battleMain = true;
                battleInv = false;
                selectorYPos = 0;
            }
        }
    }

    //gotobattleintro
    public void battleIntro() {
        textAlign(CENTER, CENTER);
        text("A" + (currEnemy.vowelName() ? "n " : " ") + currEnemy.getName() + " has appeared!", width/2, height-93); 
    }

    //gotobattlefight
    public void battleFight() {
        textAlign(LEFT, CENTER);
        textSize(32);
        strokeWeight(3);
        stroke(255);
        fill(255);
        line(670, 491, 670, 609);
        //Skill layout: 0 1
        //              2 3
        text(player.getSkill(0).getName(), 95, 525);
        try {
            text(player.getSkill(1).getName(), 495, 525);
        }
        catch (NullPointerException e) {
            text("---", 500, 525);
        }
        try {
            text(player.getSkill(2).getName(), 95, 570);
        }
        catch (NullPointerException e) {
            text("---", 100, 570);
        }
        try {
            text(player.getSkill(3).getName(), 495, 570);
        }
        catch (NullPointerException e) {
            text("---", 500, 570);
        }

        text("Press 'b' to go back", 708, 547.5f);
    }

    //gotodispattack
    public void dispAttack() {
        textAlign(CENTER, CENTER);
        textSize(40);
        text("You used " + currSkill.getName() + " and dealt " + currSkill.getDamage() + " damage!", width/2, height-93);
    }

    public void dispManaError() {
        textAlign(CENTER, CENTER);
        textSize(40);
        text("Not enough MANA!", width/2, height-93);
    }

    //gotogenerateloot
    public void generateLoot() {
        int roll = (int)(Math.random() * 6) + 1; // 1-6 
        if (roll == 1) {
            player.addHpPot();
            lootName = "HEALTH POTION";
            lootFound = true;
            responseTriangle = true;
        }
        else if (roll == 2) {
            player.addMpPot();
            lootName = "MANA POTION";
            lootFound = true;
            responseTriangle = true;
        }
    }

    //gotodisplootfound
    public void dispLootFound() {
        textAlign(CENTER, CENTER);
        textSize(32);
        text("You found a " + lootName + "!", width/2, height-93);
    }

    public void dispEnemySlain() {
        textAlign(CENTER, CENTER);
        textSize(40);
        text(currEnemy.getName() + " has been slain!", width/2, height-93);
        slayExitFlag = true;
        responseTriangle = true;
    }

    public void battleInv() {
        //Inv text
        textAlign(LEFT, CENTER);
        textSize(28);
        fill(255);
        text("HEALTH POTION x" + player.getHpPotCount(), 780, 510);
        text("MANA POTION x" + player.getMpPotCount(), 780, 545);
        text("BACK", 780, 580);
    }

    //gotorun
    /**
     * When run, 10% chance of returning false
     */
    public boolean run() {
        int failure = (int)(Math.random() * 8) + 1;
        if (failure == 1) {
            return false;
        }
        return true;

    }

    //gotorunresult
    public void runResult() {
        textAlign(CENTER, CENTER);
        responseTriangle = true;
        if (runSuccess) {
            fill(255);
            text("You ran away!", width/2, height - 93);
        }
        else {
            fill(255);
            text("You failed to escape!", width/2, height - 93);
        }
    }

    //gotoexitbattle
    /**
     * Exits battle and sets booleans and variables accordingly
     */
    public void exitBattle() {
        showPlayer = true;
        showEnemy = true;
        battling = false; 
        battleMain = false;
        battleFight = false;
        playerAttack = false;
        battleInv = false;
        battleRun = false;
        if (currEnemy.getX() == player.getX() && currEnemy.getY() == player.getY()) {
            if (!currEnemy.leftBlocked(walls, enemies)) {
                currEnemy.setX(player.getX() - cellSize);
            }
            else if (!currEnemy.rightBlocked(walls, enemies)) {
                currEnemy.setX(player.getX() + cellSize);
            }
            else if (!currEnemy.upBlocked(walls, enemies)) {
                currEnemy.setY(player.getY() - cellSize);
            }
            else if (!currEnemy.downBlocked(walls, enemies)) {
                currEnemy.setY(player.getY() + cellSize);
            }
        }
        slayExitFlag = false;
        enemySlain = false;
        enemySlainFlag = false;
        lootFound = false;
        lootName = "";
        currEnemy = null;
        responseTriangle = false;
        selectorYPos = 0;
        selectorXPos = 0;
    }

    //gotoswitchturn
    public void switchTurn() {
        if (!playerTurn) { // Enemy to player
            battleMain = true;
            selectorXPos = 0;
            selectorYPos = 0;
            enemyAttack = false;

        }
        else if (currEnemy != null) { //Player to Enemy
            battleMain = false;
            playerAttack = false;
            enemyAttack = true;
            enemyAttackFlag = true;
            responseTriangle = true;
        }
        playerTurn = !playerTurn;

        hpPotUsed = false;
        mpPotUsed = false;

    }

    //gotoenemymove    
    public void enemyMove() {
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).move(walls, enemies, player.getX(), player.getY());
        }
    }

    //gotoencounterenemy
    public void encounterEnemy() {
        for (int i = 0; i < enemies.size(); i++) {
            if ((enemies.get(i).getX() == player.getX()) && (enemies.get(i).getY() == player.getY())) {
                battling = true;
                battleMain = true;
                showPlayer = false;
                currEnemy = enemies.get(i);
                currEnemy.show();
                //                 showEnemyBattle = true;
                projectileShow = false;
            }
        }
    }

    //gototorchfire
    public void throwTorchFire() {
        if (projectiles.size() == 0) {
            for (int i = 0; i < 1; i++) {
                projectiles.add(i, new Projectile(this, bossX, bossY));
            }
        }
    }

    public void moveTorchFire() {
        for (int j = 0; j < projectiles.size(); j++) {
            projectiles.get(j).show();
            projectiles.get(j).move(player.getX(), player.getY());
            if (dist(projectiles.get(j).getX(), projectiles.get(j).getY(), player.getX(), player.getY()) <= 32) {
                projectiles.remove(j);
                j--;
                currTorchFireTime = millis();
                player.takeDamage(5);

                for (int i = 0; i < 1; i++) {
                    enemies.add(Enemy.generateMob(this, true));
                    enemiesXLoc = player.getX() + cellSize * ((int)(Math.random() * 4) - 2);
                    enemiesYLoc = player.getY() + cellSize * ((int)(Math.random() * 4) - 2);
                    while (enemiesXLoc == bossX || enemiesXLoc == player.getX() || enemiesXLoc > width || enemiesXLoc < 0) {
                        enemiesXLoc = player.getX() + cellSize * ((int)(Math.random() * 4) - 2);
                    }
                    while (enemiesYLoc == bossY || enemiesYLoc == player.getY() || enemiesYLoc > height || enemiesYLoc < 0) {
                        enemiesYLoc = player.getY() + cellSize * ((int)(Math.random() * 4) - 2);
                    }
                    enemies.get(currIndex).setX(enemiesXLoc);
                    enemies.get(currIndex).setY(enemiesYLoc);
                    currIndex++;
                }
                for (int i = 0; i < enemies.size(); i++) {
                    if (enemies.size() > 5) { // limit to 5 torchlings max
                        enemies.remove(0);
                        currIndex--;
                    }
                }
            }
            else if (projectiles.get(j).getX() > width || projectiles.get(j).getX() < 0) {
                projectiles.remove(j);
                j--;
                currTorchFireTime = millis();
            }
            else if (projectiles.get(j).getY() > height || projectiles.get(j).getY() < 0) {
                projectiles.remove(j);
                j--;
                currTorchFireTime = millis();
            }
        }
    }

    //gotospawnfirewall
    public void spawnFireWall() {
        currTime = millis();
        removeFireWalls();
        for (int i = 0; i < 10; i++) {
            int newWallX = (int)(Math.random() * 32) * cellSize;
            int newWallY = (int)(Math.random() * 20) * cellSize;
            while((newWallX == bossX && newWallY == bossY)) {
                newWallX = (int)(Math.random() * 32) * cellSize;
                newWallY = (int)(Math.random() * 20) * cellSize;
            }
            wallXLoc.add(newWallX);
            wallYLoc.add(newWallY);
        }
    }

    //gotoshowfirewall
    public void showFireWall() {
        if (millis() - currTime >= 1000) {
            for (int j = 0; j < wallXLoc.size(); j++) {
                PImage wallOfFire = loadImage("WallOfFire.png");
                PImage wallOfFireMask = loadImage("WallOfFireMask.png");
                wallOfFire.mask(wallOfFireMask);
                image(wallOfFire, wallXLoc.get(j), wallYLoc.get(j));
            }
        }
        else {
            for (int j = 0; j < wallXLoc.size(); j++) {
                fill(100);
                image(warningSign, wallXLoc.get(j), wallYLoc.get(j));
            }
        }
    }

    //gotospellcircle
    public void spellCircle() {
        if (spellCircleTopLeftLit && spellCircleTopRightLit && spellCircleBottomLeftLit && spellCircleBottomRightLit && spellCircleBottomMiddleLit) {
            spellCircleLit = true;
            removeProjectiles();
            removeEnemies();
            win = true;
        }
        if ((player.getX() >= 405 && player.getX() <= 470) && (player.getY() >= 180 && player.getY() <= 264)) {
            spellCircleTopLeftLit = true;
        }
        if ((player.getX() >= 560 && player.getX() <= 624) && (player.getY() >= 180 && player.getY() <= 264)) {
            spellCircleTopRightLit = true;
        }
        if ((player.getX() >= 350 && player.getX() <= 414) && (player.getY() >= 350 && player.getY() <= 414)) {
            spellCircleBottomLeftLit = true;
        }
        if ((player.getX() >= 605 && player.getX() <= 670) && (player.getY() >= 350 && player.getY() <= 414)) {
            spellCircleBottomRightLit = true;
        }
        if ((player.getX() >= 480 && player.getX() <= 544) && (player.getY() >= 445 && player.getY() <= 510)) {
            spellCircleBottomMiddleLit = true;
        }

        if (spellCircleLit) {
            spellCircle = loadImage("SpellCircle_Lit.png");
        }
        if (spellCircleTopLeftLit) {
            spellCircleTopLeft = loadImage("SpellCircle_Top_Left_Lit.png");
        }
        if (spellCircleTopRightLit) {
            spellCircleTopRight = loadImage("SpellCircle_Top_Right_Lit.png");
        }
        if (spellCircleBottomLeftLit) {
            spellCircleBottomLeft = loadImage("SpellCircle_Bottom_Left_Lit.png");
        }
        if (spellCircleBottomRightLit) {
            spellCircleBottomRight = loadImage("SpellCircle_Bottom_Right_Lit.png");
        }
        if (spellCircleBottomMiddleLit) {
            spellCircleBottomMiddle = loadImage("SpellCircle_Bottom_Middle_Lit.png");
        }

        spellCircleMask = loadImage("SpellCircle_Unlit_Mask.png");
        spellCircle.mask(spellCircleMask);

        spellCircleTopLeftMask = loadImage("SpellCircle_Top_Left_Mask.png");
        spellCircleTopLeft.mask(spellCircleTopLeftMask);

        spellCircleTopRightMask = loadImage("SpellCircle_Top_Right_Mask.png");
        spellCircleTopRight.mask(spellCircleTopRightMask);

        spellCircleBottomLeftMask = loadImage("SpellCircle_Bottom_Left_Mask.png");
        spellCircleBottomLeft.mask(spellCircleBottomLeftMask);

        spellCircleBottomRightMask = loadImage("SpellCircle_Bottom_Right_Mask.png");
        spellCircleBottomRight.mask(spellCircleBottomRightMask);

        spellCircleBottomMiddleMask = loadImage("SpellCircle_Bottom_Middle_Mask.png");
        spellCircleBottomMiddle.mask(spellCircleBottomMiddleMask);

        image(spellCircle, bossX - 160, bossY - 128);
        image(spellCircleTopLeft, 405, 200);
        image(spellCircleTopRight, 560, 200);
        image(spellCircleBottomLeft, 352, 350);
        image(spellCircleBottomRight, 607, 349);
        image(spellCircleBottomMiddle, 480, 445);
    }

    //gotoremove
    public void removeProjectiles() {
        while (projectiles.size() > 0) {
            projectiles.remove(0);
        }
    }

    public void removeWalls() {
        while (walls.size() > 0) {
            walls.remove(0);
        }
    }

    public void removeEnemies() {
        while (enemies.size() > 0) {
            enemies.remove(0);
        }
    }

    public void removeFireWalls() {
        while (wallXLoc.size() > 0) {
            wallXLoc.remove(0);
            wallYLoc.remove(0);
        }
    }

    //gotogameover
    public void gameOver() {
        textAlign(CENTER, CENTER);
        textSize(64);
        background(0);
        text("Game Over", width/2, height/2);
    }

    //gotowin
    public void dispWin() {
        textAlign(CENTER, CENTER);
        textSize(64);
        background(0);
        text("You Win!", width/2, 100);
    }

    public void responseTriangle() {
        fill(255);
        stroke(255);
        strokeWeight(1);
        triangle(705, 589, 721, 589, 713, 603);
    }

    /**
     * Returns the value of the side length of a cell
     */
    public static int cellSize() {
        return cellSize;
    }

    public void mousePressed() {
        //         System.out.println("x: " + mouseX);
        //         System.out.println("y: " + mouseY);
    }

    public void debug() {
        // System.out.println("battleMain: " + battleMain);
        // System.out.println("battleRun: " + battleRun);
        // System.out.println("battleInv: " + battleInv);
        // System.out.println("selectorYPos: " + selectorYPos);
        // System.out.println("hpPotError: " + hpPotError);
        // System.out.println("mpPotError: " + mpPotError);
        // System.out.println("playerTurn: " + playerTurn);
        // System.out.println("showEnemy: " + showEnemy);
        // System.out.println("num of enemies: " + enemies.size());
        //         try {
        //             System.out.println("healthPercent: " + currEnemy.getHealthPercent());
        //         }
        //         catch (NullPointerException e){};
        //         System.out.println(currEnemy);
        //         System.out.println("enemySlain: " + enemySlain);
        //         System.out.println("lootFound: " + lootFound);
        //         System.out.println(millis() - currTorchFireTime);
        //         System.out.println("playerKilled: " + playerKilled);
        //         System.out.println("dispGameOver: " + dispGameOver);
        //         System.out.println("numProjectiles: " + projectiles.size());
        //         try {
        //             System.out.println("enemyHealth: " + currEnemy.getHealth());
        //         }
        //         catch (NullPointerException e) {};
        //         System.out.println("currIndex: " + currIndex);
    }
}