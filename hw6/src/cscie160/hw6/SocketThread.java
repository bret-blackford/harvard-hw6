package cscie160.hw6;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import java.net.Socket;

import java.net.SocketException;

import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;
import java.io.InputStream;

import java.io.OutputStream;

import java.util.logging.Logger;

 /** {@code SocketThread} Used by the Server for SocketReader connections (not RMI connections).
  *
  * @author M. Bret Blackford (20849347)
  * @version 2.0
  * @since November 12, 2011
  */
public class SocketThread extends Thread
{
    /**Incoming connection*/
    protected final Socket clientConnection;
    /**Connection's reader*/
    private BufferedReader bufferedReader;

    private ATM atmImplementation;
    /** maximum number to assign to a socket listening thread */
    private static int maxThreadID = 0;
    
    private final int id;  /**Client Connection's ID*/
    
    /**Lock for preventing async requests from the same client to be executed concurrently.
     * Can be Locked() or Unlocked() 
     * Replacement for the traditional “wait-notify” method
     * example code http://robaustin.wikidot.com/reentrantlock **/ 
    private ReentrantLock lock = new ReentrantLock();
    
    /** Queue of requests (a reference to global queue)*/
    private final LinkedList<ATMRunnable> reqQueue;


    protected SocketThread(LinkedList<ATMRunnable> _queue, Socket _clientConnection) {
        this.reqQueue = _queue;
        this.clientConnection = _clientConnection;
        atmImplementation = new ATMImplementation();
        id = maxThreadID++; //increment the connection counter 
    }


    public String toString() {
        return " client connection:" + id ;
    }


    public void run() {
        try {
            // Read input from the Socket
            InputStream inputStream = clientConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(
                    inputStream));

            // Write result across Socket back to client
            OutputStream outputStream = clientConnection.getOutputStream();
            PrintStream printStream = new PrintStream(outputStream);

            String commandLine;
            
            while ((commandLine = bufferedReader.readLine()) != null) {
                ATMRunnable runnable = new ATMRunnable(atmImplementation,
                        commandLine, printStream);
                
                System.out.println(" adding " + runnable);
                
                synchronized (reqQueue) {
                    reqQueue.add(runnable);
                    reqQueue.notify();
                }
            }
            System.out.println(this + " done reading -- end");
        } catch (SocketException socketException) {
            // client has stopped sending commands.
            System.out.println(this + " done reading");
            System.out.println(this + " " + socketException);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    

    public void lockClient() {
        lock.lock();
    }
    

    public void unlockClient() {
        lock.unlock();
    }
}

