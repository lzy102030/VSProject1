package client.UI.Lobby;

import client.Service.inGame.MyHeroPro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.Objects;

import static java.lang.Thread.sleep;

public class Choose extends JFrame {
    JPanel jp1 = new JPanel();
    JPanel jp2 = new JPanel();
    JPanel jp3 = new JPanel();
    JPanel jp4 = new JPanel();
    JButton b1 = new JButton();
    JButton b2 = new JButton();
    JButton b3 = new JButton();
    JButton b4 = new JButton();

    static Socket connection;
    static ObjectOutputStream serverOut;
    static ObjectInputStream serverIn;
    static MyHeroPro hero;
    static String host = "127.0.0.1";
    static int port = 2000;

    public static void main(String[] args) {
        Choose choose = new Choose();
        choose.launchFrame();
        choose.setVisible(true);

        //网络部分
        //get connect to the server
        boolean connectFlag = false;
        while (!connectFlag) {
            try {
                connection = new Socket(host, port);
                connectFlag = true;
            } catch (IOException e) {
                //while connect failed, 10 sec for retry
                System.err.println("[ERROR]Cannot connect to the server. 10 seconds to retry.");
                connectFlag = false;
                try {
                    sleep(10000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }

        //remote thread start for checking new contents
        Thread thread = new Thread(new Choose.RemoteReader());
        thread.start();

        //get the IO streams
        try {
            serverIn = new ObjectInputStream(connection.getInputStream());
            serverOut = new ObjectOutputStream(connection.getOutputStream());
        } catch (IOException e) {
            System.err.println("[ERROR]Cannot get transfer stream from the server.");
            e.printStackTrace();
        }
    }

    private static class RemoteReader implements Runnable {
        @Override
        public void run() {
            Object heroReceive = null;
            try {
                while (true) {
                    //1号玩家英雄信息
                    do {
                        try {
                            heroReceive = serverIn.readObject();
                        } catch (ClassNotFoundException e) {
                            System.err.println("None Object received from server.");
                            e.printStackTrace();
                        }
                    } while (heroReceive == null);
                    hero = (MyHeroPro) heroReceive;

                    //[TODO]接下来显示等处理

                    //2号玩家英雄信息
                    do {
                        try {
                            heroReceive = serverIn.readObject();
                        } catch (ClassNotFoundException e) {
                            System.err.println("None Object received from server.");
                            e.printStackTrace();
                        }
                    } while (heroReceive == null);
                    hero = (MyHeroPro) heroReceive;

                    //[TODO]接下来显示等处理
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void launchFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(jp1);
        this.add(jp2);
        this.add(jp3);
        this.add(jp4);

        jp1.setBounds(0, 0, 1000, 600);

        ImageIcon icon1 = new ImageIcon(Objects.requireNonNull(
                this.getClass().getResource("/client/source/草薙京.jpg")));
        ImageIcon icon2 = new ImageIcon(Objects.requireNonNull(
                this.getClass().getResource("/client/source/草薙京.jpg")));
        ImageIcon icon3 = new ImageIcon(Objects.requireNonNull(
                this.getClass().getResource("/client/source/草薙京.jpg")));
        ImageIcon icon4 = new ImageIcon(Objects.requireNonNull(
                this.getClass().getResource("/client/source/草薙京.jpg")));
        icon1.setImage(icon1.getImage().getScaledInstance(this.getWidth() / 4, this.getHeight(), Image.SCALE_DEFAULT));
        icon2.setImage(icon2.getImage().getScaledInstance(this.getWidth() / 4, this.getHeight(), Image.SCALE_DEFAULT));
        icon3.setImage(icon3.getImage().getScaledInstance(this.getWidth() / 4, this.getHeight(), Image.SCALE_DEFAULT));
        icon4.setImage(icon4.getImage().getScaledInstance(this.getWidth() / 4, this.getHeight(), Image.SCALE_DEFAULT));
        b1.setIcon(icon1);
        b2.setIcon(icon2);
        b3.setIcon(icon3);
        b4.setIcon(icon4);
        jp1.add(b1);
        jp2.add(b2);
        jp3.add(b3);
        jp4.add(b4);

        this.setLayout(new GridLayout(1, 4));

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        b4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

    }
}
