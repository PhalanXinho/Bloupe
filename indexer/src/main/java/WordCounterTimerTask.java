import java.util.TimerTask;

public class WordCounterTimerTask extends TimerTask {
    private int count;

    public WordCounterTimerTask() {
        this.count = 0;
    }

    public void increaseCount() {
        this.count++;
    }

    public void run() {
        System.out.println(count);
        this.count = 0;
    }
}
