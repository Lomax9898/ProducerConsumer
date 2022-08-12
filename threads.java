

import java.util.*;

/**
 *
 * @author Kevin
 */
public class threads {

    boolean done = false;
    int count = 0;

    public static void main(String[] args) {
        int ten = 10;
        int hundred = 100;
        int zero = 0;
        int to_do_p1 = (int) (Math.random() * (hundred - zero + 1) + zero);
        int to_do_c1 = (int) (Math.random() * (hundred - zero + 1) + zero);
        threads myObj1 = new threads(); // Will help me stop the threads
        threads myObj2 = new threads();
        List<Integer> buffer = new ArrayList<>(ten);
        for (int i = 0; i < 10; i++) {// Arraylist buffer filled with zeroes
            buffer.add(0);
        }

        System.out.println("The buffer is of size " + ten);
        Thread P1 = new Thread(()// Producer thread
                -> {
            int min = 0, max = 20, Fullcheck;
            int b;
            while (!myObj1.done) {
                synchronized (buffer) {
                    if (myObj2.done) { // Checks if the other thread has finished
                        for (int i = 0; i < 10; i++) {
                            if (buffer.get(i) == 0) {
                                System.out.printf("Prodcuer setting %d to Full\n", i);
                                buffer.set(i, 1);
                            }
                        }
                        System.out.printf("Producer filled %d\n", myObj1.count);
                        System.out.println("Producer is ending program");
                        System.exit(0);
                    }
                    Fullcheck = 0;
                    for (int i = 0; i < 10; i++) {
                        if (buffer.get(i) == 1) {
                            Fullcheck++;
                        }
                    }
                    if (Fullcheck == 10) {// For checking if buffer is full
                        System.out.println("The buffer is Full, Producer is waiting");
                        System.out.println("Producer is giving control to consumer");
                        try {
                            buffer.wait(); //Pauses the thread here
                        } catch (InterruptedException e) {
                            System.out.println("Interrupted");
                        }
                    }
                    for (int i = 0; i < 10; i++) {
                        b = (int) (Math.random() * (max - min + 1) + min);
                        if (buffer.get(i) == 0) {
                            System.out.printf("Prodcuer setting %d to Full\n", i);
                            buffer.set(i, 1);
                            myObj1.count++;
                        }
                        if (b >= 19) {// Chance of the thread giving control over to the other thread
                            if (myObj2.done) {
                                break; //This is to make the loop go back to the if statement at the start
                            }
                            System.out.println("Producer is giving control to consumer");
                            buffer.notify();//Lets the other thread take over buffer
                            try {

                                buffer.wait();
                            } catch (InterruptedException e) {
                                System.out.println("Interrupted");
                            }
                            break;
                        }
                    }
                    if (to_do_p1 <= myObj1.count) {
                        System.out.printf("Producer has reach it's limit of %d filled\n", to_do_p1);
                        System.out.println("producer is giving control to Consumer");
                        myObj1.done = true;
                    }
                    buffer.notify();
                }
            }
        });

        Thread C1 = new Thread(()
                -> {

            int min = 0;
            int max = 20;
            int emptycheck;
            int b;
            while (!myObj2.done) {
                synchronized (buffer) {
                    if (myObj1.done) {
                        for (int i = 0; i < 10; i++) {
                            if (buffer.get(i) == 1) {
                                System.out.printf("Consumer setting %d to Empty\n", i);
                                buffer.set(i, 0);
                            }
                        }
                        System.out.printf("Consumer emptied %d\n", myObj2.count);
                        System.out.println("Consumer is ending program");
                        System.exit(0);
                    }
                    emptycheck = 0;
                    for (int i = 0; i < 10; i++) {
                        if (buffer.get(i) == 0) {
                            emptycheck++;
                        }
                    }
                    if (emptycheck == 10) {
                        System.out.println("The buffer is empty, consumer is waiting");
                        System.out.println("Consumer giving control to Producer");
                        try {

                            buffer.wait();
                        } catch (InterruptedException e) {
                            System.out.println("Interrupted");
                        }
                    }

                    for (int i = 0; i < 10; i++) {
                        b = (int) (Math.random() * (max - min + 1) + min);
                        if (buffer.get(i) == 1) {
                            System.out.printf("Consumer setting %d to Empty\n", i);
                            buffer.set(i, 0);
                            myObj2.count++;
                        }
                        if (b >= 19) {
                            if (myObj1.done) {
                                break;
                            }
                            System.out.println("Consumer giving control to Producer");
                            buffer.notify();
                            try {

                                buffer.wait();
                            } catch (InterruptedException e) {
                                System.out.println("Interrupted");
                            }

                            break;
                        }
                    }
                    if (to_do_c1 <= myObj2.count) {
                        System.out.printf("Consumer has reach it's limit of %d emptied\n", to_do_c1);
                        System.out.println("Consumer is giving control to Producer");
                        myObj2.done = true;
                    }
                    buffer.notify();
                }
            }
        });
        P1.start(); //Starting Threads
        C1.start();
    }
}
