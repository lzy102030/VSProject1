package Debug;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;


class LoggerFormatter extends Formatter {

    //[TODO] log格式化
    @Override
    public String format(LogRecord record) {

        return "";
    }
}

public class Log {

    public static void main(String[] args) throws IOException {
        Logger logger = Logger.getLogger("");

        try {
            FileHandler fileHandler = new FileHandler("D:/logs/1.txt");
            fileHandler.setLevel(Level.INFO);
            fileHandler.setFormatter(new Formatter() {//定义一个匿名类
                @Override
                public String format(LogRecord record) {
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String datef = sdf.format(date);

                    return "[" + record.getLevel() + "]" + "[" + datef + "]"
                            + "[" + this.getClass().getName() + "] " + record.getMessage() + "\n";
                }
            });
            logger.addHandler(fileHandler);
            logger.info("测试");
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }

        /*

        Logger log = Logger.getLogger("");
        log.setLevel(Level.INFO);
        Logger log1 = Logger.getLogger("lavasoft");
        System.out.println(log == log1);     //true
        Logger log2 = Logger.getLogger("lavasoft.blog");
        //log2.setLevel(Level.WARNING);
//控制台控制器
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        log.addHandler(consoleHandler);
        //文件控制器
        FileHandler fileHandler = new FileHandler("D:/logs/1.txt");
        fileHandler.setLevel(Level.INFO);
        fileHandler.setFormatter(new SimpleFormatter());
        log.addHandler(fileHandler);
        log.info("aaa");
        log2.info("bbb");
        log2.fine("fine");

         */
    }
}