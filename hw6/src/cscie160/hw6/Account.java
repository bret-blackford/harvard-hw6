package cscie160.hw6;



/** {@code Account} is a class to model a simple bank account.
 *
 * @author M. Bret Blackford (20849347)
 * @version 2.0
 * @since November 12, 2011
 */
public class Account {
   
   String nameOfAccountHolder = "John Doe";
   Float balance = new Float(0.0);

   /** Default constructor. Does not do much.
    */
   public Account() {
       super();
       System.out.println("\n Account()");
   }

   /** Used to increment account balance. Does not override/replace current account balance with value passed in but increments the current account balance.
    * Method is now thread safe (uses synchronized())
    * @param balance
    */
   public void setBalance(Float _balance) {
       System.out.println("in Account.setBalance() ...");
       
       synchronized(this) {
           System.out.println( "in synchronized § of Account.setBalance()");
           balance = _balance;
       }
   }

   /** Used to obtain the current account balance
    *  Method is now thread safe (uses synchronized())
    * @return
    */
   public Float getBalance() {
       System.out.println("in Account.getBalance() ...");
       
       synchronized(this) {
           System.out.println( "in synchronized § of Account.getBalance()");
           return balance;
       }
   }
}

