package client.UI.inGame;

public class BattleThread extends Thread {
    MPanel myPanel;

    public BattleThread(MPanel myPanel) {
        this.myPanel = myPanel;
    }

    public void run() {
        while (true) {
            myPanel.repaint();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
