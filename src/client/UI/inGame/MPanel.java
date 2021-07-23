package client.UI.inGame;

import client.Service.inGame.DataTransfer;
import client.Service.inGame.MyHeroPro;
import client.Service.inGame.PlayNetwork;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Objects;

import static java.lang.Thread.sleep;

public class MPanel extends JPanel implements KeyListener {
    ImageIcon roleStand1 = new ImageIcon(Objects.requireNonNull(
            this.getClass().getResource("/client/source/春丽stand0.png")));
    ImageIcon roleStand2 = new ImageIcon(Objects.requireNonNull(
            this.getClass().getResource("/client/source/草薙京stand0.png")));
    ImageIcon backGround = new ImageIcon(Objects.requireNonNull(
            this.getClass().getResource("/client/source/背景.jpg")));
    Image back = backGround.getImage();
    ImageIcon actionTurn = new ImageIcon();
    Image role = roleStand2.getImage();

    ObjectOutputStream serverOut;
    ObjectInputStream serverIn;
    MyHeroPro myHero, conHero;
    ArrayList<MyHeroPro> heroList;
    BattleThread thread;
    PlayNetwork playNetwork;

    int numb;
    int time;

    //role1
    int hp1 = 400;
    int mp1 = 200;
    int xLoc1;
    int yLoc1;
    int yLevel1;
    int xHead1;
    int act1;//图序
    String direction1 = null;
    String name1 = "春丽";//人物名称
    String action1;//人物动作  0站立，1跑动，2上跳，3下跳，10拳攻击，11脚攻击,12技能， 14脸防御，15 16 17受击, 20无敌

    //role2
    int hp2 = 400;
    int mp2 = 200;
    int xLoc2 = 600;
    int yLoc2 = 300;
    int yLevel2;
    int xHead2;
    int act2;//图序
    String direction2 = null;
    String name2 = "草薙京";//人物名称
    String action2; //人物动作  0站立，1跑动，2上跳，3下跳，10拳攻击，11脚攻击,12技能， 14脸防御，15 16 17受击, 20无敌

    //unify move amount settings
    int xMove = 50, yMove = 100;
    int yMaxLevel = 1, yMinLevel = 0;
    int xMaxLoc = 790, xMinLoc = 30;


    public MPanel(ObjectOutputStream serverOut, ObjectInputStream serverIn, MyHeroPro myHero, ArrayList<MyHeroPro> heroList) {
        this.serverOut = serverOut;
        this.serverIn = serverIn;
        this.myHero = myHero;
        this.heroList = heroList;

        initRole();
        updateHeroInfo(heroList);
        this.setFocusable(true);
        this.addKeyListener(this);
        thread = new BattleThread(this);
        thread.start();
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

        xLoc1 = myHero.getxLoc();
        yLoc1 = myHero.getyLoc();
        xHead1 = myHero.getxHead();
        hp1 = myHero.getHp();
        mp1 = myHero.getMp();
        act1 = myHero.getNowCondition();
        name1 = myHero.getName();
        yLevel1 = yLoc1 - 250 < 0 ? 1 : 0;

        xLoc2 = conHero.getxLoc();
        yLoc2 = conHero.getyLoc();
        xHead2 = conHero.getxHead();
        hp2 = conHero.getHp();
        mp2 = conHero.getMp();
        act2 = conHero.getNowCondition();
        name2 = conHero.getName();
        yLevel2 = yLoc2 - 250 < 0 ? 1 : 0;

        System.out.println("User #1 x-Loc=" + xLoc1 + "  y-Loc=" + yLoc1 + "  xHead=" + xHead1 +
                "  Act=" + act1 + "  HP=" + hp1 + "  MP=" + mp1);
        System.out.println("User #2 x-Loc=" + xLoc2 + "  y-Loc=" + yLoc2 + "  xHead=" + xHead2 +
                "  Act=" + act2 + "  HP=" + hp2 + "  MP=" + mp2);
        System.out.println("---------");
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //paint background
        g.drawImage(back, 0, 0, this.getWidth(), this.getHeight(), this);
        //刷新计时
        time += 50;
        numb = time / 200 % 4;

        //paint role2
        g.setColor(Color.red);
        g.fillRect(450, 0, hp2, 20);//血条
        g.setColor(Color.blue);
        g.fillRect(450, 21, mp2, 20);//怒气条
        g.drawImage(role, xLoc2, yLoc2, roleStand2.getIconWidth(), roleStand2.getIconHeight(), this);
        if (direction2 == "R" && xLoc2 <= 790) {
            xHead2 = 1;
            //移动图片选择
            action2 = "move";
            switch (numb) {
                case 0 -> actionTurn = getImage(name2, action2, numb);
                case 1 -> actionTurn = getImage(name2, action2, numb);
                case 2 -> actionTurn = getImage(name2, action2, numb);
                case 3 -> actionTurn = getImage(name2, action2, numb);
            }
            actionTurn.paintIcon(this, g, xLoc2, yLoc2);
            direction2 = null;
        } else if (direction2 == "L" && xLoc2 >= 30) {
            roleStand2.paintIcon(this, g, xLoc2, yLoc2);
            xHead1 = 0;
            direction1 = null;
        } else if (direction2 == "D" && yLoc2 <= 250) {
            roleStand2.paintIcon(this, g, xLoc2, yLoc2);
            direction2 = null;
        } else if (direction2 == "U" && yLoc2 >= 250) {
            roleStand2.paintIcon(this, g, xLoc2, yLoc2);
            direction2 = null;
        } else if (direction2 == "R" && xLoc2 >= 790) {
            roleStand2.paintIcon(this, g, xLoc2, yLoc2);
            xHead2 = 1;
            direction2 = null;
        } else if (direction2 == "L" && xLoc2 <= 30) {
            roleStand2.paintIcon(this, g, xLoc2, yLoc2);
            xHead2 = 0;
            direction1 = null;
        } else if (direction2 == "D" && yLoc2 >= 250) {
            roleStand2.paintIcon(this, g, xLoc2, yLoc2);
            direction2 = null;
        } else if (direction2 == "U" && yLoc2 <= 250) {
            roleStand2.paintIcon(this, g, xLoc2, yLoc2);
            direction2 = null;
        } else if (act2 == 10) {
            action2 = "attack";
            switch (numb) {
                case 0 -> actionTurn = getImage(name2, action2, numb);
                case 1 -> actionTurn = getImage(name2, action2, numb);
                case 2 -> actionTurn = getImage(name2, action2, numb);
                case 3 -> actionTurn = getImage(name2, action2, numb);
            }
            actionTurn.paintIcon(this, g, xLoc2, yLoc2);
            act2 = 0;
        } else if (act2 == 11) {
            switch (numb) {
                case 0 -> actionTurn = getImage(name2, "leg", numb);
                case 1 -> actionTurn = getImage(name2, "leg", numb);
                case 2 -> actionTurn = getImage(name2, "leg", numb);
                case 3 -> actionTurn = getImage(name2, "leg", numb);
            }
            actionTurn.paintIcon(this, g, xLoc2, yLoc2);
            act2 = 0;
        } else if (act2 == 14) {
            action2 = "defend";
            switch (numb) {
                case 0 -> actionTurn = getImage(name2, action2, numb);
                case 1 -> actionTurn = getImage(name2, action2, numb);
                case 2 -> actionTurn = getImage(name2, action2, numb);
                case 3 -> actionTurn = getImage(name2, action2, numb);
            }
            actionTurn.paintIcon(this, g, xLoc2, yLoc2);
            act2 = 0;
        } else if (direction2 == null) {
            roleStand2.paintIcon(this, g, xLoc2, yLoc2);
        }

        //paint role1
        g.setColor(Color.red);
        g.fillRect(0, 0, hp1, 20);//血条
        g.setColor(Color.blue);
        g.fillRect(0, 21, mp1, 20);//怒气条

        if (direction1 == "R" && xLoc1 <= 790) {
            //getImage("春丽","move", 1).paintIcon(this, g, xLoc+=50, yLoc);
            xHead1 = 1;
            //移动图片选择
            action1 = "move";
            switch (numb) {
                case 0 -> actionTurn = getImage(name1, action1, numb);
                case 1 -> actionTurn = getImage(name1, action1, numb);
                case 2 -> actionTurn = getImage(name1, action1, numb);
                case 3 -> actionTurn = getImage(name1, action1, numb);
            }
            actionTurn.paintIcon(this, g, xLoc1, yLoc1);
            direction1 = null;
        } else if (direction1 == "L" && xLoc1 >= 30) {
            roleStand1.paintIcon(this, g, xLoc1, yLoc1);
            xHead1 = 0;
            direction1 = null;
        } else if (direction1 == "D" && yLoc1 <= 250) {
            roleStand1.paintIcon(this, g, xLoc1, yLoc1);
            direction1 = null;
        } else if (direction1 == "U" && yLoc1 >= 250) {
            roleStand1.paintIcon(this, g, xLoc1, yLoc1);
            direction1 = null;
        } else if (direction1 == "R" && xLoc1 >= 790) {
            roleStand1.paintIcon(this, g, xLoc1, yLoc1);
            xHead1 = 1;
            direction1 = null;
        } else if (direction1 == "L" && xLoc1 <= 30) {
            roleStand1.paintIcon(this, g, xLoc1, yLoc1);
            xHead1 = 0;
            direction1 = null;
        } else if (direction1 == "D" && yLoc1 >= 250) {
            roleStand1.paintIcon(this, g, xLoc1, yLoc1);
            direction1 = null;
        } else if (direction1 == "U" && yLoc1 <= 250) {
            roleStand1.paintIcon(this, g, xLoc1, yLoc1);
            direction1 = null;
        } else if (act1 == 10) {
            action1 = "attack";
            switch (numb) {
                case 0 -> actionTurn = getImage(name1, action1, numb);
                case 1 -> actionTurn = getImage(name1, action1, numb);
                case 2 -> actionTurn = getImage(name1, action1, numb);
                case 3 -> actionTurn = getImage(name1, action1, numb);
            }
            actionTurn.paintIcon(this, g, xLoc1, yLoc1);
            act1 = 0;
        } else if (act1 == 11) {
            switch (numb) {
                case 0 -> actionTurn = getImage(name1, "leg", numb);
                case 1 -> actionTurn = getImage(name1, "leg", numb);
                case 2 -> actionTurn = getImage(name1, "leg", numb);
                case 3 -> actionTurn = getImage(name1, "leg", numb);
            }
            actionTurn.paintIcon(this, g, xLoc1, yLoc1);
            act1 = 0;
        } else if (act1 == 14) {
            action1 = "defend";
            switch (numb) {
                case 0 -> actionTurn = getImage(name1, action1, numb);
                case 1 -> actionTurn = getImage(name1, action1, numb);
                case 2 -> actionTurn = getImage(name1, action1, numb);
                case 3 -> actionTurn = getImage(name1, action1, numb);
            }
            actionTurn.paintIcon(this, g, xLoc1, yLoc1);
            act1 = 0;
        } else if (direction1 == null) {
            roleStand1.paintIcon(this, g, xLoc1, yLoc1);
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
            direction1 = "U";
            if (yLevel1 < yMaxLevel) {
                yLoc1 -= yMove;
                yLevel1++;
            }
            act1 = 2;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            direction1 = "R";
            int xTemp = xLoc1 + xMove;
            if (xTemp <= xMaxLoc) {
                xLoc1 = xTemp;
            }
            act1 = 1;
            xHead1 = 1;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            direction1 = "D";
            if (yLevel1 > yMinLevel) {
                yLoc1 += yMove;
                yLevel1--;
            }
            act1 = 3;
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            direction1 = "L";
            int xTemp = xLoc1 - xMove;
            if (xTemp >= xMinLoc) {
                xLoc1 = xTemp;
            }
            act1 = 1;
            xHead1 = 0;
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
            act1 = 12;
        }
    }

    //调用图片
    private ImageIcon getImage(String name, String action, int numb) {
        ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(
                this.getClass().getResource("/client/source/" + name + action + numb + ".png")));
        return imageIcon;
    }
}