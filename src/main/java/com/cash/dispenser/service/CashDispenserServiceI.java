/**
 * 
 */
package com.cash.dispenser.service;

import com.cash.dispenser.common.CashDispenserException;
import com.cash.dispenser.model.DenominationResponse;

/**
 * @author Sravan Maddipati
 *
 */
public interface CashDispenserServiceI {

	public String cashWithdrawal(int amount) throws CashDispenserException;

	public int getAvailableBalance() throws CashDispenserException;

	public DenominationResponse getAvailableDenominations() throws CashDispenserException;

}
