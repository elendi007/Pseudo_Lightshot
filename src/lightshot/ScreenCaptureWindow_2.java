package lightshot;

import javax.swing.*;
import java.awt.*;

public class ScreenCaptureWindow_2 extends Window {
    ScreenCaptureWindow_2(){
        super(new JFrame());

        setVisible(true);

        

        GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        graphicsDevice.setFullScreenWindow(this);
    }
}
