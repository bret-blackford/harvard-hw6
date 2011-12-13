package cscie160.hw6;

import java.util.LinkedList;
import java.util.logging.Logger;

public class ATMThread extends Thread {
    
    /** Queue of requests (a reference to global queue)*/
    private final LinkedList<ATMRunnable> queue;
    /** Request ID */
    private final int id;
    /** Maximum ID ever assigned to an ATMThread object */
    private static int maxID = 0;
    

    public ATMThread(LinkedList<ATMRunnable> queue)  {
        this.queue = queue;
        id = maxID++;
    }


    public String toString()  {
        return "Thread " + id;
    }


    public void run() {
        System.out.println(a("run") + this + " entering -" );
        while (true) {
            ATMRunnable runnable = null;
            try {
                System.out.println(a("run") + this + " entering iteration in run() -" );
                synchronized (queue) {
                    System.out.println(a("run") +this + " entering synchronized block in run() -" );
                    while (queue.isEmpty()) {
                        try {
                            System.out.println(a("run") + this + " waiting -");
                            queue.wait();
                            System.out.println(a("run") + this + " waiting done -");
                        } catch (InterruptedException e) {
                            System.out.println(a("run") + this + " interrupted -");
                            return;
                        }
                    }
                    
                    runnable = queue.poll(); //Retrieves and removes the head (first element) of this list. 
                    runnable.lockForClient();
                    System.out.println(a("run") + this + " receiving " + runnable);
                }
                
                System.out.println(a("run") + this + " running " + runnable);
                runnable.run();
                System.out.println(a("run") + this + " done running " + runnable);
            } finally {
                runnable.unlockForClient();
            }
        }
    }
    
    private String a(String _method) {
        String tempString = "|" + DateUtils.now() + "| ATMThread." + _method + "()";
        return tempString;
    }

}

