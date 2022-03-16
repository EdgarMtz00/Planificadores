import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        JFrame mainFrame = new JFrame("Planificador FCFS");
        mainFrame.setSize(600,600);
        mainFrame.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        ArrayList<JProgressBar> progressBars = new ArrayList<>();

        Scheduler scheduler = new Scheduler();

        for (int i = 1; i <= 5; i ++ ){
            int index = i;
            JButton button = new JButton("#" + i);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridy = i - 1;
            c.gridx = 0;
            c.weightx = 100;
            c.weighty = 100;
            button.addActionListener(e -> {
                scheduler.addJob(new ThreadWork(20, progressBars.get(index - 1)));
                JButton source = (JButton) e.getSource();
                source.setEnabled(false);
            });
            mainFrame.add(button, c);

            JProgressBar progressBar = new JProgressBar();
            progressBars.add(progressBar);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridy = i - 1;
            c.gridx = 1;
            c.weightx = 500;
            c.weighty = 50;
            mainFrame.add(progressBar,c);
        }

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 6;
        c.gridx = 0;
        c.weightx = 100;
        c.weighty = 50;
        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> scheduler.run());
        mainFrame.add(startButton, c);
        mainFrame.setVisible(true);
    }
}
