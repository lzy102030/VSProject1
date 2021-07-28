package client.service.inGame;

//策略模式 接口部分
/*   在策略模式（Strategy Pattern）中，一个类的行为或其算法可以在运行时更改。这种类型的设计模式属于行为型模式。
     在策略模式中，我们创建表示各种策略的对象和一个行为随着策略对象改变而改变的 context 对象。策略对象改变 context 对象的执行算法。*/

public interface MyHeroInterface {
    String getName();

    int getxLoc();
    int getyLoc();
    int getxHead();

    int getImpactAmt();

    int getHp();
    int getMp();

    int getUserID();
    int getNowCondition();
    int isGameOverFlag();

    void setLoc(int xLoc, int yLoc, int xHead);
    void setHp(int hp);
    void setMp(int mp);
    void setNowCondition(int condition);
    void setGameOverFlag(int flag);
}
