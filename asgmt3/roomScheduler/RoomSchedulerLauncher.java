package cst8284.asgmt3.roomScheduler;

import firstyear.secondsemester.cst8284.asgmt3.room.*;

/**
 * This is the launch point for the application.
 * 
 * @author Michael Daly
 * @version 2.0
 * 
 */

public class RoomSchedulerLauncher {

	/**
	 * The main that is to be run to start the application.
	 * 
	 * @param args If run from the command line these are any arguments
	 *             provided(ignored for this application).
	 * 
	 */
	public static void main(String[] args) {
		ComputerLab cb = new ComputerLab();
		cb.setRoomNumber("B119");
		new RoomScheduler(cb).launch();
	}
}
