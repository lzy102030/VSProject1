package client.ui.inGame;

import client.service.inGame.DataTransfer;
import client.service.inGame.MyHeroPro;
import client.service.inGame.PlayNetwork;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Objects;

public class MPanel extends JPanel implements KeyListener {
    ImageIcon backGround = new ImageIcon(Objects.requireNonNull(
            this.getClass().getResource("/client/source/背景.jpg")));
    ImageIcon vs = new ImageIcon(Objects.requireNonNull(
            this.getClass().getResource("/client/source/vs.png")));
    ImageIcon hpL = new ImageIcon(Objects.requireNonNull(
            this.getClass().getResource("/client/source/hpL.png")));
    ImageIcon hp = new ImageIcon(Objects.requireNonNull(
            this.getClass().getResource("/client/source/hp.png")));
    Image back = backGround.getImage();
    Image pk = vs.getImage();
    Image Hp = hp.getImage();
    Image HpL = hpL.getImage();
    ImageIcon actionTurn = new ImageIcon();

    ObjectOutputStream serverOut;
    ObjectInputStream serverIn;
    MyHeroPro myHero, conHero;
    ArrayList<MyHeroPro> heroList;
    BattleThread thread;
    PlayNetwork playNetwork;
    //刷新计时
    int numb;
    int time;

    //role1
    int hp1;
    int mp1;
    int xLoc1;
    int yLoc1;
    int yLevel1;
    int xHead1;
    int act1;//人物状态
    String name1 = null;//人物名称
    String action1;//人物动作  0站立，1跑动，2上跳，3下跳，10拳攻击，11脚攻击,12技能，14防御，15 16 17受击, 20无敌

    //role2
    int hp2;
    int mp2;
    int xLoc2;
    int yLoc2;
    int yLevel2;
    int xHead2;
    int act2;//人物状态
    String name2 = null;//人物名称
    String action2; //人物动作  0站立，1跑动，2上跳，3下跳，10拳攻击，11脚攻击,12技能， 14脸防御，15 16 17受击, 20无敌

    //unify move amount settings
    int xMove = 50, yMove = 100;
    int yMaxLevel = 1, yMinLevel = 0;
    int xMaxLoc = 790, xMinLoc = 30;

    boolean firstTransfer = true;

    public MPanel(ObjectOutputStream serverOut, ObjectInputStream serverIn, MyHeroPro myHero, ArrayList<MyHeroPro> heroList) {
        this.serverOut = serverOut;
        this.serverIn = serverIn;
        this.myHero = myHero;
        this.heroList = heroList;

        thread = new BattleThread(this);
        thread.start();
        updateHeroInfo(heroList);
        this.setFocusable(true);
        this.addKeyListener(this);
        //this.playBGM();
    }

    public void setPlayNetwork(PlayNetwork playNetwork) {
        this.playNetwork = playNetwork;
    }

    public void updateHeroInfo(ArrayList<MyHeroPro> heroList) {
        if (myHero.getUserID() == heroList.get(0).getUserID()) {
            myHero = heroList.get(0);
            conHero = heroList.get(1);
        } else {
            myHero = heroList.get(1);
            conHero = heroList.get(0);
        }

        if (firstTransfer) {
            xLoc1 = myHero.getxLoc();
            yLoc1 = myHero.getyLoc();
            xHead1 = myHero.getxHead();
            yLevel1 = yLoc1 - 250 < 0 ? 1 : 0;
            firstTransfer = false;
        }

        hp1 = myHero.getHp();
        mp1 = myHero.getMp();
        act1 = myHero.getNowCondition();
        name1 = myHero.getName();

        xLoc2 = conHero.getxLoc();
        yLoc2 = conHero.getyLoc();
        xHead2 = conHero.getxHead();
        hp2 = conHero.getHp();
        mp2 = conHero.getMp();
        act2 = conHero.getNowCondition();
        name2 = conHero.getName();
        yLevel2 = yLoc2 - 250 < 0 ? 1 : 0;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //paint background and vs
        g.drawImage(back, 0, 0, this.getWidth(), this.getHeight(), this);
        g.drawImage(pk, 410, 0, 80, 80, this);
        //刷新计时
        time += 50;
        numb = time / 200 % 4;

        //paint role2
        g.setColor(Color.red);
        g.drawImage(HpL, 490, 0, 400, 50, this);
        g.fillRect(525, 21, hp2, 14);//血条
        g.setColor(Color.blue);
        g.fillRect(535, 40, mp2 * 10, 17);//怒气条
        //g.drawImage(role, xLoc2, yLoc2, roleStand2.getIconWidth(), roleStand2.getIconHeight(), this);//背景

        if (act2 == 1 && xHead2 == 1) {
            xHead2 = 1;
            action2 = "move";
            actionTurn = getImage(name2, action2, numb);
            actionTurn.paintIcon(this, g, xLoc2, yLoc2);
            act2 = 0;
        } else if (act2 == 1 && xHead2 == 0) {
            action2 = "moveL";
            actionTurn = getImage(name2, action2, numb);
            actionTurn.paintIcon(this, g, xLoc2, yLoc2);
            xHead1 = 0;
            act2 = 0;
        } else if (act2 == 3 && xHead2 == 1) {
            action2 = "down";
            actionTurn = getImage(name2, action2, numb);
            actionTurn.paintIcon(this, g, xLoc2, yLoc2);
            act2 = 0;
        } else if (act2 == 3 && xHead2 == 0) {
            action2 = "downL";
            actionTurn = getImage(name2, action2, numb);
            actionTurn.paintIcon(this, g, xLoc2, yLoc2);
            act2 = 0;
        } else if (act2 == 2 && xHead2 == 1) {
            action2 = "up";
            actionTurn = getImage(name2, action2, numb);
            actionTurn.paintIcon(this, g, xLoc2, yLoc2);
            act2 = 0;
        } else if (act2 == 2 && xHead2 == 0) {
            action2 = "upL";
            actionTurn = getImage(name2, action2, numb);
            actionTurn.paintIcon(this, g, xLoc2, yLoc2);
            act2 = 0;
        } else if (act2 == 10 && xHead2 == 1) {
            action2 = "attack";
            actionTurn = getImage(name2, action2, numb);
            actionTurn.paintIcon(this, g, xLoc2, yLoc2);
            act2 = 0;
        } else if (act2 == 10 && xHead2 == 0) {
            action2 = "attackL";
            actionTurn = getImage(name2, action2, numb);
            actionTurn.paintIcon(this, g, xLoc2, yLoc2);
            act2 = 0;
        } else if (act2 == 11 && xHead2 == 1) {
            action2 = "leg";
            actionTurn = getImage(name2, action2, numb);
            actionTurn.paintIcon(this, g, xLoc2, yLoc2);
            act2 = 0;
        } else if (act2 == 11 && xHead2 == 0) {
            action2 = "legL";
            actionTurn = getImage(name2, action2, numb);
            actionTurn.paintIcon(this, g, xLoc2, yLoc2);
            act2 = 0;
        } else if (act2 == 14 && xHead2 == 1) {
            action2 = "defend";
            actionTurn = getImage(name2, action2, numb);
            actionTurn.paintIcon(this, g, xLoc2, yLoc2);
            act2 = 0;
        } else if (act2 == 14 && xHead2 == 0) {
            action2 = "defendL";
            actionTurn = getImage(name2, action2, numb);
            actionTurn.paintIcon(this, g, xLoc2, yLoc2);
            act2 = 0;
        } else if (act2 == 12 && xHead2 == 1) {
            action2 = "skill";
            actionTurn = getImage(name2, action2, numb);
            actionTurn.paintIcon(this, g, xLoc2, yLoc2);
            act2 = 0;
        } else if (act2 == 12 && xHead2 == 0) {
            action2 = "skillL";
            actionTurn = getImage(name2, action2, numb);
            actionTurn.paintIcon(this, g, xLoc2, yLoc2);
            act2 = 0;
        } else if ((act2 == 0 || act2 == 15 || act2 == 16 || act2 == 17) && xHead2 == 1) {
            action2 = "stand";
            actionTurn = getImage(name2, action2, numb);
            actionTurn.paintIcon(this, g, xLoc2, yLoc2);
        } else if ((act2 == 0 || act2 == 15 || act2 == 16 || act2 == 17) && xHead2 == 0) {
            action2 = "standL";
            actionTurn = getImage(name2, action2, numb);
            actionTurn.paintIcon(this, g, xLoc2, yLoc2);
        }

        //paint role1
        g.setColor(Color.red);
        g.drawImage(Hp, 0, 0, 400, 50, this);
        g.fillRect(35, 21, hp1, 14);//血条
        g.setColor(Color.blue);
        g.fillRect(45, 40, mp1 * 10, 17);//怒气条

        if (act1 == 1 && xHead1 == 1) {
            xHead1 = 1;
            action1 = "move";
            actionTurn = getImage(name1, action1, numb);
            actionTurn.paintIcon(this, g, xLoc1, yLoc1);
            act1 = 0;
        } else if (act1 == 1 && xHead1 == 0) {
            xHead1 = 0;
            action1 = "moveL";
            actionTurn = getImage(name1, action1, numb);
            actionTurn.paintIcon(this, g, xLoc1, yLoc1);
            act1 = 0;
        } else if (act1 == 3 && xHead1 == 1) {
            action1 = "down";
            actionTurn = getImage(name1, action1, numb);
            actionTurn.paintIcon(this, g, xLoc1, yLoc1);
            act1 = 0;
        } else if (act1 == 3 && xHead1 == 0) {
            action1 = "downL";
            actionTurn = getImage(name1, action1, numb);
            actionTurn.paintIcon(this, g, xLoc1, yLoc1);
            act1 = 0;
        } else if (act1 == 2 && xHead1 == 1) {
            action1 = "up";
            actionTurn = getImage(name1, action1, numb);
            actionTurn.paintIcon(this, g, xLoc1, yLoc1);
            act1 = 0;
        } else if (act1 == 2 && xHead1 == 0) {
            action1 = "upL";
            actionTurn = getImage(name1, action1, numb);
            actionTurn.paintIcon(this, g, xLoc1, yLoc1);
            act1 = 0;
        } else if (act1 == 10 && xHead1 == 1) {
            action1 = "attack";
            actionTurn = getImage(name1, action1, numb);
            actionTurn.paintIcon(this, g, xLoc1, yLoc1);
            act1 = 0;
        } else if (act1 == 10 && xHead1 == 0) {
            action1 = "attackL";
            actionTurn = getImage(name1, action1, numb);
            actionTurn.paintIcon(this, g, xLoc1, yLoc1);
            act1 = 0;
        } else if (act1 == 11 && xHead1 == 1) {
            action1 = "leg";
            actionTurn = getImage(name1, "leg", numb);
            actionTurn.paintIcon(this, g, xLoc1, yLoc1);
            act1 = 0;
        } else if (act1 == 11 && xHead1 == 0) {
            action1 = "legL";
            actionTurn = getImage(name1, "leg", numb);
            actionTurn.paintIcon(this, g, xLoc1, yLoc1);
            act1 = 0;
        } else if (act1 == 14 && xHead1 == 1) {
            action1 = "defend";
            actionTurn = getImage(name1, action1, numb);
            actionTurn.paintIcon(this, g, xLoc1, yLoc1);
            act1 = 0;
        } else if (act1 == 14 && xHead1 == 0) {
            action1 = "defendL";
            actionTurn = getImage(name1, action1, numb);
            actionTurn.paintIcon(this, g, xLoc1, yLoc1);
            act1 = 0;
        } else if (act1 == 12 && xHead1 == 1) {
            action1 = "skill";
            actionTurn = getImage(name1, action1, numb);
            actionTurn.paintIcon(this, g, xLoc1, yLoc1);
            act1 = 0;
        } else if (act1 == 12 && xHead1 == 0) {
            action1 = "skillL";
            actionTurn = getImage(name1, action1, numb);
            actionTurn.paintIcon(this, g, xLoc1, yLoc1);
            act1 = 0;
        } else if ((act1 == 0 || act1 == 15 || act1 == 16 || act1 == 17) && xHead1 == 1) {
            action1 = "stand";
            actionTurn = getImage(name1, action1, numb);
            actionTurn.paintIcon(this, g, xLoc1, yLoc1);
        } else if ((act1 == 0 || act1 == 15 || act1 == 16 || act1 == 17) && xHead1 == 0) {
            action1 = "standL";
            actionTurn = getImage(name1, action1, numb);
            actionTurn.paintIcon(this, g, xLoc1, yLoc1);
        }
    }

    public void heroActLast() {

    }

    public void initRole() {
        xLoc1 = 50;
        yLoc1 = 300;
    }

    public void sendHero() {
        myHero.setLoc(xLoc1, yLoc1, xHead1);
        myHero.setNowCondition(act1);

        new DataTransfer(serverOut).sendHero(myHero);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //change local hero info
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            act1 = 2;
            if (yLevel1 < yMaxLevel) {
                yLoc1 -= yMove;
                yLevel1++;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            act1 = 1;
            xHead1 = 1;
            int xTemp = xLoc1 + xMove;
            if (xTemp <= xMaxLoc) {
                xLoc1 = xTemp;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            act1 = 3;
            if (yLevel1 > yMinLevel) {
                yLoc1 += yMove;
                yLevel1--;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            act1 = 1;
            xHead1 = 0;
            int xTemp = xLoc1 - xMove;
            if (xTemp >= xMinLoc) {
                xLoc1 = xTemp;
            }
        }
        //防御
        if (e.getKeyCode() == KeyEvent.VK_U) {
            act1 = 14;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //拳攻击
        if (e.getKeyCode() == KeyEvent.VK_J) {
            act1 = 10;
        }
        //脚攻击
        if (e.getKeyCode() == KeyEvent.VK_K) {
            act1 = 11;
        }
        //防御
        if (e.getKeyCode() == KeyEvent.VK_U) {
            act1 = 14;
        }
        //技能
        if (e.getKeyCode() == KeyEvent.VK_L) {
            if (mp1 == 15) {
                act1 = 12;
            }
        }
    }

    //调用图片
    private ImageIcon getImage(String name, String action, int numb) {
        ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(
                this.getClass().getResource("/client/source/" + name + "/" + action + numb + ".png")));
        return imageIcon;
    }

    //播放音乐
    private void playBGM() {
        try {
            Clip bgm = AudioSystem.getClip();
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("source/bgm.wav");//""中写音乐文件名.wav
            AudioInputStream ais = AudioSystem.getAudioInputStream(is);
            bgm.open(ais);
            bgm.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }
}