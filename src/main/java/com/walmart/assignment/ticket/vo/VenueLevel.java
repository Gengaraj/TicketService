package com.walmart.assignment.ticket.vo;

import com.walmart.assignment.ticket.constants.Constants;
import com.walmart.assignment.ticket.util.SeatStatusCode;

/*
 * Defines the structure of the Venue, This is templeate.
 * This can be used to generate the instances as many as levels required
 * Will be initialized with seats based on the requirement
 */
public class VenueLevel {

	private int levelId;

	private String levelName;

	private double price;

	private int numberOfRows;

	private int seatsPerRow;

	private Seat seats[][];

	private int numberOfAvailableSeats;

	private int numberOfSeatsHeld;

	private int numberOfSeatsReserved;

	/*
	 * initializing the venue levels with full Available Seats by default for
	 * the given details
	 */
	public VenueLevel(int levelId, String levelName, double price, int rows,
			int seatsPerRow) {
		this.levelId = levelId;
		this.levelName = levelName;
		this.price = price;
		this.numberOfRows = rows;
		this.seatsPerRow = seatsPerRow;
		this.numberOfAvailableSeats = rows * seatsPerRow;
		// int totalSeats= numberOfAvailableSeats;
		seats = new Seat[rows][seatsPerRow];
		for (int i = 0; i < numberOfRows; i++) {
			for (int j = 0; j < seatsPerRow; j++) {
				seats[i][j] = new Seat(i * seatsPerRow + j + 1, this);
				// seats[i][j] = new Seat(totalSeats--,this);
			}
		}
	}

	public int getLevelId() {
		return levelId;
	}

	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getNumberOfRows() {
		return numberOfRows;
	}

	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}

	public int getSeatsPerRow() {
		return seatsPerRow;
	}

	public void setSeatsPerRow(int seatsPerRow) {
		this.seatsPerRow = seatsPerRow;
	}

	public Seat[][] getSeats() {
		return this.seats;
	}

	public void setSeats(Seat[][] seats) {
		this.seats = seats;
	}

	public int getNumberOfAvailableSeats() {
		return numberOfAvailableSeats;
	}

	public void setNumberOfAvailableSeats(int numberOfAvailableSeats) {
		this.numberOfAvailableSeats = numberOfAvailableSeats;
	}

	public int getNumberOfSeatsHeld() {
		return numberOfSeatsHeld;
	}

	public void setNumberOfSeatsHeld(int numberOfSeatsHeld) {
		this.numberOfSeatsHeld = numberOfSeatsHeld;
	}

	public int getNumberOfSeatsReserved() {
		return numberOfSeatsReserved;
	}

	public void setNumberOfSeatsReserved(int numberOfSeatsReserved) {
		this.numberOfSeatsReserved = numberOfSeatsReserved;
	}

	// Updating the seat count for each status depending on the operation such
	// as ONHOLD, BOOKED and AVAILABLE.
	public void updateAvailability(SeatStatusCode statusChange) {
		switch (statusChange) {
		case ONHOLD:
			numberOfSeatsHeld++;
			numberOfAvailableSeats--;
			break;
		case BOOKED:
			numberOfSeatsHeld--;
			numberOfSeatsReserved++;
			break;
		case AVAILABLE:
			numberOfSeatsHeld--;
			numberOfAvailableSeats++;
		default:
			break;
		}
	}

	/*
	 * To test & confirm the seats are assigned properly as we do not have any
	 * UI. To print the all seats of this Avenue Level with level id , seat
	 * number and status(non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer seats = new StringBuffer();
		seats.append("\n");
		seats.append("Level ID : " + this.levelId + " Level Name : "
				+ this.levelName + " Price : " + this.getPrice()
				+ " Total Seats : " + this.numberOfRows * this.seatsPerRow
				+ " AVAILABLE Seats : " + this.getNumberOfAvailableSeats()
				+ " ONHOLD Seats : " + this.getNumberOfSeatsHeld()
				+ " BOOKED SEATS : " + this.getNumberOfSeatsReserved());
		seats.append("\n\n");
		for (int i = 0; i < numberOfRows; i++) {
			for (int j = 0; j < seatsPerRow; j++) {
				Seat seat = this.seats[i][j];
				seats.append(seat);
				seats.append("\t");
			}
			seats.append("\n");
		}
		return seats.toString();
	}

	// To test this class
	public static void main(String[] args) {
		VenueLevel level = new VenueLevel(Constants.ORCHESTRA_LEVEL_ID,
				Constants.ORCHESTRA_LEVEL_NAME, Constants.ORCHESTRA_PRICE,
				Constants.ORCHESTRA_ROWS, Constants.ORCHESTRA_SEATS_PER_ROW);
		System.out.println(level.toString());
	}
}
