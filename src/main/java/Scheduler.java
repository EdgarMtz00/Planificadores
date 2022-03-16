import java.util.ArrayDeque;
import java.util.Queue;

public class Scheduler {
    private final ArrayDeque<ThreadWork> jobsQueue = new ArrayDeque<>();

    public void addJob(ThreadWork job) {
        jobsQueue.add(job);
    }

    public void run() {
        for (ThreadWork job : jobsQueue) {
            try {
                new Thread(job).start();
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
