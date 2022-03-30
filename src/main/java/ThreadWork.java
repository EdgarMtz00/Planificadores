import javax.swing.*;

public class ThreadWork extends Thread {

    private final int totalSteps;
    private final JProgressBar progressGraphic;
    private final Scheduler scheduler;

    public ThreadWork(int steps, JProgressBar progressGraphic, Scheduler scheduler) {
        totalSteps = steps;
        this.progressGraphic = progressGraphic;
        this.scheduler = scheduler;
        synchronized (this) {
            start();
            try {
                wait(); // for process to be ready
            } catch (InterruptedException ignored) {
            }
        }
    }

    private void addToReadyQueue() {
        scheduler.addJob(this);
        synchronized (this) {
            notify(); // so constructor completes
            try {
                wait(); // so process is waiting when scheduler starts
            } catch (InterruptedException ignored) {
            }
        }
    }

    private void removeFromReadyQueue() {
        scheduler.removeJob(this);
        scheduler.endSlice(this); // want something else to run!
    }


    @Override
    public void run() {
        addToReadyQueue();
        int step = 0;
        while (step != 100) {
            step = progressGraphic.getValue() + progressGraphic.getMaximum() / totalSteps;
            if (step > 100) {
                step = 100;
            }
            progressGraphic.setValue(step);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            scheduler.endSlice(this);
        }
        removeFromReadyQueue();
    }
}
