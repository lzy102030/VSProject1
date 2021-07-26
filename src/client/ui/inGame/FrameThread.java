package client.ui.inGame;

public class FrameThread extends Thread {
    MPanel myPanel;
    int frameFlushTime = 50, processWaitTime = 10;

    public FrameThread(MPanel myPanel) {
        this.myPanel = myPanel;
    }

    public void run() {
        while (true) {
            //[TODO]
            myPanel.sendHero();

            try {
                sleep(processWaitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            myPanel.repaint();

            try {
                Thread.sleep(frameFlushTime - processWaitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
