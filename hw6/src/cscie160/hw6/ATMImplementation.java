package cscie160.hw6;

/** {@code ATMImplementation} This class implements the ATM interface.
*
* @author M. Bret Blackford (20849347)
* @version 2.0
* @since November 12, 2011
*/
public class ATMImplementation implements ATM {

  private Account account;

  /** Default contructore which creates an Account object
   */
  public ATMImplementation() {
      super();
      System.out.println(DateUtils.now() + " <<>> constructing ATMImplementation()");
      account = new Account();
  }

  /**Will attempt to deposit amount passed in to the single account
   * @param amount
   * @throws cscie.hw6.ATMException
   */
  public void deposit(float amount) throws ATMException {
      System.out.println(DateUtils.now() + "in ATMImplementation.depoist()");
      Float balance = account.getBalance();
      balance += amount;
      account.setBalance(balance);
      System.out.println(DateUtils.now() + "in ATMImplementation.depoist() adding " + balance.toString());
  }

  /**Attempts to withdraw funds from the Account object.  If withdraw request exceeds current existing balance then an error message will be displayed by the server (but processing with the client continues).
   * @param amount
   * @throws cscie.hw6.ATMException
   */
  public void withdraw(float amount) throws ATMException {
      System.out.println(DateUtils.now() + "in ATMImplementation.withdraw()");
      Float balance = account.getBalance();

      if (amount > balance) {
          String msg = "\n   <*><*><*> withdraw request greater than account balance <*><*><*> \n";
          System.out.println(msg);
          //throw new ATMException(msg);
      } else {
          balance -= amount;
          account.setBalance(balance);
          System.out.println(DateUtils.now() + "in ATMImplementation.withdraw() reducing balance to " + balance.toString());
      }
  }

  /**Returns the current balance in the Account object
   * @return
   * @throws cscie.hw6.ATMException
   */
  public Float getBalance() throws ATMException {

      Float balance = account.getBalance();
      System.out.println(DateUtils.now() + "in ATMImplementation.getBalance() returning " + balance.toString());
      return balance;
  }
}

