import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;

public class WordCounterTimerTask extends TimerTask {
    private int count;
    private final Logger logger = LoggerFactory.getLogger(WordCounterTimerTask.class);

    public WordCounterTimerTask() {
        this.count = 0;
    }

    public void increaseCount() {
        this.count++;
    }

    public void run() {
        logger.info("Word indexed in the last minute: " + count);
        this.count = 0;
    }
}
