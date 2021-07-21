package client.UI.inGame;

import client.Service.inGame.MyHeroPro;
import client.Service.inGame.role1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Play extends JFrame{
    JPanel jp1 = new JPanel();
    JPanel jp2 = new JPanel();
    JLabel jl1 = new JLabel();
    JLabel jl2 = new JLabel();
    MyHeroPro hero;
    int x = 0,y = 0;
    //myJPanel myJPanel;

    public static void main(String[] args) {
        Play battle = new Play();
        battle.setResizable(false);
        battle.launchFrame();
        battle.setVisible(true);
    }

    public void launchFrame(){
        //hero = new role1("chunli",1,1,1,1,1,1,1,1);
        //myJPanel = new myJPanel(hero);
        this.setSize(900, 500);
        this.add(new MPanel());

        /*this.add(jp1);
        jp1.add(jl1);
        ImageIcon icon1 = new ImageIcon("E:\\work\\JavaTerm\\VSProject\\src\\source\\草薙京.jpg");
        icon1.setImage(icon1.getImage().getScaledInstance(100,100, Image.SCALE_DEFAULT));
        jl1.setIcon(icon1);
        jl1.setBounds(x,y,100,100);
        jp1.setBounds(x,y,100,100);
        jp1.add(jl1);

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_W){
                    hero.getWalkImages();
                    jl1.setBounds(x,y-=10,100,100);

                }
                if(e.getKeyCode()==KeyEvent.VK_D){
                    jl1.setBounds(x+=10,y,100,100);

                }
                if(e.getKeyCode()==KeyEvent.VK_S){
                    jl1.setBounds(x,y+=10,100,100);

                }
                if(e.getKeyCode()==KeyEvent.VK_A){
                    jl1.setBounds(x-=10,y,100,100);

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

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });*/

    }

}

