package Server.Service;

import client.Service.inGame.MyHeroPro;
import client.Service.inGame.MyObjectOutputStream;

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
    int x1Loc = 50, y1Loc = 300, x1Head = 1;
    int x2Loc = 700, y2Loc = 300, x2Head = 0;
    ObjectOutputStream out;

    //constructor
    private GameServer(Socket clientSok) {
        clientID = count++;
        System.out.println("Client #" + clientID + " is connected.");
        s = clientSok;
    }

    public static void main(String[] args) {

        System.out.println("Server started...");

        heroList = new ArrayList<>();

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


    public void run() {
        ObjectInputStream in;
        MyHeroPro hero = null;

        //get the input stream from client
        try {
            in = new ObjectInputStream(s.getInputStream());
            out = new ObjectOutputStream(s.getOutputStream());

            //[TODO][DEBUG]
            if (clientID > 2 && sendCount < 2) {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sendCondition(heroList);
            }

            while (true) {
                try {
                    if ((hero = (MyHeroPro) in.readObject()) == null) break;
                } catch (ClassNotFoundException e) {
                    System.err.println("[ERROR]None Object from socket has been received!");
                    e.printStackTrace();
                }

                //[Tips]服务端判定时间 avg: 2 ms  (max: 4 ms    min: 1 ms)

                if (clientID <= 2) {
                    hero.setUserID(clientID);
                }

                if (heroList.size() < 2) {
                    if (clientID == 1) {
                        hero.setLoc(x1Loc, y1Loc, x1Head);
                    } else if (clientID == 2) {
                        hero.setLoc(x2Loc, y2Loc, x2Head);
                    }
                }

                updateHeroList(hero);

                //[DEBUG Output]
                System.out.println("Client #" + clientID + " transferred " + hero.getName());
                System.out.println("Now Hero number:" + heroList.size());
                //[End]

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
                        //修改获胜方标记
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
                    }

                    //send message to every client
                    sendCondition(heroList);

                    //结束游戏network
                    if (winPendFlag != -1) {
                        break;
                    }
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
        heroList.remove(heroPro);
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
                out = new MyObjectOutputStream(
                        clientSocket.getOutputStream());
                out.writeObject(heroList);
            } catch (IOException e) {
                e.printStackTrace();
            }
            sendCount++;
        }
    }
}
