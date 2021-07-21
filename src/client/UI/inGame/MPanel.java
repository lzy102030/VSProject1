package client.UI.inGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.lang.Thread.sleep;

public class MPanel extends JPanel implements KeyListener {

    ImageIcon roleStand = new ImageIcon("E:\\work\\JavaTerm\\VSProject\\src\\source\\role1\\stand\\春丽_0-08.png");
    ImageIcon backGround = new ImageIcon("E:\\work\\JavaTerm\\VSProject\\src\\client\\Source\\背景.jpg");
    Image back = backGround.getImage();

    int hp;
    int mp;
    int xLoc;
    int yLoc;
    int xHead;
    String direction = "R";

    public MPanel(){
        initRole();
        this.setFocusable(true);
        this.addKeyListener(this);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //this.setBackground(Color.WHITE);
        g.drawImage(back,0,0,this.getWidth(),this.getHeight(),this);

        if(direction == "R"&&xLoc<=790) {
            roleStand.paintIcon(this, g, xLoc+=50, yLoc);
        }else if(direction == "L"&&xLoc>=30) {
            roleStand.paintIcon(this, g, xLoc-=50, yLoc);
        }else if(direction == "D"&&yLoc<=250) {
            roleStand.paintIcon(this, g, xLoc, yLoc+=100);
        }else if(direction == "U"&&yLoc>=200) {//250
            roleStand.paintIcon(this, g, xLoc, yLoc+=100);
            System.out.println(yLoc+"落地");
        }else if(direction == "R"&&xLoc>=790){
            roleStand.paintIcon(this, g, xLoc, yLoc);
        }else if(direction == "L"&&xLoc<=30){
            roleStand.paintIcon(this, g, xLoc, yLoc);
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
            System.out.println(yLoc);
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
            System.out.println(yLoc+"sleep over");
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
}