package com.walmart.assignment.ticket;

import java.util.Optional;
import java.util.Scanner;

import com.walmart.assignment.ticket.services.TicketService;
import com.walmart.assignment.ticket.services.TicketServiceImpl;
import com.walmart.assignment.ticket.vo.SeatHold;

public class App {
	public static void main(String[] args) {
		try {
			boolean rotate =true;
			Scanner scanner = new Scanner(System.in);
			while(rotate){
				TicketService ticketService = new TicketServiceImpl();
				
				System.out.println("Please enter the option you want to try..");
				System.out.println("Enter 1 for SeatAvailabilty");
				System.out.println("Enter 2 for Hold & Reserve");
				int option = scanner.nextInt();
				if(option ==1 ){
					System.out
							.println("Please enter any values from 1 to 4 to find the available seats \n");
					System.out.println("Available Seats: "
							+ ticketService.numSeatsAvailable(Optional.of(Integer
									.valueOf(scanner.nextInt()))));
				}else if(option ==2){
					System.out.println(" To hold seat enter the below values \n");
					System.out.println("Please enter number of Seats to be held\n");
					int numberOfSeats = scanner.nextInt();
					System.out
							.println("Please enter MINIMUM venue level (from 1 to 4) \n");
					int minLevel = scanner.nextInt();
					System.out
							.println("Please enter MAXIMUM venue level (from 1 to 4) \n");
					int maxLevel = scanner.nextInt();
					System.out.println("Please enter email address \n");
					String email = scanner.next();
					SeatHold seatHold = ticketService.findAndHoldSeats(numberOfSeats,
							Optional.of(minLevel), Optional.of(maxLevel), email);
					System.out.println("Requested Seats are ONHOLD, id# "+ seatHold.getId());
					
					for (int i = 0; i < seatHold.getSeats().toArray().length; i++) {
						System.out.print (seatHold.getSeats().toArray()[1]+ " ");
					}
					
					System.out.print("\nPlease enter 1 to reserve the seats held \n");
					if (scanner.nextInt() == 1) {
						System.out.println(ticketService.reserveSeats(seatHold.getId(),
								email));
					} else {
						System.out.println("Seats are not confirmed");
					}
												
				}			
				System.out.println("Please press 1 if you want to continue again or press any other key to terminate");
				if(scanner.nextInt()!=1){
					rotate=false;
				}
				
			}
			scanner.close();
			
		} catch (Exception exception) {
			System.out.println("Please enter the valid input : ");
			exception.printStackTrace();
		}

		System.out.println("Completed");

	}
}
