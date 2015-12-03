package com.walmart.assignment.ticket.vo;

import java.util.ArrayList;
import java.util.List;

import com.walmart.assignment.ticket.util.IDGenerator;

/*
 * This will act as container object to have the SEAT HOLD information, 
 * will have seats held and this instance will be used during reservation.
 */
public class SeatHold {

	private int id;

	private List<Seat> seats;

	private String customerEmail;

	private String confirmationCode;

	// To define the self expiration period for the hold of the seat
	private long holdExpiresAt;

	public SeatHold(int numberOfSeats, String email) {
		this.id = IDGenerator.getId();
		this.seats = new ArrayList<Seat>(numberOfSeats);
		this.customerEmail = email;
	}

	public int getId() {
		return id;
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public long getHoldExpiresAt() {
		return holdExpiresAt;
	}

	public void setHoldExpiresAt(long holdExpiresAt) {
		this.holdExpiresAt = holdExpiresAt;
	}

	public String getConfirmationCode() {
		return confirmationCode;
	}

	public void setConfirmationCode(String confirmationCode) {
		this.confirmationCode = confirmationCode;
	}

	public boolean isExpired() {
		return System.currentTimeMillis() >= this.holdExpiresAt;
	}

}
