import javax.swing.*;

public class ThreadWork implements Runnable{

    private final int totalSteps;
    private final int id;
    private final JProgressBar progressGraphic;

    public ThreadWork (int steps, int id, JProgressBar progressGraphic) {
        totalSteps = steps;
        this.id = id;
        this.progressGraphic = progressGraphic;
    }

    @Override
    public void run() {
        for (int i = 0; i <= totalSteps; i++) {
            try {
                progressGraphic.setValue(progressGraphic.getValue() + (progressGraphic.getMaximum() / 20));
                System.out.printf("#%d: trabajando%n", id);
                Thread.sleep(350);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
