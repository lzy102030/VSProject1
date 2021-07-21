package Server.Service;

import client.Service.inGame.MyHeroPro;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class GameServer extends Thread {
    static ServerSocket serverSocket = null;
    static List<Socket> clientSockets = null;
    static ArrayList<MyHeroPro> heroList = null;
    static ActPending actPending = ActPending.getActPendingInstance();
    Socket s;
    static int count = 1, port = 2000;
    int clientID, sendCount = 0;
    ObjectOutputStream out;

    //constructor
    private GameServer(Socket clientSok) {
        clientID = count++;
        System.out.println("Client #" + clientID + " is connected.");
        s = clientSok;
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
                heroList = new ArrayList<>();
                //Once you have a client connection, start the thread and wait for the next connection
                new GameServer(s).start();
            }
        } catch (IOException e) {
            System.out.println("Server closed：" + e);
            //serverSocket.close();
            System.exit(1);
        }
    }


    public void run() {
        ObjectInputStream in;
        Object hero = null;

        //get the input stream from client
        try {
            in = new ObjectInputStream(s.getInputStream());
            out = new ObjectOutputStream(s.getOutputStream());

            while (true) {
                try {
                    if ((hero = in.readObject()) == null) break;
                } catch (ClassNotFoundException e) {
                    System.err.println("[ERROR]None Object from socket has been received!");
                    e.printStackTrace();
                }

                System.out.println("Client #" + clientID + " transferred " + ((MyHeroPro) hero).getName());

                updateHeroList((MyHeroPro) hero);
                System.out.println("Now Hero number:" + heroList.size());

                //动作act判定是否生效
                if (heroList.size() == 2) {
                    actPending.setActPending(heroList.get(0), heroList.get(1));

                    actPending.actPend();

                    //胜负判定
                    //若为一方胜利则退出循环
                    int winPendFlag = new WinnerPending(
                            heroList.get(0).getHp(),
                            heroList.get(1).getHp()).winPending();
                    if (winPendFlag != -1) {
                        //发送胜负方
                        if (winPendFlag == 1) {
                            heroList.get(0).setGameOverFlag(1);
                            heroList.get(1).setGameOverFlag(2);
                        } else if (winPendFlag == 2) {
                            heroList.get(0).setGameOverFlag(2);
                            heroList.get(1).setGameOverFlag(1);
                        } else if (winPendFlag == 0) {
                            heroList.get(0).setGameOverFlag(0);
                            heroList.get(1).setGameOverFlag(0);
                        }
                        break;
                    }

                    //send message to every client
                    sendCondition(heroList);
                }
            }

            System.out.println("BYE, client " + clientID + " ! ");
            in.close();
            s.close();
        } catch (IOException e) {
            System.out.println("client " + clientID + " Exception ! ");
        }
    }

    //更新英雄list，保证同时仅存在一个userID的同一个英雄对象
    private synchronized void updateHeroList(MyHeroPro heroPro) {
        if (heroList.contains(heroPro)) {
            heroList.remove(heroPro);
        }
        heroList.add(heroPro);
        Collections.sort(heroList);
    }

    //send message for every client
    private synchronized void sendCondition(ArrayList<MyHeroPro> heroList) {
        System.out.println(heroList);
        ObjectOutputStream out;

        //use arraylist for the clients stored
        for (Socket clientSocket : clientSockets) {
            try {
                if (sendCount < 0) {
                    out = new ObjectOutputStream(
                            clientSocket.getOutputStream());
                    out.writeObject(heroList);
                } else {
                    out = new MyObjectOutputStream(
                            clientSocket.getOutputStream());
                    out.writeObject(heroList);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            sendCount++;
        }
    }

    class MyObjectOutputStream extends ObjectOutputStream {

        public MyObjectOutputStream(OutputStream out) throws IOException {
            super(out);
        }

        public void writeStreamHeader() throws IOException {
            return;
        }
    }
}
