import javax.swing.*;

public class ThreadWork extends Thread {

    private final int totalSteps;
    private final JProgressBar progressGraphic;
    private final Scheduler scheduler;
    private final JLabel timeLabel;
    private long timeAux;
    public  double timeLeft;
    private double waiting;

    public ThreadWork(int steps, JProgressBar progressGraphic, Scheduler scheduler, JLabel timeLabel) {
        totalSteps = steps;
        timeAux = System.currentTimeMillis();
        timeLeft = steps * .250;
        waiting = 0;
        this.progressGraphic = progressGraphic;
        this.scheduler = scheduler;
        this.timeLabel = timeLabel;

        updateLabel(0,0,0);
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

    private void updateLabel(double total, double working, double waiting) {
        String timeText = "Tiempo Total: %.2f s, Teimpo de Ejecucion: %.2f s, Tiempo Esperando: %.2f s, Tiempo Restante: %.2f s";
        timeLabel.setText(String.format(timeText, total, working, waiting, timeLeft));
    }

    private void removeFromReadyQueue() {
        scheduler.removeJob(this);
        scheduler.endSlice(this); // want something else to run!
    }


    @Override
    public void run() {
        addToReadyQueue();
        double step = 0;
        double total = 0;
        double working = 0;
        while (step != 100) {
            waiting += (double)(System.currentTimeMillis() - timeAux) / 1000;
            total += (double) (System.currentTimeMillis() - timeAux) / 1000;
            timeAux = System.currentTimeMillis();
            updateLabel(total, working, waiting);
            step = progressGraphic.getValue() + progressGraphic.getMaximum() / totalSteps;
            if (step > 100) {
                step = 100;
            }
            progressGraphic.setValue((int)step);

            try {
                Thread.sleep(222);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            working += (double) (System.currentTimeMillis() - timeAux) / 1000;
            total += (double) (System.currentTimeMillis() - timeAux) / 1000;
            timeAux = System.currentTimeMillis();
            updateLabel(total, working, waiting);
            timeLeft -= .222;
            scheduler.endSlice(this);
        }
        timeLeft = 0;
        updateLabel(total, working, waiting);
        removeFromReadyQueue();
    }
}
