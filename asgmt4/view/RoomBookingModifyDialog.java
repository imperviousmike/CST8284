package cst8284.asgmt4.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import firstyear.secondsemester.cst8284.asgmt4.roomScheduler.RoomScheduler;
import firstyear.secondsemester.cst8284.asgmt4.utility.RoomSchedulerConstants;

/**
 * Generates a completed {@code RoomBookingModifyDialog} with the correct look.
 * This window is used to find a booking and provide the ability to change and
 * delete it. Provides functionality for the controllers to be able to interact
 * with the window to produce results for the user. It is a child of
 * GenericRoomSchedulerDialog to maintain uniformity across dialog windows
 * 
 * @version 1.0
 * @author Michael Daly
 * 
 */
public class RoomBookingModifyDialog extends GenericRoomSchedulerDialog {

	/**
	 * Serialized due to being a child of GenericRoomSchedulerDialog
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Buttons for the button panel
	 */
	private static JButton findButton = new JButton(RoomSchedulerConstants.MODIFY_BOOKING_FIND.toString());
	private static JButton changeButton = new JButton(RoomSchedulerConstants.MODIFY_BOOKING_CHANGE.toString());
	private static JButton deleteButton = new JButton(RoomSchedulerConstants.MODIFY_BOOKING_DELETE.toString());
	private static JButton resetButton = new JButton(RoomSchedulerConstants.MODIFY_RESET.toString());
	private static JButton exitButton = new JButton(RoomSchedulerConstants.BOOKING_EXIT.toString());

	/**
	 * Constants used for sizing of components
	 */
	private static final int DIALOG_X = 700;
	private static final int DIALOG_Y = 300;
	private static final int TEXTAREA_X = 250;
	private static final int TEXTAREA_Y = 200;

	/**
	 * Text to display a found entry
	 */
	private static JTextArea displayText;

	/**
	 * List of text fields
	 */
	private static JTextField[] fields = { new JTextField(8), new JTextField(8), new JTextField(8), new JTextField(8) };

	/**
	 * Labels to be used with the text fields
	 */
	private static RoomSchedulerConstants[] labels = { RoomSchedulerConstants.BOOKING_DATE,
			RoomSchedulerConstants.BOOKING_START, RoomSchedulerConstants.MODIFY_NEW_START,
			RoomSchedulerConstants.MODIFY_NEW_END };

	/**
	 * Constructor that calls up to the parent class to set the layout of the text
	 * fields and label and add a binding, adds the button panel, and sets the title
	 * 
	 * @param scheduler A {@link cst8284.asgmt4.roomScheduler.RoomScheduler} used to
	 *                  get the current bookings room information to set the title
	 */
	public RoomBookingModifyDialog(RoomScheduler scheduler) {
		super(fields, labels);
		add(buildButtonPanel(), BorderLayout.SOUTH);
		add(buildTextPanel(), BorderLayout.EAST);
		setFieldStatus(false);
		setPreferredSize(new Dimension(DIALOG_X, DIALOG_Y));
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

		setButtons(false);

		buttonPanel.add(findButton);
		buttonPanel.add(changeButton);
		buttonPanel.add(deleteButton);
		buttonPanel.add(resetButton);
		buttonPanel.add(exitButton);
		buttonPanel.setVisible(true);
		return buttonPanel;

	}

	/**
	 * Builds the text panel that shows when a booking is found
	 * 
	 * @return The built panel with the buttons
	 */
	private JPanel buildTextPanel() {
		JPanel textPanel = new JPanel();
		displayText = new JTextArea();
		displayText.setEditable(false);
		displayText.setPreferredSize(new Dimension(TEXTAREA_X, TEXTAREA_Y));
		textPanel.add(displayText);
		textPanel.setVisible(true);
		return textPanel;
	}

	/**
	 * When a booking is found/not found this updates the text field and sets the
	 * buttons to there proper status
	 * 
	 * @param result the text from the found/not found booking
	 */
	public void processFindResult(String result) {
		setDisplayText(result);
		setFieldStatus(false);
		if (!result.contains("No booking scheduled between")) {
			setButtons(true);
			setFieldStatus(true);
		}

	}

	/**
	 * Provides functionality for when the window is exited
	 */
	@Override
	public void exitWindow() {
		dispose();
	}

	/**
	 * Resets all text fields/text area, and updates the status of the button to its
	 * default state
	 */
	public void resetWindow() {
		for (JTextField text : fields) {
			text.setText("");
		}
		setDisplayText("");
		setButtons(false);
		setFieldStatus(false);

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
	 * Setter for the text area
	 * 
	 * @param text the text to display
	 */
	private void setDisplayText(String text) {
		displayText.setText(text);
	}

	/**
	 * Turns buttons on/off based on condition provided. If the find button is
	 * enabled then we cant change or delete due to a booking not being found. And
	 * if a booking is found the find button is disabled to ensure the
	 * change/deletion is on the found entry
	 * 
	 * @param bookingFound the status the buttons should be set to
	 *
	 * 
	 */
	private void setButtons(boolean bookingFound) {
		changeButton.setEnabled(bookingFound);
		deleteButton.setEnabled(bookingFound);
		findButton.setEnabled(!bookingFound);

	}

	/**
	 * Sets the status for the input fields based on if a booking is found. If one
	 * is found we disable the date and time since there is a booking, this prevents
	 * problems with changing and deleting bookings of unverified inputs
	 * 
	 * @param bookingFound the status for the text fields
	 */
	private void setFieldStatus(boolean bookingFound) {
		for (int i = 0; i < fields.length; i++) {
			JTextField field = fields[i];
			if (i > 1) {
				field.setEnabled(bookingFound);
			} else {
				field.setEnabled(!bookingFound);
			}
		}

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
	 * Sets the listener for the find button
	 * 
	 * @param al the listener that responds when the button is pressed
	 */
	public void findBookingDisplayListener(ActionListener al) {
		findButton.addActionListener(al);
	}

	/**
	 * Sets the listener for the change button
	 * 
	 * @param al the listener that responds when the button is pressed
	 */
	public void changeBookingDisplayListener(ActionListener al) {
		changeButton.addActionListener(al);
	}

	/**
	 * Sets the listener for the delete button
	 * 
	 * @param al the listener that responds when the button is pressed
	 */
	public void deleteBookingDisplayListener(ActionListener al) {
		deleteButton.addActionListener(al);
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
	 * Sets the listener for the reset button
	 * 
	 * @param al the listener that responds when the button is pressed
	 */
	public void resetBookingDisplayListener(ActionListener al) {
		resetButton.addActionListener(al);
	}

	/**
	 * Resets all fields, buttons, and text areas
	 */
	@Override
	public void clearInputs() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				setDisplayText("");
				for (JTextField field : getFields()) {
					field.setText("");
					field.setBackground(Color.white);
				}
				setButtons(false);
				setFieldStatus(false);
			}
		});

	}

}