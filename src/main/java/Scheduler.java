import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;

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
        int count = 0;
        while (jobsQueue.size() > 0) {
            if(count >= jobsQueue.size()){
                count = 0;
            }
            ThreadWork nextRunner = jobsQueue.get(count);
            count++;
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
