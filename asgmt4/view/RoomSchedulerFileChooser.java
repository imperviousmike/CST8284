package cst8284.asgmt4.view;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import firstyear.secondsemester.cst8284.asgmt4.utility.RoomSchedulerConstants;

/**
 * A more specialized {@code JFileChooser} set with common options to maximize
 * code reuse. Can be called and used from anywhere in the application so any
 * file chooser has uniformity.
 * 
 * @version 1.0
 * @author Michael Daly
 * 
 */
public class RoomSchedulerFileChooser extends JFileChooser {

	/**
	 * Serialization necessary from parent component
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the file chooser to ensure file choosers follow similar
	 * rules, and to avoid code duplication. The parameter determines if this is a
	 * load or a save and a filter is applied to either to make finding/saving
	 * booking files easier
	 */
	public RoomSchedulerFileChooser(RoomSchedulerConstants type) {

		FileNameExtensionFilter filter = new FileNameExtensionFilter("Booking file", "booking");
		setFileFilter(filter);

		if (type == RoomSchedulerConstants.BOOKING_BACKUP) {
			showSaveDialog(null);
		}

		if (type == RoomSchedulerConstants.BOOKING_LOAD) {
			showOpenDialog(null);
		}
	}

}
