package client.Service.inGame;

import java.util.LinkedList;

public class MyLinkedList<E> extends LinkedList<E> {
    private int pos = 0;

    public int getPos(){
        return pos++ % this.size();
    }

    public void setPos(int pos){
        this.pos = pos;
    }
}
