import java.util.concurrent.Semaphore;

public class Customer extends Thread{

    //capacity semaphore & mutex semaphore
    private final int id;
    private final Semaphore capacity;
    private static int count = 0;
    private static Semaphore mutex = new Semaphore(1);

    public Customer(int id, Semaphore capacity) {
        // constructor to initialize instance variables
        this.id = id;
        this.capacity = capacity;
    }

    @Override
    public void run() {
        try {
            capacity.acquire(); // acquire capacity semaphore
            mutex.acquire(); // acquire mutex semaphore
            count++; // increment count
            mutex.release(); // release mutex semaphore
            System.out.println("Customer " + id + " is entering the restroom.");
            int sleepTime = (int) (500 + Math.random() * 501); // generate random sleep time between 500 and 1000
            Thread.sleep(sleepTime);
            System.out.println("Customer " + id + " is using the facilities.");
            sleepTime = (int) (500 + Math.random() * 501); // generate random sleep time between 500 and 1000
            Thread.sleep(sleepTime);
            System.out.println("Customer " + id + " is flushing.");
            mutex.acquire(); // acquire mutex semaphore
            count--; // decrement count
            if (count == 0) {
                System.out.println("RESTROOM IS EMPTY");
            }
            mutex.release(); // release mutex semaphore
            capacity.release(); // release capacity semaphore
            System.out.println("Customer " + id + " is leaving the restroom.");
        } catch (InterruptedException e) {
            System.err.println("Interrupted while waiting for restroom access");
        }
    }
}
