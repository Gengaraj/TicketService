package com.walmart.assignment.ticket.util;

import java.util.concurrent.atomic.AtomicInteger;

/*
 * This class is just to generate the unique Key for SeatHOLD
 */
public class IDGenerator {

	private static AtomicInteger counter = new AtomicInteger(1);

	public static int getId() {
		return counter.getAndIncrement();
	}

}

