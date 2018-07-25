package lightshot;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 *  Объект класса {@code ImagePanel} является панелью с изображением,
 *  которая должна быть фоном для заданного окна (фрейма).
 */
public class ImagePanel extends JPanel {

    private BufferedImage bufferedImage;

    private int POS_X_Border;
    private int POS_Y_Border;
    private int WidthBorder;
    private int HeightBorder;

    ImagePanel(BufferedImage bufferedImage){
        this.bufferedImage = bufferedImage;

        setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bufferedImage, 0,0,null);
        drawRect(g, POS_X_Border, POS_Y_Border, WidthBorder, HeightBorder);
    }

    private void drawRect(Graphics g, int xRect, int yRect, int widthRect, int heightRect){
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setPaint(Color.blue);

        graphics2D.drawRect(xRect, yRect, widthRect, heightRect);
    }

    public void setRectLocation(int xRect, int yRect){

        this.POS_X_Border = xRect;
        this.POS_Y_Border = yRect;

        repaint();
    }

    public void setRectSize(int widthRect, int heightRect){

        this.WidthBorder = widthRect;
        this.HeightBorder = heightRect;

    }
}
