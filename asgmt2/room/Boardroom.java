package cst8284.asgmt2.room;

/*
	Course Name: CST8284
	Student Name: Michael Daly
	Class name: Object Oriented Programming (Java)
	Date: March 2nd, 2020
*/

public final class Boardroom extends Room {

	private int seats;
	
	public Boardroom() {
		super();
		seats = 16;
	}

	@Override
	protected String getRoomType() {
		return "board room";
	}

	@Override
	protected int getSeats() {
		return seats;
	}

	@Override
	protected String getDetails() {
		return "conference call enabled";
	}

}
