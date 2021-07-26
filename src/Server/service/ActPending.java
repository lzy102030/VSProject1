package Server.Service;

import debug.LogSystem;
import client.service.inGame.MyHeroPro;

import javax.swing.*;
import java.util.logging.Logger;

public class ActPending {
    private int mpAmt = 1, actDis = 40;
    private MyHeroPro o1, o2;
    private static ActPending ActPendingInstance = new ActPending();
    private Logger logger = LogSystem.getLogger();

    private ActPending() {

    }

    public static ActPending getActPendingInstance() {
        return ActPendingInstance;
    }

    public void setActPending(MyHeroPro o1, MyHeroPro o2) {
        if (o1 == null | o2 == null) {
            //[TODO]Release需删除
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
            } else if ((o1Condition == 10 && o2Condition == 14)
                    || (o1Condition == 14 && o2Condition == 10)) {   //玩家一方处于普攻 另一方格挡时
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
    private void attackInfoModify(int player, int oPkCondition, int ImpactAmtTimes) {                 //攻击有效方 受击方状态
        if (player == 1) {   //玩家1攻击有效

            int realImpactAmt = o1.getImpactAmt() * ImpactAmtTimes;
            o2.setHp(o2.getHp() - realImpactAmt);                                 //玩家2受到1攻击
            o2.setMp(o2.getMp() + mpAmt);                                         //玩家2少量增长mp
            o2.setNowCondition(attackReactCond(oPkCondition));//玩家2进入受击状态
            o1.setMp(o1.getMp() + mpAmt * 3);                         //玩家1大量增长mp
            logger.info("[Client]User #1 Attack User #2. Got Damage " + realImpactAmt +
                    " [User #1 HP is " + o1.getHp() + "/ User #2 HP is " + o2.getHp() + ']');
        } else {    //玩家2攻击有效
            int realImpactAmt = o2.getImpactAmt() * ImpactAmtTimes;
            o1.setHp(o1.getHp() - realImpactAmt);                                 //玩家1受到攻击
            o1.setMp(o1.getMp() + mpAmt);                                         //玩家1少量增长mp
            o1.setNowCondition(attackReactCond(oPkCondition));                    //玩家1进入受击状态
            o2.setMp(o2.getMp() + mpAmt * 3);                                     //玩家2大量增长mp
            logger.info("[Client]User #2 Attack User #1. Got Damage " + realImpactAmt +
                    " User #1 now HP is " + o1.getHp() + " User #2 now HP is" + o2.getHp());
        }
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
}
