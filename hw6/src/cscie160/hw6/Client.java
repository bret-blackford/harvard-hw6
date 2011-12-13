package cscie160.hw6;

/** {@code Client} Client-side testing per requirements.
*
* @author M. Bret Blackford (20849347)
* @version 2.0
* @since November 12, 2011
*/
public class Client {
  public static void main(String[] args) {
      try {
          // parse command line arguments
          String host = args[0];
          int port = Integer.parseInt(args[1]);

          ATM atm = new ATMProxy(host, port);
          System.out.println();

          // get initial account balance
          System.out.println(DateUtils.now() + "| Balance: " + atm.getBalance());
          System.out.println();
          
          // make $1000 deposit and get new balance
          System.out.println(DateUtils.now() + "| ** Depositing: 1000");
          atm.deposit(1000);
          System.out.println("| Balance: " + atm.getBalance());
          System.out.println();
          
          // make $250 withdrawal and get new balance
          System.out.println(DateUtils.now() + "| ** Withdrawing: 250");
          atm.withdraw(250);
          System.out.println("| Balance: " + atm.getBalance());
          System.out.println();
          
          // make $750 withdrawal and get new balance
          System.out.println(DateUtils.now() + "| ** Withdrawing: 750");
          atm.withdraw(750);
          System.out.println(DateUtils.now() + "| Balance: " + atm.getBalance());
          System.out.println();

/* INFORMATION BELOW FOR ADDITIONAL TESTING
          atm.withdraw(123456);
          System.out.println("| Balance: " + atm.getBalance());
          System.out.println();
          
          atm.deposit(1);
          System.out.println("Balance: " + atm.getBalance());
          System.out.println(); 
*/

      } catch (Exception ae) {
          System.out.println("An exception occurred while communicating with the ATM");
          ae.printStackTrace();
      }
  }
}

