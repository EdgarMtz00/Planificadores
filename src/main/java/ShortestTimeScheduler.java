import javax.swing.*;
import java.util.ArrayList;

public class ShortestTimeScheduler extends Thread implements IScheduler{
    private final ArrayList<ThreadWork> jobsQueue = new ArrayList<>();
    private JLabel label;
    double working = 0;
    int process = 0;

    @Override
    public synchronized void addJob(ThreadWork job) {
        jobsQueue.add(job);
    }

    @Override
    public synchronized void removeJob(ThreadWork t) {
        jobsQueue.remove(t);
    }

    @Override
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

    @Override
    public void updateLabel(double working, int process) {
        if (label != null){
            this.working += working;
            this.process += process;

            label.setText(String.format("Procesos: %d, Tiempo Trabajado: %f", this.process, this.working));
        }
    }

    public synchronized void run() {
        while (true) {
            if (!jobsQueue.isEmpty()) {
                ThreadWork nextRunner = jobsQueue.get(0);
                for (ThreadWork w : jobsQueue) {
                    if (w.timeLeft < nextRunner.timeLeft) {
                        nextRunner = w;
                    }
                }
                synchronized (nextRunner) {
                    nextRunner.notify();
                }
                try {
                    wait();
                } catch (Exception ignored) {
                }
            }
        }
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }
}
