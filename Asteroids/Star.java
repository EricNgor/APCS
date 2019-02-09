import processing.core.PApplet;
public class Star {
    private PApplet applet;
    private int x; private int y;
    public Star(PApplet applet) {
        this.applet = applet;
        x = (int)(Math.random() * applet.width);
        y = (int)(Math.random() * applet.height);
    }

    public void show() {
        applet.fill(255);
        applet.noStroke();
        applet.ellipse(x, y, 3, 3);
    }
}