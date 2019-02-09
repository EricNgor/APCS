import processing.core.PApplet;
public class GhostShip extends Floater  
{   
    private String type;
    private PApplet applet;
    private float opacity;
    public GhostShip(PApplet applet, String type) {
        super(applet);
        this.applet = applet;
        myColor = 255;
        opacity = 100f;
        this.type = type;
        if (type.equals("fire")) {
            corners = 7;
        }
        else if (type.equals("hyper") || type.equals("life")) {
            corners = 4;
        }
        xCorners = new int[corners];
        yCorners = new int[corners];
        xCorners[0] = -8; xCorners[1] = 16; xCorners[2] = -8; xCorners[3] = -2;
        yCorners[0] = -8; yCorners[1] = 0; yCorners[2] = 8; yCorners[3] = 0;
        if (type.equals("fire")) {
            xCorners[4] = -4; xCorners[5] = -16; xCorners[6] = -4;
            yCorners[4] = -6; yCorners[5] = 0; yCorners[6] = 6;
        }
        setX(applet.width / 2);
        setY(applet.height / 2);
    }

    @Override
    public void show() {
        if (type.equals("hyper")) {
            applet.fill(myColor, opacity);
            applet.stroke(myColor, opacity);
            //applet.noStroke();
        }
        else if (type.equals("fire")) {
            applet.fill(myColor);   
            applet.stroke(myColor);   
        }
        else if (type.equals("life")) {
            applet.fill(0);
            applet.strokeWeight(3);
            applet.stroke(myColor);
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
        applet.strokeWeight(1);
    }

    public void setOpacity(float val) {
        opacity = val;
    }

    public float getOpacity() {
        return opacity;
    }

    public int getCornerX(int cornerNum) {
        return xCorners[cornerNum];
    }

    public int getCornerY(int cornerNum) {
        return yCorners[cornerNum];
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