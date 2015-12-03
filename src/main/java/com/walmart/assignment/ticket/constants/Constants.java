package com.walmart.assignment.ticket.constants;

//To define the constants
public interface Constants {

	// Orchestra details
	int ORCHESTRA_LEVEL_ID = 1;
	String ORCHESTRA_LEVEL_NAME = "Orchestra";
	double ORCHESTRA_PRICE = 100;
	int ORCHESTRA_ROWS = 25;
	int ORCHESTRA_SEATS_PER_ROW = 50;

	// Main Lobby details
	int MAIN_LEVEL_ID = 2;
	String MAIN_LEVEL_NAME = "Main";
	double MAIN_PRICE = 75;
	int MAIN_ROWS = 20;
	int MAIN_SEATS_PER_ROW = 100;

	// Balcony 1 details
	int BALCONY_LOWER_LEVEL_ID = 3;
	String BALCONY_LOWER_LEVEL_NAME = "Balcony 1";
	double BALCONY_LOWER_PRICE = 50;
	int BALCONY_LOWER_ROWS = 15;
	int BALCONY_LOWER_SEATS_PER_ROW = 100;

	// Balcony 2 details
	int BALCONY_UPPER_LEVEL_ID = 4;
	String BALCONY_UPPER_LEVEL_NAME = "Balcony 2";
	double BALCONY_UPPER_PRICE = 40;
	int BALCONY_UPPER_ROWS = 15;
	int BALCONY_UPPER_SEATS_PER_ROW = 100;

	// Seat hold expires after 20 Secs/20000ms
	long SEAT_HOLD_EXPIRY_DURATION = 20;

}
