package Server.Service;

import debug.LogSystem;
import client.service.inGame.MyHeroPro;
import client.service.inGame.MyObjectOutputStream;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class GameServer {
    static ServerSocket serverSocket = null;
    static List<Socket> clientSockets = null;
    static ArrayList<MyHeroPro> heroList = null;
    static ActPending actPending = ActPending.getActPendingInstance();
    Socket s;
    static int count = 1, port = 2000;
    int sendCount = 0;
    int x1Loc = 50, y1Loc = 300, x1Head = 1;
    int x2Loc = 700, y2Loc = 300, x2Head = 0;
    ObjectOutputStream out;
    static Logger logger;

    //constructor
    private GameServer() {
        ExecutorService executorService = Executors.newCachedThreadPool();

        try {
            serverSocket = new ServerSocket(port);
            clientSockets = new ArrayList<>();
            heroList = new ArrayList<>();
            //Socket s = serverSocket.accept();
            while (true) {
                //wait for accept new client

                Socket s = serverSocket.accept();
                clientSockets.add(s);
                //Once you have a client connection, start the thread and wait for the next connection
                executorService.execute(new SingleServer(s, count++));
            }
        } catch (IOException e) {
            System.out.println("Server closed：" + e);
            //serverSocket.close();
            System.exit(1);
        }

    }

    public static void main(String[] args) {
        logger = LogSystem.getLogger();

        System.out.println("Server started...");
        logger.info("[Server]Server started.");

        new GameServer();
    }

    //更新英雄list，保证同时仅存在一个userID的同一个英雄对象
    private synchronized void updateHeroList(MyHeroPro heroPro) {
        heroList.remove(heroPro);
        heroList.add(heroPro);
        Collections.sort(heroList);
    }

    //send message for every client
    private synchronized void sendCondition(ArrayList<MyHeroPro> heroList) {
        int i = 1;
        for (MyHeroPro hero : heroList) {
            logger.info("[Server]User #" + i++ +
                    " x-Loc=" + hero.getxLoc() +
                    " y-Loc=" + hero.getyLoc() +
                    " xHead=" + hero.getxHead() +
                    "  Act=" + hero.getNowCondition() +
                    " HP=" + hero.getHp() +
                    " MP=" + hero.getMp());
        }
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

    private class SingleServer implements Runnable {
        int clientID;
        public SingleServer(Socket clientSok, int clientID) {
            System.out.println("Client #" + clientID + " is connected.");
            s = clientSok;
            this.clientID = clientID;
        }

        @Override
        public void run() {
            ObjectInputStream in;
            MyHeroPro hero = null;

            //get the input stream from client
            try {
                in = new ObjectInputStream(s.getInputStream());
                out = new ObjectOutputStream(s.getOutputStream());

                while (true) {
                    try {
                        if ((hero = (MyHeroPro) in.readObject()) == null) break;
                    } catch (ClassNotFoundException e) {
                        System.err.println("[ERROR]None Object from socket has been received!");
                        e.printStackTrace();
                    }

                    //[Tips]服务端判定时间 avg: 2 ms  (max: 4 ms    min: 1 ms)

                    if (heroList.size() < 2) {
                        if (clientID == 1) {
                            hero.setLoc(x1Loc, y1Loc, x1Head);
                        } else if (clientID == 2) {
                            hero.setLoc(x2Loc, y2Loc, x2Head);
                        }
                    }

                    updateHeroList(hero);

                    //[DEBUG Output]
                    logger.info("[Server]client #" + clientID + " transferred " + hero.getName() + " #" + hero.getUserID());
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
                logger.info("[Server]BYE, client " + clientID + " ! ");
                in.close();
                s.close();
            } catch (IOException e) {
                System.out.println("client " + clientID + " Exception ! ");
                logger.warning("[Server]client " + clientID + " Exception ! ");
            }
        }
    }
}
