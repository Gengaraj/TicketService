package com.walmart.assignment.ticket.util;

/*
 * Enumseration to hold the static values for Status
 */
public enum SeatStatusCode {

	AVAILABLE('A'), ONHOLD('H'), BOOKED('B');

	private char status;

	private SeatStatusCode(char status) {
		this.status = status;
	}

	public char getStatus() {
		return status;
	}

}
