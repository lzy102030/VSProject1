package client.UI.Lobby;

import client.Client;
import client.Service.inGame.DataTransfer;
import client.Service.inGame.MyHeroPro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class ChooseUI extends JFrame {
    JPanel jp1 = new JPanel();
    JLabel title = new JLabel();
    JButton b1 = new JButton();
    JButton b2 = new JButton();
    JButton b3 = new JButton();
    JButton b4 = new JButton();

    static ObjectOutputStream serverOut;
    MyHeroPro hero = null;
    int userID;

    public ChooseUI(ObjectOutputStream serverOut) {
        ChooseUI.serverOut = serverOut;

        createUserID();

        launchFrame();
        setVisible(true);
    }

    private void createUserID() {
        userID = (int) (Math.toIntExact(System.currentTimeMillis() % 10000) + Math.random());
    }

    private void launchFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1100, 750);
        this.setResizable(false);
        this.add(title, BorderLayout.NORTH);
        this.add(jp1, BorderLayout.SOUTH);
        /*
        this.add(jp2);
        this.add(jp3);
        this.add(jp4);
        */

        jp1.setSize(1000, 600);

        ImageIcon icon1 = new ImageIcon(Objects.requireNonNull(
                this.getClass().getResource("/client/source/草薙京.jpg")));
        ImageIcon icon2 = new ImageIcon(Objects.requireNonNull(
                this.getClass().getResource("/client/source/不知火舞.jpg")));
        ImageIcon icon3 = new ImageIcon(Objects.requireNonNull(
                this.getClass().getResource("/client/source/春丽.jpg")));
        ImageIcon icon4 = new ImageIcon(Objects.requireNonNull(
                this.getClass().getResource("/client/source/八神庵.jpg")));
        icon1.setImage(icon1.getImage().getScaledInstance(200, 600, Image.SCALE_DEFAULT));
        icon2.setImage(icon2.getImage().getScaledInstance(200, 600, Image.SCALE_DEFAULT));
        icon3.setImage(icon3.getImage().getScaledInstance(200, 600, Image.SCALE_DEFAULT));
        icon4.setImage(icon4.getImage().getScaledInstance(200, 600, Image.SCALE_DEFAULT));
        b1.setIcon(icon1);
        b2.setIcon(icon2);
        b3.setIcon(icon3);
        b4.setIcon(icon4);
        jp1.add(b1);
        jp1.add(b2);
        jp1.add(b3);
        jp1.add(b4);

        title.setFont(new Font("楷体", Font.BOLD, 40));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setSize(1000, 100);
        title.setText("请选择英雄");

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hero = new MyHeroPro("草薙京", 10, 5, 100, 0, -1, 0, userID);
                new DataTransfer(serverOut).sendHero(hero);
                waitForGame();
            }
        });

        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hero = new MyHeroPro("不知火舞", 10, 5, 100, 0, -1, 0, userID);
                new DataTransfer(serverOut).sendHero(hero);
                waitForGame();
            }
        });

        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hero = new MyHeroPro("春丽", 10, 5, 100, 0, -1, 0, userID);
                new DataTransfer(serverOut).sendHero(hero);
                waitForGame();
            }
        });

        b4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hero = new MyHeroPro("八神庵", 10, 5, 100, 0, -1, 0, userID);
                new DataTransfer(serverOut).sendHero(hero);
                waitForGame();
            }
        });

    }

    private void waitForGame() {
        setVisible(false);
        Container pane = this.getContentPane();
        pane.removeAll();
        setSize(400, 200);
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

        setVisible(true);
    }

    public void callForGame(ArrayList<MyHeroPro> heroList) {
        dispose();
        Client.callForGame(hero, heroList);
    }
}
