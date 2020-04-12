package cst8284.asgmt2.room;

/*
	Course Name: CST8284
	Student Name: Michael Daly
	Class name: Object Oriented Programming (Java)
	Date: March 2nd, 2020
*/


public final class ComputerLab extends Room {

	private static final int DEFAULT_SEATS = 30;
	private int seats;

	public ComputerLab() {
		super();
		seats = DEFAULT_SEATS;
	}
	
	@Override
	protected String getRoomType() {
		return "computer lab";
	}

	@Override
	protected int getSeats() {
		return seats;
	}

	@Override
	protected String getDetails() {
		return "contains outlets for 30 laptops";
	}

}
