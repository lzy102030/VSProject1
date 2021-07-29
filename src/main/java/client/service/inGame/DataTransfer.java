package client.service.inGame;

import java.io.*;

//向服务器下发
public class DataTransfer {
    ObjectOutputStream oos;

    public DataTransfer(ObjectOutputStream oos) {
        this.oos =  oos;
    }

    public void sendHero(MyHeroPro hero) {
        try {
            oos.writeObject(hero);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
