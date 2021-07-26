package client.ui.inGame;

public class FrameThread extends Thread {
    MPanel myPanel;
    int frameFlushTime = 50, processWaitTime = 10;
    boolean isOver=true;


    public FrameThread(MPanel myPanel) {
        this.myPanel = myPanel;
    }

    public void run() {
        while (isOver) {
            myPanel.repaint();

            try {
                Thread.sleep(frameFlushTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void interrupt() {
        isOver = false;
    }
}
