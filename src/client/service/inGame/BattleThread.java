package client.service.inGame;

import client.ui.inGame.MPanel;
import debug.LogSystem;

import java.util.Objects;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;

public class BattleThread {
    Logger logger = LogSystem.getLogger();

    MPanel mPanel;
    Thread moveT;

    int actionTime = 1000;
    String keyUsed = null;

    //unify move amount settings
    int xMove = 50, yMove = 100;
    int yMaxLevel = 1, yMinLevel = 0;
    int xMaxLoc = 790, xMinLoc = 30;
    boolean canNotifyFlag = false;
    boolean defenceFlag = false;

    final Object obj = new Object();

    //人物动作  0站立，1跑动，2上跳，3下跳，10拳攻击，11脚攻击,12技能， 14脸防御，15 16 17受击, 20无敌

    public BattleThread(MPanel mPanel) {
        this.mPanel = mPanel;
        moveT = new Thread(new MoveChange());
        moveT.start();
    }

    private class MoveChange implements Runnable {
        @Override
        public void run() {
            synchronized (obj) {
                while (true) {
                    if (keyUsed == null) {
                        mPanel.sendHero(0, 0, -1, 0);
                        logger.info("Send stand");

                        try {
                            canNotifyFlag = true;
                            obj.wait();
                        } catch (InterruptedException e) {
                            System.err.println("[ERROR]Unexpected sleep out!");
                            e.printStackTrace();
                        }
                    } else {
                        if (keyUsed == "D") {
                            mPanel.sendHero(xMove, 0, 1, 1);
                            logger.info("Send right move");
                        } else if (keyUsed == "A") {
                            mPanel.sendHero(-xMove, 0, 0, 1);
                            logger.info("Send left move");
                        } else if (keyUsed == "W") {
                            mPanel.sendHero(0, -yMove, -1, 2);
                            logger.info("Send up");
                        } else if (keyUsed == "S") {
                            mPanel.sendHero(0, yMove, -1, 3);
                            logger.info("Send down");
                        } else if (keyUsed == "J") {
                            mPanel.sendHero(0, 0, -1, 10);
                            logger.info("Send normal attack");
                        } else if (keyUsed == "K") {
                            mPanel.sendHero(0, 0, -1, 11);
                            logger.info("Send hard attack");
                        } else if (keyUsed == "U") {
                            mPanel.sendHero(0, 0, -1, 14);
                            defenceFlag = true;
                            logger.info("Send defence");
                        } else if (keyUsed == "L") {
                            mPanel.sendHero(0, 0, -1, 12);
                            logger.info("Send huge attack");
                        } else {
                            System.err.println("[ERROR]None input found!");
                            logger.severe("None input found!");
                        }

                        try {
                            if (defenceFlag) {
                                obj.wait();
                                defenceFlag = false;
                            } else {
                                obj.wait(50);
                            }
                        } catch (InterruptedException e) {
                            System.err.println("[ERROR]Unexpected sleep out!");
                            e.printStackTrace();
                        }

                        keyUsed = null;
                    }
                }
            }
        }
    }

    public void setKeyUsed(String keyUsed) {
        synchronized (obj) {
            //if (canNotifyFlag) {
            if (defenceFlag && keyUsed == "U") {
                return;
                //[todo]解决防御动画不播放的原因 可能错误点：服务器下发数据异常 / 客户端覆盖数据异常  / 发送异常
            }
            this.keyUsed = keyUsed;
            obj.notifyAll();
        }
        //  }
    }
}

