package client.UI.inGame;

import client.Service.inGame.MyHeroPro;
import client.Service.inGame.PlayNetwork;
import client.Service.inGame.role1;

import javax.swing.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Play extends JFrame {
    MyHeroPro hero;
    ArrayList<MyHeroPro> heroList;
    ObjectOutputStream serverOut;
    ObjectInputStream serverIn;
    MPanel mPanel;
    PlayNetwork playNetwork;
    int x = 0, y = 0;

    public Play(ObjectOutputStream serverOut, ObjectInputStream serverIn, MyHeroPro hero, ArrayList<MyHeroPro> heroList) {
        this.serverOut = serverOut;
        this.serverIn = serverIn;
        this.hero = hero;
        this.heroList = heroList;

        playNetwork = new PlayNetwork(serverOut, serverIn);
        setResizable(false);
        launchFrame();
        setVisible(true);
    }

    public Play() {

    }

    public Play(ObjectOutputStream serverOut) {
        this.serverOut = serverOut;

        setResizable(false);
        launchFrame();
        setVisible(true);
    }

    public static void main(String[] args) {
        Play battle = new Play();

        battle.setResizable(false);
        battle.launchFrame();
        battle.setVisible(true);
    }

    public void launchFrame() {
        mPanel = new MPanel(serverOut, serverIn, hero, heroList);
        playNetwork.setmPanel(mPanel);
        mPanel.setPlayNetwork(playNetwork);
        this.setSize(900, 500);
        this.add(mPanel);
    }

}

