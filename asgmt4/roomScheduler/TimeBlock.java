package cst8284.asgmt4.roomScheduler;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Contains the date, start time, and end time for every
 * {@link cst8284.asgmt4.roomScheduler.RoomBooking}. Provides functionality to
 * access and change the values as well as determining the duration of the
 * booking and the ability to check if two bookings overlap. Implements
 * {@code Serializable} for versioning during loading and saving of {code
 * RoomBooking}s.
 * 
 * @author Michael Daly
 * @version 2.0
 * 
 */

public class TimeBlock implements Serializable {

	/**
	 * Holds the date and time a booking starts.
	 */
	private Calendar startTime;
	/**
	 * Holds the date and time a booking ends
	 */
	private Calendar endTime;
	/**
	 * {@code serialVersionUID} is used during serialization as a way to version the
	 * object when saving, this is also needed when de-serializing a saved object to
	 * check if its the expected version.
	 */
	public static final long serialVersionUID = 1L;

	/**
	 * Constructs a {@code TimeBlock} object. Sets a default value to the beginning
	 * of a possible booking(8:00) and ends at the end of a possible booking(23:00).
	 * 
	 */
	public TimeBlock() {
		this(new Calendar.Builder().setFields(Calendar.HOUR_OF_DAY, 8).build(),
				new Calendar.Builder().setFields(Calendar.HOUR_OF_DAY, 23).build());

	}

	/**
	 * Constructs a {@code TimeBlock} object using the passed start and end time.
	 * 
	 * @param start Calendar set to the start time for the booking
	 * @param end   Calendar set to the end time for the booking
	 */
	public TimeBlock(Calendar start, Calendar end) {
		setStartTime(start);
		setEndTime(end);
	}

	/**
	 * Returns the reference to the object set in the {@code startTime} field.
	 * 
	 * @return Calendar reference stored in {@code startTime}.
	 */
	public Calendar getStartTime() {
		return startTime;
	}

	/**
	 * Sets the reference to the object in the {@code startTime} field.
	 * 
	 * @param startTime Calendar reference to be set.
	 * 
	 */
	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	/**
	 * Returns the reference to the object set in the {@code endTime} field.
	 * 
	 * @return Calendar reference stored in {@code endTime}.
	 */
	public Calendar getEndTime() {
		return endTime;
	}

	/**
	 * Sets the reference to the object in the {@code endTime} field.
	 * 
	 * @param endTime Calendar reference to be set.
	 * 
	 */
	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	/**
	 * Subtracts the end time from the start time to determine the length of a
	 * booking.
	 * 
	 * @return The value determined from subtracting the end from the start.
	 * 
	 */
	public int duration() {

		return getEndTime().get(Calendar.HOUR_OF_DAY) - getStartTime().get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * Checks if two {@code TimeBlock} overlap each other. Takes a {code TimeBlock}
	 * from another booking and uses {@code Calendar}'s {@code before()}
	 * functionality to compare start and ends.
	 * 
	 * Both conditions need to be met to not overlap. Not only does this start time
	 * need to be before the compared end time , but also the compared object CANNOT
	 * start before this end time.
	 * 
	 * For example 8:00 - 12:00 and 10:00 - 11:00. The start time is before the end
	 * but the compares start time is actually before the first ends time so here we
	 * catch the overlap. If the start and time were just compared than we would
	 * pass even though it overlaps.
	 * 
	 * @param newBlock TimeBlock reference to be compared against.
	 * 
	 */
	public boolean overlaps(TimeBlock newBlock) {

		return getStartTime().before(newBlock.getEndTime()) && newBlock.getStartTime().before(getEndTime());

	}

	/**
	 * Returns a reference to a formatted time out of the start time and end time.
	 * 
	 * @return The formatted String value.
	 */
	@Override
	public String toString() {
		return String.format("%s:00 - %s:00", getStartTime().get(Calendar.HOUR_OF_DAY),
				getEndTime().get(Calendar.HOUR_OF_DAY));
	}

}
