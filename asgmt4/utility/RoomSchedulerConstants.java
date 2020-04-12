package cst8284.asgmt4.utility;

/**
 * To maintain consistency between fields and buttons between all portions of
 * the application this class establishes enum that provided relevance to each
 * component so processing the logic can be uniform
 * 
 * @version 1.0
 * @author Michael Daly
 * 
 */
public enum RoomSchedulerConstants {

	BOOKING_DATE("Booking Date (YYYYMMDD):"), BOOKING_MODIFY("Modify a booking"),
	BOOKING_START("Booking Start Time (2 p.m. or 14:00):"), BOOKING_END("Booking End Time (2 p.m. or 14:00):"),
	SAVE_BOOKING_SAVE("Save"), SAVE_BOOKING_NAME("Client Name (FirstName LastName):"),
	SAVE_BOOKING_PHONE("Phone Number (e.g. 613-555-1212):"), SAVE_BOOKING_ORG("Organization (optional):"),
	SAVE_BOOKING_EVENT("Event Category:"), SAVE_BOOKING_DESC("Description:"), BOOKING_ADD("Add Booking"),
	BOOKING_DISPLAY("Display Existing Booking"), BOOKING_BACKUP("Backup Bookings to File"),
	BOOKING_LOAD("Load Bookings from File"), BOOKING_EXIT("Exit"), MODIFY_BOOKING_DELETE("Delete Booking"),
	MODIFY_BOOKING_FIND("Find Booking"), MODIFY_BOOKING_CHANGE("Change Booking"),
	MODIFY_NEW_START("New Booking Start Time (2 p.m. or 14:00):"),
	MODIFY_NEW_END("New Booking End Time (2 p.m. or 14:00):"), MODIFY_RESET("Reset"),
	DISPLAY_BOOKING_DAY("Find Days Booking"), DISPLAY_BOOKING_CALENDAR("Calendar");

	/**
	 * The text used for displaying on buttons and labels
	 */
	private String field;

	/**
	 * Constructor for each enum binding the text to the name
	 */
	private RoomSchedulerConstants(String field) {
		this.setField(field);
	}

	/**
	 * Getter for the field
	 * 
	 * @return the stored text for that specific field
	 */
	private String getField() {
		return field;
	}

	/**
	 * Sets the field
	 * 
	 * @param field the given text to be tied to the value
	 */
	private void setField(String field) {
		this.field = field;
	}

	@Override
	/**
	 * Used when you need to set labels or buttons for the specific constants
	 * 
	 * @return the text stored in the constant
	 */
	public String toString() {
		return getField();
	}

}
