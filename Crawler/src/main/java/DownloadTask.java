import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;

public class DownloadTask extends TimerTask {

    Logger logger =  LoggerFactory.getLogger(DownloadTask.class);
    private final Controller controller = new Controller();

    @Override
    public void run() {
        logger.info("Starting the download task...");
        for (int i = 0; i < 5; i++) {
            if (!controller.run()) i--;
        }
        logger.info("Download task finished");
    }
}
