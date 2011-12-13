package cscie160.hw6;

/** {@code ATMException} This exception is used in the ATM exercise and can be usefull when a deposit is requested in excess of the current balance.
*
* @author M. Bret Blackford (20849347)
* @version 1.0
* @since October 12, 2011
*/
public class ATMException extends Exception {
   
  public ATMException(String msg) {
		super(msg);
  }
}
