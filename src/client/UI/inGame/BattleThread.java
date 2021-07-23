package client.UI.inGame;

public class BattleThread extends Thread {
    MPanel myPanel;
    int frameFlushTime = 100;

    public BattleThread(MPanel myPanel) {
        this.myPanel = myPanel;
    }

    public void run() {
        while (true) {
            //[TODO]
            long startTime = System.currentTimeMillis(), endTime, usedTime = 0;

            myPanel.sendHero();

            try {
                Thread.sleep(frameFlushTime);
            } catch (InterruptedException e) {
                endTime = System.currentTimeMillis();
                usedTime = endTime - startTime;
                e.printStackTrace();
            }
            
            myPanel.repaint();
            
            try {
                Thread.sleep(frameFlushTime - usedTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
