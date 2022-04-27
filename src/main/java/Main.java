import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        JFrame mainFrame = new JFrame("Planificador De Multiples Colas");
        mainFrame.setSize(800, 600);
        mainFrame.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        ArrayList<JProgressBar> progressBars = new ArrayList<>();

        ShortestTimeScheduler srtScheduler = new ShortestTimeScheduler();
        FcfsScheduler fcfsScheduler = new FcfsScheduler();
        RoundRobinScheduler rrScheduler = new RoundRobinScheduler();

        IScheduler schedulerArray[] = {srtScheduler, fcfsScheduler, rrScheduler};
        String schedulerNames[] = {"SRT", "FCFS", "RR"};

        for (int i = 0; i <= 4; i += 2) {
            int index = i == 0 ? 0 : i / 2;
            JButton button = new JButton(schedulerNames[index]);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridy = i;
            c.gridx = 0;
            c.weightx = 50;
            c.weighty = 1;
            button.addActionListener(e -> new ThreadWork(random.nextInt(10, 100), progressBars.get(index), schedulerArray[index]));
            mainFrame.add(button, c);

            JProgressBar progressBar = new JProgressBar();
            progressBars.add(progressBar);
            JLabel label = new JLabel();
            schedulerArray[index].setLabel(label);

            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridy = i;
            c.gridx = 1;
            c.weightx = 550;
            c.weighty = 1;
            mainFrame.add(progressBar, c);
            c.weightx = 1;
            c.gridy = i + 1;
            c.gridx = 1;
            mainFrame.add(label, c);
        }

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 12;
        c.gridx = 0;
        c.weightx = 1;
        c.weighty = 1;
        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            for (IScheduler scheduler : schedulerArray) {
                scheduler.start();
            }
        });
        mainFrame.add(startButton, c);
        mainFrame.setVisible(true);
    }
}
