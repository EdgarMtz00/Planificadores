public class ThreadWork implements Runnable{

    private final int totalSteps;
    private final int id;

    public ThreadWork (int steps, int id) {
        totalSteps = steps;
        this.id = id;
    }

    @Override
    public void run() {
        for (int i = 0; i <= totalSteps; i++) {
            try {
                //move graphic
                System.out.printf("#%d: trabajando%n", id);
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
