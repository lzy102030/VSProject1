package client.UI.OtherVersion;

import client.Service.inGame.MyHeroPro;
import client.Service.inGame.PlayNetwork;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Play extends Frame implements KeyListener {
    MyHeroPro hero;
    ArrayList<MyHeroPro> heroList;
    ObjectOutputStream serverOut;
    ObjectInputStream serverIn;

    PlayNetwork playNetwork;

    static Image[] bgImages, myHeroImages, conHeroImages;
    static BufferedImage bufferedImage;
    static Map<String, Image> heroImagesMap = new HashMap<>();

    public Play(ObjectOutputStream serverOut, ObjectInputStream serverIn, MyHeroPro hero, ArrayList<MyHeroPro> heroList) {
        this.serverOut = serverOut;
        this.serverIn = serverIn;
        this.hero = hero;
        this.heroList = heroList;


        playNetwork = new PlayNetwork(serverOut, serverIn);
        launchFrame();
        /*
        preLoadData(hero.getName(),
                heroList.get(0).getName().equals(hero.getName()) ?
                        heroList.get(1).getName() : heroList.get(0).getName());

         */
        preLoadData();

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

    public void preLoadData() {
        String myHeroName = hero.getName(),
                conHeroName = heroList.get(0).getName().equals(hero.getName()) ?
                        heroList.get(1).getName() : heroList.get(0).getName();

        try {
            //获取背景
            bgImages = new Image[]{
                    bufferedImage = ImageIO.read(new File("src/client/Source/背景.png")),
            };

            //获取英雄内容
            myHeroImages = new Image[]{
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/攻击/攻击1.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/攻击/攻击2.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/攻击/攻击3.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/攻击/攻击4.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/攻击/攻击5.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/攻击/攻击6.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/攻击/攻击7.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/攻击/攻击8.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/攻击/攻击9.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/攻击/攻击10.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/攻击/攻击11.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/攻击/攻击12.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/攻击/攻击13.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/攻击/攻击14.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/攻击/攻击15.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/攻击/攻击16.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/攻击/攻击17.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/攻击/攻击18.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/攻击/攻击19.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/攻击/攻击20.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/防御/防御1.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/防御/防御2.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/防御/防御3.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/防御/防御4.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/防御/防御5.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/防御/防御6.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/防御/防御7.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/防御/防御8.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/防御/防御9.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/防御/防御10.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/防御/防御11.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/防御/防御12.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/防御/防御13.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/防御/防御14.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/防御/防御15.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/防御/防御16.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/防御/防御17.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/防御/防御18.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/防御/防御19.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/防御/防御20.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/跑动/跑动1.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/跑动/跑动2.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/跑动/跑动3.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/跑动/跑动4.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/跑动/跑动5.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/跑动/跑动6.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/跑动/跑动7.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/跑动/跑动8.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/跑动/跑动9.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/跑动/跑动10.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/跑动/跑动11.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/跑动/跑动12.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/跑动/跑动13.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/跑动/跑动14.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/跑动/跑动15.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/跑动/跑动16.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/跑动/跑动17.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/跑动/跑动18.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/跑动/跑动19.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/跑动/跑动20.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/站立/站立1.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/站立/站立2.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/站立/站立3.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/站立/站立4.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/站立/站立5.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/站立/站立6.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/站立/站立7.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/站立/站立8.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/站立/站立9.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/站立/站立10.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/站立/站立11.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/站立/站立12.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/站立/站立13.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/站立/站立14.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/站立/站立15.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/站立/站立16.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/站立/站立17.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/站立/站立18.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/站立/站立19.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + myHeroName + "/站立/站立20.jpg")),
            };

            conHeroImages = new Image[]{
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/攻击/攻击1.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/攻击/攻击2.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/攻击/攻击3.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/攻击/攻击4.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/攻击/攻击5.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/攻击/攻击6.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/攻击/攻击7.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/攻击/攻击8.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/攻击/攻击9.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/攻击/攻击10.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/攻击/攻击11.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/攻击/攻击12.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/攻击/攻击13.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/攻击/攻击14.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/攻击/攻击15.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/攻击/攻击16.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/攻击/攻击17.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/攻击/攻击18.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/攻击/攻击19.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/攻击/攻击20.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/防御/防御1.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/防御/防御2.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/防御/防御3.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/防御/防御4.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/防御/防御5.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/防御/防御6.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/防御/防御7.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/防御/防御8.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/防御/防御9.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/防御/防御10.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/防御/防御11.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/防御/防御12.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/防御/防御13.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/防御/防御14.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/防御/防御15.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/防御/防御16.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/防御/防御17.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/防御/防御18.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/防御/防御19.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/防御/防御20.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/跑动/跑动1.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/跑动/跑动2.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/跑动/跑动3.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/跑动/跑动4.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/跑动/跑动5.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/跑动/跑动6.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/跑动/跑动7.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/跑动/跑动8.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/跑动/跑动9.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/跑动/跑动10.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/跑动/跑动11.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/跑动/跑动12.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/跑动/跑动13.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/跑动/跑动14.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/跑动/跑动15.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/跑动/跑动16.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/跑动/跑动17.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/跑动/跑动18.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/跑动/跑动19.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/跑动/跑动20.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/站立/站立1.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/站立/站立2.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/站立/站立3.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/站立/站立4.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/站立/站立5.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/站立/站立6.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/站立/站立7.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/站立/站立8.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/站立/站立9.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/站立/站立10.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/站立/站立11.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/站立/站立12.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/站立/站立13.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/站立/站立14.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/站立/站立15.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/站立/站立16.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/站立/站立17.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/站立/站立18.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/站立/站立19.jpg")),
                    bufferedImage = ImageIO.read(new File("src/client/UI/OtherVersion/source/" + conHeroName + "/站立/站立20.jpg")),
            };
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] actStrs = {
                "攻击", "防御", "跑动", "站立"
        };

        String[] heroStrs = {
                myHeroName, conHeroName
        };

        int j = 0;
        for (String heroStr :
                heroStrs) {
            for (String actStr :
                    actStrs) {
                for (int i = 1; i <= 20; i++) {
                    String nameStr = actStr + i;
                    String dirStr = heroStr + "+\"/" + actStr;

                    heroImagesMap.put(heroStr + nameStr, myHeroImages[j++]);

                }
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        //g.drawImage();
    }

    public void launchFrame() {
        setResizable(false);
        setSize(900, 500);

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void actionPerform(int turns) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_A -> actionPerform(0);
            case KeyEvent.VK_W -> actionPerform(2);
            case KeyEvent.VK_D -> actionPerform(0);
            case KeyEvent.VK_S -> actionPerform(1);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}