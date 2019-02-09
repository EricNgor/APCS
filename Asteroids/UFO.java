import processing.core.PApplet;
public class UFO extends Floater {
    public UFO(PApplet applet) {
        super(applet);
        myColor = 255;
        corners = 12;
        xCorners = new int[corners];
        yCorners = new int[corners];
        xCorners[0] = -20; xCorners[1] = -10; xCorners[2] = 10; xCorners[3] = 20; xCorners[4] = -20; xCorners[5] = -8; xCorners[6] = -4; xCorners[7] = 4; xCorners[8] = 8; xCorners[9] = -8; xCorners[10] = 8; xCorners[11] = 20;
        yCorners[0] = -4; yCorners[1] = -10; yCorners[2] = -10; yCorners[3] = -4; yCorners[4] = -4; yCorners[5] = 4; yCorners[6] = 10; yCorners[7] = 10; yCorners[8] = 4; yCorners[9] = 4; yCorners[10] = 4; yCorners[11] = -4; 
        setX(applet.width / 2);
        setY(applet.height / 2);
        setPointDirection(180);
    }

    @Override
    public void show ()  //Draws the floater at the current position  
    {         
        applet.noFill();
        applet.stroke(myColor);    

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