import processing.core.PApplet;
public class Asteroids extends Floater{
    private String sizeType;
    private int size;
    private int rotationSpeed;
    public Asteroids(PApplet applet, String sizeType, int xGrave, int yGrave) {
        super(applet);
        this.sizeType = sizeType;
        myColor = 170;
        corners = 9;
        xCorners = new int[corners];
        yCorners = new int[corners];

        int asteroidShape = (int)(Math.random() * 2) + 1;
        switch(sizeType) {
            case "big":
            if (asteroidShape == 1) {
                xCorners[0] = 0; xCorners[1] = 35; xCorners[2] = 45; xCorners[3] = 40; xCorners[4] = 15; xCorners[5] = -20; xCorners[6] = -40; xCorners[7] = -30; xCorners[8] = -40;
                yCorners[0] = -40; yCorners[1] = -30; yCorners[2] = -5; yCorners[3] = 35; yCorners[4] = 30; yCorners[5] = 40; yCorners[6] = 20; yCorners[7] = -5; yCorners[8] = -30;
            }
            else if (asteroidShape == 2) {
                xCorners[0] = 0; xCorners[1] = 30; xCorners[2] = 45; xCorners[3] = 20; xCorners[4] = 35; xCorners[5] = 0; xCorners[6] = -15; xCorners[7] = -40; xCorners[8] = -30;
                yCorners[0] = -35; yCorners[1] = -25; yCorners[2] = 5; yCorners[3] = 15; yCorners[4] = 40; yCorners[5] = 45; yCorners[6] = 25; yCorners[7] = 20; yCorners[8] = -20;
            }
            size = 50;
            break;
            case "medium":
            if (asteroidShape == 1) {
                xCorners[0] = 0; xCorners[1] = 18; xCorners[2] = 23; xCorners[3] = 20; xCorners[4] = 8; xCorners[5] = -10; xCorners[6] = -20; xCorners[7] = -15; xCorners[8] = -20;
                yCorners[0] = -20; yCorners[1] = -15; yCorners[2] = -3; yCorners[3] = 18; yCorners[4] = 15; yCorners[5] = 20; yCorners[6] = 10; yCorners[7] = -3; yCorners[8] = -15;
            }
            else if (asteroidShape == 2) {
                xCorners[0] = 0; xCorners[1] = 15; xCorners[2] = 23; xCorners[3] = 10; xCorners[4] = 18; xCorners[5] = 0; xCorners[6] = -8; xCorners[7] = -20; xCorners[8] = -15;
                yCorners[0] = -18; yCorners[1] = -13; yCorners[2] = 3; yCorners[3] = 8; yCorners[4] = 20; yCorners[5] = 23; yCorners[6] = 13; yCorners[7] = 10; yCorners[8] = -10;
            }
            size = 25;
            break;
            case "small":
            if (asteroidShape == 1) {
                xCorners[0] = 0; xCorners[1] = 9; xCorners[2] = 12; xCorners[3] = 10; xCorners[4] = 4; xCorners[5] = -5; xCorners[6] = -10; xCorners[7] = -8; xCorners[8] = -10;
                yCorners[0] = -10; yCorners[1] = -8; yCorners[2] = -2; yCorners[3] = 9; yCorners[4] = 8; yCorners[5] = 10; yCorners[6] = 5; yCorners[7] = -2; yCorners[8] = -8;
            }
            else if (asteroidShape == 2) {
                xCorners[0] = 0; xCorners[1] = 8; xCorners[2] = 12; xCorners[3] = 5; xCorners[4] = 9; xCorners[5] = 0; xCorners[6] = -4; xCorners[7] = -10; xCorners[8] = -8;
                yCorners[0] = -9; yCorners[1] = -7; yCorners[2] = 2; yCorners[3] = 4; yCorners[4] = 10; yCorners[5] = 12; yCorners[6] = 7; yCorners[7] = 5; yCorners[8] = -5;
            }
            size = 13;
            break;
        }

        rotationSpeed = (int)(randomPosNeg() * ((Math.random() * 2) + 2));

        // Initial location 
        switch(sizeType) {
            case "big": setX((int)(Math.random() * applet.width));
            setY((int)(Math.random() * applet.height));
            break;
            case "medium":
            case "small": setX((int)(xGrave + ((Math.random() * 4) - 2)));
            setY((int)(yGrave + ((Math.random() * 4) - 2)));
            break;
        }

        //Velocities
        setDirectionX((randomPosNeg() * (Math.random() + 1)));
        setDirectionY((randomPosNeg() * (Math.random() + 1)));
    }  

    public int getSize() {
        return size;
    }

    public double randomPosNeg() {
        if ((int)(Math.random() * 2) == 0) {
            return 1.0;
        }
        return -1.0;
    }

    @Override
    public void show ()  //Draws the floater at the current position  
    {         
        applet.fill(myColor);   
        applet.strokeWeight(2);
        applet.stroke(255, 0, 0);    

        //convert degrees to radians for sin and cos         
        double dRadians = myPointDirection * (Math.PI / 180);                 
        int xRotatedTranslated, yRotatedTranslated;    
        applet.beginShape();         
        for(int nI = 0; nI < corners; nI++)    
        {     
            //rotate and translate the coordinates of the floater using current direction 
            xRotatedTranslated = (int)((xCorners[nI] * Math.cos(dRadians)) - (yCorners[nI] * Math.sin(dRadians)) + myCenterX);     
            yRotatedTranslated = (int)((xCorners[nI] * Math.sin(dRadians)) + (yCorners[nI] * Math.cos(dRadians)) + myCenterY);      
            applet.vertex(xRotatedTranslated, yRotatedTranslated);    
        }   
        applet.endShape(applet.CLOSE);  
        applet.strokeWeight(1);
    }   

    public int scoreValue() {
        switch(sizeType) {
            case "big": return 100;
            case "medium": return 50;
            case "small": return 25;
            default: return 0;
        }
    }

    @Override
    public void move() {
        super.move();
        rotate(rotationSpeed);
    }

    public void setX(int x) {
        myCenterX = (double)x;
    }

    public int getX() {
        return (int)myCenterX;
    }

    public void setY(int y) {
        myCenterY = (double)y;
    }

    public int getY() {
        return (int)myCenterY;
    }

    public void setDirectionX(double x) {
        myDirectionX = x;
    }

    public double getDirectionX() {
        return myDirectionX;
    }

    public void setDirectionY(double y) {
        myDirectionY = y;
    }

    public double getDirectionY() {
        return myDirectionY;
    }

    public void setPointDirection(int degrees) {
        myPointDirection = degrees;
    }

    public double getPointDirection() {
        return myPointDirection;
    }
}