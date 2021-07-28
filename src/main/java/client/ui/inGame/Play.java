package client.ui.inGame;

import client.service.inGame.MyHeroPro;
import client.service.inGame.PlayNetwork;

import javax.swing.*;
import java.awt.*;
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


    public Play(ObjectOutputStream serverOut, ObjectInputStream serverIn, MyHeroPro hero, ArrayList<MyHeroPro> heroList) {
        this.serverOut = serverOut;
        this.serverIn = serverIn;
        this.hero = hero;
        this.heroList = heroList;

        mPanel = new MPanel(serverOut, serverIn, hero, heroList, this);
        playNetwork = new PlayNetwork(serverOut, serverIn);
        playNetwork.setmPanel(mPanel);
        mPanel.setPlayNetwork(playNetwork);
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

    public void launchFrame() {
        Toolkit theKit = this.getToolkit();            // Get the window toolkit
        Dimension wndSize = theKit.getScreenSize();       // Get screen size
        this.setBounds(wndSize.width / 4, wndSize.height / 5,900,500);
        //this.setSize(900, 500);
        this.add(mPanel);
    }

}

