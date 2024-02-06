import java.util.Scanner;
import java.util.concurrent.Semaphore;
//huzaifa faisal
//csc 460

public class Driver {

    public static void main(String[] args){

        // Constants for sleep times
        final int MAX_SLEEP_TIME = 1000;
        // Process user input and create & start all necessary
        // threads
        // Also responsible for managing the simulation and cleanly
        // exiting the program

        int restroomCapacity = 0;
        int customerCapacity = 0;

        // User input & check for correct capacity
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter capacity of the restroom: ");
        restroomCapacity = scanner.nextInt();
        if (restroomCapacity <= 0) {
            System.out.println("Invalid input, capacity should be above zero");
            return;
        }
        System.out.print("Enter number of customers: ");
        customerCapacity = scanner.nextInt();
        if (customerCapacity <= 0) {
            System.out.println("Invalid input, num of customers should be above zero");
            return;
        }
        scanner.close();

        // Semaphore
        Semaphore capacity = new Semaphore(restroomCapacity, true);

        // Array of customer objects
        Customer[] customers = new Customer[customerCapacity];

        // First simulation
        System.out.println("\nFirst simulation:");
        for (int i = 0; i < customerCapacity; i++) {
            customers[i] = new Customer(i + 1, capacity);
            customers[i].start();
            // Random sleep between each customer's start up
            try {
                Thread.sleep((long) (Math.random() * MAX_SLEEP_TIME));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // Wait for all customers to finish
        for (Customer customer : customers) {
            try {
                customer.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Second simulation
        System.out.println("\nSecond simulation - Classic 70's Rock Concert - consistent lines and delays");
        for (int i = 0; i < customerCapacity; i++) {
            customers[i] = new Customer(i + 1, capacity);
            customers[i].start();
        }
        // Wait for all customers to finish
        for (Customer customer : customers) {
            try {
                customer.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\nEnd of Execution");
    }
}
