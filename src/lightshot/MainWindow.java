package lightshot;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainWindow extends JFrame {
    private static final int POS_X = 400;
    private static final int POS_Y = 200;
    private static final int WIDTH = 300;
    private static final int HEIGHT = 300;

    //создание глобальной переменной для хранения картинки в буфере
    private static BufferedImage bufferedImage = null;

    private MainWindow mainWindow;

    MainWindow(){

        this.mainWindow = this;

        //настройки главного окна
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Lightshot");
        setAlwaysOnTop(false);
        setLayout(new GridLayout(3,1));

        //добавление кнопок
        JButton btnScreenCapture = new JButton("Screen capture");
        JButton btnScreenShot = new JButton("New screenshot");
        JButton btnExit = new JButton("Exit");

        //кнопка "new screenshot" - выполнить скриншот всего экрана
        btnScreenShot.addActionListener(e ->{
            //свернуть окошко с меню
            this.setState(JFrame.ICONIFIED);

            //получение скриншота всего экрана
            bufferedImage = ScreenShotAlgorithm.getScreen();

            //развернуть окошко с меню
            this.setState(JFrame.NORMAL);

            //вызов диалогового окна для сохранения картинки
            FileDialog fileDialog = new FileDialog(new Frame(), "сохранить картинку", FileDialog.SAVE);
            fileDialog.setVisible(true);

            try {
                if(fileDialog.getDirectory()!=null){
                    ImageIO.write(bufferedImage,"png",
                        new File(fileDialog.getDirectory(), fileDialog.getFile() + ".png"));
                }
            }catch (IOException exp){
                System.out.println("IOException " + exp);
            }
        });

        //кнопка "Screen capture" - выполнить скриншот выделенной области на экране
        btnScreenCapture.addActionListener(e->{
            //свернуть окошко с меню
            this.setState(JFrame.ICONIFIED);

            //вызов конструктора
            new ScreenCaptureForMac(this);
        });

        //кнопка exit
        btnExit.addActionListener(e -> System.exit(0));

        //добавление кнопок на форму
        add(btnScreenCapture);
        add(btnScreenShot);
        add(btnExit);

        //установка видимости окна
        setVisible(true);

        //установка горячей клавиши для кнопки btnExit
        btnExit.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("alt Q"),
                "exit");
        btnExit.getActionMap().put("exit", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        //установка горячей клавиши для кнопки btnScreenCapture
        btnExit.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("alt N"),
                "capture");
        btnExit.getActionMap().put("capture", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //свернуть окошко с меню
                mainWindow.setState(JFrame.ICONIFIED);
                //вызов конструктора

                new ScreenCaptureForMac(mainWindow);
            }
        });
    }
}