package client.Service.inGame;

import java.awt.*;
import java.io.Serializable;

public class MyHeroPro implements Serializable, Comparable {
    private String name;

    private int xLoc;
    private int yLoc;
    private int xHead;  //0左 1右
/*
    myLinkedList<Image> walkImages;
    myLinkedList<Image> standImages;
    myLinkedList<Image> attackImages;
    myLinkedList<Image> skillUImages;
    myLinkedList<Image> upImages;
 */
    private int impactAmt;
    private int defendInt;
    private int defendAmt;
    private int flashDis;

    private int hp;
    private int mp;

    private int userID = -1;  //-1未选中，选中后更改为对应的用户id
    private int gameOverFlag = -1; //-1未结束，0平手，1胜利，2失败

    private int nowCondition; //0站立，1跑动，2上跳，3下跳，10拳攻击，11脚攻击, 14脸防御，15 16 17受击, 20无敌

    //[DEBUG]debug用构造器 以下两个都是
    public MyHeroPro(String name) {
        this.name = name;
    }

    public MyHeroPro(String name, int xLoc, int yLoc, int xHead,
                     int impactAmt, int defendInt, int defendAmt, int flashDis,
                     int hp, int mp, int userID, int gameOverFlag, int nowCondition) {
        this.name = name;
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        this.xHead = xHead;
        this.impactAmt = impactAmt;
        this.defendInt = defendInt;
        this.defendAmt = defendAmt;
        this.flashDis = flashDis;
        this.hp = hp;
        this.mp = mp;
        this.userID = userID;
        this.gameOverFlag = gameOverFlag;
        this.nowCondition = nowCondition;
    }
    //到此

    public MyHeroPro(String name, int impactAmt, int defendInt, int defendAmt, int flashDis,
                     int hp, int mp, int gameOverFlag, int nowCondition) {
        this.name = name;
        this.impactAmt = impactAmt;
        this.defendInt = defendInt;
        this.defendAmt = defendAmt;
        this.flashDis = flashDis;
        this.hp = hp;
        this.mp = mp;
        this.gameOverFlag = gameOverFlag;
        this.nowCondition = nowCondition;
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

    public int getFlashDis() {
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

    public int getUserID() {
        return userID;
    }

    public int getNowCondition() {
        return nowCondition;
    }

    public int isGameOverFlag() {
        return gameOverFlag;
    }
/*
    public myLinkedList<Image> getWalkImages() {
        return walkImages;
    }

    public myLinkedList<Image> getStandImages() {
        return standImages;
    }

    public myLinkedList<Image> getAttackImages() {
        return attackImages;
    }

    public myLinkedList<Image> getSkillUImages() {
        return skillUImages;
    }

    public myLinkedList<Image> getUpImages() {
        return upImages;
    }

 */

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

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setGameOverFlag(int flag) {
        this.gameOverFlag = flag;
    }

    @Override
    public int compareTo(Object o) {
        MyHeroPro heroPro = (MyHeroPro) o;
        if (this.userID > heroPro.userID) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MyHeroPro)) {
            return false;
        }

        MyHeroPro heroPro = (MyHeroPro) obj;

        if (heroPro.userID == this.userID) {
            return true;
        } else {
            return false;
        }
    }
}
