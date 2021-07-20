package client.UI.inGame;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Play extends JFrame{
    JPanel jp1 = new JPanel();
    JPanel jp2 = new JPanel();
    JLabel jl1 = new JLabel();
    JLabel jl2 = new JLabel();

    public static void main(String[] args) {
        Play battle = new Play();
        battle.launchFrame();
        battle.setVisible(true);

    }

    public void launchFrame(){


        /*label.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(KeyEvent.VK_D == e.getKeyCode()){

                }
                if(KeyEvent.VK_A == e.getKeyCode()){

                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });*/

    }

}

