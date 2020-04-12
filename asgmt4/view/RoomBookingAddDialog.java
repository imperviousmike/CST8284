package cst8284.asgmt4.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import firstyear.secondsemester.cst8284.asgmt4.roomScheduler.RoomScheduler;
import firstyear.secondsemester.cst8284.asgmt4.utility.RoomSchedulerConstants;

/**
 * This sets the look and feel of the add booking dialog. It is a child of
 * GenericRoomSchedulerDialog to maintain uniformity across dialog windows
 * 
 * @version 1.0
 * @author Michael Daly
 * 
 */
public class RoomBookingAddDialog extends GenericRoomSchedulerDialog {

	/**
	 * Serialized due to being a child of GenericRoomSchedulerDialog
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Buttons for the button panel
	 */
	private JButton saveButton = new JButton(RoomSchedulerConstants.SAVE_BOOKING_SAVE.toString());
	private JButton exitButton = new JButton(RoomSchedulerConstants.BOOKING_EXIT.toString());

	/**
	 * List of text fields
	 */
	private static JTextField[] fields = { new JTextField(8), new JTextField(8), new JTextField(8), new JTextField(61),
			new JTextField(12), new JTextField(60), new JTextField(60), new JTextField(60) };

	/**
	 * Labels to be used with the text fields
	 */
	private static RoomSchedulerConstants[] labels = { RoomSchedulerConstants.BOOKING_DATE,
			RoomSchedulerConstants.BOOKING_START, RoomSchedulerConstants.BOOKING_END,
			RoomSchedulerConstants.SAVE_BOOKING_NAME, RoomSchedulerConstants.SAVE_BOOKING_PHONE,
			RoomSchedulerConstants.SAVE_BOOKING_ORG, RoomSchedulerConstants.SAVE_BOOKING_EVENT,
			RoomSchedulerConstants.SAVE_BOOKING_DESC };

	/**
	 * This is needed when loading the application if there are no entries it sets a
	 * date, when a booking is added, the title needs to update the date with the
	 * date of entries
	 */
	private RoomSchedulerDialog dialog;

	/**
	 * Constructor that calls up to the parent class to set the layout of the text
	 * fields and label and add a binding. This adds the button panel, and sets the
	 * title
	 * 
	 * @param scheduler the {@link cst8284.asgmt4.roomScheduler.RoomScheduler}
	 *                  instance needed to get the current stored room info for the
	 *                  bookings
	 * @param dialog    the root {@link cst8284.asgmt4.view.RoomSchedulerDialog} for
	 *                  updating the title with the relevant date when an entry is
	 *                  added(if there are no entries already available)
	 */
	public RoomBookingAddDialog(RoomScheduler scheduler, RoomSchedulerDialog dialog) {
		super(fields, labels);
		setRoomSchedulerDialog(dialog);
		add(buildButtonPanel(), BorderLayout.SOUTH);
		pack();
		setResizable(false);
		updateTitle(scheduler);
		setVisible(false);

	}

	/**
	 * Builds the button panel
	 * 
	 * @return The built panel with the buttons
	 */
	@Override
	protected JPanel buildButtonPanel() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(saveButton);
		buttonPanel.add(exitButton);
		buttonPanel.setVisible(true);
		return buttonPanel;

	}

	/**
	 * Getter for the list of fields, used for processing the input
	 * 
	 * @return an {@code Array} of the set fields
	 */
	private static JTextField[] getFields() {
		return fields;
	}

	/**
	 * Getter for the list of labels, used for processing the input
	 * 
	 * @return an {@code Array} of the set labels
	 */
	private static RoomSchedulerConstants[] getLabels() {
		return labels;
	}

	/**
	 * Sets the listener for the save button
	 * 
	 * @param al the listener that responds when the button is pressed
	 */
	public void saveBookingAddListener(ActionListener al) {
		saveButton.addActionListener(al);
	}

	/**
	 * Sets the listener for the exit button
	 * 
	 * @param al the listener that responds when the button is pressed
	 */
	public void exitBookingAddListener(ActionListener al) {
		exitButton.addActionListener(al);
	}

	/**
	 * Used to populate inputs set in the text fields to be processed
	 */
	@Override
	public void populateInputs() {
		LinkedHashMap<RoomSchedulerConstants, JTextField> input = new LinkedHashMap<>();
		for (int i = 0; i < getLabels().length; i++) {
			input.put(getLabels()[i], getFields()[i]);
		}
		setInputs(input);
	}

	/**
	 * Iterates through all fields and resets them to empty
	 */
	@Override
	public void clearInputs() {
		for (JTextField field : getFields()) {
			field.setText("");
			field.setBackground(Color.white);
		}
	}

	/**
	 * Provides functionality when the window is closed
	 */
	@Override
	public void exitWindow() {
		getRoomSchedulerDialog().updateTitleString(null);
		dispose();
	}

	/**
	 * Getter for the main scheduler dialog
	 * 
	 * @return the stored reference to the dialog
	 */
	private RoomSchedulerDialog getRoomSchedulerDialog() {
		return dialog;
	}

	/**
	 * Setter for the main scheduler dialog
	 * 
	 * @param dialog the reference to the dialog to be stored
	 */
	private void setRoomSchedulerDialog(RoomSchedulerDialog dialog) {
		this.dialog = dialog;
	}

}