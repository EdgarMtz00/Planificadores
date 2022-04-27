import javax.swing.*;
import java.util.ArrayList;

public class RoundRobinScheduler extends Thread implements IScheduler{
    private final ArrayList<ThreadWork> jobsQueue = new ArrayList<>();
    private JLabel label;
    double working = 0;
    int process = 0;

    public void setLabel(JLabel label) {
        this.label = label;
    }

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
        synchronized (t) {
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
        int count = 0;
        while (true) {
            if (!jobsQueue.isEmpty()) {
                if (count >= jobsQueue.size()) {
                    count = 0;
                }
                ThreadWork nextRunner = jobsQueue.get(count);
                count++;
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
}
