package client.service.lobby;

import client.service.database.MySQL;
import client.service.inGame.MyHeroPro;
import client.ui.lobby.ChooseUI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

//选人阶段的网络端
public class ChooseService {
    ObjectOutputStream serverOut;
    ObjectInputStream serverIn;
    ArrayList<MyHeroPro> heroList;
    ChooseUI chooseUI;
    HashMap<String, Integer> heroInfoMap;

    public ChooseService(ObjectOutputStream serverOut,
                         ObjectInputStream serverIn) {
        this.serverOut = serverOut;
        this.serverIn = serverIn;

        //从数据库获取英雄信息
        heroInfoMap = new MySQL().getHeroInfoMap();

        //启动线程接受新内容
        Thread thread = new Thread(new RemoteReader());
        thread.start();

        //启动UI
        chooseUI = new ChooseUI(serverOut, heroInfoMap);
    }

    private class RemoteReader implements Runnable {
        @Override
        public void run() {
            Object heroListReceive = null;
            try {
                //当接收到herolist 代表双方均完成选人
                do {
                    try {
                        heroListReceive = serverIn.readObject();
                    } catch (ClassNotFoundException e) {
                        System.err.println("None Object received from server.");
                        e.printStackTrace();
                    }
                } while (heroListReceive == null);
                heroList = (ArrayList<MyHeroPro>) heroListReceive;
                chooseUI.callForGame(heroList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
