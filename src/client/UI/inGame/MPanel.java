package client.UI.inGame;

import client.Service.inGame.DataTransfer;
import client.Service.inGame.MyHeroPro;

import client.Service.inGame.MyLinkedList;

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

    ImageIcon roleStand = new ImageIcon("E:\\work\\JavaTerm\\VSProject\\src\\source\\role1\\stand\\春丽_0-08.png");
    ImageIcon backGround = new ImageIcon("E:\\work\\JavaTerm\\VSProject\\src\\client\\Source\\背景.jpg");
    Image back = backGround.getImage();
    MyLinkedList<ImageIcon> images = new MyLinkedList<>();

    int hp=400;
    int mp=200;
    ObjectOutputStream serverOut;
    ObjectInputStream serverIn;
    MyHeroPro hero;
    ArrayList<MyHeroPro> heroList;

    int xLoc;
    int yLoc;
    int xHead;
    String direction = "R";
    String name = "春丽";
    String action = "move";

    public MPanel(ObjectOutputStream serverOut, ObjectInputStream serverIn, MyHeroPro hero, ArrayList<MyHeroPro> heroList) {
        this.serverOut = serverOut;
        this.serverIn = serverIn;
        this.hero = hero;
        this.heroList = heroList;

        initRole();
        this.setFocusable(true);
        this.addKeyListener(this);
    }

    public MPanel() {

    }

    public void paintComponent(Graphics g) {
        //把照片读入list
        /*for(int i = 1;i<12;i++){
            images.add(new ImageIcon(Objects.requireNonNull(
                    this.getClass().getResource("/client/Source/role1/walk/" + name + action + i + ".png"))));
        }*/
        super.paintComponent(g);
        //this.setBackground(Color.WHITE);
        g.drawImage(back,0,0,this.getWidth(),this.getHeight(),this);
        g.setColor(Color.red);
        g.fillRect(0,0,hp,20);//血条
        g.setColor(Color.blue);
        g.fillRect(0,21,mp,20);//怒气条

        if(direction == "R"&&xLoc<=790) {
            roleStand.paintIcon(this, g, xLoc+=50, yLoc);
            xHead = 1;
            /*for(int i = 0;i < 11; i++){
                images.get(i).paintIcon(this, g, xLoc, yLoc);
                repaint();
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }*/
            //getImage("a",191).paintIcon(this, g, xLoc+=20, yLoc);
        }else if(direction == "L"&&xLoc>=30) {
            roleStand.paintIcon(this, g, xLoc-=50, yLoc);
            xHead = 0;
        }else if(direction == "D"&&yLoc<=250) {
            roleStand.paintIcon(this, g, xLoc, yLoc+=100);
        }else if(direction == "U"&&yLoc>=250) {//250
            roleStand.paintIcon(this, g, xLoc, yLoc-=100);
        }else if(direction == "R"&&xLoc>=790){
            roleStand.paintIcon(this, g, xLoc, yLoc);
            xHead = 1;
        }else if(direction == "L"&&xLoc<=30){
            roleStand.paintIcon(this, g, xLoc, yLoc);
            xHead = 0;
        }else if(direction == "D"&&yLoc>=250){
            roleStand.paintIcon(this, g, xLoc, yLoc);
        }else if(direction == "U"&&yLoc<=250){
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
            /*System.out.println(yLoc);
            direction = "U";
            yLoc-=100;
            repaint();
            System.out.println(yLoc+"跳");
            try {
                sleep(1000);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            //repaint();
            System.out.println(yLoc+"sleep over");*/
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
        //攻击
        if(e.getKeyCode()==KeyEvent.VK_J){

        }
        //防御
        if(e.getKeyCode()==KeyEvent.VK_K){

        }
        //技能
        if(e.getKeyCode()==KeyEvent.VK_A){

        }
        repaint();

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    //调用图片
    private ImageIcon getImage(String name,int nub){
        ImageIcon turn = new ImageIcon(Objects.requireNonNull(
                this.getClass().getResource("/client/Source/role1/walk/" + name + action + nub + ".jpg")));
        /*try {
            turn = new ImageIcon(ImageIO.read(new File(
                    "/client/Source/" + name+nub+ ".gif")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image image = turn.getImage();*/

        return turn;
    }
}