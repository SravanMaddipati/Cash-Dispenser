/**
 * 
 */
package com.cash.dispenser.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cash.dispenser.common.CashDispenserException;
import com.cash.dispenser.model.DenominationResponse;
import com.cash.dispenser.service.CashDispenserServiceI;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author Sravan Maddipati
 *
 */
@RestController
@RequestMapping(value = "/cd/v1")
@Api(value = "Cash Dispenser Contoller")
public class CashDispenserController {

	private static final Logger LOGGER = LogManager.getLogger(CashDispenserController.class);

	@Autowired
	private CashDispenserServiceI cashDispenserService;

	@ApiOperation(value = "Cash Withdrawal", notes = "Dispense the cash entered by the user")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS", response = String.class),
			@ApiResponse(code = 500, message = "Unexpected error occured") })
	@RequestMapping(value = "/withdrawcash/{amount}", method = RequestMethod.PUT)
	public String cashWithdrawal(@PathVariable int amount) throws CashDispenserException {
		LOGGER.info("Cash Dispenser Controller call : Dispense Amount method" + amount);
		return cashDispenserService.cashWithdrawal(amount);

	}

	@ApiOperation(value = "Total Balance", notes = "Total available balance")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS", response = Integer.class),
			@ApiResponse(code = 500, message = "Unexpected error occured") })
	@RequestMapping(value = "/totalbalance", method = RequestMethod.GET)
	public int getAvailableBalance() throws CashDispenserException {
		return cashDispenserService.getAvailableBalance();

	}

	@ApiOperation(value = "Available Denominations", notes = "Total available Denominations")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "SUCCESS", response = DenominationResponse.class),
			@ApiResponse(code = 500, message = "Unexpected error occured") })
	@RequestMapping(value = "/availabledenominations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public DenominationResponse getAvailableDenominations() throws CashDispenserException {
		return cashDispenserService.getAvailableDenominations();

	}
}
