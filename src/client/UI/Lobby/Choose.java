package client.UI.Lobby;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Choose extends JFrame{
    JPanel jp1 = new JPanel();
    JPanel jp2 = new JPanel();
    JPanel jp3 = new JPanel();
    JPanel jp4 = new JPanel();
    JButton b1 = new JButton();
    JButton b2 = new JButton();
    JButton b3 = new JButton();
    JButton b4 = new JButton();

    public static void main(String[] args) {
        Choose choose = new Choose();
        choose.launchFrame();
        choose.setVisible(true);

        //网络
    }

    public void launchFrame(){
        this.add(jp1);
        this.add(jp2);
        this.add(jp3);
        this.add(jp4);
        this.setSize(1000,800);
        jp1.setBounds(0,0,1000,600);

        ImageIcon icon1 = new ImageIcon("E:\\work\\JavaTerm\\VSProject\\src\\client\\source\\草薙京.jpg");
        ImageIcon icon2 = new ImageIcon("E:\\work\\JavaTerm\\VSProject\\src\\client\\source\\草薙京.jpg");
        ImageIcon icon3 = new ImageIcon("E:\\work\\JavaTerm\\VSProject\\src\\client\\source\\草薙京.jpg");
        ImageIcon icon4 = new ImageIcon("E:\\work\\JavaTerm\\VSProject\\src\\client\\source\\草薙京.jpg");
        icon1.setImage(icon1.getImage().getScaledInstance(this.getWidth()/4,this.getHeight(),Image.SCALE_DEFAULT));
        icon2.setImage(icon2.getImage().getScaledInstance(this.getWidth()/4,this.getHeight(),Image.SCALE_DEFAULT));
        icon3.setImage(icon3.getImage().getScaledInstance(this.getWidth()/4,this.getHeight(),Image.SCALE_DEFAULT));
        icon4.setImage(icon4.getImage().getScaledInstance(this.getWidth()/4,this.getHeight(),Image.SCALE_DEFAULT));
        b1.setIcon(icon1);
        b2.setIcon(icon2);
        b3.setIcon(icon3);
        b4.setIcon(icon4);
        jp1.add(b1);
        jp2.add(b2);
        jp3.add(b3);
        jp4.add(b4);

        this.setLayout(new GridLayout(1,4));

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        b4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

    }
}
