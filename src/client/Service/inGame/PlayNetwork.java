package client.Service.inGame;

import client.Service.Lobby.ChooseService;
import client.UI.inGame.MPanel;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class PlayNetwork {
    ObjectOutputStream serverOut;
    ObjectInputStream serverIn;
    ArrayList<MyHeroPro> heroList;
    MPanel mPanel = null;
    MyHeroPro hero = null;

    public PlayNetwork(MyHeroPro hero, int xLoc, int yLoc, int xHead, int condition) {
        this.hero = hero;

        prepareData(xLoc, yLoc, xHead, condition);

        new DataTransfer(serverOut).sendHero(this.hero);
    }

    public PlayNetwork(ObjectOutputStream serverOut, ObjectInputStream serverIn) {
        this.serverOut = serverOut;
        this.serverIn = serverIn;

        //remote thread start for checking new contents
      //  Thread thread = new Thread(new RemoteReader());
       // thread.start();
    }

    public void setmPanel(MPanel mPanel) {
        this.mPanel = mPanel;
    }

    private void prepareData(int xLoc, int yLoc, int xHead, int condition) {
        hero.setLoc(xLoc, yLoc, xHead);
        hero.setNowCondition(condition);
    }
}
