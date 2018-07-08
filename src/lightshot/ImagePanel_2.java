package lightshot;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagePanel_2 extends JPanel {
    private BufferedImage bufferedImage;

    private int xRect;
    private int yRect;
    private int widthRect;
    private int heightRect;

    ImagePanel_2(BufferedImage bufferedImage){
        this.bufferedImage = bufferedImage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bufferedImage, 0,0,null);
        drawRect(g, xRect, yRect, widthRect, heightRect);
    }

    public void drawScreen(Graphics g){
        g.drawImage(bufferedImage, 0,0, null);
    }

    private void drawRect(Graphics g, int xRect, int yRect, int widthRect, int heightRect){
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setPaint(Color.blue);

        graphics2D.drawRect(xRect, yRect, widthRect, heightRect);
    }

    public void setRectLocation(int xRect, int yRect){
        this.xRect = xRect;
        this.yRect = yRect;

        repaint();
    }

    public void setRectSize(int widthRect, int heightRect){
        this.widthRect = widthRect;
        this.heightRect = heightRect;
    }

    public int getWidthRect(){
        return this.widthRect;
    }

    public int getHeightRect(){
        return this.heightRect;
    }

    public int getxRect(){
        return this.xRect;
    }

    public int getyRect(){
        return this.yRect;
    }
}
