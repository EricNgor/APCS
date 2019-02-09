import processing.core.PApplet;
public class PowerUp extends Floater {
    private PApplet applet;
    private String type;
    private boolean active;
    private int displayIndex;
    public PowerUp(PApplet applet, String type) {
        super(applet);
        myColor = 255;
        this.applet = applet;
        this.type = type;
        setX((int)(Math.random() * applet.width));
        setY((int)(Math.random() * applet.height));
        active = true;
        switch(type) {
            case "triShot": corners = 3;
            break;
            case "extraLife": corners = 19;
            break;
        }
        xCorners = new int[corners];
        yCorners = new int[corners];
        switch(type) {
            case "triShot": 
            xCorners[0] = 0; xCorners[1] = -10; xCorners[2] = 10;
            yCorners[0] = 10; yCorners[1] = -10; yCorners[2] = -10;
            break;
            case "extraLife":
            xCorners[0] = 0; xCorners[1] = 3; xCorners[2] = 6; xCorners[3] = 9; xCorners[4] = 10; xCorners[5] = 8; xCorners[6] = 6; xCorners[7] = 5; xCorners[8] = 3; xCorners[9] = 0; 
            yCorners[0] = -10; yCorners[1] = -8; yCorners[2] = -5; yCorners[3] = 0; yCorners[4] = 5; yCorners[5] = 9; yCorners[6] = 10; yCorners[7] = 10; yCorners[8] = 9; yCorners[9] = 5; 
            xCorners[10] = 0; xCorners[11] = -3; xCorners[12] = -5; xCorners[13] = -6; xCorners[14] = -8; xCorners[15] = -10; xCorners[16] = -9; xCorners[17] = -6; xCorners[18] = -3;
            yCorners[10] = 5; yCorners[11] = 9; yCorners[12] = 10; yCorners[13] = 10; yCorners[14] = 9; yCorners[15] = 5; yCorners[16] = 0; yCorners[17] = -5; yCorners[18] = -8;
            break;
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActivity(boolean isActive) {
        active = isActive;
    }

    public void toSideDisplay() {
        setX(applet.width - 18);
        setY((30 * displayIndex) - 15);
        setPointDirection(180);
    }

    public void setDisplayIndex(int index) {
        displayIndex = index;
    }

    public boolean collision(SpaceShip ship) {
        if (applet.dist(getX(), getY(), ship.getX(), ship.getY()) < 25) {
            return true;
        }
        return false;
    }

    @Override
    public void show ()  //Draws the floater at the current position  
    {         
        //applet.fill(myColor);   
        applet.noFill();
        applet.strokeWeight(3);
        //http://www.rapidtables.com/web/color/RGB_Color.htm
        switch(type) {
            case "triShot": applet.stroke(0, 170, 170); 
            break;
            case "extraLife": applet.stroke(255, 101, 189);
        }

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
        applet.stroke(myColor);
        applet.strokeWeight(1);
        if (active) {
            rotate(4);
        }
    }   

    public String getType() {
        return type;
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