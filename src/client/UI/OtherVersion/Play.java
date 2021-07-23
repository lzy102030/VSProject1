package client.UI.OtherVersion;

import client.Service.inGame.MyHeroPro;
import client.Service.inGame.PlayNetwork;
import client.UI.inGame.MPanel;

import javax.swing.*;
import java.awt.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Play extends Frame {
    MyHeroPro hero;
    ArrayList<MyHeroPro> heroList;
    ObjectOutputStream serverOut;
    ObjectInputStream serverIn;

    PlayNetwork playNetwork;



    public Play(ObjectOutputStream serverOut, ObjectInputStream serverIn, MyHeroPro hero, ArrayList<MyHeroPro> heroList) {
        this.serverOut = serverOut;
        this.serverIn = serverIn;
        this.hero = hero;
        this.heroList = heroList;


        playNetwork = new PlayNetwork(serverOut, serverIn);
        launchFrame();

        setVisible(true);
    }

    public static void main(String[] args) {
        /*
        Play battle = new Play();
        battle.setResizable(false);
        battle.launchFrame();
        battle.setVisible(true);

         */
    }

    @Override
    public void paint(Graphics g) {
        //g.drawImage();
    }

    public void launchFrame() {
        setResizable(false);
        setSize(900, 500);

    }
}