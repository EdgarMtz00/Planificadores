import javax.swing.*;

public class ThreadWork implements Runnable{

    private final int totalSteps;
    private final JProgressBar progressGraphic;

    public ThreadWork (int steps, JProgressBar progressGraphic) {
        totalSteps = steps;
        this.progressGraphic = progressGraphic;
    }

    @Override
    public void run() {
        for (int i = 0; i <= totalSteps; i++) {
            try {
                progressGraphic.setValue(progressGraphic.getValue() + (progressGraphic.getMaximum() / 20));
                Thread.sleep(350);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
