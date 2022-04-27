import javax.swing.*;

public interface IScheduler {
    void addJob(ThreadWork threadWork);

    void removeJob(ThreadWork threadWork);

    void endSlice(ThreadWork threadWork);

    void updateLabel(double working, int process);

    void setLabel(JLabel label);

    void start();
}
