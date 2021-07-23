package Debug;

import client.Service.inGame.MyHeroPro;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class TestMain {
    static Socket connection;
    static ObjectOutputStream serverOut;
    static ObjectInputStream serverIn;
    static TestMain testMain;
    static ConsoleUI consoleUI;

    public static void main(String[] args) {
        consoleUI = new ConsoleUI();
        testMain = new TestMain();

    }

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

        //get the IO streams
        try {
            serverOut = new ObjectOutputStream(connection.getOutputStream());
            serverIn = new ObjectInputStream(connection.getInputStream());
        } catch (IOException e) {
            System.err.println("[ERROR]Cannot get file services the server.");
            e.printStackTrace();
        }

        //remote thread start for checking new contents
        Thread thread = new Thread(new RemoteReader());
        thread.start();
    }

    public ObjectInputStream getServerIn() {
        return serverIn;
    }

    public ObjectOutputStream getServerOut() {
        return serverOut;
    }

    private class RemoteReader implements Runnable {
        @Override
        public void run() {
            ArrayList<MyHeroPro> heroListReceive = null;

            try {
                while (true) {
                    do {
                        try {
                            heroListReceive = (ArrayList<MyHeroPro>) serverIn.readObject();
                        } catch (ClassNotFoundException e) {
                            System.err.println("None Object received from server.");
                            e.printStackTrace();
                        }
                    } while (heroListReceive == null);
                    consoleUI.updateHeroInfo(heroListReceive);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
