package client.service.inGame;

import java.io.Serializable;

//项目的基本类
public class MyHeroPro implements MyHeroInterface,
        Serializable,
        Comparable<MyHeroPro> {
    private String name;

    private int xLoc;
    private int yLoc;
    private int xHead;                  //0左 1右

    private int impactAmt;

    private int hp;
    private int mp;

    private int userID = -1;           //-1未选中，选中后更改为对应的用户id
    private int gameOverFlag = -1;     //-1未结束，0平手，1胜利，2失败

    private int nowCondition;
    //0站立，1跑动，2上跳，3下跳，10拳攻击，11脚攻击,12技能， 14脸防御，15 16 17受击, 20无敌

    //debug用构造器
    public MyHeroPro(String name, int xLoc, int yLoc, int xHead,
                     int impactAmt, int defendInt, int defendAmt, int flashDis,
                     int hp, int mp, int userID, int gameOverFlag, int nowCondition) {
        this.name = name;
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        this.xHead = xHead;
        this.impactAmt = impactAmt;
        this.hp = hp;
        this.mp = mp;
        this.userID = userID;
        this.gameOverFlag = gameOverFlag;
        this.nowCondition = nowCondition;
    }

    //游戏进程中release版使用构造器
    public MyHeroPro(String name, int impactAmt, int hp, int mp, int gameOverFlag, int nowCondition, int userID) {
        this.name = name;
        this.impactAmt = impactAmt;
        this.hp = hp;
        this.mp = mp;
        this.gameOverFlag = gameOverFlag;
        this.nowCondition = nowCondition;
        this.userID = userID;
    }

    //信息获取类
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

    public int getHp() {
        return hp;
    }

    public int getMp() {
        return mp;
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

    //常规修改类
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

    public void setNowCondition(int condition) {
        this.nowCondition = condition;
    }

    public void setGameOverFlag(int flag) {
        this.gameOverFlag = flag;
    }

    //覆写Collection的相等方法 使其判定依据修改为userID
    //实现heroList只能存有用户的一个最新对象
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MyHeroPro)) {
            return false;
        }

        MyHeroPro heroPro = (MyHeroPro) obj;

        return heroPro.userID == this.userID;
    }

    //覆写比较方法 使其判定依据为userID
    //实现用户的简单排序
    @Override
    public int compareTo(MyHeroPro o) {
        if (this.userID > o.userID) {
            return 1;
        } else {
            return -1;
        }
    }
}
