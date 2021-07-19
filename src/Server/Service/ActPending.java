package Server.Service;

import Client.Service.InGame.MyHeroPro;

public class ActPending {
    private int actDis = 3, mpAmt = 1;
    private MyHeroPro o1, o2;
    private static ActPending ActPendingInstance = new ActPending();

    private ActPending() {

    }

    public static ActPending getActPendingInstance() {
        return ActPendingInstance;
    }

    public void setActPending(MyHeroPro o1, MyHeroPro o2) {
        if (o1 == null) {
            this.o2 = o2;
        }

        if (o2 == null) {
            this.o1 = o1;
        }
    }

    public void actPend() {
        if (o1 == null || o2 == null) {
            return;
        }

        int o1xLoc = o1.getxLoc(), o2xLoc = o2.getxLoc();
        int o1yLoc = o1.getyLoc(), o2yLoc = o2.getyLoc();
        int o1xHead = o1.getxHead(), o2xHead = o2.getxHead();

        if ((o1yLoc != o2yLoc) ||
                (Math.abs(o1xLoc - o2xLoc) > actDis * 2)) {
            return;
        } else if (o1xHead == o2xHead) {
            return;
        }

        int o1Condition = o1.getNowCondition(), o2Condition = o2.getNowCondition();

        if (o1Condition < 10 && o2Condition < 10) {
            return;
        } else if ((o1Condition == 10 && o2Condition == 14) || (o1Condition == 14 && o2Condition == 10)) {
            return;
        }

        if ((o1Condition == 11 && o2Condition == 10) ||
                ((o1Condition == 11 || o1Condition == 10) && o2Condition < 10)) {
            o2.setHp(o2.getHp() - o1.getImpactAmt());
            o2.setMp(o2.getMp() + mpAmt);
            o2.setNowCondition(15);
            o1.setMp(o1.getMp() + mpAmt * 3);
        } else if ((o2Condition == 11 && o1Condition == 10) ||
                ((o2Condition == 11 || o2Condition == 10) && o1Condition < 10)) {
            o1.setHp(o1.getHp() - o2.getImpactAmt());
            o1.setMp(o1.getMp() + mpAmt);
            o1.setNowCondition(15);
            o2.setMp(o2.getMp() + mpAmt * 3);
        }

        int o1DefendAmt = o1.getDefendAmt(), o2DefendAmt = o2.getDefendAmt();

        if (o1Condition == 10 && o2Condition == 14) {
            if (o2DefendAmt - o1.getImpactAmt() < 0) {
                o2.setHp(o2.getHp() - o1.getImpactAmt());
                o2.setMp(o2.getMp() + mpAmt);
                o2.setDefendAmt(o2.getDefendInt());
                o2.setNowCondition(15);
                o1.setMp(o1.getMp() + mpAmt * 3);
            } else {
                o2.setDefendAmt(o2DefendAmt - o1.getImpactAmt());
            }
        } else if (o2Condition == 10 && o1Condition == 14) {
            if (o1DefendAmt - o2.getImpactAmt() < 0) {
                o1.setHp(o1.getHp() - o2.getImpactAmt());
                o1.setMp(o1.getMp() + mpAmt);
                o1.setDefendAmt(o1.getDefendInt());
                o1.setNowCondition(15);
                o2.setMp(o2.getMp() + mpAmt * 3);
            } else {
                o1.setDefendAmt(o1DefendAmt - o2.getImpactAmt());
            }
        }
    }

    public MyHeroPro getMyHero1() {
        return o1;
    }

    public MyHeroPro getMyHero2() {
        return o2;
    }
}
