package client.UI.inGame;

import client.Service.inGame.MyHeroPro;
import client.Service.inGame.PlayNetwork;
import client.Service.inGame.role1;

import javax.swing.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Play extends JFrame {
    JPanel jp1 = new JPanel();
    JPanel jp2 = new JPanel();
    JLabel jl1 = new JLabel();
    JLabel jl2 = new JLabel();
    MyHeroPro hero;
    ArrayList<MyHeroPro> heroList;
    ObjectOutputStream serverOut;
    ObjectInputStream serverIn;
    PlayNetwork playNetwork;
    MPanel mPanel;
    int x = 0, y = 0;
    //myJPanel myJPanel;

    public Play(ObjectOutputStream serverOut, ObjectInputStream serverIn, MyHeroPro hero, ArrayList<MyHeroPro> heroList) {
        this.serverOut = serverOut;
        this.serverIn = serverIn;
        this.hero = hero;
        this.heroList = heroList;

        setResizable(false);
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

    public void launchFrame() {
        //hero = new role1("chunli",1,1,1,1,1,1,1,1);
        //myJPanel = new myJPanel(hero);

        mPanel = new MPanel(serverOut, serverIn, hero, heroList);
        this.setSize(900, 500);
        this.add(mPanel);
        playNetwork.setmPanel(mPanel);

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

