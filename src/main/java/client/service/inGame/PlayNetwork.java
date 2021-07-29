package client.service.inGame;

import client.ui.inGame.MPanel;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

//对战进程的网络端处理
public class PlayNetwork {
    ObjectOutputStream serverOut;
    ObjectInputStream serverIn;
    ArrayList<MyHeroPro> heroList;
    MPanel mPanel;

    public PlayNetwork(ObjectOutputStream serverOut, ObjectInputStream serverIn) {
        this.serverOut = serverOut;
        this.serverIn = serverIn;

        //启动线程接受新内容
        Thread thread = new Thread(new RemoteReader());
        thread.start();
    }

    public void setmPanel(MPanel mPanel) {
        this.mPanel = mPanel;
    }

    private class RemoteReader implements Runnable {
        @Override
        public void run() {
            Object heroListReceive = null;
            while (true) {
                //游戏阶段 接收herolist后下发数据修正并绘制
                do {
                    try {
                        heroListReceive = serverIn.readObject();
                    } catch (Exception e) {
                        break;
                    }
                } while (heroListReceive == null);
                heroList = (ArrayList<MyHeroPro>) heroListReceive;
                //保护绘制对象有效性
                if (heroList != null) {
                    mPanel.updateHeroInfo(heroList);
                }
            }
        }
    }
}
