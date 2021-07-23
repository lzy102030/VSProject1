package client;

import client.Service.Lobby.ChooseService;
import client.Service.inGame.MyHeroPro;
import client.UI.inGame.Play;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class Client {
    static Socket connection;
    static ObjectOutputStream serverOut;
    static ObjectInputStream serverIn;
    static ArrayList<MyHeroPro> heroList;
    static String host = "127.0.0.1";
    static int port = 2000;

    public static void main(String[] args) {
        getHost();

        connectToServer();

        new ChooseService(serverOut, serverIn);
    }

    private static void getHost() {
        host = JOptionPane.showInputDialog("Please input the server IP");
        if (host == null) {
            JOptionPane.showMessageDialog(null,
                    "None IP received. Now boot on local server.");
        }
    }

    private static void connectToServer() {
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

        //get the IO streams
        try {
            serverOut = new ObjectOutputStream(connection.getOutputStream());
            serverIn = new ObjectInputStream(connection.getInputStream());
        } catch (IOException e) {
            System.err.println("[ERROR]Cannot get transfer stream from the server.");
            e.printStackTrace();
        }
    }

    public static void callForGame(MyHeroPro hero, ArrayList<MyHeroPro> heroList) {
        new Play(serverOut, serverIn, hero, heroList);
    }
}
