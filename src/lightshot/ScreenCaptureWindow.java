package lightshot;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ScreenCaptureWindow extends JFrame {

    private static int currentCursorPositionX = 0;
    private static int currentCursorPositionY = 0;

    private static int finalCursorPositionX = 0;
    private static int finalCursorPositionY = 0;

    private static int screenWindowWidth = 0;
    private static int screenWindowHeight = 0;

    private static ImagePanelWindows imagePanelWindows = new ImagePanelWindows();

    private BtnSave btnSave = new BtnSave("Save");
    private BtnExit btnExit = new BtnExit("Exit");

    private MainWindow mainWindow = null;

    ScreenCaptureWindow(MainWindow mainWindow){

        this.mainWindow = mainWindow;

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        double HEIGHT = dimension.getHeight();
        double WIDTH = dimension.getWidth();
        setBounds(0,0,(int)WIDTH,(int)HEIGHT);

        setUndecorated(true);

        setOpacity(0.5f);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                currentCursorPositionX = e.getX();
                currentCursorPositionY = e.getY();

                imagePanelWindows.setLocation(e.getX(), e.getY());
                add(imagePanelWindows);

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

                    imagePanelWindows.setLocation(finalCursorPositionX, finalCursorPositionY);
                    imagePanelWindows.setSize(screenWindowWidth, screenWindowHeight);
                }

                //2+
                else if(currentCursorPositionX > e.getX() && currentCursorPositionY > e.getY()){
                    screenWindowWidth = currentCursorPositionX - e.getX();
                    screenWindowHeight = currentCursorPositionY - e.getY();

                    finalCursorPositionX = e.getX();
                    finalCursorPositionY = e.getY();

                    imagePanelWindows.setLocation(finalCursorPositionX, finalCursorPositionY);
                    imagePanelWindows.setSize(screenWindowWidth, screenWindowHeight);
                }

                //3+
                else if(currentCursorPositionX > e.getX() && currentCursorPositionY < e.getY()){
                    screenWindowWidth = currentCursorPositionX - e.getX();
                    screenWindowHeight = e.getY() - currentCursorPositionY;

                    finalCursorPositionX = e.getX();
                    finalCursorPositionY = currentCursorPositionY;

                    imagePanelWindows.setLocation(finalCursorPositionX, finalCursorPositionY);
                    imagePanelWindows.setSize(screenWindowWidth, screenWindowHeight);
                }

                //4+
                else if(currentCursorPositionX < e.getX() && currentCursorPositionY < e.getY()){
                    screenWindowWidth = e.getX() - currentCursorPositionX;
                    screenWindowHeight = e.getY()- currentCursorPositionY;

                    finalCursorPositionX = currentCursorPositionX;
                    finalCursorPositionY = currentCursorPositionY;

                    imagePanelWindows.setLocation(finalCursorPositionX, finalCursorPositionY);
                    imagePanelWindows.setSize(screenWindowWidth, screenWindowHeight);
                }
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                btnSave.setBounds(imagePanelWindows.getWidth()/2 - 40, imagePanelWindows.getHeight()/2 - 30, 80,30);
                btnExit.setBounds(imagePanelWindows.getWidth()/2 - 40, imagePanelWindows.getHeight()/2, 80,30);

                imagePanelWindows.add(btnSave);
                imagePanelWindows.add(btnExit);

                imagePanelWindows.repaint();
            }
        });
        setVisible(true);
    }

    private class BtnSave extends JButton{

        private BufferedImage bufferedImage2 = null;

        BtnSave(String text){

            setText(text);
            setBackground(Color.RED);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    super.mouseReleased(e);

                    setState(JFrame.ICONIFIED);
                    //setVisible(false);

                    bufferedImage2 = ScreenShotAlgorithm.getScreenCapture(finalCursorPositionX, finalCursorPositionY,
                            screenWindowWidth, screenWindowHeight);

                    setExtendedState(JFrame.NORMAL);

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

                    mainWindow.setState(NORMAL);
                }
            });
        }
    }
}
