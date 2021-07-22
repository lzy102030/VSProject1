package client.UI;

import client.UI.Lobby.Choose;

import java.io.IOException;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class ClientUI {
    static Socket connection;
    static String host = "127.0.0.1";
    static int port = 2000;

    public static void main(String[] args) {
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

        new Choose(connection);
    }
}
