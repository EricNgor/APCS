import processing.core.PApplet;
import processing.core.PImage;
public class Chess extends PApplet {
    private PImage board;
    //BLACK
    private BlackPawn bPawn;
    private BlackRook bRook;
    private BlackKnight bKnight;
    private BlackBishop bBishop;
    private BlackKing bKing;
    private BlackQueen bQueen;
    //WHITE
    private WhitePawn wPawn;
    private WhiteRook wRook;
    private WhiteKnight wKnight;
    private WhiteBishop wBishop;
    private WhiteKing wKing;
    private WhiteQueen wQueen;

    private int turn; // White = 1, Black = -1
    private int[] occupation;
    private boolean occSet;
    private int xMouse; // x coord square mouse
    private int yMouse; // y coord square mouse
    private boolean selection;
    private int xTemp;
    private int yTemp;
    private int pieceTemp;
    private boolean badSelection;
    private boolean kill;
    private boolean killSet;
    private boolean game;
    public static void main(String[] args) {
        PApplet.main(new String[] {"Chess"});
    }

    public void setup() {
        size(1300, 800);
        board = loadImage("board.jpg");
        //BLACK
        bPawn = new BlackPawn(this);
        bRook = new BlackRook(this);
        bKnight = new BlackKnight(this);
        bBishop = new BlackBishop(this);
        bKing = new BlackKing(this);
        bQueen = new BlackQueen(this);
        //WHITE
        wPawn = new WhitePawn(this);
        wRook = new WhiteRook(this);
        wKnight = new WhiteKnight(this);
        wBishop = new WhiteBishop(this);
        wKing = new WhiteKing(this);
        wQueen = new WhiteQueen(this);

        turn = 1;
        occupation = new int[65];
        occSet = false;
        xMouse = 0;
        yMouse = 0;
    }

    public void settings() {
        size(1300, 800);
        board = loadImage("board.jpg");
        //BLACK
        bPawn = new BlackPawn(this);
        bRook = new BlackRook(this);
        bKnight = new BlackKnight(this);
        bBishop = new BlackBishop(this);
        bKing = new BlackKing(this);
        bQueen = new BlackQueen(this);
        //WHITE
        wPawn = new WhitePawn(this);
        wRook = new WhiteRook(this);
        wKnight = new WhiteKnight(this);
        wBishop = new WhiteBishop(this);
        wKing = new WhiteKing(this);
        wQueen = new WhiteQueen(this);

        turn = 1;
        occupation = new int[65];
        occSet = false;
        xMouse = 0;
        yMouse = 0;
    }

    public void draw() {
        if (!selection) {
            background(0);
            xTemp = 1;
            yTemp = 1;
        }
        image(board, 0, 0);
        if (!game) {
            menu();
        }
        else if (game) {
            fill(255);
            stroke(255);
            rect(803, 400, 500, 400);
            //BLACK
            bPawn.display();
            bRook.display();
            bKnight.display();
            bBishop.display();
            bKing.display();
            bQueen.display();
            //WHITE
            wPawn.display();
            wRook.display();
            wKnight.display();
            wBishop.display();
            wKing.display();
            wQueen.display();

            displayTurn();
            updateOccupation();
            mouseSquare();

            if (selection) {
                displaySelection();
            }
        }
    }

    public void menu() {
        textSize(128);
        text("Chess", 867, 300);
        textSize(64);
        if (mouseY > 447 && mouseY < 505 && mouseX > 980 && mouseX < 1132) {
            textSize(74);
            fill(125);
            if (mousePressed) {
                game = true;
            }
        }
        text("Start", 983, 500);
        fill(255);
    }

    public void displayTurn() {
        textSize(64);
        if (turn == -1){
            fill(255);
            text("Your Turn", 880, 200);
        }
        else if (turn == 1) {
            fill(0);
            text("Your Turn", 880, 600);
        }
    }

    //Square selection
    public void mouseClicked() {
        if (game) {
            //Deselection
            if (xTemp == xMouse && yTemp == yMouse) {
                selection = false;
            }
            //Selection
            else if (!checkValidMove() && mouseX < 800) {
                selection = true;
                xTemp = xMouse;
                yTemp = yMouse;
            }
            else if (checkValidMove()) {
                //White
                //Pawn
                if (xTemp == wPawn.getX(pieceTemp) && yTemp == wPawn.getY(pieceTemp)) {
                    wPawn.move(pieceTemp, xMouse, yMouse);
                    //System.out.println("wPawn's kill: " + kill);
                    if (kill) {
                        killed();
                    }
                }

                //Black
                //Pawn
                else if (xTemp == bPawn.getX(pieceTemp) && yTemp == bPawn.getY(pieceTemp)) {
                    bPawn.move(pieceTemp, xMouse, yMouse);
                    if (kill) {
                        killed();
                    }
                }
                selection = false;
                turn *= -1;
            }
            //             else if (mouseX < 800 && !badSelection) {
            //                 selection = true;
            //                 xTemp = xMouse;
            //                 yTemp = yMouse;
            //             }
        }

    }

    public void displaySelection() {
        if (xTemp != 0 && xTemp < 9) {
            rect(71 + (xTemp - 1) * 84, 76 + (yTemp - 1) * 83, 72, 72);
        }
    }

    public void mouseSquare() {
        if (mouseX > 71 && mouseX < 743 && mouseY > 76 && mouseY < 740) {
            if (mouseX > 71 && mouseX < 155) {
                xMouse = 1;
            }
            if (mouseX > 155 && mouseX < 239) {
                xMouse = 2;
            }
            if (mouseX > 239 && mouseX < 323) {
                xMouse = 3;
            }
            if (mouseX > 323 && mouseX < 407) {
                xMouse = 4;
            }
            if (mouseX > 407 && mouseX < 491) {
                xMouse = 5;
            }
            if (mouseX > 491 && mouseX < 575) {
                xMouse = 6;
            }
            if (mouseX > 575 && mouseX < 659) {
                xMouse = 7;
            }
            if (mouseX > 659 && mouseX < 743) {
                xMouse = 8;
            }

            if (mouseY > 76 && mouseY < 159) {
                yMouse = 1;
            }
            if (mouseY > 159 && mouseY < 242) {
                yMouse = 2;
            }
            if (mouseY > 242 && mouseY < 325) {
                yMouse = 3;
            }
            if (mouseY > 325 && mouseY < 408) {
                yMouse = 4;
            }
            if (mouseY > 408 && mouseY < 491) {
                yMouse = 5;
            }
            if (mouseY > 491 && mouseY < 574) {
                yMouse = 6;
            }
            if (mouseY > 574 && mouseY < 657) {
                yMouse = 7;
            }
            if (mouseY > 657 && mouseY < 740) {
                yMouse = 8;
            }
        }

        fill(255, 0); // empty middle
        //System.out.println("xTemp: " + xTemp);
        //System.out.println("yTemp: " + yTemp);
        //System.out.println("temp: " + (xTemp + (yTemp - 1) * 8));
        //System.out.println("xMouse: " + xMouse);
        //System.out.println("yMouse: " + yMouse);
        //System.out.println("selection: " + selection);
        //System.out.println("turn: " + turn);
        if (xMouse + (yMouse - 1) * 8 > 0) {
            System.out.println("occupation: " + occupation[xMouse + (yMouse - 1) * 8]);
        }

        badSelection = false;
        //System.out.println("occupation: " + (xMouse + ((yMouse - 1) * 8)));
        //         if (mousePressed && !selection && mouseX > 71 && mouseX < 743 && mouseY > 76 && mouseY < 740 && (turn == 1 && occupation[xMouse + (yMouse - 1) * 8] == 1) || (turn == -1 && occupation[xMouse + (yMouse - 1) * 8] == 2)) { // Picking up wrong piece
        //             stroke(255, 0, 0);
        //             badSelection = true;
        //         }

        // Hovering over right piece
        if (selection && ((occupation[xTemp + (yTemp - 1) * 8] == 2 && turn == 1) || (occupation[xTemp + (yTemp - 1) * 8] == 1 && turn == -1))) { 
            stroke(125);
            //System.out.println("tempOcc: " + occupation[xTemp + (yTemp - 1) * 8]);
        }
        else {
            stroke(255);
            selection = false;
        }

        // Hovering over wrong piece
        if (!selection && mouseX > 71 && mouseX < 743 && mouseY > 76 && mouseY < 740 && (turn == 1 && occupation[xMouse + (yMouse - 1) * 8] == 1) || (turn == -1 && occupation[xMouse + (yMouse - 1) * 8] == 2)) {
            stroke(255, 0, 0);
        }

        if (checkValidMove()) {
            stroke(0, 255, 0);
        }
        strokeWeight(8);
        //Hovering Square
        //if (xMouse != 0) {
        if (selection && checkOccupation() && !checkValidMove()) {
            // Override when selection
        }
        else if (checkOccupation() || checkValidMove()) {
            rect(71 + (xMouse - 1) * 84, 76 + (yMouse - 1) * 83, 72, 72); // Draw hollow Square
        }
        //}
    }

    public void updateOccupation() {
        for (int count = 1; count < 65; count++) {
            // Black Occupation
            // Pawn
            for (int count2 = 1; count2 < 9; count2++) {
                if (count == bPawn.getX(count2) + (bPawn.getY(count2) - 1) * 8) {
                    occupation[count] = 1;
                    occSet = true;
                }
            }
            //Rook
            for (int count3 = 1; count3 < 3; count3++) {
                if (count == bRook.getX(count3) + (bRook.getY(count3) - 1) * 8) {
                    occupation[count] = 1;
                    occSet = true;
                }
            }
            //Knight
            for (int count4 = 1; count4 < 3; count4++) {
                if (count == bKnight.getX(count4) + (bKnight.getY(count4) - 1) * 8) {
                    occupation[count] = 1;
                    occSet = true;
                }
            }
            //Bishop
            for (int count5 = 1; count5 < 3; count5++) {
                if (count == bBishop.getX(count5) + (bBishop.getY(count5) - 1) * 8) {
                    occupation[count] = 1;
                    occSet = true;
                }
            }
            //King
            if (count == bKing.getX() + (bKing.getY() - 1) * 8) {
                occupation[count] = 1;
                occSet = true;
            }
            //Queen
            if (count == bQueen.getX() + (bQueen.getY() - 1) * 8) {
                occupation[count] = 1;
                occSet = true;
            }

            //White Occupation
            //Pawn
            for (int count6 = 1; count6 < 9; count6++) {
                if (count == wPawn.getX(count6) + ((wPawn.getY(count6)  - 1) * 8)) {
                    occupation[count] = 2;
                    occSet = true;
                }
            }
            //Rook
            for (int count7 = 1; count7 < 3; count7++) {
                if (count == wRook.getX(count7) + (wRook.getY(count7) - 1) * 8) {
                    occupation[count] = 2;
                    occSet = true;
                }
            }
            //Knight
            for (int count8 = 1; count8 < 3; count8++) {
                if (count == wKnight.getX(count8) + (wKnight.getY(count8) - 1) * 8) {
                    occupation[count] = 2;
                    occSet = true;
                }
            }
            //Bishop
            for (int count9 = 1; count9 < 3; count9++) {
                if (count == wBishop.getX(count9) + (wBishop.getY(count9) - 1) * 8) {
                    occupation[count] = 2;
                    occSet = true;
                }
            }
            //King
            if (count == wKing.getX() + (wKing.getY() - 1) * 8) {
                occupation[count] = 2;
                occSet = true;
            }
            //Queen
            if (count == wQueen.getX() + (wQueen.getY() - 1) * 8) {
                occupation[count] = 2;
                occSet = true;
            }

            if (!occSet) {
                occupation[count] = 0;
            }
            occSet = false;
        }
        //Updating other stuff

        //         debug        
        //         System.out.println("pawn 4: " + (bPawn.getX(4) + ((bPawn.getY(4) - 1) * 8))); // 12
        //         System.out.println("pawn 8: " + (bPawn.getX(8) + ((bPawn.getY(8) - 1) * 8))); // 16
        //         System.out.println("occupation[12]: " + occupation[12]);
        //         System.out.println("occupation[2]: " + occupation[2]);
        //         System.out.println("occupation[8]: " + occupation[8]);
        //         System.out.println("occupation[9]: " + occupation[9]);
        //         System.out.println("occupation[10]: " + occupation[10]);
        //         System.out.println("occupation[16]: " + occupation[16]);
    }

    // If a piece is there
    public boolean checkOccupation() {
        if (xMouse != 0 && yMouse != 0) {
            if (occupation[xMouse + ((yMouse - 1) * 8)] != 0) {
                return true;
            }
        }
        return false;
        // Black Occupation
        // Pawn
        //         for (int count2 = 1; count2 < 9; count2++) {
        //             if (xMouse == bPawn.getX(count2) && yMouse == bPawn.getY(count2)) {
        //                 return true;
        //             }
        //         }
        //         //Rook
        //         for (int count3 = 1; count3 < 3; count3++) {
        //             if (xMouse == bRook.getX(count3) && yMouse == bRook.getY(count3)) {
        //                 return true;
        //             }
        //         }
        //         //Knight
        //         for (int count4 = 1; count4 < 3; count4++) {
        //             if (xMouse == bKnight.getX(count4) && yMouse == bKnight.getY(count4)) {
        //                 return true;
        //             }
        //         }
        //         //Bishop
        //         for (int count5 = 1; count5 < 3; count5++) {
        //             if (xMouse == bBishop.getX(count5) && yMouse == bBishop.getY(count5)) {
        //                 return true;
        //             }
        //         }
        //         //King
        //         if (xMouse == bKing.getX() && yMouse == bKing.getY()) {
        //             return true;
        //         }
        //         //Queen
        //         if (xMouse == bQueen.getX() && yMouse == bQueen.getY()) {
        //             return true;
        //         }
        // 
        //         // White Occupation
        //         //Pawn
        //         for (int count6 = 1; count6 < 9; count6++) {
        //             if (xMouse == wPawn.getX(count6) && yMouse == wPawn.getY(count6)) {
        //                 return true;
        //             }
        //         }
        //         //Rook
        //         for (int count7 = 1; count7 < 3; count7++) {
        //             if (xMouse == wRook.getX(count7) && yMouse == wRook.getY(count7)) {
        //                 return true;
        //             }
        //         }
        //         //Knight
        //         for (int count8 = 1; count8 < 3; count8++) {
        //             if (xMouse == wKnight.getX(count8) && yMouse == wKnight.getY(count8)) {
        //                 return true;
        //             }
        //         }
        //         //Bishop
        //         for (int count9 = 1; count9 < 3; count9++) {
        //             if (xMouse == wBishop.getX(count9) && yMouse == wBishop.getY(count9)) {
        //                 return true;
        //             }
        //         }
        //         //King
        //         if (xMouse == wKing.getX() && yMouse == wKing.getY()) {
        //             return true;
        //         }
        //         //Queen
        //         if (xMouse == wQueen.getX() && yMouse == wQueen.getY()) {
        //             return true;
        //         }
        //         return false;
    }

    public boolean checkValidMove() {
        //White
        //Pawn
        for (int count = 1; count < 9; count++) {
            if (xTemp == wPawn.getX(count) && yTemp == wPawn.getY(count)) {
                //First double step
                if (wPawn.getY(count) == 7) {
                    if (yMouse == yTemp - 2 && xMouse == xTemp) {
                        pieceTemp = count;
                        return true;
                    }
                }
                //Single forward step
                if (yMouse == yTemp - 1 && xMouse == xTemp) {
                    pieceTemp = count;
                    return true;
                }
                //To Kill a Chess Piece
                if ((xMouse == xTemp - 1 || xMouse == xTemp + 1) && yMouse == yTemp - 1) { // if mouse is in position
                    if (occupation[(xMouse + (yMouse - 1) * 8)] == 1) {
                        kill = true;
                        killSet = true;
                        return true;
                    }
                } 
            }
        }

        //Black
        //Pawn
        for (int count6 = 1; count6 < 9; count6++) {
            if (xTemp == bPawn.getX(count6) && yTemp == bPawn.getY(count6)) {
                //First double step
                if (bPawn.getY(count6) == 2) {
                    if (yMouse == yTemp + 2 && xMouse == xTemp) {
                        pieceTemp = count6;
                        return true;
                    }
                }
                //Single forward step
                if (yMouse == yTemp + 1 && xMouse == xTemp) {
                    pieceTemp = count6;
                    return true;
                }
                //To Kill a Chess Piece
                if ((xMouse == xTemp - 1 || xMouse == xTemp + 1) && yMouse == yTemp + 1) { // if mouse is in position
                    if (occupation[(xMouse + (yMouse - 1) * 8)] == 2) {
                        kill = true;
                        killSet = true;
                        return true;
                    }
                }
            }
        }

        if (!killSet) {
            kill = false;
        }
        killSet = false;
        return false;
    }

    public void killed() {
        //System.out.println("killed(): run");
        //White
        //Pawn
        for (int count = 1; count < 9; count++) {
            if (xMouse == wPawn.getX(count) && yMouse == wPawn.getY(count) && turn == -1) {
                wPawn.die(count);
                //System.out.println("wPawn " + count + " died");
            }
        }

        //Black
        //Pawn
        for (int count6 = 1; count6 < 9; count6++) {
            if (xMouse == bPawn.getX(count6) && yMouse == bPawn.getY(count6) && turn == 1) {
                bPawn.die(count6);
                //System.out.println("bPawn " + count6 + " died");
            }
        }
    }
}
