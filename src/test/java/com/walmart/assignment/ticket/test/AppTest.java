package com.walmart.assignment.ticket.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;

import com.walmart.assignment.ticket.constants.Constants;
import com.walmart.assignment.ticket.services.TicketService;
import com.walmart.assignment.ticket.services.TicketServiceImpl;
import com.walmart.assignment.ticket.util.SeatStatusCode;
import com.walmart.assignment.ticket.vo.SeatHold;

public class AppTest {
	
	TicketService ticketService = new TicketServiceImpl();
	
	@Test
	public void testNumSeatsAvailable_ValidTest(){
		
		//Test counts of available seats at venue level through TicketService IMPL before any reservation made and compare with direct values
		int count = ticketService.numSeatsAvailable(Optional.of(1));
		//test for valid input and output
		assertNotEquals(0, count);
	}
	
	@Test
	public void testNumSeatsAvailable_invalidTest(){
		//Test with invalid level input '0' level
		int invalidCount = ticketService.numSeatsAvailable(Optional.of(0));
		assertEquals(0, invalidCount);
	}
	
	
	@Test
	public void testFindAndHoldSeats_onHoldTest(){
		//3 seats from level 1 
		SeatHold onHold = ticketService.findAndHoldSeats(3, Optional.of(1), Optional.of(3), "imgengaraj@gmail.com");
		for(int i=0; i<onHold.getSeats().size();i++){
			//status of the seat should be ONHOLD
			assertEquals(SeatStatusCode.ONHOLD, onHold.getSeats().get(0).getStatus());
		}
		
	}
	
	@Test
	public void testFindAndHoldSeats_dataTest(){
		//3 seats from level 1 
			SeatHold threeSeatsFirstLevel = ticketService.findAndHoldSeats(3, Optional.of(1), Optional.of(3), "imgengaraj@gmail.com");
			//SeatHold should have only request seat counts
			assertEquals(3,threeSeatsFirstLevel.getSeats().size());
			
			//SeatHold should have the email id updated
			assertEquals("imgengaraj@gmail.com",threeSeatsFirstLevel.getCustomerEmail());
			
			//3 seats from level 2 
			SeatHold threeSeatsSecondLevel = ticketService.findAndHoldSeats(3, Optional.of(2), Optional.of(3), "imgengaraj@gmail.com");
			
			//bothObjects should not be equal
			assertNotEquals(threeSeatsFirstLevel, threeSeatsSecondLevel);
			
			//both should have different seats
			assertNotEquals(threeSeatsFirstLevel.getSeats().get(0), threeSeatsSecondLevel.getSeats().get(0));
			
			//Both should have different id
			assertNotEquals(threeSeatsFirstLevel.getId(), threeSeatsSecondLevel.getId());
				
	}
	
	@Test
	public void duplicateHoldTest(){
		//3 seats from level 1 
		SeatHold threeSeatsFirstLevel = ticketService.findAndHoldSeats(3, Optional.of(1), Optional.of(3), "imgengaraj@gmail.com");
				
		//3 seats from level 2 
		SeatHold threeSeatsSecondLevel = ticketService.findAndHoldSeats(3, Optional.of(2), Optional.of(3), "imgengaraj@gmail.com");
		
		//bothObjects should not be equal
		assertNotEquals(threeSeatsFirstLevel, threeSeatsSecondLevel);
		
		//Both should have different id
		assertNotEquals(threeSeatsFirstLevel.getId(), threeSeatsSecondLevel.getId());
	}
	
	
	//Commenting this test case to avoid slowness during the build since this funcationality includes thread.sleep 
	//@Test
	public void holdAndExpiryTest(){
		SeatHold holdAndExpiry = ticketService.findAndHoldSeats(3, Optional.of(2), Optional.of(3), "imgengaraj@gmail.com");
		try {
			Thread.sleep((Constants.SEAT_HOLD_EXPIRY_DURATION*1000)+1);
			//after 20 secs(20000ms), SeatHold should expire
			assertTrue(holdAndExpiry.getSeats().get(0).isExpired());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testReserveSeats() {
		
		//Holding 3 seats from level 1 
		SeatHold seatHold = ticketService.findAndHoldSeats(3, Optional.of(1), Optional.of(3), "imgengaraj@gmail.com");
		
		String confirmId = ticketService.reserveSeats(seatHold.getId(),"imgengaraj@gmail.com");
		
		//Should return the confirmation id, should not be null
		assertNotNull(confirmId);
		
		//After reserving,status should be changed to "BOOKED"
		for(int i=0; i<seatHold.getSeats().size();i++){
			assertEquals(SeatStatusCode.BOOKED, seatHold.getSeats().get(i).getStatus());
		}
		
		//Duplication reservation should return the message "Sorry, Seats are not available/expired. Please try again"
		String duplicateReservation = ticketService.reserveSeats(seatHold.getId(),"imgengaraj@gmail.com");
		assertEquals("Sorry, Seats are not available/expired. Please try again",duplicateReservation);
		
		
	}
	
	
}
