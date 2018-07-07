package lightshot;

import javax.swing.*;
import java.awt.*;

public class ScreenCaptureWindow_2 extends Window {

    ScreenCaptureWindow_2(MainWindow mainWindow){
        super(new JFrame());

        mainWindow.setState(JFrame.ICONIFIED);


        ImagePanel imagePanel = new ImagePanel(ScreenShotAlgorithm.getScreen());
        imagePanel.repaint();


        add(imagePanel);

        setVisible(true);

        GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        graphicsDevice.setFullScreenWindow(this);



    }
}
