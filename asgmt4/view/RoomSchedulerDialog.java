package cst8284.asgmt4.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import firstyear.secondsemester.cst8284.asgmt4.roomScheduler.RoomBooking;
import firstyear.secondsemester.cst8284.asgmt4.roomScheduler.RoomScheduler;
import firstyear.secondsemester.cst8284.asgmt4.utility.RoomSchedulerConstants;

/**
 * Allows the creation of a window that is used as the first point of entry for
 * the application. This used to present the user with options of
 * adding/modifying bookings and showing them the information of the current
 * stored bookings.
 * 
 * @version 1.0
 * @author Michael Daly
 * 
 */
public class RoomSchedulerDialog extends JFrame {

	/**
	 * Serialization needed due to extend
	 * JFrame(TransferHandler.HasGetTransferHandler)
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Sets the size of the text area
	 */
	private static final int TEXTAREA_Y = 500;
	private static final int TEXTAREA_X = 500;

	/**
	 * The text area used to display all bookings.
	 */
	private static JTextArea bookingDisplay = new JTextArea();

	/**
	 * Buttons to provide functionality to application
	 */
	private static JButton booking_add = new JButton(RoomSchedulerConstants.BOOKING_ADD.toString());
	private static JButton booking_modify = new JButton(RoomSchedulerConstants.BOOKING_MODIFY.toString());
	private static JButton booking_save = new JButton(RoomSchedulerConstants.BOOKING_BACKUP.toString());
	private static JButton booking_load = new JButton(RoomSchedulerConstants.BOOKING_LOAD.toString());
	private static JButton booking_exit = new JButton(RoomSchedulerConstants.BOOKING_EXIT.toString());
	private static JButton booking_show = new JButton(RoomSchedulerConstants.BOOKING_DISPLAY.toString());
	/**
	 * {@link cst8284.asgmt4.roomScheduler.RoomScheduler} needed for displaying the
	 * days bookings
	 */
	private static RoomScheduler scheduler;

	/**
	 * Constructor for the main window. Calls the individual sub frame methods to
	 * build each component.
	 * 
	 * @param scheduler {@link cst8284.asgmt4.roomScheduler.RoomScheduler} needed
	 *                  for building the room/bookings info displays
	 */
	public RoomSchedulerDialog(RoomScheduler scheduler) {

		setScheduler(scheduler);

		setLayout(new BorderLayout());

		add(setTextPanel(bookingDisplay), BorderLayout.CENTER);
		add(setWestPanel(), BorderLayout.WEST);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		updateTitleString(null);
		pack();

	}

	/**
	 * Creates the side button panel, and adds the buttons to them.
	 * 
	 * @return The formatted panel
	 */
	private static JPanel setWestPanel() {
		JPanel controlPanel = new JPanel(new GridLayout(6, 1));
		JPanel westPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.weighty = 1;

		controlPanel.add(booking_add);
		controlPanel.add(booking_modify);
		controlPanel.add(booking_show);
		controlPanel.add(booking_save);
		controlPanel.add(booking_load);
		controlPanel.add(booking_exit);

		westPanel.add(controlPanel, gbc);
		return westPanel;
	}

	/**
	 * Sets the look and feel of the text panel. Ensures it has a scroll bar and
	 * sets the proper sizes. This also prevents the user from editing the panel.
	 * 
	 * @param jta The {@code JTextArea} to set the look.
	 * @return a panel with the formatted text area
	 */
	private static JPanel setTextPanel(JTextArea jta) {

		JScrollPane centerPane = new JScrollPane(jta);
		centerPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jta.setEditable(false);
		jta.setPreferredSize(new Dimension(TEXTAREA_X, TEXTAREA_Y));
		centerPane.getViewport().add(jta);
		setDisplayText();
		JPanel jp = new JPanel();
		jp.add(centerPane);
		return jp;
	}

	/**
	 * Getter for the instance of the stored
	 * {@link cst8284.asgmt4.roomScheduler.RoomScheduler}.
	 * 
	 * @return a reference to the stored
	 *         {@link cst8284.asgmt4.roomScheduler.RoomScheduler}
	 */
	private static RoomScheduler getScheduler() {
		return scheduler;

	}

	/**
	 * Sets the title for the frame. Based on the specifications it builds a string
	 * in the same format. It gets the room number from the
	 * {@link cst8284.asgmt4.roomScheduler.RoomScheduler}. It attempts to grab the
	 * date from the first saved booking, if there are no entries then it sets the
	 * title to the current date.
	 * 
	 * @param date If a date is provided set the title with that date instead
	 */
	public void updateTitleString(Calendar date) {
		StringBuilder sb = new StringBuilder();
		sb.append("Room Bookings for ");
		sb.append(getScheduler().getRoom().getRoomNumber());
		sb.append(" for ");
		SimpleDateFormat sdf = new SimpleDateFormat("MMM. dd, yyyy");
		if (date == null) {
			if (getScheduler().getRoomBookings() != null && !getScheduler().getRoomBookings().isEmpty()) {
				Calendar cal = getScheduler().getRoomBookings().get(0).getTimeBlock().getStartTime();
				sb.append(sdf.format(cal.getTime()).toString());
			} else {
				sb.append(sdf.format(Calendar.getInstance().getTime()).toString());
			}
		} else {
			Calendar cal = (Calendar) date.clone();
			sb.append(sdf.format(cal.getTime()).toString());
		}
		setTitle(sb.toString());

	}

	/**
	 * Update the buttons based on the status condition. This is used for when a new
	 * window is open when a button is pressed there are no available options and
	 * the current window must be used/closed before returning the main window
	 * 
	 * @param status {@code boolean} if the buttons should be on/off
	 */
	public static void updateButtons(boolean status) {
		booking_add.setEnabled(status);
		booking_modify.setEnabled(status);
		booking_save.setEnabled(status);
		booking_load.setEnabled(status);
		booking_exit.setEnabled(status);
		booking_show.setEnabled(status);
	}

	/**
	 * Sets the text area to an entire days bookings. If there are no entries
	 * already in the {@link cst8284.asgmt4.roomScheduler.RoomScheduler}'s list than
	 * we grab the current day and displays the days bookings
	 */
	public static void setDisplayText() {
		ArrayList<RoomBooking> bookings = getScheduler().getRoomBookings();
		if (bookings != null && !bookings.isEmpty()) {
			Calendar cal = (Calendar) bookings.get(0).getTimeBlock().getStartTime().clone();
			getBookingDisplay().setText(getScheduler().displayDayBookings(cal));
		} else {
			getBookingDisplay().setText(getScheduler().displayDayBookings(Calendar.getInstance()));
		}
	}

	/**
	 * Getter for this text area
	 * 
	 * @return refrence to this text area
	 */
	public static JTextArea getBookingDisplay() {
		return bookingDisplay;
	}

	/**
	 * {@code setText()} uses the Swing thread waiting to execute, so to force the
	 * textArea to update we pass it to a separate thread.
	 */
	void updateText(String text, Calendar cal) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				bookingDisplay.setText(text);
				updateTitleString(cal);
			}
		});
	}

	/**
	 * Setter for the scheduler
	 * 
	 * @param scheduler reference to the {@code RoomScheduler} to be stored
	 */
	private void setScheduler(RoomScheduler scheduler) {
		RoomSchedulerDialog.scheduler = scheduler;
	}

	/**
	 * Functionality for when the window is closed
	 */
	public void closeFrame() {
		dispose();
	}

	/**
	 * Sets the listener for the add button
	 * 
	 * @param al the listener that responds when the button is pressed
	 */
	public void addBookingAddListener(ActionListener al) {
		booking_add.addActionListener(al);
	}

	/**
	 * Sets the listener for the display button
	 * 
	 * @param al the listener that responds when the button is pressed
	 */
	public void addBookingModifyListener(ActionListener al) {
		booking_modify.addActionListener(al);
	}

	/**
	 * Sets the listener for the save button
	 * 
	 * @param al the listener that responds when the button is pressed
	 */
	public void addRoomDisplayListener(ActionListener al) {
		booking_show.addActionListener(al);
	}

	/**
	 * Sets the listener for the save button
	 * 
	 * @param al the listener that responds when the button is pressed
	 */
	public void addBookingSaveListener(ActionListener al) {
		booking_save.addActionListener(al);
	}

	/**
	 * Sets the listener for the load button
	 * 
	 * @param al the listener that responds when the button is pressed
	 */
	public void addBookingLoadListener(ActionListener al) {
		booking_load.addActionListener(al);
	}

	/**
	 * Sets the listener for the exit button
	 * 
	 * @param al the listener that responds when the button is pressed
	 */
	public void addBookingExitListener(ActionListener al) {
		booking_exit.addActionListener(al);
	}

}
