import javax.swing.*;

public class ThreadWork extends Thread {

    private final int totalSteps;
    private final JProgressBar progressGraphic;
    private final IScheduler scheduler;
    private long timeAux;
    public  double timeLeft;

    public ThreadWork(int steps, JProgressBar progressGraphic, IScheduler scheduler) {
        totalSteps = steps;
        timeAux = System.currentTimeMillis();
        timeLeft = steps * .250;
        this.progressGraphic = progressGraphic;
        this.scheduler = scheduler;

        scheduler.updateLabel(0, 1);
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
        double step = 0;
        double working = 0;
        while (step != 100) {
            timeAux = System.currentTimeMillis();
            scheduler.updateLabel(working, 0);
            step += (double) progressGraphic.getMaximum() / totalSteps;
            if (step > 100) {
                step = 100;
            }
            progressGraphic.setValue((int)step);

            try {
                Thread.sleep(222);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            working = (double) (System.currentTimeMillis() - timeAux) / 1000;
            timeAux = System.currentTimeMillis();

            timeLeft -= working;
            scheduler.endSlice(this);
        }
        timeLeft = 0;
        scheduler.updateLabel(working, -1);
        removeFromReadyQueue();
    }
}
