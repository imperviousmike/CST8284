package cst8284.asgmt4.controller;

import firstyear.secondsemester.cst8284.asgmt4.roomScheduler.RoomScheduler;

/**
 * Abstract Controller shared by all Controllers, it sets the scheduler which is
 * shared between all dialogs.
 *
 * @version 1.0
 * @author Michael Daly
 * 
 */
public abstract class GenericSchedulerController {
	/**
	 * {@link cst8284.asgmt4.roomScheduler.RoomScheduler} used by all dialogs.
	 */
	private static RoomScheduler scheduler;

	/**
	 * Default constructor
	 */
	GenericSchedulerController(RoomScheduler scheduler) {
		setScheduler(scheduler);
	}

	/**
	 * Getter for the {@link cst8284.asgmt4.roomScheduler.RoomScheduler}.
	 * 
	 * @return the current stored
	 *         {@link cst8284.asgmt4.roomScheduler.RoomScheduler}.
	 */
	protected static RoomScheduler getScheduler() {
		return scheduler;
	}

	/**
	 * Setter for the {@link cst8284.asgmt4.roomScheduler.RoomScheduler}.
	 * 
	 * @param scheduler the {@link cst8284.asgmt4.roomScheduler.RoomScheduler} to be
	 *                  stored.
	 */
	protected static void setScheduler(RoomScheduler scheduler) {
		GenericSchedulerController.scheduler = scheduler;
	}

}
