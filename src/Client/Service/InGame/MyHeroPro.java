package Client.Service.InGame;

import java.io.Serializable;

public class MyHeroPro implements Serializable {
    private String name;

    private int xLoc;
    private int yLoc;
    private int xHead;

    private int impactAmt;
    private int defendInt;
    private int defendAmt;
    private int flashDis;

    private int hp;
    private int mp;

    private int nowCondition; //0站立，1跑动，2上跳，3下跳，10拳攻击，11脚攻击, 14脸防御，15受击

    public MyHeroPro(String name, int xLoc, int yLoc, int xHead,
                     int impactAmt,int defendInt, int flashDis,
                     int hp, int mp) {
        this.name = name;
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        this.xHead = xHead;
        this.impactAmt = impactAmt;
        this.defendInt = defendInt;
        this.defendAmt = this.defendInt;
        this.flashDis = flashDis;
        this.hp = hp;
        this.mp = mp;
    }

    public String getName() {
        return name;
    }

    public int getxLoc() {
        return xLoc;
    }

    public int getyLoc() {
        return yLoc;
    }

    public int getxHead() {
        return xHead;
    }

    public int getImpactAmt() {
        return impactAmt;
    }

    public int getDefendAmt() {
        return defendAmt;
    }

    public int getFlashDis(){
        return flashDis;
    }

    public int getHp() {
        return hp;
    }

    public int getMp() {
        return mp;
    }

    public int getDefendInt() {
        return defendInt;
    }

    public int getNowCondition() {
        return nowCondition;
    }

    public void setLoc(int xLoc, int yLoc, int xHead) {
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        this.xHead = xHead;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public void setDefendAmt(int defendAmt) {
        this.defendAmt = defendAmt;
    }

    public void setNowCondition(int condition) {
        this.nowCondition = condition;
    }
}
