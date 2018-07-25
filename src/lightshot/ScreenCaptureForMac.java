package lightshot;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ScreenCaptureForMac extends Window {

    private static int currentCursorPositionX = 0;
    private static int currentCursorPositionY = 0;

    private static int finalCursorPositionX = 0;
    private static int finalCursorPositionY = 0;

    private static int screenWindowWidth = 0;
    private static int screenWindowHeight = 0;

    private ImagePanel imagePanel;

    private BtnExit btnExit = new BtnExit("exit");
    private BtnSave btnSave = new BtnSave("save");

    private MainWindow mainWindow;

    ScreenCaptureForMac(MainWindow mainWindow){

        super(new JFrame());

        this.mainWindow = mainWindow;

        mainWindow.setState(JFrame.ICONIFIED);


        imagePanel = new ImagePanel(ScreenShotAlgorithm.getScreen());

        GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        graphicsDevice.setFullScreenWindow(this);

        add(imagePanel);

        setVisible(true);


        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                currentCursorPositionX = e.getX();
                currentCursorPositionY = e.getY();

                imagePanel.setRectLocation(currentCursorPositionX, currentCursorPositionY);
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);

                //first_quarter
                if( currentCursorPositionX < e.getX() && currentCursorPositionY > e.getY() ){

                    screenWindowWidth = e.getX() - currentCursorPositionX;
                    screenWindowHeight = currentCursorPositionY - e.getY();

                    finalCursorPositionX = currentCursorPositionX;
                    finalCursorPositionY = e.getY();

                    imagePanel.setRectLocation(finalCursorPositionX, finalCursorPositionY);
                    imagePanel.setRectSize(screenWindowWidth, screenWindowHeight);

                }

                //second_quarter
                else if( currentCursorPositionX > e.getX() && currentCursorPositionY > e.getY() ){

                    screenWindowWidth = currentCursorPositionX - e.getX();
                    screenWindowHeight = currentCursorPositionY - e.getY();

                    finalCursorPositionX = e.getX();
                    finalCursorPositionY = e.getY();

                    imagePanel.setRectLocation(finalCursorPositionX, finalCursorPositionY);
                    imagePanel.setRectSize(screenWindowWidth, screenWindowHeight);

                }

                //third_quarter
                else if( currentCursorPositionX > e.getX() && currentCursorPositionY < e.getY() ){

                    screenWindowWidth = currentCursorPositionX - e.getX();
                    screenWindowHeight = e.getY() - currentCursorPositionY;

                    finalCursorPositionX = e.getX();
                    finalCursorPositionY = currentCursorPositionY;

                    imagePanel.setRectLocation(finalCursorPositionX, finalCursorPositionY);
                    imagePanel.setRectSize(screenWindowWidth, screenWindowHeight);


                }

                //fourth_quarter
                else if( currentCursorPositionX < e.getX() && currentCursorPositionY < e.getY() ){

                    screenWindowWidth = e.getX() - currentCursorPositionX;
                    screenWindowHeight = e.getY()- currentCursorPositionY;

                    finalCursorPositionX = currentCursorPositionX;
                    finalCursorPositionY = currentCursorPositionY;

                    imagePanel.setRectLocation(finalCursorPositionX, finalCursorPositionY);
                    imagePanel.setRectSize(screenWindowWidth, screenWindowHeight);

                }
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                Rectangle rectangle = new Rectangle();

                if( currentCursorPositionX < e.getX() && currentCursorPositionY > e.getY() ){

                    screenWindowWidth = e.getX() - currentCursorPositionX;
                    screenWindowHeight = currentCursorPositionY - e.getY();

                    finalCursorPositionX = currentCursorPositionX;
                    finalCursorPositionY = e.getY();

                    rectangle.setLocation(finalCursorPositionX + 1,finalCursorPositionY+1);
                    rectangle.setSize(screenWindowWidth-2,screenWindowHeight-1);

                }

                //2+
                else if( currentCursorPositionX > e.getX() && currentCursorPositionY > e.getY() ){

                    screenWindowWidth = currentCursorPositionX - e.getX();
                    screenWindowHeight = currentCursorPositionY - e.getY();

                    finalCursorPositionX = e.getX();
                    finalCursorPositionY = e.getY();

                    rectangle.setLocation(finalCursorPositionX+1,finalCursorPositionY+1);
                    rectangle.setSize(screenWindowWidth-2,screenWindowHeight-1);

                }

                //3+
                else if( currentCursorPositionX > e.getX() && currentCursorPositionY < e.getY() ){

                    screenWindowWidth = currentCursorPositionX - e.getX();
                    screenWindowHeight = e.getY() - currentCursorPositionY;

                    finalCursorPositionX = e.getX();
                    finalCursorPositionY = currentCursorPositionY;

                    rectangle.setLocation(finalCursorPositionX+1,finalCursorPositionY+1);
                    rectangle.setSize(screenWindowWidth-2,screenWindowHeight-1);

                }

                //4+
                else if( currentCursorPositionX < e.getX() && currentCursorPositionY < e.getY() ){

                    screenWindowWidth = e.getX() - currentCursorPositionX;
                    screenWindowHeight = e.getY()- currentCursorPositionY;

                    finalCursorPositionX = currentCursorPositionX;
                    finalCursorPositionY = currentCursorPositionY;

                    rectangle.setLocation(finalCursorPositionX+1,finalCursorPositionY+1);
                    rectangle.setSize(screenWindowWidth-2,screenWindowHeight-1);

                }

                btnSave.setBufferedImage2(rectangle);

                btnExit.setBounds(currentCursorPositionX + (e.getX() - currentCursorPositionX)/2 - 40,
                        currentCursorPositionY + (e.getY() - currentCursorPositionY)/2 - 15,
                        80,30);

                btnSave.setBounds(currentCursorPositionX + (e.getX() - currentCursorPositionX)/2 - 40,
                        currentCursorPositionY + (e.getY() - currentCursorPositionY)/2 - 45,
                        80,30);

                imagePanel.add(btnExit);
                imagePanel.add(btnSave);

                imagePanel.repaint();
            }
        });
    }



    private class BtnSave extends JButton{

        private BufferedImage bufferedImage2 = null;

        void setBufferedImage2(Rectangle rectangle){
            bufferedImage2 = ScreenShotAlgorithm.getScreen(rectangle);
        }

        BtnSave(String text){

            setText(text);
            setBackground(Color.RED);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    super.mouseReleased(e);

                    mainWindow.setState(JFrame.NORMAL);

                    dispose();

                    FileDialog fileDialog = new FileDialog(new Frame(), "сохранить картинку", FileDialog.SAVE);
                    fileDialog.setVisible(true);

                    try {
                        ImageIO.write(bufferedImage2,"png",
                                new File(fileDialog.getDirectory(), fileDialog.getFile() + ".png"));
                    }catch (IOException exp){
                        System.out.println("IOException " + exp);
                    }
                }
            });
        }
    }


    private class BtnExit extends JButton{
        BtnExit(String text){
            setText(text);

            setBackground(Color.red);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    super.mouseReleased(e);

                    dispose();

                    mainWindow.setState(Frame.NORMAL);
                }
            });
        }
    }
}
