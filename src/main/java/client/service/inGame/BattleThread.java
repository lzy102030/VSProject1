package client.service.inGame;

import client.ui.inGame.MPanel;
import debug.LogSystem;

import java.util.logging.Logger;

public class BattleThread {
    Logger logger = LogSystem.getLogger();

    MPanel mPanel;
    Thread moveT;

    int actionNormalTime = 100, actionHugeAtkTime = 1000;
    String keyUsed = null;

    //unify move amount settings
    int xMove = 50, yMove = 100;

    boolean defenceFlag = false, hugeAttackFlag = false;
    boolean isInterrupted = true;

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
                while (isInterrupted) {
                    if (keyUsed == null) {
                        mPanel.sendHero(0, 0, -1, 0);
                        logger.info("Send stand");

                        try {
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
                            hugeAttackFlag = true;
                            logger.info("Send huge attack");
                        } else {
                            System.err.println("[ERROR]None input found!");
                            logger.severe("None input found!");
                        }

                        try {
                            if (defenceFlag) {
                                obj.wait();
                                defenceFlag = false;
                            } else if (hugeAttackFlag) {
                                obj.wait(actionHugeAtkTime);
                                hugeAttackFlag = false;
                            } else {
                                obj.wait(actionNormalTime);
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

    public void interrupt() {
        isInterrupted = false;
    }

    public void setKeyUsed(String keyUsed) {
        synchronized (obj) {
            if ((defenceFlag && keyUsed == "U") || hugeAttackFlag) {
                return;
            }

            this.keyUsed = keyUsed;
            obj.notifyAll();
        }
    }
}

