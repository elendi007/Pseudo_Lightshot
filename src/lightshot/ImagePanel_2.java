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

    ImagePanel_2(){
    }

    ImagePanel_2(BufferedImage bufferedImage){
        this.bufferedImage = bufferedImage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
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

    public void rect(int xRect, int yRect, int widthRect, int heightRect){
        this.xRect = xRect;
        this.yRect = yRect;
        this.widthRect = widthRect;
        this.heightRect = heightRect;

        repaint();
    }
}
