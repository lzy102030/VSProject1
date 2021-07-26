package client.service.inGame;

import client.ui.inGame.MPanel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class PlayNetwork {
    ObjectOutputStream serverOut;
    ObjectInputStream serverIn;
    ArrayList<MyHeroPro> heroList;
    MPanel mPanel;
    MyHeroPro hero;

    public PlayNetwork(MyHeroPro hero, int xLoc, int yLoc, int xHead, int condition) {
        this.hero = hero;

        prepareData(xLoc, yLoc, xHead, condition);

        new DataTransfer(serverOut).sendHero(this.hero);
    }

    public PlayNetwork(ObjectOutputStream serverOut, ObjectInputStream serverIn) {
        this.serverOut = serverOut;
        this.serverIn = serverIn;

        //remote thread start for checking new contents
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
            try {
                while (true) {
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
                    mPanel.updateHeroInfo(heroList);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void prepareData(int xLoc, int yLoc, int xHead, int condition) {
        hero.setLoc(xLoc, yLoc, xHead);
        hero.setNowCondition(condition);
    }
}
