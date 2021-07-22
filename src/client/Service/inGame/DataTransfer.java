package client.Service.inGame;

import java.io.*;

public class DataTransfer {
    MyObjectOutputStream oos;

    public DataTransfer(ObjectOutputStream oos) {
        this.oos = (MyObjectOutputStream) oos;
    }

    public void sendHero(MyHeroPro hero) {
        try {
            oos.writeObject(hero);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
