package cst8284.asgmt4.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import firstyear.secondsemester.cst8284.asgmt4.roomScheduler.RoomScheduler;
import firstyear.secondsemester.cst8284.asgmt4.utility.RoomSchedulerConstants;
import firstyear.secondsemester.cst8284.asgmt4.view.RoomBookingModifyDialog;
import firstyear.secondsemester.cst8284.asgmt4.view.RoomSchedulerOptionPane;

/**
 * Controller used for the {@link cst8284.asgmt4.view.RoomBookingModifyDialog},
 * it sets the relevant actions for the buttons in the dialog.
 *
 * @version 1.0
 * @author Michael Daly
 * 
 */
public final class RoomBookingModifyController extends GenericSchedulerController {

	/**
	 * The window that will appear to user which this controller interacts with.
	 */
	private RoomBookingModifyDialog dialog;

	/**
	 * Constructor for this controller, sets the necessary fields and adds the
	 * subclass listeners to the buttons in the window.
	 *
	 * @param scheduler the {@link cst8284.asgmt4.roomScheduler.RoomScheduler} used
	 *                  as the model that handles any changes from the GUI.
	 * @param dialog    The window that will pop up.
	 * 
	 */
	public RoomBookingModifyController(RoomScheduler scheduler, RoomBookingModifyDialog dialog) {
		super(scheduler);
		setRoomBookingModifyDialog(dialog);
		;

		getRoomBookingModifyDialog().findBookingDisplayListener(new FindBookingListener());
		getRoomBookingModifyDialog().changeBookingDisplayListener(new ChangeBookingListener());
		getRoomBookingModifyDialog().deleteBookingDisplayListener(new DeleteBookingListener());
		getRoomBookingModifyDialog().resetBookingDisplayListener(new ResetBookingListener());
		getRoomBookingModifyDialog().exitBookingDisplayListener(new ExitBookingListener());

	}

	/**
	 * Getter for the dialog that is displayed that this Controller sets the
	 * actions.
	 *
	 * @return the currently stored
	 *         {@link cst8284.asgmt4.view.RoomBookingModifyDialog}.
	 * 
	 */
	public RoomBookingModifyDialog getRoomBookingModifyDialog() {
		return dialog;
	}

	/**
	 * Setter for the dialog that is displayed that this Controller sets the
	 * actions.
	 * 
	 * @param dialog The {@link cst8284.asgmt4.view.RoomBookingModifyDialog} to be
	 *               stored in the controller.
	 * 
	 */
	private void setRoomBookingModifyDialog(RoomBookingModifyDialog dialog) {
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
	class FindBookingListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			getRoomBookingModifyDialog().populateInputs();
			String result = (String) getScheduler().executeMenuItem(RoomSchedulerConstants.MODIFY_BOOKING_FIND,
					RoomBookingModifyDialog.getInputs());
			getRoomBookingModifyDialog().processFindResult(result);
		}
	}

	/**
	 * This subclass is used to set the action for the change button. It passes the
	 * inputs from the user to the scheduler action that will match the appropriate
	 * button click. It stores this result and if the result is what is expected it
	 * runs the next sequence.
	 *
	 * @version 1.0
	 * @author Michael Daly
	 * 
	 */
	class ChangeBookingListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			getRoomBookingModifyDialog().populateInputs();
			boolean result = (boolean) getScheduler().executeMenuItem(RoomSchedulerConstants.MODIFY_BOOKING_CHANGE,
					RoomBookingModifyDialog.getInputs());
			if (result == true) {
				getRoomBookingModifyDialog().exitWindow();
			}

		}

	}

	/**
	 * This subclass is used to set the action for the delete button. It passes the
	 * inputs from the user to the scheduler action that will match the appropriate
	 * button click. It prompts the user using
	 * {@link cst8284.asgmt4.view.RoomSchedulerOptionPane} to confirm the deletion,
	 * and if they confirmed it proceeds to delete the entry.
	 *
	 * @version 1.0
	 * @author Michael Daly
	 * 
	 */
	class DeleteBookingListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			getRoomBookingModifyDialog().populateInputs();
			if (new RoomSchedulerOptionPane("Delete Entry", "Are you sure you wish to delete this booking?")
					.getResponse() == JOptionPane.YES_OPTION) {
				getScheduler().executeMenuItem(RoomSchedulerConstants.MODIFY_BOOKING_DELETE,
						RoomBookingModifyDialog.getInputs());
				getRoomBookingModifyDialog().exitWindow();
			}

		}

	}

	/**
	 * This subclass is used to set the action for the exit button. It closes the
	 * window when the button is clicked.
	 *
	 * @version 1.0
	 * @author Michael Daly
	 * 
	 */
	class ExitBookingListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			getRoomBookingModifyDialog().exitWindow();
		}

	}

	/**
	 * This subclass is used to set the action for the reset button. It prompts the
	 * user using {@link cst8284.asgmt4.view.RoomSchedulerOptionPane} to confirm the
	 * reset, and if they confirmed it proceeds to reset the window
	 *
	 * @version 1.0
	 * @author Michael Daly
	 * 
	 */
	class ResetBookingListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			getRoomBookingModifyDialog().populateInputs();
			if (new RoomSchedulerOptionPane("Reset Window", "Are you sure you wish to Reset this window")
					.getResponse() == JOptionPane.YES_OPTION) {
				getRoomBookingModifyDialog().resetWindow();
			}
		}

	}

}
