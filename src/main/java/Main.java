import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame mainFrame = new JFrame("Planificador FCFS");
        mainFrame.setSize(400,400);
        mainFrame.setLayout(new GridLayout(3, 1));

        Scheduler scheduler = new Scheduler();

        //TODO: implementar botones para seleccionar el orden
        for (int i = 0; i < 3; i ++ ){
            JProgressBar progressBar = new JProgressBar();
            scheduler.addJob(new ThreadWork(20, i, progressBar));
            mainFrame.add(progressBar);
        }
        mainFrame.setVisible(true);
        scheduler.run();
    }
}
