package lightshot;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 *  Объект класса ImagePanel является панелью с изображением,
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

        //отключение компоновщика
        setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //отрисовка заскриненого изображения
        g.drawImage(bufferedImage, 0,0,null);

        //отрисовка прямоугольника, который служит областью для захвата изображения
        drawRect(g, POS_X_Border, POS_Y_Border, WidthBorder, HeightBorder);
    }

    /**
     * @param g объект графики
     * @param xRect начальная позиция фигуры по x
     * @param yRect начальная позиция фигуры по y
     * @param widthRect длина фигуры
     * @param heightRect высота фигуры
     */
    private void drawRect(Graphics g, int xRect, int yRect, int widthRect, int heightRect){
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setPaint(Color.blue);

        graphics2D.drawRect(xRect, yRect, widthRect, heightRect);
    }


    /**
     * @param xRect начальное положение по Х прямоугольника для обрезки изображения
     * @param yRect начальное положение по Y прямоугольника для обрезки изображения
     */
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
