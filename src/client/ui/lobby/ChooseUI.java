package client.ui.lobby;

import client.Client;
import client.service.inGame.DataTransfer;
import client.service.inGame.MyHeroPro;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class ChooseUI {
    JFrame frame = new JFrame();
    JPanel jp1 = new JPanel();
    JButton b1 = new JButton();
    JButton b2 = new JButton();
    JButton b3 = new JButton();
    JButton b4 = new JButton();
    JButton b5 = new JButton();
    JButton b6 = new JButton();
    JButton b7 = new JButton();
    JButton b8 = new JButton();

    static ObjectOutputStream serverOut;
    MyHeroPro hero = null;
    int userID;

    final static boolean shouldFill = true;
    final static boolean RIGHT_TO_LEFT = false;

    public ChooseUI(ObjectOutputStream serverOut) {
        ChooseUI.serverOut = serverOut;

        createUserID();

        javax.swing.SwingUtilities.invokeLater(this::launchFrame);
    }

    private void createUserID() {
        userID = (int) (Math.toIntExact(System.currentTimeMillis() % 10000) + Math.random());
    }

    private void launchFrame() {
        frame.setFont(new Font("System", Font.PLAIN, 14));
        Font f = frame.getFont();
        FontMetrics fm = frame.getFontMetrics(f);
        int x = fm.stringWidth("英雄选择");
        int y = fm.stringWidth(" ");
        int z = frame.getWidth()/2 - (x/2);
        int w = z/y;
        String pad ="";
        pad = String.format("%"+w+"s", pad);
        frame.setTitle(pad+"英雄选择");

        Toolkit theKit = frame.getToolkit();            // Get the window toolkit
        Dimension wndSize = theKit.getScreenSize();       // Get screen size
        frame.setBounds(wndSize.width / 4, wndSize.height / 5,700,400);

        //jp1.setSize(1000, 600);

        ImageIcon icon1 = new ImageIcon(Objects.requireNonNull(
                this.getClass().getResource("/client/source/草薙京.jpg")));
        ImageIcon icon2 = new ImageIcon(Objects.requireNonNull(
                this.getClass().getResource("/client/source/不知火舞.jpg")));
        ImageIcon icon3 = new ImageIcon(Objects.requireNonNull(
                this.getClass().getResource("/client/source/春丽.jpg")));
        ImageIcon icon4 = new ImageIcon(Objects.requireNonNull(
                this.getClass().getResource("/client/source/八神庵.jpg")));
        ImageIcon icon5 = new ImageIcon(Objects.requireNonNull(
                this.getClass().getResource("/client/source/伊格尼斯.png")));
        ImageIcon icon6 = new ImageIcon(Objects.requireNonNull(
                this.getClass().getResource("/client/source/克里斯.png")));
        ImageIcon icon7 = new ImageIcon(Objects.requireNonNull(
                this.getClass().getResource("/client/source/暴风子.png")));
        ImageIcon icon8 = new ImageIcon(Objects.requireNonNull(
                this.getClass().getResource("/client/source/雅典娜.png")));
        icon1.setImage(icon1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        icon2.setImage(icon2.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        icon3.setImage(icon3.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        icon4.setImage(icon4.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        icon5.setImage(icon5.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        icon6.setImage(icon6.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        icon7.setImage(icon7.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        icon8.setImage(icon8.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        b1.setIcon(icon1);
        b2.setIcon(icon2);
        b3.setIcon(icon3);
        b4.setIcon(icon4);
        b5.setIcon(icon5);
        b6.setIcon(icon6);
        b7.setIcon(icon7);
        b8.setIcon(icon8);

        addComponentsToPane(frame.getContentPane());

        b1.addActionListener(e -> {
            hero = new MyHeroPro("caotijing", 10, 5, 100, 0, -1, 0, userID);
            new DataTransfer(serverOut).sendHero(hero);
            waitForGame();
        });

        b2.addActionListener(e -> {
            hero = new MyHeroPro("buzhihuowu", 10, 5, 100, 0, -1, 0, userID);
            new DataTransfer(serverOut).sendHero(hero);
            waitForGame();
        });

        b3.addActionListener(e -> {
            hero = new MyHeroPro("chunli", 10, 5, 100, 0, -1, 0, userID);
            new DataTransfer(serverOut).sendHero(hero);
            waitForGame();
        });

        b4.addActionListener(e -> {
            hero = new MyHeroPro("bashenan", 10, 5, 100, 0, -1, 0, userID);
            new DataTransfer(serverOut).sendHero(hero);
            waitForGame();
        });

        b5.addActionListener(e -> {
            hero = new MyHeroPro("yigenisi", 10, 5, 100, 0, -1, 0, userID);
            new DataTransfer(serverOut).sendHero(hero);
            waitForGame();
        });

        b6.addActionListener(e -> {
            hero = new MyHeroPro("kelisi", 10, 5, 100, 0, -1, 0, userID);
            new DataTransfer(serverOut).sendHero(hero);
            waitForGame();
        });

        b7.addActionListener(e -> {
            hero = new MyHeroPro("baofengzi", 10, 5, 100, 0, -1, 0, userID);
            new DataTransfer(serverOut).sendHero(hero);
            waitForGame();
        });

        b8.addActionListener(e -> {
            hero = new MyHeroPro("yadianna", 10, 5, 100, 0, -1, 0, userID);
            new DataTransfer(serverOut).sendHero(hero);
            waitForGame();
        });
    }

    public void addComponentsToPane(Container pane){
        if (RIGHT_TO_LEFT) {
            pane.setComponentOrientation(
                    ComponentOrientation.RIGHT_TO_LEFT);
        }

        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        if (shouldFill) {
            //natural height, maximum width
            c.fill = GridBagConstraints.HORIZONTAL;
        }

        JLabel title = new JLabel("请选择英雄", JLabel.CENTER);
        title.setFont(new Font("宋体", Font.BOLD, 30));
        title.setForeground(Color.black);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 4;
        c.gridheight = 1;
        c.insets = new Insets(10, 10, 10, 10);
        pane.add(title, c);

        //b1 caotijing
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(5, 5, 5, 5);
        c.gridwidth = 1;
        c.gridheight = 1;
        pane.add(b1, c);

        //b2 chunli
        c.gridx = 1;
        c.gridy = 1;
        c.insets = new Insets(5, 5, 5, 5);
        c.gridwidth = 1;
        c.gridheight = 1;
        pane.add(b2, c);

        //b3 buzhihuowu
        c.gridx = 2;
        c.gridy = 1;
        c.insets = new Insets(5, 5, 5, 5);
        c.gridwidth = 1;
        c.gridheight = 1;
        pane.add(b3, c);

        //b4 bashenan
        c.gridx = 3;
        c.gridy = 1;
        c.insets = new Insets(5, 5, 5, 5);
        c.gridwidth = 1;
        c.gridheight = 1;
        pane.add(b4, c);

        //b5 yigenisi
        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(5, 5, 5, 5);
        c.gridwidth = 1;
        c.gridheight = 1;
        pane.add(b5, c);

        //b6 kelisi
        c.gridx = 1;
        c.gridy = 2;
        c.insets = new Insets(5, 5, 5, 5);
        c.gridwidth = 1;
        c.gridheight = 1;
        pane.add(b6, c);

        //b7 baofengzi
        c.gridx = 2;
        c.gridy = 2;
        c.insets = new Insets(5, 5, 5, 5);
        c.gridwidth = 1;
        c.gridheight = 1;
        pane.add(b7, c);

        //b8 yadianna
        c.gridx = 3;
        c.gridy = 2;
        c.insets = new Insets(5, 5, 5, 5);
        c.gridwidth = 1;
        c.gridheight = 1;
        pane.add(b8, c);

        frame.setVisible(true);
    }

    private void waitForGame() {
        frame.setVisible(false);
        Container pane = frame.getContentPane();
        pane.removeAll();
        frame.setSize(400, 200);
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("等待进入游戏", JLabel.CENTER);
        title.setFont(new Font("黑体", Font.BOLD, 30));
        title.setForeground(Color.BLACK);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 4;
        c.gridheight = 2;
        c.insets = new Insets(10, 10, 10, 10);
        pane.add(title, c);

        frame.setVisible(true);
    }

    public void callForGame(ArrayList<MyHeroPro> heroList) {
        frame.dispose();
        Client.callForGame(hero, heroList);
    }
}
