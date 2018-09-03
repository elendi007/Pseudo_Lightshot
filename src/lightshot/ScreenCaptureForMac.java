package lightshot;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.event.ActionEvent;
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
    private Label label = new Label("IMAGE IN A BUFFER!");

    private boolean buttonTriger = true;

    private MainWindow mainWindow;

    private Rectangle rectangle;

    ScreenCaptureForMac(MainWindow mainWindow){
        //вызов супер конструктора класса window (в качестве параметра передаётся новая форма)
        super(new JFrame());

        this.mainWindow = mainWindow;

        //свернуть окошко с меню
        mainWindow.setState(JFrame.ICONIFIED);

        //передача панельке полного изображения экрана
        imagePanel = new ImagePanel(ScreenShotAlgorithm.getScreen());

        //добавление панельки с изображением экрана на пустое окно (ScreenCaptureForMac)
        add(imagePanel);

        //получение монитора с рабочего устройства, для того чтобы накрыть на него окно (ScreenCaptureForMac)
        GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        graphicsDevice.setFullScreenWindow(this);

        //установка фона для метки
        label.setBackground(Color.red);

        //установка видимости окна
        setVisible(true);

        //добавление обработчика событий мыши по каждому нажатию
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                currentCursorPositionX = e.getX();
                currentCursorPositionY = e.getY();

                //установка начальных точек отсчёта для обрезки изображения панельки
//                imagePanel.setRectLocation(currentCursorPositionX, currentCursorPositionY);
            }
        });

        //добавление обработчика событий мыши, которое срабатывает при нажитии и перетаскивании курсора
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);

                //отключение кнопок и тригера
                buttonTriger = true;
                imagePanel.remove(btnExit);
                imagePanel.remove(btnSave);
                imagePanel.remove(label);

                //first_quarter
                if( currentCursorPositionX < e.getX() && currentCursorPositionY > e.getY() ){
                    screenWindowWidth = e.getX() - currentCursorPositionX;
                    screenWindowHeight = currentCursorPositionY - e.getY();

                    finalCursorPositionX = currentCursorPositionX;
                    finalCursorPositionY = e.getY();

                    //установка начальных точек отсчёта для обрезки изображения панельки
                    imagePanel.setRectLocation(finalCursorPositionX, finalCursorPositionY);
                    //установка размеров панельки для обрезки
                    imagePanel.setRectSize(screenWindowWidth, screenWindowHeight);
                }

                //second_quarter
                else if( currentCursorPositionX > e.getX() && currentCursorPositionY > e.getY() ){
                    screenWindowWidth = currentCursorPositionX - e.getX();
                    screenWindowHeight = currentCursorPositionY - e.getY();

                    finalCursorPositionX = e.getX();
                    finalCursorPositionY = e.getY();

                    //установка начальных точек отсчёта для обрезки изображения панельки
                    imagePanel.setRectLocation(finalCursorPositionX, finalCursorPositionY);
                    //установка размеров панельки для обрезки
                    imagePanel.setRectSize(screenWindowWidth, screenWindowHeight);
                }

                //third_quarter
                else if( currentCursorPositionX > e.getX() && currentCursorPositionY < e.getY() ){
                    screenWindowWidth = currentCursorPositionX - e.getX();
                    screenWindowHeight = e.getY() - currentCursorPositionY;

                    finalCursorPositionX = e.getX();
                    finalCursorPositionY = currentCursorPositionY;

                    //установка начальных точек отсчёта для обрезки изображения панельки
                    imagePanel.setRectLocation(finalCursorPositionX, finalCursorPositionY);
                    //установка размеров панельки для обрезки
                    imagePanel.setRectSize(screenWindowWidth, screenWindowHeight);
                }

                //fourth_quarter
                else if( currentCursorPositionX < e.getX() && currentCursorPositionY < e.getY() ){
                    screenWindowWidth = e.getX() - currentCursorPositionX;
                    screenWindowHeight = e.getY()- currentCursorPositionY;

                    finalCursorPositionX = currentCursorPositionX;
                    finalCursorPositionY = currentCursorPositionY;

                    //установка начальных точек отсчёта для обрезки изображения панельки
                    imagePanel.setRectLocation(finalCursorPositionX, finalCursorPositionY);
                    //установка размеров панельки для обрезки
                    imagePanel.setRectSize(screenWindowWidth, screenWindowHeight);
                }
            }
        });

        //добавление обработчика событий мыши, которое срабатывает при отпускании клавиши на компоненте
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                //инициализация объекта прямоугольник, который служит областью в рамках которой необходимо выделить
                //изображение из полученного скрина экрана
                rectangle = new Rectangle();

                //first_quarter
                if( currentCursorPositionX < e.getX() && currentCursorPositionY > e.getY() ){
                    screenWindowWidth = e.getX() - currentCursorPositionX;
                    screenWindowHeight = currentCursorPositionY - e.getY();

                    finalCursorPositionX = currentCursorPositionX;
                    finalCursorPositionY = e.getY();

                    rectangle.setLocation(finalCursorPositionX + 1,finalCursorPositionY+1);
                    rectangle.setSize(screenWindowWidth-2,screenWindowHeight-1);
                }

                //second_quarter
                else if( currentCursorPositionX > e.getX() && currentCursorPositionY > e.getY() ){
                    screenWindowWidth = currentCursorPositionX - e.getX();
                    screenWindowHeight = currentCursorPositionY - e.getY();

                    finalCursorPositionX = e.getX();
                    finalCursorPositionY = e.getY();

                    rectangle.setLocation(finalCursorPositionX+1,finalCursorPositionY+1);
                    rectangle.setSize(screenWindowWidth-2,screenWindowHeight-1);
                }

                //third_quarter
                else if( currentCursorPositionX > e.getX() && currentCursorPositionY < e.getY() ){
                    screenWindowWidth = currentCursorPositionX - e.getX();
                    screenWindowHeight = e.getY() - currentCursorPositionY;

                    finalCursorPositionX = e.getX();
                    finalCursorPositionY = currentCursorPositionY;

                    rectangle.setLocation(finalCursorPositionX+1,finalCursorPositionY+1);
                    rectangle.setSize(screenWindowWidth-2,screenWindowHeight-1);
                }

                //fourth_quarter
                else if( currentCursorPositionX < e.getX() && currentCursorPositionY < e.getY() ){
                    screenWindowWidth = e.getX() - currentCursorPositionX;
                    screenWindowHeight = e.getY()- currentCursorPositionY;

                    finalCursorPositionX = currentCursorPositionX;
                    finalCursorPositionY = currentCursorPositionY;

                    rectangle.setLocation(finalCursorPositionX+1,finalCursorPositionY+1);
                    rectangle.setSize(screenWindowWidth-2,screenWindowHeight-1);
                }

                //добавление прямоугольника к кнопке save для сохранения картинки в пределах заданных границ
                btnSave.setBufferedImage2(rectangle);

                btnExit.setBounds(currentCursorPositionX + (e.getX() - currentCursorPositionX)/2 - 40,
                        currentCursorPositionY + (e.getY() - currentCursorPositionY)/2 - 15,
                        80,30);

                btnSave.setBounds(currentCursorPositionX + (e.getX() - currentCursorPositionX)/2 - 40,
                        currentCursorPositionY + (e.getY() - currentCursorPositionY)/2 - 45,
                        80,30);

                label.setBounds(currentCursorPositionX + (e.getX() - currentCursorPositionX)/2 - 60,
                        currentCursorPositionY + (e.getY() - currentCursorPositionY)/2 + 25,
                        120,30);

                //сохранение картинки в буфер
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                MyBufferImage myBufferImage = new MyBufferImage(ScreenShotAlgorithm.getScreen(rectangle));
                clipboard.setContents(myBufferImage, null);

                //добавление кнопок сохранить и выйти при наличии тригера
                if(buttonTriger) {
                    imagePanel.add(btnExit);
                    imagePanel.add(btnSave);
                    imagePanel.add(label);
                    buttonTriger = false;
                }else {
                    imagePanel.remove(btnExit);
                    imagePanel.remove(btnSave);
                    imagePanel.remove(label);
                }

                //перерисовка панели с изображением скрина
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

                    //убить ScreenCaptureForMac
                    dispose();

                    //установить видимость главного окна после сохранения захваченного изображения
                    mainWindow.setState(JFrame.NORMAL);

                    //вызов диалогового окна для сохранения полученного изображения
                    FileDialog fileDialog = new FileDialog(new Frame(), "сохранить картинку", FileDialog.SAVE);
                    fileDialog.setVisible(true);

                    try {
                        if(fileDialog.getDirectory()!= null) {
                            if(fileDialog.getFile().endsWith(".png"))
                            ImageIO.write(bufferedImage2, "png",
                                    new File(fileDialog.getDirectory(), fileDialog.getFile()));
                            else ImageIO.write(bufferedImage2, "png",
                                    new File(fileDialog.getDirectory(), fileDialog.getFile() + ".png"));
                        }
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

                    //убить ScreenCaptureForMac
                    dispose();

                    //установить видимость главного окна после сохранения захваченного изображения
                    mainWindow.setState(Frame.NORMAL);
                }
            });
        }
    }
}
