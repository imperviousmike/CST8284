package cst8284.asgmt4.roomScheduler;

import firstyear.secondsemester.cst8284.asgmt4.controller.RoomSchedulerController;
import firstyear.secondsemester.cst8284.asgmt4.room.*;
import firstyear.secondsemester.cst8284.asgmt4.view.RoomSchedulerDialog;

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
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ComputerLab cl = new ComputerLab();
				cl.setRoomNumber("B119");
				RoomScheduler rs = new RoomScheduler(cl);
				RoomSchedulerDialog view = new RoomSchedulerDialog(rs);
				new RoomSchedulerController(rs, view);
				view.setVisible(true);
			}
		});
	}
}
