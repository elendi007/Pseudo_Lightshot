package lightshot;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainWindow extends JFrame {

    private static final int POS_X = 400;
    private static final int POS_Y = 200;
    private static final int WIDTH = 300;
    private static final int HEIGHT = 300;

    private static BufferedImage bufferedImage = null;

    MainWindow(){

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Lightshot");
        setAlwaysOnTop(false);

        setLayout(new GridLayout(3,1));

        JButton btnScreenCapture = new JButton("Screen capture");
        JButton btnScreenShot = new JButton("New screenshot");
        JButton btnExit = new JButton("Exit");


        btnScreenShot.addActionListener(e ->{

            this.setState(JFrame.ICONIFIED);

            bufferedImage = ScreenShotAlgorithm.getScreen();

            this.setState(JFrame.NORMAL);

            FileDialog fileDialog = new FileDialog(new Frame(), "сохранить картинку", FileDialog.SAVE);
            fileDialog.setVisible(true);

            try {
                ImageIO.write(bufferedImage,"png",
                        new File(fileDialog.getDirectory(), fileDialog.getFile() + ".png"));
            }catch (IOException exp){
                System.out.println("IOException " + exp);
            }

            this.setVisible(true);
        });

        btnScreenCapture.addActionListener(e->{
            this.setState(JFrame.ICONIFIED);
            new ScreenCaptureForMac(this);

        });

        btnExit.addActionListener(e -> System.exit(0));


        add(btnScreenCapture);
        add(btnScreenShot);
        add(btnExit);

        setVisible(true);
    }
}