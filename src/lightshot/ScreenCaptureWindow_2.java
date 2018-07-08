package lightshot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ScreenCaptureWindow_2 extends Window {

    private static int currentCursorPositionX = 0;
    private static int currentCursorPositionY = 0;

    private static int finalCursorPositionX = 0;
    private static int finalCursorPositionY = 0;

    private static int screenWindowWidth = 0;
    private static int screenWindowHeight = 0;

    private ImagePanel imagePanel = new ImagePanel();
    private ImagePanel_2 imagePanel_2;

    ScreenCaptureWindow_2(MainWindow mainWindow){
        super(new JFrame());

        mainWindow.setState(JFrame.ICONIFIED);


        imagePanel_2 = new ImagePanel_2(ScreenShotAlgorithm.getScreen());

        add(imagePanel_2);

        setVisible(true);

        GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        graphicsDevice.setFullScreenWindow(this);



        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                currentCursorPositionX = e.getX();
                currentCursorPositionY = e.getY();

                imagePanel_2.setRectLocation(currentCursorPositionX, currentCursorPositionY);
            }
        });



        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);

                //1
                if(currentCursorPositionX < e.getX() && currentCursorPositionY > e.getY()){
                    screenWindowWidth = e.getX() - currentCursorPositionX;
                    screenWindowHeight = currentCursorPositionY - e.getY();

                    finalCursorPositionX = currentCursorPositionX;
                    finalCursorPositionY = e.getY();

                    imagePanel_2.setRectLocation(finalCursorPositionX, finalCursorPositionY);
                    imagePanel_2.setRectSize(screenWindowWidth, screenWindowHeight);

                }

                //2+
                else if(currentCursorPositionX > e.getX() && currentCursorPositionY > e.getY()){
                    screenWindowWidth = currentCursorPositionX - e.getX();
                    screenWindowHeight = currentCursorPositionY - e.getY();

                    finalCursorPositionX = e.getX();
                    finalCursorPositionY = e.getY();

                    imagePanel_2.setRectLocation(finalCursorPositionX, finalCursorPositionY);
                    imagePanel_2.setRectSize(screenWindowWidth, screenWindowHeight);

                }

                //3+
                else if(currentCursorPositionX > e.getX() && currentCursorPositionY < e.getY()){
                    screenWindowWidth = currentCursorPositionX - e.getX();
                    screenWindowHeight = e.getY() - currentCursorPositionY;

                    finalCursorPositionX = e.getX();
                    finalCursorPositionY = currentCursorPositionY;

                    imagePanel_2.setRectLocation(finalCursorPositionX, finalCursorPositionY);
                    imagePanel_2.setRectSize(screenWindowWidth, screenWindowHeight);

                }

                //4+
                else if(currentCursorPositionX < e.getX() && currentCursorPositionY < e.getY()){
                    screenWindowWidth = e.getX() - currentCursorPositionX;
                    screenWindowHeight = e.getY()- currentCursorPositionY;

                    finalCursorPositionX = currentCursorPositionX;
                    finalCursorPositionY = currentCursorPositionY;

                    imagePanel_2.setRectLocation(finalCursorPositionX, finalCursorPositionY);
                    imagePanel_2.setRectSize(screenWindowWidth, screenWindowHeight);

                }
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

//                btnSave.setBounds(imagePanel.getWidth()/2 - 40, imagePanel.getHeight()/2 - 30, 80,30);
//                btnExit.setBounds(imagePanel.getWidth()/2 - 40, imagePanel.getHeight()/2, 80,30);
//
//                imagePanel.add(btnSave);
//                imagePanel.add(btnExit);

                imagePanel_2.repaint();
            }
        });
    }
}
