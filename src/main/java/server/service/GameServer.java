package server.service;

import client.service.database.MySQL;
import client.service.inGame.MyHeroPro;
import client.service.inGame.MyObjectOutputStream;


import java.io.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import client.service.inGame.ChartOutput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.ui.ServerUI;

public class GameServer {
    static ServerSocket serverSocket = null;
    static List<Socket> clientSockets = null;
    static ArrayList<MyHeroPro> heroList = null;
    static ActPending actPending = ActPending.getActPendingInstance();
    ServerUI serverUI;

    //游戏数据存放
    static int count = 1, port = 2000;
    int x1Loc = 50, y1Loc = 300, x1Head = 1;
    int x2Loc = 700, y2Loc = 300, x2Head = 0;

    ObjectOutputStream out;

    private static Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    //constructor
    private GameServer() {
        serverUI = new ServerUI();

        //启动线程池管理服务
        ExecutorService executorService = Executors.newCachedThreadPool();

        try {
            serverSocket = new ServerSocket(port);
            clientSockets = new ArrayList<>();
            heroList = new ArrayList<>();

            while (true) {
                //wait for accept new client
                Socket s = serverSocket.accept();
                clientSockets.add(s);
                //Once you have a client connection, start the thread and wait for the next connection
                executorService.execute(new SingleServer(s, count++));
            }
        } catch (IOException e) {
            logger.info("[Server] Server closed.");
        }
    }

    public static void main(String[] args) {
        logger.info("[Server] Server started.");

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
        }
    }

    //线程池单例实现
    private class SingleServer implements Runnable {
        Socket s;
        int clientID;

        public SingleServer(Socket clientSok, int clientID) {
            System.out.println("Client #" + clientID + " is connected.");
            s = clientSok;
            this.clientID = clientID;
        }

        @Override
        public void run() {
            ObjectInputStream in = null;
            MyHeroPro hero = null;

            try {
                in = new ObjectInputStream(s.getInputStream());
                out = new ObjectOutputStream(s.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            //get the input stream from client

            while (true) {
                try {
                    if ((hero = (MyHeroPro) in.readObject()) == null) break;
                } catch (ClassNotFoundException e) {
                    System.err.println("[ERROR]None Object from socket has been received!");
                    e.printStackTrace();
                } catch (IOException e) {
                    break;
                }

                //[Tips]服务端判定时间 avg: 2 ms  (max: 4 ms   min: 1 ms)

                //当heroList小于2时 赋予角色初值
                if (heroList.size() < 2) {
                    if (clientID == 1) {
                        hero.setLoc(x1Loc, y1Loc, x1Head);
                    } else if (clientID == 2) {
                        hero.setLoc(x2Loc, y2Loc, x2Head);
                    }
                }

                //更新英雄list
                updateHeroList(hero);

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

            clientSockets.remove(s);

            try {
                in.close();
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //当客户端列表无连接时 进入结束游戏后的阶段
            if (clientSockets.size() == 0) {
                endGameProcess();
            }
        }
    }

    private void endGameProcess() {
        //关闭UI
        serverUI.exitNow();

        //构造游戏数据时间戳
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmm");
        String timeTag = sdf.format(date);

        //关闭服务器
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //处理游戏数据记录
        sendDataCount(timeTag);

        //数据库的游戏数据操作
        mySQLDataProcess(timeTag);

        System.exit(0);
    }

    private void mySQLDataProcess(String timeTag) {
        //准备数据与表名称
        String tableName = "GameData" + timeTag;
        double[][] data = actPending.getGameData();

        new MySQL().createDataTable(tableName);   //建表
        new MySQL().insertData(data, tableName);   //存入数据
        new MySQL().createPDF(tableName);    //读取并生成pdf
    }

    private void sendDataCount(String timeTag) {
        double[][] data = actPending.getGameData();
        double maxLimit = actPending.getMaxCount() + 10;

        //绘制统计图表
        try {
            new ChartOutput(data, timeTag).drawAsPNG(maxLimit);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
