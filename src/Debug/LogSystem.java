package Debug;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

public class LogSystem {
    private static Logger logger;
    private static LogSystem logSystem = new LogSystem();

    private LogSystem() {
        logger = Logger.getLogger("Log");

        try {
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.OFF);
            logger.addHandler(consoleHandler);

            File file = new File("debug/logs");
            file.mkdirs();
            String path = file.getAbsolutePath().replace("\\", "/");
            FileHandler fileHandler = new FileHandler(path + "/log " + getTime(1) + ".log");
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new Formatter() {//定义一个匿名类
                @Override
                public String format(LogRecord record) {
                    return "[" + record.getLevel() + "]" + "[" + getTime(0) + "]"
                            + record.getMessage() + "\n";
                }
            });
            logger.addHandler(fileHandler);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    private String getTime(int format) {
        if (format == 0) {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(date);
        } else {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            return sdf.format(date);
        }
    }

    public static LogSystem getLogSystem() {
        return logSystem;
    }

    public static Logger getLogger() {
        return logger;
    }
}