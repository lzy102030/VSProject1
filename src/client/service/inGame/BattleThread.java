package client.service.inGame;

import client.ui.inGame.MPanel;

import java.util.Objects;

import static java.lang.Thread.sleep;

public class BattleThread {
    MPanel mPanel;
    Thread moveT;

    int actionTime = 1000;
    String keyUsed = null;

    //unify move amount settings
    int xMove = 50, yMove = 100;
    int yMaxLevel = 1, yMinLevel = 0;
    int xMaxLoc = 790, xMinLoc = 30;
    boolean canNotifyFlag = false;

    //人物动作  0站立，1跑动，2上跳，3下跳，10拳攻击，11脚攻击,12技能， 14脸防御，15 16 17受击, 20无敌

    public BattleThread(MPanel mPanel) {
        this.mPanel = mPanel;
        moveT = new Thread(new MoveChange());
        moveT.start();
    }

    private class MoveChange implements Runnable {
        @Override
        public void run() {
            while (true) {
                if (keyUsed == null) {
                    mPanel.sendHero(0, 0, -1, 0);
                    try {
                        canNotifyFlag = true;
                        wait();
                    } catch (InterruptedException e) {
                        System.err.println("[ERROR]Unexpected sleep out!");
                        e.printStackTrace();
                    }
                } else {
                    if (Objects.equals(keyUsed, "D")) {
                        mPanel.sendHero(xMove, 0, 1, 1);
                    } else if (Objects.equals(keyUsed, "A")) {
                        mPanel.sendHero(-xMove, 0, 0, 1);
                    } else if (Objects.equals(keyUsed, "W")) {
                        mPanel.sendHero(0, -yMove, -1, 2);
                    } else if (Objects.equals(keyUsed, "S")) {
                        mPanel.sendHero(0, yMove, -1, 3);
                    } else if (Objects.equals(keyUsed, "J")) {
                        mPanel.sendHero(0, 0, -1, 10);
                    } else if (Objects.equals(keyUsed, "K")) {
                        mPanel.sendHero(0, 0, -1, 11);
                    } else if (Objects.equals(keyUsed, "U")) {
                        mPanel.sendHero(0, 0, -1, 14);
                    } else if (Objects.equals(keyUsed, "L")) {
                        mPanel.sendHero(0, 0, -1, 12);
                    } else {
                        System.err.println("[ERROR]None input found!");
                    }

                    try {
                        canNotifyFlag = false;
                        sleep(actionTime);
                    } catch (InterruptedException e) {
                        System.err.println("[ERROR]Unexpected sleep out!");
                        e.printStackTrace();
                    }

                    keyUsed = null;
                }
            }
        }
    }

    public void setKeyUsed(String keyUsed) {
        this.keyUsed = keyUsed;
        if (canNotifyFlag) {
            notifyAll();
        }
    }
}
