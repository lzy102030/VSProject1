package client.ui.inGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EndGame extends Frame {
    GameWinDialog gameWinDialog = new GameWinDialog();
    private int gameOverFlag;

    public EndGame() {
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public void setGameOverFlag(int gameOverFlag) {
        this.gameOverFlag = gameOverFlag;
    }

    public void over() {
        if (gameOverFlag == 0) {
            JOptionPane.showMessageDialog(null, "平局", "结果", JOptionPane.PLAIN_MESSAGE);
        } else if (gameOverFlag == 1) {
            //JOptionPane.showMessageDialog(null, "恭喜你胜利了！", "结果", JOptionPane.PLAIN_MESSAGE);
            this.gameWin();
        } else if (gameOverFlag == 2) {
            JOptionPane.showMessageDialog(null, "很抱歉，你失败了！", "结果", JOptionPane.PLAIN_MESSAGE);
        }
    }

    class GameWinDialog extends Dialog {
        JTextField email = new JTextField(20);
        JButton submit = new JButton("submit");
        JButton again = new JButton("Again");
        JLabel hint = new JLabel("Please enter your e-mail and we will send a message to you!");
        JPanel p = new JPanel();

        public GameWinDialog() {
            super(EndGame.this, true);
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            this.setTitle("Congratulations!");
            this.add(hint);
            p.add(email);
            p.add(submit);
            this.add(p);
            this.setLocation(500, 400);
            this.pack();
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    dispose();
                    EndGame.this.dispose();
                    MailOperation mail = new MailOperation("smtp.163.com", "smtp.163.com", "l0612zy@163.com", "RXMPYJMAEAWBLNWF");
                    if (!email.getText().equals("")) {
                        try {
                            mail.sendingMimeMail("l0612zy@163.com", email.getText(), "", "", "强●者●证●明！", "恭喜！你战胜了对手！特发此邮件，请收下这强者的证明！");
                        } catch (Exception e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    public void gameWin() {
        this.gameWinDialog.setVisible(true);
    }
}
