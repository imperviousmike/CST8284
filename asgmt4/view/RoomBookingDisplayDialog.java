package cst8284.asgmt4.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.LinkedHashMap;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import firstyear.secondsemester.cst8284.asgmt4.roomScheduler.RoomScheduler;
import firstyear.secondsemester.cst8284.asgmt4.utility.RoomSchedulerConstants;

/**
 * This window is used to display day bookings of other dates. It is a child of
 * GenericRoomSchedulerDialog to maintain uniformity across dialog windows
 * 
 * @version 1.0
 * @author Michael Daly
 * 
 */
public class RoomBookingDisplayDialog extends GenericRoomSchedulerDialog {

	/**
	 * Serialized due to being a child of GenericRoomSchedulerDialog
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Buttons for the button panel
	 */
	private JButton findButton = new JButton(RoomSchedulerConstants.DISPLAY_BOOKING_DAY.toString());
	private JButton exitButton = new JButton(RoomSchedulerConstants.BOOKING_EXIT.toString());

	/**
	 * List of text fields
	 */
	private static JTextField[] fields = { new JTextField(8) };

	/**
	 * Labels to be used with the text fields
	 */
	private static RoomSchedulerConstants[] labels = { RoomSchedulerConstants.BOOKING_DATE, };

	/**
	 * This is needed when loading the application if there are no entries it sets a
	 * date, when a booking is added, the title needs to update the date with the
	 * date of entries
	 */
	private RoomSchedulerDialog dialog;

	public RoomBookingDisplayDialog(RoomScheduler scheduler, RoomSchedulerDialog dialog) {
		super(fields, labels);
		setRoomSchedulerDialog(dialog);
		add(buildButtonPanel(), BorderLayout.SOUTH);
		pack();
		setResizable(false);
		updateTitle(scheduler);
		setVisible(false);
	}

	/**
	 * Getter for the stored {@link cst8284.asgmt4.view.RoomSchedulerDialog}
	 * 
	 * @return a reference to the stored instance of the
	 *         {@link cst8284.asgmt4.view.RoomSchedulerDialog}
	 */
	private RoomSchedulerDialog getRoomSchedulerDialog() {
		return dialog;
	}

	/**
	 * Setter for the stored {@link cst8284.asgmt4.view.RoomSchedulerDialog}
	 * 
	 * @param dialog a reference to an instance of
	 *               {@link cst8284.asgmt4.view.RoomSchedulerDialog} to be stored
	 */
	private void setRoomSchedulerDialog(RoomSchedulerDialog dialog) {
		this.dialog = dialog;
	}

	@Override
	protected JPanel buildButtonPanel() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(findButton);
		buttonPanel.add(exitButton);
		buttonPanel.setVisible(true);
		return buttonPanel;
	}

	/**
	 * Exits the current window
	 */
	@Override
	public void exitWindow() {
		dispose();
	}

	/**
	 * Sets the listener for the save button
	 * 
	 * @param al the listener that responds when the button is pressed
	 */
	public void findBookingDisplayListener(ActionListener al) {
		findButton.addActionListener(al);
	}

	/**
	 * Sets the listener for the exit button
	 * 
	 * @param al the listener that responds when the button is pressed
	 */
	public void exitBookingDisplayListener(ActionListener al) {
		exitButton.addActionListener(al);
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
	 * Getter for the list of fields, used for processing the input
	 * 
	 * @return an {@code Array} of the set fields
	 */
	private static RoomSchedulerConstants[] getLabels() {
		return labels;
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

	public void updateText(String result, Calendar cal) {
		getRoomSchedulerDialog().updateText(result, cal);

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

}
