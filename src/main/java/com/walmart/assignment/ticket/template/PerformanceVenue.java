package com.walmart.assignment.ticket.template;

import java.util.HashMap;
import java.util.Map;

import com.walmart.assignment.ticket.constants.Constants;
import com.walmart.assignment.ticket.vo.SeatHold;
import com.walmart.assignment.ticket.vo.VenueLevel;

/*This class will act as a data container since this project works 
 * without permanent persistence*/
public class PerformanceVenue {

	// To Hold info about 4 levels of PerformanceVenue
	private static Map<Integer, VenueLevel> venueLevels = new HashMap<Integer, VenueLevel>();

	// To Hold info about the SeatHolds
	private Map<Integer, SeatHold> seatHolds = new HashMap<Integer, SeatHold>();

	// Initialize all 4 Venue Levels with Seats(Default Available Status)
	static {
		venueLevels.put(Constants.ORCHESTRA_LEVEL_ID, new VenueLevel(
				Constants.ORCHESTRA_LEVEL_ID, Constants.ORCHESTRA_LEVEL_NAME,
				Constants.ORCHESTRA_PRICE, Constants.ORCHESTRA_ROWS,
				Constants.ORCHESTRA_SEATS_PER_ROW));
		venueLevels.put(Constants.MAIN_LEVEL_ID, new VenueLevel(
				Constants.MAIN_LEVEL_ID, Constants.MAIN_LEVEL_NAME,
				Constants.MAIN_PRICE, Constants.MAIN_ROWS,
				Constants.MAIN_SEATS_PER_ROW));
		venueLevels.put(Constants.BALCONY_LOWER_LEVEL_ID, new VenueLevel(
				Constants.BALCONY_LOWER_LEVEL_ID,
				Constants.BALCONY_LOWER_LEVEL_NAME,
				Constants.BALCONY_LOWER_PRICE, Constants.BALCONY_LOWER_ROWS,
				Constants.BALCONY_LOWER_SEATS_PER_ROW));
		venueLevels.put(Constants.BALCONY_UPPER_LEVEL_ID, new VenueLevel(
				Constants.BALCONY_UPPER_LEVEL_ID,
				Constants.BALCONY_UPPER_LEVEL_NAME,
				Constants.BALCONY_UPPER_PRICE, Constants.BALCONY_UPPER_ROWS,
				Constants.BALCONY_UPPER_SEATS_PER_ROW));
	}

	public static Map<Integer, VenueLevel> getVenueLevels() {
		return venueLevels;
	}

	public Map<Integer, SeatHold> getSeatHolds() {
		return seatHolds;
	}

	public void setSeatHolds(Map<Integer, SeatHold> seatHolds) {
		this.seatHolds = seatHolds;
	}

	// Just for testing purpose
	public static void main(String[] args) {
		System.out.println(PerformanceVenue.getVenueLevels().toString());
	}
}
