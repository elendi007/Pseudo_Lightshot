package lightshot;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ScreenShotAlgorithm {

    public static BufferedImage getScreen() {

        try {
            return new Robot().createScreenCapture(
                    new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));

        } catch (SecurityException | AWTException ignored) {
        }

        return null;
    }

    public static BufferedImage getScreenCapture(int posX, int posY, int width, int height){

        try {
            Robot robot = new Robot();
            Rectangle rectangle = new Rectangle(posX, posY, width, height);

            BufferedImage image = robot.createScreenCapture(rectangle);

            return image;

        }catch (SecurityException ignored){

        } catch (AWTException e) {
            e.printStackTrace();
        }

        return null;
    }
}