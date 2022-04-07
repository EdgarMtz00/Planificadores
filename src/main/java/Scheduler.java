import java.util.ArrayList;

public class Scheduler extends Thread {
    private final ArrayList<ThreadWork> jobsQueue = new ArrayList<>();

    public synchronized void addJob(ThreadWork job) {
        jobsQueue.add(job);
    }

    public synchronized void removeJob(ThreadWork t) {
        jobsQueue.remove(t);
    }

    public void endSlice(ThreadWork t) {
        synchronized(t) {
            synchronized (this) {
                notify();
            }
            try {
                t.wait();
            } catch (Exception e) {
                System.out.println("Unexpected interrupt in endSlice " + e);
            }
        }
    }


    public synchronized void run() {
        while (jobsQueue.size() > 0) {
            ThreadWork nextRunner = jobsQueue.get(0);
            for (ThreadWork w: jobsQueue) {
                if (w.timeLeft < nextRunner.timeLeft){
                    nextRunner = w;
                }
            }
            synchronized (nextRunner) {
                nextRunner.notify();
            }
            try {
                wait();
            } catch (Exception e) {
                System.out.println("Unexpected interrupt in run " + e);
            }
        }
    }
}
