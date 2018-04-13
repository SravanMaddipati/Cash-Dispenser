/**
 * 
 */
package com.cash.dispenser.service.impl;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.cash.dispenser.common.CashDispenserException;
import com.cash.dispenser.model.CashDispenserResponse;
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

	private static final String ODD_AMOUNT_MESSAGE = "Please enter the amount in multiples of 10";
	private static final String NEGATIVE_AMOUNT_MESSAGE = "Cannot Procees the amount Entered";
	private static final String EXCESS_AMOUNT_MESSAGE = "Unable to Dispense the Amount Entered : ";
	private static final String WITHDRAWAL_SUCCESS_MESSAGE = "Successfull Withdrawal";
	private static final String SUCCESS_MESSAGE = "SUCCESS";

	/**
	 * @see com.cash.dispenser.service.CashDispenserServiceI#cashWithdrawal(int)
	 */
	@Override
	public CashDispenserResponse cashWithdrawal(int amount) throws CashDispenserException {
		LOGGER.info("Cash Withdrawal Service call with Amount : " + amount);
		CashDispenserResponse response = new CashDispenserResponse();
		response.setAmount(amount);
		CashDispenserResponse totalAvailableBalance = getAvailableBalance();
		int availableBalance = totalAvailableBalance.getAmount();
		if (amount > 0 && amount % 10 != 0) {
			response.setMessage(ODD_AMOUNT_MESSAGE);
			return response;
		} else if (amount < 0) {
			response.setMessage(NEGATIVE_AMOUNT_MESSAGE);
			return response;
		} else {
			if (amount <= availableBalance) {
				for (int i = 0; i < Data.denominationsAccepted.length; i++) {
					if (Data.denominationsAccepted[i] <= amount) {
						int noteCount = amount / Data.denominationsAccepted[i];
						if (Data.availableDenominations[i] > 0) {
							count[i] = noteCount >= Data.availableDenominations[i] ? Data.availableDenominations[i]
									: noteCount;
							Data.availableDenominations[i] = noteCount >= Data.availableDenominations[i] ? 0
									: Data.availableDenominations[i] - noteCount;
							availableBalance = availableBalance - (count[i] * Data.denominationsAccepted[i]);
							amount = amount - (count[i] * Data.denominationsAccepted[i]);
						}
					}
				}

			} else {
				LOGGER.info("Cash Withdrawal Service unable to Process Amount : " + amount);
				response.setMessage(EXCESS_AMOUNT_MESSAGE + amount);
				return response;
			}
			response.setMessage(WITHDRAWAL_SUCCESS_MESSAGE);
			return response;
		}
	}

	/**
	 * @see com.cash.dispenser.service.CashDispenserServiceI#getAvailableBalance()
	 */
	@Override
	public CashDispenserResponse getAvailableBalance() throws CashDispenserException {
		CashDispenserResponse availableBalanceResp = new CashDispenserResponse();
		LOGGER.info("getAvailableBalance service call");
		int availableBalance = 0;
		for (int i = 0; i < Data.availableDenominations.length; i++) {
			availableBalance = availableBalance + Data.availableDenominations[i] * Data.denominationsAccepted[i];
		}
		availableBalanceResp.setAmount(availableBalance);
		availableBalanceResp.setMessage(SUCCESS_MESSAGE);
		LOGGER.info("getAvailableBalance service call, available Balance = " + availableBalance);
		return availableBalanceResp;
	}

	/**
	 * DisplayAvailable Denominations
	 */
	@Override
	public DenominationResponse getAvailableDenominations() throws CashDispenserException {
		LOGGER.info("getAvailableDenominations service call");
		DenominationResponse response = new DenominationResponse();
		HashMap<Integer, Integer> availableNotes = new HashMap<>();
		for (int i = 0; i < Data.denominationsAccepted.length; i++) {
			availableNotes.put(Data.denominationsAccepted[i], Data.availableDenominations[i]);
		}
		response.setMessage(SUCCESS_MESSAGE);
		response.setAvaiableNotes(availableNotes);
		LOGGER.info("getAvailableDenominations service call, available Denominations = " + availableNotes);
		return response;

	}
}
