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

        //启动客户端网络与UI
        mPanel = new MPanel(serverOut, serverIn, hero, heroList, this);
        playNetwork = new PlayNetwork(serverOut, serverIn);
        playNetwork.setmPanel(mPanel);
        mPanel.setPlayNetwork(playNetwork);

        //启动UI
        setResizable(false);
        launchFrame();
        setVisible(true);
    }

    public void launchFrame() {
        //获取屏幕大小
        Toolkit theKit = this.getToolkit();
        Dimension wndSize = theKit.getScreenSize();

        //设为居中显示
        this.setBounds(wndSize.width / 4, wndSize.height / 5,900,500);

        this.add(mPanel);
    }
}

