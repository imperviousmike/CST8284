package cst8284.asgmt4.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedHashMap;

import javax.swing.JTextField;

import firstyear.secondsemester.cst8284.asgmt4.roomScheduler.RoomScheduler;
import firstyear.secondsemester.cst8284.asgmt4.utility.RoomSchedulerConstants;
import firstyear.secondsemester.cst8284.asgmt4.view.RoomBookingAddDialog;
import firstyear.secondsemester.cst8284.asgmt4.view.RoomBookingModifyDialog;
import firstyear.secondsemester.cst8284.asgmt4.view.RoomBookingDisplayDialog;
import firstyear.secondsemester.cst8284.asgmt4.view.RoomSchedulerDialog;
import firstyear.secondsemester.cst8284.asgmt4.view.RoomSchedulerFileChooser;

/**
 * Controller used for the main {@link cst8284.asgmt4.view.RoomSchedulerDialog},
 * it sets the relevant actions for the buttons
 *
 * @version 1.0
 * @author Michael Daly
 * 
 */
public class RoomSchedulerController extends GenericSchedulerController {

	/**
	 * The dialog to be displayed
	 */
	private RoomSchedulerDialog dialog;

	/*
	 * These are set to static to ensure we only have one instance of either to
	 * avoid multiple running controllers in the background and cause issues with
	 * mismatch listeners from multiple instances
	 */
	private static RoomBookingModifyController modifyController;
	private static RoomBookingAddController addController;
	private static RoomBookingDisplayController roomDisplayController;

	/**
	 * This constructor calls to the superclass to set the shared scheduler, it
	 * initializes the controllers and hides them until they are available to be
	 * used when the correct button is clicked. It adds listeners to the buttons in
	 * the window.
	 *
	 * @param scheduler the {@link cst8284.asgmt4.roomScheduler.RoomScheduler} used
	 *                  as the model that handles any changes from the GUI.
	 * @param dialog    The window that will pop up.
	 * 
	 */
	public RoomSchedulerController(RoomScheduler scheduler, RoomSchedulerDialog dialog) {
		super(scheduler);
		setRoomSchedulerDialog(dialog);

		setModifyController(
				new RoomBookingModifyController(getScheduler(), new RoomBookingModifyDialog(getScheduler())));
		getModifyController().getRoomBookingModifyDialog().setVisible(false);

		setAddController(new RoomBookingAddController(getScheduler(),
				new RoomBookingAddDialog(getScheduler(), getRoomSchedulerDialog())));
		getAddController().getRoomBookingAddDialog().setVisible(false);

		setRoomDisplayController(new RoomBookingDisplayController(getScheduler(),
				new RoomBookingDisplayDialog(getScheduler(), getRoomSchedulerDialog())));
		getRoomDisplayController().getRoomBookingDisplayDialog().setVisible(false);

		getRoomSchedulerDialog().addBookingAddListener(new AddListener());
		getRoomSchedulerDialog().addBookingModifyListener(new ModifyListener());
		getRoomSchedulerDialog().addRoomDisplayListener(new RoomDisplayListener());
		getRoomSchedulerDialog().addBookingSaveListener(new SaveListener());
		getRoomSchedulerDialog().addBookingLoadListener(new LoadListener());
		getRoomSchedulerDialog().addBookingExitListener(new ExitListener());

	}

	/**
	 * Getter for the dialog that is displayed that this Controller sets the
	 * actions.
	 *
	 *
	 * @return the currently stored {@link cst8284.asgmt4.view.RoomSchedulerDialog}.
	 * 
	 */
	private RoomSchedulerDialog getRoomSchedulerDialog() {
		return dialog;
	}

	/**
	 * Setter for the dialog that is displayed that this Controller sets the
	 * actions.
	 *
	 * @param dialog The {@link cst8284.asgmt4.view.RoomSchedulerDialog} to be
	 *               stored in the controller.
	 * 
	 */
	private void setRoomSchedulerDialog(RoomSchedulerDialog dialog) {
		this.dialog = dialog;
	}

	/**
	 * Calls the method to set the title based on the entries stored in the list and
	 * repaints the window.
	 */
	private void updateTitle() {
		dialog.updateTitleString(null);
		dialog.repaint();
	}

	/**
	 * This method opens a {@code RoomSchedulerFileChooser} and lets the user select
	 * a file for saving/loading. When the file is grabbed, based on the option
	 * provided it either saved or loaded.
	 * 
	 * @return the success/fail of the action
	 */
	private static boolean setFileChooser(RoomSchedulerConstants rsConst) {
		File file = new RoomSchedulerFileChooser(rsConst).getSelectedFile();
		LinkedHashMap<RoomSchedulerConstants, JTextField> map = new LinkedHashMap<>();
		JTextField saveText = new JTextField();
		if (file != null) {
			saveText.setText(file.toString());
			map.put(rsConst, saveText);
			return (boolean) getScheduler().executeMenuItem(rsConst, map);
		}
		return false;
	}

	/**
	 * Getter for the
	 * {@link cst8284.asgmt4.RoomBookingModifyController.RoomBookingDisplayController}
	 * instance stored.
	 * 
	 * @return a reference to the stored
	 *         {@link cst8284.asgmt4.RoomBookingModifyController.RoomBookingDisplayController}
	 */
	private static RoomBookingModifyController getModifyController() {
		return modifyController;
	}

	/**
	 * Setter for the
	 * {@link cst8284.asgmt4.RoomBookingModifyController.RoomBookingDisplayController}
	 * instance stored.
	 * 
	 * @return a reference to the stored
	 *         {@link cst8284.asgmt4.RoomBookingModifyController.RoomBookingDisplayController}
	 */
	private static void setModifyController(RoomBookingModifyController displayController) {
		RoomSchedulerController.modifyController = displayController;
	}

	/**
	 * Getter for the {@link cst8284.asgmt4.controller.RoomBookingAddController}
	 * instance stored.
	 * 
	 * @return a reference to the stored
	 *         {@link cst8284.asgmt4.controller.RoomBookingAddController}
	 */
	private static RoomBookingAddController getAddController() {
		return addController;
	}

	/**
	 * Setter for the {@link cst8284.asgmt4.controller.RoomBookingAddController}
	 * instance stored.
	 * 
	 * @return a reference to the stored
	 *         {@link cst8284.asgmt4.controller.RoomBookingAddController}
	 */
	private static void setAddController(RoomBookingAddController addController) {
		RoomSchedulerController.addController = addController;
	}

	public static RoomBookingDisplayController getRoomDisplayController() {
		return roomDisplayController;
	}

	public static void setRoomDisplayController(RoomBookingDisplayController roomDisplayController) {
		RoomSchedulerController.roomDisplayController = roomDisplayController;
	}

	/**
	 * This subclass is used to set the action for the Add button. When the button
	 * is pressed it calls the associated controller to make the
	 * {@link cst8284.asgmt4.view.RoomBookingAddDialog} visible
	 * 
	 * @version 1.0
	 * @author Michael Daly
	 * 
	 */
	class AddListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			getAddController().getRoomBookingAddDialog().clearInputs();
			getAddController().getRoomBookingAddDialog().setVisible(true);

			RoomSchedulerDialog.updateButtons(false);
		}
	}

	/**
	 * This subclass is used to set the action for the Add button. When the button
	 * is pressed it calls the associated controller to make the
	 * {@link cst8284.asgmt4.view.RoomBookingModifyDialog} visible
	 * 
	 * @version 1.0
	 * @author Michael Daly
	 * 
	 */
	class ModifyListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			getModifyController().getRoomBookingModifyDialog().clearInputs();
			getModifyController().getRoomBookingModifyDialog().setVisible(true);
			RoomSchedulerDialog.updateButtons(false);

		}
	}

	class RoomDisplayListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			getRoomDisplayController().getRoomBookingDisplayDialog().clearInputs();
			getRoomDisplayController().getRoomBookingDisplayDialog().setVisible(true);
			RoomSchedulerDialog.updateButtons(false);

		}
	}

	/**
	 * This subclass is used to set the action for the save button. When clicked it
	 * calls the {@code setFileChooser()} method and updates the title.
	 *
	 * @version 1.0
	 * @author Michael Daly
	 * 
	 */
	class SaveListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			RoomSchedulerController.setFileChooser(RoomSchedulerConstants.BOOKING_BACKUP);
			updateTitle();
		}
	}

	/**
	 * This subclass is used to set the action for the load button. When clicked it
	 * calls the {@code setFileChooser()} method and updates the title.
	 *
	 * @version 1.0
	 * @author Michael Daly
	 * 
	 */
	class LoadListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			RoomSchedulerController.setFileChooser(RoomSchedulerConstants.BOOKING_LOAD);
			updateTitle();

		}
	}

	/**
	 * This subclass is used to set the action for the exit button. It calls the
	 * scheduler to execute a save before closing the window.
	 *
	 * @version 1.0
	 * @author Michael Daly
	 * 
	 */
	class ExitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			getScheduler().executeMenuItem(RoomSchedulerConstants.BOOKING_BACKUP, null);
			getRoomSchedulerDialog().closeFrame();
		}
	}

}
