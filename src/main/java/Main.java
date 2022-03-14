public class Main {
    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler();
        for (int i = 0; i < 3; i ++ ){
            scheduler.addJob(new ThreadWork(10, i));
        }
        scheduler.run();
    }
}
