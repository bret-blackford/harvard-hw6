package cscie160.hw6;

/** {@code ATM} interface per requirements.
*
* @author M. Bret Blackford (20849347)
* @version 2.0
* @since November 12, 2011
*/
public interface ATM {
	public void deposit(float amount) throws ATMException;
	public void withdraw(float amount) throws ATMException;
	public Float getBalance() throws ATMException;
}
