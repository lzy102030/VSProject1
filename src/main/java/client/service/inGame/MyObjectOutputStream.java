package client.service.inGame;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

//覆写原方法 实现多对象连续传输
public class MyObjectOutputStream extends ObjectOutputStream {
    public MyObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }

    //覆写流头标记 使其不传输 即可实现多对象连续传输不报错
    public void writeStreamHeader() {
    }
}
