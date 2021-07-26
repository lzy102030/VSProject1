package client.ui.inGame;

import javax.swing.*;

public class EndGame {
    private int gameOverFlag;

    public EndGame(int gameOverFlag){
        this.gameOverFlag = gameOverFlag;
    }

    public void over(){
        if (gameOverFlag == 0) {
            JOptionPane.showMessageDialog(null, "平局", "结果", JOptionPane.PLAIN_MESSAGE);
        } else if (gameOverFlag == 1) {
            JOptionPane.showMessageDialog(null, "恭喜你胜利了！", "结果", JOptionPane.PLAIN_MESSAGE);
        } else if (gameOverFlag == 2) {
            JOptionPane.showMessageDialog(null, "很抱歉，你失败了！", "结果", JOptionPane.PLAIN_MESSAGE);
        }
    }
}
