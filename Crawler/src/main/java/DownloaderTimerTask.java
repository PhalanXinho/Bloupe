import com.hazelcast.collection.ISet;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class DownloaderTimerTask extends TimerTask {
    Config config = new Config();
    HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(config);

    ISet<Integer> usedNumbers = hazelcastInstance.getSet("usedNumbers");

    public void executeTask() {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    new Controller().execute();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        timer.schedule(timerTask, 0, 300000);
    }

    public void run() {
        executeTask();
    }
}