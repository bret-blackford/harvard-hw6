package cscie160.hw6;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import java.util.LinkedList;
import java.util.StringTokenizer;

/** {@code Server} This is the server-side code.
 *
 * @author M. Bret Blackford (20849347)
 * @version 2.0
 * @since November 12, 2011
 */
public class Server {
	
    private ServerSocket serverSocket;
    private ATM atmImplementation;
    private BufferedReader bufferedReader;
    private int portNo;
    
    final LinkedList<ATMRunnable> queue;
    private final static int noOfThreads = 5;
    private final ATMThread[] threadPool;


    /** Default constructor that will create the ATM/ATMImplementation object as well as establish the ServerSocket for client communication
     * @param port
     * @throws java.io.IOException
     */
    public Server(int port) throws java.io.IOException {
        serverSocket = new ServerSocket(port);
        queue = new LinkedList<ATMRunnable>();
        threadPool = new ATMThread[noOfThreads];
        
        for (int i=0; i<noOfThreads; i++) {
            threadPool[i] = new ATMThread(queue);
        }
        
        // Anonymous no-op implementation of interface ATM as place-holder
        // here.  Replace it with a real ATMImplementation that holds an
        // Account object and manages it.
        atmImplementation = new ATMImplementation();
        /* 		{
			public void deposit(float amount) throws ATMException {}
			public void withdraw(float amount) throws ATMException {}
			public Float getBalance() throws ATMException { return new Float(0.0); }
	    }; */
        portNo = port;
    }


    /**serviceClient accepts a client connection and reads lines from the socket.
     * Each line is handed to executeCommand for parsing and execution.
     * @throws java.io.IOException
     */
    public void serviceClient() throws java.io.IOException {
        
        for(int i=0; i<threadPool.length; i++) {
            threadPool[i].start();
        }
        while(true){
            System.out.println(DateUtils.now() + "Server.serviceClient()  Accepting clients now on port [" + portNo + "]");
            Socket clientConnection = serverSocket.accept();
            System.out.println(DateUtils.now() + "Server.serviceClient()  Got a client ... ");
            
            SocketThread readerThread = new SocketThread(queue, clientConnection);
            System.out.println(DateUtils.now() + "Starting " + readerThread);
            
            readerThread.start();
        }
    }
    
    

            
    /**
     * Starts the ATM server for later use by clients
     * @param argv
     */
    public static void main(String[] argv) {
        int port = 1099;

        if (argv.length > 1) {
            try {
                String host = argv[0];
                port = Integer.parseInt(argv[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (argv.length > 0) {
            try {
                port = Integer.parseInt(argv[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            Server server = new Server(port);
            server.serviceClient();
            System.out.println(DateUtils.now() + "Client serviced");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

