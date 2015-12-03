package com.walmart.assignment.ticket.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.walmart.assignment.ticket.constants.Constants;
import com.walmart.assignment.ticket.template.PerformanceVenue;
import com.walmart.assignment.ticket.util.SeatStatusCode;
import com.walmart.assignment.ticket.vo.Seat;
import com.walmart.assignment.ticket.vo.SeatHold;
import com.walmart.assignment.ticket.vo.VenueLevel;

public class TicketServiceImpl implements TicketService {

	PerformanceVenue performanceVenue = new PerformanceVenue();

	/**
	 * The number of seats in the requested level that are neither held nor
	 * reserved
	 * 
	 * @param venueLevel
	 *            a numeric venue level identifier to limit the search
	 * @return the number of tickets available on the provided level
	 */
	@Override
	public int numSeatsAvailable(Optional<Integer> venueLevel) {
		if (PerformanceVenue.getVenueLevels().containsKey(venueLevel.get())) {
			return PerformanceVenue.getVenueLevels().get(venueLevel.get())
					.getNumberOfAvailableSeats();
		} else {
			System.out.println("Incorrect Level, so returing 0");
		}
		return 0;
	}

	/**
	 * Find and hold the best available seats for a customer
	 * @param numSeats
	 *            the number of seats to find and hold
	 * @param minLevel
	 *            the minimum venue level
	 * @param maxLevel
	 *            the maximum venue level
	 * @param customerEmail
	 *            unique identifier for the customer
	 * @return a SeatHold object identifying the specific seats and related
	 *         information
	 */
	@Override
	public SeatHold findAndHoldSeats(int numSeats, Optional<Integer> minLevel,
			Optional<Integer> maxLevel, String customerEmail) {
		boolean isHeld = false;
		int availableSeats = 0;
		SeatHold seatHold = new SeatHold(numSeats, customerEmail);
		List<Seat> seatsOnHold = new ArrayList<Seat>(numSeats);
		long bookingTime = System.currentTimeMillis()
				+ (Constants.SEAT_HOLD_EXPIRY_DURATION * 1000);
		
		synchronized (performanceVenue) {
			for (int i = minLevel.get(); i <= maxLevel.get(); i++) {
				availableSeats += PerformanceVenue.getVenueLevels().get(i)
						.getNumberOfAvailableSeats();
			}
			if (availableSeats < numSeats) {
				System.out
						.println("SORRY!!! only "
								+ availableSeats
								+ " seats available to hold/reserve, sorry please try for next event");
			} else {
				int offSet = numSeats;
				for (int nLevel = minLevel.get(); nLevel <= maxLevel.get(); nLevel++) {
					if (offSet == 0) {
						break;
					}
					VenueLevel venueLevel = PerformanceVenue.getVenueLevels()
							.get(nLevel);
					Seat[][] allSeats = venueLevel.getSeats();
					for (Seat[] seats : allSeats) {
						if (offSet == 0) {
							break;
						}
						for (int j = 0; j < seats.length; j++) {
							Seat seat = seats[j];
							if (seat.isAvailable()) {
								seat.setStatus(SeatStatusCode.ONHOLD);
								seat.getVenueLevel().updateAvailability(
										SeatStatusCode.ONHOLD);
								seat.setHoldExpiresAt(bookingTime);
								seat.setSeatHoldId(seatHold.getId());
								seatsOnHold.add(seat);
								isHeld = true;
								offSet--;
								if (offSet == 0) {
									break;
								}
							}
						}
					}

				}
				if (!isHeld) {
					System.out
							.println(numSeats
									+ " Seats are not available in the same level, will be held across multiple levels");
				}
			}
		}
		seatHold.setCustomerEmail(customerEmail);
		seatHold.setHoldExpiresAt(bookingTime);
		seatHold.setSeats(seatsOnHold);
		performanceVenue.getSeatHolds().put(seatHold.getId(),
				seatHold);
		return seatHold;
	}

	/**
	 * Commit seats held for a specific customer
	 * 
	 * @param seatHoldId
	 *            the seat hold identifier
	 * @param customerEmail
	 *            the email address of the customer to which the seat hold is
	 *            assigned
	 * @return a reservation confirmation code
	 */
	@Override
	public String reserveSeats(int seatHoldId, String customerEmail) {
		SeatHold seatHold = performanceVenue.getSeatHolds().get(seatHoldId);
		List<Seat> seats = seatHold.getSeats();
		synchronized (performanceVenue) {
			if (!seatHold.isExpired() && seatHold.getConfirmationCode()== null) {
				for (Seat seat : seats) {
					seat.setStatus(SeatStatusCode.BOOKED);
					seat.getVenueLevel().updateAvailability(
							SeatStatusCode.BOOKED);
				}
				seatHold.setConfirmationCode("CONF_" + seatHoldId);
			} else {
				for (Seat seat : seats) {
					/*
					 * This check will make sure that this functionality is
					 * resetting the status if seats are belongs to this
					 * seatHoldId booking
					 */
					if (seat.getSeatHoldId() == seatHoldId) {
						seat.setStatus(SeatStatusCode.AVAILABLE);
						seat.getVenueLevel().updateAvailability(
								SeatStatusCode.AVAILABLE);
					}
				}
				return "Sorry, Seats are not available/expired. Please try again";
			}
		}
		return seatHold.getConfirmationCode();
	}
	
	

	/*
	 * Below main method is only for testing purpose. Hold, then wait until
	 * expire, then again hold then reserve all seatHolds to make sure SeatHolds
	 * are not impacting the data of each other
	 */
	public static void main(String[] args) {
		
		TicketServiceImpl ticketServiceImpl = new TicketServiceImpl();
		ticketServiceImpl.numSeatsAvailable(Optional.of(1));
		SeatHold seatHold1 = ticketServiceImpl.findAndHoldSeats(5,
				Optional.of(1), Optional.of(3), "imgengaraj@gmail.com");
		SeatHold seatHold2 = ticketServiceImpl.findAndHoldSeats(5,
				Optional.of(2), Optional.of(4), "imgengaraj@gmail.com");
		SeatHold seatHold3 = ticketServiceImpl.findAndHoldSeats(5,
				Optional.of(1), Optional.of(2), "imgengaraj@gmail.com");
		SeatHold seatHold4 = ticketServiceImpl.findAndHoldSeats(5,
				Optional.of(2), Optional.of(3), "imgengaraj@gmail.com");
		


		System.out.println("\n\n"
				+ PerformanceVenue.getVenueLevels().get(1).toString() + "\n");
		System.out.println(PerformanceVenue.getVenueLevels().get(2).toString()
				+ "\n");
		System.out.println(PerformanceVenue.getVenueLevels().get(3).toString()
				+ "\n");
		System.out.println(PerformanceVenue.getVenueLevels().get(4).toString()
				+ "\n");
		
		
		System.out.println("After 21 secs ");
		try {
			Thread.sleep(21000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		SeatHold seatHold5 = ticketServiceImpl.findAndHoldSeats(14,
				Optional.of(1), Optional.of(3), "imgengaraj@gmail.com");

		ticketServiceImpl.reserveSeats(seatHold1.getId(),
				"imgengaraj@gmail.com");
		ticketServiceImpl.reserveSeats(seatHold2.getId(),
				"imgengaraj@gmail.com");
		ticketServiceImpl.reserveSeats(seatHold3.getId(),
				"imgengaraj@gmail.com");
		ticketServiceImpl.reserveSeats(seatHold4.getId(),
				"imgengaraj@gmail.com");
		System.out.println(PerformanceVenue.getVenueLevels().get(1).toString()
				+ "\n");
		System.out.println(PerformanceVenue.getVenueLevels().get(2).toString()
				+ "\n");
		System.out.println(PerformanceVenue.getVenueLevels().get(3).toString()
				+ "\n");
		System.out.println(PerformanceVenue.getVenueLevels().get(4).toString()
				+ "\n");
		System.out.println(PerformanceVenue.getVenueLevels().get(1).toString()
				+ "\n");
		ticketServiceImpl.reserveSeats(seatHold5.getId(),
				"imgengaraj@gmail.com");
		System.out.println(PerformanceVenue.getVenueLevels().get(1).toString()
				+ "\n");
		
	}

}
