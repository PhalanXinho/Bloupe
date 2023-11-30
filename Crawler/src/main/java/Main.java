import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;

public class Main {

    public static void main(String[] args) {

        Logger logger = LoggerFactory.getLogger(Main.class);
        logger.info("Starting the application");

        TimerTask downloadTask = new DownloadTask();
        Timer timer = new Timer();
        timer.schedule( downloadTask, 0, 1000*60*5);
    }
}
