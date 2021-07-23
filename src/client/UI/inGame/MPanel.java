package client.UI.inGame;

import client.Service.inGame.DataTransfer;
import client.Service.inGame.MyHeroPro;

import client.Service.inGame.MyLinkedList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Objects;

import static java.lang.Thread.sleep;

public class MPanel extends JPanel implements KeyListener{
    ImageIcon roleStand = new ImageIcon("E:\\work\\JavaTerm\\VSProject\\src\\source\\role1\\stand\\春丽_0-08.png");
    ImageIcon backGround = new ImageIcon("E:\\work\\JavaTerm\\VSProject\\src\\client\\Source\\背景.jpg");
    Image back = backGround.getImage();
    ImageIcon actionTurn = new ImageIcon();

    int hp=400;
    int mp=200;
    ObjectOutputStream serverOut;
    ObjectInputStream serverIn;
    MyHeroPro hero;
    ArrayList<MyHeroPro> heroList;
    BattleThread thread;

    int xLoc;
    int yLoc;
    int xHead;
    int act;//图序
    String direction = null;
    String name = "春丽";//人物名称
    String action;//人物动作
    int numb;
    int time;

    public MPanel(ObjectOutputStream serverOut, ObjectInputStream serverIn, MyHeroPro hero, ArrayList<MyHeroPro> heroList) {
        this.serverOut = serverOut;
        this.serverIn = serverIn;
        this.hero = hero;
        this.heroList = heroList;

        initRole();
        this.setFocusable(true);
        this.addKeyListener(this);
        thread = new BattleThread(this);
        thread.start();
    }

    public MPanel() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(back,0,0,this.getWidth(),this.getHeight(),this);
        g.setColor(Color.red);
        g.fillRect(0,0,hp,20);//血条
        g.setColor(Color.blue);
        g.fillRect(0,21,mp,20);//怒气条
        time += 100;
        numb = time/200%4;

        if(direction == "R"&&xLoc<=790) {
            //getImage("春丽","move", 1).paintIcon(this, g, xLoc+=50, yLoc);
            xHead = 1;
            //移动图片选择
            switch (numb) {
                case 0 -> actionTurn = getImage("春丽", "move", numb);
                case 1 -> actionTurn = getImage("春丽", "move", numb);
                case 2 -> actionTurn = getImage("春丽", "move", numb);
                case 3 -> actionTurn = getImage("春丽", "move", numb);
            }
            actionTurn.paintIcon(this,g,xLoc+=50,yLoc);
            direction = null;
        }else if(direction == "L"&&xLoc>=30) {
            roleStand.paintIcon(this, g, xLoc-=50, yLoc);
            xHead = 0;
            direction = null;
        }else if(direction == "D"&&yLoc<=250) {
            roleStand.paintIcon(this, g, xLoc, yLoc+=100);
            direction = null;
        }else if(direction == "U"&&yLoc>=250) {
            roleStand.paintIcon(this, g, xLoc, yLoc-=100);
            direction = null;
        }else if(direction == "R"&&xLoc>=790){
            roleStand.paintIcon(this, g, xLoc, yLoc);
            xHead = 1;
            direction = null;
        }else if(direction == "L"&&xLoc<=30){
            roleStand.paintIcon(this, g, xLoc, yLoc);
            xHead = 0;
            direction = null;
        }else if(direction == "D"&&yLoc>=250){
            roleStand.paintIcon(this, g, xLoc, yLoc);
            direction = null;
        }else if(direction == "U"&&yLoc<=250){
            roleStand.paintIcon(this, g, xLoc, yLoc);
            direction = null;
        }else if(act == 10){
            switch (numb) {
                case 0 -> actionTurn = getImage("春丽", "attack", numb);
                case 1 -> actionTurn = getImage("春丽", "attack", numb);
                case 2 -> actionTurn = getImage("春丽", "attack", numb);
                case 3 -> actionTurn = getImage("春丽", "attack", numb);
            }
            actionTurn.paintIcon(this,g,xLoc,yLoc);
            act = 0;
        }else if(act == 11){
            switch (numb) {
                case 0 -> actionTurn = getImage("春丽", "leg", numb);
                case 1 -> actionTurn = getImage("春丽", "leg", numb);
                case 2 -> actionTurn = getImage("春丽", "leg", numb);
                case 3 -> actionTurn = getImage("春丽", "leg", numb);
            }
            actionTurn.paintIcon(this,g,xLoc,yLoc);
            act = 0;
        }else if(act == 14){
            switch (numb) {
                case 0 -> actionTurn = getImage("春丽", "defend", numb);
                case 1 -> actionTurn = getImage("春丽", "defend", numb);
                case 2 -> actionTurn = getImage("春丽", "defend", numb);
                case 3 -> actionTurn = getImage("春丽", "defend", numb);
            }
            actionTurn.paintIcon(this,g,xLoc,yLoc);
            act = 0;
        }else if(direction == null) {
            roleStand.paintIcon(this, g, xLoc, yLoc);
        }
    }

    public void initRole(){
        xLoc = 50;
        yLoc = 300;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_W){
            direction = "U";
        }
        if(e.getKeyCode()==KeyEvent.VK_D){
            direction = "R";
        }
        if(e.getKeyCode()==KeyEvent.VK_S){
            direction = "D";
        }
        if(e.getKeyCode()==KeyEvent.VK_A){
            direction = "L";
        }
        //防御
        if(e.getKeyCode()==KeyEvent.VK_U){
            act = 14;
        }
        //技能
        if(e.getKeyCode()==KeyEvent.VK_A){
            act = 12;
        }
        //repaint();

    }

    @Override
    public void keyReleased(KeyEvent e) {
        //拳攻击
        if(e.getKeyCode()==KeyEvent.VK_J){
            act = 10;
        }
        //脚攻击
        if(e.getKeyCode()==KeyEvent.VK_K){
            act = 11;
        }
        //防御
        if(e.getKeyCode()==KeyEvent.VK_U){
            act = 14;
        }
    }
    //调用图片
    private ImageIcon getImage(String name,String action,int numb){
        ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(
                this.getClass().getResource("/client/Source/role1/" + name + action + numb + ".png")));
        return imageIcon;
    }


}