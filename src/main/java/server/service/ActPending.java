package server.service;

import debug.LogSystem;
import client.service.inGame.MyHeroPro;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Logger;

//游戏业务逻辑 动作实现与判定
public class ActPending {
    //基本数据信息
    private int mpAmt = 1, actDis = 60;
    private MyHeroPro o1, o2;
    private static ActPending ActPendingInstance = new ActPending();
    private Logger logger = LogSystem.getLogger();

    //游戏数据记录区
    private int p1ImpactCount = 0, p2ImpactCount = 0,
            p1DefenceCount = 0, p2DefenceCount = 0,
            p1MpCount = 0, p2MpCount = 0;

    private ActPending() {

    }

    public static ActPending getActPendingInstance() {
        return ActPendingInstance;
    }

    public void setActPending(MyHeroPro o1, MyHeroPro o2) {
        if (o1 == null | o2 == null) {
            JOptionPane.showMessageDialog(null,
                    "[ERROR]Logical Fatal in List Sorting.");
        } else {
            this.o2 = o2;
            this.o1 = o1;
        }
    }

    public void actPend() {
        //玩家数据 未加载完全时 跳过
        if (o1 == null || o2 == null) {
            return;
        }

        //获取玩家 x y坐标 朝向 信息
        int o1xLoc = o1.getxLoc(), o2xLoc = o2.getxLoc();
        int o1yLoc = o1.getyLoc(), o2yLoc = o2.getyLoc();
        int o1xHead = o1.getxHead(), o2xHead = o2.getxHead();

        //排除判定情况[Start]
        if ((o1yLoc != o2yLoc) ||                                     //玩家1和2不在同一y层上
                (Math.abs(o1xLoc - o2xLoc) > actDis * 2)) {           //玩家1和2的水平距离 超过了判定范围
            return;
        }

        //通过此处表示 玩家1 2 均在同一层 且处于判定范围之内

        if ((((o1xLoc - o2xLoc) < 0) && o1xHead == 0 && o2xHead == 1) ||         //玩家1在玩家2左边 且 1左2右时 朝向不同  ←1 2→
                (((o1xLoc - o2xLoc) > 0) && o1xHead == 1 && o2xHead == 0)) {     //玩家1在玩家2右边 且 1右2左时 朝向不同  ←2 1→
            return;
        }

        //排除判定情况[End]
        //通过此处表示 玩家1 2 符合前置逻辑 且处于对向或同向中

        //获取玩家状态  0站立，1跑动，2上跳，3下跳，10拳攻击，11脚攻击, 12技能, 14脸防御，15 16 17受击, 20无敌
        int o1Condition = o1.getNowCondition(), o2Condition = o2.getNowCondition();

        //同向状态的判定
        if (((o1xLoc - o2xLoc) < 0 && o1xHead == 1 && o2xHead == 1)               //玩家1在玩家2左边 且 1右2右时 同向   1→ 2→
                || ((o1xLoc - o2xLoc) > 0 && o1xHead == 0 && o2xHead == 0)) {     //玩家1在玩家2右边 且 1右2左时 同向  ←2 ←1
            if (o2Condition == 20) {   //玩家2背向 但 无敌 时
                return;
            } else if (o1Condition >= 10 && o1Condition <= 12) {   //玩家1攻击时 到此 玩家2非无敌 则任意状态均受击
                attackInfoModify(1, o2Condition, 1);
            } else {
                return;
            }
        } else if (((o1xLoc - o2xLoc) < 0 && o1xHead == 0 && o2xHead == 0)            //玩家1在玩家2左边 且 1右2右时 同向  ←1 ←2
                || ((o1xLoc - o2xLoc) > 0 && o1xHead == 1 && o2xHead == 1)) {         //玩家1在玩家2右边 且 1右2左时 同向   2→ 1→
            if (o1Condition == 20) {   //玩家1背向 但 无敌 时
                return;
            } else if (o2Condition >= 10 && o2Condition <= 12) {   //玩家2攻击时  到此 玩家1非无敌 则任意状态均受击
                attackInfoModify(2, o1Condition, 1);
            } else {
                return;
            }
        } else {  //对向状态的判定
            //排除判定情况[Start]
            if ((o1Condition < 10 || o1Condition >= 15)
                    && (o2Condition < 10 || o2Condition >= 15)) {     //玩家均处于移动/受击/无敌等状态时
                return;
            } else if (o1Condition == 10 && o2Condition == 14) {    //玩家1普攻 玩家2格挡时
                p2DefenceCount += o1.getImpactAmt();
                return;
            } else if (o1Condition == 14 && o2Condition == 10) {     //玩家2普攻 玩家1格挡时
                p1DefenceCount += o2.getImpactAmt();
                return;
            } else if (o1Condition == 11 && o2Condition == 11) {    //玩家均使用长攻击
                return;
            } else if (o1Condition == 20 || o2Condition == 20) {    //玩家任意一方处于无敌状态时
                return;
            }
            //排除判定情况[End]

            //玩家1攻击成立
            if (o1Condition == 12) {  //玩家1技能攻击
                attackInfoModify(1, o2Condition, 3);
            } else if ((o1Condition == 11 && o2Condition == 10) ||          //玩家1长攻击  玩家2短攻击时
                    ((o1Condition == 11 || o1Condition == 10) && (o2Condition < 10 || (o2Condition >= 15 && o2Condition <= 17)))) {//玩家1任意两种攻击  玩家2处于移动/受击状态时
                attackInfoModify(1, o2Condition, 1);
            }

            //玩家2攻击成立
            if (o2Condition == 12) { //玩家2技能攻击
                attackInfoModify(2, o1Condition, 3);
            } else if ((o2Condition == 11 && o1Condition == 10) ||         //玩家2长攻击  玩家1短攻击时
                    ((o2Condition == 11 || o2Condition == 10) && (o1Condition < 10 || (o1Condition >= 15 && o1Condition <= 17)))) {   //玩家2任意两种攻击  玩家1处于移动/受击状态时
                attackInfoModify(2, o1Condition, 1);
            }
        }
    }

    //攻击成立信息修正
    private void attackInfoModify(int player, int oPkCondition, int impactAmtTimes) {                 //攻击有效方 受击方状态
        int realImpactAmt;

        if (player == 1) {   //玩家1攻击有效
            realImpactAmt = o1.getImpactAmt() * impactAmtTimes;

            o2.setHp(o2.getHp() - realImpactAmt);                                 //玩家2受到1攻击
            o2.setMp(o2.getMp() + mpAmt);                                         //玩家2少量增长mp
            o2.setNowCondition(attackReactCond(oPkCondition));                    //玩家2进入受击状态
            o1.setMp(o1.getMp() + mpAmt * 3);                                     //玩家1大量增长mp

            p1ImpactCount += realImpactAmt;
            p1MpCount += mpAmt * 3;
            p2MpCount += mpAmt;
        } else {    //玩家2攻击有效
            realImpactAmt = o2.getImpactAmt() * impactAmtTimes;

            o1.setHp(o1.getHp() - realImpactAmt);                                 //玩家1受到攻击
            o1.setMp(o1.getMp() + mpAmt);                                         //玩家1少量增长mp
            o1.setNowCondition(attackReactCond(oPkCondition));                    //玩家1进入受击状态
            o2.setMp(o2.getMp() + mpAmt * 3);                                     //玩家2大量增长mp

            p2ImpactCount += realImpactAmt;
            p2MpCount += mpAmt * 3;
            p1MpCount += mpAmt;
        }

        if (o1.getMp() > 15) {
            o1.setMp(15);
        }

        if (o2.getMp() > 15) {
            o2.setMp(15);
        }

        //log信息录入
        String infoStr = player == 1 ?
                "Player #1 Attack Player #2. Get Damage " :
                "Player #2 Attack Player #1. Get Damage ";
        String sparePattern = "                                   ";

        logger.info("[Server] " + infoStr + realImpactAmt + "pts.\n" +
                sparePattern + " Now Player #1 HP is " + o1.getHp() + "   MP is " + o1.getMp() + "\n" +
                sparePattern + "     Player #2 HP is " + o2.getHp() + "   MP is " + o2.getMp());
    }

    //受击状态判定
    private int attackReactCond(int cond) {
        if (cond < 15 || cond > 17) {      //非受击状态 进入 初始受击状态
            return 15;
        } else if (cond == 15 || cond == 16) {    //受击状态累计数
            return cond + 1;
        } else {     //受击满退回普通状态
            return 0;
        }
    }

    //获取游戏数据记录
    public double[][] getGameData() {
        return new double[][]{
                {
                        p1ImpactCount,
                        p1DefenceCount,
                        o1.getHp(),
                        p1MpCount
                },
                {
                        p2ImpactCount,
                        p2DefenceCount,
                        o2.getHp(),
                        p2MpCount
                }};
    }

    //获取最大数值
    public int getMaxCount() {
        //使用ArrayList快速排序
        ArrayList<Integer> arrayList = new ArrayList<>();

        arrayList.add(p1ImpactCount);
        arrayList.add(p2ImpactCount);
        arrayList.add(p1DefenceCount);
        arrayList.add(p2DefenceCount);
        arrayList.add(p1MpCount);
        arrayList.add(p2MpCount);

        arrayList.sort((o1, o2) -> {
            if (o1 > o2) {
                return 1;
            } else {
                return -1;
            }
        });

        return arrayList.get(5);
    }
}
