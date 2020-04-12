package cst8284.asgmt2.roomScheduler;

import firstyear.secondsemester.cst8284.asgmt2.room.ComputerLab;

/*
	Course Name: CST8284
	Student Name: Michael Daly
	Class name: Object Oriented Programming (Java)
	Date: March 2nd, 2020
*/


public class RoomSchedulerLauncher {

	public static void main(String[] args) {
		ComputerLab cb = new ComputerLab();
		cb.setRoomNumber("B119");
		new RoomScheduler(cb).launch();
	}
}
