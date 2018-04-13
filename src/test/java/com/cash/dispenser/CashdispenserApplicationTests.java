package com.cash.dispenser;

import static org.junit.Assert.assertNotNull;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cash.dispenser.common.CashDispenserException;
import com.cash.dispenser.model.CashDispenserResponse;
import com.cash.dispenser.model.DenominationResponse;
import com.cash.dispenser.service.impl.CashDispenserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CashDispenserService.class)
public class CashdispenserApplicationTests {

	private static final String SUCCESS_MESSAGE = "Successfull Withdrawal";
	private static final String ERROR_MESSAGE = "Please enter the amount in multiples of 10";
	private static final String ERROR_MESSAGE_EXCESS = "Unable to Dispense the Amount Entered : ";

	@Autowired
	CashDispenserService cashDispenserService;

	@Test
	public void testAvailableDenominations() throws CashDispenserException {
		DenominationResponse response = new DenominationResponse();
		response = cashDispenserService.getAvailableDenominations();
		assertNotNull(response);
	}

	@Test
	public void positiveTestDispenseAmount() throws CashDispenserException {
		int amount = 100;
		CashDispenserResponse response = cashDispenserService.cashWithdrawal(amount);
		Assert.assertEquals(SUCCESS_MESSAGE, response.getMessage());
	}

	@Test
	public void NegativeTestDispenseAmount() throws CashDispenserException {
		int amount = 606;
		CashDispenserResponse response = cashDispenserService.cashWithdrawal(amount);
		Assert.assertEquals(ERROR_MESSAGE, response.getMessage());
	}

	@Test
	public void NegativeTest2DispenseAmount() throws CashDispenserException {
		int amount = 200000;
		CashDispenserResponse response = cashDispenserService.cashWithdrawal(amount);
		Assert.assertEquals(ERROR_MESSAGE_EXCESS + amount, response.getMessage());
	}

}