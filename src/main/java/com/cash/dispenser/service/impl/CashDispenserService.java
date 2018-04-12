/**
 * 
 */
package com.cash.dispenser.service.impl;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.cash.dispenser.common.CashDispenserException;
import com.cash.dispenser.model.Data;
import com.cash.dispenser.model.DenominationResponse;
import com.cash.dispenser.service.CashDispenserServiceI;

/**
 * @author Sravan Maddipati
 *
 */
@Service
public class CashDispenserService implements CashDispenserServiceI {

	private static final Logger LOGGER = LogManager.getLogger(CashDispenserService.class);

	private int[] count = { 0, 0, 0, 0, 0 };

	/**
	 * @see com.cash.dispenser.service.CashDispenserServiceI#cashWithdrawal(int)
	 */
	@Override
	public String cashWithdrawal(int amount) throws CashDispenserException {
		LOGGER.info("Cash Withdrawal Service call with Amount : " + amount);
		int totalAvailableBalance = getAvailableBalance();
		if (amount % 10 != 0) {
			return "Please enter the amount in multiples of 10";
		} else {
			if (amount <= totalAvailableBalance) {
				for (int i = 0; i < Data.denominationsAccepted.length; i++) {
					if (Data.denominationsAccepted[i] <= amount) {
						int noteCount = amount / Data.denominationsAccepted[i];
						if (Data.availableDenominations[i] > 0) {
							count[i] = noteCount >= Data.availableDenominations[i] ? Data.availableDenominations[i]
									: noteCount;
							Data.availableDenominations[i] = noteCount >= Data.availableDenominations[i] ? 0
									: Data.availableDenominations[i] - noteCount;
							totalAvailableBalance = totalAvailableBalance - (count[i] * Data.denominationsAccepted[i]);
							amount = amount - (count[i] * Data.denominationsAccepted[i]);
						}
					}
				}

			} else {
				LOGGER.info("Cash Withdrawal Service unable to Process Amount : " + amount);
				return "Unable to Dispense the Amount Entered : " + amount;
			}
			return "Successfull Withdrawal";
		}
		// return null;
	}

	/**
	 * @see com.cash.dispenser.service.CashDispenserServiceI#getAvailableBalance()
	 */
	@Override
	public int getAvailableBalance() throws CashDispenserException {
		LOGGER.info("getAvailableBalance service call");
		int availableBalance = 0;
		for (int i = 0; i < Data.availableDenominations.length; i++) {
			availableBalance = availableBalance + Data.availableDenominations[i] * Data.denominationsAccepted[i];
		}
		LOGGER.info("getAvailableBalance service call, available Balance = " + availableBalance);
		return availableBalance;
	}

	/**
	 * DisplayAvailable Denominations.
	 */
	@Override
	public DenominationResponse getAvailableDenominations() throws CashDispenserException {
		LOGGER.info("getAvailableDenominations service call");
		DenominationResponse response = new DenominationResponse();
		HashMap<Integer, Integer> availableNotes = new HashMap<>();
		for (int i = 0; i < Data.denominationsAccepted.length; i++) {
			availableNotes.put(Data.denominationsAccepted[i], Data.availableDenominations[i]);
		}
		response.setMessage("Sucess");
		response.setAvaiableNotes(availableNotes);
		LOGGER.info("getAvailableDenominations service call, available Denominations = " + availableNotes);
		return response;

	}
}
