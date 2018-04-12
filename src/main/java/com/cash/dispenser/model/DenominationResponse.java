/**
 * 
 */
package com.cash.dispenser.model;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author Sravan Maddipati
 *
 */
public class DenominationResponse implements Serializable {

	private static final long serialVersionUID = 4051745965181981744L;

	private String message = "";

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the avaiableNotes
	 */
	public HashMap<Integer, Integer> getAvaiableNotes() {
		return avaiableNotes;
	}

	HashMap<Integer, Integer> avaiableNotes = new HashMap<>();

	/**
	 * @param avaiableNotes
	 *            the avaiableNotes to set
	 */
	public void setAvaiableNotes(HashMap<Integer, Integer> avaiableNotes) {
		this.avaiableNotes = avaiableNotes;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
