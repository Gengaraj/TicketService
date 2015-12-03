package com.walmart.assignment.ticket.vo;

import com.walmart.assignment.ticket.util.SeatStatusCode;

/*Seat Template to create instances during Venue Level initialization
 * Core Component :)
 */
public class Seat {

	private long seatNumber;

	private VenueLevel venueLevel;

	private int seatHoldId;

	// A - Available, H - Held, R - Reserved
	private SeatStatusCode status = SeatStatusCode.AVAILABLE;

	// To define the self expiration period for the hold of the seat
	private long holdExpiresAt;

	public Seat(int seatNumber, VenueLevel venueLevel) {
		this.seatNumber = seatNumber;
		this.venueLevel = venueLevel;
	}

	public long getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(long seatNumber) {
		this.seatNumber = seatNumber;
	}

	public VenueLevel getVenueLevel() {
		return venueLevel;
	}

	public void setVenueLevel(VenueLevel venueLevel) {
		this.venueLevel = venueLevel;
	}

	public SeatStatusCode getStatus() {
		return status;
	}

	public void setStatus(SeatStatusCode status) {
		this.status = status;
	}

	public long getHoldExpiresAt() {
		return holdExpiresAt;
	}

	public void setHoldExpiresAt(long holdExpiresAt) {
		this.holdExpiresAt = holdExpiresAt;
	}

	public boolean isExpired() {
		return System.currentTimeMillis() > this.holdExpiresAt;
	}

	public int getSeatHoldId() {
		return seatHoldId;
	}

	public void setSeatHoldId(int seatHoldId) {
		this.seatHoldId = seatHoldId;
	}

	/*
	 * return true only if seat is available to reserve, it means status should
	 * be available or if status is Hold,then it should be expired.
	 */
	public boolean isAvailable() {
		if (this.status == SeatStatusCode.AVAILABLE) {
			return true;
		} else if (this.status == SeatStatusCode.ONHOLD && isExpired()) {
			this.status = SeatStatusCode.AVAILABLE;
			this.getVenueLevel().updateAvailability(SeatStatusCode.AVAILABLE);
			return true;
		}
		return false;
	}

	@Override
	// To print the all seats of this Avenue Level with level id , seat number
	// and status with just viewable foramt
	public String toString() {
		return String.format("%16s", this.venueLevel.getLevelId() + "-"
				+ this.seatNumber + "-" + this.status);
	}

}
