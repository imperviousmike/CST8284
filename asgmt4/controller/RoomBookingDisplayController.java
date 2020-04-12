package cst8284.asgmt4.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import firstyear.secondsemester.cst8284.asgmt4.roomScheduler.RoomScheduler;
import firstyear.secondsemester.cst8284.asgmt4.utility.RoomSchedulerConstants;
import firstyear.secondsemester.cst8284.asgmt4.view.RoomBookingDisplayDialog;

/**
 * Controller used for the {@link cst8284.asgmt4.view.RoomBookingDisplayDialog},
 * it sets the relevant actions for the buttons in the dialog.
 *
 * @version 1.0
 * @author Michael Daly
 * 
 */
public class RoomBookingDisplayController extends GenericSchedulerController {

	/**
	 * The window that will appear to user which this controller interacts with.
	 */
	private RoomBookingDisplayDialog dialog;

	/**
	 * Constructor for this controller, sets the necessary fields and adds the
	 * subclass listeners to the buttons in the window.
	 *
	 * @param scheduler the {@link cst8284.asgmt4.roomScheduler.RoomScheduler} used
	 *                  as the model that handles any changes from the GUI.
	 * @param dialog    The window that will pop up.
	 * 
	 */
	public RoomBookingDisplayController(RoomScheduler scheduler, RoomBookingDisplayDialog dialog) {
		super(scheduler);
		setRoomBookingDisplayDialog(dialog);

		getRoomBookingDisplayDialog().findBookingDisplayListener(new FindListener());
		getRoomBookingDisplayDialog().findBookingDisplayListener(new ExitListener());
		getRoomBookingDisplayDialog().exitBookingDisplayListener(new ExitListener());

	}

	/**
	 * Getter for the dialog that is displayed that this Controller sets the
	 * actions.
	 *
	 *
	 * @return the currently stored
	 *         {@link cst8284.asgmt4.view.RoomBookingAddDialog}.
	 * 
	 */
	public RoomBookingDisplayDialog getRoomBookingDisplayDialog() {
		return dialog;
	}

	/**
	 * Setter for the dialog that is displayed that this Controller sets the
	 * actions.
	 *
	 * @param dialog The {@link cst8284.asgmt4.view.RoomBookingAddDialog} to be
	 *               stored in the controller.
	 * 
	 */
	private void setRoomBookingDisplayDialog(RoomBookingDisplayDialog dialog) {
		this.dialog = dialog;
	}

	/**
	 * This subclass is used to set the action for the save button. It passes the
	 * inputs from the user to the scheduler action that will match the appropriate
	 * button click. It stores this result and if the result is what is expected it
	 * runs the next sequence.
	 *
	 * @version 1.0
	 * @author Michael Daly
	 * 
	 */
	class FindListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			getRoomBookingDisplayDialog().populateInputs();
			String result = (String) getScheduler().executeMenuItem(RoomSchedulerConstants.DISPLAY_BOOKING_DAY,
					RoomBookingDisplayDialog.getInputs());

			Calendar cal = (Calendar) getScheduler().executeMenuItem(RoomSchedulerConstants.DISPLAY_BOOKING_CALENDAR,
					RoomBookingDisplayDialog.getInputs());
			getRoomBookingDisplayDialog().updateText(result, cal);
		}
	}

	/**
	 * This subclass is used to set the action for the exit button. If the button is
	 * clicked it exits the dialog.
	 * 
	 * @version 1.0
	 * @author Michael Daly
	 * 
	 */
	class ExitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			getRoomBookingDisplayDialog().exitWindow();
		}

	}
}
