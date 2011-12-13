package cscie160.hw6;

import java.io.PrintStream;
import java.util.StringTokenizer;

public class ATMRunnable implements Runnable {

        private final ATM atm;
        private final String commandLine;
        private final PrintStream printStream;
        private final int id;  /** Request ID */
        private static int maxID = 0; 
                               
        /** The master thread invoking the runnable */
        private final SocketThread runnerThread;


        public ATMRunnable(ATM atm, String commandLine, PrintStream printStream) {
            super();
            this.runnerThread = (SocketThread)Thread.currentThread();
            this.atm = atm;
            this.commandLine = commandLine;
            this.printStream = printStream;
            id = maxID++;
        }


        public String toString() {
            return String.format("Request %d(%s): %s", id, runnerThread, commandLine); 
        }


        public void run() {
            
            try {
                Float amount = executeCommand(commandLine);
                if (amount != null) { 
                    printStream.println(amount);  // Write it back to the client
                }
            } 
            catch (ATMException atmex) {
                System.out.println("ERROR: " + atmex);
            }

        }


        private Float executeCommand(String commandLine) throws ATMException {
            
            // Break out the command line into String[]
            StringTokenizer tokenizer = new StringTokenizer(commandLine);
            String commandAndParam[] = new String[tokenizer.countTokens()];
            int index = 0;
            while (tokenizer.hasMoreTokens()) {
                commandAndParam[index++] = tokenizer.nextToken();
            }
            
            String command = commandAndParam[0];
            // Dispatch BALANCE request without further ado.
            if (command.equalsIgnoreCase(Commands.BALANCE.toString())) {
                return atm.getBalance();
            }
            // Must have 2nd arg for amount when processing DEPOSIT/WITHDRAW commands
            if (commandAndParam.length < 2) {
                throw new ATMException("Missing amount for command \"" + command + "\"");
            }
            try {
                float amount = Float.parseFloat(commandAndParam[1]);
                
                if (command.equalsIgnoreCase(Commands.DEPOSIT.toString())) {
                    atm.deposit(amount);        
                } else if (command.equalsIgnoreCase(Commands.WITHDRAW.toString())) {
                    atm.withdraw(amount);
                } else {
                    throw new ATMException("Unrecognized command: " + command);
                }
            }  catch (NumberFormatException nfe) {
                throw new ATMException("Unable to make float from input: " + commandAndParam[1]);
            }
            // BALANCE command returned result above.  Other commands return null;
            return null;
        }


        public String getCommandLine() {
            return commandLine;
        }
        

        public void lockForClient() {
            runnerThread.lockClient();
        }

        /**
         * Signal the {@link #runnerThread} that subsequent requests could be processed.
         */
        public void unlockForClient() {
            runnerThread.unlockClient();
        }
    }

