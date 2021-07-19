package Server.Service;

import Client.Service.InGame.MyHeroPro;
import Server.Service.ActPending;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GameServer extends Thread {
    static ServerSocket serverSocket = null;
    static List<Socket> clientSockets = null;
    static ActPending actPending = ActPending.getActPendingInstance();
    Socket s;
    static int count = 1, port = 2000;
    int clientID;

    //constructor
    private GameServer(Socket clientSok) {
        clientID = count++;
        System.out.println("Client #" + clientID + " is connected.");
        s = clientSok;
    }

    public void run() {
        ObjectInputStream in;
        Object hero = null;

        //get the input stream from client
        try {
            in = new ObjectInputStream(
                    s.getInputStream());

            while (true) {
                try {
                    if ((hero = in.readObject()) == null) break;
                } catch (ClassNotFoundException e) {
                    System.err.println("No Object from socket has been received!");
                    e.printStackTrace();
                }

                //对象如何处理？
                if (clientID == 1) {
                    actPending.setActPending((MyHeroPro) hero, null);
                } else if(clientID == 2) {
                    actPending.setActPending(null, (MyHeroPro) hero);
                }

                //send message to every client
                sendCondition(hero);

                //胜负判定
                //若为一方胜利则退出循环
                if (new WinnerPending(actPending.getMyHero1().getHp(),actPending.getMyHero2().getHp()).winPending() != 2) {
                    //发送胜负方
                    break;
                }
            }

            System.out.println("BYE, client " + clientID + " ! ");
            in.close();
            s.close();
        } catch (IOException e) {
            System.out.println("Client " + clientID + " Exception ! ");
        }
    }

    //send message for every client
    private void sendCondition(Object o) {
        System.out.println(o);
        ObjectOutputStream out;

        //use arraylist for the clients stored
        for (Socket clientSocket : clientSockets) {
            try {
                out = new ObjectOutputStream(
                        clientSocket.getOutputStream());
                out.writeObject(o);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        System.out.println("Server started...");

        try {
            serverSocket = new ServerSocket(port);
            clientSockets = new ArrayList<>();
            //Socket s = serverSocket.accept();
            while (true) {
                //wait for accept new client
                Socket s = serverSocket.accept();
                clientSockets.add(s);
                //Once you have a client connection, start the thread and wait for the next connection
                new GameServer(s).start();
            }
        } catch (IOException e) {
            System.out.println("Server closed：" + e);
            //serverSocket.close();
            System.exit(1);
        }
    }
}
