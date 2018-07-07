package lightshot;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel {

    private BufferedImage bufferedImage;

    ImagePanel(){
        setBackground(new Color(0,0,0,1f));
    }


    ImagePanel(BufferedImage bufferedImage){
        this.bufferedImage = bufferedImage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bufferedImage, 0, 0, null);
    }

}
