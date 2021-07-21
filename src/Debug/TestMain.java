package Debug;

import client.Service.inGame.MyHeroPro;

import java.io.*;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class TestMain {
    static Socket connection;
    static ObjectOutputStream serverOut;
    static ObjectInputStream serverIn;
    static MyHeroPro hero;

    public TestMain() {
        //get connect to the server
        boolean connectFlag = false;
        while (!connectFlag) {
            try {
                connection = new Socket("127.0.0.1", 2000);
                connectFlag = true;
            } catch (IOException e) {
                //while connect failed, 10 sec for retry
                System.err.println("[ERROR]Cannot connect to the server. 10 Seconds to retry.");
                connectFlag = false;
                try {
                    sleep(10000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }

        //remote thread start for checking new contents
        Thread thread = new Thread(new RemoteReader());
        thread.start();

        //get the IO streams
        try {
            serverIn = new ObjectInputStream(connection.getInputStream());
            serverOut = new ObjectOutputStream(connection.getOutputStream());
        } catch (IOException e) {
            System.err.println("[ERROR]Cannot get file services the server.");
            e.printStackTrace();
        }

        MyHeroPro hero1 = new MyHeroPro("test1");
        MyHeroPro hero2 = new MyHeroPro("test2");

        try {
            serverOut.writeObject(hero1);
            serverOut.writeObject(hero2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObjectInputStream getServerIn() {
        return serverIn;
    }

    public ObjectOutputStream getServerOut() {
        return serverOut;
    }

    private static class RemoteReader implements Runnable {
        @Override
        public void run() {
            Object heroReceive = null;

            try {
                while (true) {
                    do {
                        try {
                            heroReceive = serverIn.readObject();
                        } catch (ClassNotFoundException e) {
                            System.err.println("None Object received from server.");
                            e.printStackTrace();
                        }
                    } while (heroReceive == null);
                    hero = (MyHeroPro) heroReceive;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
