package client.service.inGame;

import client.ui.inGame.MPanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class BattleThread {
    private static Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    MPanel mPanel;
    Thread moveT;

    int actionNormalTime = 100, actionHugeAtkTime = 1000;
    String keyUsed = null;

    //移动标准统一设定
    int xMove = 30, yMove = 100;

    //标记flag
    boolean defenceFlag = false, hugeAttackFlag = false;
    boolean isInterrupted = true;

    //线程用锁对象
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
                    //若无输入则处于站立状态
                    if (keyUsed == null) {
                        mPanel.sendHero(0, 0, -1, 0);
                        logger.trace("[Client] Send stand");

                        //无输入进入阻塞状态 等待直到下一个键盘输入的唤醒
                        try {
                            obj.wait();
                        } catch (InterruptedException e) {
                            System.err.println("[ERROR]Unexpected sleep out!");
                            e.printStackTrace();
                        }
                    } else {
                        //依据输入分别输出动作
                        if (keyUsed == "D") {
                            mPanel.sendHero(xMove, 0, 1, 1);
                            logger.trace("[Client] Send right move");
                        } else if (keyUsed == "A") {
                            mPanel.sendHero(-xMove, 0, 0, 1);
                            logger.trace("[Client] Send left move");
                        } else if (keyUsed == "W") {
                            mPanel.sendHero(0, -yMove, -1, 2);
                            logger.trace("[Client] Send up");
                        } else if (keyUsed == "S") {
                            mPanel.sendHero(0, yMove, -1, 3);
                            logger.trace("[Client] Send down");
                        } else if (keyUsed == "J") {
                            mPanel.sendHero(0, 0, -1, 10);
                            logger.trace("[Client] Send normal attack");
                        } else if (keyUsed == "K") {
                            mPanel.sendHero(0, 0, -1, 11);
                            logger.trace("[Client] Send hard attack");
                        } else if (keyUsed == "U") {
                            mPanel.sendHero(0, 0, -1, 14);
                            defenceFlag = true;
                            logger.trace("[Client] Send defence");
                        } else if (keyUsed == "L") {
                            mPanel.sendHero(0, 0, -1, 12);
                            hugeAttackFlag = true;
                            logger.trace("[Client] Send huge attack");
                        } else {
                            logger.error("[Client] Logical Fatal! None input found!");
                        }

                        //特殊动作的判定
                        try {
                            if (defenceFlag) {  //防御状态的长按实现 同样为阻塞式 等待松开防御按键时唤醒
                                obj.wait();
                                defenceFlag = false;
                            } else if (hugeAttackFlag) {  //大招状态的延时效果 限时阻塞式 等待大招动画结束后自动唤醒
                                obj.wait(actionHugeAtkTime);
                                hugeAttackFlag = false;
                            } else {
                                //非特殊动作时 停留动作保留帧时间 即可自动唤醒 或通过新键入唤醒
                                obj.wait(actionNormalTime);
                            }

                        } catch (InterruptedException e) {
                            System.err.println("[ERROR]Unexpected sleep out!");
                            e.printStackTrace();
                        }

                        //若无新键入 则自动落回站立状态
                        keyUsed = null;
                    }
                }
            }
        }
    }

    //线程的结束标记
    public void interrupt() {
        isInterrupted = false;
    }

    public void setKeyUsed(String keyUsed) {
        //通过线程锁实现关联唤醒与阻塞线程操作 完成对应动作判定
        synchronized (obj) {
            //防御与大招的跳过唤醒情况
            if ((defenceFlag && keyUsed == "U") || hugeAttackFlag) {
                return;
            }

            //接收新键入 并唤醒线程
            this.keyUsed = keyUsed;
            obj.notifyAll();
        }
    }
}

