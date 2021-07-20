package Server.Service;

public class WinnerPending {
    private int hp1, hp2;

    public WinnerPending() {

    }

    public WinnerPending(int hp1, int hp2) {
        this.hp1 = hp1;
        this.hp2 = hp2;
    }

    public int winPending() {
        if (hp1 < 0 && hp2 > 0) {
            return 2; //2胜
        } else if (hp1 > 0 && hp2 < 0) {
            return 1;  //1胜
        } else if (hp1 < 0 && hp2 < 0) {
            return 0;  //平
        } else {
            return -1;  //未结束
        }
    }
}
